package com.mailer;

import com.mailer.listener.RabbitMQMessageListener;
import javax.mail.MessagingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MailerApplication {

    private static Logger logger = Logger.getLogger("MailerApplication");
    public  static int MESSAGE_LIMIT = 2;

    public static void main(String[] argv) throws Exception {

        MailSender mailSender = new MailSender();
        Connection connection = new BDConnectionManager().getConnection();
        MailRepository mailRepository = new MailRepository(connection);
        MailRepository dataModificator=new MailRepository(connection);
        RabbitMQConnectionManager rabbitConnect=new RabbitMQConnectionManager();

        rabbitConnect.connect(new RabbitMQMessageListener() {
            @Override
            public void onMessageReciever(String qMessage) {
                List<Message> messageList = null;
                try {
                    messageList = mailRepository.GetMessage(MESSAGE_LIMIT);
                    while (messageList.size() != 0) {
                        for (Message message : messageList) {
                            mailSender.send(message);
                            dataModificator.dataModification(message.getId());
                            messageList = mailRepository.GetMessage(MESSAGE_LIMIT);
                        }
                    }
                } catch (SQLException | MessagingException e) {
                    logger.log(Level.WARNING, "Error", e);
                }
            }
        });
    }

}
