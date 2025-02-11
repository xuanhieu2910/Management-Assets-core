package com.example.csvccdshustbe.repository.toolInstance;

import com.example.csvccdshustbe.entity.ToolInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolInstanceRepository extends JpaRepository<ToolInstance,Integer>,ToolInstanceRepositoryCustom{
}
