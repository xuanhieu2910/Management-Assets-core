package com.example.csvccdshustbe.repository.districts;

import com.example.csvccdshustbe.entity.Districts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictsRepository extends JpaRepository<Districts, Integer>, DistrictsRepositoryCustom {
}
