package com.example.csvccdshustbe.service.countryProducer.impl;

import com.example.csvccdshustbe.entity.CountryProducer;
import com.example.csvccdshustbe.repository.countryProducer.CountryProducerRepository;
import com.example.csvccdshustbe.service.countryProducer.CountryProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryProducerServiceImpl implements CountryProducerService {
    @Autowired
    CountryProducerRepository countryProducerRepository;
    @Override
    public List<CountryProducer> findAllCountryProducer(){
        return countryProducerRepository.findAllCountryProducer();
    }
}
