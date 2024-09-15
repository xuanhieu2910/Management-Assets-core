package com.example.csvccdshustbe.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "animal_tree_module")
public class AnimalTreeModule implements IModules{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_animal_tree_module")
    private Integer idAnimalTreeModule;
    @Column(name = "id_asset")
    private Integer idAsset;
    @Column(name = "publish_date")
    private String publishDate;
    @Column(name = "id_type_use")
    private Integer idTypeUse;
    @Column(name = "id_country_producer")
    private Integer idCountryProducer;
    @Column(name = "spare_parts_attack")
    private String sparePartsAttack;
}
