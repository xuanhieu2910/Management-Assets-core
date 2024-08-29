package com.example.csvccdshustbe.service.originalOfFormation.impl;

import com.example.csvccdshustbe.dto.originalOfFormation.FindAllOriginalOfFormationDto;
import com.example.csvccdshustbe.repository.originalOfFormation.OriginalOfFormationRepository;
import com.example.csvccdshustbe.request.originalOfFormation.FindAllOriginalOfFormationRequest;
import com.example.csvccdshustbe.response.originalOfFormation.FindAllOriginalOfFormationResponse;
import com.example.csvccdshustbe.service.originalOfFormation.OriginalOfFormationService;
import com.example.csvccdshustbe.utility.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OriginalOfFormationServiceImpl implements OriginalOfFormationService {

    @Autowired
    OriginalOfFormationRepository originalOfFormationRepository;


    @Override
    public Page<FindAllOriginalOfFormationResponse> findAllOriginalOfFormation(FindAllOriginalOfFormationRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        Page<FindAllOriginalOfFormationDto> dtos = originalOfFormationRepository.findAllOriginalOfFormationVisible(pageable, request);
        return new PageImpl<>(convertToFindAllOriginalOfFormation(dtos.get().collect(Collectors.toList())), pageable, dtos.getTotalElements());
    }

    private List<FindAllOriginalOfFormationResponse> convertToFindAllOriginalOfFormation(List<FindAllOriginalOfFormationDto> dtos) {
        List<FindAllOriginalOfFormationResponse> responses = new ArrayList<>();
        for (FindAllOriginalOfFormationDto originalOfFormationDto: dtos){
            FindAllOriginalOfFormationResponse response = new FindAllOriginalOfFormationResponse();
            response.setIdOriginalOfFormation(originalOfFormationDto.getIdOriginalOfFormation());
            response.setName(originalOfFormationDto.getName());
            response.setParent(originalOfFormationDto.getParent());
            response.setVisible(originalOfFormationDto.getVisible());
            response.setDepth(originalOfFormationDto.getDepth());
            response.setPath(originalOfFormationDto.getPath());
            responses.add(response);
        }
        return responses;
    }
}
