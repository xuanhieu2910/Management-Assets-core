package com.example.csvccdshustbe.service.suppliers.impl;


import com.example.csvccdshustbe.entity.Suppliers;
import com.example.csvccdshustbe.repository.suppliers.SuppliersRepository;

import com.example.csvccdshustbe.response.suppliers.FindAllSuppliersResponse;
import com.example.csvccdshustbe.service.suppliers.SuppliersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
}
