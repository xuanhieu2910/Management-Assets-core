package com.example.csvccdshustbe.repository.otherVehicleTransportModule.impl;

import com.example.csvccdshustbe.entity.OtherVehicleTransportModule;
import com.example.csvccdshustbe.repository.otherVehicleTransportModule.OtherVehicleTransportRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

public class OtherVehicleTransportRepositoryImpl implements OtherVehicleTransportRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;
    @Override
    public Optional<OtherVehicleTransportModule> findOtherVehicleTransportById(Integer idOtherVehicleTransport) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select otherVehicle.id_other_vehicle_transport_module, otherVehicle.id_asset, " +
                "       otherVehicle.license_plate, otherVehicle.label, otherVehicle.load_capacity, " +
                "       otherVehicle.number_seats, otherVehicle.capacity, otherVehicle.cylinder_capacity, " +
                "       otherVehicle.clutch_number, otherVehicle.vehicle_identification_number, " +
                "       otherVehicle.machine_number, otherVehicle.publish_year, otherVehicle.id_country_producer, " +
                "       otherVehicle.license_certificate_register, otherVehicle.publish_date_license, " +
                "       otherVehicle.company_register, otherVehicle.source, otherVehicle.color, " +
                "       otherVehicle.id_user, otherVehicle.id_type_use, otherVehicle.time_created, " +
                "       otherVehicle.time_modified, otherVehicle.id_position_name " +
                "from other_vehicle_transport_module otherVehicle " +
                "where otherVehicle.id_other_vehicle_transport_module = :idOtherVehicle ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idOtherVehicle", idOtherVehicleTransport);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result){
                OtherVehicleTransportModule module = new OtherVehicleTransportModule();
                module.setIdOtherVehicleTransportModule(ValueUtil.getIntegerByObject(obj[0]));
                module.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                module.setLicensePlate(ValueUtil.getStringByObject(obj[2]));
                module.setLabel(ValueUtil.getStringByObject(obj[3]));
                module.setLoadCapacity(ValueUtil.getStringByObject(obj[4]));
                module.setNumberSeats(ValueUtil.getStringByObject(obj[5]));
                module.setCapacity(ValueUtil.getStringByObject(obj[6]));
                module.setCylinderCapacity(ValueUtil.getStringByObject(obj[7]));
                module.setClutchNumber(ValueUtil.getStringByObject(obj[8]));
                module.setVehicleIdentificationNumber(ValueUtil.getStringByObject(obj[9]));
                module.setMachineNumber(ValueUtil.getStringByObject(obj[10]));
                module.setPublishYear(ValueUtil.getStringByObject(obj[11]));
                module.setIdCountryProducer(ValueUtil.getIntegerByObject(obj[12]));
                module.setLicenseCertificateRegister(ValueUtil.getStringByObject(obj[13]));
                module.setPublishDateLicense(ValueUtil.getStringByObject(obj[14]));
                module.setCompanyRegister(ValueUtil.getStringByObject(obj[15]));
                module.setSource(ValueUtil.getStringByObject(obj[16]));
                module.setColor(ValueUtil.getStringByObject(obj[17]));
                module.setIdUser(ValueUtil.getIntegerByObject(obj[18]));
                module.setIdTypeUse(ValueUtil.getIntegerByObject(obj[19]));
                module.setTimeCreated(ValueUtil.getStringByObject(obj[20]));
                module.setTimeModified(ValueUtil.getStringByObject(obj[21]));
                module.setIdPositionName(ValueUtil.getIntegerByObject(obj[22]));
                return Optional.of(module);
            }
        }
        return Optional.empty();
    }
}
