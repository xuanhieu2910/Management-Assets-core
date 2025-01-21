package com.example.csvccdshustbe.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tool")
public class Tool {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tool")
    private Integer idTool;
    @Column(name = "name")
    private String name;
    @Column(name = "code_tool")
    private String codeTool;
    @Column(name = "salt")
    private String salt;
    @Column(name = "id_tool_category")
    private Integer idToolCategory;
    @Column(name = "time_created")
    private String timeCreated;
    @Column(name = "time_modified")
    private String timeModified;
    @Column(name = "id_user_created")
    private Integer idUserCreated;
    @Column(name = "id_user_modified")
    private Integer idUserModified;
    @Column(name = "value")
    private String value;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "is_increase")
    private Integer isIncrease;
    @Column(name = "is_decrease")
    private Integer isDecrease;
    @Column(name = "quantity_increase_current")
    private Integer quantityIncreaseCurrent;
    @Column(name = "quantity_decrease_current")
    private Integer quantityDecreaseCurrent;
    @Column(name = "id_process_current")
    private Integer idProcessCurrent;
    @Column(name = "status_process_current")
    private Integer statusProcessCurrent;
    @Column(name = "id_type_process_current")
    private Integer idTypeProcessCurrent;
    @Column(name = "id_department_original")
    private Integer idDepartmentOriginal;
    @Column(name = "status_use")
    private Integer statusUse;
    @Column(name = "parent")
    private Integer parent;
    @Column(name = "id_department")
    private Integer idDepartment;
    @Column(name = "id_location")
    private Integer idLocation;
    @Column(name = "id_user_use")
    private Integer idUserUse;
    @Column(name = "year_use")
    private String yearUse;
    @Column(name = "price")
    private String price;
    @Column(name = "quantity_inventory_current")
    private Integer quantityInventoryCurrent;
}
