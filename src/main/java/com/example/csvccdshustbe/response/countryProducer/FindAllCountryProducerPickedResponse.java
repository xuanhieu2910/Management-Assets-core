package com.example.csvccdshustbe.response.countryProducer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllCountryProducerPickedResponse {


    @JsonProperty("id_country_producer")
    private Integer idCountryProducer;
    @JsonProperty("name")
    private String name;
}
