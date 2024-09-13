package com.example.csvccdshustbe.service.countryProducer.impl;

import com.example.csvccdshustbe.entity.CountryProducer;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.repository.countryProducer.CountryProducerRepository;
import com.example.csvccdshustbe.request.countryProducer.CreateCountryProducerRequest;
import com.example.csvccdshustbe.request.countryProducer.FindAllCountryProducerActiveRequest;
import com.example.csvccdshustbe.request.countryProducer.UpdateCountryProducerRequest;
import com.example.csvccdshustbe.response.countryProducer.FindAllCountryProducerPickedResponse;
import com.example.csvccdshustbe.service.countryProducer.CountryProducerService;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.PageUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CountryProducerServiceImpl implements CountryProducerService {
    @Autowired
    CountryProducerRepository countryProducerRepository;
    @Override
    public Page<FindAllCountryProducerPickedResponse> findAllCountryProducerActiveResponse(FindAllCountryProducerActiveRequest request){
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        Page<CountryProducer> countryProducers = countryProducerRepository.findAllCountryProducerPickedResponse(request, pageable);
        return new PageImpl<>(convertToFindAllCountryProducerPicked(countryProducers.get().collect(Collectors.toList())),
                pageable, countryProducers.getTotalElements());
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
        return countryProducerOptional.get();
    }
    private CountryProducer editLevelCountryProducer(CountryProducer countryProducer, UpdateCountryProducerRequest request) {
        countryProducer.setName(request.getName());
        countryProducer.setStatus(request.getStatus());
        String timeModified = String.valueOf(new Date().getTime());
        countryProducer.setTimeModified(timeModified);
        return countryProducer;
    }

    private List<FindAllCountryProducerPickedResponse> convertToFindAllCountryProducerPicked(List<CountryProducer> allCountryProducerByStatus) {
        List<FindAllCountryProducerPickedResponse> responses = new ArrayList<>();
        for (CountryProducer countryProducer : allCountryProducerByStatus){
            FindAllCountryProducerPickedResponse response = new FindAllCountryProducerPickedResponse();
            response.setIdCountryProducer(countryProducer.getIdCountryProducer());
            response.setNameCountryProducer(countryProducer.getName());
            responses.add(response);
        }
        return responses;
    }
}
