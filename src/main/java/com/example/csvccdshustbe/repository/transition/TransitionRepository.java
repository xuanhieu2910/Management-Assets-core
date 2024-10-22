package com.example.csvccdshustbe.repository.transition;

import com.example.csvccdshustbe.entity.Transition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransitionRepository extends JpaRepository<Transition, Integer>, TransitionRepositoryCustom {
}
