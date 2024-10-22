package com.example.csvccdshustbe.service.process.impl;

import com.example.csvccdshustbe.dto.process.FindAllProcessAssetDto;
import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.repository.process.ProcessRepository;
import com.example.csvccdshustbe.request.process.FindAllProcessAssetRequest;
import com.example.csvccdshustbe.response.process.FindAllProcessAssetResponse;
import com.example.csvccdshustbe.service.process.ProcessService;
import com.example.csvccdshustbe.utility.DateUtil;
import com.example.csvccdshustbe.utility.PageUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class ProcessServiceImpl implements ProcessService {
    @Autowired
    ProcessRepository processRepository;

    @Override
    public Page<FindAllProcessAssetResponse> findAllProcessAsset(FindAllProcessAssetRequest request){
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        setIdsDepartmentOriginal(request);
        setStatusTypeProcess(request);
        Page<FindAllProcessAssetDto> findAllProcessAssetDtos = processRepository.findAllProcessAssetDtoByIdsDepartment(request, pageable);
        return new PageImpl<>(convertToFindAllProcessAssetResponse(findAllProcessAssetDtos.get().collect(Collectors.toList())),
                pageable, findAllProcessAssetDtos.getTotalElements());
    }



    private List<FindAllProcessAssetResponse> convertToFindAllProcessAssetResponse(List<FindAllProcessAssetDto> collect) {
        List<FindAllProcessAssetResponse> responses = new ArrayList<>();
        for (FindAllProcessAssetDto dto : collect) {
            FindAllProcessAssetResponse response = new FindAllProcessAssetResponse();
            response.setCodeDocument(dto.getCodeDocument());
            response.setIdUserCreate(dto.getIdUserCreate());
            response.setCodeUserCreate(dto.getCodeUserCreate());
            response.setNameUserCreate(dto.getNameUserCreate());
            response.setStatus(dto.getStatus());
            response.setTimeIncrease(dto.getTimeIncrease());

            response.setCodeDepartment(dto.getCodeDepartment());
            response.setNameDepartment(dto.getNameDepartment());
            response.setTimeCreated(DateUtil.formatToPattern(
                    DateUtil.formatDatePattern(dto.getTimeCreated(),
                            DateUtil.DATE_FORMAT),DateUtil.DATE_FORMAT_HH_MM));
            response.setTimeModified(DateUtil.formatToPattern(
                    DateUtil.formatDatePattern(dto.getTimeModified(),
                            DateUtil.DATE_FORMAT),DateUtil.DATE_FORMAT_HH_MM));
            response.setTimeDocument(dto.getTimeDocument());
            responses.add(response);
        }
        return responses;
    }

    private void setIdsDepartmentOriginal(FindAllProcessAssetRequest request) {
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        request.setIdsDepartmentOriginal(csvcUser.getIdsDepartmentCurrent());
    }
    private void setStatusTypeProcess(FindAllProcessAssetRequest request) {
        request.setStatusTypeProcess(1);//test thu = 1 truoc xong xet constant sau
    }
}

