package com.example.csvccdshustbe.request.suppliers;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateSuppliersRequest {
    @NonNull
    private Integer idSuppliers;
    @NonNull
    private String name;
    private String phoneNumber;
    private String email;
    private String fax;
    private String address;
    private String url;
    private String notes;
    @NonNull
    private Integer status;
}
