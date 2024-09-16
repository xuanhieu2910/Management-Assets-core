package com.example.csvccdshustbe.repository.carModule.impl;

import com.example.csvccdshustbe.dto.modules.carModules.CarModulesDetailsDto;
import com.example.csvccdshustbe.entity.CarModule;
import com.example.csvccdshustbe.repository.carModule.CarModuleRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

public class CarModuleRepositoryImpl implements CarModuleRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<CarModulesDetailsDto> findCarModulesDetailsDtoByIdCar(Integer idCarModule) {
        StringBuilder sb = new StringBuilder();
        sb.append("select carModule.id_car_module, carModule.id_asset, carModule.is_free_tax,          " +
                "        carModule.value_tax, carModule.license_plate, carModule.label_car,       " +
                "        carModule.type_car, carModule.load_capacity, carModule.number_seats,  " +
                "        carModule.capacity, carModule.cylinder_capacity, carModule.clutch_number,  " +
                "        carModule.vehicle_identification_number, carModule.machine_number, carModule.publish_year,  " +
                "        carModule.id_country_producer, carModule.license_certificate_register, carModule.publish_date_license,  " +
                "        carModule.company_register, carModule.source, carModule.color, us.code_user, carModule.id_type_use,  " +
                "        carModule.time_created, carModule.time_modified,  " +
                "        countryProducer.name nameCountryProducer, us.user_name, us.full_name, ty.name nameTypeUse,  " +
                "        carModule.spare_parts_attack  " +
                "from car_module carModule     " +
                "     left join country_producer countryProducer on carModule.id_country_producer = countryProducer.id_country_producer   " +
                "     left join csvc_user us on carModule.id_user = us.id_user    " +
                "     left join type_use ty on carModule.id_type_use = ty.id_type_use    " +
                "where carModule.id_car_module = :idCarModule  ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idCarModule", idCarModule);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj: result){
                CarModulesDetailsDto module = new CarModulesDetailsDto();
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
                module.setCodeUser(ValueUtil.getStringByObject(obj[21]));
                module.setIdTypeUse(ValueUtil.getIntegerByObject(obj[22]));
                module.setTimeCreated(ValueUtil.getStringByObject(obj[23]));
                module.setTimeModified(ValueUtil.getStringByObject(obj[24]));
                module.setNameCountryProducer(ValueUtil.getStringByObject(obj[25]));
                module.setUserName(ValueUtil.getStringByObject(obj[26]));
                module.setFullName(ValueUtil.getStringByObject(obj[27]));
                module.setNameTypeUse(ValueUtil.getStringByObject(obj[28]));
                module.setSparePartsAttack(ValueUtil.getStringByObject(obj[29]));
                return Optional.of(module);
            }
        }
        return Optional.empty();
    }

    @Modifying
    @Transactional
    @Override
    public void deleteCarModuleByIdCarModule(Integer idInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append(" delete from car_module carModule " +
                "where carModule.id_car_module = :idCarModule ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idCarModule", idInstance);
        query.executeUpdate();
    }

    @Override
    public Optional<CarModule> findCarModuleByIdCarModule(Integer idInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select carModule.id_car_module, carModule.id_asset, carModule.is_free_tax, " +
                "       carModule.value_tax, carModule.license_plate, carModule.label_car, " +
                "       carModule.type_car, carModule.load_capacity, carModule.number_seats, " +
                "       carModule.capacity, carModule.cylinder_capacity, carModule.clutch_number, " +
                "       carModule.vehicle_identification_number, carModule.machine_number, " +
                "       carModule.publish_year, carModule.id_country_producer, " +
                "       carModule.license_certificate_register, carModule.publish_date_license, " +
                "       carModule.company_register, carModule.source, carModule.color, " +
                "       carModule.id_user, carModule.id_type_use, carModule.time_created, " +
                "       carModule.time_modified, carModule.spare_parts_attack " +
                "from car_module carModule " +
                "where carModule.id_car_module = :idCarModule ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idCarModule", idInstance);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result) {
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
                module.setSparePartsAttack(ValueUtil.getStringByObject(obj[25]));
                return Optional.of(module);
            }
        }
        return Optional.empty();
    }
}
