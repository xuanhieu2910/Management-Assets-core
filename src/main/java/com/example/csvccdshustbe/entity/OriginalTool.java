package com.example.csvccdshustbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "original_tool")
public class OriginalTool {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_original_tool")
    private Integer idOriginalTool;
    @Column(name = "name")
    private String name;
    @Column(name = "short_name")
    private String shortName;
    @Column(name = "description")
    private String description;
    @Column(name = "parent")
    private Integer parent;
    @Column(name = "sort_order")
    private Integer sortOrder;
    @Column(name = "visible")
    private Integer visible;
    @Column(name = "time_created")
    private String timeCreated;
    @Column(name = "time_modified")
    private String timeModified;
    @Column(name = "id_user_created")
    private Integer idUserCreated;
    @Column(name = "id_user_modified")
    private Integer idUserModified;
    @Column(name = "id_tool_category")
    private Integer idToolCategory;
    @Column(name = "code")
    private String code;
}
