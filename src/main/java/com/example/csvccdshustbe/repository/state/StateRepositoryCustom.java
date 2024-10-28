package com.example.csvccdshustbe.repository.state;

import com.example.csvccdshustbe.dto.state.StateDetailsDto;
import com.example.csvccdshustbe.dto.state.StateLinkListDto;
import com.example.csvccdshustbe.entity.State;
import org.apache.poi.sl.draw.geom.GuideIf;

import java.util.List;
import java.util.Optional;

public interface StateRepositoryCustom {
    Optional<State> findStateByIdState(Integer idState);
    Optional<StateLinkListDto> findStateByIdProcessAndStep(Integer idProcess, Integer stepState);
    Optional<StateDetailsDto> findStateDetailsByIdState(Integer idState, List<Integer> idsDepartment);
}
