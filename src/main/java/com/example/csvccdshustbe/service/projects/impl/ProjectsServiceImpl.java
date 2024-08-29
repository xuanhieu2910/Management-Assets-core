package com.example.csvccdshustbe.service.projects.impl;

import com.example.csvccdshustbe.dto.projects.FindAllProjectsDto;
import com.example.csvccdshustbe.repository.projects.ProjectsRepository;
import com.example.csvccdshustbe.request.projects.FindAllProjectsRequest;
import com.example.csvccdshustbe.response.projects.FindAllProjectsResponse;
import com.example.csvccdshustbe.service.projects.ProjectsService;
import com.example.csvccdshustbe.utility.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectsServiceImpl implements ProjectsService {


    @Autowired
    ProjectsRepository projectsRepository;

    @Override
    public Page<FindAllProjectsResponse> findAllProjectsResponseByName(FindAllProjectsRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        Page<FindAllProjectsDto> dtos = projectsRepository.findAllProjectVisible(pageable, request);
        return new PageImpl<>(convertToFindAllProjectsResponse(dtos.get().collect(Collectors.toList())),
                pageable, dtos.getTotalElements());
    }

    private List<FindAllProjectsResponse> convertToFindAllProjectsResponse(List<FindAllProjectsDto> collect) {
        List<FindAllProjectsResponse> responses = new ArrayList<>();
        for (FindAllProjectsDto allProjectsDto: collect){
            FindAllProjectsResponse res = new FindAllProjectsResponse();
            res.setIdProject(allProjectsDto.getIdProject());
            res.setName(allProjectsDto.getName());
            res.setShortName(allProjectsDto.getShortName());
            res.setParent(allProjectsDto.getParent());
            res.setDepth(allProjectsDto.getDepth());
            res.setPath(allProjectsDto.getPath());
            responses.add(res);
        }
        return responses;
    }

}
