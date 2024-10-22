package com.example.csvccdshustbe.repository.process;

import com.example.csvccdshustbe.entity.Process;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessRepository extends JpaRepository<Process, Integer>, ProcessRepositoryCustom {
}
