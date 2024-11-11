package com.example.csvccdshustbe.utility;

import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Properties;
import java.util.Queue;


public class EmailUtil implements Runnable {

    private static Logger log = LoggerFactory.getLogger(EmailUtil.class);

    private static EmailUtil INSTANCE = null;

    private static SmtpAuthenticator smtpAuthenticator;
    private Queue<MailDto> mailDtoQueue;

    public final static String[] SUBJECTS_PROCESS = {"Kiểm tra/Đánh giá ghi tăng tài sản", "Kiểm tra/Đánh giá Giảm tài sản",
            "Kiểm tra/Đánh giá điều chuyển tài sản", "Kiểm tra/Đánh giá lại tài sản", "Kiểm tra/Đánh giá", "Kiểm tra/Đánh giá kiểm kê tài sản"};



    public static EmailUtil getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EmailUtil();
            new Thread(INSTANCE).start();
        }
        return INSTANCE;
    }

    public EmailUtil() {
        String email = PropertiesUtil.getEmailProperty("mail.user");
        String password = PropertiesUtil.getEmailProperty("mail.password");
        smtpAuthenticator = new SmtpAuthenticator(email, password);
        mailDtoQueue = new LinkedList<>();
    }



    private static boolean send(MailDto mailDto) {
        try {
            Properties emailProps = new Properties();
            emailProps.load(PropertiesUtil.class.getResourceAsStream("/email.properties"));
            // Get the default Session object.
            Session session = Session.getDefaultInstance(emailProps, smtpAuthenticator);
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            // Set From: header field of the header.
            message.setFrom(new InternetAddress(PropertiesUtil.getEmailProperty("mail.user")));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(mailDto.getAddressTo()));
            // Set Subject: header field
            message.setSubject(mailDto.getSubject(), "UTF-8");

            // Send the actual HTML message, as big as you like
            message.setHeader("Content-Type", "text/plain; charset=UTF-8");
//            message.setHeader("Content-Type", "text/html; charset=UTF-8");
            message.setContent(mailDto.getContent(), "text/html; charset=UTF-8");
            // Send message
            Transport.send(message);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);

                MailDto mailDto = mailDtoQueue.poll();
                if (mailDto != null) {
                    String rs = send(mailDto) ? "success" : "fail";
                    log.info("Send mail is " + rs + " (" + mailDto + ")");
                }
            } catch (Exception e) {
                log.error("Lỗi", e);
            }
        }
    }
}
