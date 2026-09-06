package com.example.csvccdshustbe.response.process;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProcessStatisticsChangeResponse {

    @JsonProperty("total_change_pending_approved")
    private Integer totalChangePendingApproved = 0;
    @JsonProperty("total_change_pending_be_approved")
    private Integer totalChangePendingBeApproved = 0;
    @JsonProperty("total_change_rejected")
    private Integer totalChangeRejected = 0;

}
