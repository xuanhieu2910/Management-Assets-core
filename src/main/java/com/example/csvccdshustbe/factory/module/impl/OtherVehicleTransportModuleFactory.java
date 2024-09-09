package com.example.csvccdshustbe.factory.module.impl;

import com.example.csvccdshustbe.entity.IModules;
import com.example.csvccdshustbe.entity.OtherVehicleTransportModule;
import com.example.csvccdshustbe.factory.module.ModuleFactory;
import com.example.csvccdshustbe.utility.ValueUtil;

import java.util.Date;
import java.util.Map;

public class OtherVehicleTransportModuleFactory implements ModuleFactory {
    @Override
    public IModules createModule(Map<String, Object> mapModuleCreate) {
        OtherVehicleTransportModule  module = new OtherVehicleTransportModule();
        module.setIdAsset(ValueUtil.getIntegerByObject(mapModuleCreate.get("idAsset")));
        module.setLicensePlate(ValueUtil.getStringByObject(mapModuleCreate.get("licensePlate")));
        module.setLabel(ValueUtil.getStringByObject(mapModuleCreate.get("label")));
        module.setLoadCapacity(ValueUtil.getStringByObject(mapModuleCreate.get("loadCapacity")));
        module.setNumberSeats(ValueUtil.getStringByObject(mapModuleCreate.get("numberSeats")));
        module.setCapacity(ValueUtil.getStringByObject(mapModuleCreate.get("capacity")));
        module.setCylinderCapacity(ValueUtil.getStringByObject(mapModuleCreate.get("cylinderCapacity")));
        module.setClutchNumber(ValueUtil.getStringByObject(mapModuleCreate.get("clutchNumber")));
        module.setVehicleIdentificationNumber(ValueUtil.getStringByObject(mapModuleCreate.get("vehicleIdentificationNumber")));
        module.setMachineNumber(ValueUtil.getStringByObject(mapModuleCreate.get("machineNumber")));
        module.setPublishYear(ValueUtil.getStringByObject(mapModuleCreate.get("publishYear")));
        module.setIdCountryProducer(ValueUtil.getIntegerByObject(mapModuleCreate.get("idCountryProducer")));
        module.setLicenseCertificateRegister(ValueUtil.getStringByObject(mapModuleCreate.get("licenseCertificateRegister")));
        module.setPublishDateLicense(ValueUtil.getStringByObject(mapModuleCreate.get("publishDateLicense")));
        module.setCompanyRegister(ValueUtil.getStringByObject(mapModuleCreate.get("companyRegister")));
        module.setSource(ValueUtil.getStringByObject(mapModuleCreate.get("source")));
        module.setColor(ValueUtil.getStringByObject(mapModuleCreate.get("color")));
        module.setIdUser(ValueUtil.getIntegerByObject(mapModuleCreate.get("idUser")));
        module.setIdTypeUse(ValueUtil.getIntegerByObject(mapModuleCreate.get("idTypeUse")));
        String timeCurrent = String.valueOf(new Date().getTime());
        module.setTimeCreated(timeCurrent);
        module.setTimeModified(timeCurrent);
        module.setIdPositionName(ValueUtil.getIntegerByObject(mapModuleCreate.get("idPositionName")));
        return module;
    }
}
