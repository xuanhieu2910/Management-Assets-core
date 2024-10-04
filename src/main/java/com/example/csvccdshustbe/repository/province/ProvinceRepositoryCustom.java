package com.example.csvccdshustbe.repository.province;

import com.example.csvccdshustbe.entity.Provinces;
import com.example.csvccdshustbe.request.province.FindAllProvinceRequest;
import com.example.csvccdshustbe.response.province.FindAllProvinceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProvinceRepositoryCustom {

    Page<FindAllProvinceResponse> findAllProvinceResponse(FindAllProvinceRequest request, Pageable pageable);

    List<Provinces> findAllProvincesByCodes(List<String> ProvincesCodes );
}
