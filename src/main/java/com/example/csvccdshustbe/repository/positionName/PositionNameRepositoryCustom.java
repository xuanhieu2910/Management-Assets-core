package com.example.csvccdshustbe.repository.positionName;

import com.example.csvccdshustbe.dto.positionName.FindAllPositionNameDto;
import com.example.csvccdshustbe.entity.PositionName;
import com.example.csvccdshustbe.request.positionName.FindAllPositionNameRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PositionNameRepositoryCustom {
    Page<FindAllPositionNameDto> findAllPositionNameStatus(Pageable pageable, FindAllPositionNameRequest request);
    Optional<PositionName> findPositionNameByName(String name);

    Optional<PositionName> findPositionNameById(Integer idPositionName);

}
