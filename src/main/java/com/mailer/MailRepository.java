package com.mailer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MailRepository {

    Connection connection;

    MailRepository(Connection connect){
       connection=connect;
    }

public  List<Message> GetMessage(int messageLimit) throws  SQLException{

       List<Message> letterList=new ArrayList<>();
       String select="select * from only mailer.queue limit "+messageLimit;

           Statement statement = connection.createStatement();
           ResultSet Result = statement.executeQuery(select);

           while (Result.next()) {
               Message message=new Message();
               message.setTo(Result.getString("to"));
               message.setFrom(Result.getString("from"));
               message.setSubject(Result.getString("subject"));
               message.setBody(Result.getString("body"));
               message.setId(Result.getString("id"));
               letterList.add(message);
           }
           statement.close();
           Result.close();

       return letterList;
   }

    public void dataModification(int id) throws SQLException{

                String insert = "insert into mailer.log (select * from only mailer.queue where id=?)";
                String delete = "delete from only mailer.queue where id=?";

                PreparedStatement insertStatement = connection.prepareStatement(insert);
                insertStatement.setInt(1,id);
                insertStatement.executeUpdate();
                insertStatement.close();

                PreparedStatement deleteStatement = connection.prepareStatement(delete);
                deleteStatement.setInt(1,id);
                deleteStatement.executeUpdate();
                deleteStatement.close();

    }

}
