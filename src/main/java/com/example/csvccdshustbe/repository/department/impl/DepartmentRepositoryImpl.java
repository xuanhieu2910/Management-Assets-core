package com.example.csvccdshustbe.repository.department.impl;

import com.example.csvccdshustbe.dto.department.FindAllDepartmentByCodeAndVisibleDto;
import com.example.csvccdshustbe.dto.department.FindAllDepartmentSDto;
import com.example.csvccdshustbe.dto.location.FindAllLocationDto;
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

import java.util.*;

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
                "where 1 = 1 and cte.status = :status and cte.id_department in (:idsDepartment) ");
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

    @Override
    public List<FindAllDepartmentByCodeAndVisibleDto> findAllDepartmentByCodeAndVisible() {
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
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("status", Constants.DEPARTMENT_ACTIVE_STATUS);
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
        return dtos;
    }

    @Override
    public List<FindAllDepartmentByCodeAndVisibleDto> findAllStructDepartmentByIdDepartment(Integer idDepartment) {
        StringBuilder sb = new StringBuilder();
        sb.append(" WITH RECURSIVE cte_department as (     " +
                "       select department.id_department,department.name,     " +
                "              department.code, department.short_name,     " +
                "              department.description, department.parent,     " +
                "               department.time_created,department.status,     " +
                "              department.time_modified,     " +
                "              1 as depth,   CAST(department.id_department as NCHAR ) as path     " +
                "       from department     " +
                "       where department.id_department = :idDepartment  " +
                "       union all     " +
                "       select department.id_department,department.name,     " +
                "              department.code, department.short_name,     " +
                "              department.description, department.parent,     " +
                "              department.time_created,department.status,     " +
                "              department.time_modified,     " +
                "              cte.depth + 1 as depth,     " +
                "   concat_ws('/',cte.path,CAST(department.id_department as NCHAR)) as path     " +
                "   from department    " +
                "                INNER JOIN cte_department cte ON department.parent = cte.id_department     " +
                "   )     " +
                "select cte.id_department, cte.name,  " +
                "       cte.code, cte.short_name, cte.description,  " +
                "       cte.parent,  " +
                "       cte.time_created, cte.time_modified,  " +
                "       cte.depth, cte.status, cte.path  " +
                "from cte_department cte  " +
                "where cte.status = :status ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idDepartment", idDepartment);
        query.setParameter("status", Constants.DEPARTMENT_ACTIVE_STATUS);
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
        return dtos;
    }

    private void setParameterFindAllDepartmentByCodeAndVisible(FindAllDepartmentVisibleRequest request, Query query) {
        query.setParameter("status", Constants.DEPARTMENT_ACTIVE_STATUS);
        query.setParameter("idsDepartment", request.getIdDepartmentOriginal());
        if (StringUtils.isNotBlank(request.getKeyword())) {
            query.setParameter("keyword", request.getKeyword());
        }
    }

    @Override
    public Page<FindAllDepartmentSDto> findAllDepartment(Pageable pageable, FindAllDepartmentRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("WITH RECURSIVE cte_asset_categories as (        " +
                "      select department.id_department,department.name,        " +
                "             department.code, department.short_name,        " +
                "             department.description, department.parent,        " +
                "              department.time_created,department.status,        " +
                "             department.time_modified,        " +
                "             1 as depth,   CAST(department.id_department as NCHAR ) as path,  " +
                "             case when department.parent is not null then department.name end nameParent  " +
                "      from department        " +
                "      where department.parent is null        " +
                "      union all        " +
                "      select department.id_department,department.name,        " +
                "             department.code, department.short_name,        " +
                "             department.description, department.parent,        " +
                "             department.time_created,department.status,        " +
                "             department.time_modified,        " +
                "             cte.depth + 1 as depth,        " +
                "             concat_ws('/',cte.path,CAST(department.id_department as NCHAR)) as path,  " +
                "             cte.name nameParent  " +
                "                 from department     " +
                "               INNER JOIN cte_asset_categories cte ON department.parent = cte.id_department        " +
                "  )        " +
                "select cte.id_department, cte.name,  " +
                "cte.code, cte.short_name, cte.description,  " +
                "cte.parent,  " +
                "cte.time_created, cte.time_modified,  " +
                "cte.depth, cte.status, cte.path, cte.nameParent  " +
                "from cte_asset_categories cte  " +
                "where 1 = 1 and cte.id_department in (:idsDepartment) ");
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
                dto.setNameParent(ValueUtil.getStringByObject(obj[11]));
                dtos.add(dto);
            }
        }
        return new PageImpl<>(dtos, pageable, countFindAllDepartment(request));
    }

    @Override
    public Page<FindAllDepartmentByCodeAndVisibleDto>
    findAllDepartmentSource(Pageable pageable, FindAllDepartmentVisibleRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("WITH RECURSIVE cte_department as (    " +
                "      select department.id_department,department.name,    " +
                "             department.code, department.short_name,    " +
                "             department.description, department.parent,    " +
                "              department.time_created,department.status,    " +
                "             department.time_modified,    " +
                "             1 as depth,   CAST(department.id_department as NCHAR ) as path    " +
                "      from department    " +
                "      where department.parent is null    " +
                "      union all    " +
                "      select department.id_department,department.name,    " +
                "             department.code, department.short_name,    " +
                "             department.description, department.parent,    " +
                "             department.time_created,department.status,    " +
                "             department.time_modified,    " +
                "             cte.depth + 1 as depth,    " +
                "  concat_ws('/',cte.path,CAST(department.id_department as NCHAR)) as path    " +
                "  from department   " +
                "               INNER JOIN cte_department cte ON department.parent = cte.id_department    " +
                "  )    " +
                "  select cte.id_department, cte.name,     " +
                "         cte.code, cte.short_name, cte.description,     " +
                "         cte.parent,     " +
                "          cte.time_created, cte.time_modified,     " +
                "         cte.depth, cte.status, cte.path     " +
                "  from cte_department cte     " +
                "  where 1 = 1 and cte.status = :status ");
        setConditionFindAllDepartmentSource(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllDepartmentSource(request, query);
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
        return new PageImpl<>(dtos, pageable, countFindAllDepartmentSource(request));
    }

    private long countFindAllDepartmentSource(FindAllDepartmentVisibleRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("WITH RECURSIVE cte_department as (      " +
                "       select department.id_department,department.name,      " +
                "          department.code, department.short_name,      " +
                "          department.description, department.parent,      " +
                "       department.time_created,department.status,      " +
                "          department.time_modified,      " +
                "          1 as depth,   CAST(department.id_department as NCHAR ) as path      " +
                "       from department      " +
                "       where department.parent is null      " +
                "       union all      " +
                "       select department.id_department,department.name,      " +
                "          department.code, department.short_name,      " +
                "          department.description, department.parent,      " +
                "          department.time_created,department.status,      " +
                "          department.time_modified,      " +
                "          cte.depth + 1 as depth,      " +
                "       concat_ws('/',cte.path,CAST(department.id_department as NCHAR)) as path      " +
                "       from department     " +
                "        INNER JOIN cte_department cte ON department.parent = cte.id_department      " +
                "       )      " +
                "       select count(cte.id_department) count      " +
                "       from cte_department cte       " +
                "   where 1 = 1 and cte.status = :status ");
        setConditionFindAllDepartmentSource(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllDepartmentSource(request, query);
        return  ValueUtil.getLongByObject(query.getSingleResult());
    }

    private void setParameterFindAllDepartmentSource(FindAllDepartmentVisibleRequest request, Query query) {
        query.setParameter("status", Constants.DEPARTMENT_ACTIVE_STATUS);
        if (StringUtils.isNotBlank(request.getKeyword())) {
            query.setParameter("keyword", request.getKeyword());
        }
    }

    private void setConditionFindAllDepartmentSource(FindAllDepartmentVisibleRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getKeyword())){
            sb.append(" and (cte.name REGEXP :keyword ) ");
        }
        sb.append(" ORDER BY path ");
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
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
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

    @Override
    public boolean isExitsAssetByIdDepartment(Integer idDepartment) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select asset.id_asset " +
                "from department de " +
                "    inner join asset asset on de.id_department = asset.id_department " +
                "where de.id_department = :idDepartment ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idDepartment", idDepartment);
        return !CollectionUtils.isEmpty(query.getResultList());
    }

    @Override
    public List<Department> findDepartmentByIds(List<Integer> ids) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select de.id_department, de.name, de.code, " +
                "       de.short_name, de.description, de.parent, " +
                "       de.time_created, de.time_modified, de.status " +
                "from department de  " +
                "where de.id_department in :ids ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("ids", ids);
        List<Department> departments = new ArrayList<>();
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result){
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
                departments.add(department);
            }
        }
        return departments;
    }

    @Override
    public Map<String, List<FindAllLocationDto>>
     findAllDepartmentLocationToDownloadByIdsDepartment(List<Integer> idsDepartment) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select de.id_department, de.name nameDepartment,     " +
                "         lo.id_location, lo.name nameLocation     " +
                "  from department de     " +
                "      left join (select * from location where location.visible = :visibleLocation)lo  " +
                "          on de.id_department = lo.id_department  " +
                "  where de.id_department in (:idDepartments)     " +
                "  and de.status = :statusDepartment ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("statusDepartment", Constants.DEPARTMENT_ACTIVE_STATUS);
        query.setParameter("visibleLocation", Constants.LOCATION_ACTIVE_STATUS);
        query.setParameter("idDepartments", idsDepartment);
        List<Object[]> result = query.getResultList();
        Map<String,List<FindAllLocationDto>> responses = new HashMap<>();
        if (!CollectionUtils.isEmpty(result)){
            String keyword = null;
            Integer idDepartment = null;
            String nameDepartment = null;
            for (Object[] obj : result){
                idDepartment = ValueUtil.getIntegerByObject(obj[0]);
                nameDepartment = ValueUtil.getStringByObject(obj[1]);
                keyword = "STT_" + idDepartment + "_" + nameDepartment;
                keyword = ValueUtil.convertToVietnamese(keyword).replaceAll(ValueUtil.REGEX_letter_digit_period_underscore, "");
                if (responses.containsKey(keyword)){
                    FindAllLocationDto findAllLocationDto = new FindAllLocationDto();
                    findAllLocationDto.setIdLocation(ValueUtil.getIntegerByObject(obj[2]));
                    findAllLocationDto.setName(ValueUtil.getStringByObject(obj[3]));
                    responses.get(keyword).add(findAllLocationDto);
                } else {
                    List<FindAllLocationDto> findAllLocationDtos = new ArrayList<>();
                    if (ValueUtil.getIntegerByObject(obj[2]) != null) {
                        FindAllLocationDto findAllLocationDto = new FindAllLocationDto();
                        findAllLocationDto.setIdLocation(ValueUtil.getIntegerByObject(obj[2]));
                        findAllLocationDto.setName(ValueUtil.getStringByObject(obj[3]));
                        findAllLocationDtos.add(findAllLocationDto);
                    }
                    responses.put(keyword, findAllLocationDtos);
                }
            }
        }
        return responses;
    }

    @Override
    public List<FindAllDepartmentSDto> findAllAssetDepartmentToDownload() {
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
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("status", Constants.DEPARTMENT_ACTIVE_STATUS);
        List<Object[]> result = query.getResultList();
        List<FindAllDepartmentSDto> dtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj: result){
                FindAllDepartmentSDto dto= new FindAllDepartmentSDto();
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
        return dtos;
    }


    private void setParameterFindAllDepartment(FindAllDepartmentRequest request, Query query) {
        query.setParameter("idsDepartment", request.getIdsDepartment());
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
            sb.append(" and (cte.name REGEXP :keyword ) ");
        }
        if (ObjectUtils.isNotEmpty(request.getStatus())){
            sb.append(" and cte.status = :status ");
        }
        if (StringUtils.isNotBlank(request.getShortName())){
            sb.append(" and (cte.code REGEXP :codeName ) ");
        }
        if (StringUtils.isNotBlank(request.getShortName())){
            sb.append(" and (cte.short_name REGEXP :shortName ) ");
        }
        sb.append(" order by cte.path ");
    }

    private void setConditionFindAllDepartmentByCodeAndVisible(FindAllDepartmentVisibleRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getKeyword())){
            sb.append(" and (cte.name REGEXP :keyword ) ");
        }
        sb.append(" ORDER BY path ");
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
                "where 1 = 1 and cte.status = :status and cte.id_department in (:idsDepartment)  ");
        setConditionFindAllDepartmentByCodeAndVisible(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllDepartmentByCodeAndVisible(request, query);
        return  ValueUtil.getLongByObject(query.getSingleResult());
    }

    private long countFindAllDepartment(FindAllDepartmentRequest request){
        StringBuilder sb = new StringBuilder();
        sb.append(" WITH RECURSIVE cte_asset_categories as (        " +
                "      select department.id_department,department.name,        " +
                "             department.code, department.short_name,        " +
                "             department.description, department.parent,        " +
                "              department.time_created,department.status,        " +
                "             department.time_modified,        " +
                "             1 as depth,   CAST(department.id_department as NCHAR ) as path,  " +
                "             case when department.parent is not null then department.name end nameParent  " +
                "      from department        " +
                "      where department.parent is null        " +
                "      union all        " +
                "      select department.id_department,department.name,        " +
                "             department.code, department.short_name,        " +
                "             department.description, department.parent,        " +
                "             department.time_created,department.status,        " +
                "             department.time_modified,        " +
                "             cte.depth + 1 as depth,        " +
                "             concat_ws('/',cte.path,CAST(department.id_department as NCHAR)) as path,  " +
                "             cte.name nameParent  " +
                "                 from department     " +
                "               INNER JOIN cte_asset_categories cte ON department.parent = cte.id_department        " +
                "  )        " +
                "select count(cte.id_department) count  " +
                "from cte_asset_categories cte  " +
                "where 1 = 1 and cte.id_department in (:idsDepartment)  ");
        setConditionFindAllDepartment(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllDepartment(request, query);
        return  ValueUtil.getLongByObject(query.getSingleResult());
    }
}
