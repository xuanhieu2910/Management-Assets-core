package com.example.csvccdshustbe.controller;

import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.dto.taskSendDetailMail.TaskSendDetailMailDto;
import com.example.csvccdshustbe.request.taskSendDetailMail.UpdateTaskSendDetailMailRequest;
import com.example.csvccdshustbe.service.taskSendDetailMail.TaskSendDetailMailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@Tag(name = "Email Tracking Controller", description = "The Email Tracking APIs. Contains operations like find all, create, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/process")
public class ProcessTestController {

    @Autowired
    TaskSendDetailMailService taskSendDetailMailService;

    @GetMapping("/image.png")
    public ResponseEntity<?> testProcess(HttpServletRequest request){
        try {
            taskSendDetailMailService.updateTrackingTaskSendDetailMail(contructionTaskSendMail(request));
            return ApiResponseDto.createdWithMessage("Test Success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    private UpdateTaskSendDetailMailRequest contructionTaskSendMail(HttpServletRequest request) {
        UpdateTaskSendDetailMailRequest taskSendDetailMailRequest = new UpdateTaskSendDetailMailRequest();
        taskSendDetailMailRequest.setCodeTaskSendMail( request.getParameter("utm_content"));
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = request.getRemoteAddr();
        }
        taskSendDetailMailRequest.setIpAddressRemote(ipAddress);
        taskSendDetailMailRequest.setDevice(getBrowserInfo(request.getHeader("User-Agent")));
        return taskSendDetailMailRequest;
    }

    public String  getBrowserInfo( String Information )
    {
        String browsername = "";
        String browserversion = "";
        String browser = Information;
        if (browser.contains("MSIE"))
        {
            String subsString = browser.substring(browser.indexOf("MSIE"));
            String info[] = (subsString.split(";")[0]).split(" ");
            browsername = info[0];
            browserversion = info[1];
        } else if (browser.contains("Firefox"))
        {

            String subsString = browser.substring(browser.indexOf("Firefox"));
            String info[] = (subsString.split(" ")[0]).split("/");
            browsername = info[0];
            browserversion = info[1];
        } else if (browser.contains("Chrome"))
        {

            String subsString = browser.substring(browser.indexOf("Chrome"));
            String info[] = (subsString.split(" ")[0]).split("/");
            browsername = info[0];
            browserversion = info[1];
        } else if (browser.contains("Opera"))
        {

            String subsString = browser.substring(browser.indexOf("Opera"));
            String info[] = (subsString.split(" ")[0]).split("/");
            browsername = info[0];
            browserversion = info[1];
        } else if (browser.contains("Safari"))
        {

            String subsString = browser.substring(browser.indexOf("Safari"));
            String info[] = (subsString.split(" ")[0]).split("/");
            browsername = info[0];
            browserversion = info[1];
        }
        return browsername + "-" + browserversion;
    }
}
