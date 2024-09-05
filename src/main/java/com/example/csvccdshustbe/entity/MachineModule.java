package com.example.csvccdshustbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "machine_module")
public class MachineModule implements IModules{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_machine_module")
    private Integer idMachineModule;
    @Column(name = "id_asset")
    private Integer idAsset;
    @Column(name = "label_machine")
    private String labelMachine;
    @Column(name = "model")
    private String model;
    @Column(name = "serial")
    private String serial;
    @Column(name = "publish_date")
    private String publishDate;
    @Column(name = "country")
    private String country;
    @Column(name = "id_user")
    private Integer idUser;
    @Column(name = "id_type_use")
    private Integer idTypeUse;
}
