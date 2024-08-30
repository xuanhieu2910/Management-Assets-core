package com.example.csvccdshustbe.service.countryProducer;

import com.example.csvccdshustbe.entity.CountryProducer;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.countryProducer.CreateCountryProducerRequest;
import com.example.csvccdshustbe.response.countryProducer.FindAllCountryProducerResponse;

import java.util.List;

public interface CountryProducerService {
    List<FindAllCountryProducerResponse> findAllCountryProducerResponseByStatus(Integer status);

    void createCountryProducer(CreateCountryProducerRequest request) throws ValidateFiledException;
}
