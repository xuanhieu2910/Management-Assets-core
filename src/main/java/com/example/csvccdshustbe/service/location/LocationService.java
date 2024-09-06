package com.example.csvccdshustbe.service.location;

import com.example.csvccdshustbe.entity.Location;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.Location.CreateLocationRequest;
import com.example.csvccdshustbe.request.Location.FindAllLocationRequest;
import com.example.csvccdshustbe.request.Location.UpdateLocationRequest;
import com.example.csvccdshustbe.response.location.FindAllLocationResponse;
import org.springframework.data.domain.Page;

public interface LocationService {

    Page<FindAllLocationResponse> findAllLocationResponseByName(FindAllLocationRequest request,Integer idDepartment);
    void createLocation(CreateLocationRequest request) throws ValidateFiledException;

    void updateLocation(UpdateLocationRequest request) throws ValidateFiledException;

    void deleteLocationByIdLocation(Integer idLocation);

    Location findLocationByIdLocationAndIdDepartmentAndVisible(Integer idLocation,
                                                               Integer idDepartment,
                                                               Integer visible);
}
