package com.example.csvccdshustbe.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "request_stake_holder")
public class RequestStakeHolder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_request_stake_holder")
    private Integer idRequestStakeHolder;
    @Column(name = "id_request")
    private Integer idRequest;
    @Column(name = "id_user")
    private Integer idUser;
    @Column(name = "status")
    private Integer status;
    @Column(name = "time_created")
    private String timeCreated;
    @Column(name = "time_modified")
    private String timeModified;
    @Column(name = "id_reason")
    private Integer idReason;
    @Column(name = "description")
    private String description;
    @Column(name = "id_department")
    private Integer idDepartment;
    @Column(name = "position")
    private String position;
    @Column(name = "position_instance")
    private String positionInstance;
    @Column(name = "level")
    private Integer level;
}
