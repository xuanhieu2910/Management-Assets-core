package com.example.csvccdshustbe.repository.medicineModule.impl;

import com.example.csvccdshustbe.entity.MedicineModule;
import com.example.csvccdshustbe.repository.medicineModule.MedicineModuleRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

public class MedicineModuleRepositoryImpl implements MedicineModuleRepositoryCustom {


    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<MedicineModule> findMedicineModuleById(Integer idMedicineModule) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select medicineModule.id_medicine_module, medicineModule.id_asset, medicineModule.id_medicine_type, " +
                "       medicineModule.id_medicine_group, medicineModule.publish_date, medicineModule.expiry_date, " +
                "       medicineModule.circulation_number, medicineModule.number_batch_of_goods, " +
                "       medicineModule.own_name_circulation_number, medicineModule.own_address_circulation_number, " +
                "       medicineModule.spare_parts_attack " +
                "from medicine_module medicineModule " +
                "where medicineModule.id_medicine_module = :idMedicineModule ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idMedicineModule", idMedicineModule);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                MedicineModule medicineModule = new MedicineModule();
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
                return Optional.of(medicineModule);
            }
        }
        return Optional.empty();
    }
}
