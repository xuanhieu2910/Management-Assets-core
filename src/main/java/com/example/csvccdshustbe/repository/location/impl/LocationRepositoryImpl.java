package com.example.csvccdshustbe.repository.location.impl;

import com.example.csvccdshustbe.dto.location.FindAllLocationDto;
import com.example.csvccdshustbe.dto.medicine.FindAllMedicineTypeDto;
import com.example.csvccdshustbe.entity.Location;
import com.example.csvccdshustbe.entity.MedicineType;
import com.example.csvccdshustbe.repository.location.LocationRepositoryCustom;
import com.example.csvccdshustbe.request.Location.FindAllLocationRequest;
import com.example.csvccdshustbe.request.medicineType.FindAllMedicineTypeRequest;
import com.example.csvccdshustbe.utility.Constants;
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
import java.util.Optional;

public class LocationRepositoryImpl implements LocationRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public Page<FindAllLocationDto> findAllLocationVisible( Pageable pageable,FindAllLocationRequest request,Integer idDepartment) {
        StringBuilder sb = new StringBuilder();
        sb.append(" WITH RECURSIVE cte_location as (    " +
                "       select location.id_location, location.name, " +
                "              location.short_name, location.id_department, " +
                "              location.parent, location.visible, " +
                "              location.time_created, location.time_modified, " +
                "              1 as depth,    " +
                "              CAST(location.id_location as NCHAR ) as path " +
                "       from location " +
                "       where location.parent is null " +
                "       union all    " +
                "       select location.id_location, location.name, " +
                "              location.short_name, location.id_department, " +
                "              location.parent, location.visible, " +
                "              location.time_created, location.time_modified, " +
                "              cte.depth + 1 as depth,    " +
                "              concat_ws('/',cte.path,CAST(location.id_location as NCHAR)) as path " +
                "       from location " +
                "                INNER JOIN cte_location cte ON location.parent = cte.id_location " +
                "       )    " +
                "   select cte.id_location, cte.name, " +
                "          cte.short_name, cte.id_department, cte.parent, " +
                "          cte.visible,  " +
                "          cte.time_created, cte.time_modified, " +
                "          cte.depth, cte.path " +
                "   from cte_location cte    " +
                "   where 1 = 1 " +
                "  and  cte.id_department = :idDepartment " +
                "and cte.visible = :visible ");
        setConditionFindAllLocationVisible(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idDepartment", idDepartment);
        setParameterFindAllLocationVisible(request, query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<FindAllLocationDto> findAllLocationDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                FindAllLocationDto dto = new FindAllLocationDto();
                dto.setIdLocation(ValueUtil.getIntegerByObject(obj[0]));
                dto.setName(ValueUtil.getStringByObject(obj[1]));
                dto.setShortName(ValueUtil.getStringByObject(obj[2]));
                dto.setIdDepartment(ValueUtil.getIntegerByObject(obj[3]));
                dto.setParent(ValueUtil.getIntegerByObject(obj[4]));
                dto.setVisible(ValueUtil.getIntegerByObject(obj[5]));
                dto.setTimeCreated(ValueUtil.getStringByObject(obj[6]));
                dto.setTimeModified(ValueUtil.getStringByObject(obj[7]));
                dto.setDepth(ValueUtil.getIntegerByObject(obj[8]));
                dto.setPath(ValueUtil.getStringByObject(obj[9]));
                findAllLocationDtos.add(dto);
            }
        }
        return new PageImpl<>(findAllLocationDtos, pageable, countFindAllLocationVisible(request));
    }


    private void setParameterFindAllLocationVisible(FindAllLocationRequest request, Query query) {
        query.setParameter("visible", Constants.LOCATION_ACTIVE_STATUS);
        if (StringUtils.isNotBlank(request.getKeyword())){
            query.setParameter("keyword", request.getKeyword());
        }
    }

    private void setConditionFindAllLocationVisible(FindAllLocationRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getKeyword())) {
            sb.append(" and (cte.name REGEXP  :keyword )  ");
        }
    }

    private long countFindAllLocationVisible(FindAllLocationRequest request){
        StringBuilder sb = new StringBuilder();
        sb.append(" WITH RECURSIVE cte_location as (  " +
                "       select location.id_location, location.name,  " +
                "              location.short_name, location.id_department,  " +
                "              location.parent, location.visible,   " +
                "              location.time_created, location.time_modified,  " +
                "              1 as depth,     " +
                "              CAST(location.id_location as NCHAR ) as path  " +
                "       from location  " +
                "       where location.parent is null  " +
                "       union all     " +
                "       select Location.id_location, Location.name,  " +
                "              Location.short_name, Location.id_department,  " +
                "              Location.parent, Location.visible,   " +
                "              Location.time_created, Location.time_modified,  " +
                "              cte.depth + 1 as depth,     " +
                "              concat_ws('/',cte.path,CAST(Location.id_location as NCHAR)) as path  " +
                "       from location Location  " +
                "                INNER JOIN cte_location cte ON Location.parent = cte.id_location  " +
                "       )     " +
                "   select count(cte.id_location) count  " +
                "   from cte_location cte  " +
                "   where 1 = 1  " +
                "   and cte.visible = :visible ");
        setConditionFindAllLocationVisible(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllLocationVisible(request, query);
        return  ValueUtil.getLongByObject(query.getSingleResult());
    }

    @Override
    public Optional<Location> findLocationByName(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select location.id_location, location.name, " +
                "       location.short_name, location.parent, location.id_department, " +
                "       location.time_created, location.time_modified, location.visible " +
                "from location " +
                "where location.name = :name ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("name", name);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                Location location = new Location();
                location.setIdLocation(ValueUtil.getIntegerByObject(obj[0]));
                location.setName(ValueUtil.getStringByObject(obj[1]));
                location.setShortName(ValueUtil.getStringByObject(obj[2]));
                location.setParent(ValueUtil.getIntegerByObject(obj[3]));
                location.setIdDepartment(ValueUtil.getIntegerByObject(obj[4]));
                location.setTimeCreated(ValueUtil.getStringByObject(obj[5]));
                location.setTimeModified(ValueUtil.getStringByObject(obj[6]));
                location.setVisible(ValueUtil.getIntegerByObject(obj[7]));
                return Optional.of(location);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Location> findLocationByIdParent(Integer idParent) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select location.id_location, location.name, " +
                "       location.short_name, location.parent, location.id_department, " +
                "       location.time_created, location.time_modified, location.visible " +
                "from location " +
                "where location.id_location = :idParent ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idParent", idParent);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                Location location = new Location();
                location.setIdLocation(ValueUtil.getIntegerByObject(obj[0]));
                location.setName(ValueUtil.getStringByObject(obj[1]));
                location.setShortName(ValueUtil.getStringByObject(obj[2]));
                location.setParent(ValueUtil.getIntegerByObject(obj[3]));
                location.setIdDepartment(ValueUtil.getIntegerByObject(obj[4]));
                location.setTimeCreated(ValueUtil.getStringByObject(obj[5]));
                location.setTimeModified(ValueUtil.getStringByObject(obj[6]));
                location.setVisible(ValueUtil.getIntegerByObject(obj[7]));
                return Optional.of(location);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Location> findLocationById(Integer idLocation) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select location.id_location, location.name, " +
                "       location.short_name, location.parent, location.id_department, " +
                "       location.time_created, location.time_modified, location.visible " +
                "from location " +
                "where location.id_location = :idLocation ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idLocation", idLocation);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                Location location = new Location();
                location.setIdLocation(ValueUtil.getIntegerByObject(obj[0]));
                location.setName(ValueUtil.getStringByObject(obj[1]));
                location.setShortName(ValueUtil.getStringByObject(obj[2]));
                location.setParent(ValueUtil.getIntegerByObject(obj[3]));
                location.setIdDepartment(ValueUtil.getIntegerByObject(obj[4]));
                location.setTimeCreated(ValueUtil.getStringByObject(obj[5]));
                location.setTimeModified(ValueUtil.getStringByObject(obj[6]));
                location.setVisible(ValueUtil.getIntegerByObject(obj[7]));
                return Optional.of(location);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean checkExitsLocationByNameOrShortName(String name, String shortName) {
        StringBuilder sb = new StringBuilder();
        sb.append("select * " +
                "from location " +
                "where 1 = 1 ");
        if (StringUtils.isNotBlank(name)){
            sb.append(" or location.name = :name ");
        }
        if (StringUtils.isNotBlank(shortName)){
            sb.append(" or location.short_name = :shortName ");
        }
        Query query = entityManager.createNativeQuery(sb.toString());
        if (StringUtils.isNotBlank(name)){
            query.setParameter("name", name);
        }
        if (StringUtils.isNotBlank(shortName)){
            query.setParameter("shortName", shortName);
        }
        List<Object[]> result = query.getResultList();
        return CollectionUtils.isEmpty(result);
    }
}
