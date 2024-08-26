package com.example.csvccdshustbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "other_declare")
public class OtherDeclare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_other_declare")
    private Integer idOtherDeclare;
    @Column(name = "id_asset")
    private Integer idAsset;
    @Column(name = "specification")
    private String specification;
    @Column(name = "id_current_usage")
    private Integer idCurrentUsage;
    @Column(name = "time_created")
    private String timeCreated;
    @Column(name = "time_modified")
    private String timeModified;
}
