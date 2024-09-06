package com.example.csvccdshustbe.repository.medicineGroup.impl;

import com.example.csvccdshustbe.entity.MedicineGroup;
import com.example.csvccdshustbe.repository.medicineGroup.MedicineGroupRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MedicineGroupRepositoryImpl implements MedicineGroupRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public List<MedicineGroup> findAllMedicineGroupByStatus(Integer status) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select medicineGroup.id_medicine_group, medicineGroup.name, " +
                "       medicineGroup.short_name, medicineGroup.description, " +
                "       medicineGroup.status, medicineGroup.time_created, " +
                "       medicineGroup.time_modified " +
                "from medicine_group medicineGroup " +
                "where medicineGroup.status = :status ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("status", status);
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
        return medicineGroups;
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
}
