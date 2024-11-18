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
import com.example.csvccdshustbe.repository.assetInstance.AssetInstanceRepository;
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
import com.example.csvccdshustbe.repository.positionName.PositionNameRepository;
import com.example.csvccdshustbe.repository.projects.ProjectsRepository;
import com.example.csvccdshustbe.repository.province.ProvinceRepository;
import com.example.csvccdshustbe.repository.typeDeclareAsset.TypeDeclareAssetRepository;
import com.example.csvccdshustbe.repository.typeUse.TypeUseRepository;
import com.example.csvccdshustbe.repository.units.UnitsRepository;
import com.example.csvccdshustbe.repository.user.CsvcUserRepository;
import com.example.csvccdshustbe.repository.wards.WardsRepository;
import com.example.csvccdshustbe.request.asset.*;
import com.example.csvccdshustbe.response.asset.*;
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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
    @Autowired
    PositionNameRepository positionNameRepository;
    @Autowired
    CsvcUserRepository csvcUserRepository;
    @Autowired
    AssetInstanceRepository assetInstanceRepository;
    @Transactional
    @Override
    public void createAsset(Map<String, Object> createAssetRequest) throws JsonProcessingException, ValidateFiledException {
        Map<String, Object> dataCreateAssetRequest =
                objectMapper.readValue(JSONObjectUtils.toJSONString(createAssetRequest), Map.class);
        validateDataCreateAsset(dataCreateAssetRequest);
        storeNewAsset(dataCreateAssetRequest);
    }

    @Transactional
    @Override
    public void createAssetLot(Map<String, Object> createAssetLotRequest) throws JsonProcessingException, ValidateFiledException {
        Map<String, Object> dataCreateAssetLotRequest =
                objectMapper.readValue(JSONObjectUtils.toJSONString(createAssetLotRequest), Map.class);
        validateDataCreateAssetLot(dataCreateAssetLotRequest);
        storeNewAssetLot(dataCreateAssetLotRequest);
    }

    @Override
    public Page<FindAllAssetResponse> findAllAsset(FindAllAssetRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        setIdsDepartmentOriginal(request);
        Page<FindAllAssetDto> findAllAssetDtos = assetRepository.findAllAssetDtoByIdsDepartment(request, pageable);
        return new PageImpl<>(convertToFindAllAssetResponse(findAllAssetDtos.get().collect(Collectors.toList())),
                    pageable, findAllAssetDtos.getTotalElements());
    }

    @Override
    public Page<FindAllAssetLotChildrenResponse> findAllAssetLotChildren(FindAllAssetLotChildrenRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        List<Integer> idsDepartment = ((CsvcUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getIdsDepartmentCurrent();
        request.setIdsDepartmentOriginal(idsDepartment);
        Page<FindAllAssetDto> findAllAssetDtos = assetRepository.findAllAssetLotChildrenDtoByIdsDepartment(request, pageable);
        return new PageImpl<>(convertToFindAllAssetLotChildren(findAllAssetDtos.get().collect(Collectors.toList())),
                pageable, findAllAssetDtos.getTotalElements());
    }

    private List<FindAllAssetLotChildrenResponse> convertToFindAllAssetLotChildren(List<FindAllAssetDto> collect) {
        List<FindAllAssetLotChildrenResponse> responses = new ArrayList<>();
        for (FindAllAssetDto dto : collect) {
            FindAllAssetLotChildrenResponse response = new FindAllAssetLotChildrenResponse();
            response.setCodeAsset(dto.getCodeAsset());
            response.setNameAsset(dto.getNameAsset());
            response.setNameAssetCategory(dto.getNameAssetCategory());
            response.setCodeAssetCategory(dto.getCodeAssetCategory());
            response.setIdDepartment(dto.getIdDepartment());
            response.setCodeDepartment(dto.getCodeDepartment());
            response.setNameDepartment(dto.getNameDepartment());
            response.setIdLocation(dto.getIdLocation());
            response.setNameLocation(dto.getNameLocation());
            response.setSalt(dto.getSalt());
            response.setTimeCreated(DateUtil.formatToPattern(new Date(dto.getTimeCreated()),DateUtil.DATE_FORMAT));
            response.setTimeModified(DateUtil.formatToPattern(new Date(dto.getTimeModified()), DateUtil.DATE_FORMAT));
            responses.add(response);
        }
        return responses;
    }

    private void setIdsDepartmentOriginal(FindAllAssetRequest request) {
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        request.setIdsDepartmentOriginal(csvcUser.getIdsDepartmentCurrent());
    }

    @Override
    public FindDetailsAssetResponse findDetailsAssetBySaltAsset(String saltAsset) throws ValidateFiledException, IllegalAccessException {
        Optional<AssetBluePrintDto> assetBluePrintDto = assetRepository.findDetailAssetBySaltAsset(saltAsset);
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

    @Transactional
    @Override
    public void updateAssetLot(HashMap<String, Object> updateAssetRequest) throws JsonProcessingException, ValidateFiledException,
            IllegalAccessException {
        Map<String, Object> dataCreateAssetRequest =
                objectMapper.readValue(JSONObjectUtils.toJSONString(updateAssetRequest), Map.class);
        validateDataUpdateAssetLot(dataCreateAssetRequest);
        updateDataAssetLot(dataCreateAssetRequest);
    }

    @Override
    public void deleteAssetBySaltAsset(String saltAsset) throws ValidateFiledException {
        Optional<AssetBluePrintDto> assetBluePrintDto = assetRepository.findDetailAssetBySaltAsset(saltAsset);
        if (assetBluePrintDto.isEmpty()) {
            throw new NotFoundException("Don't exits asset by code!");
        }
        deleteAsseDepreciation(assetBluePrintDto.get());
        deleteCommonAsset(assetBluePrintDto.get());
        deleteModuleAsset(assetBluePrintDto.get());
        deleteOriginalAsset(assetBluePrintDto.get());
        deleteDeclareAsset(assetBluePrintDto.get());
    }

    @Override
    public void deleteAssetBySaltAssetLot(String saltAsset) throws ValidateFiledException {
        Optional<Asset> assetParent = assetRepository.findAssetBySalt(saltAsset);
        if (assetParent.isEmpty()) {
            throw new NotFoundException("Don't exits asset by salt!");
        }
        List<Asset> assetChildren = assetRepository.findAllAssetChildrenByParentId(assetParent.get().getIdAsset());
        for (Asset asset: assetChildren){
            deleteAssetBySaltAsset(asset.getSalt());
        }
        deleteAssetBySaltAsset(assetParent.get().getSalt());
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

    private void updateDataAssetLot(Map<String, Object> dataUpdateAssetRequest) throws ValidateFiledException, IllegalAccessException {
        Asset assetParent = updateAttributeAssetLotParent(dataUpdateAssetRequest);
        //original of formation for assetParent
        Map<String,Object> commonDataAsset = (Map<String, Object>) dataUpdateAssetRequest.get(Constants.KEY_COMMON);
        updateOriginalOfFormation(assetParent, commonDataAsset);
        updateAssetDepreciation(dataUpdateAssetRequest, assetParent);
        updateModulesDataAsset(dataUpdateAssetRequest, assetParent);
        updateOriginalDataAsset(dataUpdateAssetRequest, assetParent);
        updateDeclareDataAsset(dataUpdateAssetRequest, assetParent);

        List<Asset> assetChildren = updateCommonDataAssetLot(dataUpdateAssetRequest, assetParent);
        updateAssetDepreciationLot(dataUpdateAssetRequest, assetChildren);
        updateModulesDataAssetLot(dataUpdateAssetRequest, assetChildren);
        updateOriginalDataAssetLot(dataUpdateAssetRequest, assetChildren);
        updateDeclareDataAssetLot(dataUpdateAssetRequest, assetChildren);
    }

    private void updateAssetDepreciation(Map<String, Object> dataUpdateAssetRequest, Asset asset) {
        log.info("Storing depreciation data asset");
        Map<String, Object> depreciationAsset = (Map<String, Object>) dataUpdateAssetRequest.get(Constants.KEY_DEPRECIATION);
        AssetDepreciation assetDepreciation = assetDepreciationService.findAssetDepreciationByIdAsset(asset.getIdAsset());
        updateDataAssetDepreciation(assetDepreciation, depreciationAsset);
            assetDepreciationService.save(assetDepreciation);
    }

    private void updateAssetDepreciationLot(Map<String, Object> dataUpdateAssetRequest, List<Asset> assetChildren) {
        log.info("Storing depreciation data asset");
        for (Asset assetChild: assetChildren) {
            Map<String, Object> depreciationAsset = (Map<String, Object>) dataUpdateAssetRequest.get(Constants.KEY_DEPRECIATION);
            AssetDepreciation assetDepreciation = assetDepreciationService.findAssetDepreciationByIdAsset(assetChild.getIdAsset());
            updateDataAssetDepreciation(assetDepreciation, depreciationAsset);
            assetDepreciationService.save(assetDepreciation);
        }
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

    private void updateDeclareDataAssetLot(Map<String, Object> dataUpdateAssetRequest, List<Asset> assetChildren) throws ValidateFiledException {
        for (Asset asset: assetChildren) {
            log.info("Start update declare data asset by code asset " + asset.getCodeAsset());
            Map<String, Object> declareDataAsset = (Map<String, Object>) dataUpdateAssetRequest.get(Constants.KEY_DECLARE_ASSET);
            BluePrintDeclareDto bluePrintDeclareDto = declareServiceFactory.findBluePrintAssetDeclareByIdAsset(asset.getIdAsset());
            deleteAssetDeclare(bluePrintDeclareDto, declareDataAsset, asset);
            createNewAssetDeclare(bluePrintDeclareDto, declareDataAsset, asset);
            updateAssetDeclare(bluePrintDeclareDto, declareDataAsset, asset);
        }
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

    private void updateOriginalDataAssetLot(Map<String, Object> dataUpdateAssetRequest, List<Asset> assetChildren) throws ValidateFiledException {
        for (Asset asset: assetChildren) {
            log.info("Start update original data asset by code asset " + asset.getCodeAsset());
            Map<String, Object> originalDataAsset = (Map<String, Object>) dataUpdateAssetRequest.get(Constants.KEY_ORIGINAL_ASSET);
            BluePrintOriginalDto bluePrintOriginalDto = originalServiceFactory.findBluePrintAssetOriginalByIdAsset(asset.getIdAsset());
            deleteAssetOriginal(bluePrintOriginalDto, originalDataAsset);
            createNewAssetOriginal(bluePrintOriginalDto, originalDataAsset, asset);
            updateAssetOriginal(bluePrintOriginalDto, originalDataAsset);
        }
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

    private void updateModulesDataAssetLot(Map<String, Object> dataUpdateAssetRequest, List<Asset> assetChildren) throws ValidateFiledException, IllegalAccessException {
        for (Asset asset: assetChildren) {
            log.info("Start update modules data asset by code asset " + asset.getCodeAsset());
            List<BluePrintAssetModulesDto> bluePrintAssetModulesDtos = modulesServiceFactory.findBluePrintAssetModulesByIdAsset(asset.getIdAsset());
            List<HashMap<String, Object>> modulesDataAsset = (List<HashMap<String, Object>>) dataUpdateAssetRequest.get(Constants.KEY_MODULE);

            deleteAssetModule(bluePrintAssetModulesDtos, modulesDataAsset);
            createNewAssetModule(bluePrintAssetModulesDtos, modulesDataAsset, asset);
            updateAssetModule(bluePrintAssetModulesDtos, modulesDataAsset);
        }
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
        log.info("Start update common data asset by salt asset = " + ValueUtil.getStringByObject(commonDataAsset.get("salt")));
        Optional<Asset> asset = assetRepository.findAssetBySalt(ValueUtil.getStringByObject(commonDataAsset.get("salt")));
        if (asset.isEmpty()) {
            throw new NotFoundException("Don't exits asset by salt!");
        }
        updateAttributeAsset(commonDataAsset, asset.get());
        updateOriginalOfFormation(asset.get(), commonDataAsset);
        log.info("Update finished common data asset by salt asset = " + asset.get().getCodeAsset());
        return asset.get();
    }

    private List<Asset> updateCommonDataAssetLot(Map<String, Object> dataUpdateAssetRequest, Asset assetParent) throws ValidateFiledException {
        Map<String,Object> commonDataAsset = (Map<String, Object>) dataUpdateAssetRequest.get(Constants.KEY_COMMON);
        List<Asset> assetChildren = updateAttributeAssetChildrenLot(dataUpdateAssetRequest,assetParent);
        updateOriginalOfFormationLot(assetChildren, commonDataAsset);
        return assetChildren;
    }

    private List<Asset> updateAttributeAssetChildrenLot(Map<String, Object> dataUpdateAssetRequest, Asset assetParent) throws ValidateFiledException {
        Map<String,Object> commonDataAsset = (Map<String, Object>) dataUpdateAssetRequest.get(Constants.KEY_COMMON);
        List<Map<String, Object>> dataAssetChildren = (List<Map<String, Object>>) commonDataAsset.get(Constants.KEY_CHILDREN_DISTRIBUTION);
        List<Asset> assetChildren = assetRepository.findAllAssetChildrenByParentId(assetParent.getIdAsset());
        if (CollectionUtils.isEmpty(assetChildren)){
            throw new NotFoundException("Don't exist asset children by parent id!");
        }
        deleteAssetChildrenLot(dataAssetChildren, assetChildren);
        createNewAssetChildrenLot(dataUpdateAssetRequest,dataAssetChildren, assetChildren, assetParent);
        updateAssetChildrenLot(dataAssetChildren, assetChildren,assetParent);
        return assetChildren;
    }

    private void createNewAssetChildrenLot(Map<String, Object> dataUpdateAssetRequest,
                                           List<Map<String, Object>> dataAssetChildren,
                                           List<Asset> assetChildren, Asset assetParent) throws ValidateFiledException {
        for (Map<String, Object> dataDistributionAsset : dataAssetChildren) {
            boolean isCheckExits = false;
            for (Asset asset : assetChildren) {
                if (asset.getSalt().equals(ValueUtil.getStringByObject(dataDistributionAsset.get("salt")))) {
                    isCheckExits = true;
                    break;
                }
            }
            if (!isCheckExits){
                Asset asset = createSingleAssetChildren(assetParent,dataDistributionAsset);
                storeDepreciationLot(dataUpdateAssetRequest, List.of(asset));
                storeModulesDataAssetLot(dataUpdateAssetRequest, List.of(asset));
                storeOriginalDataAssetLot(dataUpdateAssetRequest, List.of(asset));
                storeDeclareDataAssetLot(dataUpdateAssetRequest, List.of(asset));
                assetChildren.add(asset);
            }
        }
    }

    private void updateAssetChildrenLot(List<Map<String, Object>> dataAssetChildren, List<Asset> assetChildren, Asset assetParent) {
        for (Asset asset : assetChildren){
            for (Map<String, Object> dataDistributionAsset: dataAssetChildren){
                if (asset.getSalt().equals(ValueUtil.getStringByObject(dataDistributionAsset.get("salt")))) {
                    updateConstructionAssetChild(dataDistributionAsset, asset, assetParent);
                }
            }
        }
    }

    private void updateConstructionAssetChild(Map<String, Object> obj, Asset childAsset, Asset assetParent) {
        childAsset.setName(assetParent.getName());
        childAsset.setIdAssetCategory(assetParent.getIdAssetCategory());
        childAsset.setIdDocumentAttack(assetParent.getIdDocumentAttack());
        childAsset.setIdUnit(assetParent.getIdUnit());
        childAsset.setIdProjects(assetParent.getIdProjects());
        childAsset.setPurpose(assetParent.getPurpose());
        childAsset.setNotes(assetParent.getNotes());
        childAsset.setDescription(assetParent.getDescription());
        childAsset.setQuantity(Constants.QUANTITY_DEFAULT);
        childAsset.setFileAttack(assetParent.getFileAttack());
        String timeCurrent = String.valueOf(new Date().getTime());
        childAsset.setTimeModified(timeCurrent);
        childAsset.setIdDepartmentDefault(assetParent.getIdDepartmentDefault());
        childAsset.setIdDepartmentOrigin(assetParent.getIdDepartmentOrigin());
        childAsset.setIdLevelTypeAsset(assetParent.getIdLevelTypeAsset());
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        childAsset.setIdUserModified(csvcUser.getIdUser());
        childAsset.setCodeAsset(ValueUtil.getStringByObject(obj.get("codeAsset")));
        childAsset.setIdDepartment(ValueUtil.getIntegerByObject(obj.get("idDepartment")));
        childAsset.setIdLocation(ValueUtil.getIntegerByObject(obj.get("idLocation")));
        assetRepository.save(childAsset);
    }

    private void deleteAssetChildrenLot(List<Map<String, Object>> dataAssetChildren, List<Asset> assetChildren) throws ValidateFiledException {
        for (Asset asset : assetChildren){
            boolean isCheckExits = false;
            for (Map<String, Object> dataDistributionAsset: dataAssetChildren){
                if (asset.getSalt().equals(ValueUtil.getStringByObject(dataDistributionAsset.get("salt")))) {
                    isCheckExits = true;
                    break;
                }
            }
            if (!isCheckExits){
                deleteAssetBySaltAsset(asset.getSalt());
                assetChildren.remove(asset);
            }
        }
    }


    private void updateOriginalOfFormation(Asset asset, Map<String, Object> commonDataAsset) {
        List<AssetOriginalOfFormation> originalOfFormations =
                assetOriginalOfFormationService.findOriginalOfFormationByIdAsset(asset.getIdAsset());
        List<Map<String,Object>> assetOriginalOfFormationData =
                (List<Map<String, Object>>) commonDataAsset.get(Constants.KEY_ASSET_ORIGINAL_OF_FORMATION);
        deleteAssetOriginalOfFormation(originalOfFormations, assetOriginalOfFormationData, asset);
        createAssetOriginalOfFormation(originalOfFormations, assetOriginalOfFormationData, asset);
        updateAssetOriginalOfFormation(originalOfFormations, assetOriginalOfFormationData);
    }

    private void updateOriginalOfFormationLot(List<Asset> assetChildren, Map<String, Object> commonDataAsset) {
        for (Asset asset: assetChildren) {
            List<AssetOriginalOfFormation> originalOfFormations =
                    assetOriginalOfFormationService.findOriginalOfFormationByIdAsset(asset.getIdAsset());
            List<Map<String, Object>> assetOriginalOfFormationData =
                    (List<Map<String, Object>>) commonDataAsset.get(Constants.KEY_ASSET_ORIGINAL_OF_FORMATION);
            deleteAssetOriginalOfFormation(originalOfFormations, assetOriginalOfFormationData, asset);
            createAssetOriginalOfFormation(originalOfFormations, assetOriginalOfFormationData, asset);
            updateAssetOriginalOfFormation(originalOfFormations, assetOriginalOfFormationData);
        }
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
        asset.setCodeAsset(ValueUtil.getStringByObject(commonDataAsset.get("codeAsset")));
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

    private Asset updateAttributeAssetLotParent(Map<String, Object> dataUpdateAssetRequest) {
        Map<String,Object> commonDataAsset = (Map<String, Object>) dataUpdateAssetRequest.get(Constants.KEY_COMMON);
        log.info("Start update common data asset by salt asset = " + ValueUtil.getStringByObject(commonDataAsset.get("salt")));
        Optional<Asset> assetParent = assetRepository.findAssetBySalt(ValueUtil.getStringByObject(commonDataAsset.get("salt")));
        if (assetParent.isEmpty()) {
            throw new NotFoundException("Don't exits asset by salt!");
        }
        assetParent.get().setName(ValueUtil.getStringByObject(commonDataAsset.get("name")));
        assetParent.get().setIdAssetCategory(ValueUtil.getIntegerByObject(commonDataAsset.get("idAssetCategory")));
        assetParent.get().setIdDocumentAttack(ValueUtil.getIntegerByObject(commonDataAsset.get("idDocumentAttack")));
        assetParent.get().setCodeAsset(ValueUtil.getStringByObject(commonDataAsset.get("codeAsset")));
        assetParent.get().setIdDepartment(ValueUtil.getIntegerByObject(commonDataAsset.get("idDepartment")));
        assetParent.get().setIdLocation(ValueUtil.getIntegerByObject(commonDataAsset.get("idLocation")));
        assetParent.get().setIdUnit(ValueUtil.getIntegerByObject(commonDataAsset.get("idUnit")));
        assetParent.get().setIdProjects(ValueUtil.getIntegerByObject(commonDataAsset.get("idProjects")));
        assetParent.get().setPurpose(ValueUtil.getStringByObject(commonDataAsset.get("purpose")));
        assetParent.get().setNotes(ValueUtil.getStringByObject(commonDataAsset.get("notes")));
        assetParent.get().setDescription(ValueUtil.getStringByObject(commonDataAsset.get("description")));
        assetParent.get().setDescription(ValueUtil.getStringByObject(commonDataAsset.get("description")));
        assetParent.get().setQuantity(ValueUtil.getIntegerByObject(commonDataAsset.get("quantity")));
        assetParent.get().setFileAttack(ValueUtil.getStringByObject(commonDataAsset.get("fileAttack")));
        String timeCurrent = String.valueOf(new Date().getTime());
        assetParent.get().setTimeModified(timeCurrent);
        assetParent.get().setIdDepartmentDefault(ValueUtil.getIntegerByObject(commonDataAsset.get("idDepartmentDefault")));
        assetParent.get().setIdLevelTypeAsset(ValueUtil.getIntegerByObject(commonDataAsset.get("idLevelTypeAsset")));
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        assetParent.get().setIdUserModified(csvcUser.getIdUser());
        return assetRepository.save(assetParent.get());
    }


    private void validateDataUpdateAsset(Map<String, Object> dataCreateAssetRequest) throws ValidateFiledException {
        validateDataCommonUpdateAsset(dataCreateAssetRequest);
        validateDataModuleUpdateAsset(dataCreateAssetRequest);
        validateDataOriginalUpdateAsset(dataCreateAssetRequest);
        validateDataDeclareUpdateAsset(dataCreateAssetRequest);
    }

    private void validateDataUpdateAssetLot(Map<String, Object> dataCreateAssetRequest) throws ValidateFiledException {
        validateDataCommonUpdateAssetLot(dataCreateAssetRequest);
        validateDataModuleUpdateAsset(dataCreateAssetRequest);
        validateDataOriginalUpdateAsset(dataCreateAssetRequest);
        validateDataDeclareUpdateAsset(dataCreateAssetRequest);
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
        commonAssetDto.setParent(assetBluePrintDto.getParent());
        commonAssetDto.setSalt(assetBluePrintDto.getSalt());
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
            response.setTimeCreated(DateUtil.formatToPattern(new Date(dto.getTimeCreated()),DateUtil.DATE_FORMAT));
            response.setTimeModified(DateUtil.formatToPattern(new Date(dto.getTimeModified()), DateUtil.DATE_FORMAT));
            response.setSalt(dto.getSalt());
            response.setQuantity(dto.getQuantity());
            responses.add(response);
        }
        return responses;
    }

    private List<FindAllAssetResponseToIncrease> convertToFindAllAssetToIncreaseResponse(List<FindAllAssetDto> collect) {
        List<FindAllAssetResponseToIncrease> responses = new ArrayList<>();
        for (FindAllAssetDto dto : collect) {
            FindAllAssetResponseToIncrease response = new FindAllAssetResponseToIncrease();
            response.setCodeAsset(dto.getCodeAsset());
            response.setIdAsset(dto.getIdAsset());
            response.setNameAsset(dto.getNameAsset());
            response.setNameAssetCategory(dto.getNameAssetCategory());
            response.setCodeAssetCategory(dto.getCodeAssetCategory());
            response.setCodeDepartment(dto.getCodeDepartment());
            response.setNameDepartment(dto.getNameDepartment());
            response.setTimeCreated(DateUtil.formatToPattern(new Date(dto.getTimeCreated()), DateUtil.DATE_FORMAT));
            response.setTimeModified(DateUtil.formatToPattern(new Date(dto.getTimeModified()), DateUtil.DATE_FORMAT));
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

    private void validateDataCreateAssetLot(Map<String, Object> createAssetRequest) throws ValidateFiledException {
        validateDataCommonCreateAssetLot(createAssetRequest);
        validateDataModuleCreateAssetLot(createAssetRequest);
        validateDataOriginalCreateAssetLot(createAssetRequest);
        validateDataDeclareCreateAssetLot(createAssetRequest);
    }


    private void validateDataDeclareCreateAsset(Map<String, Object> createAssetRequest) {
        declareServiceFactory.validateDataDeclare((Map<String, Object>) createAssetRequest.get(Constants.KEY_DECLARE_ASSET));
    }

    private void validateDataDeclareCreateAssetLot(Map<String, Object> createAssetRequest) {
        declareServiceFactory.validateDataDeclare((Map<String, Object>) createAssetRequest.get(Constants.KEY_DECLARE_ASSET));
    }

    private void validateDataDeclareUpdateAsset(Map<String, Object> createAssetRequest) {
        declareServiceFactory.validateDataDeclare((Map<String, Object>) createAssetRequest.get(Constants.KEY_DECLARE_ASSET));
    }

    private void validateDataOriginalCreateAsset(Map<String, Object> createAssetRequest) {
       originalServiceFactory.validateDataOriginal((Map<String, Object>) createAssetRequest.get(Constants.KEY_ORIGINAL_ASSET));
    }

    private void validateDataOriginalCreateAssetLot(Map<String, Object> createAssetRequest) {
        originalServiceFactory.validateDataOriginal((Map<String, Object>) createAssetRequest.get(Constants.KEY_ORIGINAL_ASSET));
    }

    private void validateDataOriginalUpdateAsset(Map<String, Object> createAssetRequest) {
        originalServiceFactory.validateDataOriginal((Map<String, Object>) createAssetRequest.get(Constants.KEY_ORIGINAL_ASSET));
    }

    private void validateDataModuleCreateAsset(Map<String, Object> createAssetRequest) throws ValidateFiledException {
        modulesServiceFactory.validateDataModules((List<Map<String,Object>>) createAssetRequest.get(Constants.KEY_MODULE));
    }

    private void validateDataModuleCreateAssetLot(Map<String, Object> createAssetRequest) throws ValidateFiledException {
        modulesServiceFactory.validateDataModules((List<Map<String,Object>>) createAssetRequest.get(Constants.KEY_MODULE));
    }


    private void validateDataModuleUpdateAsset(Map<String, Object> createAssetRequest) throws ValidateFiledException {
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

    private void validateDataCommonCreateAssetLot(Map<String, Object> createAssetRequest) {
        Map<String,Object> commonDataAsset = (Map<String, Object>) createAssetRequest.get(Constants.KEY_COMMON);
//        Integer idDepartment = ValueUtil.getIntegerByObject(commonDataAsset.get("idDepartment"));
//        departmentService.findDepartmentByIdDepartmentAndStatus(idDepartment, Constants.DEPARTMENT_ACTIVE_STATUS);
//        Integer idLocation = ValueUtil.getIntegerByObject(commonDataAsset.get("idLocation"));
//        locationService.findLocationByIdLocationAndIdDepartmentAndVisible(idLocation, idDepartment, Constants.LOCATION_ACTIVE_STATUS);
        Integer idAssetCategory = ValueUtil.getIntegerByObject(commonDataAsset.get("idAssetCategory"));
        assetCategoriesService.findAssetCategoriesByVisibleAndIdAssetCategory(idAssetCategory, Constants.ASSET_CATEGORY_IS_VISIBLE);
        Integer idUnit = ValueUtil.getIntegerByObject(commonDataAsset.get("idUnit"));
        unitsService.findUnitsByIdUnitAndStatus(idUnit, Constants.UNITS_IS_ACTIVE);
        Integer idDocumentsAttack = ValueUtil.getIntegerByObject(commonDataAsset.get("idDocumentAttack"));
        documentAttackService.findDocumentAttackByIdDocumentAndStatus(idDocumentsAttack, Constants.DOCUMENT_ATTACK_ACTIVE_STATUS);
        Integer idProject = ValueUtil.getIntegerByObject(commonDataAsset.get("idProjects"));
        projectsService.findProjectsByIdProjectAndStatus(idProject, Constants.PROJECTS_IS_VISIBLE);
    }

    private void validateDataCommonUpdateAsset(Map<String, Object> createAssetRequest) {
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

    private void validateDataCommonUpdateAssetLot(Map<String, Object> createAssetRequest) {
        Map<String,Object> commonDataAsset = (Map<String, Object>) createAssetRequest.get(Constants.KEY_COMMON);
//        Integer idDepartment = ValueUtil.getIntegerByObject(commonDataAsset.get("idDepartment"));
//        departmentService.findDepartmentByIdDepartmentAndStatus(idDepartment, Constants.DEPARTMENT_ACTIVE_STATUS);
//        Integer idLocation = ValueUtil.getIntegerByObject(commonDataAsset.get("idLocation"));
//        locationService.findLocationByIdLocationAndIdDepartmentAndVisible(idLocation, idDepartment, Constants.LOCATION_ACTIVE_STATUS);
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

    private void storeNewAssetLot(Map<String, Object> createAssetLotRequest) throws ValidateFiledException {
        log.info("Init store asset");
        Asset assetParent = createDataAssetParentLot(createAssetLotRequest);
        //CREATE ORIGINAL OF FORMATION FOR ASSSETPARENT
        Map<String,Object> commonDataAsset = (Map<String, Object>) createAssetLotRequest.get(Constants.KEY_COMMON);
        saveAssetOriginalOfFormations(assetParent, commonDataAsset);
        storeDepreciation(createAssetLotRequest, assetParent);
        storeModulesDataAsset(createAssetLotRequest, assetParent);
        storeOriginalDataAsset(createAssetLotRequest, assetParent);
        storeDeclareDataAsset(createAssetLotRequest, assetParent);
        List<Asset> assetChildren = storeCommonDataAssetChildrenLot(createAssetLotRequest,assetParent);
        storeDepreciationLot(createAssetLotRequest, assetChildren);
        storeModulesDataAssetLot(createAssetLotRequest, assetChildren);
        storeOriginalDataAssetLot(createAssetLotRequest, assetChildren);
        storeDeclareDataAssetLot(createAssetLotRequest, assetChildren);
    }


    private void storeDepreciation(Map<String, Object> createAssetRequest, Asset asset) {
        log.info("Storing depreciation data asset");
        Map<String, Object> depreciationAsset = (Map<String, Object>) createAssetRequest.get(Constants.KEY_DEPRECIATION);
        AssetDepreciation depreciation = contructionDataAssetDepreciation(depreciationAsset, asset.getIdAsset());
        assetDepreciationService.save(depreciation);
        log.info("Stored success depreciation data asset");
    }

    private void storeDepreciationLot(Map<String, Object> createAssetRequest, List<Asset> assetChildren) {
        log.info("Storing depreciation data asset");
        Map<String, Object> depreciationAsset = (Map<String, Object>) createAssetRequest.get(Constants.KEY_DEPRECIATION);
        for (Asset assetChild: assetChildren) {
            AssetDepreciation depreciation = contructionDataAssetDepreciation(depreciationAsset, assetChild.getIdAsset());
            assetDepreciationService.save(depreciation);
        }
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

    private void storeDeclareDataAssetLot(Map<String, Object> createAssetRequest, List<Asset> assetChildren) throws ValidateFiledException {
        log.info("Storing declare data asset");
        for (Asset assetChild : assetChildren) {
            Map<String, Object> declareDataAsset = (Map<String, Object>) createAssetRequest.get(Constants.KEY_DECLARE_ASSET);
            declareDataAsset.put("idAsset", assetChild.getIdAsset());
            DeclareFactory declareFactory = (DeclareFactory) ProxyInitDataAssetUtil.
                    proxyInitDeclareDataAsset(ValueUtil.getStringByObject(declareDataAsset.get(Constants.KEY_TYPE_DECLARE)));
            IDeclare iDeclare = declareFactory.createDeclare(declareDataAsset);
            declareServiceFactory.save(iDeclare, declareDataAsset);
            log.info("Finish store declare factory " + declareFactory.getClass());
        }
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

    private void storeOriginalDataAssetLot(Map<String, Object> createAssetRequest, List<Asset> assetChildren) throws ValidateFiledException {
        log.info("Storing original data asset");
        for (Asset assetChild : assetChildren) {
            Map<String,Object> originalDataAsset = (Map<String, Object>) createAssetRequest.get(Constants.KEY_ORIGINAL_ASSET);
            originalDataAsset.put("idAsset", assetChild.getIdAsset());
            OriginalFactory originalFactory = (OriginalFactory) ProxyInitDataAssetUtil.
                    proxyInitOriginalDataAsset(ValueUtil.getStringByObject(originalDataAsset.get(Constants.KEY_TYPE_ORIGINAL_ASSET)));
            IOriginal iOriginal = originalFactory.createOriginal(originalDataAsset);
            originalServiceFactory.save(iOriginal, originalDataAsset);
            log.info("Finish store original factory " + originalFactory.getClass());
        }
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

    private void storeModulesDataAssetLot(Map<String, Object> createAssetRequest, List<Asset> assetChildren) throws ValidateFiledException {
        log.info("Storing modules data asset");
        List<HashMap<String,Object>> modulesDataAsset = (List<HashMap<String,Object>>) createAssetRequest.get(Constants.KEY_MODULE);
        for (Asset assetChild : assetChildren) {
            if (!CollectionUtils.isEmpty(modulesDataAsset)) {
                for (HashMap<String, Object> moduleDataAsset : modulesDataAsset) {
                    if (StringUtils.isNotBlank(ValueUtil.getStringByObject(moduleDataAsset.get("codeUser")))) {
                        Optional<CsvcUser> user = csvcUserService.findByCodeUser(ValueUtil.getStringByObject(moduleDataAsset.get("codeUser")));
                        if (user.isEmpty()) {
                            throw new UsernameNotFoundException("User not found!");
                        }
                        if (!user.get().isAccountNonLocked()) {
                            throw new UsernameNotFoundException("User is locked!");
                        }
                        moduleDataAsset.put("idUser", user.get().getIdUser());
                    }
                    moduleDataAsset.put("idAsset", assetChild.getIdAsset());
                    ModuleFactory moduleFactory = (ModuleFactory) ProxyInitDataAssetUtil.
                            proxyInitModuleDataAsset(ValueUtil.getStringByObject(moduleDataAsset.get(Constants.KEY_TYPE_MODULE)));
                    IModules iModules = moduleFactory.createModule(moduleDataAsset);
                    modulesServiceFactory.save(iModules, moduleDataAsset);
                    log.info("Finish store module factory " + moduleFactory.getClass());
                }
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

    private List<Asset> storeCommonDataAssetChildrenLot(Map<String, Object> createAssetLotRequest, Asset assetParentLot) {
        log.info("Storing common data asset LOT ");
        Map<String,Object> commonDataAsset = (Map<String, Object>) createAssetLotRequest.get(Constants.KEY_COMMON);
        List<Map<String, Object>> dataAssetChildrenLot = (List<Map<String, Object>>) commonDataAsset.get(Constants.KEY_CHILDREN_DISTRIBUTION);
        List<Asset> assetChildren = createAssetChildren(assetParentLot,dataAssetChildrenLot);
        saveAssetChildrenOriginalOfFormations(assetChildren, commonDataAsset);
        return assetChildren;
    }

    private void saveAssetChildrenOriginalOfFormations(List<Asset> assetChildren, Map<String, Object> commonDataAsset) {
        List<HashMap<String,Object>> assetOriginalOfFormationData = (List<HashMap<String,Object>>) commonDataAsset.get(Constants.KEY_ASSET_ORIGINAL_OF_FORMATION);
        List<AssetOriginalOfFormation> originalOfFormations = new ArrayList<>();
        String currentTime = String.valueOf(new Date().getTime());
        for (Asset assetChild: assetChildren) {
            if (!CollectionUtils.isEmpty(assetOriginalOfFormationData)) {
                for (HashMap<String, Object> obj : assetOriginalOfFormationData) {
                    AssetOriginalOfFormation originalOfFormation = new AssetOriginalOfFormation();
                    originalOfFormation.setIdOriginalOfFormation(ValueUtil.getIntegerByObject(obj.get("idOriginOfFormation")));
                    originalOfFormation.setIdAsset(assetChild.getIdAsset());
                    originalOfFormation.setTimeCreated(currentTime);
                    originalOfFormation.setTimeModified(currentTime);
                    originalOfFormation.setValue(ValueUtil.getStringByObject(obj.get("value")));
                    originalOfFormations.add(originalOfFormation);
                }
            }
        }
        if (!CollectionUtils.isEmpty(originalOfFormations)) {
            assetOriginalOfFormationService.saveAll(originalOfFormations);
        }
    }

    private List<Asset> createAssetChildren(Asset parentAsset, List<Map<String, Object>> dataAssetChildrenDistribution) {
        List<Asset> assetChildren = new ArrayList<>();
        if (!CollectionUtils.isEmpty(dataAssetChildrenDistribution)){
            dataAssetChildrenDistribution.forEach(obj -> {
                assetChildren.add(constructionChildAssetLot(parentAsset, obj));
            });
        }
        return assetRepository.saveAll(assetChildren);
    }

    private Asset createSingleAssetChildren(Asset parentAsset, Map<String, Object> dataAssetChildrenDistribution) {
        return assetRepository.save(constructionChildAssetLot(parentAsset, dataAssetChildrenDistribution));
    }


    private Asset constructionChildAssetLot(Asset parentAsset, Map<String, Object> obj) {
        Asset childAsset = new Asset();
        childAsset.setName(parentAsset.getName());
        childAsset.setIdAssetCategory(parentAsset.getIdAssetCategory());
        childAsset.setCodeAsset(ValueUtil.getStringByObject(obj.get("codeAsset")));
        childAsset.setSalt(String.valueOf(UUID.randomUUID()));
        childAsset.setIdDocumentAttack(parentAsset.getIdDocumentAttack());
        childAsset.setIdDepartment(ValueUtil.getIntegerByObject(obj.get("idDepartment")));
        childAsset.setIdLocation(ValueUtil.getIntegerByObject(obj.get("idLocation")));
        childAsset.setIdUnit(parentAsset.getIdUnit());
        childAsset.setIdProjects(parentAsset.getIdProjects());
        childAsset.setPurpose(parentAsset.getPurpose());
        childAsset.setNotes(parentAsset.getNotes());
        childAsset.setDescription(parentAsset.getDescription());
        childAsset.setQuantity(Constants.QUANTITY_DEFAULT);
        childAsset.setFileAttack(parentAsset.getFileAttack());
        String timeCurrent = String.valueOf(new Date().getTime());
        childAsset.setTimeCreated(timeCurrent);
        childAsset.setTimeModified(timeCurrent);
        childAsset.setIdDepartmentDefault(parentAsset.getIdDepartmentDefault());
        childAsset.setIdLevelTypeAsset(parentAsset.getIdLevelTypeAsset());
        childAsset.setIdInstance(parentAsset.getIdInstance());
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        childAsset.setIdUserCreated(csvcUser.getIdUser());
        childAsset.setIdUserModified(csvcUser.getIdUser());
        childAsset.setIdDepartmentOrigin(parentAsset.getIdDepartmentOrigin());
        childAsset.setParent(parentAsset.getIdAsset());
        assetRepository.save(childAsset);

        return childAsset;
    }

    private Asset createDataAssetParentLot(Map<String, Object> createAssetLotRequest) {
        Map<String,Object> dataAssetParent = (Map<String, Object>) createAssetLotRequest.get(Constants.KEY_COMMON);
        Asset assetParent = new Asset();
        assetParent.setName(ValueUtil.getStringByObject(dataAssetParent.get("name")));
        assetParent.setIdAssetCategory(ValueUtil.getIntegerByObject(dataAssetParent.get("idAssetCategory")));
        assetParent.setCodeAsset(ValueUtil.getStringByObject(dataAssetParent.get("codeAsset")));
        assetParent.setSalt(String.valueOf(UUID.randomUUID()));
        assetParent.setIdDocumentAttack(ValueUtil.getIntegerByObject(dataAssetParent.get("idDocumentAttack")));
        assetParent.setIdDepartment(ValueUtil.getIntegerByObject(dataAssetParent.get("idDepartment")));
        assetParent.setIdLocation(ValueUtil.getIntegerByObject(dataAssetParent.get("idLocation")));
        assetParent.setIdUnit(ValueUtil.getIntegerByObject(dataAssetParent.get("idUnit")));
        assetParent.setIdProjects(ValueUtil.getIntegerByObject(dataAssetParent.get("idProjects")));
        assetParent.setPurpose(ValueUtil.getStringByObject(dataAssetParent.get("purpose")));
        assetParent.setNotes(ValueUtil.getStringByObject(dataAssetParent.get("notes")));
        assetParent.setDescription(ValueUtil.getStringByObject(dataAssetParent.get("description")));
        assetParent.setQuantity(ValueUtil.getIntegerByObject(dataAssetParent.get("quantity")));
        assetParent.setFileAttack(ValueUtil.getStringByObject(dataAssetParent.get("fileAttack")));
        String timeCurrent = String.valueOf(new Date().getTime());
        assetParent.setTimeCreated(timeCurrent);
        assetParent.setTimeModified(timeCurrent);
        assetParent.setIdDepartmentDefault(ValueUtil.getIntegerByObject(dataAssetParent.get("idDepartmentDefault")));
        assetParent.setIdLevelTypeAsset(ValueUtil.getIntegerByObject(dataAssetParent.get("idLevelTypeAsset")));
        assetParent.setIdInstance(ValueUtil.getIntegerByObject(dataAssetParent.get("idInstance")));
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        assetParent.setIdUserCreated(csvcUser.getIdUser());
        assetParent.setIdUserModified(csvcUser.getIdUser());
        assetParent.setIdDepartmentOrigin(csvcUser.getIdDepartmentCurrent());
        return assetRepository.save(assetParent);
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
        asset.setCodeAsset(ValueUtil.getStringByObject(dataAsset.get("codeAsset")));
        asset.setSalt(String.valueOf(UUID.randomUUID()));
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
        asset.setIdDepartmentOrigin(csvcUser.getIdDepartmentCurrent());
        return asset;
    }

    @Override
    public String downloadFileTemplateImportAsset() throws IOException {
        return filesStorageService.downLoadFileImportAsset();
    }

    @Override
    public String exportFileReportByPath(String pathFile) throws IOException {
        return filesStorageService.downLoadReportByPathFile(pathFile);
    }

    public void uploadFileImportAsset(MultipartFile file) throws FileExcelException, ValidateFiledException, JsonProcessingException {
    ValidateExcelUtils.checkFileExcel(file);
    List<Map<String, Object>> assetRequests = handleUploadFileAsset(file);
        //Lưu Request vào bảng tạm, rồi sao khi xử lý thì lưu vào assete sau.
        //common:error
    for (Map<String, Object> data: assetRequests) {
        Map<String,Object> commonDataAsset = (Map<String, Object>) data.get(Constants.KEY_COMMON);
        ObjectMapper objectMapper = new ObjectMapper();
        String assetRequestsJson = objectMapper.writeValueAsString(data);
        String assetErrorRequestsJson = objectMapper.writeValueAsString(commonDataAsset.get("error"));
        String dateNow = String.valueOf(new Date().getTime());
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AssetInstance assetInstance = new AssetInstance();
        assetInstance.setIdUser(csvcUser.getIdUser());
        assetInstance.setIdDepartmentOriginal(csvcUser.getIdDepartmentCurrent());
        assetInstance.setTimeCreated(dateNow);
        assetInstance.setTimeModified(dateNow);
        assetInstance.setValue(assetRequestsJson);
        assetInstance.setError(assetErrorRequestsJson);
        assetInstanceRepository.save(assetInstance);
    }

        //lưu bảng tạm
        //khi chọn bảng tạm
        //Map<String, Object> abc  =(Map<String, Object>)Object
//    for (Map<String, Object> createAssetRequest : assetRequests) {
//        createAssetFromFile(createAssetRequest);
//        }
    }

    @Override
    public Page<FindAllAssetResponseToIncrease> findAllAssetToIncrease(FinaAllAssetToIncreaseRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        List<Integer> idsDepartment = ((CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getIdsDepartmentCurrent();
        request.setIdsDepartmentOriginal(idsDepartment);
        Page<FindAllAssetDto> findAllAssetDtos = assetRepository.findAllAssetDtoToIncrease(request, pageable);
        return new PageImpl<>(convertToFindAllAssetToIncreaseResponse(findAllAssetDtos.getContent()),
                pageable, findAllAssetDtos.getTotalElements());
    }

    @Override
    public Page<FindAllAssetDocumentResponse> findAllAssetDocumentByCodeDocument(FindAllAssetDocumentRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        List<Integer> idsDepartment = ((CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getIdsDepartmentCurrent();
        request.setIdsDepartmentOriginal(idsDepartment);
        Page<FindAllAssetDto> findAllAssetDtos = null;
        if (request.getTypeProcess().equals(Constants.CODE_TYPE_PROCESS_INVENTORY)) {
            findAllAssetDtos = assetRepository.findAllAssetDocumentInventoryByCodeDocument(request, pageable);
        } else {
            findAllAssetDtos = assetRepository.findAllAssetDocumentByCodeDocument(request, pageable);
        }
        return new PageImpl<>(convertToFindAllAssetDocument(findAllAssetDtos.getContent()),
                pageable, findAllAssetDtos.getTotalElements());
    }

    @Override
    public String generateCodeAsset(String prefix) {
        if (prefix.equals(Constants.PREFIX_ASSET_LOT)){
            return prefixAssetLot(prefix);
        } else if (prefix.equals(Constants.PREFIX_ASSET)) {
            return prefixAsset(prefix);
        }
        return prefix;
    }

    @Override
    public Page<FindAllAssetResponseToInventory> findAllAssetToInventory(FindAllAssetToInventoryRequest inventoryRequest) {
        Pageable pageable = PageUtils.buildPage(inventoryRequest.getPage(), inventoryRequest.getSize());
        List<Integer> idsDepartment = ((CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getIdsDepartmentCurrent();
        inventoryRequest.setIdsDepartmentOriginal(idsDepartment);
        Page<FindAllAssetDto> findAllAssetDtos = assetRepository.findAllAssetDtoToInventory(inventoryRequest, pageable);
        return new PageImpl<>(convertToFindAllAssetToInventoryResponse(findAllAssetDtos.getContent()),
                pageable, findAllAssetDtos.getTotalElements());
    }

    private List<FindAllAssetResponseToInventory> convertToFindAllAssetToInventoryResponse(List<FindAllAssetDto> content) {
        List<FindAllAssetResponseToInventory> response = new ArrayList<>();
        for (FindAllAssetDto dto : content){
            FindAllAssetResponseToInventory inventory = new FindAllAssetResponseToInventory();
            inventory.setCodeAsset(dto.getCodeAsset());
            inventory.setNameAsset(dto.getNameAsset());
            inventory.setNameAssetCategory(dto.getNameAssetCategory());
            inventory.setCodeAssetCategory(dto.getCodeAssetCategory());
            inventory.setCodeDepartment(dto.getCodeDepartment());
            inventory.setNameDepartment(dto.getNameDepartment());
            inventory.setTimeCreated(DateUtil.formatToPattern(new Date(dto.getTimeCreated()),DateUtil.DATE_FORMAT));
            inventory.setTimeModified(DateUtil.formatToPattern(new Date(dto.getTimeModified()),DateUtil.DATE_FORMAT));
            inventory.setIdAsset(dto.getIdAsset());
            inventory.setSalt(dto.getSalt());
            inventory.setQuantityOriginal(dto.getQuantity());
            inventory.setRestValueOriginal(dto.getRestValue());
            inventory.setTotalOriginalOfFormationOriginal(String.valueOf(
                    Arrays.stream(dto.getOriginalOfFormation().split("-"))
                            .mapToLong(Long::parseLong)
                            .sum()
            ));
            inventory.setQuantityInventory(dto.getQuantity());
            inventory.setTotalOriginalOfFormationInventory(inventory.getTotalOriginalOfFormationOriginal());
            inventory.setRestValueInventory(dto.getRestValue());
            response.add(inventory);
        }
        return response;
    }

    private String prefixAsset(String prefix) {
        int minLength = 4;
        Integer idDepartment = ((CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getIdDepartmentCurrent();
        Department department =
                departmentService.findDepartmentByIdDepartmentAndStatus(idDepartment, Constants.DEPARTMENT_ACTIVE_STATUS);
        int codeValueCurrent = 1;
        if (StringUtils.isNotBlank(department.getCode())){
            prefix = department.getCode();
        }
        Optional<Asset> asset = assetRepository.findAssetByIdDepartmentOrigin(idDepartment);
        if (asset.isEmpty()) {
            return prefix + String.format("%0"+ minLength +"d", codeValueCurrent) + "-";
        }
        codeValueCurrent = Integer.parseInt(asset.get().getCodeAsset().replace(prefix,"").replace(Constants.PREFIX_ASSET_LOT,"").replace(Constants.PREFIX_ASSET_KK,"").split("-")[0]);
        if (String.valueOf(codeValueCurrent).length() > minLength) {
            minLength = minLength + 2;
        }
        return prefix + String.format("%0" + minLength + "d",(codeValueCurrent + 1)) + "-";
    }

    private String prefixAssetLot(String prefix) {
        int minLength = 4;
        Integer idDepartment = ((CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getIdDepartmentCurrent();
        Department department =
                departmentService.findDepartmentByIdDepartmentAndStatus(idDepartment, Constants.DEPARTMENT_ACTIVE_STATUS);
        int codeValueCurrent = 1;
        if (StringUtils.isNotBlank(department.getCode())){
            prefix = department.getCode() + Constants.PREFIX_ASSET_LOT;
        }
        Optional<Asset> asset = assetRepository.findAssetLotByIdDepartmentOrigin(idDepartment);
        if (asset.isEmpty()) {
            return prefix + String.format("%0" + minLength +"d", codeValueCurrent) + "-";
        }
        codeValueCurrent = Integer.parseInt(asset.get().getCodeAsset().replace(prefix,"").split("-")[0]);
        if (String.valueOf(codeValueCurrent).length() > minLength) {
            minLength = minLength + 2;
        }
        return prefix + String.format("%0" + minLength + "d",(codeValueCurrent + 1)) + "-";
    }


    private List<FindAllAssetDocumentResponse> convertToFindAllAssetDocument(List<FindAllAssetDto> content) {
        List<FindAllAssetDocumentResponse> responses = new ArrayList<>();
        for (FindAllAssetDto dto : content) {
            FindAllAssetDocumentResponse response = new FindAllAssetDocumentResponse();
            response.setCodeAsset(dto.getCodeAsset());
            response.setNameAsset(dto.getNameAsset());
            response.setNameAssetCategory(dto.getNameAssetCategory());
            response.setCodeAssetCategory(dto.getCodeAssetCategory());
            response.setCodeDepartment(dto.getCodeDepartment());
            response.setNameDepartment(dto.getNameDepartment());
            response.setTimeCreated(DateUtil.formatToPattern(new Date(dto.getTimeCreated()), DateUtil.DATE_FORMAT));
            response.setTimeModified(DateUtil.formatToPattern( new Date(dto.getTimeModified()),DateUtil.DATE_FORMAT));
            responses.add(response);
        }
        return responses;
    }

    @Override
    public void createAssetFromFile(Map<String, Object> createAssetRequest) throws JsonProcessingException,
            ValidateFiledException {
        Map<String, Object> dataCreateAssetRequest =
                objectMapper.readValue(JSONObjectUtils.toJSONString(createAssetRequest), Map.class);
//        validateDataCreateAsset(dataCreateAssetRequest);

        storeNewAssetFromFile(dataCreateAssetRequest);
    }
    private void storeNewAssetFromFile(Map<String, Object> createAssetRequest) throws ValidateFiledException {
        log.info("Init store asset");
        Asset asset = storeCommonData(createAssetRequest);
        storeModulesDataAsset(createAssetRequest, asset);
        storeOriginalDataAsset(createAssetRequest, asset);
        storeDeclareDataAsset(createAssetRequest, asset);
        storeDepreciation(createAssetRequest, asset);

    }



    //extract ID IN STT_ID_...
    public Integer extractIdSTTFromExcel(String input) {
        if (input != null && input.contains("_")) {
            String[] parts = input.split("_");
            if (parts.length > 1) {
                String numberStr = parts[1];
                if (!numberStr.isEmpty()) {
                    try {
                        return Integer.parseInt(numberStr);
                    } catch (NumberFormatException e) {
                        return null;
                    }
                }
            }
        }
        return null;
    }
    //êexexxtract String after STT_ID_...
    public String extractNameAfterIdSTTFromExcel(String input) {
        if (input != null && input.contains("_")) {
            String[] parts = input.split("_");
            if (parts.length > 2) {
                return parts[2];
            }
        }
        return null;
    }

    //extract id in id_...
    public Integer extractIdValueFromExcel(String input) {
        if (input != null && input.contains("_")) {
            String firstPart = input.split("_")[0];
            if (!firstPart.isEmpty()) {
                try {
                    return Integer.parseInt(firstPart);
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        }
        return null;
    }
    //extract name after id in id_name
    public String extractNameAfterIDFromExcel(String input) {
        if (input != null && input.contains("_")) {
            String[] parts = input.split("_", 2);
            if (parts.length > 1) {
                return parts[1];
            }
        }
        return null;
    }
    //STT_0001_Name lay ra 0001
    public String extractCodeValueFromExcel(String input) {
        if (input != null && input.contains("_")) {
            String[] parts = input.split("_");
            if (parts.length > 1) {
                return parts[1];
            }
        }
        return input;
    }
    //STT_0001_Name lay ra Name
    public String extractNameAfterCodeValueFromExcel(String input) {
        if (input != null && input.contains("_")) {
            String[] parts = input.split("_");
            return parts[2];
        }
        return input;
    }
    public String extractUserNameUsedAsset(String input) {
        if (input != null && input.contains("(")) {
            String firstPart = input.split("\\(")[0].trim();
            if (!firstPart.isEmpty()) {
                return firstPart;
            }
        }
        return null;
    }
    private boolean hasDataInRow(XSSFRow row, int numCellsToCheck) {

        int limit = Math.min(numCellsToCheck, row.getLastCellNum());
        int countCheckExits = 0;
        for (int cellIndex = 0; cellIndex < limit; cellIndex++) {
            XSSFCell cell = row.getCell(cellIndex);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                ++countCheckExits;
            }
        }
        if (countCheckExits == numCellsToCheck){
            return true;
        }
        return false;
    }
    private List<Map<String, Object>> handleUploadFileAsset(MultipartFile file) {
        List<Map<String, Object>> assetRequests = new ArrayList<>();
        int constantMaximumRow = 2000;
        int indexSheet = 0;
        int indexRowStartToReadData = 3;
        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(indexSheet);
            int totalRow = xssfSheet.getLastRowNum();
            if (totalRow > (constantMaximumRow + indexRowStartToReadData)){
                totalRow = constantMaximumRow;
            }
            List<XSSFRow> allRows = new ArrayList<>();
            for (int i = indexRowStartToReadData; i <= totalRow; i++) {
                XSSFRow row = xssfSheet.getRow(i);
                if (row != null && hasDataInRow(row,1)) {
                    allRows.add(row);
                }
//                if (row != null) {
//                    allRows.add(row);
//                }
            }
            int batchSize = Constants.SIZE_HANDLE;
            for (int start = 0; start < allRows.size(); start += batchSize) {
                List<Integer> instanceCategoryListExcel= new ArrayList<>();
                List<Integer> categoryListExcel = new ArrayList<>();
                List<Integer> departmentAndDefaultListExcel = new ArrayList<>();
                List<Integer> locationListExcel= new ArrayList<>();
                List<Integer> unitsListExcel = new ArrayList<>();
                List<Integer> projectListExcel = new ArrayList<>();
                List<Integer> documentAttackListExcel = new ArrayList<>();
                List<Integer> originalListExcel= new ArrayList<>();
                List<Integer> countryProducerListExcel = new ArrayList<>();
                List<Integer> typeUseListExcel = new ArrayList<>();
                List<String> provincesListExcel = new ArrayList<>();
                List<String> districtListExcel = new ArrayList<>();
                List<String> wardsListExcel = new ArrayList<>();
                List<Integer> positionNameAllListExcel = new ArrayList<>();
                List<Integer> medicineTypeListExcel = new ArrayList<>();
                List<Integer> medicineGroupListExcel = new ArrayList<>();
                List<Integer> originalOfFormationIds = new ArrayList<>();
                List<String> userUsedInModuleExcel= new ArrayList<>();
                List<OriginalOfFormation> originalOfFormationList = originalOfFormationRepository.findAll();
                for (OriginalOfFormation original : originalOfFormationList) {
                    originalOfFormationIds.add(original.getIdOriginalOfFormation());
                }
                for (int i = start; i < Math.min(start + batchSize, allRows.size()); i++) {
                    XSSFRow row = allRows.get(i);
                    String idInstanceCategoryExcel = (String) ExcelUtil.convertValue(row.getCell(0), CellType.STRING);
                    String idCategoryExcel = (String) ExcelUtil.convertValue(row.getCell(1), CellType.STRING);
                    String idDepartmentExcel = (String) ExcelUtil.convertValue(row.getCell(3), CellType.STRING);
                    String idLocationExcel = (String) ExcelUtil.convertValue(row.getCell(4), CellType.STRING);
                    String idDefaultDepartmentExcel = (String) ExcelUtil.convertValue(row.getCell(10), CellType.STRING);
                    String idUnitsExcel = (String) ExcelUtil.convertValue(row.getCell(5), CellType.STRING);
                    String idDocumentAttackExcel = (String) ExcelUtil.convertValue(row.getCell(6), CellType.STRING);
                    String idProjectExcel = (String) ExcelUtil.convertValue(row.getCell(7), CellType.STRING);
                    String idOriginalExcel = (String) ExcelUtil.convertValue(row.getCell(12), CellType.STRING);
                    String idCountryProducerMachineExcel = (String) ExcelUtil.convertValue(row.getCell(20), CellType.STRING);
                    String idCountryProducerArchitectureExcel = (String) ExcelUtil.convertValue(row.getCell(41), CellType.STRING);
                    String idCountryProducerCarExcel = (String) ExcelUtil.convertValue(row.getCell(55), CellType.STRING);
                    String idCountryProducerVehicleExcel = (String) ExcelUtil.convertValue(row.getCell(75), CellType.STRING);
                    String idCountryProducerAnimalTreeExcel = (String) ExcelUtil.convertValue(row.getCell(85), CellType.STRING);
                    String idCountryProducerOtherAssetExcel = (String) ExcelUtil.convertValue(row.getCell(91), CellType.STRING);
                    String idTypeUseMachineExcel = (String) ExcelUtil.convertValue(row.getCell(22), CellType.STRING);
                    String idTypeUseCarExcel = (String) ExcelUtil.convertValue(row.getCell(62), CellType.STRING);
                    String idTypeUseVehicleExcel = (String) ExcelUtil.convertValue(row.getCell(82), CellType.STRING);
                    String idTypeUseAnimalTreeExcel = (String) ExcelUtil.convertValue(row.getCell(86), CellType.STRING);
                    String idTypeUseOtherAssetExcel = (String) ExcelUtil.convertValue(row.getCell(93), CellType.STRING);
                    String idProvincesGroundExcel = (String) ExcelUtil.convertValue(row.getCell(23), CellType.STRING);
                    String idDistrictsGroundExcel = (String) ExcelUtil.convertValue(row.getCell(24), CellType.STRING);
                    String idWardsGroundExcel = (String) ExcelUtil.convertValue(row.getCell(25), CellType.STRING);
                    String idPositionNameCarExcel = (String) ExcelUtil.convertValue(row.getCell(63), CellType.STRING);
                    String idPositionNameOtherCarExcel = (String) ExcelUtil.convertValue(row.getCell(64), CellType.STRING);
                    String idProvincesHouseExcel = (String) ExcelUtil.convertValue(row.getCell(29), CellType.STRING);
                    String idDistrictsHouseExcel = (String) ExcelUtil.convertValue(row.getCell(30), CellType.STRING);
                    String idWardsHouseExcel = (String) ExcelUtil.convertValue(row.getCell(31), CellType.STRING);
                    String idPositionNameOtherVehicleExcel = (String) ExcelUtil.convertValue(row.getCell(83), CellType.STRING);
                    String idMedicineTypeExcel = (String) ExcelUtil.convertValue(row.getCell(94), CellType.STRING);
                    String idMedicineGroupExcel = (String) ExcelUtil.convertValue(row.getCell(95), CellType.STRING);
                    String userNameUsedMachine = (String) ExcelUtil.convertValue(row.getCell(21), CellType.STRING);
                    String userNameUsedCar = (String) ExcelUtil.convertValue(row.getCell(61), CellType.STRING);
                    String userNameUsedOtherVehicle = (String) ExcelUtil.convertValue(row.getCell(81), CellType.STRING);
                    String userNameUsedOtherAsset = (String) ExcelUtil.convertValue(row.getCell(92), CellType.STRING);

                    if (idInstanceCategoryExcel != null) {

                        instanceCategoryListExcel.add(extractIdSTTFromExcel(idInstanceCategoryExcel));
                    }
                    if (idCategoryExcel != null) {
                        categoryListExcel.add(extractIdValueFromExcel(idCategoryExcel));
                    }
                    if (idDepartmentExcel != null) {
                        departmentAndDefaultListExcel.add(extractIdSTTFromExcel(idDepartmentExcel));
                    }
                    if (idLocationExcel != null  && !idLocationExcel.equals("Không có")) {
                        locationListExcel.add(extractIdValueFromExcel(idLocationExcel));
                    }
                    if (idDefaultDepartmentExcel != null) {
                        departmentAndDefaultListExcel.add(extractIdValueFromExcel(idDefaultDepartmentExcel));
                    }
                    if (idUnitsExcel != null) {
                        unitsListExcel.add(extractIdValueFromExcel(idUnitsExcel));
                    }
                    if (idProjectExcel != null) {
                        projectListExcel.add(extractIdValueFromExcel(idProjectExcel));
                    }
                    if (idDocumentAttackExcel != null) {
                        documentAttackListExcel.add(extractIdValueFromExcel(idDocumentAttackExcel));
                    }
                    if (idOriginalExcel != null) {
                        originalListExcel.add(extractIdValueFromExcel(idOriginalExcel));
                    }
                    if (idCountryProducerMachineExcel != null) {
                        countryProducerListExcel.add(extractIdValueFromExcel(idCountryProducerMachineExcel));
                    }
                    if (idCountryProducerArchitectureExcel != null) {
                        countryProducerListExcel.add(extractIdValueFromExcel(idCountryProducerArchitectureExcel));
                    }
                    if (idCountryProducerCarExcel != null) {
                        countryProducerListExcel.add(extractIdValueFromExcel(idCountryProducerCarExcel));
                    }
                    if (idCountryProducerVehicleExcel != null) {
                        countryProducerListExcel.add(extractIdValueFromExcel(idCountryProducerVehicleExcel));
                    }
                    if (idCountryProducerAnimalTreeExcel != null) {
                        countryProducerListExcel.add(extractIdValueFromExcel(idCountryProducerAnimalTreeExcel));
                    }
                    if (idCountryProducerOtherAssetExcel != null) {
                        countryProducerListExcel.add(extractIdValueFromExcel(idCountryProducerOtherAssetExcel));
                    }
                    if (idTypeUseMachineExcel != null) {
                        typeUseListExcel.add(extractIdValueFromExcel(idTypeUseMachineExcel));
                    }
                    if (idTypeUseCarExcel != null) {
                        typeUseListExcel.add(extractIdValueFromExcel(idTypeUseCarExcel));
                    }
                    if (idTypeUseVehicleExcel != null) {
                        typeUseListExcel.add(extractIdValueFromExcel(idTypeUseVehicleExcel));
                    }
                    if (idTypeUseAnimalTreeExcel != null) {
                        typeUseListExcel.add(extractIdValueFromExcel(idTypeUseAnimalTreeExcel));
                    }
                    if (idTypeUseOtherAssetExcel != null) {
                        typeUseListExcel.add(extractIdValueFromExcel(idTypeUseOtherAssetExcel));
                    }
                    if (idProvincesGroundExcel != null) {
                        provincesListExcel.add(extractCodeValueFromExcel(idProvincesGroundExcel));
                    }
                    if (idProvincesHouseExcel != null) {
                        provincesListExcel.add(extractCodeValueFromExcel(idProvincesHouseExcel));
                    }
                    if (idDistrictsGroundExcel != null) {
                        districtListExcel.add(extractCodeValueFromExcel(idDistrictsGroundExcel));
                    }
                    if (idDistrictsHouseExcel != null) {
                        districtListExcel.add(extractCodeValueFromExcel(idDistrictsHouseExcel));
                    }
                    if (idWardsGroundExcel != null) {
                        wardsListExcel.add(extractCodeValueFromExcel(idWardsGroundExcel));
                    }
                    if (idWardsHouseExcel != null) {
                        wardsListExcel.add(extractCodeValueFromExcel(idWardsHouseExcel));
                    }
                    if (idPositionNameCarExcel != null) {
                        positionNameAllListExcel.add(extractIdValueFromExcel(idPositionNameCarExcel));
                    }
                    if (idPositionNameOtherCarExcel != null) {
                        positionNameAllListExcel.add(extractIdValueFromExcel(idPositionNameOtherCarExcel));
                    }
                    if (idPositionNameOtherVehicleExcel != null) {
                        positionNameAllListExcel.add(extractIdValueFromExcel(idPositionNameOtherVehicleExcel));
                    }
                    if (idMedicineTypeExcel != null) {
                        medicineTypeListExcel.add(extractIdValueFromExcel(idMedicineTypeExcel));
                    }
                    if (idMedicineGroupExcel != null) {
                        medicineGroupListExcel.add(extractIdValueFromExcel(idMedicineGroupExcel));
                    }
                    if (userNameUsedMachine != null) {
                        userUsedInModuleExcel.add(extractUserNameUsedAsset(userNameUsedMachine));
                    }
                    if (userNameUsedCar != null) {
                        userUsedInModuleExcel.add(extractUserNameUsedAsset(userNameUsedCar));
                    }
                    if (userNameUsedOtherVehicle != null) {
                        userUsedInModuleExcel.add(extractUserNameUsedAsset(userNameUsedOtherVehicle));
                    }
                    if (userNameUsedOtherAsset != null) {
                        userUsedInModuleExcel.add(extractUserNameUsedAsset(userNameUsedOtherAsset));
                    }

                }
                Set<Integer> uniqueInstanceCategorySet = new HashSet<>(instanceCategoryListExcel);
                instanceCategoryListExcel = new ArrayList<>(uniqueInstanceCategorySet);
                Set<Integer> uniqueCategorySet = new HashSet<>(categoryListExcel);
                categoryListExcel = new ArrayList<>(uniqueCategorySet);
                Set<Integer> uniqueDepartmentListSet = new HashSet<>(departmentAndDefaultListExcel);
                departmentAndDefaultListExcel = new ArrayList<>(uniqueDepartmentListSet);
                Set<Integer> uniqueLocationAndDefaultLocationListSet = new HashSet<>(locationListExcel);
                locationListExcel = new ArrayList<>(uniqueLocationAndDefaultLocationListSet);
                Set<Integer> uniqueUnitListSet = new HashSet<>(unitsListExcel);
                unitsListExcel = new ArrayList<>(uniqueUnitListSet);
                Set<Integer> uniqueProjectListSet = new HashSet<>(projectListExcel);
                projectListExcel = new ArrayList<>(uniqueProjectListSet);
                Set<Integer> uniqueDocumentAttackSet = new HashSet<>(documentAttackListExcel);
                documentAttackListExcel = new ArrayList<>(uniqueDocumentAttackSet);
                Set<Integer> uniqueOriginalSet = new HashSet<>(originalListExcel);
                originalListExcel = new ArrayList<>(uniqueOriginalSet);
                Set<Integer> uniqueCountryProducerSet = new HashSet<>(countryProducerListExcel);
                countryProducerListExcel = new ArrayList<>(uniqueCountryProducerSet);
                Set<Integer> uniqueTypeUseSet = new HashSet<>(typeUseListExcel);
                typeUseListExcel = new ArrayList<>(uniqueTypeUseSet);
                Set<String> uniqueProvincesSet = new HashSet<>(provincesListExcel);
                provincesListExcel = new ArrayList<>(uniqueProvincesSet);
                Set<String> uniqueDistrictSet = new HashSet<>(districtListExcel);
                districtListExcel = new ArrayList<>(uniqueDistrictSet);
                Set<String> uniqueWardSet = new HashSet<>(wardsListExcel);
                wardsListExcel = new ArrayList<>(uniqueWardSet);
                Set<Integer> uniquePositionNameAllSet = new HashSet<>(positionNameAllListExcel);
                positionNameAllListExcel = new ArrayList<>(uniquePositionNameAllSet);
                Set<Integer> uniqueMedicineTypeAllSet = new HashSet<>(medicineTypeListExcel);
                medicineTypeListExcel = new ArrayList<>(uniqueMedicineTypeAllSet);
                Set<Integer> uniqueMedicineGroupSet = new HashSet<>(medicineGroupListExcel);
                medicineGroupListExcel = new ArrayList<>(uniqueMedicineGroupSet);
                Set<String> uniqueUserNameUsedSet = new HashSet<>(userUsedInModuleExcel);
                userUsedInModuleExcel = new ArrayList<>(uniqueUserNameUsedSet);

                List<AssetCategories> assetInstanceCategoryNamesList = assetCategoriesRepository.findAllAssetCategoriesByIdIn(instanceCategoryListExcel);
                List<AssetCategories> assetCategoriesList = assetCategoriesRepository.findAllAssetCategoriesByIdIn(categoryListExcel);
                List<OriginalOfFormation> ofFormationList = originalOfFormationRepository.findAllOriginalOfFormationById(originalOfFormationIds);
                List<Department> departmentList = departmentRepository.findDepartmentByIds(departmentAndDefaultListExcel);
                List<Location> locationList=locationRepository.findAllLocationById(locationListExcel);
                List<Units> unitsList=unitsRepository.findAllUnitsById(unitsListExcel);
                List<Projects> projectsList=projectsRepository.findAllProjectById(projectListExcel);
                List<DocumentAttack> documentAttackList=documentAttackRepository.findAllDocumentAttackId(documentAttackListExcel);
                List<Original> originalList=originalRepository.findAllOriginalById(originalListExcel);
                List<CountryProducer> countryProducerList=countryProducerRepository.findAllCountryProducerById(countryProducerListExcel);
                List<TypeUse> typeUseList=typeUseRepository.findAllTypeUseByIds(typeUseListExcel);
                List<Provinces> provincesList=provinceRepository.findAllProvincesByCodes(provincesListExcel);
                List<Districts> districtsList=districtsRepository.findAllDistrictsByCodes(districtListExcel);
                List<Wards> wardsList=wardsRepository.findAllWardsByCodes(wardsListExcel);
                List<PositionName> positionNameList=positionNameRepository.findPositionNameByListId(positionNameAllListExcel);
                List<MedicineType> medicineTypeList=medicineTypeRepository.findMedicineTypeByAllId(medicineTypeListExcel);
                List<MedicineGroup> medicineGroupList=medicineGroupRepository.findMedicineGroupByAllId(medicineGroupListExcel);
                List<String> codeUserNameUsedAssetList=csvcUserRepository.findCodeUserByListUserName(userUsedInModuleExcel);

                // Kiểm tra xem số luong có khớp ko
                if (categoryListExcel.size() != assetCategoriesList.size() || departmentAndDefaultListExcel.size() != departmentList.size()
                || locationList.size() != locationListExcel.size() || unitsList.size() != unitsListExcel.size()
                || projectsList.size() != projectListExcel.size() || documentAttackList.size() != documentAttackListExcel.size()
                || originalList.size() != originalListExcel.size() || countryProducerList.size() != countryProducerListExcel.size()
                || typeUseList.size() != typeUseListExcel.size() || provincesList.size() != provincesListExcel.size()
                        || districtsList.size() != districtListExcel.size() ||wardsList.size() != wardsListExcel.size()
                        || positionNameList.size() != positionNameAllListExcel.size() || medicineTypeList.size() != medicineTypeListExcel.size()
                || medicineGroupList.size() != medicineGroupListExcel.size() || instanceCategoryListExcel.size() != assetInstanceCategoryNamesList.size()
                || codeUserNameUsedAssetList.size() != userUsedInModuleExcel.size() ) {
                    throw new RuntimeException("You need update new file temple Upload Asset");
                }

                Map<String, OriginalOfFormation> originalOfFormationMap = ofFormationList.stream()
                        .collect(Collectors.toMap(OriginalOfFormation::getName, Function.identity(), (existing, replacement) -> existing));
                Map<Integer, AssetCategories> AssetCategoriesInstanceMap = assetInstanceCategoryNamesList.stream()
                        .collect(Collectors.toMap(AssetCategories::getIdAssetCategory, Function.identity(), (existing, replacement) -> existing));
                Map<String, String> userNameToCodeMap = IntStream.range(0, userUsedInModuleExcel.size())
                        .boxed()
                        .collect(Collectors.toMap(
                                userUsedInModuleExcel::get,      // Khóa là tên người dùng từ danh sách userUsedInModuleExcel
                                codeUserNameUsedAssetList::get   // Giá trị là mã code tương ứng từ danh sách codeUserNameUsedAssetList
                        ));
                for (int indexRow = start; indexRow < Math.min(start + batchSize, allRows.size()); indexRow++) {
                    XSSFRow row = allRows.get(indexRow);
                    if (row != null) {
                        assetRequests.add(convertExcelRowToMap(indexRow,row,originalOfFormationMap,AssetCategoriesInstanceMap,userNameToCodeMap));

                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return assetRequests;
    }


    private Map<String, Object> convertExcelRowToMap(int indexRow,XSSFRow row, Map<String, OriginalOfFormation> originalOfFormationMap, Map<Integer, AssetCategories> AssetCategoriesInstanceMap,Map<String, String> userNameToCodeMap) {
        Map<String, Object> createAssetRequest = new HashMap<>();

        createAssetRequest.put(Constants.INDEX_ROW, indexRow+1);
        // Gọi hàm xử lý commonData
        Map<String, Object> commonData = processCommonData(row,originalOfFormationMap,AssetCategoriesInstanceMap);
        createAssetRequest.put(Constants.KEY_COMMON, commonData);

        // Gọi hàm xử lý modulesDataAsset
        List<Map<String, Object>> modulesDataAsset = processModulesData(row, commonData, userNameToCodeMap);
        createAssetRequest.put(Constants.KEY_MODULE, modulesDataAsset);

        Map<String, Object> DeclareData = processDeclareData(row,commonData);
        createAssetRequest.put(Constants.KEY_DECLARE_ASSET, DeclareData);

        Map<String, Object> originalData = processOriginalData(row);
        createAssetRequest.put(Constants.KEY_ORIGINAL_ASSET, originalData);

        Map<String, Object> depreciationData = processDepreciationData(row);
        createAssetRequest.put(Constants.KEY_DEPRECIATION, depreciationData);

        return createAssetRequest;

    }

    private String getCellValue(Cell cell) {
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return "";
        }
        return (String) ExcelUtil.convertValue(cell, CellType.STRING);
    }
    private Map<String, Object> processCommonData(XSSFRow row, Map<String, OriginalOfFormation> originalOfFormationMap,Map<Integer, AssetCategories> AssetCategoriesInstanceMap) {
        Map<String, Object> commonData = new HashMap<>();

        List<String> errorList = new ArrayList<>();

        String instanceCategory = (String) ExcelUtil.convertValue(row.getCell(0), CellType.STRING);
        if (instanceCategory == null) {
            errorList.add("Thiếu dữ liệu Danh mục tài sản");
        }

        String category = (String) ExcelUtil.convertValue(row.getCell(1), CellType.STRING);
        if (category == null) {
            errorList.add("Thiếu dữ liệu cho Loại tài sản");
        }

        String nameAsset = (String) ExcelUtil.convertValue(row.getCell(2), CellType.STRING);
        if (nameAsset == null) {
            errorList.add("Thiếu dữ liệu cho Tên tài sản");
        }

        String department = (String) ExcelUtil.convertValue(row.getCell(3), CellType.STRING);
        if (department == null) {
            errorList.add("Thiếu dữ liệu Đơn vị sử dụng");
        }

        String location = (String) ExcelUtil.convertValue(row.getCell(4), CellType.STRING);
        if (location == null) {
            errorList.add("Thiếu dữ liệu cho trường Địa điểm sử dụng");
        }

        String unit = (String) ExcelUtil.convertValue(row.getCell(5), CellType.STRING);
        if (unit == null) {
            errorList.add("Thiếu dữ liệu cho Đơn vị tính của tài sản");
        }

        String documentAttack = (String) ExcelUtil.convertValue(row.getCell(6), CellType.STRING);
        if (documentAttack == null) {
            errorList.add("Thiếu dữ liệu Số quyết định trang cấp");
        }

        String project = (String) ExcelUtil.convertValue(row.getCell(7), CellType.STRING);
        if (project == null) {
            errorList.add("Thiếu dữ liệu Dự án của tài sản");
        }
        String description = (String) ExcelUtil.convertValue(row.getCell(8), CellType.STRING);
        String purpose = (String) ExcelUtil.convertValue(row.getCell(9), CellType.STRING);
        String departmentDefault = (String) ExcelUtil.convertValue(row.getCell(10), CellType.STRING);
        String notes = (String) ExcelUtil.convertValue(row.getCell(11), CellType.STRING);
        String originalOfFormationName = (String) ExcelUtil.convertValue(row.getCell(13), CellType.STRING);
        String originalOfFormationValues = (String) ExcelUtil.convertValue(row.getCell(14), CellType.STRING);
        String maximumTimeDepreciation = (String) ExcelUtil.convertValue(row.getCell(144), CellType.STRING);
        String minimumTimeDepreciation = (String) ExcelUtil.convertValue(row.getCell(143), CellType.STRING);
        String valueWearTear = (String) ExcelUtil.convertValue(row.getCell(138), CellType.STRING);
        String yearUsedWearTear = (String) ExcelUtil.convertValue(row.getCell(137), CellType.STRING);
        Optional<Department> departmentOptional = departmentRepository.findDepartmentById(extractIdSTTFromExcel(department));
        List<Map<String, Object>> originOfFormationList = new ArrayList<>();
        if (originalOfFormationName == null && originalOfFormationValues== null){
            Map<String, Object> originOfFormation = new HashMap<>();
            originOfFormation.put("idOriginOfFormation", null);
            originOfFormation.put("nameOriginOfFormation", null);
            originOfFormation.put("value", null);
        }else if(originalOfFormationName == null && originalOfFormationValues != null){
            String[] originOfFormationValuesArray = originalOfFormationValues.split(";");
            for (int i = 0; i < originOfFormationValuesArray.length; i++) {
                Map<String, Object> originOfFormation = new HashMap<>();
                originOfFormation.put("idOriginOfFormation", null);
                originOfFormation.put("nameOriginOfFormation", null);
                originOfFormation.put("value", originOfFormationValuesArray[i]);
            }
        }
        else if(originalOfFormationName != null && originalOfFormationValues == null){
            String[] originOfFormationNameArray = originalOfFormationValues.split(";");
            for (int i = 0; i < originOfFormationNameArray.length; i++) {
                Map<String, Object> originOfFormation = new HashMap<>();
                originOfFormation.put("idOriginOfFormation", originalOfFormationMap.get(originOfFormationNameArray[i]).getIdOriginalOfFormation());
                originOfFormation.put("nameOriginOfFormation", originOfFormationNameArray[i]);
                originOfFormation.put("value", null);
            }
        }
        else {
            String[] originOfFormationNameArray = originalOfFormationName.split(";");
            String[] originOfFormationValuesArray = originalOfFormationValues.split(";");
            if (originOfFormationNameArray.length != originOfFormationValuesArray.length) {
                errorList.add("Số lượng giá trị của 2 cột nguồn hình thành không bằng nhau");
            } else {

                for (int i = 0; i < originOfFormationNameArray.length; i++) {
                    if (originOfFormationNameArray[i].isEmpty() || originOfFormationValuesArray[i].isEmpty()) {
                        errorList.add("Dữ liệu nguồn hình thành không hợp lệ");
                    }
                }
            }
            int maxLength = Math.max(originOfFormationNameArray.length, originOfFormationValuesArray.length);

            for (int i = 0; i < maxLength; i++) {
                Map<String, Object> originOfFormation = new HashMap<>();
                if (originOfFormationNameArray.length - 1 < i) {
                    originOfFormation.put("idOriginOfFormation", null);
                    originOfFormation.put("nameOriginOfFormation", null);
                } else {
                    originOfFormation.put("idOriginOfFormation", originalOfFormationMap.get(originOfFormationNameArray[i]).getIdOriginalOfFormation());
                    originOfFormation.put("nameOriginOfFormation", originOfFormationNameArray[i]);
                }
                if (originOfFormationValuesArray.length - 1 < i) {
                    originOfFormation.put("value", null);
                } else {
                    originOfFormation.put("value", originOfFormationValuesArray[i]);
                }

                originOfFormationList.add(originOfFormation);
            }
        }


        commonData.put("codeAsset", generateCodeAsset(Constants.PREFIX_ASSET));
        commonData.put("name", nameAsset);
        commonData.put("idAssetCategory", extractIdValueFromExcel(category));
        commonData.put("nameAssetCategory", extractNameAfterIDFromExcel(category));
//        commonData.put("idInstance", assetInstanceCategoriesMap.get(instanceCategory).getIdAssetCategory());
        commonData.put("idInstance",extractIdSTTFromExcel(instanceCategory));
        commonData.put("codeInstance",AssetCategoriesInstanceMap.get(extractIdSTTFromExcel(instanceCategory)).getCodeName());
        commonData.put("nameInstance",extractNameAfterIdSTTFromExcel(instanceCategory));
        commonData.put("idParentInstance",AssetCategoriesInstanceMap.get(extractIdSTTFromExcel(instanceCategory)).getParent());
        commonData.put("idDepartment", extractIdSTTFromExcel(department));
        commonData.put("nameDepartment", extractNameAfterIdSTTFromExcel(department));
        commonData.put("codeDepartment", departmentOptional.map(Department::getCode).orElse(null));
        commonData.put("idLocation", extractIdValueFromExcel(location));
        commonData.put("nameLocation", extractNameAfterIDFromExcel(location));
        commonData.put("idUnit", extractIdValueFromExcel(unit));
        commonData.put("nameUnit", extractNameAfterIDFromExcel(unit));
        commonData.put("idDocumentAttack", extractIdValueFromExcel(documentAttack));
        commonData.put("nameDocumentAttack", extractNameAfterIDFromExcel(documentAttack));
        commonData.put("idProjects", extractIdValueFromExcel(project));
        commonData.put("nameProjects", extractNameAfterIDFromExcel(project));
//        commonData.put("idLevelTypeAsset", extractIdValueFromExcel(levelTypeAsset));
        commonData.put("originOfFormation", originOfFormationList);

        commonData.put("purpose", purpose);
        commonData.put("notes", notes);
        commonData.put("description", description);
        commonData.put("quantity", 1);
        commonData.put("idDepartmentDefault", extractIdValueFromExcel(departmentDefault));
        commonData.put("nameDepartmentDefault", extractNameAfterIDFromExcel(departmentDefault));

        commonData.put("maximumTimeDepreciation", maximumTimeDepreciation);
        commonData.put("minimumTimeDepreciation", minimumTimeDepreciation);
        commonData.put("valueWearTear", valueWearTear);
        commonData.put("yearUsedWearTear", yearUsedWearTear);
        // Các lỗi liên quan validate các trường
        commonData.put("error", errorList);
        return commonData;
    }

    private List<Map<String, Object>> processModulesData(XSSFRow row, Map<String, Object> commonData, Map<String, String> userNameToCodeMap) {
        List<Map<String, Object>> modulesDataAsset = new ArrayList<>();
//        Optional<AssetCategories> assetCategoriesInstanceOptional = assetCategoriesRepository.findAssetCategoryById((Integer) commonData.get("idInstance"));
        List<Modules> ModulesArray = modulesRepository.findAllModulesByIdAssetCategoryAndStatus((Integer) commonData.get("idInstance"), 1);
        handleModulesArray(row,ModulesArray);
        for (Modules modules : ModulesArray) {
            String typeModules = modules.getHardCode();
            Map<String, Object> moduleDataDetails = new HashMap<>();
            moduleDataDetails.put("typeModules", typeModules);
            moduleDataDetails.put("idModule", modules.getIdModule());

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
                    moduleDataDetails.put("nameCountryProducer",  extractNameAfterIDFromExcel(countryProducer));
                    moduleDataDetails.put("codeUser",  userNameToCodeMap.get(extractUserNameUsedAsset(userNameMachine)));
                    moduleDataDetails.put("userName",  extractUserNameUsedAsset(userNameMachine));
                    moduleDataDetails.put("idTypeUse", extractIdValueFromExcel(typeUse));
                    moduleDataDetails.put("nameTypeUse", extractNameAfterIDFromExcel(typeUse));
                    break;
                case "GroundModule":
                    String provinceGround = (String)ExcelUtil.convertValue(row.getCell(23), CellType.STRING);
                    String districtGround = (String)ExcelUtil.convertValue(row.getCell(24), CellType.STRING);
                    String wardGround = (String)ExcelUtil.convertValue(row.getCell(25), CellType.STRING);
                    moduleDataDetails.put("provinceCode", extractCodeValueFromExcel(provinceGround));
                    moduleDataDetails.put("provinceName", extractNameAfterCodeValueFromExcel(provinceGround));
                    moduleDataDetails.put("districtCode", extractCodeValueFromExcel(districtGround));
                    moduleDataDetails.put("districtName", extractNameAfterCodeValueFromExcel(districtGround));
                    moduleDataDetails.put("wardCode", extractCodeValueFromExcel(wardGround));
                    moduleDataDetails.put("wardName", extractNameAfterCodeValueFromExcel(wardGround));
                    moduleDataDetails.put("addressDetail", ExcelUtil.convertValue(row.getCell(26), CellType.STRING));
                    break;
                case "HouseModule":
                    String provinceHouse = (String)ExcelUtil.convertValue(row.getCell(29), CellType.STRING);
                    String districtHouse  = (String)ExcelUtil.convertValue(row.getCell(30), CellType.STRING);
                    String wardHouse  = (String)ExcelUtil.convertValue(row.getCell(31), CellType.STRING);
                    String houseBelongLand = (String) ExcelUtil.convertValue(row.getCell(28), CellType.STRING);
                    Object valueIsManagerGround;
                    valueIsManagerGround = (String) ExcelUtil.convertValue(row.getCell(27), CellType.STRING);
                    if (valueIsManagerGround != null && valueIsManagerGround.equals("Có")) {
                        moduleDataDetails.put("isManageGround",1);
                        moduleDataDetails.put("idInstance", extractIdValueFromExcel(houseBelongLand));
                        moduleDataDetails.put("nameInstance", extractNameAfterIDFromExcel(houseBelongLand));
                    }
                    else{
                        moduleDataDetails.put("isManageGround", -1);
                        moduleDataDetails.put("idInstance", null);
                    }
                    moduleDataDetails.put("provinceCode", extractCodeValueFromExcel(provinceHouse));
                    moduleDataDetails.put("provinceName", extractNameAfterCodeValueFromExcel(provinceHouse));
                    moduleDataDetails.put("districtCode", extractCodeValueFromExcel(districtHouse));
                    moduleDataDetails.put("districtName", extractNameAfterCodeValueFromExcel(districtHouse));
                    moduleDataDetails.put("wardCode", extractCodeValueFromExcel(wardHouse));
                    moduleDataDetails.put("wardName", extractNameAfterCodeValueFromExcel(wardHouse));
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
                    moduleDataDetails.put("nameInstance", extractNameAfterIDFromExcel(architectureBelongLand));
                    moduleDataDetails.put("length", ExcelUtil.convertValue(row.getCell(37), CellType.STRING));
                    moduleDataDetails.put("acreage", ExcelUtil.convertValue(row.getCell(38), CellType.STRING));
                    moduleDataDetails.put("volume", ExcelUtil.convertValue(row.getCell(39), CellType.STRING));
                    moduleDataDetails.put("publishDate", ExcelUtil.convertValue(row.getCell(40), CellType.STRING));
                    moduleDataDetails.put("idCountryProducer", extractIdValueFromExcel(countryProducerArchitecture));
                    moduleDataDetails.put("nameCountryProducer", extractNameAfterIDFromExcel(countryProducerArchitecture));
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
                        moduleDataDetails.put("valueTax", ExcelUtil.convertValue(row.getCell(43), CellType.STRING));
                    }
                    else{
                        moduleDataDetails.put("isFreeTax", -1);
                        moduleDataDetails.put("valueTax",null);
                    }
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
                    moduleDataDetails.put("nameCountryProducer",  extractIdValueFromExcel(countryProducerCar));
                    moduleDataDetails.put("licenseCertificateRegister", ExcelUtil.convertValue(row.getCell(56), CellType.STRING));
                    moduleDataDetails.put("publishDateLicense", ExcelUtil.convertValue(row.getCell(57), CellType.STRING));
                    moduleDataDetails.put("companyRegister", ExcelUtil.convertValue(row.getCell(58), CellType.STRING));
                    moduleDataDetails.put("source", ExcelUtil.convertValue(row.getCell(59), CellType.STRING));
                    moduleDataDetails.put("color", ExcelUtil.convertValue(row.getCell(60), CellType.STRING));
                    moduleDataDetails.put("codeUser",  userNameToCodeMap.get(extractUserNameUsedAsset(userNameCar)));
                    moduleDataDetails.put("userName",  extractUserNameUsedAsset(userNameCar));
                    moduleDataDetails.put("idTypeUse", extractIdValueFromExcel(typeUseCar));
                    moduleDataDetails.put("nameTypeUse", extractNameAfterIDFromExcel(typeUseCar));
                    moduleDataDetails.put("idPositionName", extractIdValueFromExcel(positionNameCar));
                    moduleDataDetails.put("namePositionName", extractNameAfterIDFromExcel(positionNameCar));
                    moduleDataDetails.put("idPositionNameOther", extractIdValueFromExcel(positionNameOtherCar));
                    moduleDataDetails.put("namePositionNameOther", extractNameAfterIDFromExcel(positionNameOtherCar));
                    break;
                case "OtherVehicleTransportModule":
                    String countryProducerOtherVehicle = (String)ExcelUtil.convertValue(row.getCell(75), CellType.STRING);
//                    Optional<CountryProducer> countryProducerOtherVehicleOptional=countryProducerRepository.findCiountryProducerByNameAndStatus(countryProducerOtherVehicle,1);
                    String userNameOtherVehicle= (String) ExcelUtil.convertValue(row.getCell(81), CellType.STRING);
                    Optional<CsvcUser> userOptionalOtherVehicle = csvcUserService.findByUserName(userNameOtherVehicle);
                    String typeUseOtherVehicle = (String) ExcelUtil.convertValue(row.getCell(82), CellType.STRING);
//                    Optional<TypeUse> typeUseOptionalOtherVehicle=typeUseRepository.findTypeUseByName(typeUseOtherVehicle);
                    String positionNameVehicle = (String) ExcelUtil.convertValue(row.getCell(83), CellType.STRING);
                    moduleDataDetails.put("licensePlate", ExcelUtil.convertValue(row.getCell(65), CellType.STRING));
                    moduleDataDetails.put("label", ExcelUtil.convertValue(row.getCell(66), CellType.STRING));
                    moduleDataDetails.put("loadCapacity", ExcelUtil.convertValue(row.getCell(67), CellType.STRING));
                    moduleDataDetails.put("numberSeats", ExcelUtil.convertValue(row.getCell(68), CellType.STRING));
                    moduleDataDetails.put("capacity", ExcelUtil.convertValue(row.getCell(69), CellType.STRING));
                    moduleDataDetails.put("cylinderCapacity", ExcelUtil.convertValue(row.getCell(70), CellType.STRING));
                    moduleDataDetails.put("clutchNumber", ExcelUtil.convertValue(row.getCell(71), CellType.STRING));
                    moduleDataDetails.put("vehicleIdentificationNumber", ExcelUtil.convertValue(row.getCell(72), CellType.STRING));
                    moduleDataDetails.put("machineNumber", ExcelUtil.convertValue(row.getCell(73), CellType.STRING));
                    moduleDataDetails.put("publishYear", ExcelUtil.convertValue(row.getCell(74), CellType.STRING));
                    moduleDataDetails.put("idCountryProducer",  extractIdValueFromExcel(countryProducerOtherVehicle));
                    moduleDataDetails.put("nameCountryProducer",  extractNameAfterIDFromExcel(countryProducerOtherVehicle));
                    moduleDataDetails.put("licenseCertificateRegister", ExcelUtil.convertValue(row.getCell(76), CellType.STRING));
                    moduleDataDetails.put("publishDateLicense", ExcelUtil.convertValue(row.getCell(77), CellType.STRING));
                    moduleDataDetails.put("companyRegister", ExcelUtil.convertValue(row.getCell(78), CellType.STRING));
                    moduleDataDetails.put("source", ExcelUtil.convertValue(row.getCell(79), CellType.STRING));
                    moduleDataDetails.put("color", ExcelUtil.convertValue(row.getCell(80), CellType.STRING));
                    moduleDataDetails.put("codeUser",  userNameToCodeMap.get(extractUserNameUsedAsset(userNameOtherVehicle)));
                    moduleDataDetails.put("userName",  extractUserNameUsedAsset(userNameOtherVehicle));
                    moduleDataDetails.put("idTypeUse", extractIdValueFromExcel(typeUseOtherVehicle));
                    moduleDataDetails.put("nameTypeUse", extractNameAfterIDFromExcel(typeUseOtherVehicle));
                    moduleDataDetails.put("idPositionName", extractIdValueFromExcel(positionNameVehicle));
                    moduleDataDetails.put("namePositionName", extractNameAfterIDFromExcel(positionNameVehicle));
                    break;

                case "TreeAndAnimalModule":
                    String countryProducerTreeAndAnimal = (String)ExcelUtil.convertValue(row.getCell(85), CellType.STRING);
//                    Optional<CountryProducer> countryProducerTreeAndAnimalOptional=countryProducerRepository.findCountryProducerByNameAndStatus(countryProducerTreeAndAnimal,1);
                    String typeUseTreeAndAnimal = (String) ExcelUtil.convertValue(row.getCell(86), CellType.STRING);
//                    Optional<TypeUse> typeUseOptionalTreeAndAnimal=typeUseRepository.findTypeUseByName(typeUseTreeAndAnimal);
                    moduleDataDetails.put("publishDate", ExcelUtil.convertValue(row.getCell(84), CellType.STRING));
                    moduleDataDetails.put("idTypeUse", extractIdValueFromExcel(typeUseTreeAndAnimal));
                    moduleDataDetails.put("nameTypeUse", extractNameAfterIDFromExcel(typeUseTreeAndAnimal));
                    moduleDataDetails.put("idCountryProducer", extractIdValueFromExcel(countryProducerTreeAndAnimal));
                    moduleDataDetails.put("nameCountryProducer", extractNameAfterIDFromExcel(countryProducerTreeAndAnimal));
                    break;
                case "OtherAssetModule":
                    String countryProducerOther = (String)ExcelUtil.convertValue(row.getCell(91), CellType.STRING);
//                    Optional<CountryProducer> countryProducerOtherOptional=countryProducerRepository.findCountryProducerByNameAndStatus(countryProducerOther,1);
                    String userNameOther= (String) ExcelUtil.convertValue(row.getCell(92), CellType.STRING);
                    Optional<CsvcUser> userOptionalOther = csvcUserService.findByUserName(userNameOther);
                    String typeUseOther = (String) ExcelUtil.convertValue(row.getCell(93), CellType.STRING);
//                    Optional<TypeUse> typeUseOptionalOther=typeUseRepository.findTypeUseByName(typeUseOther);

                    moduleDataDetails.put("label", ExcelUtil.convertValue(row.getCell(87), CellType.STRING));
                    moduleDataDetails.put("model", ExcelUtil.convertValue(row.getCell(88), CellType.STRING));
                    moduleDataDetails.put("serial", ExcelUtil.convertValue(row.getCell(89), CellType.STRING));
                    moduleDataDetails.put("publishDate", ExcelUtil.convertValue(row.getCell(90), CellType.STRING));
                    moduleDataDetails.put("idCountryProducer", extractIdValueFromExcel(countryProducerOther));
                    moduleDataDetails.put("nameCountryProducer", extractNameAfterIDFromExcel(countryProducerOther));
                    moduleDataDetails.put("codeUser",  userNameToCodeMap.get(extractUserNameUsedAsset(userNameOther)));
                    moduleDataDetails.put("userName",  extractUserNameUsedAsset(userNameOther));
                    moduleDataDetails.put("idTypeUse", extractIdValueFromExcel(typeUseOther));
                    moduleDataDetails.put("nameTypeUse", extractNameAfterIDFromExcel(typeUseOther));
                    break;
                case "MedicineModule":
                    String medicineType = (String)ExcelUtil.convertValue(row.getCell(94), CellType.STRING);
//                    Optional<MedicineType> medicineTypeOptional=medicineTypeRepository.findMedicineTypeByName(medicineType);
                    String medicineGroup = (String)ExcelUtil.convertValue(row.getCell(95), CellType.STRING);
//                    Optional<MedicineGroup> medicineGroupOptional=medicineGroupRepository.findMedicineGroupByName(medicineGroup);
                    moduleDataDetails.put("idMedicineType", extractIdValueFromExcel(medicineType));
                    moduleDataDetails.put("nameMedicineType", extractNameAfterIDFromExcel(medicineType));
                    moduleDataDetails.put("idMedicineGroup", extractIdValueFromExcel(medicineGroup));
                    moduleDataDetails.put("nameMedicineGroup", extractNameAfterIDFromExcel(medicineGroup));
                    moduleDataDetails.put("publishDate", ExcelUtil.convertValue(row.getCell(96), CellType.STRING));
                    moduleDataDetails.put("expiryDate", ExcelUtil.convertValue(row.getCell(97), CellType.STRING));
                    moduleDataDetails.put("circulationNumber", ExcelUtil.convertValue(row.getCell(98), CellType.STRING));
                    moduleDataDetails.put("numberBatchOfGoods", ExcelUtil.convertValue(row.getCell(99), CellType.STRING));
                    moduleDataDetails.put("ownNameCirculationNumber", ExcelUtil.convertValue(row.getCell(100), CellType.STRING));
                    moduleDataDetails.put("ownAddressCirculationNumber", ExcelUtil.convertValue(row.getCell(101), CellType.STRING));
//                    moduleDataDetails.put("sparePartsAttack", ExcelUtil.convertValue(row.getCell(31), CellType.STRING));
                    break;
            }
//            moduleAssetData.put("data_details",moduleDataDetails);
            modulesDataAsset.add(moduleDataDetails);
        }

        return modulesDataAsset;
    }

    private void handleModulesArray(XSSFRow row, List<Modules> ModulesArray) {
        String medicineType = (String)ExcelUtil.convertValue(row.getCell(94), CellType.STRING);
        String medicineGroup = (String)ExcelUtil.convertValue(row.getCell(95), CellType.STRING);
        if (medicineType == null && medicineGroup == null){
            for (int i = 0 ; i< ModulesArray.size(); i++){
                if (ModulesArray.get(i).getHardCode().equals("MedicineModule")) {
                    ModulesArray.remove(i);
                }
            }
        }
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
        Optional<Declare> declareOptional= declareRepository.findDeclareByIdAssetCategoryAndVisible( (Integer) commonData.get("idInstance"),Constants.DECLARE_VISIBLE);
        Optional<TypeDeclareAsset> typeDeclareAssetOptional = typeDeclareAssetRepository.findTypeDeclareAssetByIdAssetCategory((Integer) commonData.get("idInstance"));
        String typeDeclare=declareOptional.get().getHardCode();
        Map<String, Object> declareData = new HashMap<>();
        declareData.put("typeDeclare", typeDeclare);
        declareData.put("idDeclare", declareOptional.get().getIdDeclare());
        declareData.put("idTypeDeclareAsset",  typeDeclareAssetOptional.map(TypeDeclareAsset::getIdTypeDeclareAsset).orElse(null));

        switch (typeDeclare) {
            case "CommonDeclare":
                List<Map<String, Object>> currentUsageList = new ArrayList<>();

                Object value;

                value = (String)ExcelUtil.convertValue(row.getCell(103), CellType.STRING);
                if (value != null && value.equals("Có")) {
                    currentUsageList.add(Map.of("idCurrentUsage", getIdForCurrentUsage("Quản lý nhà nước")));
                }

                value = (String)ExcelUtil.convertValue(row.getCell(104), CellType.STRING);
                if (value != null && value.equals("Có")) {
                    currentUsageList.add(Map.of("idCurrentUsage", getIdForCurrentUsage("HĐSN - Không KD")));
                }

                value = (String)ExcelUtil.convertValue(row.getCell(105), CellType.STRING);
                if (value != null && value.equals("Có")) {
                    currentUsageList.add(Map.of("idCurrentUsage", getIdForCurrentUsage("HĐSN - KD")));
                }

                value = (String)ExcelUtil.convertValue(row.getCell(106), CellType.STRING);
                if (value != null && value.equals("Có")) {
                    currentUsageList.add(Map.of("idCurrentUsage", getIdForCurrentUsage("HĐSN - LDLK")));
                }

                value = (String)ExcelUtil.convertValue(row.getCell(107), CellType.STRING);
                if (value != null && value.equals("Có")) {
                    currentUsageList.add(Map.of("idCurrentUsage", getIdForCurrentUsage("HĐSN - Cho thuê")));
                }

                value = (String)ExcelUtil.convertValue(row.getCell(108), CellType.STRING);
                if (value != null && value.equals("Có")) {
                    currentUsageList.add(Map.of("idCurrentUsage", getIdForCurrentUsage("Sử dụng khác")));
                }

                declareData.put("specification", ExcelUtil.convertValue(row.getCell(102), CellType.STRING));
                declareData.put("currentUsage",currentUsageList);

                break;

            case "HouseDeclare":

                Double workPlaceHouse=convertStringToDouble( ExcelUtil.convertValue(row.getCell(109), CellType.STRING));
                Double hdsnNoBussinessHouse=convertStringToDouble(ExcelUtil.convertValue(row.getCell(110), CellType.STRING));
                Double hdsnBussinessHouse=convertStringToDouble(ExcelUtil.convertValue(row.getCell(111), CellType.STRING));
                Double hdsnRentHouse=convertStringToDouble( ExcelUtil.convertValue(row.getCell(112), CellType.STRING));
                Double hdsnBondsHouse=convertStringToDouble(ExcelUtil.convertValue(row.getCell(113), CellType.STRING));
                Double syntheticUseHouse=convertStringToDouble( ExcelUtil.convertValue(row.getCell(114), CellType.STRING));
                Double blankPlaceHouse=convertStringToDouble( ExcelUtil.convertValue(row.getCell(115), CellType.STRING));
                Double livePlaceHouse=convertStringToDouble(ExcelUtil.convertValue(row.getCell(116), CellType.STRING));
                Double encroachedPlaceHouse=convertStringToDouble(ExcelUtil.convertValue(row.getCell(117), CellType.STRING));
                Double otherUseHouse=convertStringToDouble(ExcelUtil.convertValue(row.getCell(118), CellType.STRING));

                declareData.put("workplace", workPlaceHouse) ;
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

                Double workPlaceGround=convertStringToDouble( ExcelUtil.convertValue(row.getCell(109), CellType.STRING));
                Double hdsnNoBussinessGround=convertStringToDouble(ExcelUtil.convertValue(row.getCell(110), CellType.STRING));
                Double hdsnBussinessGround=convertStringToDouble(ExcelUtil.convertValue(row.getCell(111), CellType.STRING));
                Double hdsnRentGround=convertStringToDouble( ExcelUtil.convertValue(row.getCell(112), CellType.STRING));
                Double hdsnBondsGround=convertStringToDouble(ExcelUtil.convertValue(row.getCell(113), CellType.STRING));
                Double syntheticUseGround=convertStringToDouble( ExcelUtil.convertValue(row.getCell(114), CellType.STRING));
                Double blankPlaceGround=convertStringToDouble( ExcelUtil.convertValue(row.getCell(115), CellType.STRING));
                Double livePlaceGround=convertStringToDouble(ExcelUtil.convertValue(row.getCell(116), CellType.STRING));
                Double encroachedPlaceGround=convertStringToDouble(ExcelUtil.convertValue(row.getCell(117), CellType.STRING));
                Double otherUseGround=convertStringToDouble(ExcelUtil.convertValue(row.getCell(118), CellType.STRING));

                declareData.put("workplace", workPlaceGround );
                declareData.put("hdsnNoBussiness",hdsnNoBussinessGround);
                declareData.put("hdsnBussiness",(hdsnBussinessGround));
                declareData.put("hdsnRent", hdsnRentGround);
                declareData.put("hdsnBonds", hdsnBondsGround);
                declareData.put("syntheticUse", syntheticUseGround);
                declareData.put("blankPlace",blankPlaceGround);
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
                String goalsUseGround=(String) ExcelUtil.convertValue(row.getCell(120), CellType.STRING);
                declareData.put("idGoalsUseGround", extractIdValueFromExcel(goalsUseGround));
                declareData.put("nameGoalsUseGround", extractNameAfterIDFromExcel(goalsUseGround));
                declareData.put("licenseCertificateUseGround", ExcelUtil.convertValue(row.getCell(121), CellType.STRING));
                declareData.put("dateLicenseCertificateUseGround", ExcelUtil.convertValue(row.getCell(122), CellType.STRING));
                declareData.put("numberDecisionDeliverGround", ExcelUtil.convertValue(row.getCell(123), CellType.STRING));
                declareData.put("dateNumberDecisionDeliverGround", ExcelUtil.convertValue(row.getCell(124), CellType.STRING));
                declareData.put("contractNumberTransferGround", ExcelUtil.convertValue(row.getCell(125), CellType.STRING));
                declareData.put("dateContractNumberTransferGround", ExcelUtil.convertValue(row.getCell(126), CellType.STRING));
                declareData.put("numberDecisionRentGround", ExcelUtil.convertValue(row.getCell(127), CellType.STRING));
                declareData.put("dateNumberDecisionRentGround", ExcelUtil.convertValue(row.getCell(128), CellType.STRING));
                declareData.put("contractNumberRentGround", ExcelUtil.convertValue(row.getCell(129), CellType.STRING));
                declareData.put("dateContractNumberRentGround", ExcelUtil.convertValue(row.getCell(130), CellType.STRING));
                    declareData.put("anotherContract", ExcelUtil.convertValue(row.getCell(131), CellType.STRING));
//                declareData.put("idTypeDeclareAsset", ExcelUtil.convertValue(row.getCell(200), CellType.STRING));
                break;


        }
        return declareData;
    }
    private Map<String, Object> processOriginalData(XSSFRow row) {
        String original = (String) ExcelUtil.convertValue(row.getCell(12), CellType.STRING);
        Map<String, Object> departmentInFor = new HashMap<>();
        if(original !=null){
            Optional<Original> originalOptional = originalRepository.findOriginalById(extractIdValueFromExcel(original));
            departmentInFor.put("idOriginal", extractIdValueFromExcel(original));
            departmentInFor.put("nameOriginal", extractNameAfterIDFromExcel(original));
            departmentInFor.put("typeOriginal", originalOptional.map(Original::getHardCodeDev).orElse(null));
        }
        else {
            departmentInFor.put("idOriginal", null);
            departmentInFor.put("typeOriginal", null);
        }

        return departmentInFor;
    }
    public int calculateRemainingMonths(String dateFromExcel, int amountMonthsDepreciationNumber) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dateFromExcelParsed = LocalDate.parse(dateFromExcel, formatter);
        LocalDate dateAfterMonths = dateFromExcelParsed.plusMonths(amountMonthsDepreciationNumber);
        LocalDate currentDate = LocalDate.now();
        long monthsRemaining = ChronoUnit.MONTHS.between(currentDate, dateAfterMonths);
        if (monthsRemaining <= 0) {
            return 0;
        }
        return (int) monthsRemaining;
    }
    public int calculateRemainingYears(String dateFromExcel, int amountMonthsDepreciationNumber) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dateFromExcelParsed = LocalDate.parse(dateFromExcel, formatter);
        LocalDate dateAfterDepreciationMonths = dateFromExcelParsed.plusMonths(amountMonthsDepreciationNumber);
        LocalDate currentDate = LocalDate.now();

        long monthsRemaining = ChronoUnit.MONTHS.between(currentDate, dateAfterDepreciationMonths);

        if (monthsRemaining <= 6) {
            return 0;
        }
        int yearsRemaining = (int) Math.round((double) monthsRemaining / 12);
        return yearsRemaining;
    }
    private Map<String, Object> processDepreciationData(XSSFRow row) {
        Map<String, Object> depreciationInFor = new HashMap<>();

        String timeBuy = (String) ExcelUtil.convertValue(row.getCell(133), CellType.STRING);
        String timeStartedUsed = (String) ExcelUtil.convertValue(row.getCell(134), CellType.STRING);
        String timeStartedIncrease = (String) ExcelUtil.convertValue(row.getCell(135), CellType.STRING);
        String timeStartedWearTear = (String) ExcelUtil.convertValue(row.getCell(136), CellType.STRING);
        String timeEndWearTear = (String) ExcelUtil.convertValue(row.getCell(139), CellType.STRING);
        String timeStartedDepreciation = (String) ExcelUtil.convertValue(row.getCell(140), CellType.STRING);
        String amountMonthsDepreciation = (String) ExcelUtil.convertValue(row.getCell(142), CellType.STRING);

        Integer amountMonthsDepreciationNumber = null;
        if (amountMonthsDepreciation != null && !amountMonthsDepreciation.isEmpty()) {
                amountMonthsDepreciationNumber = Integer.parseInt(amountMonthsDepreciation);
        }
        Double cumulative = convertStringToDouble(ExcelUtil.convertValue(row.getCell(145), CellType.STRING));
        if (cumulative == null) {
            cumulative = (double) 0;
        }
        Object valueTypeCalculate = (String) ExcelUtil.convertValue(row.getCell(132), CellType.STRING);
        if (valueTypeCalculate != null) {
            switch (valueTypeCalculate.toString()) {
                case "Tính hao mòn":
                    depreciationInFor.put("typeCalculate", 1);
                    depreciationInFor.put("timeStartedWearTear", timeStartedWearTear); //bắt đầu tính hao mòn
                    depreciationInFor.put("timeEndWearTear", timeEndWearTear); //kết thúc tính hao mòn
                    depreciationInFor.put("timeStartedDepreciation", null); //bắt đầu tính khấu hao
                    depreciationInFor.put("amountMonthsDepreciation", null); //số tháng khấu hao
                    break;
                case "Tính khấu hao":
                    depreciationInFor.put("typeCalculate", 2);
                    depreciationInFor.put("timeStartedDepreciation", timeStartedDepreciation); //bắt đầu tính khấu hao
                    depreciationInFor.put("amountMonthsDepreciation", amountMonthsDepreciationNumber); //số tháng khấu hao
                    depreciationInFor.put("timeStartedWearTear", null); //bắt đầu tính hao mòn
                    depreciationInFor.put("timeEndWearTear", null); //kết thúc tính hao mòn
                    break;
                case "Tính cả hai":
                    depreciationInFor.put("typeCalculate", 3);
                    depreciationInFor.put("timeStartedWearTear", timeStartedWearTear); //bắt đầu tính hao mòn
                    depreciationInFor.put("timeEndWearTear", timeEndWearTear); //kết thúc tính hao mòn
                    depreciationInFor.put("timeStartedDepreciation", timeStartedDepreciation); //bắt đầu tính khấu hao
                    depreciationInFor.put("amountMonthsDepreciation", String.valueOf(amountMonthsDepreciationNumber)); //số tháng khấu hao
                    break;
            }
        }
        depreciationInFor.put("timeBuy", timeBuy);      // thời gian mua
        depreciationInFor.put("timeStartedUsed", timeStartedUsed); // bắt đầu sử dụng
        depreciationInFor.put("timeStartedIncrease", timeStartedIncrease); //bắt đầu ghi tăng
        depreciationInFor.put("timeYearTracking", String.valueOf(Year.now())); //năm theo dõi
        String valueDepreciation = (String) ExcelUtil.convertValue(row.getCell(14), CellType.STRING);
        double totalDepreciation = 0;
        if (valueDepreciation != null && !valueDepreciation.isEmpty()) {
            String[] valueDepreciationArray = valueDepreciation.split(";");
            for (String value : valueDepreciationArray) {
                totalDepreciation += Double.parseDouble(value.trim());
            }
        }
        depreciationInFor.put("valueDepreciation", String.valueOf(totalDepreciation)); //giá trị tổng original
        Object valueTypeDepreciation = (String) ExcelUtil.convertValue(row.getCell(141), CellType.STRING); // loại kỳ tích khấu hao
        if (valueTypeDepreciation != null && amountMonthsDepreciationNumber !=null) {
            if (valueTypeDepreciation.equals("Năm")) {
                depreciationInFor.put("typeDepreciation", 1);
                depreciationInFor.put("valueTypeDepreciation", String.valueOf(totalDepreciation * 12 / amountMonthsDepreciationNumber)); //giá trị tổng original theo năm
                if (timeStartedDepreciation != null && !timeStartedDepreciation.isEmpty()) {
                    int remainingYears = calculateRemainingYears(timeStartedDepreciation,amountMonthsDepreciationNumber);
                    depreciationInFor.put("amountRestMonthsDepreciation", String.valueOf(remainingYears)); // số năm khấu hao còn lại
                }
            } else if (valueTypeDepreciation.equals("Tháng")) {
                depreciationInFor.put("typeDepreciation", 2);
                depreciationInFor.put("valueTypeDepreciation", String.valueOf(totalDepreciation / amountMonthsDepreciationNumber)); //giá trị tổng original theo tháng
                if (timeStartedDepreciation != null && !timeStartedDepreciation.isEmpty()) {
                    int remainingMonths = calculateRemainingMonths(timeStartedDepreciation,amountMonthsDepreciationNumber);
                    depreciationInFor.put("amountRestMonthsDepreciation", String.valueOf(remainingMonths)); // số tháng khấu hao còn lại
                }
            }
        }
        depreciationInFor.put("cumulative", String.valueOf(cumulative));
        depreciationInFor.put("restValue", String.valueOf(totalDepreciation - cumulative)); // giá trị còn lại
        return depreciationInFor;
    }

}
