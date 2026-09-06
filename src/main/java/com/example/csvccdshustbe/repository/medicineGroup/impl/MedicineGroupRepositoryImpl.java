package com.example.csvccdshustbe.repository.medicineGroup.impl;

import com.example.csvccdshustbe.dto.modules.medicineModules.medicineGroup.MedicineGroupDetailsDto;
import com.example.csvccdshustbe.entity.MedicineGroup;
import com.example.csvccdshustbe.repository.medicineGroup.MedicineGroupRepositoryCustom;
import com.example.csvccdshustbe.request.medicineGroup.FindAllMedicineGroupRequest;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.PageUtils;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MedicineGroupRepositoryImpl implements MedicineGroupRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public Page<MedicineGroup> findAllMedicineGroupActive(FindAllMedicineGroupRequest request, Pageable  pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select medicineGroup.id_medicine_group, medicineGroup.name, " +
                "       medicineGroup.short_name, medicineGroup.description, " +
                "       medicineGroup.status, medicineGroup.time_created, " +
                "       medicineGroup.time_modified " +
                "from medicine_group medicineGroup " +
                "where medicineGroup.status = :status ");
        setConditionFindAllMedicineGroupActive(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllMedicineGroupActive(request,query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<MedicineGroup> medicineGroups = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                MedicineGroup group = new MedicineGroup();
                group.setIdMedicineGroup(ValueUtil.getIntegerByObject(obj[0]));
                group.setName(ValueUtil.getStringByObject(obj[1]));
                group.setShortName(ValueUtil.getStringByObject(obj[2]));
                group.setDescription(ValueUtil.getStringByObject(obj[3]));
                group.setStatus(ValueUtil.getIntegerByObject(obj[4]));
                group.setTimeCreated(ValueUtil.getStringByObject(obj[5]));
                group.setTimeModified(ValueUtil.getStringByObject(obj[6]));
                medicineGroups.add(group);
            }
        }
        return new PageImpl<>(medicineGroups, pageable, countFindAllMedicineGroupActive(request));
    }


    private long countFindAllMedicineGroupActive(FindAllMedicineGroupRequest request){
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0) " +
                "from medicine_group medicineGroup " +
                "where medicineGroup.status = :status ");
        setConditionFindAllMedicineGroupActive(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllMedicineGroupActive(request, query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }

    private void setParameterFindAllMedicineGroupActive(FindAllMedicineGroupRequest request, Query query) {
        query.setParameter("status", Constants.MEDICINE_GROUP_ACTIVE_STATUS);
        if (StringUtils.isNotBlank(request.getKeyword())){
            query.setParameter("keyword", request.getKeyword());
        }
    }

    private void setConditionFindAllMedicineGroupActive(FindAllMedicineGroupRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getKeyword())){
            sb.append(" and (medicineGroup.name REGEXP :keyword ) ");
        }
    }

    @Override
    public Optional<MedicineGroup> findMedicineGroupByName(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select mg.id_medicine_group, mg.name, " +
                "mg.short_name, mg.description, " +
                "mg.time_created, mg.time_modified, mg.status " +
                "from medicine_group mg " +
                "where mg.name = :name ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("name", name);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                MedicineGroup medicineGroup = new MedicineGroup();
                medicineGroup.setIdMedicineGroup(ValueUtil.getIntegerByObject(obj[0]));
                medicineGroup.setName(ValueUtil.getStringByObject(obj[1]));
                medicineGroup.setShortName(ValueUtil.getStringByObject(obj[2]));
                medicineGroup.setDescription(ValueUtil.getStringByObject(obj[3]));
                medicineGroup.setTimeCreated(ValueUtil.getStringByObject(obj[4]));
                medicineGroup.setTimeModified(ValueUtil.getStringByObject(obj[5]));
                medicineGroup.setStatus(ValueUtil.getIntegerByObject(obj[6]));
                return Optional.of(medicineGroup);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<MedicineGroup> findMedicineGroupById(Integer idMedicineType) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select mg.id_medicine_group, mg.name, " +
                "mg.short_name, mg.description, " +
                "mg.time_created, mg.time_modified, mg.status " +
                "from medicine_group mg " +
                "where mg.id_medicine_group = :idMedicineType ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idMedicineType", idMedicineType);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                MedicineGroup medicineGroup = new MedicineGroup();
                medicineGroup.setIdMedicineGroup(ValueUtil.getIntegerByObject(obj[0]));
                medicineGroup.setName(ValueUtil.getStringByObject(obj[1]));
                medicineGroup.setShortName(ValueUtil.getStringByObject(obj[2]));
                medicineGroup.setDescription(ValueUtil.getStringByObject(obj[3]));
                medicineGroup.setTimeCreated(ValueUtil.getStringByObject(obj[4]));
                medicineGroup.setTimeModified(ValueUtil.getStringByObject(obj[5]));
                medicineGroup.setStatus(ValueUtil.getIntegerByObject(obj[6]));
                return Optional.of(medicineGroup);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<MedicineGroupDetailsDto> findAllMedicineGroupToDownload() {
        StringBuilder sb = new StringBuilder();
        sb.append(" select medicineGroup.id_medicine_group, medicineGroup.name " +
                "   from medicine_group medicineGroup    " +
                "   where medicineGroup.status = :status  ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("status", Constants.MEDICINE_GROUP_ACTIVE_STATUS);
        List<Object[]> result = query.getResultList();
        List<MedicineGroupDetailsDto> responses = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                MedicineGroupDetailsDto dto = new MedicineGroupDetailsDto();
                dto.setIdMedicineGroup(ValueUtil.getIntegerByObject(obj[0]));
                dto.setName(ValueUtil.getStringByObject(obj[1]));
                responses.add(dto);
            }
        }
        return responses;
    }

    @Override
    public List<MedicineGroup> findMedicineGroupByAllId(List<Integer> idMedicineType) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select mg.id_medicine_group, mg.name, " +
                "mg.short_name, mg.description, " +
                "mg.time_created, mg.time_modified, mg.status " +
                "from medicine_group mg " +
                "where mg.id_medicine_group in :idMedicineType ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idMedicineType", idMedicineType);
        List<Object[]> result = query.getResultList();
        List<MedicineGroup> medicineGroupList= new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                MedicineGroup medicineGroup = new MedicineGroup();
                medicineGroup.setIdMedicineGroup(ValueUtil.getIntegerByObject(obj[0]));
                medicineGroup.setName(ValueUtil.getStringByObject(obj[1]));
                medicineGroup.setShortName(ValueUtil.getStringByObject(obj[2]));
                medicineGroup.setDescription(ValueUtil.getStringByObject(obj[3]));
                medicineGroup.setTimeCreated(ValueUtil.getStringByObject(obj[4]));
                medicineGroup.setTimeModified(ValueUtil.getStringByObject(obj[5]));
                medicineGroup.setStatus(ValueUtil.getIntegerByObject(obj[6]));
                medicineGroupList.add(medicineGroup);
            }
        }
        return medicineGroupList;
    }

}
