package com.example.csvccdshustbe.response.process;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessStatisticsIncreaseResponse {

    @JsonProperty("total_increase_pending_approved")
    private Integer totalIncreasePendingApproved = 0;
    @JsonProperty("total_increase_pending_be_approved")
    private Integer totalIncreasePendingBeApproved = 0;
    @JsonProperty("total_increase_rejected")
    private Integer totalIncreaseRejected = 0;
}
