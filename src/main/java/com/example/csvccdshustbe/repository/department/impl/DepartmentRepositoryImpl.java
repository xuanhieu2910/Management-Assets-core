package com.example.csvccdshustbe.repository.department.impl;

import com.example.csvccdshustbe.dto.department.FindAllDepartmentByCodeAndVisibleDto;
import com.example.csvccdshustbe.repository.department.DepartmentRepositoryCustom;
import com.example.csvccdshustbe.request.department.FindAllDepartmentVisibleRequest;
import com.example.csvccdshustbe.utility.PageUtils;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

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
                "where 1 = 1 ");
        setConditionFindAllDepartmentByCodeAndVisible(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
//        setParameterFindAllDepartmentByCodeAndVisible(request,query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<FindAllDepartmentByCodeAndVisibleDto> dtos=new ArrayList<>();
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

//    private void setParameterFindAllDepartmentByCodeAndVisible(FindAllDepartmentRequest request, Query query) {
//        if (StringUtils.isNotBlank(request.getName())) {
//            query.setParameter("name", request.getName().trim());
//        }
//        if (StringUtils.isNotBlank(request.getKeyword())) {
//            query.setParameter("keyword", request.getKeyword());
//        }
//    }
    private void setConditionFindAllDepartmentByCodeAndVisible(FindAllDepartmentVisibleRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getKeyword())){
            sb.append(" and (cte.name REGEXP '[' + :keyword + ']') ");
        }
    }
    private long countFindAllDepartmentByCodeAndVisible(FindAllDepartmentVisibleRequest request){
        StringBuilder sb = new StringBuilder();
        sb.append("WITH RECURSIVE cte_department as ( " +
                "                 select department.id_department,department.name, " +
                "                        department.code, department.short_name, " +
                "                        department.description, department.parent, " +
                "                         department.time_created, " +
                "                        department.time_modified, " +
                "                          1 as depth, " +
                "                           CAST(department.id_department as NCHAR ) as path " +
                "                   from department " +
                "                where department.parent is null " +
                "                   union all " +
                "                   select department.id_department,department.name, " +
                "                          department.code, department.short_name, " +
                "                          department.description, department.parent, " +
                "                           department.time_created, " +
                "                          department.time_modified, " +
                "                           cte.depth + 1 as depth, " +
                "                          concat_ws('/',cte.path,CAST(department.id_department as NCHAR)) as path " +
                "                   from department " +
                "                            INNER JOIN cte_department cte ON department.parent = cte.id_department " +
                "                   ) " +
                "                select count(cte.id_department) count " +
                "                from cte_department cte " +
                "                where 1 = 1");
        setConditionFindAllDepartmentByCodeAndVisible(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
//        setParameterFindAllDepartmentByCodeAndVisible(request, query);
        return  ValueUtil.getLongByObject(query.getSingleResult());
    }
}
