package com.example.csvccdshustbe.service.projects;

import com.example.csvccdshustbe.request.projects.FindAllProjectsRequest;
import com.example.csvccdshustbe.response.projects.FindAllProjectsResponse;
import org.springframework.data.domain.Page;

public interface ProjectsService {

    Page<FindAllProjectsResponse> findAllProjectsResponseByName(FindAllProjectsRequest request);
}
