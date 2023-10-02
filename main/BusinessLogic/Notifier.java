package main.BusinessLogic;

import main.DomainModel.Educator;
import main.DomainModel.Parent;

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
    private final String emailPassword = "qbxbrlmjimttqntw";
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

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailAddress, emailPassword);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(emailAddress));
        message.setSubject(subject);

        MimeMultipart multipart = new MimeMultipart("related");

        for (Parent parent : toBeNotified) {
            Address addressTo = new InternetAddress(parent.getEmail());
            message.setRecipient(Message.RecipientType.TO, addressTo);
            String htmlText = "<img src=\"cid:image\" alt=\"SummerCamp\" style=\"width: 150px; height: 150px; \">\r\n" + //
                    "<h3 style=\"color: black; font-family: Arial,sans-serif\">" + "Hi " + parent.getName()+ ", " + messageToSend + "</h3>\r\n";
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(htmlText, "text/html");
            multipart.addBodyPart(messageBodyPart);

            BodyPart messageBodyPart1 = new MimeBodyPart();
            FileDataSource fds = new FileDataSource("imgs/LogoSummerCamp.png");
            messageBodyPart1.setDataHandler(new DataHandler(fds));
            messageBodyPart1.setHeader("Content-ID", "<image>");
            messageBodyPart1.setFileName("LogoSummerCamp.png");
            multipart.addBodyPart(messageBodyPart1);
            message.setContent(multipart);
            Transport.send(message);
            multipart.removeBodyPart(messageBodyPart);
            multipart.removeBodyPart(messageBodyPart1);
        }
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
        MimeMultipart multipart = new MimeMultipart("related");

        for (Educator educator : toBeNotified) {
            Address addressTo = new InternetAddress(educator.getEmail());
            message.setRecipient(Message.RecipientType.TO, addressTo);
            String htmlText = "<img src=\"cid:image\" alt=\"SummerCamp\" style=\"width: 150px; height: 150px; \">\r\n" + //
                    "<h3 style=\"color: black; font-family: Arial,sans-serif\">" + "Hi " + educator.getName()+ ", " + messageToSend + "</h3>\r\n";
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(htmlText, "text/html");
            multipart.addBodyPart(messageBodyPart);

            BodyPart messageBodyPart1 = new MimeBodyPart();
            FileDataSource fds = new FileDataSource("imgs/LogoSummerCamp.png");
            messageBodyPart1.setDataHandler(new DataHandler(fds));
            messageBodyPart1.setHeader("Content-ID", "<image>");
            messageBodyPart1.setFileName("LogoSummerCamp.png");
            multipart.addBodyPart(messageBodyPart1);
            message.setContent(multipart);
            Transport.send(message);
            multipart.removeBodyPart(messageBodyPart);
            multipart.removeBodyPart(messageBodyPart1);
        }
    }

}
