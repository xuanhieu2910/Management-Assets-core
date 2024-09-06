package com.example.csvccdshustbe.service.asset.impl;

import com.example.csvccdshustbe.entity.*;
import com.example.csvccdshustbe.enums.EnumModuleFactory;
import com.example.csvccdshustbe.enums.OAuth2Factory;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.factory.declare.DeclareFactory;
import com.example.csvccdshustbe.factory.module.ModuleFactory;
import com.example.csvccdshustbe.factory.original.OriginalFactory;
import com.example.csvccdshustbe.repository.asset.AssetRepository;
import com.example.csvccdshustbe.service.asset.AssetService;
import com.example.csvccdshustbe.service.assetCategories.AssetCategoriesService;
import com.example.csvccdshustbe.service.declare.DeclareServiceFactory;
import com.example.csvccdshustbe.service.department.DepartmentService;
import com.example.csvccdshustbe.service.documentAttack.DocumentAttackService;
import com.example.csvccdshustbe.service.location.LocationService;
import com.example.csvccdshustbe.service.modules.ModulesServiceFactory;
import com.example.csvccdshustbe.service.original.OriginalServiceFactory;
import com.example.csvccdshustbe.service.projects.ProjectsService;
import com.example.csvccdshustbe.service.units.UnitsService;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.ProxyInitDataAssetUtil;
import com.example.csvccdshustbe.utility.ValueUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.util.JSONObjectUtils;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Log4j2
@Service
public class AssetServiceImpl implements AssetService {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    AssetRepository assetRepository;
    @Autowired
    DeclareServiceFactory declareServiceFactory;
    @Autowired
    OriginalServiceFactory originalServiceFactory;
    @Autowired
    ModulesServiceFactory modulesServiceFactory;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    LocationService locationService;
    @Autowired
    AssetCategoriesService assetCategoriesService;
    @Autowired
    UnitsService unitsService;
    @Autowired
    DocumentAttackService documentAttackService;
    @Autowired
    ProjectsService projectsService;




    @Transactional
    @Override
    public void createAsset(Map<String, Object> createAssetRequest) throws JsonProcessingException, ValidateFiledException {
        initCreateAsset(createAssetRequest);
    }

    private void initCreateAsset(Map<String, Object> createAssetRequest) throws JsonProcessingException, ValidateFiledException {
        Map<String, Object> dataCreateAssetRequest =
                objectMapper.readValue(JSONObjectUtils.toJSONString(createAssetRequest), Map.class);
        validateDataCreateAsset(dataCreateAssetRequest);
        storeNewAsset(dataCreateAssetRequest);
    }

    private void validateDataCreateAsset(Map<String, Object> createAssetRequest) {
        validateDataCommonCreateAsset(createAssetRequest);
        validateDataModuleCreateAsset(createAssetRequest);
        validateDataOriginalCreateAsset(createAssetRequest);
        validateDataDeclareCreateAsset(createAssetRequest);
    }

    private void validateDataDeclareCreateAsset(Map<String, Object> createAssetRequest) {
    }

    private void validateDataOriginalCreateAsset(Map<String, Object> createAssetRequest) {
    }

    private void validateDataModuleCreateAsset(Map<String, Object> createAssetRequest) {
    }

    private void validateDataCommonCreateAsset(Map<String, Object> createAssetRequest) {
        Integer idDepartment = ValueUtil.getIntegerByObject(createAssetRequest.get("idDepartment"));
        departmentService.findDepartmentByIdDepartmentAndStatus(idDepartment, Constants.DEPARTMENT_ACTIVE_STATUS);
        //validate location
        Integer idLocation = ValueUtil.getIntegerByObject(createAssetRequest.get("idLocation"));
        locationService.findLocationByIdLocationAndIdDepartmentAndVisible(idLocation, idDepartment, Constants.LOCATION_ACTIVE_STATUS);
        //validate asset category
//        Integer idAssetCategory =
        //validate units
        //validate documents
        //validate project
    }

    private void storeNewAsset(Map<String, Object> createAssetRequest) throws ValidateFiledException {
        log.info("Init store asset");
        Asset asset = storeCommonData(createAssetRequest);
        storeModulesDataAsset(createAssetRequest, asset);
        storeOriginalDataAsset(createAssetRequest, asset);
        storeDeclareDataAsset(createAssetRequest, asset);
    }

    private void storeDeclareDataAsset(Map<String, Object> createAssetRequest, Asset asset) throws ValidateFiledException {
        log.info("Storing declare data asset");
        Map<String,Object> declareDataAsset = (Map<String, Object>) createAssetRequest.get(Constants.KEY_DECLARE_ASSET);
        declareDataAsset.put("idAsset", asset.getIdAsset());
        DeclareFactory declareFactory = (DeclareFactory) ProxyInitDataAssetUtil.
                proxyInitDeclareDataAsset(ValueUtil.getStringByObject(declareDataAsset.get(Constants.KEY_TYPE_DECLARE)));
        IDeclare iDeclare = declareFactory.createDeclare(declareDataAsset);
        declareServiceFactory.save(iDeclare,declareDataAsset);
        log.info("Finish store declare factory " + declareFactory.getClass());
    }

    private void storeOriginalDataAsset(Map<String, Object> createAssetRequest, Asset asset) throws ValidateFiledException {
        log.info("Storing original data asset");
        Map<String,Object> originalDataAsset = (Map<String, Object>) createAssetRequest.get(Constants.KEY_ORIGINAL_ASSET);
        originalDataAsset.put("idAsset", asset.getIdAsset());
        OriginalFactory originalFactory = (OriginalFactory) ProxyInitDataAssetUtil.
                proxyInitOriginalDataAsset(ValueUtil.getStringByObject(originalDataAsset.get(Constants.KEY_TYPE_ORIGINAL_ASSET)));
        IOriginal iOriginal = originalFactory.createOriginal(originalDataAsset);
        originalServiceFactory.save(iOriginal, originalDataAsset);
        log.info("Finish store original factory " + originalFactory.getClass());

    }

    private void storeModulesDataAsset(Map<String, Object> createAssetRequest, Asset asset) throws ValidateFiledException {
        log.info("Storing modules data asset");
        Map<String,Object> moduleDataAsset = (Map<String, Object>) createAssetRequest.get(Constants.KEY_MODULE);
        moduleDataAsset.put("idAsset", asset.getIdAsset());
        ModuleFactory moduleFactory = (ModuleFactory) ProxyInitDataAssetUtil.
                proxyInitModuleDataAsset(ValueUtil.getStringByObject(moduleDataAsset.get(Constants.KEY_TYPE_MODULE)));
        IModules iModules = moduleFactory.createModule(moduleDataAsset);
        modulesServiceFactory.save(iModules,moduleDataAsset);
        log.info("Finish store module factory " + moduleFactory.getClass());
    }

    private Asset storeCommonData(Map<String, Object> createAssetRequest) {
        log.info("Storing common data asset");
        Map<String,Object> commonDataAsset = (Map<String, Object>) createAssetRequest.get(Constants.KEY_COMMON);
        Asset asset = contructionDataAsset(commonDataAsset);
        assetRepository.save(asset);
        log.info("Store success common data asset by id = " + asset.getIdAsset());
        return asset;
    }

    private Asset contructionDataAsset(Map<String, Object> dataAsset) {
        Asset asset = new Asset();
        asset.setName(ValueUtil.getStringByObject(dataAsset.get("name")));
        asset.setIdAssetCategory(ValueUtil.getIntegerByObject(dataAsset.get("idAssetCategory")));
        if (StringUtils.isNotBlank(ValueUtil.getStringByObject(dataAsset.get("codeDepartment")))) {
            asset.setCodeAsset(ValueUtil.getStringByObject(dataAsset.get("codeDepartment")) + "-" + String.valueOf(UUID.randomUUID()));
        } else {
            asset.setCodeAsset(String.valueOf(UUID.randomUUID()));
        }
        asset.setIdDocumentAttack(ValueUtil.getIntegerByObject(dataAsset.get("idDocumentAttack")));
        asset.setIdDepartment(ValueUtil.getIntegerByObject(dataAsset.get("idDepartment")));
        asset.setIdLocation(ValueUtil.getIntegerByObject(dataAsset.get("idLocation")));
        asset.setIdUnit(ValueUtil.getIntegerByObject(dataAsset.get("idUnit")));
        asset.setIdOriginal(ValueUtil.getIntegerByObject(dataAsset.get("idOriginal")));
        asset.setIdOriginOfFormation(ValueUtil.getIntegerByObject(dataAsset.get("idOriginOfFormation")));
        asset.setIdProjects(ValueUtil.getIntegerByObject(dataAsset.get("idProjects")));
        asset.setPurpose(ValueUtil.getStringByObject(dataAsset.get("purpose")));
        asset.setNotes(ValueUtil.getStringByObject(dataAsset.get("notes")));
        asset.setFileAttack(ValueUtil.getStringByObject(dataAsset.get("fileAttack")));
        String timeCurrent = String.valueOf(new Date().getTime());
        asset.setTimeCreated(timeCurrent);
        asset.setTimeModified(timeCurrent);
        asset.setIdDepartmentDefault(ValueUtil.getIntegerByObject(dataAsset.get("idDepartmentDefault")));
        asset.setIdLevelTypeAsset(ValueUtil.getIntegerByObject(dataAsset.get("idLevelTypeAsset")));
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        asset.setIdUserCreated(csvcUser.getIdUser());
        asset.setIdUserModified(csvcUser.getIdUser());
        return asset;
    }
}
