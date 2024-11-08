package com.example.csvccdshustbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "task_send_mail")
public class TaskSendDetailMail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tasl_send_detail")
    private Integer idTaskSendDetail;
    @Column(name = "id_task_send_mail")
    private Integer idTaskSendMail;
    @Column(name = "id_user")
    private Integer idUser;
    @Column(name = "address_from")
    private String addressFrom;
    @Column(name = "address_to")
    private String addressTo;
    @Column(name = "address_cc")
    private String addressCc;
    @Column(name = "subject")
    private String subject;
    @Column(name = "content")
    private String content;
    @Column(name = "status")
    private Integer status;
    @Column(name = "reason")
    private String reason;
    @Column(name = "time_created")
    private String timeCreated;
    @Column(name = "time_modified")
    private String timeModified;
    @Column(name = "id_opened")
    private Integer idOpened;
    @Column(name = "open_count")
    private Integer openCount;
    @Column(name = "time_first_open")
    private String timeFirstOpen;
    @Column(name = "ip_address_first")
    private String ipAddressFirst;
    @Column(name = "country_first")
    private String countryFirst;
    @Column(name = "state_region_first")
    private String stateRegionFirst;
    @Column(name = "location_first")
    private String locationFirst;
    @Column(name = "opening_system_first")
    private String openingSystemFirst;
    @Column(name = "device_first")
    private String deviceFirst;
    @Column(name = "time_last_open")
    private String timeLastOpen;
    @Column(name = "ip_address_last")
    private String ipAddressLast;
    @Column(name = "country_last")
    private String countryLast;
    @Column(name = "state_region_last")
    private String stateRegionLast;
    @Column(name = "location_last")
    private String locationLast;
    @Column(name = "opening_system_last")
    private String openingSystemLast;
    @Column(name = "device_last")
    private Integer deviceLast;
    @Column(name = "id_department_original")
    private Integer idDepartmentOriginal;

}
