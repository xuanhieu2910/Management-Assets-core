package com.example.csvccdshustbe.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "fluctuating_situation")
public class FluctuatingSituation {


    @Id
    @Column(name = "id_fluctuating_situation")
    private Integer idFluctuatingSituation;
    @Column(name = "id_process")
    private Integer idProcess;
    @Column(name = "status")
    private Integer status;
    @Column(name = "time_created")
    private String timeCreated;
    @Column(name = "time_modified")
    private String timeModified;
    @Column(name = "id_user_modified")
    private Integer idUserModified;
}
