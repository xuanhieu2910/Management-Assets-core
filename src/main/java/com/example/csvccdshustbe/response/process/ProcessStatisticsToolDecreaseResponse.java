package com.example.csvccdshustbe.response.process;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessStatisticsToolDecreaseResponse {

    @JsonProperty("total_decrease_pending_approved")
    private Integer totalDecreasePendingApproved = 0;
    @JsonProperty("total_decrease_pending_be_approved")
    private Integer totalDecreasePendingBeApproved = 0;
    @JsonProperty("total_decrease_rejected")
    private Integer totalDecreaseRejected = 0;

}
