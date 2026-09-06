package com.example.csvccdshustbe.repository.currentUsage;

import com.example.csvccdshustbe.entity.CurrentUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrentUsageRepository extends JpaRepository<CurrentUsage,Integer>, CurrentUsageRepositoryCustom{
}
