package com.example.csvccdshustbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "modules")
public class Modules {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_module")
    private Integer idModule;
    @Column(name = "name")
    private String name;
    @Column(name = "code")
    private String code;
    @Column(name = "time_created")
    private String timeCreated;
    @Column(name = "time_modified")
    private String timeModified;
    @Column(name = "status")
    private Integer status;
    @Column(name = "id_user_created")
    private Integer idUserCreated;
    @Column(name = "id_user_modified")
    private Integer idUserModified;
    @Column(name = "id_asset_category")
    private Integer idAssetCategory;
    @Column(name = "hard_code")
    private String hardCode;
}
