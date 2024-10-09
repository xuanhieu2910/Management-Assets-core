package com.example.csvccdshustbe.service.countryProducer;

import com.example.csvccdshustbe.entity.CountryProducer;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.countryProducer.CreateCountryProducerRequest;
import com.example.csvccdshustbe.request.countryProducer.FindAllCountryProducerActiveRequest;
import com.example.csvccdshustbe.request.countryProducer.UpdateCountryProducerRequest;
import com.example.csvccdshustbe.response.countryProducer.FindAllCountryProducerPickedResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CountryProducerService {
    Page<FindAllCountryProducerPickedResponse> findAllCountryProducerActiveResponse(FindAllCountryProducerActiveRequest request);

    void createCountryProducer(CreateCountryProducerRequest request) throws ValidateFiledException;
    void updateCountryProducer(UpdateCountryProducerRequest request) throws ValidateFiledException;
    void deleteCountryProducerByIdCP(Integer idCountryProducer);
    List<CountryProducer> findAllCountryProducerToDownload();
}
