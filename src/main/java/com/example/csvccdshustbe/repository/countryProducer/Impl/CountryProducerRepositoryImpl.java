package com.example.csvccdshustbe.repository.countryProducer.Impl;

import com.example.csvccdshustbe.entity.CountryProducer;
import com.example.csvccdshustbe.repository.countryProducer.CountryProducerRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class CountryProducerRepositoryImpl implements CountryProducerRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;
    @Override
    public List<CountryProducer>findAllCountryProducer(){
        StringBuilder sb = new StringBuilder();
        sb.append("select country_producer.id_country_producer, " +
                "country_producer.name, country_producer.status, " +
                " country_producer.time_created, country_producer.time_modified " +
                "from country_producer ");
        Query query = entityManager.createNativeQuery(sb.toString());
        List<Object[]> result = query.getResultList();
        List<CountryProducer>countryProducers= new ArrayList<>();
        if(!CollectionUtils.isEmpty(result)) {
            for(Object[] obj :result) {
                CountryProducer countryProducer=new CountryProducer();
                countryProducer.setIdCountryProducer(ValueUtil.getIntegerByObject(obj[0]));
                countryProducer.setName(ValueUtil.getStringByObject(obj[1]));
                countryProducer.setStatus(ValueUtil.getIntegerByObject(obj[2]));
                countryProducer.setTimeCreated(ValueUtil.getStringByObject(obj[3]));
                countryProducer.setTimeModified(ValueUtil.getStringByObject(obj[4]));
                countryProducers.add(countryProducer);
            }
        }
        return countryProducers;
    }
}
