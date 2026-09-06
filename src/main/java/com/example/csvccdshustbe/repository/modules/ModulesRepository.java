package com.example.csvccdshustbe.repository.modules;

import com.example.csvccdshustbe.entity.Modules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModulesRepository extends JpaRepository<Modules, Integer>, ModulesRepositoryCustom  {
}
