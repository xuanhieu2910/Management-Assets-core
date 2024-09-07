package com.example.csvccdshustbe.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "common_declare_current_usage")
public class CommonDeclareCurrentUsage {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_common_declare_current_usage")
    private Integer idCommonDeclareCurrentUsage;
    @Column(name = "id_common_declare")
    private Integer idCommonDeclare;
    @Column(name = "id_current_usage")
    private Integer idCurrentUsage;
    @Column(name = "time_created")
    private String timeCreated;
}
