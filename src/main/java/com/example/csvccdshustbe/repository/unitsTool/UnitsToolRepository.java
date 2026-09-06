package com.example.csvccdshustbe.repository.unitsTool;

import com.example.csvccdshustbe.entity.UnitsTool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitsToolRepository extends JpaRepository<UnitsTool, Integer>, UnitsToolRepositoryCustom {
}
