package com.example.csvccdshustbe.repository.carModule.impl;

import com.example.csvccdshustbe.entity.CarModule;
import com.example.csvccdshustbe.repository.carModule.CarModuleRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

public class CarModuleRepositoryImpl implements CarModuleRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<CarModule> findCarModulesByIdCar(Integer idCarModule) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select carModule.id_car_module, carModule.id_asset, carModule.is_free_tax, " +
                "       carModule.value_tax, carModule.license_plate, carModule.label_car, " +
                "       carModule.type_car, carModule.load_capacity, carModule.number_seats, " +
                "       carModule.capacity, carModule.cylinder_capacity, carModule.clutch_number, " +
                "       carModule.vehicle_identification_number, carModule.machine_number, carModule.publish_year, " +
                "       carModule.id_country_producer, carModule.license_certificate_register, carModule.publish_date_license, " +
                "       carModule.company_register, carModule.source, carModule.color, carModule.id_user, carModule.id_type_use, " +
                "       carModule.time_created, carModule.time_modified " +
                "from car_module carModule " +
                "where carModule.id_car_module = :idCarModule ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idCarModule", idCarModule);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj: result){
                CarModule module = new CarModule();
                module.setIdCarModule(ValueUtil.getIntegerByObject(obj[0]));
                module.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                module.setIsFreeTax(ValueUtil.getIntegerByObject(obj[2]));
                module.setValueTax(ValueUtil.getStringByObject(obj[3]));
                module.setLicensePlate(ValueUtil.getStringByObject(obj[4]));
                module.setLabelCar(ValueUtil.getStringByObject(obj[5]));
                module.setTypeCar(ValueUtil.getStringByObject(obj[6]));
                module.setLoadCapacity(ValueUtil.getStringByObject(obj[7]));
                module.setNumberSeats(ValueUtil.getIntegerByObject(obj[8]));
                module.setCapacity(ValueUtil.getStringByObject(obj[9]));
                module.setCylinderCapacity(ValueUtil.getStringByObject(obj[10]));
                module.setClutchNumber(ValueUtil.getStringByObject(obj[11]));
                module.setVehicleIdentificationNumber(ValueUtil.getStringByObject(obj[12]));
                module.setMachineNumber(ValueUtil.getStringByObject(obj[13]));
                module.setPublishYear(ValueUtil.getStringByObject(obj[14]));
                module.setIdCountryProducer(ValueUtil.getIntegerByObject(obj[15]));
                module.setLicenseCertificateRegister(ValueUtil.getStringByObject(obj[16]));
                module.setPublishDateLicense(ValueUtil.getStringByObject(obj[17]));
                module.setCompanyRegister(ValueUtil.getStringByObject(obj[18]));
                module.setSource(ValueUtil.getStringByObject(obj[19]));
                module.setColor(ValueUtil.getStringByObject(obj[20]));
                module.setIdUser(ValueUtil.getIntegerByObject(obj[21]));
                module.setIdTypeUse(ValueUtil.getIntegerByObject(obj[22]));
                module.setTimeCreated(ValueUtil.getStringByObject(obj[23]));
                module.setTimeModified(ValueUtil.getStringByObject(obj[24]));
                return Optional.of(module);
            }
        }
        return Optional.empty();
    }
}
