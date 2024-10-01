package com.example.csvccdshustbe.dto.modules.carModules;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CarModulesDetailsDto {

    @JsonProperty("id_car_module")
    private Integer idCarModule;
    @JsonProperty("id_asset")
    private Integer idAsset;
    @JsonProperty("is_free_tax")
    private Integer isFreeTax;
    @JsonProperty("value_tax")
    private String valueTax;
    @JsonProperty("license_plate")
    private String licensePlate;
    @JsonProperty("label_car")
    private String labelCar;
    @JsonProperty("type_car")
    private String typeCar;
    @JsonProperty("load_capacity")
    private String loadCapacity;
    @JsonProperty("number_seats")
    private Integer numberSeats;
    @JsonProperty("capacity")
    private String capacity;
    @JsonProperty("cylinder_capacity")
    private String cylinderCapacity;
    @JsonProperty("clutch_number")
    private String clutchNumber;
    @JsonProperty("vehicle_identification_number")
    private String vehicleIdentificationNumber;
    @JsonProperty("machine_number")
    private String machineNumber;
    @JsonProperty("publish_year")
    private String publishYear;
    @JsonProperty("id_country_producer")
    private Integer idCountryProducer;
    @JsonProperty("license_certificate_register")
    private String licenseCertificateRegister;
    @JsonProperty("publish_date_license")
    private String publishDateLicense;
    @JsonProperty("company_register")
    private String companyRegister;
    @JsonProperty("source")
    private String source;
    @JsonProperty("color")
    private String color;
    @JsonProperty("code_user")
    private String codeUser;
    @JsonProperty("id_type_use")
    private Integer idTypeUse;
    @JsonProperty("time_created")
    private String timeCreated;
    @JsonProperty("time_modified")
    private String timeModified;
    @JsonProperty("name_country_producer")
    private String nameCountryProducer;
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("name_type_use")
    private String nameTypeUse;
    @JsonProperty("spare_part_attack")
    private String sparePartsAttack;
    @JsonProperty("id_position_name")
    private Integer idPositionName;
    @JsonProperty("id_position_name_other")
    private Integer idPositionNameOther;
    @JsonProperty("name_position")
    private String namePosition;
    @JsonProperty("name_position_other")
    private String namePositionOther;
}
