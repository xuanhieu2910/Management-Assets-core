package com.example.csvccdshustbe.service.declare.commonDeclare.impl;

import com.example.csvccdshustbe.dto.declare.CommonDeclareDetailsDto;
import com.example.csvccdshustbe.entity.CommonDeclare;
import com.example.csvccdshustbe.repository.commonDeclare.CommonDeclareRepository;
import com.example.csvccdshustbe.service.declare.commonDeclare.CommonDeclareService;
import com.example.csvccdshustbe.utility.ValueUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Map;
import java.util.Optional;

@Service
public class CommonDeclareServiceImpl implements CommonDeclareService {

    @Autowired
    CommonDeclareRepository commonDeclareRepository;


    @Override
    public CommonDeclare save(CommonDeclare declare) {
        return commonDeclareRepository.save(declare);
    }

    @Override
    public Map<String, Object> findCommonDeclareDetailsDtoById(Integer idInstance) throws IllegalAccessException {
        Optional<CommonDeclareDetailsDto> detailsDto = commonDeclareRepository.findCommonDeclareDetailDtoById(idInstance);
        if (!detailsDto.isPresent()){
            throw new NotFoundException("Don't exits common declare details!");
        }
        return ValueUtil.convertObjectToMap(detailsDto.get());
    }

    @Override
    public void deleteCommonDeclareById(Integer idInstance) {
        commonDeclareRepository.deleteCommonDeclareById(idInstance);
    }

    @Override
    public CommonDeclare findCommonDeclareById(Integer idInstance) {
        Optional<CommonDeclare> commonDeclare = commonDeclareRepository.findCommonDeclareById(idInstance);
        if (commonDeclare.isEmpty()){
            throw new NotFoundException("Don't exits find common declare!");
        }
        return commonDeclare.get();
    }
}
