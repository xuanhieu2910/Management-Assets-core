package com.example.csvccdshustbe.service.process;

import com.example.csvccdshustbe.request.process.FindAllProcessAssetRequest;
import com.example.csvccdshustbe.response.process.FindAllProcessAssetResponse;
import org.springframework.data.domain.Page;

public interface ProcessService {

    Page<FindAllProcessAssetResponse> findAllProcessAsset(FindAllProcessAssetRequest request);
}
