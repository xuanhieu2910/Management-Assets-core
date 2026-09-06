package com.example.csvccdshustbe.request.countryProducer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateCountryProducerRequest {

    @NonNull
    private String name;
    @NonNull
    private Integer status;
}
