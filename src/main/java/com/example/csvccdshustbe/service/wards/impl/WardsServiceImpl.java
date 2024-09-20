package com.example.csvccdshustbe.service.wards.impl;

import com.example.csvccdshustbe.repository.wards.WardsRepository;
import com.example.csvccdshustbe.request.wards.FindAllWardsRequest;
import com.example.csvccdshustbe.response.wards.FindAllWardsResponse;
import com.example.csvccdshustbe.service.wards.WardsService;
import com.example.csvccdshustbe.utility.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class WardsServiceImpl implements WardsService {

    @Autowired
    WardsRepository wardsRepository;


    @Override
    public Page<FindAllWardsResponse> findAllWardsResponse(FindAllWardsRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        return wardsRepository.findAllWardsResponse(request, pageable);
    }
}
