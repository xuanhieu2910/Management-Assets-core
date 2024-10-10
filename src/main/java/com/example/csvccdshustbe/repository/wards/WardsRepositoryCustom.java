package com.example.csvccdshustbe.repository.wards;

import com.example.csvccdshustbe.dto.wards.WardsDto;
import com.example.csvccdshustbe.entity.Districts;
import com.example.csvccdshustbe.entity.Wards;
import com.example.csvccdshustbe.request.wards.FindAllWardsRequest;
import com.example.csvccdshustbe.response.wards.FindAllWardsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface WardsRepositoryCustom {

    Page<FindAllWardsResponse> findAllWardsResponse(FindAllWardsRequest request, Pageable pageable);
    Map<String, List<WardsDto>> findAllWardsToDownload();
    List<Wards> findAllWardsByCodes(List<String> codeWard);
}
