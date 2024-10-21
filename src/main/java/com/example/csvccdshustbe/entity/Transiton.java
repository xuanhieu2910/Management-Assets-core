package com.example.csvccdshustbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "transition")
public class Transiton {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transition")
    private Integer idTransition;
    @Column(name = "id_process")
    private Integer idProcess;
    @Column(name = "id_state_current")
    private Integer idStateCurrent;
    @Column(name = "id_state_next")
    private Integer idStateNext;

}
