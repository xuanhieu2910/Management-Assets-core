package com.example.csvccdshustbe.service.countryProducer.impl;

import com.example.csvccdshustbe.entity.CountryProducer;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.repository.countryProducer.CountryProducerRepository;
import com.example.csvccdshustbe.request.countryProducer.CreateCountryProducerRequest;
import com.example.csvccdshustbe.response.countryProducer.FindAllCountryProducerResponse;
import com.example.csvccdshustbe.service.countryProducer.CountryProducerService;
import com.example.csvccdshustbe.utility.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CountryProducerServiceImpl implements CountryProducerService {
    @Autowired
    CountryProducerRepository countryProducerRepository;
    @Override
    public List<FindAllCountryProducerResponse> findAllCountryProducerResponseByStatus(Integer status){
        return convertToFindAllCountryProducer(countryProducerRepository.findAllCountryProducerByStatus(status));
    }

    @Override
    public void createCountryProducer(CreateCountryProducerRequest request) throws ValidateFiledException {
        validateFiledCreateCountryProducer(request);
        countryProducerRepository.save(contructCountryProducer(request));
    }

    private CountryProducer contructCountryProducer(CreateCountryProducerRequest request) {
        CountryProducer countryProducer = new CountryProducer();
        countryProducer.setName(request.getName());
        countryProducer.setStatus(Constants.COUNTRY_PRODUCER_ACTIVE_STATUS);
        String currentTime = String.valueOf(new Date().getTime());
        countryProducer.setTimeCreated(currentTime);
        countryProducer.setTimeModified(currentTime);
        return countryProducer;
    }

    private void validateFiledCreateCountryProducer(CreateCountryProducerRequest request) throws ValidateFiledException {
        if (StringUtils.isBlank(request.getName())){
            throw new ValidateFiledException("Validate data request!");
        }
        Optional<CountryProducer> countryProducer = countryProducerRepository.
                findCountryProducerByNameAndStatus(request.getName().trim(), Constants.COUNTRY_PRODUCER_ACTIVE_STATUS);
        if (countryProducer.isPresent()){
            throw new ValidateFiledException("Exits country producer by name!");
        }
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
