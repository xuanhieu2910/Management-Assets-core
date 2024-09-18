package com.example.csvccdshustbe.service.location.impl;

import com.example.csvccdshustbe.dto.location.FindAllLocationDto;
import com.example.csvccdshustbe.dto.projects.FindAllProjectsDto;
import com.example.csvccdshustbe.entity.Department;
import com.example.csvccdshustbe.entity.Location;
import com.example.csvccdshustbe.entity.Projects;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.repository.department.DepartmentRepository;
import com.example.csvccdshustbe.repository.location.LocationRepository;
import com.example.csvccdshustbe.request.Location.*;
import com.example.csvccdshustbe.response.location.FindAllLocationResponse;
import com.example.csvccdshustbe.response.location.FindAllLocationVisibleResponse;
import com.example.csvccdshustbe.service.location.LocationService;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.PageUtils;
import com.example.csvccdshustbe.utility.ValueUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService {
    @Autowired
    LocationRepository locationRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    public Page<FindAllLocationVisibleResponse> findAllLocationVisibleResponse(FindAllLocationVisibleRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        Page<FindAllLocationDto> dtos = locationRepository.findAllLocationVisible(pageable, request);
        return new PageImpl<>(convertToFindAllLocationsVisibleResponse(dtos.get().collect(Collectors.toList())),
                pageable, dtos.getTotalElements());
    }

    @Override
    public Page<FindAllLocationResponse> findAllLocationResponse(FindAllLocationRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        Page<FindAllLocationDto> dtos = locationRepository.findAllLocation(pageable, request);
        return new PageImpl<>(convertToFindAllLocationsResponse(dtos.get().collect(Collectors.toList())),
                pageable, dtos.getTotalElements());
    }

    private List<FindAllLocationVisibleResponse> convertToFindAllLocationsVisibleResponse(List<FindAllLocationDto> collect) {
        List<FindAllLocationVisibleResponse> responses = new ArrayList<>();
        for (FindAllLocationDto allLocationDto: collect){
            FindAllLocationVisibleResponse res = new FindAllLocationVisibleResponse();
            res.setIdLocation(allLocationDto.getIdLocation());
            res.setName(allLocationDto.getName());
            res.setShortName(allLocationDto.getShortName());
            res.setParent(allLocationDto.getParent());
            res.setDepth(allLocationDto.getDepth());
            res.setPath(allLocationDto.getPath());
            res.setIdDepartment(allLocationDto.getIdDepartment());
            responses.add(res);
        }
        return responses;
    }

    private List<FindAllLocationResponse> convertToFindAllLocationsResponse(List<FindAllLocationDto> collect) {
        List<FindAllLocationResponse> responses = new ArrayList<>();
        for (FindAllLocationDto allLocationDto: collect){
            FindAllLocationResponse res = new FindAllLocationResponse();
            res.setIdLocation(allLocationDto.getIdLocation());
            res.setName(allLocationDto.getName());
            res.setShortName(allLocationDto.getShortName());
            res.setParent(allLocationDto.getParent());
            res.setDepth(allLocationDto.getDepth());
            res.setPath(allLocationDto.getPath());
            res.setIdDepartment(allLocationDto.getIdDepartment());
            res.setNameParent(allLocationDto.getNameParent());
            res.setVisible(allLocationDto.getVisible());
            responses.add(res);
        }
        return responses;
    }


    @Override
    public void createLocation(CreateLocationRequest request) throws ValidateFiledException {
        validateDataCreateLocation(request);
        locationRepository.save(contructLocation(request));
    }

    private void validateDataCreateLocation(CreateLocationRequest request) throws ValidateFiledException{
        if (StringUtils.isBlank(request.getName())) {
            throw new ValidateFiledException("Validate data request!");
        }
//        ValueUtil.validateNumberOrCharacter(request.getName());
        Optional<Location> locationOptional = locationRepository.findLocationByName(request.getName());
        if (locationOptional.isPresent()){
            if (StringUtils.isNotBlank(request.getShortName())) {
                if (request.getShortName().equals(locationOptional.get().getShortName())){
                    throw new ValidateFiledException("Exits location by short name");
                }
            }
            throw new ValidateFiledException("Exits location by name of !");
        }
        if (ObjectUtils.isNotEmpty(request.getParentId())) {
            Optional<Location> location = locationRepository.findLocationByIdParent(request.getParentId());
            if (location.isEmpty()){
                throw new ValidateFiledException("Don't exits location by id parent!");
            }
        }
    }
    private Location contructLocation(CreateLocationRequest request) {
        Location location = new Location();
        location.setName(request.getName().trim());

        if (StringUtils.isNotBlank(request.getShortName())){
            location.setShortName(request.getShortName());
        }
        if (ObjectUtils.isNotEmpty(request.getParentId())){
            location.setParent(request.getParentId());
        }
        if (ObjectUtils.isNotEmpty(request.getVisible())) {
            location.setVisible(request.getVisible());
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())) {
            location.setIdDepartment(request.getIdDepartment());
        }
        String timeCurrent = String.valueOf(new Date().getTime());
        location.setTimeCreated(timeCurrent);
        location.setTimeModified(timeCurrent);
        return location;
    }

    @Override
    public void updateLocation(UpdateLocationRequest request) throws ValidateFiledException {
        Location location = validateDataUpdateLocation(request);
        locationRepository.save(editLocation(location, request));
    }

    private Location validateDataUpdateLocation(UpdateLocationRequest request) throws ValidateFiledException{
        Optional<Location> locationOptional = locationRepository.findLocationById(request.getIdLocation());
        if (locationOptional.isEmpty()) {
            throw new NotFoundException("Don't exits location by id!");
        }
        if (StringUtils.isBlank(request.getName())) {
            throw new ValidateFiledException("Validate data request!");
        }
        if (!locationOptional.get().getName().equals(request.getName()) ||
                !locationOptional.get().getShortName().equals(request.getShortName())) {
            if (locationRepository.checkExitsLocationByNameOrShortName(request.getName(),
                    request.getShortName())) {
                throw new ValidateFiledException("Exits location by name or short name!");
            }
        }
        if (ObjectUtils.isNotEmpty(request.getParentId())) {
            Optional<Location> locationByIdParent = locationRepository.findLocationByIdParent(request.getParentId());
            if (locationByIdParent.isEmpty()){
                throw new ValidateFiledException("Don't exits location by id parent!");
            }
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())) {
            Optional<Department> departmentOptional = departmentRepository.findDepartmentById(request.getIdDepartment());
            if (departmentOptional.isEmpty()){
                throw new ValidateFiledException("Don't exits department by id when update location!");
            }
        }


        return locationOptional.get();
    }
    private Location editLocation(Location location, UpdateLocationRequest request) {
        location.setName(request.getName());
        location.setShortName(request.getShortName());
        location.setParent(request.getParentId());
        location.setIdDepartment(request.getIdDepartment());
        location.setVisible(request.getVisible());
        String timeModified = String.valueOf(new Date().getTime());
        location.setTimeModified(timeModified);
        return location;
    }
    @Override
    public void deleteLocationByIdLocation(Integer idLocation) throws ValidateFiledException {
        Optional<Location> locationOptional = locationRepository.findLocationById(idLocation);
        if (locationOptional.isEmpty()){
            throw new NotFoundException("Don't exits location by id!");
        }
        if (locationRepository.isCheckExitsAssetByIdLocation(idLocation)){
            throw new ValidateFiledException("Exits asset by id location, can't delete location!");
        }
        locationRepository.delete(locationOptional.get());
    }

    @Override
    public Location findLocationByIdLocationAndIdDepartmentAndVisible(Integer idLocation, Integer idDepartment,
                                                                      Integer visible) {
        Optional<Location> location = locationRepository.findLocationByIdLocationAndIdDepartmentAndVisible(idLocation,
                idDepartment, visible);
        if (location.isEmpty()) {
            throw new NotFoundException("Don't exits location!");
        }
        return location.get();
    }

    @Override
    public void updateVisibleLocation(UpdateVisibleLocationRequest request) throws ValidateFiledException {
        Optional<Location> location = locationRepository.findLocationById(request.getIdLocation());
        if (location.isEmpty()){
            throw new NotFoundException("Don't exits location!");
        }
        if (!request.getVisible().equals(Constants.LOCATION_ACTIVE_STATUS)
                && !request.getVisible().equals(Constants.LOCATION_UN_ACTIVE_STATUS)) {
            throw new ValidateFiledException("Don't exits status location!");
        }
        location.get().setVisible(request.getVisible());
        locationRepository.save(location.get());
    }
}
