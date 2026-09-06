package com.example.csvccdshustbe.service.location;

import com.example.csvccdshustbe.entity.Location;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.Location.*;
import com.example.csvccdshustbe.response.location.FindAllLocationResponse;
import com.example.csvccdshustbe.response.location.FindAllLocationVisibleResponse;
import org.springframework.data.domain.Page;

public interface LocationService {

    Page<FindAllLocationVisibleResponse> findAllLocationVisibleResponse(FindAllLocationVisibleRequest request);
    Page<FindAllLocationResponse> findAllLocationResponse(FindAllLocationRequest request);
    void createLocation(CreateLocationRequest request) throws ValidateFiledException;

    void updateLocation(UpdateLocationRequest request) throws ValidateFiledException;

    void deleteLocationByIdLocation(Integer idLocation) throws ValidateFiledException;

    Location findLocationByIdLocationAndIdDepartmentAndVisible(Integer idLocation,
                                                               Integer idDepartment,
                                                               Integer visible);

    void updateVisibleLocation(UpdateVisibleLocationRequest request) throws ValidateFiledException;
}
