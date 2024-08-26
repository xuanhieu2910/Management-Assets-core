package com.example.csvccdshustbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "asset_categories")
public class AssetCategories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asset_category")
    private Integer idAssetCategory;
    @Column(name = "name")
    private String name;
    @Column(name = "id_number")
    private Integer idNumber;
    @Column(name = "short_name")
    private String shortName;
    @Column(name = "code_name")
    private String codeName;
    @Column(name = "description")
    private String description;
    @Column(name = "parent")
    private Integer parent;
    @Column(name = "sort_order")
    private String sortOrder;
    @Column(name = "asset_count")
    private Integer assetCount;
    @Column(name = "visible")
    private Integer visible;
    @Column(name = "time_created")
    private String timeCreated;
    @Column(name = "time_modified")
    private String timeModified;
    @Column(name = "depth")
    private Integer depth;
    @Column(name = "path")
    private String path;
    @Column(name = "path_image")
    private String pathImage;


}
