package touchmars.technology.email;
import java.io.File;
import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.google.api.services.gmail.model.Message;

public interface EmailService {
    void sendSimpleMessage(String to,
                           String subject,
                           String text);
    void sendSimpleMessageUsingTemplate(String to,
                                        String subject,
                                        SimpleMailMessage template,
                                        String ...templateArgs);
    void sendMessageWithAttachment(String to,
                                   String subject,
                                   String text,
                                   String pathToAttachment);
    
    //Google Methods
    public MimeMessage createEmail(String to,
            String from,
            String subject,
            String bodyText)
            throws MessagingException;
    public Message createMessageWithEmail(MimeMessage emailContent)
            throws MessagingException, IOException;
    public MimeMessage createEmailWithAttachment(String to,
            String from,
            String subject,
            String bodyText,
            File file)
            throws MessagingException, IOException;
    public Message sendMessage(
            String userId,
            MimeMessage emailContent)
            throws MessagingException, IOException;
}