package com.example.csvccdshustbe.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "other_vehicle_transport_module")
public class OtherVehicleTransportModule implements IModules{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_other_vehicle_transport_module")
    private Integer idOtherVehicleTransportModule;
    @Column(name = "id_asset")
    private Integer idAsset;
    @Column(name = "license_plate")
    private String licensePlate;
    @Column(name = "label")
    private String label;
    @Column(name = "load_capacity")
    private String loadCapacity;
    @Column(name = "number_seats")
    private String numberSeats;
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
    private Integer idCountryProducer;
    @Column(name = "license_certificate_register")
    private String licenseCertificateRegister;
    @Column(name = "publish_date_license")
    private String publishDateLicense;
    @Column(name = "company_register")
    private String companyRegister;
    @Column(name = "source")
    private String source;
    @Column(name = "color")
    private String color;
    @Column(name = "id_user")
    private Integer idUser;
    @Column(name = "id_type_use")
    private Integer idTypeUse;
    @Column(name = "time_created")
    private String timeCreated;
    @Column(name = "time_modified")
    private String timeModified;
    @Column(name = "id_position_name")
    private Integer idPositionName;
}
