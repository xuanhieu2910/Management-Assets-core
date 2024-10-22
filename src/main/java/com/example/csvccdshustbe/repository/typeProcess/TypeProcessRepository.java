package com.example.csvccdshustbe.repository.typeProcess;

import com.example.csvccdshustbe.entity.TypeProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeProcessRepository extends JpaRepository<TypeProcess, Integer>, TypeProcessRepositoryCustom {
}
