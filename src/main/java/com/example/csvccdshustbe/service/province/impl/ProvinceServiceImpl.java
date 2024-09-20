package com.example.csvccdshustbe.service.province.impl;

import com.example.csvccdshustbe.repository.province.ProvinceRepository;
import com.example.csvccdshustbe.request.province.FindAllProvinceRequest;
import com.example.csvccdshustbe.response.province.FindAllProvinceResponse;
import com.example.csvccdshustbe.service.province.ProvinceService;
import com.example.csvccdshustbe.utility.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProvinceServiceImpl implements ProvinceService {

    @Autowired
    ProvinceRepository provinceRepository;


    @Override
    public Page<FindAllProvinceResponse> findAllProvinceResponse(FindAllProvinceRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        return provinceRepository.findAllProvinceResponse(request, pageable);
    }
}
