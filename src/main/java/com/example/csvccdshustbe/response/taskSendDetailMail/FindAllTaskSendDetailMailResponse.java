package com.example.csvccdshustbe.response.taskSendDetailMail;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllTaskSendDetailMailResponse {

    @JsonProperty("id_task_send_detail")
    private Integer idTaskSendDetail;
    @JsonProperty("code_task_send_mail")
    private String codeTaskSendMail;
    @JsonProperty("id_user")
    private Integer idUser;
    @JsonProperty("address_from")
    private String addressFrom;
    @JsonProperty("address_to")
    private String addressTo;
    @JsonProperty("address_cc")
    private String addressCc;
    @JsonProperty("subject")
    private String subject;
    @JsonProperty("status")
    private Integer status;
    @JsonProperty("reason")
    private String reason;
    @JsonProperty("time_created")
    private String timeCreated;
    @JsonProperty("time_modified")
    private String timeModified;
    @JsonProperty("is_opened")
    private Integer isOpened;
    @JsonProperty("open_count")
    private Integer openCount;
    @JsonProperty("time_first_open")
    private String timeFirstOpen;
    @JsonProperty("ip_address_first")
    private String ipAddressFirst;
    @JsonProperty("country_first")
    private String countryFirst;
    @JsonProperty("state_region_first")
    private String stateRegionFirst;
    @JsonProperty("location_first")
    private String locationFirst;
    @JsonProperty("opening_system_first")
    private String openingSystemFirst;
    @JsonProperty("device_first")
    private String deviceFirst;
    @JsonProperty("time_last_open")
    private String timeLastOpen;
    @JsonProperty("ip_address_last")
    private String ipAddressLast;
    @JsonProperty("country_last")
    private String countryLast;
    @JsonProperty("opening_system_last")
    private String openingSystemLast;
    @JsonProperty("device_last")
    private String deviceLast;
    @JsonProperty("state_region_last")
    private String stateRegionLast;
    @JsonProperty("location_last")
    private String locationLast;
}
