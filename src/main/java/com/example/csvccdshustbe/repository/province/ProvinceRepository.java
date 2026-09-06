package com.example.csvccdshustbe.repository.province;

import com.example.csvccdshustbe.entity.Provinces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinceRepository extends JpaRepository<Provinces, Integer>, ProvinceRepositoryCustom {
}
