package com.example.csvccdshustbe.repository.goalsUseGround;

import com.example.csvccdshustbe.dto.goalsUseGround.FindAllGoalsUseGroundDto;
import com.example.csvccdshustbe.entity.GoalsUseGround;
import com.example.csvccdshustbe.request.goalsUseGround.FindAllGoalsUseGroundRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface GoalsUseGroundRepositoryCustom {
    Page<GoalsUseGround> findAllGoalsUseGroundActive(FindAllGoalsUseGroundRequest request, Pageable pageable);
    Optional<GoalsUseGround> findGoalsUseGroundByName(String name);
    Optional<GoalsUseGround> findGoalsUseGroundById(Integer idGoalsUseGround);
    List<FindAllGoalsUseGroundDto> findAllGoalsUseGroundToDownload();
}
