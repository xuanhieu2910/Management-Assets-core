package com.example.csvccdshustbe.repository.process;

import com.example.csvccdshustbe.entity.Process;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessRepository extends JpaRepository<Process, Integer>,ProcessRepositoryCustom{
}
