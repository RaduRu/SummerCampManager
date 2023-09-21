package BusinessLogic;

import DomainModel.Educator;
import DomainModel.Parent;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.ArrayList;
import java.util.Properties;

public class Notifier{
    private static Notifier notifier;

    private final String emailAddress = "summercampswe@gmail.com";
    private final String emailPassword = "Summercamp.";
    private Notifier(){}
    public static synchronized Notifier getInstance(){
        if(notifier == null){
            notifier = new Notifier();
        }
        return notifier;
    }

    public void sendEmailParent(ArrayList<Parent> toBeNotified, String subject, String messageToSend) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");  //autenticazione user
        properties.put("mail.smtp.host", "smtp.gmail.com");  //server smtp gmail
        properties.put("mail.smtp.port", "587"); //numero di porta richiesto da gmail
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailAddress, emailPassword);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(emailAddress));
        message.setSubject(subject);
        for (Parent parent : toBeNotified) {
            Address addressTo = new InternetAddress(parent.getEmail());
            message.addRecipient(Message.RecipientType.TO, addressTo);
        }
        message.setSubject(subject);

        MimeMultipart multipart = new MimeMultipart("related");

        //first part of the message
        BodyPart messageBodyPart = new MimeBodyPart();
        String htmlText = "<img src=\"cid:image\" alt=\"SummerCamp\" style=\"width: 300px; height: 100px; \">\r\n" + //
                "<h5 style=\"color: black; font-family: Arial,sans-serif\">" + messageToSend + "</h5>\r\n"  //
                /* + "<div style=\"margin-top: 5em\">\r\n" + //
                "  <p style=\"color: gray; font-family: Arial,sans-serif; font-size: 0.7em\">Contatti:</p>\r\n" + //
                "  <p style=\"color: gray; font-family: Arial,sans-serif; font-size: 0.7em\">Telefono: 1234567890</p>\r\n" + //
                "  <p style=\"color: gray; font-family: Arial,sans-serif; font-size: 0.7em\">Email: museoswe@gmail.com</p>\r\n" + //
                "</div>"*/ ;
        messageBodyPart.setContent(htmlText, "text/html");
        multipart.addBodyPart(messageBodyPart);

        //second part of the message
        messageBodyPart = new MimeBodyPart();
        FileDataSource fds = new FileDataSource("imgs/LogoSummerCamp.png");
        messageBodyPart.setDataHandler(new DataHandler(fds));
        messageBodyPart.setHeader("Content-ID", "<image>");
        messageBodyPart.setFileName("LogoSummerCamp.png");
        multipart.addBodyPart(messageBodyPart);

        message.setContent(multipart);

        Transport.send(message);
    }
    public void sendEmailEducator(ArrayList<Educator> toBeNotified, String subject, String messageToSend) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");  //autenticazione user
        properties.put("mail.smtp.host", "smtp.gmail.com");  //server smtp gmail
        properties.put("mail.smtp.port", "587"); //numero di porta richiesto da gmail
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailAddress, emailPassword);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(emailAddress));
        message.setSubject(subject);
        String htmlText = "";

        for (Educator educator : toBeNotified) {
            Address addressTo = new InternetAddress(educator.getEmail());
            message.addRecipient(Message.RecipientType.TO, addressTo);
            htmlText = "<img src=\"cid:image\" alt=\"SummerCamp\" style=\"width: 300px; height: 100px; \">\r\n" + //
                    "<h5 style=\"color: black; font-family: Arial,sans-serif\">" + "Hi" + educator.getName()+ ", " + messageToSend + "</h5>\r\n"  //
                /* + "<div style=\"margin-top: 5em\">\r\n" + //
                "  <p style=\"color: gray; font-family: Arial,sans-serif; font-size: 0.7em\">Contatti:</p>\r\n" + //
                "  <p style=\"color: gray; font-family: Arial,sans-serif; font-size: 0.7em\">Telefono: 1234567890</p>\r\n" + //
                "  <p style=\"color: gray; font-family: Arial,sans-serif; font-size: 0.7em\">Email: museoswe@gmail.com</p>\r\n" + //
                "</div>"*/ ;
        }
        message.setSubject(subject);

        MimeMultipart multipart = new MimeMultipart("related");

        //first part of the message
        BodyPart messageBodyPart = new MimeBodyPart();

        messageBodyPart.setContent(htmlText, "text/html");
        multipart.addBodyPart(messageBodyPart);

        //second part of the message
        messageBodyPart = new MimeBodyPart();
        FileDataSource fds = new FileDataSource("imgs/LogoSummerCamp.png");
        messageBodyPart.setDataHandler(new DataHandler(fds));
        messageBodyPart.setHeader("Content-ID", "<image>");
        messageBodyPart.setFileName("LogoSummerCamp.png");
        multipart.addBodyPart(messageBodyPart);

        message.setContent(multipart);

        Transport.send(message);
    }

}
