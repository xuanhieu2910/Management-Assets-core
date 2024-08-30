package com.example.csvccdshustbe.request.countryProducer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateCountryProducerRequest {

    @JsonProperty("name")
    private String name;
}
