package touchmars.technology.email;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name="invoice")
@Table(name = "invoice")
public class Invoice {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	@Column(name = "invoice_num")
	private String invoiceNo;
	
	@Column(name = "client_name")
	private String clientName;
	
	@Column(name = "client_num")
	private String clientNo;
	
	@Column(name = "client_status")
	private String clientStatus;
	
	@Column(name = "client_email")
	private String clientEmail;
	
	@Column(name = "invoice_loc")	
	private String invoiceFolder;
	
	@Column(name = "client_filename")	
	private String invoicePdfFileName;
	
	@Column(name = "client_email_sent_dt")
	@Temporal(TemporalType.DATE)
	private Date emailSentDt;			// null means never sent
		
	@OneToMany(cascade=CascadeType.ALL, mappedBy="invoice", fetch = FetchType.EAGER)
	private List<InvoiceLineItem> items = new ArrayList<InvoiceLineItem>();
	
	@ManyToOne(cascade={CascadeType.MERGE, CascadeType.PERSIST})
	@JoinColumn(name="task_id", nullable=false)
	@JsonIgnore
	private Task task;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientEmail() {
		return clientEmail;
	}

	public void setClientEmail(String clientEmail) {
		this.clientEmail = clientEmail;
	}

	public String getInvoiceFolder() {
		return invoiceFolder;
	}

	public void setInvoiceFolder(String invoiceFolder) {
		this.invoiceFolder = invoiceFolder;
	}

	public String getInvoicePdfFileName() {
		return invoicePdfFileName;
	}

	public void setInvoicePdfFileName(String invoicePdfFileName) {
		this.invoicePdfFileName = invoicePdfFileName;
	}

	public Date getEmailSentDt() {
		return emailSentDt;
	}

	public void setEmailSentDt(Date emailSentDt) {
		this.emailSentDt = emailSentDt;
	}

	public List<InvoiceLineItem> getItems() {
		return items;
	}

	public void setItems(List<InvoiceLineItem> items) {
		this.items = items;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public String getClientNo() {
		return clientNo;
	}

	public void setClientNo(String clientNo) {
		this.clientNo = clientNo;
	}

	public String getClientStatus() {
		return clientStatus;
	}

	public void setClientStatus(String clientStatus) {
		this.clientStatus = clientStatus;
	}
	

}
