package touchmars.technology.email;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@SuppressWarnings("serial")
@Service
public class ReportServiceImpl implements ReportService {

	private static final String END = "END";
	private static final String CUSTOMER = "customer";
	
	@Autowired
	private ReportProperties reportProperties;
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private InvoiceRepository invoiceRepository;
	
	public Task getTask(Long taskId){
		return taskRepository.findOne(taskId);
	}

	@Override
	public Task generateTaskFromExcel(String filename) throws EncryptedDocumentException, InvalidFormatException, IOException {
		
		String folder = reportProperties.getLocation();
		File file = new File(folder, filename.endsWith("xlsx")?filename:filename+".xlsx");			
		if(!file.exists())
			throw new IllegalArgumentException(String.format("File %s doesn't exist!", filename));
		Workbook wb = WorkbookFactory.create(file);
		Sheet sheet = wb.getSheetAt(0);
		
		//init and persist a Task first
		Task task = new Task();
		task.setCreateDate(Calendar.getInstance().getTime());
		task.setFilename(filename);
		task = taskRepository.save(task);
		
		List<Invoice> invoices = new ArrayList<Invoice>();
		Row titleRow = null;
		Iterator<Row> rowIter = sheet.iterator();
		while(rowIter.hasNext()){
			Row row = rowIter.next();
			if(row!=null){
				Cell cell0 = row.getCell(0);
				if(cell0!=null){
					String cell0String = cell0.getStringCellValue().trim();
					if(StringUtils.hasText(cell0String)){
						if(titleRow==null && cell0String.toLowerCase().startsWith(CUSTOMER)){
							titleRow = row;
						}else if(cell0String.equalsIgnoreCase(END)){
							break;
						}else if(titleRow==null){
							//skip
						}else{	//data row
							Invoice invoice = handleRow(row, task, folder);
							invoices.add(invoice);
						}						
					}
				}
			}			
		}			
		//loop & write Report for every Invoice
		for(Invoice i: invoices){
			try {
				String pdfFilename = createReport4Invoice(i);
				i.setInvoicePdfFileName(pdfFilename);
//				invoiceRepository.save(i);
			} catch (JRException e) {
				e.printStackTrace();
			}
		}			
		taskRepository.save(task);
		//finally return the Task
		return task;
	}
	
	private Invoice handleRow(Row row, Task task, String folder){
		String clientName = row.getCell(0).getStringCellValue();
		String clientStatus = null;
		String clientNo = row.getCell(1).getStringCellValue().trim();
		
		String[] possibleTokens = clientNo.trim().split(" ");
		if(possibleTokens!=null && possibleTokens.length>1){
			String firstToken = possibleTokens[0];
			String status = clientNo.substring(firstToken.length()).trim();			
			clientNo = firstToken;
			clientStatus = status;
		}
		
		String clientEmail = row.getCell(2).getStringCellValue();
		Invoice invoice = invoiceRepository.findByClientNoAndTask(clientNo, task);
		if(invoice!=null){
			//has existing Invoice matching to clientNo
		}else{
			invoice = new Invoice();
			invoice.setTask(task);
			task.getInvoices().add(invoice);
			invoice.setInvoiceFolder(folder);
			invoice.setClientName(clientName);
			invoice.setClientNo(clientNo);
			invoice.setClientStatus(clientStatus);
			invoice.setClientEmail(clientEmail);
		}
		InvoiceLineItem item = new InvoiceLineItem();
		item.setInvoice(invoice);
		String docId = row.getCell(3).getStringCellValue();
		if(docId.trim().endsWith(".")){
			docId = docId.trim().substring(0, docId.trim().length()-1);
		}
		Date docDt = row.getCell(4).getDateCellValue();
		String currency = row.getCell(5).getStringCellValue();
		double total = row.getCell(6).getNumericCellValue();
		double range1 = row.getCell(7).getNumericCellValue();
		double range2 = row.getCell(8).getNumericCellValue();
		double range3 = row.getCell(9).getNumericCellValue();
		double range4 = row.getCell(10).getNumericCellValue();
		item.setDocId(docId);
		item.setDocDt(docDt);
		item.setCurrency(currency);
		item.setBalance(total);
		item.setRange1(range1);
		item.setRange2(range2);
		item.setRange3(range3);
		item.setRange4(range4);
		//add this brand new InvoiceLineItem to the Invoice
		invoice.getItems().add(item);
		//persist Invoice for every new InvoiceLineItem
		invoice = invoiceRepository.save(invoice);
		return invoice;
	}
	
	public String createReport4Invoice(Invoice invoice) throws JRException{
		String jasperFilename = reportProperties.getJasperFilename();
		File jasperFile = new File(jasperFilename);
		if(!jasperFile.exists())
			throw new IllegalArgumentException(String.format("No Jasper file found: %s", jasperFilename));
		String destFilename = invoice.getClientNo() + "_" + invoice.getTask().getCreateDate().getTime() + ".pdf";
		Map<String, Object> parameters = getReportParamMap(invoice);
		JRDataSource ds = new JRBeanCollectionDataSource(invoice.getItems());
		JasperPrint jp = JasperFillManager.fillReport(jasperFile.getAbsolutePath(), parameters, ds);
		JasperExportManager.exportReportToPdfFile(jp, destFilename);
		return destFilename;
	}
	
	private Map<String, Object> getReportParamMap(Invoice invoice){
		Map <String, Object> parameters = new HashMap<String, Object>();
		parameters.put("invoiceNo", invoice.getInvoiceNo());
		parameters.put("clientName", invoice.getClientName());
		parameters.put("clientNo", invoice.getClientNo());
		parameters.put("clientStatus", invoice.getClientStatus());
		parameters.put("clientEmail", invoice.getClientEmail());		
		return parameters;
	}
	

}
