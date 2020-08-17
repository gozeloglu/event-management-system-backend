package eventmanagementinternyte.eventmanagementsystembackend.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService {

    public void sendMail(String to, String subject, String mail) {
        final String username = "noreply.tubitakbilgemyte@gmail.com";
        final String password = "23011998123456789.!";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress("noreply.tubitakbilgemyte@gmail.com"));
        } catch (MessagingException messagingException) {
            messagingException.printStackTrace();
        }
        try {
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(to)
            );
        } catch (MessagingException messagingException) {
            messagingException.printStackTrace();
        }
        try {
            message.setSubject(subject);
        } catch (MessagingException messagingException) {
            messagingException.printStackTrace();
        }
        try {
            message.setText(mail);
        } catch (MessagingException messagingException) {
            messagingException.printStackTrace();
        }

        try {
            Transport.send(message);
        } catch (MessagingException messagingException) {
            messagingException.printStackTrace();
        }

        System.out.println("Done");
    }
}