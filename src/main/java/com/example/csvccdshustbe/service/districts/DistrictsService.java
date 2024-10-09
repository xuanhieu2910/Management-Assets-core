package com.example.csvccdshustbe.service.districts;

import com.example.csvccdshustbe.dto.districts.DistrictsDto;
import com.example.csvccdshustbe.request.districts.FindAllDistrictsRequest;
import com.example.csvccdshustbe.response.districts.FindAllDistrictsResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface DistrictsService {
    Page<FindAllDistrictsResponse> findAllDistrictResponse(FindAllDistrictsRequest request);
    Map<String, List<DistrictsDto>> findAllDistrictToDownload();
}
