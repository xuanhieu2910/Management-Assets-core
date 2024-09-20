package com.example.csvccdshustbe.service.wards;

import com.example.csvccdshustbe.request.wards.FindAllWardsRequest;
import com.example.csvccdshustbe.response.wards.FindAllWardsResponse;
import org.springframework.data.domain.Page;

public interface WardsService {

    Page<FindAllWardsResponse> findAllWardsResponse(FindAllWardsRequest request);

}
