package com.example.csvccdshustbe.service.curentUsage.impl;

import com.example.csvccdshustbe.entity.CurrentUsage;
import com.example.csvccdshustbe.repository.currentUsage.CurrentUsageRepository;
import com.example.csvccdshustbe.service.curentUsage.CurrentUsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CurrentUsageServiceImpl implements CurrentUsageService {
    @Autowired
    CurrentUsageRepository currentUsageRepository;
    @Override
    public List<CurrentUsage> findAllCurrentUsage(){
        return currentUsageRepository.findAllCurrentUsage();
    }
}
