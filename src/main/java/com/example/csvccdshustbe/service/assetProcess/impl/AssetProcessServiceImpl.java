package com.example.csvccdshustbe.service.assetProcess.impl;

import com.example.csvccdshustbe.dto.asset.FindAllAssetDto;
import com.example.csvccdshustbe.entity.AssetProcess;
import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.repository.assetProcess.AssetProcessRepository;
import com.example.csvccdshustbe.request.assetProcess.FindAllAssetProcessRequest;
import com.example.csvccdshustbe.response.assetProcess.FindAllAssetProcessResponse;
import com.example.csvccdshustbe.service.assetProcess.AssetProcessService;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.DateUtil;
import com.example.csvccdshustbe.utility.PageUtils;
import org.apache.poi.sl.draw.geom.GuideIf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AssetProcessServiceImpl implements AssetProcessService {

    @Autowired
    AssetProcessRepository assetProcessRepository;

    @Override
    public List<AssetProcess> saveListAssetProcess(List<AssetProcess> assetProcessList) {
        return assetProcessRepository.saveAll(assetProcessList);
    }

    @Override
    public Page<FindAllAssetProcessResponse> findAllAssetProcess(FindAllAssetProcessRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        List<Integer> idsDepartment = ((CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getIdsDepartmentCurrent();
        request.setIdsDepartmentOriginal(idsDepartment);
        Page<FindAllAssetDto> findAllAssetDtos = assetProcessRepository.findAllAssetProcess(request, pageable);
        return new PageImpl<>(convertToFindAllAssetProcess(findAllAssetDtos.getContent()),
                pageable, findAllAssetDtos.getTotalElements());
    }

    @Override
    public AssetProcess findAssetProcessByIdProcess(Integer idProcess) {
        Optional<AssetProcess> assetProcess = assetProcessRepository.findAssetProcessByIdProcess(idProcess);
        if (assetProcess.isEmpty()){
            throw new NotFoundException("Don't exits asset process!");
        }
        return assetProcess.get();
    }

    private List<FindAllAssetProcessResponse> convertToFindAllAssetProcess(List<FindAllAssetDto> content) {
        List<FindAllAssetProcessResponse> responses = new ArrayList<>();
        for (FindAllAssetDto dto : content) {
            FindAllAssetProcessResponse response = new FindAllAssetProcessResponse();
            response.setCodeAsset(dto.getCodeAsset());
            response.setNameAsset(dto.getNameAsset());
            response.setNameAssetCategory(dto.getNameAssetCategory());
            response.setCodeAssetCategory(dto.getCodeAssetCategory());
            response.setCodeDepartment(dto.getCodeDepartment());
            response.setNameDepartment(dto.getNameDepartment());
            response.setTimeCreated(DateUtil.formatToPattern(new Date(dto.getTimeCreated()), DateUtil.DATE_FORMAT));
            response.setTimeModified(DateUtil.formatToPattern( new Date(dto.getTimeModified()),DateUtil.DATE_FORMAT));
            responses.add(response);
        }
        return responses;
    }


}
