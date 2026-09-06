package com.example.csvccdshustbe.dto.taskSendDetailMail;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskSendDetailMailDto {

    private Integer idTaskSendDetail;
    private String codeTaskSendMail;
    private Integer idUser;
    private String addressFrom;
    private String addressTo;
    private String addressCc;
    private String subject;
    private String content;
    private Integer status;
    private String reason;
    private String timeCreated;
    private String timeModified;
    private Integer isOpened;
    private Integer openCount;
    private String timeFirstOpen;
    private String ipAddressFirst;
    private String countryFirst;
    private String stateRegionFirst;
    private String locationFirst;
    private String openingSystemFirst;
    private String deviceFirst;
    private String timeLastOpen;
    private String ipAddressLast;
    private String countryLast;
    private String stateRegionLast;
    private String locationLast;
    private String openingSystemLast;
    private String deviceLast;
    private Integer idDepartmentOriginal;

}
