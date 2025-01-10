package com.example.csvccdshustbe.repository.toolProcess;

import com.example.csvccdshustbe.entity.ToolProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolProcessRepository extends JpaRepository<ToolProcess, Integer>, ToolProcessRepositoryCustom {
}
