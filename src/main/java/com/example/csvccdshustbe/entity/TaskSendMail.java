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
public class TaskSendMail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_task_send_mail")
    private Integer idTaskSendMail;
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
    @Column(name = "retry")
    private Integer retry;
    @Column(name = "time_created")
    private String timeCreated;
    @Column(name = "time_modified")
    private String timeModified;
    @Column(name = "status")
    private Integer status;

}
