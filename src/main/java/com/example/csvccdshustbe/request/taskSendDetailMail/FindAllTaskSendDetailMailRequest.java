package com.example.csvccdshustbe.request.taskSendDetailMail;

import com.example.csvccdshustbe.request.RequestPageBase;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindAllTaskSendDetailMailRequest extends RequestPageBase {

    private String subject;
    private Integer status;
    private Integer isOpened;


}
