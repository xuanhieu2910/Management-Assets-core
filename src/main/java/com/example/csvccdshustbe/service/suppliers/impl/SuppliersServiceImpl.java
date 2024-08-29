package com.example.csvccdshustbe.service.suppliers.impl;

import com.example.csvccdshustbe.entity.Suppliers;
import com.example.csvccdshustbe.repository.suppliers.SuppliersRepository;

import com.example.csvccdshustbe.service.suppliers.SuppliersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuppliersServiceImpl implements SuppliersService {

    @Autowired
    SuppliersRepository suppliersRepository;


    @Override
    public List<Suppliers> findAllSuppliers() {
        return suppliersRepository.findAllSuppliers();
    }
}
