package com.example.csvccdshustbe.service.suppliers;

import com.example.csvccdshustbe.entity.Suppliers;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.suppliers.CreateSuppliersRequest;
import com.example.csvccdshustbe.request.suppliers.UpdateSuppliersRequest;
import com.example.csvccdshustbe.response.suppliers.FindAllSuppliersResponse;

import java.util.List;

public interface SuppliersService {
    List<FindAllSuppliersResponse> findAllSuppliersResponseByStatus(Integer status);

    void createSuppliers(CreateSuppliersRequest request) throws ValidateFiledException;

    void updateSuppliers(UpdateSuppliersRequest request) throws ValidateFiledException;

    void deleteSuppliersByIdSuppliers(Integer idSuppliers);
}
