package com.example.csvccdshustbe.service.countryProducer.impl;

import com.example.csvccdshustbe.entity.CountryProducer;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.repository.countryProducer.CountryProducerRepository;
import com.example.csvccdshustbe.request.countryProducer.CreateCountryProducerRequest;
import com.example.csvccdshustbe.request.countryProducer.UpdateCountryProducerRequest;
import com.example.csvccdshustbe.response.countryProducer.FindAllCountryProducerResponse;
import com.example.csvccdshustbe.service.countryProducer.CountryProducerService;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.ValueUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

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
    public void updateCountryProducer(UpdateCountryProducerRequest request) throws ValidateFiledException {
        CountryProducer countryProducer =validateDataUpdateCountryProducer(request);
        countryProducerRepository.save(editLevelCountryProducer(countryProducer, request));

    }
    @Override
    public void deleteCountryProducerByIdCP(Integer idCountryProducer) {
        Optional<CountryProducer> countryProducerOptional = countryProducerRepository.findCountryProducerById(idCountryProducer);
        if (!countryProducerOptional.isPresent()){
            throw new NotFoundException("Don't exits Country producer by id");
        }
        countryProducerRepository.delete(countryProducerOptional.get());
    }

    private CountryProducer validateDataUpdateCountryProducer(UpdateCountryProducerRequest request) throws ValidateFiledException {
        Optional<CountryProducer> countryProducerOptional = countryProducerRepository.findCountryProducerById(request.getIdCountryProducer());
        if (!countryProducerOptional.isPresent()) {
            throw new NotFoundException("Don't exits Country producer by id!");
        }
        if (StringUtils.isBlank(request.getName())) {
            throw new ValidateFiledException("Validate data request!");
        }
        if (!countryProducerOptional.get().getName().equals(request.getName())) {

            if (StringUtils.isNotBlank(request.getName())){
                ValueUtil.validateNumberOrCharacter(request.getName());
            }
        }
        return countryProducerOptional.get();
    }
    private CountryProducer editLevelCountryProducer(CountryProducer countryProducer, UpdateCountryProducerRequest request) {
        countryProducer.setName(request.getName());
        countryProducer.setStatus(request.getStatus());
        String timeModified = String.valueOf(new Date().getTime());
        countryProducer.setTimeModified(timeModified);
        return countryProducer;
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
