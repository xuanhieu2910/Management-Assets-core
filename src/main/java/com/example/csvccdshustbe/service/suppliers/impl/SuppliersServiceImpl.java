package com.example.csvccdshustbe.service.suppliers.impl;


import com.example.csvccdshustbe.dto.suppliers.FindAllSuppliersDto;
import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.entity.Suppliers;

import com.example.csvccdshustbe.entity.UnitsTool;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.repository.suppliers.SuppliersRepository;

import com.example.csvccdshustbe.request.suppliers.CreateSuppliersRequest;
import com.example.csvccdshustbe.request.suppliers.FindAllSuppliersRequest;
import com.example.csvccdshustbe.request.suppliers.UpdateSuppliersRequest;

import com.example.csvccdshustbe.response.suppliers.FindAllSuppliersResponse;
import com.example.csvccdshustbe.service.suppliers.SuppliersService;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.PageUtils;
import com.example.csvccdshustbe.utility.ValueUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SuppliersServiceImpl implements SuppliersService {

    @Autowired
    SuppliersRepository suppliersRepository;


    @Override
    public Page<FindAllSuppliersResponse> findAllSuppliersResponse(FindAllSuppliersRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        request.setIdsDepartment(csvcUser.getIdsDepartmentCurrent());
        return suppliersRepository.findAllSuppliers(request, pageable);
    }

    @Override
    public void createSuppliers(CreateSuppliersRequest request) throws ValidateFiledException {
        validateDataCreateSuppliers(request);
        suppliersRepository.save(constructionSuppliers(request));
    }



    @Override
    public void updateSuppliers(UpdateSuppliersRequest request) throws ValidateFiledException {
        Suppliers suppliers = validateDataUpdateSuppliers(request);
        suppliersRepository.save(editSuppliers(suppliers, request));
    }



    @Override
    public void deleteSuppliersByIdSuppliers(Integer idSuppliers) {
        Optional<Suppliers> suppliersOptional = suppliersRepository.findSuppliersById(idSuppliers);
        if (suppliersOptional.isEmpty()){
            throw new NotFoundException("Don't exits Suppliers by id!");
        }
        suppliersRepository.delete(suppliersOptional.get());
    }

    @Override
    public List<FindAllSuppliersDto> findAllSuppliersByIdsDepartmentOriginal() {
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Integer> idsDepartment = csvcUser.getIdsDepartmentCurrent();
        idsDepartment.add(Constants.DEFAULT_ASSET_CATEGORY);
        return suppliersRepository.findAllSuppliersByIdsDepartmentOriginal(idsDepartment);
    }


    private void validateDataCreateSuppliers(CreateSuppliersRequest request) throws ValidateFiledException{
        if (StringUtils.isBlank(request.getName())) {
            throw new ValidateFiledException("Validate data request!");
        }
        Optional<Suppliers> suppliers = suppliersRepository.findSuppliersByName(request.getName());
        if (suppliers.isPresent()) {
            throw new ValidateFiledException("Exits suppliers by name!");
        }
    }
    private Suppliers constructionSuppliers(CreateSuppliersRequest request) {
        Suppliers suppliers = new Suppliers();
        suppliers.setName(request.getName().trim());
        suppliers.setPhoneNumber(request.getPhoneNumber());
        suppliers.setEmail(request.getEmail());
        suppliers.setFax(request.getFax());
        suppliers.setAddress(request.getAddress());
        suppliers.setUrl(request.getUrl());
        suppliers.setNotes(request.getNotes());
        suppliers.setStatus(request.getStatus());
        String timeCurrent = String.valueOf(new Date().getTime());
        suppliers.setTimeCreated(timeCurrent);
        suppliers.setTimeModified(timeCurrent);
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        suppliers.setIdUserCreated(csvcUser.getIdUser());
        suppliers.setIdUserModified(csvcUser.getIdUser());
        suppliers.setIdDepartmentOriginal(csvcUser.getIdDepartmentCurrent());
        return suppliers;
    }

    private Suppliers validateDataUpdateSuppliers(UpdateSuppliersRequest request) throws ValidateFiledException{
        Optional<Suppliers> suppliersOptional = suppliersRepository.findSuppliersById(request.getIdSuppliers());
        if (suppliersOptional.isEmpty()) {
            throw new NotFoundException("Don't exits suppliers by id!");
        }
        if (StringUtils.isBlank(request.getName())) {
            throw new ValidateFiledException("Validate data request!");
        } else {
            if (!suppliersOptional.get().getName().equals(request.getName())) {
                Optional<Suppliers> suppliers = suppliersRepository.findSuppliersByName(request.getName());
                if (suppliers.isPresent()){
                    throw new ValidateFiledException("Exits supply by name, please choose another name!");
                }
            }
        }
        return suppliersOptional.get();

    }

    private Suppliers editSuppliers(Suppliers suppliers, UpdateSuppliersRequest request) {
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        suppliers.setName(request.getName());
        suppliers.setPhoneNumber(request.getPhoneNumber());
        suppliers.setEmail(request.getEmail());
        suppliers.setFax(request.getFax());
        suppliers.setAddress(request.getAddress());
        suppliers.setUrl(request.getUrl());
        suppliers.setNotes(request.getNotes());
        suppliers.setStatus(request.getStatus());
        String timeModified = String.valueOf(new Date().getTime());
        suppliers.setTimeModified(timeModified);
        suppliers.setIdUserModified(csvcUser.getIdUser());
        return suppliers;
    }

}
