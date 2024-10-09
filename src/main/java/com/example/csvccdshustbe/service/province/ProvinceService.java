package com.example.csvccdshustbe.service.province;

import com.example.csvccdshustbe.dto.provinces.ProvincesDto;
import com.example.csvccdshustbe.entity.Provinces;
import com.example.csvccdshustbe.request.province.FindAllProvinceRequest;
import com.example.csvccdshustbe.response.province.FindAllProvinceResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface ProvinceService {
    Page<FindAllProvinceResponse> findAllProvinceResponse(FindAllProvinceRequest request);
    List<ProvincesDto> findAllProvinceToDownload();
}
