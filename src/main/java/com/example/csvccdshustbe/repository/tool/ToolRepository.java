package com.example.csvccdshustbe.repository.tool;

import com.example.csvccdshustbe.entity.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolRepository extends JpaRepository<Tool, Integer>, ToolRepositoryCustom {
}
