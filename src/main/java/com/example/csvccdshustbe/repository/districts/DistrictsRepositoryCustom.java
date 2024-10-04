package com.example.csvccdshustbe.repository.districts;

import com.example.csvccdshustbe.entity.Districts;
import com.example.csvccdshustbe.request.districts.FindAllDistrictsRequest;
import com.example.csvccdshustbe.response.districts.FindAllDistrictsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DistrictsRepositoryCustom {

    Page<FindAllDistrictsResponse> findAllDistrictsResponse(FindAllDistrictsRequest request, Pageable pageable);
    List<Districts> findAllDistrictsByCodes(List<String> DistrictCodes );
}
