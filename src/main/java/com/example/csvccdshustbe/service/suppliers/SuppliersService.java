package com.example.csvccdshustbe.service.suppliers;

import com.example.csvccdshustbe.dto.suppliers.FindAllSuppliersDto;
import com.example.csvccdshustbe.entity.Suppliers;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.suppliers.CreateSuppliersRequest;
import com.example.csvccdshustbe.request.suppliers.FindAllSuppliersRequest;
import com.example.csvccdshustbe.request.suppliers.UpdateSuppliersRequest;
import com.example.csvccdshustbe.response.suppliers.FindAllSuppliersResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface SuppliersService {

    Page<FindAllSuppliersResponse>  findAllSuppliersResponse(FindAllSuppliersRequest request);
    void createSuppliers(CreateSuppliersRequest request) throws ValidateFiledException;
    void updateSuppliers(UpdateSuppliersRequest request) throws ValidateFiledException;
    void deleteSuppliersByIdSuppliers(Integer idSuppliers);
    List<FindAllSuppliersDto> findAllSuppliersByIdsDepartmentOriginal();
}
