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

    public final static String content_test = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\"><head><!--[if gte mso 9]><xml><o:officedocumentsettings><o:allowpng><o:pixelsperinch>96</o:pixelsperinch></o:officedocumentsettings></xml><![endif]--><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><meta name=\"viewport\" content=\"width=device-width,initial-scale=1\"><meta name=\"x-apple-disable-message-reformatting\"><!--[if !mso]><!--><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><!--<![endif]--><title></title><style type=\"text/css\">@media only screen and (min-width:620px){.u-row{width:600px!important}.u-row .u-col{vertical-align:top}.u-row .u-col-100{width:600px!important}}@media only screen and (max-width:620px){.u-row-container{max-width:100%!important;padding-left:0!important;padding-right:0!important}.u-row{width:100%!important}.u-row .u-col{display:block!important;width:100%!important;min-width:320px!important;max-width:100%!important}.u-row .u-col>div{margin:0 auto}.u-row .u-col img{max-width:100%!important}}body{margin:0;padding:0}table,td,tr{border-collapse:collapse;vertical-align:top}p{margin:0}.ie-container table,.mso-container table{table-layout:fixed}*{line-height:inherit}a[x-apple-data-detectors=true]{color:inherit!important;text-decoration:none!important}table,td{color:#000}#u_body a{color:#00e;text-decoration:underline}@media (max-width:480px){#u_content_image_1 .v-container-padding-padding{padding:45px 10px 10px!important}#u_content_heading_2 .v-font-size{font-size:20px!important}#u_content_text_1 .v-container-padding-padding{padding:10px!important}#u_content_button_1 .v-size-width{width:65%!important}#u_content_button_1 .v-container-padding-padding{padding:10px 10px 40px!important}}</style><!--[if !mso]><!--><link href=\"https://fonts.googleapis.com/css?family=Raleway:400,700&display=swap\" rel=\"stylesheet\" type=\"text/css\"><!--<![endif]--></head><body class=\"clean-body u_body\" style=\"margin:0;padding:0;-webkit-text-size-adjust:100%;background-color:#e7e7e7;color:#000\"><!--[if IE]><div class=\"ie-container\"><![endif]--><!--[if mso]><div class=\"mso-container\"><![endif]--><table id=\"u_body\" style=\"border-collapse:collapse;table-layout:fixed;border-spacing:0;mso-table-lspace:0;mso-table-rspace:0;vertical-align:top;min-width:320px;Margin:0 auto;background-color:#e7e7e7;width:100%\" cellpadding=\"0\" cellspacing=\"0\"><tbody><tr style=\"vertical-align:top\"><td style=\"word-break:break-word;border-collapse:collapse!important;vertical-align:top\"><!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td align=\"center\" style=\"background-color:#e7e7e7\"><![endif]--><div class=\"u-row-container\" style=\"padding:0;background-color:transparent\"><div class=\"u-row\" style=\"margin:0 auto;min-width:320px;max-width:600px;overflow-wrap:break-word;word-wrap:break-word;word-break:break-word;background-color:transparent\"><div style=\"border-collapse:collapse;display:table;width:100%;height:100%;background-color:transparent\"><!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding:0;background-color:transparent\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px\"><tr style=\"background-color:transparent\"><![endif]--><!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"background-color:#fff;width:600px;padding:0;border-top:0 solid transparent;border-left:0 solid transparent;border-right:0 solid transparent;border-bottom:0 solid transparent\" valign=\"top\"><![endif]--><div class=\"u-col u-col-100\" style=\"max-width:320px;min-width:600px;display:table-cell;vertical-align:top\"><div style=\"background-color:#fff;height:100%;width:100%!important\"><!--[if (!mso)&(!IE)]><!--><div style=\"box-sizing:border-box;height:100%;padding:0;border-top:0 solid transparent;border-left:0 solid transparent;border-right:0 solid transparent;border-bottom:0 solid transparent\"><!--<![endif]--><table id=\"u_content_image_1\" style=\"font-family:arial,helvetica,sans-serif\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\"><tbody><tr><td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:80px 10px 10px;font-family:arial,helvetica,sans-serif\" align=\"left\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right:0;padding-left:0\" align=\"center\"><img align=\"center\" border=\"0\" src=\"images/image-1.png\" alt=\"image\" title=\"image\" style=\"outline:0;text-decoration:none;-ms-interpolation-mode:bicubic;clear:both;display:inline-block!important;border:none;height:auto;float:none;width:50%;max-width:290px\" width=\"290\"></td></tr></table></td></tr></tbody></table><table id=\"u_content_heading_2\" style=\"font-family:arial,helvetica,sans-serif\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\"><tbody><tr><td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:20px 10px 10px;font-family:arial,helvetica,sans-serif\" align=\"left\"><!--[if mso]><table width=\"100%\"><tr><td><![endif]--><h1 class=\"v-font-size\" style=\"margin:0;line-height:140%;text-align:center;word-wrap:break-word;font-size:25px;font-weight:400\"><span>A Journey of Success<br>Celebrate Our Milestone with Us!</span></h1><!--[if mso]><![endif]--></td></tr></tbody></table><table style=\"font-family:arial,helvetica,sans-serif\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\"><tbody><tr><td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif\" align=\"left\"><table height=\"0px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"19%\" style=\"border-collapse:collapse;table-layout:fixed;border-spacing:0;mso-table-lspace:0;mso-table-rspace:0;vertical-align:top;border-top:5px solid #0202aa;-ms-text-size-adjust:100%;-webkit-text-size-adjust:100%\"><tbody><tr style=\"vertical-align:top\"><td style=\"word-break:break-word;border-collapse:collapse!important;vertical-align:top;font-size:0;line-height:0;mso-line-height-rule:exactly;-ms-text-size-adjust:100%;-webkit-text-size-adjust:100%\"><span>&#160;</span></td></tr></tbody></table></td></tr></tbody></table><table id=\"u_content_text_1\" style=\"font-family:arial,helvetica,sans-serif\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\"><tbody><tr><td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px 60px;font-family:arial,helvetica,sans-serif\" align=\"left\"><div class=\"v-font-size\" style=\"font-size:14px;color:#616161;line-height:140%;text-align:center;word-wrap:break-word\"><p style=\"line-height:140%\"><span style=\"font-family:Raleway,sans-serif;line-height:19.6px\">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor ut labore et dolore magna aliqua. Quis ipsum suspen disse ultrices gravida. Risus commodo viverra dolor sit.</span></p></div></td></tr></tbody></table><table id=\"u_content_button_1\" style=\"font-family:arial,helvetica,sans-serif\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\"><tbody><tr><td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:10px 10px 60px;font-family:arial,helvetica,sans-serif\" align=\"left\"><!--[if mso]><style>.v-button{background:0 0!important}</style><![endif]--><div align=\"center\"></div></td></tr></tbody></table><!--[if (!mso)&(!IE)]><!--></div><!--<![endif]--></div></div><!--[if (mso)|(IE)]><![endif]--><!--[if (mso)|(IE)]><![endif]--></div></div></div><div class=\"u-row-container\" style=\"padding:0;background-color:transparent\"><div class=\"u-row\" style=\"margin:0 auto;min-width:320px;max-width:600px;overflow-wrap:break-word;word-wrap:break-word;word-break:break-word;background-color:transparent\"><div style=\"border-collapse:collapse;display:table;width:100%;height:100%;background-color:transparent\"><!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding:0;background-color:transparent\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px\"><tr style=\"background-color:transparent\"><![endif]--><!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width:600px;padding:0;border-top:0 solid transparent;border-left:0 solid transparent;border-right:0 solid transparent;border-bottom:0 solid transparent;border-radius:0;-webkit-border-radius:0;-moz-border-radius:0\" valign=\"top\"><![endif]--><div class=\"u-col u-col-100\" style=\"max-width:320px;min-width:600px;display:table-cell;vertical-align:top\"><div style=\"height:100%;width:100%!important;border-radius:0;-webkit-border-radius:0;-moz-border-radius:0\"><!--[if (!mso)&(!IE)]><!--><div style=\"box-sizing:border-box;height:100%;padding:0;border-top:0 solid transparent;border-left:0 solid transparent;border-right:0 solid transparent;border-bottom:0 solid transparent;border-radius:0;-webkit-border-radius:0;-moz-border-radius:0\"><!--<![endif]--><table style=\"font-family:arial,helvetica,sans-serif\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\"><tbody><tr><td class=\"v-container-padding-padding\" style=\"overflow-wrap:break-word;word-break:break-word;padding:20px 0;font-family:arial,helvetica,sans-serif\" align=\"left\"><table height=\"0px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse:collapse;table-layout:fixed;border-spacing:0;mso-table-lspace:0;mso-table-rspace:0;vertical-align:top;border-top:1px solid #bbb;-ms-text-size-adjust:100%;-webkit-text-size-adjust:100%\"><tbody><tr style=\"vertical-align:top\"><td style=\"word-break:break-word;border-collapse:collapse!important;vertical-align:top;font-size:0;line-height:0;mso-line-height-rule:exactly;-ms-text-size-adjust:100%;-webkit-text-size-adjust:100%\"><span>&#160;</span></td></tr></tbody></table></td></tr></tbody></table><!--[if (!mso)&(!IE)]><!--></div><!--<![endif]--></div></div><!--[if (mso)|(IE)]><![endif]--><!--[if (mso)|(IE)]><![endif]--></div></div></div><!--[if (mso)|(IE)]><![endif]--></td></tr></tbody></table><!--[if mso]><![endif]--><!--[if IE]><![endif]--></body><footer><img src=\":CONTENT_URL\" height=\"1px\" width=\"1px\"></footer></html>";
}
