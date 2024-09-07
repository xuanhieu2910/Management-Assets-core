package com.example.csvccdshustbe.repository.countryProducer;

import com.example.csvccdshustbe.entity.CountryProducer;
import com.example.csvccdshustbe.request.countryProducer.FindAllCountryProducerActiveRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;

public interface CountryProducerRepositoryCustom {
    List<CountryProducer> findAllCountryProducerByStatus(Integer status);

    Page<CountryProducer> findAllCountryProducerPickedResponse(FindAllCountryProducerActiveRequest request, Pageable pageable);

    Optional<CountryProducer> findCountryProducerByNameAndStatus(String name, Integer status);
    Optional<CountryProducer> findCountryProducerById(Integer idCountryProducer);
}
