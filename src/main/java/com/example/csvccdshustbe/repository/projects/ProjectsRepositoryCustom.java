package com.example.csvccdshustbe.repository.projects;

import com.example.csvccdshustbe.dto.projects.FindAllProjectsDto;
import com.example.csvccdshustbe.request.projects.FindAllProjectsRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectsRepositoryCustom {

    Page<FindAllProjectsDto> findAllProjectVisible(Pageable pageable, FindAllProjectsRequest request);
}
