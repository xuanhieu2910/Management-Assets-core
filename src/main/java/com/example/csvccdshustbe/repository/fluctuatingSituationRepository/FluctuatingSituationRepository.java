package com.example.csvccdshustbe.repository.fluctuatingSituationRepository;

import com.example.csvccdshustbe.entity.FluctuatingSituation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FluctuatingSituationRepository extends JpaRepository<FluctuatingSituation, Integer>, FluctuatingSituationRepositoryCustom {
}
