package com.mailer;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {

    private final static String MAIL_ADRES = "";
    private final static String MAIL_PASSWORD = "";

    private static final Properties MAIL_SERVER_PROPERTIES = new Properties(){{
        put("mail.smtp.starttls.enable", "true");
        put("mail.smtp.auth", "true");
        put("mail.smtp.host", "smtp.gmail.com");
        put("mail.smtp.port", "587");
        put("mail.smtp.ssl.trust", "smtp.gmail.com");
    }};

    Session session;

    public MailSender() {
        session = Session.getInstance(MAIL_SERVER_PROPERTIES,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(MAIL_ADRES, MAIL_PASSWORD);
                    }
                });
    }

    public  void send(Message message) throws MessagingException {
        if (message==null) throw new NullPointerException("Message error");

            MimeMessage sendedMessage = new MimeMessage(session);
        sendedMessage.setFrom(new InternetAddress(message.getFrom()));
        sendedMessage.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(message.getTo()));
        sendedMessage.setSubject(message.getSubject());
        sendedMessage.setText(message.getBody());
            Transport.send(sendedMessage);

    }

}
