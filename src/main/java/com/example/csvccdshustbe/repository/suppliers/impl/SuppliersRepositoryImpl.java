package com.example.csvccdshustbe.repository.suppliers.impl;

import com.example.csvccdshustbe.entity.Suppliers;
import com.example.csvccdshustbe.repository.suppliers.SuppliersRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class SuppliersRepositoryImpl implements SuppliersRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;
    @Override
    public List<Suppliers> findAllSuppliersByStatus(Integer status){
        StringBuilder sb = new StringBuilder();
        sb.append("select suppliers.id_supplier, suppliers.name, suppliers.phone_number, " +
                "suppliers.email, suppliers.fax, suppliers.address, suppliers.url, " +
                "suppliers.notes, suppliers.status, suppliers.time_created, suppliers.time_modified " +
                "from suppliers  " +
                "where 1=1 and suppliers.status = :status ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("status", status);
        List<Object[]> result = query.getResultList();
        List<Suppliers>suppliers=new ArrayList<>();
        if(!CollectionUtils.isEmpty(result)){
            for(Object[] obj : result){
                Suppliers supplier= new Suppliers();
                supplier.setIdSupplier(ValueUtil.getIntegerByObject(obj[0]));
                supplier.setName(ValueUtil.getStringByObject(obj[1]));
                supplier.setPhoneNumber(ValueUtil.getStringByObject(obj[2]));
                supplier.setEmail(ValueUtil.getStringByObject(obj[3]));
                supplier.setFax(ValueUtil.getStringByObject(obj[4]));
                supplier.setAddress(ValueUtil.getStringByObject(obj[5]));
                supplier.setUrl(ValueUtil.getStringByObject(obj[6]));
                supplier.setNotes(ValueUtil.getStringByObject(obj[7]));
                supplier.setStatus(ValueUtil.getIntegerByObject(obj[8]));
                supplier.setTimeCreated(ValueUtil.getStringByObject(obj[9]));
                supplier.setTimeModified(ValueUtil.getStringByObject(obj[10]));
                suppliers.add(supplier);


            }
        }
        return suppliers;
    }
}
