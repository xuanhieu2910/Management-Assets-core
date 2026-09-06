package com.example.csvccdshustbe.repository.suppliers.impl;

import com.example.csvccdshustbe.dto.suppliers.FindAllSuppliersDto;
import com.example.csvccdshustbe.entity.Suppliers;
import com.example.csvccdshustbe.repository.suppliers.SuppliersRepositoryCustom;
import com.example.csvccdshustbe.request.suppliers.FindAllSuppliersRequest;
import com.example.csvccdshustbe.response.suppliers.FindAllSuppliersResponse;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.DateUtil;
import com.example.csvccdshustbe.utility.PageUtils;
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

import java.util.*;

public class SuppliersRepositoryImpl implements SuppliersRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;
    @Override
    public Page<FindAllSuppliersResponse> findAllSuppliers(FindAllSuppliersRequest request, Pageable pageable){
        StringBuilder sb = new StringBuilder();
        sb.append(" select suppliers.id_supplier, suppliers.name, suppliers.phone_number,  " +
                "       suppliers.email, suppliers.fax, suppliers.address, suppliers.url,  " +
                "       suppliers.notes, suppliers.status, suppliers.time_created, suppliers.time_modified,  " +
                "       userCreated.user_name, userCreated.full_name, userModified.user_name, userModified.full_name,  " +
                "       department.id_department, department.name  " +
                "from suppliers  " +
                "    inner join csvc_user userCreated on suppliers.id_user_created = userCreated.id_user  " +
                "    inner join csvc_user userModified on suppliers.id_user_modified = userModified.id_user  " +
                "    inner join department on suppliers.id_department_original = department.id_department  " +
                "where 1 = 1  " +
                "and department.id_department in (:idsDepartment) ");
        setConditionFindAllSuppliers(request,sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllSuppliers(request,query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<FindAllSuppliersResponse> responses = new ArrayList<>();
        if(!CollectionUtils.isEmpty(result)){
            for(Object[] obj : result){
                responses.add(writeDataFindAllSuppliersResponses(obj));
            }
        }
        return new PageImpl<>(responses, pageable, countFindAllSuppliers(request));
    }

    private FindAllSuppliersResponse writeDataFindAllSuppliersResponses(Object[] obj) {
        FindAllSuppliersResponse response = new FindAllSuppliersResponse();
        response.setIdSupplier(ValueUtil.getIntegerByObject(obj[0]));
        response.setName(ValueUtil.getStringByObject(obj[1]));
        response.setPhoneNumber(ValueUtil.getStringByObject(obj[2]));
        response.setEmail(ValueUtil.getStringByObject(obj[3]));
        response.setFax(ValueUtil.getStringByObject(obj[4]));
        response.setAddress(ValueUtil.getStringByObject(obj[5]));
        response.setUrl(ValueUtil.getStringByObject(obj[6]));
        response.setNotes(ValueUtil.getStringByObject(obj[7]));
        response.setStatus(ValueUtil.getStringByObject(obj[8]));
        response.setTimeCreated(ValueUtil.getStringByObject(obj[9]));
        response.setTimeModified(ValueUtil.getStringByObject(obj[10]));
        response.setUserNameCreated(ValueUtil.getStringByObject(obj[11]));
        response.setFullNameCreated(ValueUtil.getStringByObject(obj[12]));
        response.setUserNameModified(ValueUtil.getStringByObject(obj[13]));
        response.setFullNameModified(ValueUtil.getStringByObject(obj[14]));
        response.setIdDepartment(ValueUtil.getIntegerByObject(obj[15]));
        response.setName(ValueUtil.getStringByObject(obj[16]));
        return response;
    }

    private long countFindAllSuppliers(FindAllSuppliersRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0) count  " +
                "from suppliers  " +
                "    inner join csvc_user userCreated on suppliers.id_user_created = userCreated.id_user  " +
                "    inner join csvc_user userModified on suppliers.id_user_modified = userModified.id_user  " +
                "    inner join department on suppliers.id_department_original = department.id_department  " +
                "where 1 = 1  " +
                "and department.id_department in (:idsDepartment) ");
        setConditionFindAllSuppliers(request,sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllSuppliers(request, query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }

    private void setParameterFindAllSuppliers(FindAllSuppliersRequest request, Query query) {
        query.setParameter("idsDepartment", request.getIdsDepartment());
        if (ObjectUtils.isNotEmpty(request.getStatus())){
            query.setParameter("status", request.getStatus());
        }
        if (StringUtils.isNotBlank(request.getName())) {
            query.setParameter("name", request.getName());
        }
        if (StringUtils.isNotBlank(request.getPhoneNumber())){
            query.setParameter("phoneNumber", request.getPhoneNumber());
        }
        if (StringUtils.isNotBlank(request.getEmail())) {
            query.setParameter("email", request.getEmail());
        }
        if (StringUtils.isNotBlank(request.getFax())){
            query.setParameter("fax", request.getFax());
        }
        if (StringUtils.isNotBlank(request.getAddress())){
            query.setParameter("address", request.getAddress());
        }
        if (StringUtils.isNotBlank(request.getUrl())) {
            query.setParameter("url", request.getUrl());
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())) {
            query.setParameter("idDepartment", request.getIdDepartment());
        }
    }

    private void setConditionFindAllSuppliers(FindAllSuppliersRequest request, StringBuilder sb) {
        if (ObjectUtils.isNotEmpty(request.getStatus())){
            sb.append(" and suppliers.status = :status ");
        }
        if (StringUtils.isNotBlank(request.getName())) {
            sb.append(" and (suppliers.name REGEXP :name ) ");
        }
        if (StringUtils.isNotBlank(request.getPhoneNumber())){
            sb.append(" and (suppliers.phone_number REGEXP :phoneNumber ) ");
        }
        if (StringUtils.isNotBlank(request.getEmail())) {
            sb.append(" and (suppliers.email REGEXP :email ) ");
        }
        if (StringUtils.isNotBlank(request.getFax())){
            sb.append(" and (suppliers.fax REGEXP :fax ) ");
        }
        if (StringUtils.isNotBlank(request.getAddress())){
            sb.append(" and (suppliers.address REGEXP :address ) ");
        }
        if (StringUtils.isNotBlank(request.getUrl())) {
            sb.append("  and (suppliers.url REGEXP :url ) ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())) {
            sb.append(" and department.id_department = :idDepartment ");
        }
        sb.append(" order by suppliers.id_supplier desc ");
    }


    @Override
    public Optional<Suppliers> findSuppliersByName(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select id_supplier, name, phone_number,   " +
                "       email, fax, address, url, notes,  " +
                "       status, time_created, time_modified,   " +
                "       id_department_original, id_user_created,  " +
                "       id_user_modified   " +
                "from suppliers  " +
                "where name = :name ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("name", name);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                return Optional.of(writeDataSuppliers(obj));
            }
        }
        return Optional.empty();
    }

    private Suppliers writeDataSuppliers(Object[] obj) {
        Suppliers suppliers = new Suppliers();
        suppliers.setIdSupplier(ValueUtil.getIntegerByObject(obj[0]));
        suppliers.setName(ValueUtil.getStringByObject(obj[1]));
        suppliers.setPhoneNumber(ValueUtil.getStringByObject(obj[2]));
        suppliers.setEmail(ValueUtil.getStringByObject(obj[3]));
        suppliers.setFax(ValueUtil.getStringByObject(obj[4]));
        suppliers.setAddress(ValueUtil.getStringByObject(obj[5]));
        suppliers.setUrl(ValueUtil.getStringByObject(obj[6]));
        suppliers.setNotes(ValueUtil.getStringByObject(obj[7]));
        suppliers.setStatus(ValueUtil.getIntegerByObject(obj[8]));
        suppliers.setTimeCreated(ValueUtil.getStringByObject(obj[9]));
        suppliers.setTimeModified(ValueUtil.getStringByObject(obj[10]));
        suppliers.setIdDepartmentOriginal(ValueUtil.getIntegerByObject(obj[11]));
        suppliers.setIdUserCreated(ValueUtil.getIntegerByObject(obj[12]));
        suppliers.setIdUserModified(ValueUtil.getIntegerByObject(obj[13]));
        return suppliers;
    }

    @Override
    public Optional<Suppliers> findSuppliersById(Integer idSupply) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select id_supplier, name, phone_number,  " +
                "       email, fax, address, url, notes,  " +
                "       status, time_created, time_modified,  " +
                "       id_department_original, id_user_created,  " +
                "       id_user_modified  " +
                "from suppliers  " +
                "where id_supplier = :idSupply ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idSupply", idSupply);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                return Optional.of(writeDataSuppliers(obj));
            }
        }
        return Optional.empty();
    }

    @Override
    public List<FindAllSuppliersDto> findAllSuppliersByIdsDepartmentOriginal(List<Integer> idsDepartmentOriginal) {
        StringBuilder sb = new StringBuilder();
        sb.append("select su.id_supplier idSupply, su.name nameSupply  " +
                "  from suppliers su " +
                "      inner join department de on de.id_department = su.id_department_original " +
                "where de.id_department in (:idDepartments) " +
                "and su.status = :statusSupply " +
                "and de.status = :statusDepartment ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idDepartments", idsDepartmentOriginal);
        query.setParameter("statusSupply", Constants.SUPPLIERS_ACTIVE_STATUS);
        query.setParameter("statusDepartment", Constants.DEPARTMENT_ACTIVE_STATUS);
        List<Object[]> result = query.getResultList();
        List<FindAllSuppliersDto> responses = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result){
                FindAllSuppliersDto findAllSuppliersDto = new FindAllSuppliersDto();
                findAllSuppliersDto.setIdSupplier(ValueUtil.getIntegerByObject(obj[0]));
                findAllSuppliersDto.setName(ValueUtil.getStringByObject(obj[1]));
                responses.add(findAllSuppliersDto);
            }
        }
        return responses;
    }
}
