package com.example.csvccdshustbe.dto.modules.carModules;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class CarModulesDetailsDto {

    private Integer idCarModule;
    private Integer idAsset;
    private Integer isFreeTax;
    private String valueTax;
    private String licensePlate;
    private String labelCar;
    private String typeCar;
    private String loadCapacity;
    private Integer numberSeats;
    private String capacity;
    private String cylinderCapacity;
    private String clutchNumber;
    private String vehicleIdentificationNumber;
    private String machineNumber;
    private String publishYear;
    private Integer idCountryProducer;
    private String licenseCertificateRegister;
    private String publishDateLicense;
    private String companyRegister;
    private String source;
    private String color;
    private Integer idUser;
    private Integer idTypeUse;
    private String timeCreated;
    private String timeModified;
}
