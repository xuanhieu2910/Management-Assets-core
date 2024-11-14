package com.example.csvccdshustbe.utility;

import com.example.csvccdshustbe.config.WebSecurityConfig;
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
    public final static String KEYWORD_REPLACE = ":CONTENT_URL";
    public final static String KEYWORD_CODE_TASK_SEND_MAIL = ":CODE_TASK_SEND_MAIL";
    public final static String KEY_TYPE_PROCESS = ":TYPE_PROCESS";
    public final static String KEY_CODE_DOCUMENT = ":CODE_DOCUMENT";
    public final static String KEY_FULL_NAME = ":FULL_NAME";
    public final static String KEY_DESCRIPTION = ":DESCRIPTION";
    public final static String CONTENT_DOMAIN = WebSecurityConfig.DOMAIN_BE +
            "/api/v1/process/image.png?utm_source=email&utm_medium=newsletter&utm_content=:CODE_TASK_SEND_MAIL ";

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

    public final static String CONTENT_DOCUMENT = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><meta name=\"viewport\" content=\"width=device-width,initial-scale=1\"><meta name=\"x-apple-disable-message-reformatting\"><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><title></title><style type=\"text/css\">@media only screen and (min-width:620px){.u-row{width:600px!important}.u-row .u-col{vertical-align:top}.u-row .u-col-33p33{width:199.98px!important}.u-row .u-col-66p67{width:400.02px!important}.u-row .u-col-100{width:600px!important}}@media only screen and (max-width:620px){.u-row-container{max-width:100%!important;padding-left:0!important;padding-right:0!important}.u-row{width:100%!important}.u-row .u-col{display:block!important;width:100%!important;min-width:320px!important;max-width:100%!important}.u-row .u-col>div{margin:0 auto}.u-row .u-col img{max-width:100%!important}}body{margin:0;padding:0}table,td,tr{border-collapse:collapse;vertical-align:top}p{margin:0}.ie-container table,.mso-container table{table-layout:fixed}*{line-height:inherit}a[x-apple-data-detectors=true]{color:inherit!important;text-decoration:none!important}table,td{color:#000}#u_body a{color:#00e;text-decoration:underline}</style><link href=\"https://fonts.googleapis.com/css?family=Cabin:400,700\" rel=\"stylesheet\" type=\"text/css\"></head><body class=\"clean-body u_body\" style=\"margin:0;padding:0;-webkit-text-size-adjust:100%;background-color:#f9f9f9;color:#000\"><table id=\"u_body\" style=\"border-collapse:collapse;table-layout:fixed;border-spacing:0;mso-table-lspace:0;mso-table-rspace:0;vertical-align:top;min-width:320px;Margin:0 auto;background-color:#f9f9f9;width:100%\" cellpadding=\"0\" cellspacing=\"0\"><tbody><tr style=\"vertical-align:top\"><td style=\"word-break:break-word;border-collapse:collapse!important;vertical-align:top\"><div class=\"u-row-container\" style=\"padding:0;background-color:transparent\"><div class=\"u-row\" style=\"margin:0 auto;min-width:320px;max-width:600px;overflow-wrap:break-word;word-wrap:break-word;word-break:break-word;background-color:#c0102f\"><div style=\"border-collapse:collapse;display:table;width:100%;height:100%;background-color:transparent\"><div class=\"u-col u-col-100\" style=\"max-width:320px;min-width:600px;display:table-cell;vertical-align:top\"><div style=\"height:100%;width:100%!important\"><div style=\"box-sizing:border-box;height:100%;padding:0;border-top:0 solid transparent;border-left:0 solid transparent;border-right:0 solid transparent;border-bottom:0 solid transparent\"><table style=\"font-family:Cabin,sans-serif\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\"><tbody><tr><td style=\"overflow-wrap:break-word;word-break:break-word;padding:14px;font-family:Cabin,sans-serif\" align=\"left\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right:0;padding-left:0\" align=\"center\"><img align=\"center\" border=\"0\" src=\"https://assets.unlayer.com/projects/256863/1731553004481-logo%20bk_trắng.png\" alt=\"Image\" title=\"Image\" style=\"outline:0;text-decoration:none;-ms-interpolation-mode:bicubic;clear:both;display:inline-block!important;border:none;height:auto;float:none;width:45%;max-width:257.4px\" width=\"257.4\"></td></tr></table></td></tr></tbody></table></div></div></div></div></div></div><div class=\"u-row-container\" style=\"padding:0;background-color:transparent\"><div class=\"u-row\" style=\"margin:0 auto;min-width:320px;max-width:600px;overflow-wrap:break-word;word-wrap:break-word;word-break:break-word;background-color:transparent\"><div style=\"border-collapse:collapse;display:table;width:100%;height:100%;background-color:transparent\"><div class=\"u-col u-col-100\" style=\"max-width:320px;min-width:600px;display:table-cell;vertical-align:top\"><div style=\"background-color:#fff;height:100%;width:100%!important\"><div style=\"box-sizing:border-box;height:100%;padding:0;border-top:0 solid transparent;border-left:0 solid transparent;border-right:0 solid transparent;border-bottom:0 solid transparent\"><table style=\"font-family:Cabin,sans-serif\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\"><tbody><tr><td style=\"overflow-wrap:break-word;word-break:break-word;padding:32px 10px;font-family:Cabin,sans-serif\" align=\"left\"><div style=\"font-size:14px;line-height:150%;text-align:center;word-wrap:break-word\"><p style=\"font-size:14px;line-height:150%;text-align:center\"><span style=\"color:#039;line-height:24px;font-size:16px\"><span style=\"color:#000;line-height:21px\"><span style=\"line-height:21px\">Hệ thống Quản lý Cơ sở vật chất - Đại học Bách khoa Hà Nội</span></span></span></p><p style=\"font-size:14px;line-height:150%;text-align:center\"><span style=\"color:#039;line-height:24px;font-size:16px\"><span style=\"color:#000;line-height:21px\"><span style=\"line-height:21px\">xin trân trọng thông báo:</span></span></span></p><p style=\"font-size:14px;line-height:150%;text-align:center\">&nbsp;</p><p style=\"font-size:14px;line-height:150%;text-align:center\"><span style=\"color:#c0102f;line-height:21px\"><strong style=\"font-size:24px\">Thầy/Cô có chứng từ mới cần xử lý!</strong></span></p></div></td></tr></tbody></table></div></div></div></div></div></div><div class=\"u-row-container\" style=\"padding:0;background-color:transparent\"><div class=\"u-row\" style=\"margin:0 auto;min-width:320px;max-width:600px;overflow-wrap:break-word;word-wrap:break-word;word-break:break-word;background-color:transparent\"><div style=\"border-collapse:collapse;display:table;width:100%;height:100%;background-color:transparent\"><div class=\"u-col u-col-100\" style=\"max-width:320px;min-width:600px;display:table-cell;vertical-align:top\"><div style=\"background-color:#fff;height:100%;width:100%!important;border-radius:0;-webkit-border-radius:0;-moz-border-radius:0\"><div style=\"box-sizing:border-box;height:100%;padding:0;border-top:0 solid transparent;border-left:0 solid transparent;border-right:0 solid transparent;border-bottom:0 solid transparent;border-radius:0;-webkit-border-radius:0;-moz-border-radius:0\"><table style=\"font-family:Cabin,sans-serif\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\"><tbody><tr><td style=\"overflow-wrap:break-word;word-break:break-word;padding:0;font-family:Cabin,sans-serif\" align=\"left\"><table height=\"0px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"91%\" style=\"border-collapse:collapse;table-layout:fixed;border-spacing:0;mso-table-lspace:0;mso-table-rspace:0;vertical-align:top;border-top:1px solid #bbb;-ms-text-size-adjust:100%;-webkit-text-size-adjust:100%\"><tbody><tr style=\"vertical-align:top\"><td style=\"word-break:break-word;border-collapse:collapse!important;vertical-align:top;font-size:0;line-height:0;mso-line-height-rule:exactly;-ms-text-size-adjust:100%;-webkit-text-size-adjust:100%\"><span>&#160;</span></td></tr></tbody></table></td></tr></tbody></table></div></div></div></div></div></div><div class=\"u-row-container\" style=\"padding:0;background-color:transparent\"><div class=\"u-row\" style=\"margin:0 auto;min-width:320px;max-width:600px;overflow-wrap:break-word;word-wrap:break-word;word-break:break-word;background-color:#fff\"><div style=\"border-collapse:collapse;display:table;width:100%;height:100%;background-color:transparent\"><div class=\"u-col u-col-100\" style=\"max-width:320px;min-width:600px;display:table-cell;vertical-align:top\"><div style=\"background-color:#ebf6ff;height:100%;width:100%!important\"><div style=\"box-sizing:border-box;height:100%;padding:0;border-top:36px solid #fff;border-left:36px solid #fff;border-right:36px solid #fff;border-bottom:36px solid #fff\"><table style=\"font-family:Cabin,sans-serif\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\"><tbody><tr><td style=\"overflow-wrap:break-word;word-break:break-word;padding:20px 32px;font-family:Cabin,sans-serif\" align=\"left\"><div style=\"font-size:14px;line-height:200%;text-align:center;word-wrap:break-word\"><p style=\"line-height:200%;text-align:left\"><span style=\"color:#c0102f;line-height:32px;font-size:16px\"><strong><span style=\"line-height:28px\">Thông tin chứng từ:</span></strong></span></p><p style=\"line-height:200%;text-align:left\"><span style=\"font-size:14px;line-height:28px\"><span style=\"line-height:28px\">Loại chứng từ:</span><strong><span style=\"line-height:28px\">:TYPE_PROCESS</span></strong></span></p><p style=\"line-height:200%;text-align:left\"><span style=\"font-size:14px;line-height:28px\"><span style=\"line-height:28px\">Mã chứng từ:</span><strong><span style=\"line-height:28px\">:CODE_DOCUMENT</span></strong></span></p><p style=\"line-height:200%;text-align:left\"><span style=\"font-size:14px;line-height:28px\">Người lập chứng từ:<strong>:FULL_NAME</strong></span></p><p style=\"line-height:200%;text-align:left\"><span style=\"font-size:14px;line-height:28px\">Nội dung chứng từ:<strong>:DESCRIPTION</strong></span></p></div></td></tr></tbody></table></div></div></div></div></div></div><div class=\"u-row-container\" style=\"padding:0;background-color:transparent\"><div class=\"u-row\" style=\"margin:0 auto;min-width:320px;max-width:600px;overflow-wrap:break-word;word-wrap:break-word;word-break:break-word;background-color:transparent\"><div style=\"border-collapse:collapse;display:table;width:100%;height:100%;background-color:transparent\"><div class=\"u-col u-col-66p67\" style=\"max-width:320px;min-width:400px;display:table-cell;vertical-align:top\"><div style=\"height:100%;width:100%!important;border-radius:0;-webkit-border-radius:0;-moz-border-radius:0\"><div style=\"box-sizing:border-box;height:100%;padding:1px;border-top:0 solid transparent;border-left:0 solid transparent;border-right:0 solid transparent;border-bottom:0 solid transparent;border-radius:0;-webkit-border-radius:0;-moz-border-radius:0\"><table style=\"font-family:Cabin,sans-serif\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\"><tbody><tr><td style=\"overflow-wrap:break-word;word-break:break-word;padding:26px 26px 26px 36px;font-family:Cabin,sans-serif\" align=\"left\"><div style=\"font-size:14px;line-height:140%;text-align:left;word-wrap:break-word\"><p style=\"line-height:140%\">Để xem thêm thông tin chi tiết và xử lý chứng từ, Thầy/Cô vui lòng truy cập:</p></div></td></tr></tbody></table></div></div></div><div class=\"u-col u-col-33p33\" style=\"max-width:320px;min-width:200px;display:table-cell;vertical-align:top\"><div style=\"height:100%;width:100%!important;border-radius:0;-webkit-border-radius:0;-moz-border-radius:0\"><div style=\"box-sizing:border-box;height:100%;padding:0;border-top:0 solid transparent;border-left:0 solid transparent;border-right:0 solid transparent;border-bottom:0 solid transparent;border-radius:0;-webkit-border-radius:0;-moz-border-radius:0\"><table style=\"font-family:Cabin,sans-serif\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\"><tbody><tr><td style=\"overflow-wrap:break-word;word-break:break-word;padding:28px 36px 28px 30px;font-family:Cabin,sans-serif\" align=\"left\"><div align=\"right\"><a href=\"https://csvc-development.hust.edu.vn/\" target=\"_blank\" class=\"v-button\" style=\"box-sizing:border-box;display:inline-block;text-decoration:none;-webkit-text-size-adjust:none;text-align:center;color:#fff;background-color:#c0102f;border-radius:4px;-webkit-border-radius:4px;-moz-border-radius:4px;width:auto;max-width:100%;overflow-wrap:break-word;word-break:break-word;word-wrap:break-word;mso-border-alt:none;font-size:14px\"><span style=\"display:block;padding:10px 20px;line-height:120%\"><strong><span style=\"line-height:16.8px\">Truy cập</span></strong></span></a></div></td></tr></tbody></table></div></div></div></div></div></div><div class=\"u-row-container\" style=\"padding:0;background-color:transparent\"><div class=\"u-row\" style=\"margin:0 auto;min-width:320px;max-width:600px;overflow-wrap:break-word;word-wrap:break-word;word-break:break-word;background-color:#e5eaf5\"><div style=\"border-collapse:collapse;display:table;width:100%;height:100%;background-color:transparent\"><div class=\"u-col u-col-100\" style=\"max-width:320px;min-width:600px;display:table-cell;vertical-align:top\"><div style=\"background-color:#212d3e;height:100%;width:100%!important\"><div style=\"box-sizing:border-box;height:100%;padding:5px;border-top:0 solid transparent;border-left:0 solid transparent;border-right:0 solid transparent;border-bottom:0 solid transparent\"><table style=\"font-family:Cabin,sans-serif\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\"><tbody><tr><td style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:Cabin,sans-serif\" align=\"left\"><div style=\"font-size:12px;color:#fff;line-height:140%;text-align:center;word-wrap:break-word\"><p style=\"line-height:140%\"><span style=\"color:#fff;line-height:16.8px\"><span style=\"line-height:16.8px\"><span style=\"line-height:16.8px\">Hệ thống Quản lý Cơ sở vật chất - Đại học Bách khoa Hà Nội</span></span></span></p></div></td></tr></tbody></table></div></div></div></div></div></div></td></tr></tbody></table></body><footer><img src=\":CONTENT_URL\" height=\"1px\" width=\"1px\"></footer></html>";
}
