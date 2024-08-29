package com.example.csvccdshustbe.repository.countryProducer;

import com.example.csvccdshustbe.entity.CountryProducer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryProducerRepository extends JpaRepository<CountryProducer,Integer>, CountryProducerRepositoryCustom {
}
