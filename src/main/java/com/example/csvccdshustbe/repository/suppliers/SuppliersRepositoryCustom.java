package com.example.csvccdshustbe.repository.suppliers;

import com.example.csvccdshustbe.entity.Suppliers;
import com.example.csvccdshustbe.request.suppliers.FindAllSuppliersRequest;
import com.example.csvccdshustbe.response.suppliers.FindAllSuppliersResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SuppliersRepositoryCustom {
    Page<Suppliers> findAllSuppliersByStatus(FindAllSuppliersRequest request, Pageable pageable);
    Optional<Suppliers> findSuppliersByName(String name);
    Optional<Suppliers> findSuppliersById(Integer idSuppliers);
}
