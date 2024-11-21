package com.example.csvccdshustbe.entity;


import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@Data
@Entity
@Table(name = "asset_instance")
public class AssetInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asset_instance")
    private Integer idAssetInstance;
    @Column(name = "id_user")
    private Integer idUser;
    @Column(name = "value")
    private String value;
    @Column(name = "id_department_original")
    private Integer idDepartmentOriginal;
    @Column(name = "time_created")
    private String timeCreated;
    @Column(name = "time_modified")
    private String timeModified;
    @Column(name = "error")
    private String error;
}
