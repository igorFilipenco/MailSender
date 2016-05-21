package com.mailer.listener;


import javax.mail.MessagingException;
import java.sql.SQLException;

public interface RabbitMQMessageListener {
   public void onMessageReciever(String message);
}
