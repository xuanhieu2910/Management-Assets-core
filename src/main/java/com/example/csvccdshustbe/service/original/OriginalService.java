package com.example.csvccdshustbe.service.original;

import com.example.csvccdshustbe.dto.original.FindAllOriginalDto;
import com.example.csvccdshustbe.entity.Original;
import com.example.csvccdshustbe.request.original.FindAllOriginalVisibleRequest;
import com.example.csvccdshustbe.response.original.FindAllOriginalVisibleResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface OriginalService {

    Page<FindAllOriginalVisibleResponse> findAllOriginalVisibleResponse(FindAllOriginalVisibleRequest request);
    Original findOriginalByHardCodeAndStatus(String hardCode, Integer status);
    Map<String, List<FindAllOriginalDto>> findAllOriginalToDownload();

}
