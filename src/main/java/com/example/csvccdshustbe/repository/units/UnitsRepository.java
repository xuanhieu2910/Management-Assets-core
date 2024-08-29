package com.example.csvccdshustbe.repository.units;

import com.example.csvccdshustbe.entity.Units;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitsRepository extends JpaRepository<Units, Integer>, UnitsRepositoryCustom {
}
