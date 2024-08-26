package com.example.csvccdshustbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "original_of_formation")
public class OriginalOfFormation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_original_of_formation")
    private Integer idOriginalOfFormation;
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

}
