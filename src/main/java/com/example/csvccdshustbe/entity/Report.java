package com.example.csvccdshustbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "report")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_report")
    private Integer idReport;
    @Column(name = "code")
    private String code;
    @Column(name = "title")
    private String title;
    @Column(name = "path")
    private String path;
    @Column(name = "time_created")
    private String timeCreated;
    @Column(name = "time_modified")
    private String timeModified;
    @Column(name = "id_user_created")
    private Integer idUserCreated;
    @Column(name = "id_user_modified")
    private Integer idUserModified;
    @Column(name = "status")
    private Integer status;
    @Column(name = "type_mime")
    private String typeMime;
    @Column(name = "id_government_circular")
    private Integer idGovernmentCircular;
}
