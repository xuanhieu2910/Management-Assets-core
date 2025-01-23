package com.example.csvccdshustbe.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "fluctuating_situation_tool")
public class FluctuatingSituationTool {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fluctuating_situation_tool")
    private Integer idFluctuatingSituationTool;
    @Column(name = "id_tool")
    private Integer idTool;
    @Column(name = "id_process")
    private Integer idProcess;
    @Column(name = "status")
    private Integer status;
    @Column(name = "type")
    private Integer type;
    @Column(name = "time_created")
    private String timeCreated;
    @Column(name = "time_modified")
    private String timeModified;
    @Column(name = "id_user_created")
    private Integer idUserCreated;
    @Column(name = "id_user_modified")
    private Integer idUserModified;
    @Column(name = "id_fluctuating_situation")
    private Integer idFluctuatingSituation;
}
