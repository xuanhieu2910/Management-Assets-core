package com.example.csvccdshustbe.repository.goalsUseGround;

import com.example.csvccdshustbe.entity.GoalsUseGround;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalsUseGroundRepository extends JpaRepository<GoalsUseGround,Integer>,GoalsUseGroundRepositoryCustom {
}
