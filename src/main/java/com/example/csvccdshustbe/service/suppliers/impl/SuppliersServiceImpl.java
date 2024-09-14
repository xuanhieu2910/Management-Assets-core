package com.example.csvccdshustbe.service.suppliers.impl;


import com.example.csvccdshustbe.entity.Suppliers;

import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.repository.suppliers.SuppliersRepository;

import com.example.csvccdshustbe.request.suppliers.CreateSuppliersRequest;
import com.example.csvccdshustbe.request.suppliers.UpdateSuppliersRequest;

import com.example.csvccdshustbe.response.suppliers.FindAllSuppliersResponse;
import com.example.csvccdshustbe.service.suppliers.SuppliersService;
import com.example.csvccdshustbe.utility.ValueUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SuppliersServiceImpl implements SuppliersService {

    @Autowired
    SuppliersRepository suppliersRepository;


    @Override
    public List<FindAllSuppliersResponse> findAllSuppliersResponseByStatus(Integer status) {
        return convertToFindAllSuppliers(suppliersRepository.findAllSuppliersByStatus(status));
    }

    private List<FindAllSuppliersResponse> convertToFindAllSuppliers(List<Suppliers> allSuppliersByStatus) {
        List<FindAllSuppliersResponse> responses = new ArrayList<>();
        for (Suppliers suppliers : allSuppliersByStatus){
            FindAllSuppliersResponse response = new FindAllSuppliersResponse();
            response.setIdSupplier(suppliers.getIdSupplier());
            response.setName(suppliers.getName());
            responses.add(response);
        }
        return responses;
    }

    @Override
    public void createSuppliers(CreateSuppliersRequest request) throws ValidateFiledException {
        validateDataCreateSuppliers(request);
        suppliersRepository.save(contructSuppliers(request));
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


    private void validateDataCreateSuppliers(CreateSuppliersRequest request) throws ValidateFiledException{
       if (StringUtils.isBlank(request.getName())) {
                throw new ValidateFiledException("Validate data request!");
            }
            Optional<Suppliers> suppliers = suppliersRepository.findSuppliersByName(request.getName());
            if (suppliers.isPresent()){
                throw new ValidateFiledException("Exits suppliers by name!");
        }

    }
    private Suppliers contructSuppliers(CreateSuppliersRequest request) {
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
        return suppliers;
    }

    private Suppliers validateDataUpdateSuppliers(UpdateSuppliersRequest request) throws ValidateFiledException{
        Optional<Suppliers> suppliersOptional = suppliersRepository.findSuppliersById(request.getIdSuppliers());
        if (suppliersOptional.isEmpty()) {
            throw new NotFoundException("Don't exits suppliers by id!");
        }
        if (StringUtils.isBlank(request.getName())) {
            throw new ValidateFiledException("Validate data request!");
        }
        return suppliersOptional.get();

    }

    private Suppliers editSuppliers(Suppliers suppliers, UpdateSuppliersRequest request) {
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
        return suppliers;
    }

}
