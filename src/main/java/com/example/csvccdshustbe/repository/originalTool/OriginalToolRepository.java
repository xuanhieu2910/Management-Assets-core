package com.example.csvccdshustbe.repository.originalTool;

import com.example.csvccdshustbe.entity.OriginalTool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OriginalToolRepository extends JpaRepository<OriginalTool, Integer>, OriginalToolRepositoryCustom {

}
