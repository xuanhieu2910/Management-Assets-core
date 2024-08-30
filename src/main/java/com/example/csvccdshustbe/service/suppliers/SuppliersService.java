package com.example.csvccdshustbe.service.suppliers;

import com.example.csvccdshustbe.entity.Suppliers;
import com.example.csvccdshustbe.response.suppliers.FindAllSuppliersResponse;

import java.util.List;

public interface SuppliersService {
    List<FindAllSuppliersResponse> findAllSuppliersResponseByStatus(Integer status);
}
