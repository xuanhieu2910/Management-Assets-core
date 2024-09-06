package com.example.csvccdshustbe.repository.department.impl;

import com.example.csvccdshustbe.dto.department.FindAllDepartmentByCodeAndVisibleDto;
import com.example.csvccdshustbe.dto.department.FindAllDepartmentSDto;
import com.example.csvccdshustbe.entity.Department;
import com.example.csvccdshustbe.repository.department.DepartmentRepositoryCustom;
import com.example.csvccdshustbe.request.department.FindAllDepartmentRequest;
import com.example.csvccdshustbe.request.department.FindAllDepartmentVisibleRequest;
import com.example.csvccdshustbe.utility.Constants;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DepartmentRepositoryImpl implements DepartmentRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;
    @Override
    public Page<FindAllDepartmentByCodeAndVisibleDto>
    findAllDepartmentByCodeAndVisible(Pageable pageable, FindAllDepartmentVisibleRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("WITH RECURSIVE cte_department as ( " +
                "    select department.id_department,department.name, " +
                "           department.code, department.short_name, " +
                "           department.description, department.parent, " +
                "            department.time_created,department.status, " +
                "           department.time_modified, " +
                "           1 as depth,   CAST(department.id_department as NCHAR ) as path " +
                "    from department " +
                "    where department.parent is null " +
                "    union all " +
                "    select department.id_department,department.name, " +
                "           department.code, department.short_name, " +
                "           department.description, department.parent, " +
                "           department.time_created,department.status, " +
                "           department.time_modified, " +
                "           cte.depth + 1 as depth, " +
                "concat_ws('/',cte.path,CAST(department.id_department as NCHAR)) as path " +
                "from department" +
                "             INNER JOIN cte_department cte ON department.parent = cte.id_department " +
                ") " +
                "select cte.id_department, cte.name,  " +
                "       cte.code, cte.short_name, cte.description,  " +
                "       cte.parent,  " +
                "        cte.time_created, cte.time_modified,  " +
                "       cte.depth, cte.status, cte.path  " +
                "from cte_department cte  " +
                "where 1 = 1 and cte.status = :status ");
        setConditionFindAllDepartmentByCodeAndVisible(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllDepartmentByCodeAndVisible(request, query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<FindAllDepartmentByCodeAndVisibleDto> dtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj: result){
                FindAllDepartmentByCodeAndVisibleDto dto= new FindAllDepartmentByCodeAndVisibleDto();
                dto.setIdDepartment(ValueUtil.getIntegerByObject(obj[0]));
                dto.setName(ValueUtil.getStringByObject(obj[1]));
                dto.setCode(ValueUtil.getStringByObject(obj[2]));
                dto.setShortName(ValueUtil.getStringByObject(obj[3]));
                dto.setDescription(ValueUtil.getStringByObject(obj[4]));
                dto.setParent(ValueUtil.getIntegerByObject(obj[5]));
                dto.setTimeCreated(ValueUtil.getStringByObject(obj[6]));
                dto.setTimeModified(ValueUtil.getStringByObject(obj[7]));
                dto.setDepth(ValueUtil.getIntegerByObject(obj[8]));
                dto.setStatus(ValueUtil.getIntegerByObject(obj[9]));
                dto.setPath(ValueUtil.getStringByObject(obj[10]));
                dtos.add(dto);
            }
        }
        return new PageImpl<>(dtos, pageable, countFindAllDepartmentByCodeAndVisible(request));
    }

    private void setParameterFindAllDepartmentByCodeAndVisible(FindAllDepartmentVisibleRequest request, Query query) {
        query.setParameter("status", Constants.DEPARTMENT_ACTIVE_STATUS);
        if (StringUtils.isNotBlank(request.getKeyword())) {
            query.setParameter("keyword", request.getKeyword());
        }
    }

    @Override
    public Page<FindAllDepartmentSDto> findAllDepartment(Pageable pageable, FindAllDepartmentRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" WITH RECURSIVE cte_asset_categories as (     " +
                "      select department.id_department,department.name,     " +
                "             department.code, department.short_name,     " +
                "             department.description, department.parent,     " +
                "              department.time_created,department.status,     " +
                "             department.time_modified,     " +
                "             1 as depth,   CAST(department.id_department as NCHAR ) as path     " +
                "      from department     " +
                "      where department.parent is null     " +
                "      union all     " +
                "      select department.id_department,department.name,     " +
                "             department.code, department.short_name,     " +
                "             department.description, department.parent,     " +
                "             department.time_created,department.status,     " +
                "             department.time_modified,     " +
                "             cte.depth + 1 as depth,     " +
                "             concat_ws('/',cte.path,CAST(department.id_department as NCHAR)) as path  " +
                "from department  " +
                "               INNER JOIN cte_asset_categories cte ON department.parent = cte.id_department     " +
                "  )     " +
                "select cte.id_department, cte.name,  " +
                "         cte.code, cte.short_name, cte.description,      " +
                "         cte.parent,      " +
                "          cte.time_created, cte.time_modified,      " +
                "         cte.depth, cte.status, cte.path      " +
                "from cte_asset_categories cte  " +
                "where 1 = 1 ");
        setConditionFindAllDepartment(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllDepartment(request, query);
        PageUtils.buildQuery(pageable, query);
        List<FindAllDepartmentSDto> dtos = new ArrayList<>();
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for(Object[] obj: result){
                FindAllDepartmentSDto dto = new FindAllDepartmentSDto();
                dto.setIdDepartment(ValueUtil.getIntegerByObject(obj[0]));
                dto.setName(ValueUtil.getStringByObject(obj[1]));
                dto.setCode(ValueUtil.getStringByObject(obj[2]));
                dto.setShortName(ValueUtil.getStringByObject(obj[3]));
                dto.setDescription(ValueUtil.getStringByObject(obj[4]));
                dto.setParent(ValueUtil.getIntegerByObject(obj[5]));
                dto.setTimeCreated(ValueUtil.getStringByObject(obj[6]));
                dto.setTimeModified(ValueUtil.getStringByObject(obj[7]));
                dto.setDepth(ValueUtil.getIntegerByObject(obj[8]));
                dto.setStatus(ValueUtil.getIntegerByObject(obj[9]));
                dto.setPath(ValueUtil.getStringByObject(obj[10]));
                dtos.add(dto);
            }
        }
        return new PageImpl<>(dtos, pageable, countFindAllDepartment(request));
    }

    @Override
                public Optional<Department> findDepartmentByName(String name) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(" select de.id_department, de.name, de.code, " +
                            "       de.short_name, de.description, de.parent, " +
                            "       de.time_created, de.time_modified, de.status " +
                            "from department de " +
                            "where de.name = :name ");
                    Query query = entityManager.createNativeQuery(sb.toString());
                    query.setParameter("name", name);
                    List<Object[]> result = query.getResultList();
                    if (!CollectionUtils.isEmpty(result)){
                        for (Object[] obj: result){
                            Department department = new Department();
                            department.setIdDepartment(ValueUtil.getIntegerByObject(obj[0]));
                department.setName(ValueUtil.getStringByObject(obj[1]));
                department.setCode(ValueUtil.getStringByObject(obj[2]));
                department.setShortName(ValueUtil.getStringByObject(obj[3]));
                department.setDescription(ValueUtil.getStringByObject(obj[4]));
                department.setParent(ValueUtil.getIntegerByObject(obj[5]));
                department.setTimeCreated(ValueUtil.getStringByObject(obj[6]));
                department.setTimeModified(ValueUtil.getStringByObject(obj[7]));
                department.setStatus(ValueUtil.getIntegerByObject(obj[8]));
                return Optional.of(department);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Department> findDepartmentByIdParent(Integer idParent) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select de.id_department, de.name, de.code, " +
                "       de.short_name, de.description, de.parent, " +
                "       de.time_created, de.time_modified, de.status " +
                "from department de " +
                "where de.id_department = :idParent ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idParent", idParent);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                Department department = new Department();
                department.setIdDepartment(ValueUtil.getIntegerByObject(obj[0]));
                department.setName(ValueUtil.getStringByObject(obj[1]));
                department.setCode(ValueUtil.getStringByObject(obj[2]));
                department.setShortName(ValueUtil.getStringByObject(obj[3]));
                department.setDescription(ValueUtil.getStringByObject(obj[4]));
                department.setParent(ValueUtil.getIntegerByObject(obj[5]));
                department.setTimeCreated(ValueUtil.getStringByObject(obj[6]));
                department.setTimeModified(ValueUtil.getStringByObject(obj[7]));
                department.setStatus(ValueUtil.getIntegerByObject(obj[8]));
                return Optional.of(department);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Department> findDepartmentById(Integer idDepartment) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select de.id_department, de.name, de.code, " +
                "       de.short_name, de.description, de.parent, " +
                "       de.time_created, de.time_modified, de.status " +
                "from department de " +
                "where de.id_department = :idDepartment ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idDepartment", idDepartment);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                Department department = new Department();
                department.setIdDepartment(ValueUtil.getIntegerByObject(obj[0]));
                department.setName(ValueUtil.getStringByObject(obj[1]));
                department.setCode(ValueUtil.getStringByObject(obj[2]));
                department.setShortName(ValueUtil.getStringByObject(obj[3]));
                department.setDescription(ValueUtil.getStringByObject(obj[4]));
                department.setParent(ValueUtil.getIntegerByObject(obj[5]));
                department.setTimeCreated(ValueUtil.getStringByObject(obj[6]));
                department.setTimeModified(ValueUtil.getStringByObject(obj[7]));
                department.setStatus(ValueUtil.getIntegerByObject(obj[8]));
                return Optional.of(department);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Department> findDepartmentByIdDepartmentAndStatus(Integer idDepartment, Integer status) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select de.id_department, de.name, de.code, " +
                "       de.short_name, de.description, de.parent, " +
                "       de.time_created, de.time_modified, de.status " +
                "from department de " +
                "where de.id_department = :idDepartment and de.status = :status ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idDepartment", idDepartment);
        query.setParameter("status", status);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                Department department = new Department();
                department.setIdDepartment(ValueUtil.getIntegerByObject(obj[0]));
                department.setName(ValueUtil.getStringByObject(obj[1]));
                department.setCode(ValueUtil.getStringByObject(obj[2]));
                department.setShortName(ValueUtil.getStringByObject(obj[3]));
                department.setDescription(ValueUtil.getStringByObject(obj[4]));
                department.setParent(ValueUtil.getIntegerByObject(obj[5]));
                department.setTimeCreated(ValueUtil.getStringByObject(obj[6]));
                department.setTimeModified(ValueUtil.getStringByObject(obj[7]));
                department.setStatus(ValueUtil.getIntegerByObject(obj[8]));
                return Optional.of(department);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean checkExitsDepartmentByNameOrCodeOrShortName(String name, String code, String shortName) {
        StringBuilder sb = new StringBuilder();
        sb.append("select * " +
                "from department de " +
                "where 1 = 1 ");
        if (StringUtils.isNotBlank(name)){
            sb.append(" or de.name = :name ");
        }
        if (StringUtils.isNotBlank(code)){
            sb.append(" or de.code = :code ");
        }
        if (StringUtils.isNotBlank(shortName)){
            sb.append(" or de.short_name = :shortName ");
        }
        Query query = entityManager.createNativeQuery(sb.toString());
        if (StringUtils.isNotBlank(name)){
            query.setParameter("name", name);
        }
        if (StringUtils.isNotBlank(code)){
            query.setParameter("code", code);
        }
        if (StringUtils.isNotBlank(shortName)){
            query.setParameter("shortName", shortName);
        }
        List<Object[]> result = query.getResultList();
        return CollectionUtils.isEmpty(result);
    }

    private void setParameterFindAllDepartment(FindAllDepartmentRequest request, Query query) {
        if (StringUtils.isNotBlank(request.getKeyword())){
          query.setParameter("keyword", request.getKeyword());
        }
        if (ObjectUtils.isNotEmpty(request.getStatus())){
            query.setParameter("status", request.getStatus());
        }
        if (StringUtils.isNotBlank(request.getShortName())){
            query.setParameter("codeName", request.getCode());
        }
        if (StringUtils.isNotBlank(request.getShortName())){
            query.setParameter("shortName", request.getShortName());
        }
    }


    private void setConditionFindAllDepartment(FindAllDepartmentRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getKeyword())){
            sb.append(" and (cte.name REGEXP '[' + :keyword + ']') ");
        }
        if (ObjectUtils.isNotEmpty(request.getStatus())){
            sb.append(" and cte.status = :status ");
        }
        if (StringUtils.isNotBlank(request.getShortName())){
            sb.append(" and (cte.code REGEXP '[' + :codeName + ']') ");
        }
        if (StringUtils.isNotBlank(request.getShortName())){
            sb.append(" and (cte.short_name REGEXP '[' + :shortName + ']') ");
        }
    }

    private void setConditionFindAllDepartmentByCodeAndVisible(FindAllDepartmentVisibleRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getKeyword())){
            sb.append(" and (cte.name REGEXP '[' + :keyword + ']') ");
        }
    }



    private long countFindAllDepartmentByCodeAndVisible(FindAllDepartmentVisibleRequest request){
        StringBuilder sb = new StringBuilder();
        sb.append(" WITH RECURSIVE cte_department as (  " +
                "    select department.id_department,department.name,  " +
                "       department.code, department.short_name,  " +
                "       department.description, department.parent,  " +
                "    department.time_created,department.status,  " +
                "       department.time_modified,  " +
                "       1 as depth,   CAST(department.id_department as NCHAR ) as path  " +
                "    from department  " +
                "    where department.parent is null  " +
                "    union all  " +
                "    select department.id_department,department.name,  " +
                "       department.code, department.short_name,  " +
                "       department.description, department.parent,  " +
                "       department.time_created,department.status,  " +
                "       department.time_modified,  " +
                "       cte.depth + 1 as depth,  " +
                "    concat_ws('/',cte.path,CAST(department.id_department as NCHAR)) as path  " +
                "    from department " +
                "     INNER JOIN cte_department cte ON department.parent = cte.id_department  " +
                "    )  " +
                "    select count(cte.id_department) count  " +
                "    from cte_department cte   " +
                "where 1 = 1 and cte.status = :status  ");
        setConditionFindAllDepartmentByCodeAndVisible(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllDepartmentByCodeAndVisible(request, query);
        return  ValueUtil.getLongByObject(query.getSingleResult());
    }

    private long countFindAllDepartment(FindAllDepartmentRequest request){
        StringBuilder sb = new StringBuilder();
        sb.append(" WITH RECURSIVE cte_asset_categories as (     " +
                "      select department.id_department,department.name,     " +
                "             department.code, department.short_name,     " +
                "             department.description, department.parent,     " +
                "              department.time_created,department.status,     " +
                "             department.time_modified,     " +
                "             1 as depth,   CAST(department.id_department as NCHAR ) as path     " +
                "      from department     " +
                "      where department.parent is null     " +
                "      union all     " +
                "      select department.id_department,department.name,     " +
                "             department.code, department.short_name,     " +
                "             department.description, department.parent,     " +
                "             department.time_created,department.status,     " +
                "             department.time_modified,     " +
                "             cte.depth + 1 as depth,     " +
                "             concat_ws('/',cte.path,CAST(department.id_department as NCHAR)) as path  " +
                "from department  " +
                "               INNER JOIN cte_asset_categories cte ON department.parent = cte.id_department     " +
                "  )     " +
                "select count(cte.id_department) count  " +
                "from cte_asset_categories cte  " +
                "where 1 = 1 ");
        setConditionFindAllDepartment(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllDepartment(request, query);
        return  ValueUtil.getLongByObject(query.getSingleResult());
    }
}
