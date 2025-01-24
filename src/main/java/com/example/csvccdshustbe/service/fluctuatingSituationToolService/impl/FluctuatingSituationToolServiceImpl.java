package com.example.csvccdshustbe.service.fluctuatingSituationToolService.impl;

import com.example.csvccdshustbe.entity.FluctuatingSituationTool;
import com.example.csvccdshustbe.repository.fluctuatingSituationTool.FluctuatingSituationToolRepository;
import com.example.csvccdshustbe.request.fluctuatingSituationTool.FindAllFluctuatingSituationToolRequest;
import com.example.csvccdshustbe.response.fluctuatingSituationTool.FindAllFluctuatingSituationToolResponses;
import com.example.csvccdshustbe.response.fluctuatingSituationTool.StatisticFluctuatingSituationTool;
import com.example.csvccdshustbe.service.fluctuatingSituationToolService.FluctuatingSituationToolService;
import com.example.csvccdshustbe.utility.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FluctuatingSituationToolServiceImpl implements FluctuatingSituationToolService {

    @Autowired
    FluctuatingSituationToolRepository fluctuatingSituationToolRepository;

    @Override
    public List<FluctuatingSituationTool> saveAll(List<FluctuatingSituationTool> fluctuatingSituationToolList) {
        return fluctuatingSituationToolRepository.saveAll(fluctuatingSituationToolList);
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
}
