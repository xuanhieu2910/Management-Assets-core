package com.example.csvccdshustbe.service.original.impl;

import com.example.csvccdshustbe.dto.original.FindAllOriginalDto;
import com.example.csvccdshustbe.entity.Original;
import com.example.csvccdshustbe.repository.original.OriginalRepository;
import com.example.csvccdshustbe.request.original.FindAllOriginalVisibleRequest;
import com.example.csvccdshustbe.response.original.FindAllOriginalVisibleResponse;
import com.example.csvccdshustbe.service.original.OriginalService;
import com.example.csvccdshustbe.utility.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OriginalServiceImpl implements OriginalService {

    @Autowired
    OriginalRepository originalRepository;

    @Override
    public Page<FindAllOriginalVisibleResponse> findAllOriginalVisibleResponse(FindAllOriginalVisibleRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        Page<FindAllOriginalDto> findAllOriginalDtos = originalRepository.findAllOriginalDtoByIdAssetCategory(request, pageable);
        return new PageImpl<>(convertToFindAllOriginalVisibleResponse(findAllOriginalDtos.get().collect(Collectors.toList())),
                pageable, findAllOriginalDtos.getTotalElements());
    }

    @Override
    public Original findOriginalByHardCodeAndStatus(String hardCode, Integer status) {
        Optional<Original> original = originalRepository.findOriginalByHardCodeAndStatus(hardCode, status);
        if (original.isEmpty()) {
            throw new NotFoundException("Don't exits original!");
        }
        return original.get();
    }

    private List<FindAllOriginalVisibleResponse> convertToFindAllOriginalVisibleResponse(List<FindAllOriginalDto> collect) {
        List<FindAllOriginalVisibleResponse> responses = new ArrayList<>();
        for (FindAllOriginalDto dto : collect){
            FindAllOriginalVisibleResponse response = new FindAllOriginalVisibleResponse();
            response.setIdOriginal(dto.getIdOriginal());
            response.setName(dto.getName());
            response.setCode(dto.getShortName());
            response.setDepth(dto.getDepth());
            response.setParent(dto.getParent());
            response.setVisible(dto.getVisible());
            response.setHardCodeDev(dto.getHardCodeDev());
            response.setIdAssetCategory(dto.getIdAssetCategory());
            responses.add(response);
        }
        return responses;
    }
}
