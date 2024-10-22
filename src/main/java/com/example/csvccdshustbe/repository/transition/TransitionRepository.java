package com.example.csvccdshustbe.repository.transition;

import com.example.csvccdshustbe.entity.Transiton;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransitionRepository extends JpaRepository<Transiton, Integer>, TransitionRepositoryCustom {
}
