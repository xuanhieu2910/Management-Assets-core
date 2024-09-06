package com.example.csvccdshustbe.repository.suppliers;

import com.example.csvccdshustbe.entity.Suppliers;

import java.util.List;
import java.util.Optional;

public interface SuppliersRepositoryCustom {
    List<Suppliers> findAllSuppliersByStatus(Integer status);
    Optional<Suppliers> findSuppliersByName(String name);
    Optional<Suppliers> findSuppliersById(Integer idSuppliers);
}
