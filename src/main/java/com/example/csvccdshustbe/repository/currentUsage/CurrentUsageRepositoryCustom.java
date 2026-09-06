package com.example.csvccdshustbe.repository.currentUsage;



import com.example.csvccdshustbe.entity.CurrentUsage;

import java.util.List;
import java.util.Optional;

public interface CurrentUsageRepositoryCustom {
    List<CurrentUsage> findAllCurrentUsage();
    Optional<CurrentUsage> findCurrentUsageByName(String name);
    Optional<CurrentUsage> findCurrentUsageById(Integer idCurrentUsage);
}

