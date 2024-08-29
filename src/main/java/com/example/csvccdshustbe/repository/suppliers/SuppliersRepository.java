package com.example.csvccdshustbe.repository.suppliers;

import com.example.csvccdshustbe.entity.Suppliers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuppliersRepository extends JpaRepository<Suppliers,Integer>,SuppliersRepositoryCustom {
}
