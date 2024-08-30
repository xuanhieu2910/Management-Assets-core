package com.example.csvccdshustbe.response.suppliers;

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
}
