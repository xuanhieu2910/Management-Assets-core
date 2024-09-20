package com.example.csvccdshustbe.repository.wards;

import com.example.csvccdshustbe.request.wards.FindAllWardsRequest;
import com.example.csvccdshustbe.response.wards.FindAllWardsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WardsRepositoryCustom {

    Page<FindAllWardsResponse> findAllWardsResponse(FindAllWardsRequest request, Pageable pageable);
}
