package com.example.csvccdshustbe.repository.typeState;

import com.example.csvccdshustbe.entity.TypeState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeStateRepository extends JpaRepository<TypeState, Integer>, TypeStateRepositoryCustom {
}
