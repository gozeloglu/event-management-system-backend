package eventmanagementinternyte.eventmanagementsystembackend.mail;


import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class EmailService {

    /**
     * This method send the mail to participant after registration of the meetup
     *
     * @param to      is the receiver's mail address
     * @param subject is the subject of the mail
     * @param mail    is the body of the mail
     * @throws MessagingException
     */
    public void sendMail(String to, String subject, String mail) throws MessagingException {
        final String username = "noreply.tubitakbilgemyte@gmail.com";
        final String password = "23011998123456789.!";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new Authenticator() {
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

        BodyPart messageBodyPart1 = new MimeBodyPart();
        try {
            messageBodyPart1.setText(mail);
        } catch (MessagingException messagingException) {
            messagingException.printStackTrace();
        }

        MimeBodyPart messageBodyPart2 = new MimeBodyPart();
        String filename = "C:\\Users\\gozel\\OneDrive\\Desktop\\qr.png";
        DataSource source = new FileDataSource(filename);
        messageBodyPart2.setDataHandler(new DataHandler(source));
        messageBodyPart2.setFileName("QR_Code.png");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart1);
        multipart.addBodyPart(messageBodyPart2);

        message.setContent(multipart);
        try {
            Transport.send(message);
        } catch (MessagingException messagingException) {
            messagingException.printStackTrace();
        }

        System.out.println("Done");
    }
}