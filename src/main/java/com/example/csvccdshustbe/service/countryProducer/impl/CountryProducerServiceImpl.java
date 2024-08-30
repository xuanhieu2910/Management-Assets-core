package com.example.csvccdshustbe.service.countryProducer.impl;

import com.example.csvccdshustbe.entity.CountryProducer;
import com.example.csvccdshustbe.repository.countryProducer.CountryProducerRepository;
import com.example.csvccdshustbe.response.countryProducer.FindAllCountryProducerResponse;
import com.example.csvccdshustbe.service.countryProducer.CountryProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountryProducerServiceImpl implements CountryProducerService {
    @Autowired
    CountryProducerRepository countryProducerRepository;
    @Override
    public List<FindAllCountryProducerResponse> findAllCountryProducerResponseByStatus(Integer status){
        return convertToFindAllCountryProducer(countryProducerRepository.findAllCountryProducerByStatus(status));
    }

    private List<FindAllCountryProducerResponse> convertToFindAllCountryProducer(List<CountryProducer> allCountryProducerByStatus) {
        List<FindAllCountryProducerResponse> responses = new ArrayList<>();
        for (CountryProducer countryProducer : allCountryProducerByStatus){
            FindAllCountryProducerResponse response = new FindAllCountryProducerResponse();
            response.setIdCountryProducer(countryProducer.getIdCountryProducer());
            response.setName(countryProducer.getName());
            responses.add(response);
        }
        return responses;
    }
}
