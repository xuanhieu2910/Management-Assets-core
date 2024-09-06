package com.example.csvccdshustbe.repository.countryProducer;

import com.example.csvccdshustbe.entity.CountryProducer;



import java.util.List;
import java.util.Optional;

public interface CountryProducerRepositoryCustom {
    List<CountryProducer> findAllCountryProducerByStatus(Integer status);

    Optional<CountryProducer> findCountryProducerByNameAndStatus(String name, Integer status);
    Optional<CountryProducer> findCountryProducerById(Integer idCountryProducer);
}
