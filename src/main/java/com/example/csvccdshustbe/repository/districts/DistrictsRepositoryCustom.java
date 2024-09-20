package com.example.csvccdshustbe.repository.districts;

import com.example.csvccdshustbe.request.districts.FindAllDistrictsRequest;
import com.example.csvccdshustbe.response.districts.FindAllDistrictsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DistrictsRepositoryCustom {

    Page<FindAllDistrictsResponse> findAllDistrictsResponse(FindAllDistrictsRequest request, Pageable pageable);
}
