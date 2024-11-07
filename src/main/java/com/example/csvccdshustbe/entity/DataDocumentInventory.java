package com.example.csvccdshustbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "data_document_inventory")
public class DataDocumentInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_data_document_inventory")
    private Integer idDataDocumentInventory;
    @Column(name = "id_asset")
    private Integer idAsset;
    @Column(name = "id_document")
    private Integer idDocument;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "original_value")
    private String originalValue;
    @Column(name = "rest_value")
    private String restValue;
    @Column(name = "status")
    private Integer status;
    @Column(name = "time_created")
    private String timeCreated;
    @Column(name = "time_modified")
    private String timeModified;
}
