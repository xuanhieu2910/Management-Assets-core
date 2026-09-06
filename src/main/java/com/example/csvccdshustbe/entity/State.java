package com.example.csvccdshustbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "state")
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_state")
    private Integer idState;
    @Column(name = "id_type_state")
    private Integer idTypeState;
    @Column(name = "id_process")
    private Integer idProcess;
    @Column(name = "status")
    private int status;
    @Column(name = "time_created")
    private String timeCreated;
    @Column(name = "time_modified")
    private String timeModified;
    @Column(name = "code_type_state")
    private String codeTypeState;
    @Column(name = "step")
    private Integer step;
}
