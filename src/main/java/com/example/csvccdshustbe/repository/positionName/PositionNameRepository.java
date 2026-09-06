package com.example.csvccdshustbe.repository.positionName;

import com.example.csvccdshustbe.entity.PositionName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionNameRepository extends JpaRepository<PositionName,Integer>,PositionNameRepositoryCustom {
}
