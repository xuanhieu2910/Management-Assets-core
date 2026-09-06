package com.example.csvccdshustbe.repository.projects;

import com.example.csvccdshustbe.entity.Projects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectsRepository extends JpaRepository<Projects, Integer>, ProjectsRepositoryCustom {
}
