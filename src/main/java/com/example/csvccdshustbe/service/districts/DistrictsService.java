package com.example.csvccdshustbe.service.districts;

import com.example.csvccdshustbe.request.districts.FindAllDistrictsRequest;
import com.example.csvccdshustbe.response.districts.FindAllDistrictsResponse;
import org.springframework.data.domain.Page;

public interface DistrictsService {

    Page<FindAllDistrictsResponse> findAllDistrictResponse(FindAllDistrictsRequest request);
}
