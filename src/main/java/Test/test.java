package Test;


import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;

public class test {
    public static void main(String[] args) throws GeneralSecurityException, MessagingException {
        Properties properties = new Properties();
        properties.put("mail.host","smtp.qq.com");
        properties.put("mail.transport.protocol","smtp");
        properties.put("mail.smtp.auth","true");

        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable","true");
        properties.put("mail.smtp.ssl.socketFactory",sf);

        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("2825575455@qq.com","fmjswyakfbnidgeb");
            }
        });

        session.setDebug(true);

        Transport ts = session.getTransport();

        ts.connect("smtp.qq.com","2825575455@qq.com","fmjswyakfbnidgeb");

        MimeMessage message = new MimeMessage(session);

        /*发件人*/
        message.setFrom(new InternetAddress("2825575455@qq.com"));

        /*收件人*/
        message.setRecipient(Message.RecipientType.TO,new InternetAddress("v485rq83@163.com"));
        message.setSubject("测试");

        message.setContent("不想再测试了","text/html;charset=utf-8");

        ts.sendMessage(message, message.getAllRecipients());

        ts.close();





    }
}
