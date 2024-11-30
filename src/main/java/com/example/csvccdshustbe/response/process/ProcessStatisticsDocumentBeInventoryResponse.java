package com.example.csvccdshustbe.response.process;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessStatisticsDocumentBeInventoryResponse {

    @JsonProperty("total_inventory_be_approved")
    private Integer totalInventoryBeApproved = 0;
}
