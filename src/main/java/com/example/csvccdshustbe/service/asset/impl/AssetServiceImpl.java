package com.example.csvccdshustbe.service.asset.impl;

import com.example.csvccdshustbe.dto.asset.AssetBluePrintDto;
import com.example.csvccdshustbe.dto.asset.CommonAssetDto;
import com.example.csvccdshustbe.dto.asset.FindAllAssetDto;
import com.example.csvccdshustbe.dto.declare.BluePrintDeclareDto;
import com.example.csvccdshustbe.dto.modules.AssetModulesDto;
import com.example.csvccdshustbe.dto.modules.BluePrintAssetModulesDto;
import com.example.csvccdshustbe.dto.original.BluePrintOriginalDto;
import com.example.csvccdshustbe.entity.*;
import com.example.csvccdshustbe.exception.FileExcelException;
import com.example.csvccdshustbe.exception.FileException;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.factory.declare.DeclareFactory;
import com.example.csvccdshustbe.factory.module.ModuleFactory;
import com.example.csvccdshustbe.factory.original.OriginalFactory;
import com.example.csvccdshustbe.repository.asset.AssetRepository;
import com.example.csvccdshustbe.repository.assetCategories.AssetCategoriesRepository;
import com.example.csvccdshustbe.repository.countryProducer.CountryProducerRepository;
import com.example.csvccdshustbe.repository.currentUsage.CurrentUsageRepository;
import com.example.csvccdshustbe.repository.declare.DeclareRepository;
import com.example.csvccdshustbe.repository.department.DepartmentRepository;
import com.example.csvccdshustbe.repository.districts.DistrictsRepository;
import com.example.csvccdshustbe.repository.documentAttack.DocumentAttackRepository;
import com.example.csvccdshustbe.repository.levelTypeAsset.LevelTypeAssetRepository;
import com.example.csvccdshustbe.repository.location.LocationRepository;
import com.example.csvccdshustbe.repository.medicineGroup.MedicineGroupRepository;
import com.example.csvccdshustbe.repository.medicineType.MedicineTypeRepository;
import com.example.csvccdshustbe.repository.modules.ModulesRepository;
import com.example.csvccdshustbe.repository.original.OriginalRepository;
import com.example.csvccdshustbe.repository.originalOfFormation.OriginalOfFormationRepository;
import com.example.csvccdshustbe.repository.projects.ProjectsRepository;
import com.example.csvccdshustbe.repository.province.ProvinceRepository;
import com.example.csvccdshustbe.repository.typeDeclareAsset.TypeDeclareAssetRepository;
import com.example.csvccdshustbe.repository.typeUse.TypeUseRepository;
import com.example.csvccdshustbe.repository.units.UnitsRepository;
import com.example.csvccdshustbe.repository.wards.WardsRepository;
import com.example.csvccdshustbe.request.asset.FindAllAssetRequest;
import com.example.csvccdshustbe.request.asset.FindAllGroundAssetRequest;
import com.example.csvccdshustbe.response.asset.FindAllAssetResponse;
import com.example.csvccdshustbe.response.asset.FindAllGroundAssetResponse;
import com.example.csvccdshustbe.response.asset.FindDetailsAssetResponse;
import com.example.csvccdshustbe.service.asset.AssetService;
import com.example.csvccdshustbe.service.assetCategories.AssetCategoriesService;
import com.example.csvccdshustbe.service.assetDepreciation.AssetDepreciationService;
import com.example.csvccdshustbe.service.assetOriginalOfFormation.AssetOriginalOfFormationService;
import com.example.csvccdshustbe.service.declare.DeclareServiceFactory;
import com.example.csvccdshustbe.service.department.DepartmentService;
import com.example.csvccdshustbe.service.documentAttack.DocumentAttackService;
import com.example.csvccdshustbe.service.location.LocationService;
import com.example.csvccdshustbe.service.modules.ModulesServiceFactory;
import com.example.csvccdshustbe.service.original.OriginalService;
import com.example.csvccdshustbe.service.original.OriginalServiceFactory;
import com.example.csvccdshustbe.service.originalOfFormation.OriginalOfFormationService;
import com.example.csvccdshustbe.service.projects.ProjectsService;
import com.example.csvccdshustbe.service.units.UnitsService;
import com.example.csvccdshustbe.service.upload.FilesStorageService;
import com.example.csvccdshustbe.service.user.CsvcUserService;
import com.example.csvccdshustbe.utility.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.util.JSONObjectUtils;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    @Autowired
    AssetOriginalOfFormationService assetOriginalOfFormationService;
    @Autowired
    CsvcUserService csvcUserService;
    @Autowired
    OriginalOfFormationService originalOfFormationService;
    @Autowired
    AssetDepreciationService assetDepreciationService;
    @Autowired
    FilesStorageService filesStorageService;
    @Autowired
    OriginalService originalService;
    @Autowired
    UnitsRepository unitsRepository;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    AssetCategoriesRepository assetCategoriesRepository;
    @Autowired
    DocumentAttackRepository documentAttackRepository;
    @Autowired
    ProjectsRepository projectsRepository;
    @Autowired
    OriginalRepository originalRepository;
    @Autowired
    ModulesRepository modulesRepository;
    @Autowired
    OriginalOfFormationRepository originalOfFormationRepository;
    @Autowired
    DeclareRepository declareRepository;
    @Autowired
    LevelTypeAssetRepository levelTypeAssetRepository;
    @Autowired
    CountryProducerRepository countryProducerRepository;
    @Autowired
    TypeUseRepository typeUseRepository;
    @Autowired
    MedicineTypeRepository medicineTypeRepository;
    @Autowired
    MedicineGroupRepository medicineGroupRepository;
    @Autowired
    WardsRepository wardsRepository;
    @Autowired
    DistrictsRepository districtsRepository;
    @Autowired
    ProvinceRepository provinceRepository;
    @Autowired
    TypeDeclareAssetRepository typeDeclareAssetRepository;
    @Autowired
    CurrentUsageRepository currentUsageRepository;



    @Transactional
    @Override
    public void createAsset(Map<String, Object> createAssetRequest) throws JsonProcessingException, ValidateFiledException {
        Map<String, Object> dataCreateAssetRequest =
                objectMapper.readValue(JSONObjectUtils.toJSONString(createAssetRequest), Map.class);
        validateDataCreateAsset(dataCreateAssetRequest);
        storeNewAsset(dataCreateAssetRequest);
    }

    @Override
    public Page<FindAllAssetResponse> findAllAsset(FindAllAssetRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        setIdsDepartmentOriginal(request);
        Page<FindAllAssetDto> findAllAssetDtos = assetRepository.findAllAssetDtoByIdsDepartment(request, pageable);
        return new PageImpl<>(convertToFindAllAssetResponse(findAllAssetDtos.get().collect(Collectors.toList())),
                    pageable, findAllAssetDtos.getTotalElements());
    }

    private void setIdsDepartmentOriginal(FindAllAssetRequest request) {
        Integer idDepartment = csvcUserService.getInformationUser().getIdDepartment();
        request.setIdsDepartmentOriginal(departmentService.findIdsStructureDepartment(idDepartment));
    }

    @Override
    public FindDetailsAssetResponse findDetailsAssetByCodeAsset(String codeAsset) throws ValidateFiledException, IllegalAccessException {
        Optional<AssetBluePrintDto> assetBluePrintDto = assetRepository.findDetailAssetByCodeAsset(codeAsset);
        if (assetBluePrintDto.isEmpty()) {
            throw new NotFoundException("Don't exits asset by code!");
        }
        setParentAssetCategory(assetBluePrintDto.get());
        setOriginalOfFormation(assetBluePrintDto.get());
        setDataModulesDetail(assetBluePrintDto.get());
        setDataOriginalDetail(assetBluePrintDto.get());
        setDataDeclareDetail(assetBluePrintDto.get());
        return convertToFindDetailsAssetResponse(assetBluePrintDto.get());
    }

    @Transactional
    @Override
    public void updateAsset(HashMap<String, Object> updateAssetRequest) throws JsonProcessingException, ValidateFiledException,
            IllegalAccessException {
        Map<String, Object> dataCreateAssetRequest =
                objectMapper.readValue(JSONObjectUtils.toJSONString(updateAssetRequest), Map.class);
        validateDataUpdateAsset(dataCreateAssetRequest);
        updateDataAsset(dataCreateAssetRequest);
    }

    @Override
    public void deleteAssetByCodeAsset(String codeAsset) throws ValidateFiledException {
        Optional<AssetBluePrintDto> assetBluePrintDto = assetRepository.findDetailAssetByCodeAsset(codeAsset);
        if (assetBluePrintDto.isEmpty()) {
            throw new NotFoundException("Don't exits asset by code!");
        }
        deleteAsseDepreciation(assetBluePrintDto.get());
        deleteCommonAsset(assetBluePrintDto.get());
        deleteModuleAsset(assetBluePrintDto.get());
        deleteOriginalAsset(assetBluePrintDto.get());
        deleteDeclareAsset(assetBluePrintDto.get());
    }

    private void deleteAsseDepreciation(AssetBluePrintDto assetBluePrintDto) {
        assetDepreciationService.deleteAssetDepreciationByIdAsset(assetBluePrintDto.getIdAsset());
    }

    @Override
    public Page<FindAllGroundAssetResponse> findAllGroundAsset(FindAllGroundAssetRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        return  assetRepository.findAllGroundAsset(pageable, request);
    }

    @Override
    public String uploadFile(MultipartFile file) throws IOException, FileException {
        return filesStorageService.saveAndReturnPathAsset(file, FileUtil.FOLDER_ASSET);
    }

    @Override
    public void deleteFile(String pathFile) throws ValidateFiledException, IOException, InterruptedException {
        filesStorageService.deleteByPathFile(pathFile);
    }

    private void deleteDeclareAsset(AssetBluePrintDto assetBluePrintDto) throws ValidateFiledException {
        declareServiceFactory.deleteAssetDeclare(assetBluePrintDto.getDeclare().getBluePrintDeclare(),
                assetBluePrintDto.getIdAsset());
    }

    private void deleteOriginalAsset(AssetBluePrintDto assetBluePrintDto) throws ValidateFiledException {
        String typeModule = assetBluePrintDto.getOriginal().getBluePrintAssetOriginalDto().getTypeOriginal();
        Integer idOriginal = assetBluePrintDto.getOriginal().getBluePrintAssetOriginalDto().getIdOriginal();
        Integer idInstance = assetBluePrintDto.getOriginal().getBluePrintAssetOriginalDto().getIdInstance();
        originalServiceFactory.deleteAssetOriginal(typeModule, idOriginal, idInstance);
    }

    private void deleteModuleAsset(AssetBluePrintDto assetBluePrintDto) throws ValidateFiledException {
        String typeModule;
        Integer idModule;
        Integer idInstance;
        for (AssetModulesDto modulesDto : assetBluePrintDto.getModules()) {
            typeModule = modulesDto.getBluePrintAssetModules().getTypeModules();
            idModule = modulesDto.getBluePrintAssetModules().getIdModules();
            idInstance = modulesDto.getBluePrintAssetModules().getIdInstance();
            modulesServiceFactory.deleteModulesByTypeModulesAndIdInstance(typeModule, idInstance, idModule);
        }
    }

    private void deleteCommonAsset(AssetBluePrintDto assetBluePrintDto) {
        assetRepository.deleteByIdAsset(assetBluePrintDto.getIdAsset());
        assetOriginalOfFormationService.deleteByIdAsset(assetBluePrintDto.getIdAsset());
    }

    private void updateDataAsset(Map<String, Object> dataUpdateAssetRequest) throws ValidateFiledException, IllegalAccessException {
        Asset asset = updateCommonDataAsset(dataUpdateAssetRequest);
        updateAssetDepreciation(dataUpdateAssetRequest, asset);
        updateModulesDataAsset(dataUpdateAssetRequest, asset);
        updateOriginalDataAsset(dataUpdateAssetRequest, asset);
        updateDeclareDataAsset(dataUpdateAssetRequest, asset);
    }

    private void updateAssetDepreciation(Map<String, Object> dataUpdateAssetRequest, Asset asset) {
        log.info("Storing depreciation data asset");
        Map<String, Object> depreciationAsset = (Map<String, Object>) dataUpdateAssetRequest.get(Constants.KEY_DEPRECIATION);
        AssetDepreciation assetDepreciation = assetDepreciationService.findAssetDepreciationByIdAsset(asset.getIdAsset());
        updateDataAssetDepreciation(assetDepreciation, depreciationAsset);
            assetDepreciationService.save(assetDepreciation);
    }

    private void updateDataAssetDepreciation(AssetDepreciation assetDepreciation, Map<String, Object> depreciationAsset) {
        assetDepreciation.setTimeStartedDepreciation(ValueUtil.getStringByObject(depreciationAsset.get("timeStartedDepreciation")));
        assetDepreciation.setAmountMonthsDepreciation(ValueUtil.getIntegerByObject(depreciationAsset.get("amountMonthsDepreciation")));
        assetDepreciation.setValueDepreciation(ValueUtil.getStringByObject(depreciationAsset.get("valueDepreciation")));
        assetDepreciation.setTypeDepreciation(ValueUtil.getIntegerByObject(depreciationAsset.get("typeDepreciation")));
        assetDepreciation.setValueTypeDepreciation(ValueUtil.getStringByObject(depreciationAsset.get("valueTypeDepreciation")));
        assetDepreciation.setAmountRestMonthsDepreciation(ValueUtil.getIntegerByObject(depreciationAsset.get("amountRestMonthsDepreciation")));
        assetDepreciation.setCumulative(ValueUtil.getStringByObject(depreciationAsset.get("cumulative")));
        assetDepreciation.setRestValue(ValueUtil.getStringByObject(depreciationAsset.get("restValue")));
        assetDepreciation.setTimeStartedWearTear(ValueUtil.getStringByObject(depreciationAsset.get("timeStartedWearTear")));
        assetDepreciation.setTimeEndWearTear(ValueUtil.getStringByObject(depreciationAsset.get("timeEndWearTear")));
        assetDepreciation.setTypeCalculate(ValueUtil.getIntegerByObject(depreciationAsset.get("typeCalculate")));
        assetDepreciation.setTimeBuy(ValueUtil.getStringByObject(depreciationAsset.get("timeBuy")));
        assetDepreciation.setTimeStartedUsed(ValueUtil.getStringByObject(depreciationAsset.get("timeStartedUsed")));
        assetDepreciation.setTimeStartedIncrease(ValueUtil.getStringByObject(depreciationAsset.get("timeStartedIncrease")));
        assetDepreciation.setTimeYearTracking(ValueUtil.getStringByObject(depreciationAsset.get("timeYearTracking")));
        String timeCurrent = String.valueOf(new Date().getTime());
        assetDepreciation.setTimeModified(timeCurrent);
    }

    private void updateDeclareDataAsset(Map<String, Object> dataUpdateAssetRequest, Asset asset) throws ValidateFiledException {
        log.info("Start update declare data asset by code asset " + asset.getCodeAsset());
        Map<String,Object> declareDataAsset = (Map<String, Object>) dataUpdateAssetRequest.get(Constants.KEY_DECLARE_ASSET);
        BluePrintDeclareDto bluePrintDeclareDto = declareServiceFactory.findBluePrintAssetDeclareByIdAsset(asset.getIdAsset());
        deleteAssetDeclare(bluePrintDeclareDto, declareDataAsset, asset);
        createNewAssetDeclare(bluePrintDeclareDto, declareDataAsset, asset);
        updateAssetDeclare(bluePrintDeclareDto, declareDataAsset, asset);
    }

    private void updateAssetDeclare(BluePrintDeclareDto bluePrintDeclareDto,
                                    Map<String, Object> declareDataAsset,
                                    Asset asset) throws ValidateFiledException {
        if (bluePrintDeclareDto.getTypeDeclare().equals(ValueUtil.getStringByObject(declareDataAsset.get(Constants.KEY_TYPE_DECLARE)))){
            declareDataAsset.put("idAsset", asset.getIdAsset());
            String typeDeclare = bluePrintDeclareDto.getTypeDeclare();
            Integer idInstance = bluePrintDeclareDto.getIdInstance();
            IDeclare iDeclareDetails = declareServiceFactory.findIDeclareByTypeDeclareAndIdInstance(typeDeclare, idInstance);
            DeclareFactory declareFactory = (DeclareFactory) ProxyInitDataAssetUtil.
                    proxyInitDeclareDataAsset(ValueUtil.getStringByObject(declareDataAsset.get(Constants.KEY_TYPE_DECLARE)));
            IDeclare iDeclare = declareFactory.updateDeclare(declareDataAsset,iDeclareDetails);
            declareServiceFactory.update(declareDataAsset, iDeclare);
            log.info("Finish update original factory " + declareFactory.getClass());
        }
    }

    private void createNewAssetDeclare(BluePrintDeclareDto bluePrintDeclareDto, Map<String, Object> declareDataAsset, Asset asset) throws ValidateFiledException {
        if (!bluePrintDeclareDto.getTypeDeclare().equals(ValueUtil.getStringByObject(declareDataAsset.get(Constants.KEY_TYPE_DECLARE)))){
            log.info("Storing declare data asset");
            declareDataAsset.put("idAsset", asset.getIdAsset());
            DeclareFactory declareFactory = (DeclareFactory) ProxyInitDataAssetUtil.
                    proxyInitDeclareDataAsset(ValueUtil.getStringByObject(declareDataAsset.get(Constants.KEY_TYPE_DECLARE)));
            IDeclare iDeclare = declareFactory.createDeclare(declareDataAsset);
            declareServiceFactory.save(iDeclare,declareDataAsset);
            log.info("Finish store declare factory " + declareFactory.getClass());
        }
    }

    private void deleteAssetDeclare(BluePrintDeclareDto bluePrintDeclareDto,
                                    Map<String, Object> declareDataAsset, Asset asset) throws ValidateFiledException {
        if (!bluePrintDeclareDto.getTypeDeclare().equals(ValueUtil.getStringByObject(declareDataAsset.get(Constants.KEY_TYPE_DECLARE)))) {
            declareServiceFactory.deleteAssetDeclare(bluePrintDeclareDto,asset.getIdAsset());
        }
    }

    private void updateOriginalDataAsset(Map<String, Object> dataUpdateAssetRequest, Asset asset) throws ValidateFiledException {
        log.info("Start update original data asset by code asset " + asset.getCodeAsset());
        Map<String,Object> originalDataAsset = (Map<String, Object>) dataUpdateAssetRequest.get(Constants.KEY_ORIGINAL_ASSET);
        BluePrintOriginalDto bluePrintOriginalDto = originalServiceFactory.findBluePrintAssetOriginalByIdAsset(asset.getIdAsset());
        deleteAssetOriginal(bluePrintOriginalDto, originalDataAsset);
        createNewAssetOriginal(bluePrintOriginalDto, originalDataAsset, asset);
        updateAssetOriginal(bluePrintOriginalDto, originalDataAsset);
    }

    private void updateAssetOriginal(BluePrintOriginalDto bluePrintOriginalDto, Map<String, Object> originalDataAsset) throws ValidateFiledException {
        if (bluePrintOriginalDto.getTypeOriginal().equals(ValueUtil.getStringByObject(originalDataAsset.get(Constants.KEY_TYPE_ORIGINAL_ASSET)))){
            String typeOriginal = bluePrintOriginalDto.getTypeOriginal();
            Integer idInstance = bluePrintOriginalDto.getIdInstance();
            IOriginal iOriginalDetails = originalServiceFactory.findIOriginalByTypeOriginalAndIdInstance(typeOriginal, idInstance);
            OriginalFactory originalFactory = (OriginalFactory) ProxyInitDataAssetUtil.
                    proxyInitOriginalDataAsset(ValueUtil.getStringByObject(originalDataAsset.get(Constants.KEY_TYPE_ORIGINAL_ASSET)));
            IOriginal iOriginal = originalFactory.updateOriginal(originalDataAsset,iOriginalDetails);
            originalServiceFactory.update(ValueUtil.getStringByObject(originalDataAsset.get(Constants.KEY_TYPE_ORIGINAL_ASSET)), iOriginal);
            log.info("Finish update original factory " + originalFactory.getClass());
        }
    }

    private void createNewAssetOriginal(BluePrintOriginalDto bluePrintOriginalDto, Map<String, Object> originalDataAssetRq, Asset asset) throws ValidateFiledException {
        if (!bluePrintOriginalDto.getTypeOriginal().equals(ValueUtil.getStringByObject(originalDataAssetRq.get(Constants.KEY_TYPE_ORIGINAL_ASSET)))){
            log.info("Storing original data asset");
            originalDataAssetRq.put("idAsset", asset.getIdAsset());
            OriginalFactory originalFactory = (OriginalFactory) ProxyInitDataAssetUtil.
                    proxyInitOriginalDataAsset(ValueUtil.getStringByObject(originalDataAssetRq.get(Constants.KEY_TYPE_ORIGINAL_ASSET)));
            IOriginal iOriginal = originalFactory.createOriginal(originalDataAssetRq);
            originalServiceFactory.save(iOriginal, originalDataAssetRq);
            log.info("Finish store original factory " + originalFactory.getClass());
        }
    }

    private void deleteAssetOriginal(BluePrintOriginalDto bluePrintOriginalDto, Map<String, Object> originalDataAsset) throws ValidateFiledException {
        if (!bluePrintOriginalDto.getTypeOriginal().equals(ValueUtil.getStringByObject(originalDataAsset.get(Constants.KEY_TYPE_ORIGINAL_ASSET)))){
            originalServiceFactory.deleteAssetOriginal(bluePrintOriginalDto.getTypeOriginal(), bluePrintOriginalDto.getIdInstance(),
                    bluePrintOriginalDto.getIdOriginal());
        }
    }

    private void updateModulesDataAsset(Map<String, Object> dataUpdateAssetRequest, Asset asset) throws ValidateFiledException, IllegalAccessException {
        log.info("Start update modules data asset by code asset " + asset.getCodeAsset());
        List<BluePrintAssetModulesDto> bluePrintAssetModulesDtos = modulesServiceFactory.findBluePrintAssetModulesByIdAsset(asset.getIdAsset());
        List<HashMap<String,Object>> modulesDataAsset = (List<HashMap<String,Object>>) dataUpdateAssetRequest.get(Constants.KEY_MODULE);

        deleteAssetModule(bluePrintAssetModulesDtos, modulesDataAsset);
        createNewAssetModule(bluePrintAssetModulesDtos,modulesDataAsset,asset);
        updateAssetModule(bluePrintAssetModulesDtos, modulesDataAsset);
    }

    private void updateAssetModule(List<BluePrintAssetModulesDto> bluePrintAssetModulesDtos,
                                   List<HashMap<String, Object>> dataModule) throws ValidateFiledException, IllegalAccessException {
        for (HashMap<String,Object> dataAsset: dataModule) {
            String typeModules;
            Integer idInstance;
            for (BluePrintAssetModulesDto bluePrintAssetModulesDto : bluePrintAssetModulesDtos) {
                if (ValueUtil.getStringByObject(dataAsset.get(Constants.KEY_TYPE_MODULE)).
                        equals(bluePrintAssetModulesDto.getTypeModules())) {
                    if (StringUtils.isNotBlank(ValueUtil.getStringByObject(dataAsset.get("codeUser")))){
                        Optional<CsvcUser> user = csvcUserService.findByCodeUser(ValueUtil.getStringByObject(dataAsset.get("codeUser")));
                        if (user.isEmpty()) {
                            throw new UsernameNotFoundException("User not found!");
                        }
                        if (!user.get().isAccountNonLocked()){
                            throw new UsernameNotFoundException("User is locked!");
                        }
                        dataAsset.put("idUser", user.get().getIdUser());
                    }
                    typeModules = bluePrintAssetModulesDto.getTypeModules();
                    idInstance = bluePrintAssetModulesDto.getIdInstance();

                    IModules iModulesDetails = modulesServiceFactory.
                            findDataModulesByTypeModulesAndIdInstance(typeModules,idInstance);
                    ModuleFactory moduleFactory = (ModuleFactory) ProxyInitDataAssetUtil.
                            proxyInitModuleDataAsset(ValueUtil.getStringByObject(dataAsset.get(Constants.KEY_TYPE_MODULE)));
                    IModules iModules = moduleFactory.updateModule(dataAsset,iModulesDetails);
                    modulesServiceFactory.update(ValueUtil.getStringByObject(dataAsset.get(Constants.KEY_TYPE_MODULE)), iModules);
                }
            }
        }

    }

    private void createNewAssetModule(List<BluePrintAssetModulesDto> assetModulesDtos ,
                                      List<HashMap<String,Object>> moduleDataAsset,
                                      Asset asset) throws ValidateFiledException {
        for (HashMap<String,Object> dataAsset: moduleDataAsset){
            boolean checkEqual = false;
            for (BluePrintAssetModulesDto bluePrintAssetModulesDto : assetModulesDtos){
                if (ValueUtil.getStringByObject(dataAsset.get(Constants.KEY_TYPE_MODULE)).
                        equals(bluePrintAssetModulesDto.getTypeModules())) {
                    checkEqual = true;
                    break;
                }
            }
            if (!checkEqual){
                if (StringUtils.isNotBlank(ValueUtil.getStringByObject(dataAsset.get("codeUser")))) {
                    Optional<CsvcUser> user = csvcUserService.findByCodeUser(ValueUtil.getStringByObject(dataAsset.get("codeUser")));
                    if (user.isEmpty()) {
                        throw new UsernameNotFoundException("User not found!");
                    }
                    if (!user.get().isAccountNonLocked()){
                        throw new UsernameNotFoundException("User is locked!");
                    }
                    dataAsset.put("idUser", user.get().getIdUser());
                }
                dataAsset.put("idAsset", asset.getIdAsset());
                ModuleFactory moduleFactory = (ModuleFactory) ProxyInitDataAssetUtil.
                        proxyInitModuleDataAsset(ValueUtil.getStringByObject(dataAsset.get(Constants.KEY_TYPE_MODULE)));
                IModules iModules = moduleFactory.createModule(dataAsset);
                modulesServiceFactory.save(iModules, dataAsset);
                log.info("Finish store module factory " + moduleFactory.getClass());
            }
        }
    }

    private void deleteAssetModule(List<BluePrintAssetModulesDto> bluePrintAssetModulesDtoList,
                                   List<HashMap<String,Object>> modulesDataAsset ) throws ValidateFiledException {
        for (BluePrintAssetModulesDto bluePrintAssetModulesDto : bluePrintAssetModulesDtoList){
            boolean checkEqual = false;
            for (HashMap<String,Object> dataAsset : modulesDataAsset) {
                if (bluePrintAssetModulesDto.getTypeModules().
                        equals(ValueUtil.getStringByObject(dataAsset.get(Constants.KEY_TYPE_MODULE)))) {
                    checkEqual = true;
                    break;
                }
            }
            if (!checkEqual) {
                modulesServiceFactory.deleteModulesByTypeModulesAndIdInstance(bluePrintAssetModulesDto.getTypeModules(),
                        bluePrintAssetModulesDto.getIdInstance(), bluePrintAssetModulesDto.getIdModules());
            }
        }
    }

    private Asset updateCommonDataAsset(Map<String, Object> dataUpdateAssetRequest) {
        Map<String,Object> commonDataAsset = (Map<String, Object>) dataUpdateAssetRequest.get(Constants.KEY_COMMON);
        log.info("Start update common data asset by code asset = " + ValueUtil.getStringByObject(commonDataAsset.get("codeAsset")));
        Optional<Asset> asset = assetRepository.findAssetByCodeAsset(ValueUtil.getStringByObject(commonDataAsset.get("codeAsset")));
        if (asset.isEmpty()) {
            throw new NotFoundException("Don't exits asset by code!");
        }
        updateAttributeAsset(commonDataAsset, asset.get());
        updateOriginalOfFormation(asset.get(), commonDataAsset);
        log.info("Update finished common data asset by code asset = " + asset.get().getCodeAsset());
        return asset.get();
    }

    private void updateOriginalOfFormation(Asset asset, Map<String, Object> commonDataAsset) {
        List<AssetOriginalOfFormation> originalOfFormations =
                assetOriginalOfFormationService.findOriginalOfFormationByIdAsset(asset.getIdAsset());
        List<Map<String,Object>> assetOriginalOfFormationData = (List<Map<String, Object>>) commonDataAsset.get(Constants.KEY_ASSET_ORIGINAL_OF_FORMATION);
        deleteAssetOriginalOfFormation(originalOfFormations, assetOriginalOfFormationData, asset);
        createAssetOriginalOfFormation(originalOfFormations, assetOriginalOfFormationData, asset);
        updateAssetOriginalOfFormation(originalOfFormations, assetOriginalOfFormationData);
    }

    private void updateAssetOriginalOfFormation(List<AssetOriginalOfFormation> originalOfFormations,
                                                List<Map<String, Object>> assetOriginalOfFormationData) {
        for (Map<String, Object> dataOriginalOfFormation : assetOriginalOfFormationData) {
            for (AssetOriginalOfFormation original : originalOfFormations) {
                if (ValueUtil.getIntegerByObject(dataOriginalOfFormation.get("idOriginOfFormation")).
                        equals(original.getIdOriginalOfFormation())) {
                    original.setValue(ValueUtil.getStringByObject(dataOriginalOfFormation.get("value")));
                    original.setTimeModified(String.valueOf(new Date().getTime()));
                    assetOriginalOfFormationService.save(original);
                }
            }
        }
    }

    private void createAssetOriginalOfFormation(List<AssetOriginalOfFormation> originalOfFormations,
                                                List<Map<String, Object>> assetOriginalOfFormationData,
                                                Asset asset) {
        for (Map<String, Object> dataOriginalOfFormation : assetOriginalOfFormationData) {
            boolean isCheckExits = false;
            for (AssetOriginalOfFormation original : originalOfFormations) {
                if (ValueUtil.getIntegerByObject(dataOriginalOfFormation.get("idOriginOfFormation")).
                        equals(original.getIdOriginalOfFormation())) {
                    isCheckExits = true;
                    break;
                }
            }
            if (!isCheckExits){
                createNewAssetOriginalOfFormation(dataOriginalOfFormation,asset);
            }
        }
    }

    private void createNewAssetOriginalOfFormation(Map<String, Object> dataOriginalOfFormation, Asset asset) {
        AssetOriginalOfFormation originalOfFormation = new AssetOriginalOfFormation();
        originalOfFormation.setIdOriginalOfFormation(ValueUtil.getIntegerByObject(dataOriginalOfFormation.get("idOriginOfFormation")));
        originalOfFormation.setIdAsset(asset.getIdAsset());
        String timeCurrent = String.valueOf(new Date().getTime());
        originalOfFormation.setTimeCreated(timeCurrent);
        originalOfFormation.setTimeModified(timeCurrent);
        originalOfFormation.setValue(ValueUtil.getStringByObject(dataOriginalOfFormation.get("value")));
        assetOriginalOfFormationService.save(originalOfFormation);
    }

    private void deleteAssetOriginalOfFormation(List<AssetOriginalOfFormation> originalOfFormations,
                                                List<Map<String, Object>> assetOriginalOfFormationData,
                                                Asset asset) {
        for (AssetOriginalOfFormation original : originalOfFormations){
            boolean isCheckExits = false;
            for (Map<String, Object> dataOriginalOfFormation: assetOriginalOfFormationData){
                if (original.getIdOriginalOfFormation().
                        equals(ValueUtil.getIntegerByObject(dataOriginalOfFormation.get("idOriginOfFormation")))) {
                    isCheckExits = true;
                    break;
                }
            }
            if (!isCheckExits){
                assetOriginalOfFormationService.deleteAssetOriginalOfFormation(original);
            }
        }
    }

    private void updateAttributeAsset(Map<String, Object> commonDataAsset, Asset asset) {
        asset.setName(ValueUtil.getStringByObject(commonDataAsset.get("name")));
        asset.setIdAssetCategory(ValueUtil.getIntegerByObject(commonDataAsset.get("idAssetCategory")));
        asset.setIdDocumentAttack(ValueUtil.getIntegerByObject(commonDataAsset.get("idDocumentAttack")));
        if (!asset.getIdDepartment().equals(ValueUtil.getIntegerByObject(commonDataAsset.get("idDepartment")))) {
            if (StringUtils.isNotBlank(ValueUtil.getStringByObject(commonDataAsset.get("codeDepartment")))) {
                asset.setCodeAsset(ValueUtil.getStringByObject(commonDataAsset.get("codeDepartment")) + "-" + UUID.randomUUID());
            } else {
                asset.setCodeAsset(String.valueOf(UUID.randomUUID()));
            }
        }
        asset.setIdDepartment(ValueUtil.getIntegerByObject(commonDataAsset.get("idDepartment")));
        asset.setIdLocation(ValueUtil.getIntegerByObject(commonDataAsset.get("idLocation")));
        asset.setIdUnit(ValueUtil.getIntegerByObject(commonDataAsset.get("idUnit")));
        asset.setIdProjects(ValueUtil.getIntegerByObject(commonDataAsset.get("idProjects")));
        asset.setPurpose(ValueUtil.getStringByObject(commonDataAsset.get("purpose")));
        asset.setNotes(ValueUtil.getStringByObject(commonDataAsset.get("notes")));
        asset.setDescription(ValueUtil.getStringByObject(commonDataAsset.get("description")));
        asset.setQuantity(ValueUtil.getIntegerByObject(commonDataAsset.get("quantity")));
        asset.setFileAttack(ValueUtil.getStringByObject(commonDataAsset.get("fileAttack")));
        String timeCurrent = String.valueOf(new Date().getTime());
        asset.setTimeModified(timeCurrent);
        asset.setIdDepartmentDefault(ValueUtil.getIntegerByObject(commonDataAsset.get("idDepartmentDefault")));
        asset.setIdLevelTypeAsset(ValueUtil.getIntegerByObject(commonDataAsset.get("idLevelTypeAsset")));
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        asset.setIdUserModified(csvcUser.getIdUser());
        assetRepository.save(asset);
    }

    private void validateDataUpdateAsset(Map<String, Object> dataCreateAssetRequest) {
    }

    private FindDetailsAssetResponse convertToFindDetailsAssetResponse(AssetBluePrintDto assetBluePrintDto) {
        FindDetailsAssetResponse response = new FindDetailsAssetResponse();
        response.setCodeAssetCategory(assetBluePrintDto.getBluePrintParentAssetCategoryDto().getCodeAssetCategory());
        response.setIdPAssetCategory(assetBluePrintDto.getBluePrintParentAssetCategoryDto().getIdAssetCategory());
        response.setNameAssetCategory(assetBluePrintDto.getBluePrintParentAssetCategoryDto().getNameAssetCategory());
        response.setIdParent(assetBluePrintDto.getBluePrintParentAssetCategoryDto().getIdParent());
        response.setCommon(setCommonDetailsAssetResponse(assetBluePrintDto));
        response.setModules(assetBluePrintDto.getModules());
        response.setOriginal(assetBluePrintDto.getOriginal());
        response.setDeclare(assetBluePrintDto.getDeclare());
        response.setAssetDepreciationDto(assetBluePrintDto.getAssetDepreciationDto());
        return response;
    }


    private CommonAssetDto setCommonDetailsAssetResponse(AssetBluePrintDto assetBluePrintDto) {
        CommonAssetDto commonAssetDto = new CommonAssetDto();
        commonAssetDto.setIdAsset(assetBluePrintDto.getIdAsset());
        commonAssetDto.setName(assetBluePrintDto.getName());
        commonAssetDto.setCodeAsset(assetBluePrintDto.getCodeAsset());
        commonAssetDto.setAssetCategory(assetBluePrintDto.getAssetCategory());
        commonAssetDto.setDepartment(assetBluePrintDto.getDepartment());
        commonAssetDto.setDocumentAttack(assetBluePrintDto.getDocumentAttack());
        commonAssetDto.setLocation(assetBluePrintDto.getLocation());
        commonAssetDto.setUnits(assetBluePrintDto.getUnits());
        commonAssetDto.setProjects(assetBluePrintDto.getProjects());
        commonAssetDto.setPurpose(assetBluePrintDto.getPurpose());
        commonAssetDto.setNotes(assetBluePrintDto.getNotes());
        commonAssetDto.setDescription(assetBluePrintDto.getDescription());
        commonAssetDto.setQuantity(assetBluePrintDto.getQuantity());
        commonAssetDto.setFileAttack(assetBluePrintDto.getFileAttack());
        commonAssetDto.setDepartmentDefault(assetBluePrintDto.getDepartmentDefault());
        commonAssetDto.setLevelTypeAsset(assetBluePrintDto.getLevelTypeAsset());
        commonAssetDto.setOriginOfFormation(assetBluePrintDto.getOriginOfFormation());
        commonAssetDto.setIdInstance(assetBluePrintDto.getIdInstance());
        return commonAssetDto;
    }

    private void setDataDeclareDetail(AssetBluePrintDto assetBluePrintDto) throws ValidateFiledException, IllegalAccessException {
        String typeDeclare = assetBluePrintDto.getDeclare().getBluePrintDeclare().getTypeDeclare();
        Integer idInstance = assetBluePrintDto.getDeclare().getBluePrintDeclare().getIdInstance();
        assetBluePrintDto.getDeclare().setDataDetail(declareServiceFactory.findDataDetailByTypeDeclareAndIdInstance(typeDeclare, idInstance));
    }

    private void setDataOriginalDetail(AssetBluePrintDto assetBluePrintDto) throws ValidateFiledException, IllegalAccessException {
        String typeOriginal = assetBluePrintDto.getOriginal().getBluePrintAssetOriginalDto().getTypeOriginal();
        Integer idInstance = assetBluePrintDto.getOriginal().getBluePrintAssetOriginalDto().getIdInstance();
        assetBluePrintDto.getOriginal().setDataDetails(originalServiceFactory.findDataDetailByTypeOriginalAndIdInstance(typeOriginal, idInstance));
    }

    private void setDataModulesDetail(AssetBluePrintDto assetBluePrintDto) throws ValidateFiledException, IllegalAccessException {
        for (AssetModulesDto modulesDto : assetBluePrintDto.getModules()) {
            String typeModules = modulesDto.getBluePrintAssetModules().getTypeModules();
            Integer idInstance = modulesDto.getBluePrintAssetModules().getIdInstance();
            modulesDto.setDataDetails( modulesServiceFactory.findDataDetailByTypeModulesAndIdInstance(typeModules, idInstance));
        }
    }

    private void setOriginalOfFormation(AssetBluePrintDto assetBluePrintDto) {
        assetBluePrintDto.
                setOriginOfFormation(assetOriginalOfFormationService.
                        findOriginalOfFormationDtoByIdAsset(assetBluePrintDto.getIdAsset()));
    }

    private void setParentAssetCategory(AssetBluePrintDto assetBluePrintDto){
        assetBluePrintDto.setBluePrintParentAssetCategoryDto(
                        assetCategoriesService.findBluePrintParentAssetCategoryDtoById(assetBluePrintDto.getIdInstance()));
    }

    private List<FindAllAssetResponse> convertToFindAllAssetResponse(List<FindAllAssetDto> collect) {
        List<FindAllAssetResponse> responses = new ArrayList<>();
        for (FindAllAssetDto dto : collect) {
            FindAllAssetResponse response = new FindAllAssetResponse();
            response.setCodeAsset(dto.getCodeAsset());
            response.setNameAsset(dto.getNameAsset());
            response.setNameAssetCategory(dto.getNameAssetCategory());
            response.setCodeAssetCategory(dto.getCodeAssetCategory());
            response.setCodeDepartment(dto.getCodeDepartment());
            response.setNameDepartment(dto.getNameDepartment());
            response.setTimeCreated(DateUtil.formatToPattern(
                    DateUtil.formatDatePattern(dto.getTimeCreated(),
                            DateUtil.DATE_FORMAT),DateUtil.DATE_FORMAT_HH_MM));
            response.setTimeModified(DateUtil.formatToPattern(
                    DateUtil.formatDatePattern(dto.getTimeModified(),
                            DateUtil.DATE_FORMAT),DateUtil.DATE_FORMAT_HH_MM));
            responses.add(response);
        }
        return responses;
    }

    private void validateDataCreateAsset(Map<String, Object> createAssetRequest) throws ValidateFiledException {
        validateDataCommonCreateAsset(createAssetRequest);
        validateDataModuleCreateAsset(createAssetRequest);
        validateDataOriginalCreateAsset(createAssetRequest);
        validateDataDeclareCreateAsset(createAssetRequest);
    }

    private void validateDataDeclareCreateAsset(Map<String, Object> createAssetRequest) {
        declareServiceFactory.validateDataDeclare((Map<String, Object>) createAssetRequest.get(Constants.KEY_DECLARE_ASSET));
    }

    private void validateDataOriginalCreateAsset(Map<String, Object> createAssetRequest) {
       originalServiceFactory.validateDataOriginal((Map<String, Object>) createAssetRequest.get(Constants.KEY_ORIGINAL_ASSET));
    }

    private void validateDataModuleCreateAsset(Map<String, Object> createAssetRequest) throws ValidateFiledException {
        modulesServiceFactory.validateDataModules((List<Map<String,Object>>) createAssetRequest.get(Constants.KEY_MODULE));
    }

    private void validateDataCommonCreateAsset(Map<String, Object> createAssetRequest) {
        Map<String,Object> commonDataAsset = (Map<String, Object>) createAssetRequest.get(Constants.KEY_COMMON);
        Integer idDepartment = ValueUtil.getIntegerByObject(commonDataAsset.get("idDepartment"));
        departmentService.findDepartmentByIdDepartmentAndStatus(idDepartment, Constants.DEPARTMENT_ACTIVE_STATUS);
        Integer idLocation = ValueUtil.getIntegerByObject(commonDataAsset.get("idLocation"));
        locationService.findLocationByIdLocationAndIdDepartmentAndVisible(idLocation, idDepartment, Constants.LOCATION_ACTIVE_STATUS);
        Integer idAssetCategory = ValueUtil.getIntegerByObject(commonDataAsset.get("idAssetCategory"));
        assetCategoriesService.findAssetCategoriesByVisibleAndIdAssetCategory(idAssetCategory, Constants.ASSET_CATEGORY_IS_VISIBLE);
        Integer idUnit = ValueUtil.getIntegerByObject(commonDataAsset.get("idUnit"));
        unitsService.findUnitsByIdUnitAndStatus(idUnit, Constants.UNITS_IS_ACTIVE);
        Integer idDocumentsAttack = ValueUtil.getIntegerByObject(commonDataAsset.get("idDocumentAttack"));
        documentAttackService.findDocumentAttackByIdDocumentAndStatus(idDocumentsAttack, Constants.DOCUMENT_ATTACK_ACTIVE_STATUS);
        Integer idProject = ValueUtil.getIntegerByObject(commonDataAsset.get("idProjects"));
        projectsService.findProjectsByIdProjectAndStatus(idProject, Constants.PROJECTS_IS_VISIBLE);
    }

    private void storeNewAsset(Map<String, Object> createAssetRequest) throws ValidateFiledException {
        log.info("Init store asset");
        Asset asset = storeCommonData(createAssetRequest);
        storeDepreciation(createAssetRequest, asset);
        storeModulesDataAsset(createAssetRequest, asset);
        storeOriginalDataAsset(createAssetRequest, asset);
        storeDeclareDataAsset(createAssetRequest, asset);
    }

    private void storeDepreciation(Map<String, Object> createAssetRequest, Asset asset) {
        log.info("Storing depreciation data asset");
        Map<String, Object> depreciationAsset = (Map<String, Object>) createAssetRequest.get(Constants.KEY_DEPRECIATION);
        AssetDepreciation depreciation = contructionDataAssetDepreciation(depreciationAsset, asset.getIdAsset());
        assetDepreciationService.save(depreciation);
        log.info("Stored success depreciation data asset");
    }

    private AssetDepreciation contructionDataAssetDepreciation(Map<String, Object> depreciationAsset, Integer idAsset) {
        AssetDepreciation assetDepreciation = new AssetDepreciation();
        assetDepreciation.setIdAsset(idAsset);
        assetDepreciation.setTimeStartedDepreciation(ValueUtil.getStringByObject(depreciationAsset.get("timeStartedDepreciation")));
        assetDepreciation.setAmountMonthsDepreciation(ValueUtil.getIntegerByObject(depreciationAsset.get("amountMonthsDepreciation")));
        assetDepreciation.setValueDepreciation(ValueUtil.getStringByObject(depreciationAsset.get("valueDepreciation")));
        assetDepreciation.setTypeDepreciation(ValueUtil.getIntegerByObject(depreciationAsset.get("typeDepreciation")));
        assetDepreciation.setValueTypeDepreciation(ValueUtil.getStringByObject(depreciationAsset.get("valueTypeDepreciation")));
        assetDepreciation.setAmountRestMonthsDepreciation(ValueUtil.getIntegerByObject(depreciationAsset.get("amountRestMonthsDepreciation")));
        assetDepreciation.setCumulative(ValueUtil.getStringByObject(depreciationAsset.get("cumulative")));
        assetDepreciation.setRestValue(ValueUtil.getStringByObject(depreciationAsset.get("restValue")));
        assetDepreciation.setTimeStartedWearTear(ValueUtil.getStringByObject(depreciationAsset.get("timeStartedWearTear")));
        assetDepreciation.setTimeEndWearTear(ValueUtil.getStringByObject(depreciationAsset.get("timeEndWearTear")));
        assetDepreciation.setTypeCalculate(ValueUtil.getIntegerByObject(depreciationAsset.get("typeCalculate")));
        assetDepreciation.setTimeBuy(ValueUtil.getStringByObject(depreciationAsset.get("timeBuy")));
        assetDepreciation.setTimeStartedUsed(ValueUtil.getStringByObject(depreciationAsset.get("timeStartedUsed")));
        assetDepreciation.setTimeStartedIncrease(ValueUtil.getStringByObject(depreciationAsset.get("timeStartedIncrease")));
        assetDepreciation.setTimeYearTracking(ValueUtil.getStringByObject(depreciationAsset.get("timeYearTracking")));
        String timeCurrent = String.valueOf(new Date().getTime());
        assetDepreciation.setTimeCreated(timeCurrent);
        assetDepreciation.setTimeModified(timeCurrent);
        return assetDepreciation;
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
        List<HashMap<String,Object>> modulesDataAsset = (List<HashMap<String,Object>>) createAssetRequest.get(Constants.KEY_MODULE);
        if (!CollectionUtils.isEmpty(modulesDataAsset)){
            for (HashMap<String, Object> moduleDataAsset : modulesDataAsset) {
                if (StringUtils.isNotBlank(ValueUtil.getStringByObject(moduleDataAsset.get("codeUser")))){
                    Optional<CsvcUser> user = csvcUserService.findByCodeUser(ValueUtil.getStringByObject(moduleDataAsset.get("codeUser")));
                    if (user.isEmpty()) {
                        throw new UsernameNotFoundException("User not found!");
                    }
                    if (!user.get().isAccountNonLocked()){
                        throw new UsernameNotFoundException("User is locked!");
                    }
                    moduleDataAsset.put("idUser", user.get().getIdUser());
                }
                moduleDataAsset.put("idAsset", asset.getIdAsset());
                ModuleFactory moduleFactory = (ModuleFactory) ProxyInitDataAssetUtil.
                        proxyInitModuleDataAsset(ValueUtil.getStringByObject(moduleDataAsset.get(Constants.KEY_TYPE_MODULE)));
                IModules iModules = moduleFactory.createModule(moduleDataAsset);
                modulesServiceFactory.save(iModules, moduleDataAsset);
                log.info("Finish store module factory " + moduleFactory.getClass());
            }
        }
    }

    private Asset storeCommonData(Map<String, Object> createAssetRequest) {
        log.info("Storing common data asset");
        Map<String,Object> commonDataAsset = (Map<String, Object>) createAssetRequest.get(Constants.KEY_COMMON);
        Asset asset = contructionDataAsset(commonDataAsset);
        assetRepository.save(asset);
        saveAssetOriginalOfFormations(asset, commonDataAsset);
        log.info("Store success common data asset by id = " + asset.getIdAsset());
        return asset;
    }

    private void saveAssetOriginalOfFormations(Asset asset, Map<String, Object> commonDataAsset) {
        List<HashMap<String,Object>> assetOriginalOfFormationData = (List<HashMap<String,Object>>) commonDataAsset.get(Constants.KEY_ASSET_ORIGINAL_OF_FORMATION);
        if (!CollectionUtils.isEmpty(assetOriginalOfFormationData)){
            List<AssetOriginalOfFormation>originalOfFormations = new ArrayList<>();
            String currentTime = String.valueOf(new Date().getTime());
            assetOriginalOfFormationData.forEach(obj -> {
                AssetOriginalOfFormation originalOfFormation = new AssetOriginalOfFormation();
                originalOfFormation.setIdOriginalOfFormation(ValueUtil.getIntegerByObject(obj.get("idOriginOfFormation")));
                originalOfFormation.setIdAsset(asset.getIdAsset());
                originalOfFormation.setTimeCreated(currentTime);
                originalOfFormation.setTimeModified(currentTime);
                originalOfFormation.setValue(ValueUtil.getStringByObject(obj.get("value")));
                originalOfFormations.add(originalOfFormation);
            });
            assetOriginalOfFormationService.saveAll(originalOfFormations);
        }
    }

    private Asset contructionDataAsset(Map<String, Object> dataAsset) {
        Asset asset = new Asset();
        asset.setName(ValueUtil.getStringByObject(dataAsset.get("name")));
        asset.setIdAssetCategory(ValueUtil.getIntegerByObject(dataAsset.get("idAssetCategory")));
        if (StringUtils.isNotBlank(ValueUtil.getStringByObject(dataAsset.get("codeDepartment")))) {
            asset.setCodeAsset(ValueUtil.getStringByObject(dataAsset.get("codeDepartment")) + "-" + UUID.randomUUID());
        } else {
            asset.setCodeAsset(String.valueOf(UUID.randomUUID()));
        }
        asset.setIdDocumentAttack(ValueUtil.getIntegerByObject(dataAsset.get("idDocumentAttack")));
        asset.setIdDepartment(ValueUtil.getIntegerByObject(dataAsset.get("idDepartment")));
        asset.setIdLocation(ValueUtil.getIntegerByObject(dataAsset.get("idLocation")));
        asset.setIdUnit(ValueUtil.getIntegerByObject(dataAsset.get("idUnit")));
        asset.setIdProjects(ValueUtil.getIntegerByObject(dataAsset.get("idProjects")));
        asset.setPurpose(ValueUtil.getStringByObject(dataAsset.get("purpose")));
        asset.setNotes(ValueUtil.getStringByObject(dataAsset.get("notes")));
        asset.setDescription(ValueUtil.getStringByObject(dataAsset.get("description")));
        asset.setQuantity(ValueUtil.getIntegerByObject(dataAsset.get("quantity")));
        asset.setFileAttack(ValueUtil.getStringByObject(dataAsset.get("fileAttack")));
        String timeCurrent = String.valueOf(new Date().getTime());
        asset.setTimeCreated(timeCurrent);
        asset.setTimeModified(timeCurrent);
        asset.setIdDepartmentDefault(ValueUtil.getIntegerByObject(dataAsset.get("idDepartmentDefault")));
        asset.setIdLevelTypeAsset(ValueUtil.getIntegerByObject(dataAsset.get("idLevelTypeAsset")));
        asset.setIdInstance(ValueUtil.getIntegerByObject(dataAsset.get("idInstance")));
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        asset.setIdUserCreated(csvcUser.getIdUser());
        asset.setIdUserModified(csvcUser.getIdUser());
        asset.setIdDepartmentOrigin(csvcUserService.getInformationUser().getIdDepartment());
        return asset;
    }

    @Override
    public Resource downloadFileTemplateImportAsset() throws IOException {
        return filesStorageService.downLoadFileImportAsset();
    }


    //Upload File CSV Sample
    public void uploadFileAsset(MultipartFile file) throws FileExcelException, ValidateFiledException, JsonProcessingException {
    ValidateExcelUtils.checkFileExcel(file);
    List<Map<String, Object>> assetRequests = handleUploadFileAsset(file);
    for (Map<String, Object> createAssetRequest : assetRequests) {
        createAssetFromFile(createAssetRequest);
        }
    }
    public void createAssetFromFile(Map<String, Object> createAssetRequest) throws JsonProcessingException, ValidateFiledException {
        Map<String, Object> dataCreateAssetRequest =
                objectMapper.readValue(JSONObjectUtils.toJSONString(createAssetRequest), Map.class);
//        validateDataCreateAsset(dataCreateAssetRequest);
        storeNewAssetFromFile(dataCreateAssetRequest);
    }
    private void storeNewAssetFromFile(Map<String, Object> createAssetRequest) throws ValidateFiledException {
        log.info("Init store asset");
        Asset asset = storeCommonData(createAssetRequest);
//        storeModulesDataAsset(createAssetRequest, asset);
        storeOriginalDataAsset(createAssetRequest, asset);
//        storeDeclareDataAsset(createAssetRequest, asset);
        storeDepreciation(createAssetRequest, asset);

    }




    public Integer extractIdCategoryFromExcel(String input) {
        if (input != null) {
            String firstPart = input.split("_")[0];
            if (firstPart.startsWith("Stt")) {
                String numberStr = firstPart.replaceAll("[^0-9]", "");
                if (!numberStr.isEmpty()) {
                    return Integer.parseInt(numberStr);
                }
            }
        }
        return null;
    }
    public Integer extractIdValueFromExcel(String input) {
        if (input != null && input.contains(".")) {
            String firstPart = input.split("\\.")[0];
            if (!firstPart.isEmpty()) {
                return Integer.parseInt(firstPart);
            }
        }
        return null;
    }

    private List<Map<String, Object>> handleUploadFileAsset(MultipartFile file) {
        List<Map<String, Object>> assetRequests = new ArrayList<>();
        int indexSheet = 0;
        int indexRowStartToReadData = 3;

        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(indexSheet);
            int totalRow = xssfSheet.getLastRowNum();
            List<XSSFRow> allRows = new ArrayList<>();

            for (int i = indexRowStartToReadData; i <= totalRow; i++) {
                XSSFRow row = xssfSheet.getRow(i);
                if (row != null) {
                    allRows.add(row);
                }
            }


            int batchSize = Constants.SIZE_HANDLE;
            for (int start = 0; start < allRows.size(); start += batchSize) {
                List<Integer> instanceCategoryNames = new ArrayList<>();
                List<Integer> categoryNames = new ArrayList<>();

                for (int i = start; i < Math.min(start + batchSize, allRows.size()); i++) {
                    XSSFRow row = allRows.get(i);
                    String idInstanceCategory = (String) ExcelUtil.convertValue(row.getCell(0), CellType.STRING);
                    String idCategory = (String) ExcelUtil.convertValue(row.getCell(1), CellType.STRING);
                    if (idInstanceCategory != null) {

                        instanceCategoryNames.add(extractIdCategoryFromExcel(idInstanceCategory));
                    }
                    if (idCategory != null) {
                        categoryNames.add(extractIdValueFromExcel(idCategory));
                    }
                }

                List<AssetCategories> assetInstanceCategoryNamesList = assetCategoriesRepository.findAllAssetCategoriesByIdIn(instanceCategoryNames);
                List<AssetCategories> assetCategoriesList = assetCategoriesRepository.findAllAssetCategoriesByIdIn(categoryNames);
                if (instanceCategoryNames.size() != assetInstanceCategoryNamesList.size()) {
                    // Log error or throw an exception
                    throw new RuntimeException("quantity is other");
                }

                if (categoryNames.size() != assetCategoriesList.size()) {
                    throw new RuntimeException("quantity is other");
                }
//                Map<String, AssetCategories> assetInstanceCategoriesMap = assetInstanceCategoryNamesList.stream()
//                        .collect(Collectors.toMap(AssetCategories::getName, Function.identity(), (existing, replacement) -> existing));
//                Map<String, AssetCategories> assetCategoriesMap = assetCategoriesList.stream()
//                        .collect(Collectors.toMap(AssetCategories::getName, Function.identity(), (existing, replacement) -> existing));


                for (int i = start; i < Math.min(start + batchSize, allRows.size()); i++) {
                    XSSFRow row = allRows.get(i);
                    if (row != null) {
                        assetRequests.add(convertExcelRowToMap(row));
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return assetRequests;
    }


    private Map<String, Object> convertExcelRowToMap(XSSFRow row) {
        Map<String, Object> createAssetRequest = new HashMap<>();

        // Gọi hàm xử lý commonData
        Map<String, Object> commonData = processCommonData(row);
        createAssetRequest.put(Constants.KEY_COMMON, commonData);

        // Gọi hàm xử lý modulesDataAsset
        List<Map<String, Object>> modulesDataAsset = processModulesData(row, commonData);
        createAssetRequest.put(Constants.KEY_MODULE, modulesDataAsset);

        Map<String, Object> DeclareData = processDeclareData(row,commonData);
        createAssetRequest.put(Constants.KEY_DECLARE_ASSET, DeclareData);

        Map<String, Object> originalData = processOriginalData(row);
        createAssetRequest.put(Constants.KEY_ORIGINAL_ASSET, originalData);

        Map<String, Object> depreciationData = processDepreciationData(row);
        createAssetRequest.put(Constants.KEY_DEPRECIATION, depreciationData);
        return createAssetRequest;

    }


    private Map<String, Object> processCommonData(XSSFRow row) {
        Map<String, Object> commonData = new HashMap<>();

        String instanceCategory = (String) ExcelUtil.convertValue(row.getCell(0), CellType.STRING);
        String category = (String) ExcelUtil.convertValue(row.getCell(1), CellType.STRING);
        String nameAsset = (String) ExcelUtil.convertValue(row.getCell(2), CellType.STRING);
        String department = (String) ExcelUtil.convertValue(row.getCell(3), CellType.STRING);
        String location = (String) ExcelUtil.convertValue(row.getCell(4), CellType.STRING);
        String unit = (String) ExcelUtil.convertValue(row.getCell(5), CellType.STRING);
        String documentAttack = (String) ExcelUtil.convertValue(row.getCell(6), CellType.STRING);
        String project = (String) ExcelUtil.convertValue(row.getCell(7), CellType.STRING);
        String purpose = (String) ExcelUtil.convertValue(row.getCell(8), CellType.STRING);
        String levelTypeAsset = (String) ExcelUtil.convertValue(row.getCell(9), CellType.STRING);
        String description = (String) ExcelUtil.convertValue(row.getCell(10), CellType.STRING);
        String departmentDefault = (String) ExcelUtil.convertValue(row.getCell(11), CellType.STRING);
        String notes = (String) ExcelUtil.convertValue(row.getCell(12), CellType.STRING);
//        String original = (String) ExcelUtil.convertValue(row.getCell(13), CellType.STRING);
        String originalOfFormationName = (String) ExcelUtil.convertValue(row.getCell(14), CellType.STRING);
        String originalOfFormationValues = (String) ExcelUtil.convertValue(row.getCell(15), CellType.STRING);

//        Optional<AssetCategories> assetCategoriesInstanceOptional=assetCategoriesRepository.findAssetCategoryByName(instanceCategory);
//        Optional<AssetCategories> assetCategoriesOptional=assetCategoriesRepository.findAssetCategoryByName(category);
        Optional<Department> departmentOptional=departmentRepository.findDepartmentById(extractIdValueFromExcel(department));
//        Optional<Location> locationOptional=locationRepository.findLocationByName(location);
//        Optional<Units> unitsOptional = unitsRepository.findUnitByName(unit);
//        Optional<DocumentAttack> documentAttackOptional=documentAttackRepository.findDocumentAttackByName(documentAttack);
//        Optional<Projects> projectsOptional=projectsRepository.findProjectByName(project);
//        Optional<Department> departmentDefaultOptional=departmentRepository.findDepartmentByName(departmentDefault);
//        Optional<Original> originalOptional=originalRepository.findOriginalByName(original);


        String codeAsset= ValueUtil.getStringByObject(departmentOptional.get().getCode()) + "-" + UUID.randomUUID();
        String[] originOfFormationNameArray = originalOfFormationName.split(";");
        String[] originOfFormationValuesArray = originalOfFormationValues.split(";");
        List<Map<String, Object>> originOfFormationList = new ArrayList<>();
        for (int i = 0; i < originOfFormationNameArray.length; i++) {
            Map<String, Object> originOfFormation = new HashMap<>();
            Optional<OriginalOfFormation> originalOfFormationOptional=originalOfFormationRepository.findOriginalOfFormationByName(originOfFormationNameArray[i]);
            originOfFormation.put("idOriginOfFormation", originalOfFormationOptional.map(OriginalOfFormation::getIdOriginalOfFormation).orElse(null));
            originOfFormation.put("value", originOfFormationValuesArray[i]);
            originOfFormationList.add(originOfFormation);
        }


        commonData.put("codeAsset", codeAsset);
        commonData.put("name", nameAsset);
        commonData.put("idAssetCategory", extractIdValueFromExcel(category));
//        commonData.put("idInstance", assetInstanceCategoriesMap.get(instanceCategory).getIdAssetCategory());
        commonData.put("idInstance", extractIdCategoryFromExcel(instanceCategory));
        commonData.put("idDepartment", extractIdValueFromExcel(department));
        commonData.put("codeDepartment", departmentOptional.map(Department::getCode).orElse(null));
        commonData.put("idLocation", extractIdValueFromExcel(location));
        commonData.put("idUnit", extractIdValueFromExcel(unit));
        commonData.put("idDocumentAttack", extractIdValueFromExcel(documentAttack));
        commonData.put("idProjects", extractIdValueFromExcel(project));
        commonData.put("idLevelTypeAsset", extractIdValueFromExcel(levelTypeAsset));
        commonData.put("originOfFormation", originOfFormationList);

        commonData.put("purpose", purpose);
        commonData.put("notes", notes);
        commonData.put("description", description);
        commonData.put("quantity", 1);
        commonData.put("idDepartmentDefault", extractIdValueFromExcel(departmentDefault));

        return commonData;
    }
    public String extractCodeValueFromExcel(String input) {
        if (input != null && input.contains(".")) {
            return input.split("\\.")[0]; // Escape dấu chấm
        }
        return input;
    }
    private List<Map<String, Object>> processModulesData(XSSFRow row, Map<String, Object> commonData) {
        List<Map<String, Object>> modulesDataAsset = new ArrayList<>();

//        Optional<AssetCategories> assetCategoriesInstanceOptional = assetCategoriesRepository.findAssetCategoryById((Integer) commonData.get("idInstance"));
        List<Modules> ModulesArray = modulesRepository.findAllModulesByIdAssetCategoryAndStatus((Integer) commonData.get("idInstance"), 1);

        for (Modules modules : ModulesArray) {
            String typeModules = modules.getHardCode();
            Map<String, Object> moduleDataDetails = new HashMap<>();
            moduleDataDetails.put("typeModules", typeModules);



            switch (typeModules) {
                case "MachineModule":
                    String countryProducer = (String)ExcelUtil.convertValue(row.getCell(20), CellType.STRING);
//                    Optional<CountryProducer> countryProducerOptional=countryProducerRepository.findCountryProducerByNameAndStatus(countryProducer,1);
                    String userNameMachine = (String) ExcelUtil.convertValue(row.getCell(21), CellType.STRING);
                    Optional<CsvcUser> userOptionalMachine = csvcUserService.findByUserName(userNameMachine);
                    String typeUse = (String) ExcelUtil.convertValue(row.getCell(22), CellType.STRING);
//                    Optional<TypeUse> typeUseOptional=typeUseRepository.findTypeUseByName(typeUse);
                    moduleDataDetails.put("labelMachine", ExcelUtil.convertValue(row.getCell(16), CellType.STRING));
                    moduleDataDetails.put("model", ExcelUtil.convertValue(row.getCell(17), CellType.STRING));
                    moduleDataDetails.put("serial", ExcelUtil.convertValue(row.getCell(18), CellType.STRING));
                    moduleDataDetails.put("publishDate", ExcelUtil.convertValue(row.getCell(19), CellType.STRING));
                    moduleDataDetails.put("idCountryProducer",  extractIdValueFromExcel(countryProducer));
                    moduleDataDetails.put("codeUser",  userOptionalMachine.isPresent() ? userOptionalMachine.get().getCodeUser() : null);
                    moduleDataDetails.put("idTypeUse", extractIdValueFromExcel(typeUse));

                    break;

                case "GroundModule":
                    String provinceGround = (String)ExcelUtil.convertValue(row.getCell(23), CellType.STRING);
                    String districtGround = (String)ExcelUtil.convertValue(row.getCell(24), CellType.STRING);
                    String wardGround = (String)ExcelUtil.convertValue(row.getCell(25), CellType.STRING);
                    moduleDataDetails.put("provinceCode", extractCodeValueFromExcel(provinceGround));
                    moduleDataDetails.put("districtCode", extractCodeValueFromExcel(districtGround));
                    moduleDataDetails.put("wardCode", extractCodeValueFromExcel(wardGround));
                    moduleDataDetails.put("addressDetail", ExcelUtil.convertValue(row.getCell(26), CellType.STRING));
                    break;
                case "HouseModule":
                    String provinceHouse = (String)ExcelUtil.convertValue(row.getCell(29), CellType.STRING);
                    String districtHouse  = (String)ExcelUtil.convertValue(row.getCell(30), CellType.STRING);
                    String wardHouse  = (String)ExcelUtil.convertValue(row.getCell(31), CellType.STRING);
                    String houseBelongLand = (String) ExcelUtil.convertValue(row.getCell(28), CellType.STRING);
                    moduleDataDetails.put("isManageGround", ExcelUtil.convertValue(row.getCell(27), CellType.STRING));
                    moduleDataDetails.put("idInstance", extractIdValueFromExcel(houseBelongLand));
                    moduleDataDetails.put("provinceCode", extractCodeValueFromExcel(provinceHouse));
                    moduleDataDetails.put("districtCode", extractCodeValueFromExcel(districtHouse));
                    moduleDataDetails.put("wardCode", extractCodeValueFromExcel(wardHouse));
                    moduleDataDetails.put("addressDetail", ExcelUtil.convertValue(row.getCell(32), CellType.STRING));
                    moduleDataDetails.put("floorsNumber", ExcelUtil.convertValue(row.getCell(33), CellType.STRING));
                    moduleDataDetails.put("acreage", ExcelUtil.convertValue(row.getCell(34), CellType.STRING));
                    moduleDataDetails.put("publishYear", ExcelUtil.convertValue(row.getCell(35), CellType.STRING));
                    break;
                case "ArchitectureModule":
                    String countryProducerArchitecture = (String)ExcelUtil.convertValue(row.getCell(41), CellType.STRING);
//                    Optional<CountryProducer> countryProducerArchitectureOptional=countryProducerRepository.findCountryProducerByNameAndStatus(countryProducerArchitecture,1);
                    String architectureBelongLand = (String) ExcelUtil.convertValue(row.getCell(36), CellType.STRING);
                    moduleDataDetails.put("idInstance", extractIdValueFromExcel(architectureBelongLand));
                    moduleDataDetails.put("length", ExcelUtil.convertValue(row.getCell(37), CellType.STRING));
                    moduleDataDetails.put("acreage", ExcelUtil.convertValue(row.getCell(38), CellType.STRING));
                    moduleDataDetails.put("volume", ExcelUtil.convertValue(row.getCell(39), CellType.STRING));
                    moduleDataDetails.put("publishDate", ExcelUtil.convertValue(row.getCell(40), CellType.STRING));
                    moduleDataDetails.put("idCountryProducer", extractIdValueFromExcel(countryProducerArchitecture));
                    break;
                case "CarModule":
                    String countryProducerCar = (String)ExcelUtil.convertValue(row.getCell(55), CellType.STRING);
//                    Optional<CountryProducer> countryProducerCarOptional=countryProducerRepository.findCountryProducerByNameAndStatus(countryProducerCar,43);
                    String userNameCar= (String) ExcelUtil.convertValue(row.getCell(61), CellType.STRING);
                    Optional<CsvcUser> userOptionalCar = csvcUserService.findByUserName(userNameCar);
                    String typeUseCar = (String) ExcelUtil.convertValue(row.getCell(62), CellType.STRING);
//                    Optional<TypeUse> typeUseOptionalCar=typeUseRepository.findTypeUseByName(typeUseCar);
                    String positionNameCar = (String) ExcelUtil.convertValue(row.getCell(63), CellType.STRING);
                    String positionNameOtherCar = (String) ExcelUtil.convertValue(row.getCell(64), CellType.STRING);

                    Object valueIsFreeTax;
                    valueIsFreeTax = (String)ExcelUtil.convertValue(row.getCell(42), CellType.STRING);
                    if (valueIsFreeTax != null && valueIsFreeTax.equals("Có")) {
                        moduleDataDetails.put("isFreeTax", 1);
                    }
                    else{
                        moduleDataDetails.put("isFreeTax", -1);
                    }

                    moduleDataDetails.put("valueTax", ExcelUtil.convertValue(row.getCell(43), CellType.STRING));
                    moduleDataDetails.put("licensePlate", ExcelUtil.convertValue(row.getCell(44), CellType.STRING));
                    moduleDataDetails.put("labelCar", ExcelUtil.convertValue(row.getCell(45), CellType.STRING));
                    moduleDataDetails.put("typeCar", ExcelUtil.convertValue(row.getCell(46), CellType.STRING));
                    moduleDataDetails.put("loadCapacity", ExcelUtil.convertValue(row.getCell(47), CellType.STRING));
                    moduleDataDetails.put("numberSeats", ExcelUtil.convertValue(row.getCell(48), CellType.STRING));
                    moduleDataDetails.put("capacity", ExcelUtil.convertValue(row.getCell(49), CellType.STRING));
                    moduleDataDetails.put("cylinderCapacity", ExcelUtil.convertValue(row.getCell(50), CellType.STRING));
                    moduleDataDetails.put("clutchNumber", ExcelUtil.convertValue(row.getCell(51), CellType.STRING));
                    moduleDataDetails.put("vehicleIdentificationNumber", ExcelUtil.convertValue(row.getCell(52), CellType.STRING));
                    moduleDataDetails.put("machineNumber", ExcelUtil.convertValue(row.getCell(53), CellType.STRING));
                    moduleDataDetails.put("publishYear", ExcelUtil.convertValue(row.getCell(54), CellType.STRING));
                    moduleDataDetails.put("idCountryProducer",  extractIdValueFromExcel(countryProducerCar));
                    moduleDataDetails.put("licenseCertificateRegister", ExcelUtil.convertValue(row.getCell(56), CellType.STRING));
                    moduleDataDetails.put("publishDateLicense", ExcelUtil.convertValue(row.getCell(57), CellType.STRING));
                    moduleDataDetails.put("companyRegister", ExcelUtil.convertValue(row.getCell(58), CellType.STRING));
                    moduleDataDetails.put("source", ExcelUtil.convertValue(row.getCell(59), CellType.STRING));
                    moduleDataDetails.put("color", ExcelUtil.convertValue(row.getCell(60), CellType.STRING));
                    moduleDataDetails.put("codeUser",  userOptionalCar.isPresent() ? userOptionalCar.get().getCodeUser() : null);
                    moduleDataDetails.put("idTypeUse", extractIdValueFromExcel(typeUseCar));
                    moduleDataDetails.put("idPositionName", extractIdValueFromExcel(positionNameCar));
                    moduleDataDetails.put("idPositionNameOther", extractIdValueFromExcel(positionNameOtherCar));
                    break;
                case "OtherVehicleTransportModule":
                    String countryProducerOtherVehicle = (String)ExcelUtil.convertValue(row.getCell(74), CellType.STRING);
//                    Optional<CountryProducer> countryProducerOtherVehicleOptional=countryProducerRepository.findCiountryProducerByNameAndStatus(countryProducerOtherVehicle,1);
                    String userNameOtherVehicle= (String) ExcelUtil.convertValue(row.getCell(80), CellType.STRING);
                    Optional<CsvcUser> userOptionalOtherVehicle = csvcUserService.findByUserName(userNameOtherVehicle);
                    String typeUseOtherVehicle = (String) ExcelUtil.convertValue(row.getCell(81), CellType.STRING);
//                    Optional<TypeUse> typeUseOptionalOtherVehicle=typeUseRepository.findTypeUseByName(typeUseOtherVehicle);
                    String positionNameVehicle = (String) ExcelUtil.convertValue(row.getCell(82), CellType.STRING);
                    moduleDataDetails.put("licensePlate", ExcelUtil.convertValue(row.getCell(65), CellType.STRING));
                    moduleDataDetails.put("label", ExcelUtil.convertValue(row.getCell(66), CellType.STRING));
                    moduleDataDetails.put("loadCapacity", ExcelUtil.convertValue(row.getCell(67), CellType.STRING));
                    moduleDataDetails.put("numberSeats", ExcelUtil.convertValue(row.getCell(68), CellType.STRING));
                    moduleDataDetails.put("capacity", ExcelUtil.convertValue(row.getCell(69), CellType.STRING));
                    moduleDataDetails.put("cylinderCapacity", ExcelUtil.convertValue(row.getCell(70), CellType.STRING));
                    moduleDataDetails.put("clutchNumber", ExcelUtil.convertValue(row.getCell(71), CellType.STRING));
//                    moduleDataDetails.put("vehicleIdentificationNumber", ExcelUtil.convertValue(row.getCell(72), CellType.STRING));
                    moduleDataDetails.put("machineNumber", ExcelUtil.convertValue(row.getCell(72), CellType.STRING));
                    moduleDataDetails.put("publishYear", ExcelUtil.convertValue(row.getCell(73), CellType.STRING));
                    moduleDataDetails.put("idCountryProducer",  extractIdValueFromExcel(countryProducerOtherVehicle));
                    moduleDataDetails.put("licenseCertificateRegister", ExcelUtil.convertValue(row.getCell(75), CellType.STRING));
                    moduleDataDetails.put("publishDateLicense", ExcelUtil.convertValue(row.getCell(76), CellType.STRING));
                    moduleDataDetails.put("companyRegister", ExcelUtil.convertValue(row.getCell(77), CellType.STRING));
                    moduleDataDetails.put("source", ExcelUtil.convertValue(row.getCell(78), CellType.STRING));
                    moduleDataDetails.put("color", ExcelUtil.convertValue(row.getCell(79), CellType.STRING));
                    moduleDataDetails.put("codeUser",  userOptionalOtherVehicle.isPresent() ? userOptionalOtherVehicle.get().getCodeUser() : null);
                    moduleDataDetails.put("idTypeUse", extractIdValueFromExcel(typeUseOtherVehicle));
                    moduleDataDetails.put("idPositionName", extractIdValueFromExcel(positionNameVehicle));
                    break;

                case "TreeAndAnimalModule":
                    String countryProducerTreeAndAnimal = (String)ExcelUtil.convertValue(row.getCell(84), CellType.STRING);
//                    Optional<CountryProducer> countryProducerTreeAndAnimalOptional=countryProducerRepository.findCountryProducerByNameAndStatus(countryProducerTreeAndAnimal,1);
                    String typeUseTreeAndAnimal = (String) ExcelUtil.convertValue(row.getCell(85), CellType.STRING);
//                    Optional<TypeUse> typeUseOptionalTreeAndAnimal=typeUseRepository.findTypeUseByName(typeUseTreeAndAnimal);
                    moduleDataDetails.put("publishDate", ExcelUtil.convertValue(row.getCell(83), CellType.STRING));
                    moduleDataDetails.put("idTypeUse", extractIdValueFromExcel(typeUseTreeAndAnimal));
                    moduleDataDetails.put("idCountryProducer", extractIdValueFromExcel(countryProducerTreeAndAnimal));
                    break;
                case "OtherAssetModule":
                    String countryProducerOther = (String)ExcelUtil.convertValue(row.getCell(90), CellType.STRING);
//                    Optional<CountryProducer> countryProducerOtherOptional=countryProducerRepository.findCountryProducerByNameAndStatus(countryProducerOther,1);
                    String userNameOther= (String) ExcelUtil.convertValue(row.getCell(91), CellType.STRING);
                    Optional<CsvcUser> userOptionalOther = csvcUserService.findByUserName(userNameOther);
                    String typeUseOther = (String) ExcelUtil.convertValue(row.getCell(92), CellType.STRING);
//                    Optional<TypeUse> typeUseOptionalOther=typeUseRepository.findTypeUseByName(typeUseOther);

                    moduleDataDetails.put("label", ExcelUtil.convertValue(row.getCell(86), CellType.STRING));
                    moduleDataDetails.put("model", ExcelUtil.convertValue(row.getCell(87), CellType.STRING));
                    moduleDataDetails.put("serial", ExcelUtil.convertValue(row.getCell(88), CellType.STRING));
                    moduleDataDetails.put("publishDate", ExcelUtil.convertValue(row.getCell(89), CellType.STRING));
                    moduleDataDetails.put("idCountryProducer", extractIdValueFromExcel(countryProducerOther));
                    moduleDataDetails.put("codeUser",  userOptionalOther.isPresent() ? userOptionalOther.get().getCodeUser() : null);
                    moduleDataDetails.put("idTypeUse", extractIdValueFromExcel(typeUseOther));
                    break;
                case "MedicineModule":
                    String medicineType = (String)ExcelUtil.convertValue(row.getCell(93), CellType.STRING);
//                    Optional<MedicineType> medicineTypeOptional=medicineTypeRepository.findMedicineTypeByName(medicineType);
                    String medicineGroup = (String)ExcelUtil.convertValue(row.getCell(94), CellType.STRING);
//                    Optional<MedicineGroup> medicineGroupOptional=medicineGroupRepository.findMedicineGroupByName(medicineGroup);

                    moduleDataDetails.put("idMedicineType", extractIdValueFromExcel(medicineType));
                    moduleDataDetails.put("idMedicineGroup", extractIdValueFromExcel(medicineGroup));
                    moduleDataDetails.put("publishDate", ExcelUtil.convertValue(row.getCell(95), CellType.STRING));
                    moduleDataDetails.put("expiryDate", ExcelUtil.convertValue(row.getCell(96), CellType.STRING));
                    moduleDataDetails.put("circulationNumber", ExcelUtil.convertValue(row.getCell(97), CellType.STRING));
                    moduleDataDetails.put("numberBatchOfGoods", ExcelUtil.convertValue(row.getCell(98), CellType.STRING));
                    moduleDataDetails.put("ownNameCirculationNumber", ExcelUtil.convertValue(row.getCell(99), CellType.STRING));
                    moduleDataDetails.put("ownAddressCirculationNumber", ExcelUtil.convertValue(row.getCell(100), CellType.STRING));
//                    moduleDataDetails.put("sparePartsAttack", ExcelUtil.convertValue(row.getCell(31), CellType.STRING));
                    break;
            }
//            moduleAssetData.put("data_details",moduleDataDetails);
            modulesDataAsset.add(moduleDataDetails);
        }

        return modulesDataAsset;
    }
    private Integer getIdForCurrentUsage(String usageType) {
        Optional<CurrentUsage> currentUsage=currentUsageRepository.findCurrentUsageByName(usageType);
        return currentUsage.get().getIdCurrentUsage();
    }

    private Double convertStringToDouble(Object value) {
        if (value != null) {
            try {
                return Double.parseDouble(value.toString().trim());
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }
    private Map<String, Object> processDeclareData(XSSFRow row, Map<String, Object> commonData) {


//        Optional<AssetCategories> assetCategoriesInstanceOptional = assetCategoriesRepository.findAssetCategoryById((Integer) commonData.get("idInstance"));
        Optional<Declare> declareOptional= declareRepository.findDeclareByIdAssetCategoryAndVisible( (Integer) commonData.get("idInstance"),1);
        Optional<TypeDeclareAsset> typeDeclareAssetOptional = typeDeclareAssetRepository.findTypeDeclareAssetByIdAssetCategory((Integer) commonData.get("idInstance"));
        String typeDeclare=declareOptional.get().getHardCode();
        Map<String, Object> declareData = new HashMap<>();
        declareData.put("typeDeclare", typeDeclare);
        declareData.put("idTypeDeclareAsset",  typeDeclareAssetOptional.map(TypeDeclareAsset::getIdTypeDeclareAsset).orElse(null));

        switch (typeDeclare) {
            case "CommonDeclare":
                List<Map<String, Object>> currentUsageList = new ArrayList<>();

                Object value;

                value = (String)ExcelUtil.convertValue(row.getCell(102), CellType.STRING);
                if (value != null && value.equals("Có")) {
                    currentUsageList.add(Map.of("idCurrentUsage", getIdForCurrentUsage("Quản lý nhà nước")));
                }

                value = (String)ExcelUtil.convertValue(row.getCell(103), CellType.STRING);
                if (value != null && value.equals("Có")) {
                    currentUsageList.add(Map.of("idCurrentUsage", getIdForCurrentUsage("HĐSN - Không KD")));
                }

                value = (String)ExcelUtil.convertValue(row.getCell(104), CellType.STRING);
                if (value != null && value.equals("Có")) {
                    currentUsageList.add(Map.of("idCurrentUsage", getIdForCurrentUsage("HĐSN - KD")));
                }

                value = (String)ExcelUtil.convertValue(row.getCell(105), CellType.STRING);
                if (value != null && value.equals("Có")) {
                    currentUsageList.add(Map.of("idCurrentUsage", getIdForCurrentUsage("HĐSN - LDLK")));
                }

                value = (String)ExcelUtil.convertValue(row.getCell(106), CellType.STRING);
                if (value != null && value.equals("Có")) {
                    currentUsageList.add(Map.of("idCurrentUsage", getIdForCurrentUsage("HĐSN - Cho thuê")));
                }

                value = (String)ExcelUtil.convertValue(row.getCell(107), CellType.STRING);
                if (value != null && value.equals("Có")) {
                    currentUsageList.add(Map.of("idCurrentUsage", getIdForCurrentUsage("Sử dụng khác")));
                }

                declareData.put("specification", ExcelUtil.convertValue(row.getCell(101), CellType.STRING));
                declareData.put("currentUsage",currentUsageList);

                break;

            case "HouseDeclare":

                Double workPlaceHouse=convertStringToDouble( ExcelUtil.convertValue(row.getCell(108), CellType.STRING));
                Double hdsnNoBussinessHouse=convertStringToDouble(ExcelUtil.convertValue(row.getCell(109), CellType.STRING));
                Double hdsnBussinessHouse=convertStringToDouble(ExcelUtil.convertValue(row.getCell(110), CellType.STRING));
                Double hdsnRentHouse=convertStringToDouble( ExcelUtil.convertValue(row.getCell(111), CellType.STRING));
                Double hdsnBondsHouse=convertStringToDouble(ExcelUtil.convertValue(row.getCell(112), CellType.STRING));
                Double syntheticUseHouse=convertStringToDouble( ExcelUtil.convertValue(row.getCell(113), CellType.STRING));
                Double blankPlaceHouse=convertStringToDouble( ExcelUtil.convertValue(row.getCell(114), CellType.STRING));
                Double livePlaceHouse=convertStringToDouble(ExcelUtil.convertValue(row.getCell(115), CellType.STRING));
                Double encroachedPlaceHouse=convertStringToDouble(ExcelUtil.convertValue(row.getCell(116), CellType.STRING));
                Double otherUseHouse=convertStringToDouble(ExcelUtil.convertValue(row.getCell(117), CellType.STRING));

                declareData.put("workplace", workPlaceHouse );
                declareData.put("hdsnNoBussiness",hdsnNoBussinessHouse);
                declareData.put("hdsnBussiness",hdsnBussinessHouse );
                declareData.put("hdsnRent", hdsnRentHouse);
                declareData.put("hdsnBonds", hdsnBondsHouse);
                declareData.put("syntheticUse", syntheticUseHouse);
                declareData.put("blankPlace", blankPlaceHouse);
                declareData.put("livePlace", livePlaceHouse);
                declareData.put("encroachedPlace", encroachedPlaceHouse);
                declareData.put("otherUse", otherUseHouse);
                double acreage = Stream.of(workPlaceHouse, hdsnNoBussinessHouse, hdsnBussinessHouse, hdsnRentHouse,hdsnBondsHouse,
                                syntheticUseHouse, blankPlaceHouse, livePlaceHouse, encroachedPlaceHouse, otherUseHouse)
                        .filter(Objects::nonNull)
                        .mapToDouble(Double::doubleValue)
                        .sum();
                declareData.put("acreage",acreage);
//                declareData.put("idTypeDeclareAsset", ExcelUtil.convertValue(row.getCell(200), CellType.STRING));

                break;
            case "GroundDeclare":

                Double workPlaceGround=convertStringToDouble(ExcelUtil.convertValue(row.getCell(108), CellType.STRING));
                Double hdsnNoBussinessGround=convertStringToDouble(ExcelUtil.convertValue(row.getCell(109), CellType.STRING));
                Double hdsnBussinessGround=convertStringToDouble( ExcelUtil.convertValue(row.getCell(110), CellType.STRING));
                Double hdsnRentGround=convertStringToDouble( ExcelUtil.convertValue(row.getCell(111), CellType.STRING));
                Double hdsnBondsGround=convertStringToDouble(ExcelUtil.convertValue(row.getCell(112), CellType.STRING));
                Double syntheticUseGround=convertStringToDouble( ExcelUtil.convertValue(row.getCell(113), CellType.STRING));
                Double blankPlaceGround=convertStringToDouble(ExcelUtil.convertValue(row.getCell(114), CellType.STRING));
                Double livePlaceGround=convertStringToDouble(ExcelUtil.convertValue(row.getCell(115), CellType.STRING));
                Double encroachedPlaceGround=convertStringToDouble( ExcelUtil.convertValue(row.getCell(116), CellType.STRING));
                Double otherUseGround=convertStringToDouble(ExcelUtil.convertValue(row.getCell(117), CellType.STRING));

                declareData.put("workplace", workPlaceGround );
                declareData.put("hdsnNoBussiness",hdsnNoBussinessGround);
                declareData.put("hdsnBussiness", hdsnBussinessGround);
                declareData.put("hdsnRent", hdsnRentGround);
                declareData.put("hdsnBonds", hdsnBondsGround);
                declareData.put("syntheticUse", syntheticUseGround);
                declareData.put("blankPlace", blankPlaceGround);
                declareData.put("livePlace", livePlaceGround);
                declareData.put("encroachedPlace", encroachedPlaceGround);
                declareData.put("otherUse", otherUseGround);
                double acreageGround = Stream.of(workPlaceGround, hdsnNoBussinessGround, hdsnBussinessGround, hdsnRentGround,
                                hdsnBondsGround, syntheticUseGround, blankPlaceGround, livePlaceGround, encroachedPlaceGround,otherUseGround)
                        .filter(Objects::nonNull)
                        .mapToDouble(Double::doubleValue)
                        .sum();
                declareData.put("acreage",acreageGround);
//                declareData.put("acreage", ExcelUtil.convertValue(row.getCell(20), CellType.STRING));
                String goalsUseGround=(String) ExcelUtil.convertValue(row.getCell(118), CellType.STRING);
                declareData.put("idGoalsUseGround", extractIdValueFromExcel(goalsUseGround));
                declareData.put("licenseCertificateUseGround", ExcelUtil.convertValue(row.getCell(119), CellType.STRING));
                declareData.put("dateLicenseCertificateUseGround", ExcelUtil.convertValue(row.getCell(120), CellType.STRING));
                declareData.put("numberDecisionDeliverGround", ExcelUtil.convertValue(row.getCell(121), CellType.STRING));
                declareData.put("dateNumberDecisionDeliverGround", ExcelUtil.convertValue(row.getCell(122), CellType.STRING));
                declareData.put("contractNumberTransferGround", ExcelUtil.convertValue(row.getCell(123), CellType.STRING));
                declareData.put("dateContractNumberTransferGround", ExcelUtil.convertValue(row.getCell(124), CellType.STRING));
                declareData.put("contractNumberRentGround", ExcelUtil.convertValue(row.getCell(125), CellType.STRING));
                declareData.put("dateContractNumberRentGround", ExcelUtil.convertValue(row.getCell(126), CellType.STRING));
                declareData.put("numberDecisionRentGround", ExcelUtil.convertValue(row.getCell(127), CellType.STRING));
                declareData.put("dateNumberDecisionRentGround", ExcelUtil.convertValue(row.getCell(128), CellType.STRING));
                declareData.put("anotherContract", ExcelUtil.convertValue(row.getCell(129), CellType.STRING));
//                declareData.put("idTypeDeclareAsset", ExcelUtil.convertValue(row.getCell(200), CellType.STRING));
                break;


        }

        return declareData;
    }
    public String extractNameValueFromExcel(String input) {
        if (input != null && input.contains(".")) {
            String[] parts = input.split("\\."); // Escape dấu chấm bằng "\\."

            // Kiểm tra xem có đủ phần tử sau khi tách không
            if (parts.length > 1) {
                return parts[1]; // Trả về phần tử thứ hai nếu tồn tại
            }
        }
        return input; // Trả về chuỗi gốc nếu không hợp lệ hoặc không có dấu chấm
    }
    private Map<String, Object> processOriginalData(XSSFRow row) {
        String original = (String) ExcelUtil.convertValue(row.getCell(13), CellType.STRING);
        Optional<Original> originalOptional = originalRepository.findOriginalByName(extractNameValueFromExcel(original));
        Map<String, Object> departmentInFor = new HashMap<>();
        departmentInFor.put("idOriginal", extractIdValueFromExcel(original));
        departmentInFor.put("typeOriginal", originalOptional.map(Original::getHardCodeDev).orElse(null));
        return departmentInFor;
    }
    public int calculateRemainingMonths(String dateFromExcel) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Ví dụ: 01/01/2023
        LocalDate dateFromExcelParsed = LocalDate.parse(dateFromExcel, formatter);
        LocalDate dateAfter15Months = dateFromExcelParsed.plusMonths(15);
        LocalDate currentDate = LocalDate.now();
        long monthsRemaining = ChronoUnit.MONTHS.between(currentDate, dateAfter15Months);

        return (int) monthsRemaining; // Chuyển đổi sang kiểu int
    }
    private Map<String, Object> processDepreciationData(XSSFRow row) {
        Map<String, Object> depreciationInFor = new HashMap<>();

        String timeBuy = (String) ExcelUtil.convertValue(row.getCell(131), CellType.STRING);
        String timeStartedUsed = (String) ExcelUtil.convertValue(row.getCell(132), CellType.STRING);
        String timeStartedIncrease = (String) ExcelUtil.convertValue(row.getCell(133), CellType.STRING);
        String timeStartedWearTear = (String) ExcelUtil.convertValue(row.getCell(134), CellType.STRING);
        String timeEndWearTear = (String) ExcelUtil.convertValue(row.getCell(137), CellType.STRING);
        String timeStartedDepreciation = (String) ExcelUtil.convertValue(row.getCell(138), CellType.STRING);
        String amountMonthsDepreciation = (String) ExcelUtil.convertValue(row.getCell(140), CellType.STRING);

        Integer amountMonthsDepreciationNumber = null;
        if (amountMonthsDepreciation != null && !amountMonthsDepreciation.isEmpty()) {
            amountMonthsDepreciationNumber = Integer.parseInt(amountMonthsDepreciation);
        }

        Double cumulative = convertStringToDouble(ExcelUtil.convertValue(row.getCell(141), CellType.STRING));
        if (cumulative == null) {
            cumulative = 0.0;
        }
        Object valueTypeCalculate = (String) ExcelUtil.convertValue(row.getCell(130), CellType.STRING);
        if (valueTypeCalculate != null) {
            switch (valueTypeCalculate.toString()) {
                case "Tính hao mòn":
                    depreciationInFor.put("typeCalculate", 1);
                    break;
                case "Tính khấu hao":
                    depreciationInFor.put("typeCalculate", 2);
                    break;
                case "Tính cả hai":
                    depreciationInFor.put("typeCalculate", 3);
                    break;
            }
        }

        depreciationInFor.put("timeBuy", timeBuy);      // thời gian mua
        depreciationInFor.put("timeStartedUsed", timeStartedUsed); // bắt đầu sử dụng
        depreciationInFor.put("timeStartedIncrease", timeStartedIncrease); //bắt đầu ghi tăng
        depreciationInFor.put("timeYearTracking", String.valueOf(Year.now())); //năm theo dõi
        depreciationInFor.put("timeStartedWearTear", timeStartedWearTear); //bắt đầu tính hao mòn
        depreciationInFor.put("timeEndWearTear", timeEndWearTear); //kết thúc tính hao mòn
        depreciationInFor.put("timeStartedDepreciation", timeStartedDepreciation); //bắt đầu tính khấu hao
        depreciationInFor.put("amountMonthsDepreciation", amountMonthsDepreciationNumber); //số tháng khấu hao

        String valueDepreciation = (String) ExcelUtil.convertValue(row.getCell(15), CellType.STRING);
        double totalDepreciation = 0;
        if (valueDepreciation != null && !valueDepreciation.isEmpty()) {
            String[] valueDepreciationArray = valueDepreciation.split(";");
            for (String value : valueDepreciationArray) {
                totalDepreciation += Double.parseDouble(value.trim());
            }
        }
        depreciationInFor.put("valueDepreciation", String.valueOf(totalDepreciation)); //giá trị tổng original

        Object valueTypeDepreciation = (String) ExcelUtil.convertValue(row.getCell(139), CellType.STRING); // loại kỳ tích khấu hao
        if (valueTypeDepreciation != null) {
            if (valueTypeDepreciation.equals("Năm")) {
                depreciationInFor.put("typeDepreciation", 1);
                depreciationInFor.put("valueTypeDepreciation", String.valueOf(totalDepreciation * 12 / amountMonthsDepreciationNumber)); //giá trị tổng original theo năm
            } else if (valueTypeDepreciation.equals("Tháng")) {
                depreciationInFor.put("typeDepreciation", 2);
                depreciationInFor.put("valueTypeDepreciation", String.valueOf(totalDepreciation / amountMonthsDepreciationNumber)); //giá trị tổng original theo tháng
            }
        }

        if (timeStartedDepreciation != null && !timeStartedDepreciation.isEmpty()) {
            int remainingMonths = calculateRemainingMonths(timeStartedDepreciation);
            depreciationInFor.put("amountRestMonthsDepreciation", remainingMonths); // số tháng khấu hao còn lại
        }

        depreciationInFor.put("cumulative", String.valueOf(cumulative));
        depreciationInFor.put("restValue", String.valueOf(totalDepreciation - cumulative)); // giá trị còn lại
        return depreciationInFor;
    }

}
