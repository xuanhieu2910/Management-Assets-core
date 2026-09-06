package com.example.csvccdshustbe.repository.houseDeclare;

import com.example.csvccdshustbe.entity.HouseDeclare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseDeclareRepository extends JpaRepository<HouseDeclare, Integer>, HouseDeclareRepositoryCustom {
}
