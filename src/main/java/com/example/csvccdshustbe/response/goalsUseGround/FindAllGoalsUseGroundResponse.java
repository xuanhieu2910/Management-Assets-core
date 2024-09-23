package com.example.csvccdshustbe.response.goalsUseGround;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllGoalsUseGroundResponse {
    @JsonProperty("id_goals_use_ground")
    private Integer idGoalsUseGround;
    @JsonProperty("name")
    private String name;
    @JsonProperty("code")
    private String code;
}
