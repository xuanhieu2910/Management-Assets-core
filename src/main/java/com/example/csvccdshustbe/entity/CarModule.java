package com.example.csvccdshustbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "car_module")
public class CarModule implements IModules{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_car_module")
    private Integer idCarModule;
    @Column(name = "id_asset")
    private Integer idAsset;
    @Column(name = "is_free_tax")
    private Integer isFreeTax;
    @Column(name = "value_tax")
    private String valueTax;
    @Column(name = "license_plate")
    private String licensePlate;
    @Column(name = "id_label_car")
    private Integer idLabelCar;
    @Column(name = "id_type_car")
    private Integer idTypeCar;
    @Column(name = "load_capacity")
    private String loadCapacity;
    @Column(name = "number_seats")
    private Integer numberSeats;
    @Column(name = "capacity")
    private String capacity;
    @Column(name = "cylinder_capacity")
    private String cylinderCapacity;
    @Column(name = "clutch_number")
    private String clutchNumber;
    @Column(name = "vehicle_identification_number")
    private String vehicleIdentificationNumber;
    @Column(name = "machine_number")
    private String machineNumber;
    @Column(name = "publish_year")
    private String publishYear;
    @Column(name = "id_country_producer")
    private String idCountryProducer;
    @Column(name = "license_certificate_register")
    private String licenseCertificateRegister;
    @Column(name = "publish_date_license")
    private String publishDateLicense;
    @Column(name = "company_register")
    private String companyRegister;
    @Column(name = "sourse")
    private String source;
    @Column(name = "color")
    private String color;
    @Column(name = "id_user")
    private Integer idUser;
    @Column(name = "id_use_type")
    private Integer idUseType;
    @Column(name = "time_created")
    private String timeCreated;
    @Column(name = "time_modified")
    private String timeModified;
}
