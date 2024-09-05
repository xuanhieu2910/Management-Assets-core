package com.example.csvccdshustbe.request.countryProducer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateCountryProducerRequest {
    @NonNull
    private Integer idCountryProducer;
    @NonNull
    private String name;

    private Integer status;
}
