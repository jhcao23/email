package touchmars.technology.email;

import java.io.File;
import java.io.IOException;

import javax.mail.internet.MimeMessage;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jhcao on 2017-05-11.
 */
@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;
    @Autowired
    private ReportService reportService;

    @GetMapping("/report/{filename}")
    public Task createReportTask(@PathVariable String filename){
    	Task task;
		try {
			task = reportService.generateTaskFromExcel(filename);
			return task;
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
    
    @GetMapping("/task/{taskId}")
    public Task getTask(@PathVariable Long taskId){
    	return reportService.getTask(taskId);
    }
    
    @GetMapping("/simple")
    public String createSimpleMail(){
        try {
            emailService.sendSimpleMessage("jhcao23@gmail.com", "test", "test test");
        }catch (Exception e){
            e.printStackTrace();
            return "failed!";
        }
        return "done!";
    }

	@GetMapping("/gmail/{title}")
    public String createSimpleGmail(@PathVariable String title){
        try {
            MimeMessage msg = emailService.createEmail("jhcao23@gmail.com", "john@itouchmars.com", title, "test test test");
            emailService.sendMessage("john@itouchmars.com", msg);
        }catch (Exception e){
            e.printStackTrace();
            return "failed!";
        }
        return "done!";
    }

    ///Users/jhcao/Desktop/1.jpg
	@GetMapping("/gmailfile/{title}")
    public String createSimpleGmailAttachment(@PathVariable String title){
        try {
            File file = new File("/Users/jhcao/Downloads/taliCard.pdf");
            MimeMessage msg = emailService.createEmailWithAttachment("jhcao23@gmail.com", "john@itouchmars.com", title, "test test test", file);
            emailService.sendMessage("john@itouchmars.com", msg);
            return file.getAbsolutePath();
        }catch (Exception e){
            e.printStackTrace();
            return "failed!";
        }
    }
}
