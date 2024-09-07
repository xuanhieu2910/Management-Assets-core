package com.example.csvccdshustbe.repository.location;

import com.example.csvccdshustbe.dto.location.FindAllLocationDto;


import com.example.csvccdshustbe.entity.Location;
import com.example.csvccdshustbe.request.Location.FindAllLocationRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface LocationRepositoryCustom {

    Page<FindAllLocationDto> findAllLocationVisible( Pageable pageable,FindAllLocationRequest request);

    Optional<Location> findLocationByName(String name);

    Optional<Location> findLocationByIdParent(Integer idParent);

    Optional<Location> findLocationById(Integer idLocation);

    boolean checkExitsLocationByNameOrShortName(String name,String shortName);

    Optional<Location> findLocationByIdLocationAndIdDepartmentAndVisible(Integer idLocation,
                                                                         Integer idDepartment,
                                                                         Integer visible);

}
