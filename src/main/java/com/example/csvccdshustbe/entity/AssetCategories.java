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
    @Column(name = "path_image")
    private String pathImage;
    @Column(name = "is_pick")
    private Integer isPick;
    @Column(name = "value_wear_tear")
    private String valueWearTear;
    @Column(name = "year_used_wear_tear")
    private String yearUsedWearTear;
    @Column(name = "minimum_time_depreciation")
    private String minimumTimeDepreciation;
    @Column(name = "maximum_time_depreciation")
    private String maximumTimeDepreciation;
    @Column(name = "id_department_original")
    private Integer idDepartmentOriginal;
}
