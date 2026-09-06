package com.example.csvccdshustbe.service.fluctuatingSituationToolService.impl;

import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.entity.FluctuatingSituationAsset;
import com.example.csvccdshustbe.entity.FluctuatingSituationTool;
import com.example.csvccdshustbe.repository.fluctuatingSituationTool.FluctuatingSituationToolRepository;
import com.example.csvccdshustbe.request.fluctuatingSituationTool.FindAllFluctuatingSituationToolRequest;
import com.example.csvccdshustbe.request.fluctuatingSituationTool.FluctuatingSituationToolRequest;
import com.example.csvccdshustbe.response.fluctuatingSituationTool.FindAllFluctuatingSituationToolResponses;
import com.example.csvccdshustbe.response.fluctuatingSituationTool.StatisticFluctuatingSituationTool;
import com.example.csvccdshustbe.service.fluctuatingSituationService.FluctuatingSituationService;
import com.example.csvccdshustbe.service.fluctuatingSituationToolService.FluctuatingSituationToolService;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.webjars.NotFoundException;

import java.util.Date;
import java.util.List;

@Service
public class FluctuatingSituationToolServiceImpl implements FluctuatingSituationToolService {

    @Lazy
    @Autowired
    FluctuatingSituationService fluctuatingSituationService;
    @Autowired
    FluctuatingSituationToolRepository fluctuatingSituationToolRepository;

    @Override
    public List<FluctuatingSituationTool> saveAll(List<FluctuatingSituationTool> fluctuatingSituationToolList) {
        return fluctuatingSituationToolRepository.saveAll(fluctuatingSituationToolList);
    }

    @Override
    public void updateStatusToolFluctuatingSituation(FluctuatingSituationToolRequest request) {
        List<FluctuatingSituationTool> fluctuatingSituationTools =
                findFluctuatingSituationToolByIds(request.getIdsFluctuatingSituationTool());
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        for (FluctuatingSituationTool fluctuatingSituationTool : fluctuatingSituationTools) {
            fluctuatingSituationTool.setTimeModified(String.valueOf(new Date().getTime()));
            fluctuatingSituationTool.setIdUserModified(csvcUser.getIdUser());
            if (request.getTypeCurrent().equals(Constants.TYPE_FLUCTUATING_SITUATION_DECLARE)) {
                fluctuatingSituationTool.setType(Constants.TYPE_FLUCTUATING_SITUATION_INCREASE);
                fluctuatingSituationTool.setStatus(Constants.STATUS_FLUCTUATING_SITUATION_TOOL_NOT_FINISH);
            } else {
                fluctuatingSituationTool.setStatus(Constants.STATUS_FLUCTUATING_SITUATION_TOOL_FINISH);
            }
        }
        fluctuatingSituationToolRepository.saveAll(fluctuatingSituationTools);
        updateStatusFluctuatingSituationTool(fluctuatingSituationTools.get(0).getIdFluctuatingSituation());
    }

    private void updateStatusFluctuatingSituationTool(Integer idFluctuatingSituation) {
        fluctuatingSituationService.calculatorStatusFluctuatingSituationToolById(idFluctuatingSituation);
    }

    @Override
    public StatisticFluctuatingSituationTool getStatisticFluctuatingSituationTool(Integer idFluctuatingSituation) {
        return fluctuatingSituationToolRepository.getStatisticFluctuatingSituationTool(idFluctuatingSituation);
    }

    @Override
    public Page<FindAllFluctuatingSituationToolResponses> findAllFluctuatingSituationTool(FindAllFluctuatingSituationToolRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        return fluctuatingSituationToolRepository.findAllFluctuatingSituationTool(request, pageable);
    }

    @Override
    public List<FluctuatingSituationTool> findFluctuatingSituationToolByIds(List<Integer> idsFluctuatingSituationTool) {
        List<FluctuatingSituationTool> fluctuatingSituationTools =
                fluctuatingSituationToolRepository.findFluctuatingSituationToolByIds(idsFluctuatingSituationTool);
        if (CollectionUtils.isEmpty(fluctuatingSituationTools)){
            throw new NotFoundException("Don't exits fluctuating situation tool by id!");
        }
        return fluctuatingSituationTools;
    }

    @Override
    public List<FluctuatingSituationTool> findFluctuatingSituationToolByIdFlu(Integer idsFluctuatingSituation) {
        List<FluctuatingSituationTool> fluctuatingSituationTools =
                fluctuatingSituationToolRepository.findFluctuatingSituationToolByIdFlu(idsFluctuatingSituation);
        if (CollectionUtils.isEmpty(fluctuatingSituationTools)){
            throw new NotFoundException("Don't exits fluctuating situation tool by id!");
        }
        return fluctuatingSituationTools;
    }

    @Override
    public void deleteListFluctuatingSituationTool(List<FluctuatingSituationTool> fluctuatingSituationTools) {
        fluctuatingSituationToolRepository.deleteAll(fluctuatingSituationTools);
    }
}
