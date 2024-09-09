package com.example.csvccdshustbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "other_asset_module")
public class OtherAssetModule implements IModules{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_other_asset_module")
    private Integer idOtherAssetModule;
    @Column(name = "id_asset")
    private Integer idAsset;
    @Column(name = "lable")
    private String lable;
    @Column(name = "model")
    private String model;
    @Column(name = "serial")
    private String serial;
    @Column(name = "publish_date")
    private String publishDate;
    @Column(name = "id_country_producer")
    private Integer idCountryProducer;
    @Column(name = "id_user")
    private Integer idUser;
    @Column(name = "id_type_use")
    private Integer idTypeUse;
}
