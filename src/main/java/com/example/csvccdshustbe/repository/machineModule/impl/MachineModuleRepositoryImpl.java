package com.example.csvccdshustbe.repository.machineModule.impl;

import com.example.csvccdshustbe.dto.modules.machineModules.MachineModuleDetailsDto;
import com.example.csvccdshustbe.repository.machineModule.MachineModuleRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

public class MachineModuleRepositoryImpl implements MachineModuleRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<MachineModuleDetailsDto> findMachineModuleDetailsDtoById(Integer idMachineModule) {
        StringBuilder sb = new StringBuilder();
        sb.append("select machineModule.id_machine_module, machineModule.id_asset, machineModule.label_machine,    " +
                "        machineModule.model, machineModule.serial, machineModule.publish_date,  " +
                "        machineModule.id_country_producer, machineModule.id_user, machineModule.id_type_use,  " +
                "        co.name nameCountryProducer, cu.user_name nameUser, cu.full_name fullName,  " +
                "        ty.name nameTypeUse  " +
                "from machine_module machineModule  " +
                "    left join csvc_user cu on machineModule.id_user = cu.id_user  " +
                "    left join country_producer co on machineModule.id_country_producer = co.id_country_producer  " +
                "    left join type_use ty on machineModule.id_type_use = ty.id_type_use  " +
                "where machineModule.id_machine_module = :idMachineModule ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idMachineModule", idMachineModule);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj: result){
                MachineModuleDetailsDto machineModule = new MachineModuleDetailsDto();
                machineModule.setIdMachineModule(ValueUtil.getIntegerByObject(obj[0]));
                machineModule.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                machineModule.setLabelMachine(ValueUtil.getStringByObject(obj[2]));
                machineModule.setModel(ValueUtil.getStringByObject(obj[3]));
                machineModule.setSerial(ValueUtil.getStringByObject(obj[4]));
                machineModule.setPublishDate(ValueUtil.getStringByObject(obj[5]));
                machineModule.setIdCountryProducer(ValueUtil.getIntegerByObject(obj[6]));
                machineModule.setIdUser(ValueUtil.getIntegerByObject(obj[7]));
                machineModule.setIdTypeUse(ValueUtil.getIntegerByObject(obj[8]));
                machineModule.setNameCountryProducer(ValueUtil.getStringByObject(obj[9]));
                machineModule.setNameUser(ValueUtil.getStringByObject(obj[10]));
                machineModule.setFullName(ValueUtil.getStringByObject(obj[11]));
                machineModule.setNameTypeUse(ValueUtil.getStringByObject(obj[12]));
                return Optional.of(machineModule);
            }
        }
        return Optional.empty();
    }
}
