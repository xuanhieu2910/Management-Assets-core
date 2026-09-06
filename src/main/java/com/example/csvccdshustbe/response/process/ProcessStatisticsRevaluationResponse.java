package com.example.csvccdshustbe.response.process;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProcessStatisticsRevaluationResponse {

    @JsonProperty("total_revaluation_pending_approved")
    private Integer totalRevaluationPendingApproved = 0;
    @JsonProperty("total_revaluation_pending_be_approved")
    private Integer totalRevaluationPendingBeApproved = 0;
    @JsonProperty("total_revaluation_rejected")
    private Integer totalRevaluationRejected = 0;

}
