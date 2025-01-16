package com.example.csvccdshustbe.repository.suppliers.impl;

import com.example.csvccdshustbe.entity.Suppliers;

import com.example.csvccdshustbe.repository.suppliers.SuppliersRepositoryCustom;
import com.example.csvccdshustbe.request.suppliers.FindAllSuppliersRequest;
import com.example.csvccdshustbe.response.suppliers.FindAllSuppliersResponse;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SuppliersRepositoryImpl implements SuppliersRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;
    @Override
    public Page<Suppliers> findAllSuppliersByStatus(FindAllSuppliersRequest request, Pageable pageable){
        StringBuilder sb = new StringBuilder();
        sb.append("select suppliers.id_supplier, suppliers.name, suppliers.phone_number, " +
                "suppliers.email, suppliers.fax, suppliers.address, suppliers.url, " +
                "suppliers.notes, suppliers.status, suppliers.time_created, suppliers.time_modified " +
                "from suppliers  " +
                "where 1=1 ");
        setConditionFindAllSuppliers(request,sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllSuppliers(request,query);
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
        return new PageImpl<>(suppliers, pageable, countFindAllSuppliers(request));
    }

    private long countFindAllSuppliers(FindAllSuppliersRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0) " +
                "  from suppliers where 1=1 ");
        setConditionFindAllSuppliers(request,sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllSuppliers(request, query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }

    private void setParameterFindAllSuppliers(FindAllSuppliersRequest request, Query query) {
        if (StringUtils.isNotBlank(request.getKeyword())) {
            query.setParameter("keyword", request.getKeyword());
        }
        if (ObjectUtils.isNotEmpty(request.getStatus())) {
            query.setParameter("status", request.getStatus());
        }
    }

    private void setConditionFindAllSuppliers(FindAllSuppliersRequest request, StringBuilder sb) {
        if (ObjectUtils.isNotEmpty(request.getStatus())) {
            sb.append(" and (suppliers.status = :status ) ");
        }
        if (StringUtils.isNotBlank(request.getKeyword())) {
            sb.append(" and (suppliers.name REGEXP :keyword ) ");
        }
    }


    @Override
    public Optional<Suppliers> findSuppliersByName(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select sp.id_supplier, sp.name, " +
                "sp.phone_number, sp.email, sp.fax, " +
                "sp.address, sp.url, sp.notes, " +
                "sp.time_created, sp.time_modified, sp.status " +
                "from suppliers sp " +
                "where sp.name = :name ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("name", name);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                Suppliers suppliers = new Suppliers();
                suppliers.setIdSupplier(ValueUtil.getIntegerByObject(obj[0]));
                suppliers.setName(ValueUtil.getStringByObject(obj[1]));
                suppliers.setPhoneNumber(ValueUtil.getStringByObject(obj[2]));
                suppliers.setEmail(ValueUtil.getStringByObject(obj[3]));
                suppliers.setFax(ValueUtil.getStringByObject(obj[4]));
                suppliers.setAddress(ValueUtil.getStringByObject(obj[5]));
                suppliers.setUrl(ValueUtil.getStringByObject(obj[6]));
                suppliers.setNotes(ValueUtil.getStringByObject(obj[7]));
                suppliers.setTimeCreated(ValueUtil.getStringByObject(obj[8]));
                suppliers.setTimeModified(ValueUtil.getStringByObject(obj[9]));
                suppliers.setStatus(ValueUtil.getIntegerByObject(obj[10]));
                return Optional.of(suppliers);
            }
        }
        return Optional.empty();
    }
    @Override
    public Optional<Suppliers> findSuppliersById(Integer idSuppliers) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select sp.id_supplier, sp.name, " +
                "sp.phone_number, sp.email, sp.fax, " +
                "sp.address, sp.url, sp.notes, " +
                "sp.time_created, sp.time_modified, sp.status " +
                "from suppliers sp " +
                "where sp.id_supplier = :idSuppliers ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idSuppliers", idSuppliers);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                Suppliers suppliers = new Suppliers();
                suppliers.setIdSupplier(ValueUtil.getIntegerByObject(obj[0]));
                suppliers.setName(ValueUtil.getStringByObject(obj[1]));
                suppliers.setPhoneNumber(ValueUtil.getStringByObject(obj[2]));
                suppliers.setEmail(ValueUtil.getStringByObject(obj[3]));
                suppliers.setFax(ValueUtil.getStringByObject(obj[4]));
                suppliers.setAddress(ValueUtil.getStringByObject(obj[5]));
                suppliers.setUrl(ValueUtil.getStringByObject(obj[6]));
                suppliers.setNotes(ValueUtil.getStringByObject(obj[7]));
                suppliers.setTimeCreated(ValueUtil.getStringByObject(obj[8]));
                suppliers.setTimeModified(ValueUtil.getStringByObject(obj[9]));
                suppliers.setStatus(ValueUtil.getIntegerByObject(obj[10]));
                return Optional.of(suppliers);
            }
        }
        return Optional.empty();
    }
}
