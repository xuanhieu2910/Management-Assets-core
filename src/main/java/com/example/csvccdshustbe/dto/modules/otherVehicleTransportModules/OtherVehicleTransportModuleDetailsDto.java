package com.example.csvccdshustbe.dto.modules.otherVehicleTransportModules;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtherVehicleTransportModuleDetailsDto {

    @JsonProperty("id_other_vehicle_transport_module")
    private Integer idOtherVehicleTransportModule;
    @JsonProperty("id_asset")
    private Integer idAsset;
    @JsonProperty("license_plate")
    private String licensePlate;
    @JsonProperty("label")
    private String label;
    @JsonProperty("load_capacity")
    private String loadCapacity;
    @JsonProperty("number_seats")
    private String numberSeats;
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
    @JsonProperty("id_position_name")
    private Integer idPositionName;
    @JsonProperty("name_country_producer")
    private String nameCountryProducer;
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("name_type_use")
    private String nameTypeUse;
    @JsonProperty("position_name")
    private String positionName;
    @JsonProperty("spare_parts_attack")
    private String sparePartsAttack;
}
