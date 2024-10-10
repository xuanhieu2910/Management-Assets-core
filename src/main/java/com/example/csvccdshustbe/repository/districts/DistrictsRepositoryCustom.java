package com.example.csvccdshustbe.repository.districts;

import com.example.csvccdshustbe.dto.districts.DistrictsDto;
import com.example.csvccdshustbe.entity.Districts;
import com.example.csvccdshustbe.request.districts.FindAllDistrictsRequest;
import com.example.csvccdshustbe.response.districts.FindAllDistrictsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface DistrictsRepositoryCustom {

    Page<FindAllDistrictsResponse> findAllDistrictsResponse(FindAllDistrictsRequest request, Pageable pageable);
    Map<String, List<DistrictsDto>> findAllDistrictsToDownload();
   List<Districts> findAllDistrictsByCodes(List<String> codeDistrict);
}
