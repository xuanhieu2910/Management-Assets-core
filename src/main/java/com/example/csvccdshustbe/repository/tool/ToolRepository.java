package com.example.csvccdshustbe.repository.tool;

import com.example.csvccdshustbe.dto.tool.ToolDto;
import com.example.csvccdshustbe.entity.Tool;
import com.example.csvccdshustbe.request.tool.FindAllToolRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolRepository extends JpaRepository<Tool, Integer>, ToolRepositoryCustom {
}
