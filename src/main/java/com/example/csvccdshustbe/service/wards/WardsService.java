package com.example.csvccdshustbe.service.wards;

import com.example.csvccdshustbe.dto.wards.WardsDto;
import com.example.csvccdshustbe.request.wards.FindAllWardsRequest;
import com.example.csvccdshustbe.response.wards.FindAllWardsResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface WardsService {

    Page<FindAllWardsResponse> findAllWardsResponse(FindAllWardsRequest request);
    Map<String, List<WardsDto>> findAllWardsToDownload();
}
