package com.example.csvccdshustbe.repository.medicine.impl;

import com.example.csvccdshustbe.dto.medicine.FindAllMedicineTypeDto;
import com.example.csvccdshustbe.repository.medicine.MedicineTypeRepositoryCustom;
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

public class MedicineTypeRepositoryImpl implements MedicineTypeRepositoryCustom {


    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Page<FindAllMedicineTypeDto> findAllMedicineTypeVisible(FindAllMedicineTypeRequest request, Pageable pageable) {
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

    private void setParameterFindAllMedicineTypeVisible(FindAllMedicineTypeRequest request, Query query) {
        query.setParameter("visible", Constants.MEDICINE_TYPE_IS_VISIBLE);
        if (StringUtils.isNotBlank(request.getKeyword())){
            query.setParameter("keyword", request.getKeyword());
        }
    }

    private void setConditionFindAllMedicineTypeVisible(FindAllMedicineTypeRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getKeyword())) {
            sb.append(" and (cte.name REGEXP '[' + :keyword + ']')  ");
        }
    }

    private long countFindAllMedicineTypeVisible(FindAllMedicineTypeRequest request){
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
}
