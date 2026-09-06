package com.example.csvccdshustbe.response.suppliers;

import com.example.csvccdshustbe.utility.DateUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllSuppliersResponse {
    @JsonProperty("id_supplier")
    private Integer idSupplier;
    @JsonProperty("name")
    private String name;
    @JsonProperty("phone_number")
    private String phoneNumber;
    @JsonProperty("email")
    private String email;
    @JsonProperty("fax")
    private String fax;
    @JsonProperty("address")
    private String address;
    @JsonProperty("url")
    private String url;
    @JsonProperty("notes")
    private String notes;
    @JsonProperty("status")
    private String status;
    @JsonProperty("time_created")
    private String timeCreated;
    @JsonProperty("time_modified")
    private String timeModified;
    @JsonProperty("user_name_created")
    private String userNameCreated;
    @JsonProperty("full_name_created")
    private String fullNameCreated;
    @JsonProperty("user_name_modified")
    private String userNameModified;
    @JsonProperty("full_name_modified")
    private String fullNameModified;
    @JsonProperty("name_department")
    private String nameDepartment;
    @JsonProperty("id_department")
    private Integer idDepartment;


    public void setTimeCreated(String timeCreated){
         this.timeCreated = DateUtil.formatToPattern(DateUtil.formatDatePattern(timeCreated, DateUtil.DDMMYYYY),DateUtil.DDMMYYYY);
    }

    public void setTimeModified(String timeModified){
        this.timeModified = DateUtil.formatToPattern(DateUtil.formatDatePattern(timeModified, DateUtil.DDMMYYYY),DateUtil.DDMMYYYY);
    }

}
