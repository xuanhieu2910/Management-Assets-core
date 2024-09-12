package com.example.csvccdshustbe.repository.medicineModule.impl;

import com.example.csvccdshustbe.dto.modules.medicineModules.MedicineModuleDetailsDto;
import com.example.csvccdshustbe.repository.medicineModule.MedicineModuleRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

public class MedicineModuleRepositoryImpl implements MedicineModuleRepositoryCustom {


    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<MedicineModuleDetailsDto> findMedicineModuleDetailsDtoById(Integer idMedicineModule) {
        StringBuilder sb = new StringBuilder();
        sb.append("select medicineModule.id_medicine_module, medicineModule.id_asset, medicineModule.id_medicine_type,  " +
                "       medicineModule.id_medicine_group, medicineModule.publish_date, medicineModule.expiry_date,  " +
                "       medicineModule.circulation_number, medicineModule.number_batch_of_goods,  " +
                "       medicineModule.own_name_circulation_number, medicineModule.own_address_circulation_number,  " +
                "       medicineModule.spare_parts_attack, medicineType.name nameMedicineType, medicineGroup.name nameMedicineGroup " +
                "from medicine_module medicineModule " +
                "    left join medicine_type medicineType on medicineModule.id_medicine_type = medicineType.id_medicine_type " +
                "    left join medicine_group medicineGroup on medicineModule.id_medicine_group = medicineGroup.id_medicine_group " +
                "where medicineModule.id_medicine_module = :idMedicineModule ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idMedicineModule", idMedicineModule);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                MedicineModuleDetailsDto medicineModule = new MedicineModuleDetailsDto();
                medicineModule.setIdMedicineModule(ValueUtil.getIntegerByObject(obj[0]));
                medicineModule.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                medicineModule.setIdMedicineType(ValueUtil.getIntegerByObject(obj[2]));
                medicineModule.setIdMedicineGroup(ValueUtil.getIntegerByObject(obj[3]));
                medicineModule.setPublishDate(ValueUtil.getStringByObject(obj[4]));
                medicineModule.setExpiryDate(ValueUtil.getStringByObject(obj[5]));
                medicineModule.setCirculationNumber(ValueUtil.getStringByObject(obj[6]));
                medicineModule.setNumberBatchOfGoods(ValueUtil.getStringByObject(obj[7]));
                medicineModule.setOwnNameCirculationNumber(ValueUtil.getStringByObject(obj[8]));
                medicineModule.setOwnAddressCirculationNumber(ValueUtil.getStringByObject(obj[9]));
                medicineModule.setSparePartsAttack(ValueUtil.getStringByObject(obj[10]));
                medicineModule.setNameMedicineType(ValueUtil.getStringByObject(obj[11]));
                medicineModule.setNameMedicineGroup(ValueUtil.getStringByObject(obj[12]));
                return Optional.of(medicineModule);
            }
        }
        return Optional.empty();
    }

    @Transactional
    @Modifying
    @Override
    public void deleteMedicineModuleById(Integer idMedicineModule) {
        StringBuilder sb = new StringBuilder();
        sb.append(" delete from medicine_module medicineModule " +
                "where medicineModule.id_medicine_module = :idMedicineModule ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idMedicineModule", idMedicineModule);
        query.executeUpdate();
    }
}
