package com.example.csvccdshustbe.repository.countryProducer.Impl;

import com.example.csvccdshustbe.entity.CountryProducer;
import com.example.csvccdshustbe.repository.countryProducer.CountryProducerRepositoryCustom;
import com.example.csvccdshustbe.request.countryProducer.FindAllCountryProducerActiveRequest;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.PageUtils;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public Page<CountryProducer> findAllCountryProducerPickedResponse(FindAllCountryProducerActiveRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append("select country_producer.id_country_producer, " +
                "       country_producer.name, country_producer.status, " +
                "       country_producer.time_created, country_producer.time_modified " +
                "from country_producer " +
                "where 1=1 and country_producer.status = :status ");
        setConditionFindAllCountryProducer(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllCountryProducer(request, query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<CountryProducer>countryProducers = new ArrayList<>();
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
        return new PageImpl<>(countryProducers, pageable, countFindAllCountryProducerPickedResponse(request));
    }

    private long countFindAllCountryProducerPickedResponse(FindAllCountryProducerActiveRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0) " +
                "from country_producer " +
                "where 1 = 1 " +
                "  and country_producer.status = :status  ");
        setConditionFindAllCountryProducer(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllCountryProducer(request, query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }


    private void setParameterFindAllCountryProducer(FindAllCountryProducerActiveRequest request, Query query) {
        query.setParameter("status", Constants.COUNTRY_PRODUCER_ACTIVE_STATUS);
        if (StringUtils.isNotBlank(request.getKeyword())) {
            query.setParameter("keyword", request.getKeyword());
        }
    }

    private void setConditionFindAllCountryProducer(FindAllCountryProducerActiveRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getKeyword())) {
            sb.append(" and (country_producer.name REGEXP  :keyword )  ");
        }
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

    @Override
    public Optional<CountryProducer> findCountryProducerById( Integer idCountryProducer) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select countryProducer.id_country_producer, " +
                "countryProducer.name, countryProducer.status, " +
                "countryProducer.time_created, countryProducer.time_modified " +
                "from country_producer countryProducer " +
                "where countryProducer.id_country_producer = :idCountryProducer ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idCountryProducer", idCountryProducer);
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

    @Override
    public List<CountryProducer> findAllCountryProducerToDownload() {
        StringBuilder sb = new StringBuilder();
        sb.append(" select id_country_producer, name,  " +
                "       status, time_created, time_modified " +
                "from country_producer " +
                "where status = :status ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("status", Constants.COUNTRY_PRODUCER_ACTIVE_STATUS);
        List<Object[]> result = query.getResultList();
        List<CountryProducer> responses = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                CountryProducer countryProducer = new CountryProducer();
                countryProducer.setIdCountryProducer(ValueUtil.getIntegerByObject(obj[0]));
                countryProducer.setName(ValueUtil.getStringByObject(obj[1]));
                countryProducer.setStatus(ValueUtil.getIntegerByObject(obj[2]));
                countryProducer.setTimeCreated(ValueUtil.getStringByObject(obj[3]));
                countryProducer.setTimeModified(ValueUtil.getStringByObject(obj[4]));
                responses.add(countryProducer);
            }
        }
        return responses;
    }
}
