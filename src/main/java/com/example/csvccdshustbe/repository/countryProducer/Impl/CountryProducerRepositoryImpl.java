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
import java.util.Optional;

public class CountryProducerRepositoryImpl implements CountryProducerRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;
    @Override
    public List<CountryProducer>findAllCountryProducerByStatus(Integer status){
        StringBuilder sb = new StringBuilder();
        sb.append("select country_producer.id_country_producer, " +
                "       country_producer.name, country_producer.status, " +
                "       country_producer.time_created, country_producer.time_modified " +
                "from country_producer " +
                "where 1=1 and country_producer.status = :status ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("status", status);
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

    @Override
    public Optional<CountryProducer> findCountryProducerByNameAndStatus(String name, Integer status) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select countryProducer.id_country_producer, " +
                "       countryProducer.name, countryProducer.status, " +
                "       countryProducer.time_created, countryProducer.time_modified " +
                "from country_producer countryProducer " +
                "where countryProducer.status = :status " +
                "and countryProducer.name = :name ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("status", status);
        query.setParameter("name", name);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj: result){
                CountryProducer countryProducer = new CountryProducer();
                countryProducer.setIdCountryProducer(ValueUtil.getIntegerByObject(obj[0]));
                countryProducer.setName(ValueUtil.getStringByObject(obj[1]));
                countryProducer.setStatus(ValueUtil.getIntegerByObject(obj[2]));
                countryProducer.setTimeCreated(ValueUtil.getStringByObject(obj[3]));
                countryProducer.setTimeModified(ValueUtil.getStringByObject(obj[4]));
                return Optional.of(countryProducer);
            }
        }
        return Optional.empty();
    }
}
