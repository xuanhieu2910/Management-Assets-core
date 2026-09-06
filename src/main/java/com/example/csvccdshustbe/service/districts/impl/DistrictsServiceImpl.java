package com.example.csvccdshustbe.service.districts.impl;

import com.example.csvccdshustbe.dto.districts.DistrictsDto;
import com.example.csvccdshustbe.repository.districts.DistrictsRepository;
import com.example.csvccdshustbe.request.districts.FindAllDistrictsRequest;
import com.example.csvccdshustbe.response.districts.FindAllDistrictsResponse;
import com.example.csvccdshustbe.service.districts.DistrictsService;
import com.example.csvccdshustbe.utility.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class DistrictsServiceImpl implements DistrictsService {


    @Autowired
    DistrictsRepository districtsRepository;

    @Override
    public Page<FindAllDistrictsResponse> findAllDistrictResponse(FindAllDistrictsRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        return districtsRepository.findAllDistrictsResponse(request, pageable);
    }

    @Override
    public Map<String, List<DistrictsDto>> findAllDistrictToDownload() {
        return districtsRepository.findAllDistrictsToDownload();
    }
}
