package com.example.csvccdshustbe.repository.medicineType.impl;

import com.example.csvccdshustbe.dto.modules.medicineModules.FindAllMedicineTypeDto;
import com.example.csvccdshustbe.entity.MedicineType;
import com.example.csvccdshustbe.repository.medicineType.MedicineTypeRepositoryCustom;
import com.example.csvccdshustbe.request.medicineType.FindAllMedicineTypeRequest;
import com.example.csvccdshustbe.request.medicineType.FindAllMedicineTypeVisibleRequest;
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
import java.util.Objects;
import java.util.Optional;

public class MedicineTypeRepositoryImpl implements MedicineTypeRepositoryCustom {


    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Page<FindAllMedicineTypeDto> findAllMedicineTypeVisible(FindAllMedicineTypeVisibleRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append(" WITH RECURSIVE cte_medicine_type as (    " +
                "       select medicineType.id_medicine_type, medicineType.name, " +
                "              medicineType.short_name, medicineType.code, " +
                "              medicineType.parent, medicineType.visible, medicineType.notes, " +
                "              medicineType.time_created, medicineType.time_modified, " +
                "              1 as depth,    " +
                "              CAST(medicineType.id_medicine_type as NCHAR ) as path " +
                "       from medicine_type medicineType " +
                "       where medicineType.parent is null " +
                "       union all    " +
                "       select medicineType.id_medicine_type, medicineType.name, " +
                "              medicineType.short_name, medicineType.code, " +
                "              medicineType.parent, medicineType.visible, medicineType.notes, " +
                "              medicineType.time_created, medicineType.time_modified, " +
                "              cte.depth + 1 as depth,    " +
                "              concat_ws('/',cte.path,CAST(medicineType.id_medicine_type as NCHAR)) as path " +
                "       from medicine_type medicineType " +
                "                INNER JOIN cte_medicine_type cte ON medicineType.parent = cte.id_medicine_type " +
                "       )    " +
                "   select cte.id_medicine_type, cte.name, " +
                "          cte.short_name, cte.code, cte.parent, " +
                "          cte.visible, cte.notes, " +
                "          cte.time_created, cte.time_modified, " +
                "          cte.depth, cte.path " +
                "   from cte_medicine_type cte    " +
                "   where 1 = 1 " +
                "   and cte.visible = :visible ");
        setConditionFindAllMedicineTypeVisible(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllMedicineTypeVisible(request, query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<FindAllMedicineTypeDto> findAllMedicineTypeDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                FindAllMedicineTypeDto dto = new FindAllMedicineTypeDto();
                dto.setIdMedicineType(ValueUtil.getIntegerByObject(obj[0]));
                dto.setName(ValueUtil.getStringByObject(obj[1]));
                dto.setShortName(ValueUtil.getStringByObject(obj[2]));
                dto.setCode(ValueUtil.getStringByObject(obj[3]));
                dto.setParent(ValueUtil.getIntegerByObject(obj[4]));
                dto.setVisible(ValueUtil.getIntegerByObject(obj[5]));
                dto.setNotes(ValueUtil.getStringByObject(obj[6]));
                dto.setTimeCreated(ValueUtil.getStringByObject(obj[7]));
                dto.setTimeModified(ValueUtil.getStringByObject(obj[8]));
                dto.setDepth(ValueUtil.getIntegerByObject(obj[9]));
                dto.setPath(ValueUtil.getStringByObject(obj[10]));
                findAllMedicineTypeDtos.add(dto);
            }
        }
        return new PageImpl<>(findAllMedicineTypeDtos, pageable, countFindAllMedicineTypeVisible(request));
    }

    @Override
    public Page<FindAllMedicineTypeDto> findAllMedicineType(FindAllMedicineTypeRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append(" WITH RECURSIVE cte_medicine_type as (          " +
                "        select medicineType.id_medicine_type, medicineType.name,       " +
                "               medicineType.short_name, medicineType.code,       " +
                "               medicineType.parent, medicineType.visible, medicineType.notes,       " +
                "               medicineType.time_created, medicineType.time_modified,       " +
                "               1 as depth,          " +
                "               CAST(medicineType.id_medicine_type as NCHAR ) as path,   " +
                "               case when medicineType.parent is not null then medicineType.name end nameParent   " +
                "        from medicine_type medicineType       " +
                "        where medicineType.parent is null       " +
                "        union all          " +
                "        select medicineType.id_medicine_type, medicineType.name,       " +
                "               medicineType.short_name, medicineType.code,       " +
                "               medicineType.parent, medicineType.visible, medicineType.notes,       " +
                "               medicineType.time_created, medicineType.time_modified,       " +
                "               cte.depth + 1 as depth,          " +
                "               concat_ws('/',cte.path,CAST(medicineType.id_medicine_type as NCHAR)) as path,   " +
                "               cte.name nameParent   " +
                "        from medicine_type medicineType       " +
                "                 INNER JOIN cte_medicine_type cte ON medicineType.parent = cte.id_medicine_type       " +
                "        )          " +
                "    select cte.id_medicine_type, cte.name,       " +
                "           cte.short_name, cte.code, cte.parent,       " +
                "           cte.visible, cte.notes,       " +
                "           cte.time_created, cte.time_modified,       " +
                "           cte.depth, cte.path, cte.nameParent   " +
                "from cte_medicine_type cte   " +
                "where 1 = 1    ");
        setConditionFindAllMedicineType(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllMedicineType(request, query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<FindAllMedicineTypeDto> findAllMedicineTypeDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                FindAllMedicineTypeDto dto = new FindAllMedicineTypeDto();
                dto.setIdMedicineType(ValueUtil.getIntegerByObject(obj[0]));
                dto.setName(ValueUtil.getStringByObject(obj[1]));
                dto.setShortName(ValueUtil.getStringByObject(obj[2]));
                dto.setCode(ValueUtil.getStringByObject(obj[3]));
                dto.setParent(ValueUtil.getIntegerByObject(obj[4]));
                dto.setVisible(ValueUtil.getIntegerByObject(obj[5]));
                dto.setNotes(ValueUtil.getStringByObject(obj[6]));
                dto.setTimeCreated(ValueUtil.getStringByObject(obj[7]));
                dto.setTimeModified(ValueUtil.getStringByObject(obj[8]));
                dto.setDepth(ValueUtil.getIntegerByObject(obj[9]));
                dto.setPath(ValueUtil.getStringByObject(obj[10]));
                dto.setNameParent(ValueUtil.getStringByObject(obj[11]));
                findAllMedicineTypeDtos.add(dto);
            }
        }
        return new PageImpl<>(findAllMedicineTypeDtos, pageable, countFindAllMedicineType(request));
    }

    private void setParameterFindAllMedicineTypeVisible(FindAllMedicineTypeVisibleRequest request, Query query) {
        query.setParameter("visible", Constants.MEDICINE_TYPE_IS_VISIBLE);
        if (StringUtils.isNotBlank(request.getKeyword())){
            query.setParameter("keyword", request.getKeyword());
        }
    }

    private void setParameterFindAllMedicineType(FindAllMedicineTypeRequest request, Query query) {
        if (StringUtils.isNotBlank(request.getKeyword())){
            query.setParameter("keyword", request.getKeyword());
        }
        if (!Objects.isNull(request.getStatus())){
            query.setParameter("visible", request.getStatus());
        }
    }

    private void setConditionFindAllMedicineTypeVisible(FindAllMedicineTypeVisibleRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getKeyword())) {
            sb.append(" and (cte.name REGEXP  :keyword )  ");
        }
        sb.append(" ORDER BY path ");
    }

    private void setConditionFindAllMedicineType(FindAllMedicineTypeRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getKeyword())) {
            sb.append(" and (cte.name REGEXP  :keyword )  ");
        }
        if (!Objects.isNull(request.getStatus())) {
            sb.append(" and cte.visible = :visible ");
        }
        sb.append(" ORDER BY path ");
    }

    private long countFindAllMedicineTypeVisible(FindAllMedicineTypeVisibleRequest request){
        StringBuilder sb = new StringBuilder();
        sb.append(" WITH RECURSIVE cte_medicine_type as (  " +
                "       select medicineType.id_medicine_type, medicineType.name,  " +
                "              medicineType.short_name, medicineType.code,  " +
                "              medicineType.parent, medicineType.visible, medicineType.notes,  " +
                "              medicineType.time_created, medicineType.time_modified,  " +
                "              1 as depth,     " +
                "              CAST(medicineType.id_medicine_type as NCHAR ) as path  " +
                "       from medicine_type medicineType  " +
                "       where medicineType.parent is null  " +
                "       union all     " +
                "       select medicineType.id_medicine_type, medicineType.name,  " +
                "              medicineType.short_name, medicineType.code,  " +
                "              medicineType.parent, medicineType.visible, medicineType.notes,  " +
                "              medicineType.time_created, medicineType.time_modified,  " +
                "              cte.depth + 1 as depth,     " +
                "              concat_ws('/',cte.path,CAST(medicineType.id_medicine_type as NCHAR)) as path  " +
                "       from medicine_type medicineType  " +
                "                INNER JOIN cte_medicine_type cte ON medicineType.parent = cte.id_medicine_type  " +
                "       )     " +
                "   select count(cte.id_medicine_type) count  " +
                "   from cte_medicine_type cte  " +
                "   where 1 = 1  " +
                "   and cte.visible = :visible ");
        setConditionFindAllMedicineTypeVisible(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllMedicineTypeVisible(request, query);
        return  ValueUtil.getLongByObject(query.getSingleResult());
    }


    private long countFindAllMedicineType(FindAllMedicineTypeRequest request){
        StringBuilder sb = new StringBuilder();
        sb.append("  WITH RECURSIVE cte_medicine_type as (          " +
                "        select medicineType.id_medicine_type, medicineType.name,       " +
                "               medicineType.short_name, medicineType.code,       " +
                "               medicineType.parent, medicineType.visible, medicineType.notes,       " +
                "               medicineType.time_created, medicineType.time_modified,       " +
                "               1 as depth,          " +
                "               CAST(medicineType.id_medicine_type as NCHAR ) as path,    " +
                "               case when medicineType.parent is not null then medicineType.name end nameParent    " +
                "        from medicine_type medicineType       " +
                "        where medicineType.parent is null       " +
                "        union all          " +
                "        select medicineType.id_medicine_type, medicineType.name,       " +
                "               medicineType.short_name, medicineType.code,       " +
                "               medicineType.parent, medicineType.visible, medicineType.notes,       " +
                "               medicineType.time_created, medicineType.time_modified,       " +
                "               cte.depth + 1 as depth,          " +
                "               concat_ws('/',cte.path,CAST(medicineType.id_medicine_type as NCHAR)) as path,    " +
                "               cte.name nameParent " +
                "        from medicine_type medicineType       " +
                "                 INNER JOIN cte_medicine_type cte ON medicineType.parent = cte.id_medicine_type       " +
                "        )          " +
                "    select count(0) count    " +
                "from cte_medicine_type cte    " +
                "where 1 = 1     ");
        setConditionFindAllMedicineType(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllMedicineType(request, query);
        return  ValueUtil.getLongByObject(query.getSingleResult());
    }


    @Override
    public Optional<MedicineType> findMedicineTypeByName(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select mt.id_medicine_type, mt.name, mt.code, " +
                "       mt.short_name, mt.notes, mt.parent, " +
                "       mt.time_created, mt.time_modified, mt.visible " +
                "from medicine_type mt " +
                "where mt.name = :name ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("name", name);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                MedicineType medicineType = new MedicineType();
                medicineType.setIdMedicineType(ValueUtil.getIntegerByObject(obj[0]));
                medicineType.setName(ValueUtil.getStringByObject(obj[1]));
                medicineType.setCode(ValueUtil.getStringByObject(obj[2]));
                medicineType.setShortName(ValueUtil.getStringByObject(obj[3]));
                medicineType.setNotes(ValueUtil.getStringByObject(obj[4]));
                medicineType.setParent(ValueUtil.getIntegerByObject(obj[5]));
                medicineType.setTimeCreated(ValueUtil.getStringByObject(obj[6]));
                medicineType.setTimeModified(ValueUtil.getStringByObject(obj[7]));
                medicineType.setVisible(ValueUtil.getIntegerByObject(obj[8]));
                return Optional.of(medicineType);
            }
        }
        return Optional.empty();
    }
    @Override
    public Optional<MedicineType> findMedicineTypeByIdParent(Integer idParent) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select mt.id_medicine_type, mt.name, mt.code, " +
                "       mt.short_name, mt.notes, mt.parent, " +
                "       mt.time_created, mt.time_modified, mt.visible " +
                "from medicine_type mt " +
                "where mt.id_medicine_type = :idParent ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idParent", idParent);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                MedicineType medicineType = new MedicineType();
                medicineType.setIdMedicineType(ValueUtil.getIntegerByObject(obj[0]));
                medicineType.setName(ValueUtil.getStringByObject(obj[1]));
                medicineType.setCode(ValueUtil.getStringByObject(obj[2]));
                medicineType.setShortName(ValueUtil.getStringByObject(obj[3]));
                medicineType.setNotes(ValueUtil.getStringByObject(obj[4]));
                medicineType.setParent(ValueUtil.getIntegerByObject(obj[5]));
                medicineType.setTimeCreated(ValueUtil.getStringByObject(obj[6]));
                medicineType.setTimeModified(ValueUtil.getStringByObject(obj[7]));
                medicineType.setVisible(ValueUtil.getIntegerByObject(obj[8]));
                return Optional.of(medicineType);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<MedicineType> findMedicineTypeById(Integer idMedicineType) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select mt.id_medicine_type, mt.name, mt.code, " +
                "       mt.short_name, mt.notes, mt.parent, " +
                "       mt.time_created, mt.time_modified, mt.visible " +
                "from medicine_type mt " +
                "where mt.id_medicine_type = :idMedicineType ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idMedicineType", idMedicineType);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                MedicineType medicineType = new MedicineType();
                medicineType.setIdMedicineType(ValueUtil.getIntegerByObject(obj[0]));
                medicineType.setName(ValueUtil.getStringByObject(obj[1]));
                medicineType.setCode(ValueUtil.getStringByObject(obj[2]));
                medicineType.setShortName(ValueUtil.getStringByObject(obj[3]));
                medicineType.setNotes(ValueUtil.getStringByObject(obj[4]));
                medicineType.setParent(ValueUtil.getIntegerByObject(obj[5]));
                medicineType.setTimeCreated(ValueUtil.getStringByObject(obj[6]));
                medicineType.setTimeModified(ValueUtil.getStringByObject(obj[7]));
                medicineType.setVisible(ValueUtil.getIntegerByObject(obj[8]));
                return Optional.of(medicineType);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean checkExitsMedicineTypeByNameOrCodeOrShortName(String name, String code, String shortName) {
        StringBuilder sb = new StringBuilder();
        sb.append("select * " +
                "from medicine_type mt " +
                "where 1 = 1 ");
        if (StringUtils.isNotBlank(name)){
            sb.append(" or mt.name = :name ");
        }
        if (StringUtils.isNotBlank(code)){
            sb.append(" or mt.code = :code ");
        }
        if (StringUtils.isNotBlank(shortName)){
            sb.append(" or mt.short_name = :shortName ");
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
}
