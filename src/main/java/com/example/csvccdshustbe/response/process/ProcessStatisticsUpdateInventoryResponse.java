package com.example.csvccdshustbe.response.process;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessStatisticsUpdateInventoryResponse {

    @JsonProperty("total_update_inventory_pending_approved")
    private Integer totalUpdateInventoryPendingApproved = 0;
    @JsonProperty("total_update_inventory_pending_be_approved")
    private Integer totalUpdateInventoryPendingBeApproved = 0;
    @JsonProperty("total_update_inventory_rejected")
    private Integer totalUpdateInventoryRejected = 0;
}
