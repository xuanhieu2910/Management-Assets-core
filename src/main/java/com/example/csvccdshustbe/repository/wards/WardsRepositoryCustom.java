package com.example.csvccdshustbe.repository.wards;

import com.example.csvccdshustbe.entity.Wards;
import com.example.csvccdshustbe.request.wards.FindAllWardsRequest;
import com.example.csvccdshustbe.response.wards.FindAllWardsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WardsRepositoryCustom {

    Page<FindAllWardsResponse> findAllWardsResponse(FindAllWardsRequest request, Pageable pageable);

    List<Wards> findAllWardsByCodes(List<String> wardsCodes );
}
