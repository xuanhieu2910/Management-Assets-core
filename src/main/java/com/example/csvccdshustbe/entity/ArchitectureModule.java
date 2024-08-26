package com.example.csvccdshustbe.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "architecture_module")
public class ArchitectureModule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_architecture_module")
    private Integer idArchitectureModule;
    @Column(name = "id_asset")
    private Integer idAsset;
    @Column(name = "instance_id")
    private Integer instanceId;
    @Column(name = "length")
    private Double length;
    @Column(name = "acreage")
    private Double acreage;
    @Column(name = "volume")
    private Double volume;
    @Column(name = "publish_date")
    private String publishDate;
    @Column(name = "id_country_producer")
    private Integer idCountryProducer;
}
