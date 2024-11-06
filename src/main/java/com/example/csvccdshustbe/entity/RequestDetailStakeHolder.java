package com.example.csvccdshustbe.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "request_detail_stake_holder")
public class RequestDetailStakeHolder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_request_detail_stake_holder")
    private Integer idRequestDetailStakeHolder;
    @Column(name = "id_request_stake_holder")
    private Integer idRequestStakeHolder;
    @Column(name = "position")
    private String position;
    @Column(name = "position_instance")
    private String positionInstance;
    @Column(name = "level")
    private Integer level;
    @Column(name = "time_created")
    private String timeCreated;
    @Column(name = "time_modified")
    private String timeModified;

}
