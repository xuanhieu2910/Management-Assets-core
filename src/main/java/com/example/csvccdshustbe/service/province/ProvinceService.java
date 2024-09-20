package com.example.csvccdshustbe.service.province;

import com.example.csvccdshustbe.request.province.FindAllProvinceRequest;
import com.example.csvccdshustbe.response.province.FindAllProvinceResponse;
import org.springframework.data.domain.Page;

public interface ProvinceService {

    Page<FindAllProvinceResponse> findAllProvinceResponse(FindAllProvinceRequest request);
}
