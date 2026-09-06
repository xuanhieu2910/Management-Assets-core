package com.example.csvccdshustbe.repository.fluctuatingSituationTool;

import com.example.csvccdshustbe.entity.FluctuatingSituationTool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FluctuatingSituationToolRepository extends JpaRepository<FluctuatingSituationTool, Integer>,
                FluctuatingSituationToolRepositoryCustom{

}
