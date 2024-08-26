package com.example.csvccdshustbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "units")
public class Units {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_unit")
    private Integer idUnit;
    @Column(name = "name")
    private String name;
    @Column(name = "time_created")
    private String timeCreated;
    @Column(name = "time_modified")
    private String timeModified;
    @Column(name = "id_asset_category")
    private Integer idAssetCategory;
    @Column(name = "status")
    private Integer status;
}
