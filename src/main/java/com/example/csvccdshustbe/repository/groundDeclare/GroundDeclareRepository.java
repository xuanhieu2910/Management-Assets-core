package com.example.csvccdshustbe.repository.groundDeclare;

import com.example.csvccdshustbe.entity.GroundDeclare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroundDeclareRepository extends JpaRepository<GroundDeclare,Integer>, GroundDeclareRepositoryCustom {
}
