package com.example.csvccdshustbe.service.fluctuatingSituationService.impl;

import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.entity.FluctuatingSituation;
import com.example.csvccdshustbe.repository.fluctuatingSituationRepository.FluctuatingSituationRepository;
import com.example.csvccdshustbe.request.fluctuatingSituation.FindAllFluctuatingSituationRequest;
import com.example.csvccdshustbe.response.fluctuatingSituation.FindAllFluctuationSituationResponse;
import com.example.csvccdshustbe.service.fluctuatingSituationService.FluctuatingSituationService;
import com.example.csvccdshustbe.utility.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class FluctuatingSituationServiceImpl implements FluctuatingSituationService {

    @Autowired
    FluctuatingSituationRepository fluctuatingSituationRepository;


    @Override
    public FluctuatingSituation saveFluctuatingSituation(FluctuatingSituation fluctuatingSituation) {
        return fluctuatingSituationRepository.save(fluctuatingSituation);
    }

    @Override
    public Page<FindAllFluctuationSituationResponse> findAllFluctuatingSituation(FindAllFluctuatingSituationRequest request) {
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        request.setIdsDepartmentOriginal(csvcUser.getIdsDepartmentCurrent());
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        return fluctuatingSituationRepository.findAllFluctuationSituationResponse(pageable, request);
    }

    @Override
    public void calculatorStatusFluctuatingSituationById(Integer idFluctuatingSituation) {
        fluctuatingSituationRepository.calculatorStatusFluctuatingSituationById(idFluctuatingSituation);
    }
}
