package com.example.csvccdshustbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "data_process_asset")
public class DataProcessAsset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_data_process_asset")
    private Integer idDataProcessAsset;
    @Column(name = "id_asset")
    private Integer idAsset;
    @Column(name = "id_document")
    private Integer idDocument;
    @Column(name = "id_process")
    private Integer idProcess;
    @Column(name = "time_created")
    private String timeCreated;
    @Column(name = "time_modified")
    private String timeModified;
    @Column(name = "status")
    private Integer status;
}
