package com.example.csvccdshustbe.response.process;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessStatisticsInventoryResponse {

    @JsonProperty("total_inventory_pending_approved")
    private Integer totalInventoryPendingApproved = 0;
    @JsonProperty("total_inventory_pending_be_approved")
    private Integer totalInventoryPendingBeApproved = 0;
    @JsonProperty("total_inventory_rejected")
    private Integer totalInventoryRejected = 0;
}
