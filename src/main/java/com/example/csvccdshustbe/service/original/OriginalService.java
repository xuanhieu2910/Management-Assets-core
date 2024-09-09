package com.example.csvccdshustbe.service.original;

import com.example.csvccdshustbe.entity.Original;
import com.example.csvccdshustbe.request.original.FindAllOriginalVisibleRequest;
import com.example.csvccdshustbe.response.original.FindAllOriginalVisibleResponse;
import org.springframework.data.domain.Page;

public interface OriginalService {

    Page<FindAllOriginalVisibleResponse> findAllOriginalVisibleResponse(FindAllOriginalVisibleRequest request);


    Original findOriginalByHardCodeAndStatus(String hardCode, Integer status);

}
