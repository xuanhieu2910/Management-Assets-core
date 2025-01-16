package com.example.csvccdshustbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tool_process")
public class ToolProcess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tool_process")
    private Integer idToolProcess;
    @Column(name = "id_tool")
    private Integer idTool;
    @Column(name = "id_process")
    private Integer idProcess;
    @Column(name = "id_type_process")
    private Integer idTypeProcess;
    @Column(name = "status")
    private Integer status;
    @Column(name = "value")
    private String value;
    @Column(name = "time_created")
    private String timeCreated;
    @Column(name = "time_modified")
    private String timeModified;
    @Column(name = "id_user_created")
    private Integer idUserCreated;
    @Column(name = "id_user_modified")
    private Integer idUserModified;
    @Column(name = "quantity")
    private Integer quantity;
}
