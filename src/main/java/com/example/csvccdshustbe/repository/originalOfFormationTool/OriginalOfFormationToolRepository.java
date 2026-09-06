package com.example.csvccdshustbe.repository.originalOfFormationTool;

import com.example.csvccdshustbe.entity.OriginalOfFormationTool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OriginalOfFormationToolRepository
        extends JpaRepository<OriginalOfFormationTool, Integer>, OriginalOfFormationToolRepositoryCustom{
}
