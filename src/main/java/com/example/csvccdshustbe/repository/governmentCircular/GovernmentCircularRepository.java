package com.example.csvccdshustbe.repository.governmentCircular;

import com.example.csvccdshustbe.entity.GovernmentCircular;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GovernmentCircularRepository extends JpaRepository<GovernmentCircular, Integer>, GovernmentCircularRepositoryCustom {
}
