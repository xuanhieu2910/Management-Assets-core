package com.example.csvccdshustbe.repository.wards;

import com.example.csvccdshustbe.entity.Wards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WardsRepository extends JpaRepository<Wards, Integer>, WardsRepositoryCustom {
}
