package com.example.csvccdshustbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "context")
public class Context {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_context")
    private Integer idContext;
    @Column(name = "context_level")
    private Integer contextLevel;
    @Column(name = "id_instance")
    private Integer idInstance;
    @Column(name = "path")
    private String path;
    @Column(name = "depth")
    private String depth;
    @Column(name = "locked")
    private Integer locked;
}
