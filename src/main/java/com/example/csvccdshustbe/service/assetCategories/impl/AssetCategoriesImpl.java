package com.example.csvccdshustbe.service.assetCategories.impl;

import com.example.csvccdshustbe.dto.assetCategories.*;
import com.example.csvccdshustbe.entity.AssetCategories;
import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.entity.Role;
import com.example.csvccdshustbe.enums.RolePattern;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.repository.assetCategories.AssetCategoriesRepository;
import com.example.csvccdshustbe.request.assetCategories.*;
import com.example.csvccdshustbe.response.assetCategories.FindAllAssetCategoriesPickedResponse;
import com.example.csvccdshustbe.response.assetCategories.FindAllAssetCategoriesResponse;
import com.example.csvccdshustbe.response.assetCategories.FindAllAssetCategoriesVisibleResponse;
import com.example.csvccdshustbe.response.assetCategories.FindAssetCategoryDetailsResponse;
import com.example.csvccdshustbe.service.assetCategories.AssetCategoriesService;
import com.example.csvccdshustbe.service.department.DepartmentService;
import com.example.csvccdshustbe.service.user.CsvcUserService;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.PageUtils;
import com.example.csvccdshustbe.utility.ValueUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.*;
import java.util.stream.Collectors;



@Service
public class AssetCategoriesImpl implements AssetCategoriesService {


    @Autowired
    AssetCategoriesRepository assetCategoriesRepository;
    @Autowired
    CsvcUserService csvcUserService;
    @Autowired
    DepartmentService departmentService;

    @Override
    public List<FindAllAssetCategoriesPickedResponse> findAllAssetCategoriesIsPicked() {
        List<FindAllAssetCategoriesPickedDto> categories = assetCategoriesRepository.findAllAssetCategoriesIsPickedAndVisible();
        return convertToFindAllAssetCategoriesPicked(categories);
    }

    @Override
    public Page<FindAllAssetCategoriesVisibleResponse> findAllAssetCategoriesVisibleByCodeAssetCategories(
            FindAllAssetCategoriesVisibleRequest request) throws ValidateFiledException {
        validateFindAllAssetCategoriesByCodeAssetCategories(request);
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());

        Page<FindAllAssetCategoriesByCodeAndVisibleDto> categories =
                assetCategoriesRepository.findAllAssetCategoriesByCodeAndVisible(pageable, request);
        return new PageImpl<>(convertToFindAllAssetCategoriesByCodeAndVisible(categories.get().collect(Collectors.toList())),
                pageable, categories.getTotalElements());
    }

    @Override
    public Page<FindAllAssetCategoriesVisibleResponse>
    findAllAssetCategoriesVisible(FindAllAssetCategoriesVisibleRequest request){
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Integer> idsDepartment = csvcUser.getIdsDepartmentCurrent();
        idsDepartment.add(Constants.DEFAULT_ASSET_CATEGORY);
        request.setIdsDepartment(idsDepartment);
        Page<FindAllAssetCategoriesByCodeAndVisibleDto> categories =
                assetCategoriesRepository.findAllAssetCategoriesVisible(pageable, request);
        return new PageImpl<>(convertToFindAllAssetCategoriesByCodeAndVisible(categories.get().collect(Collectors.toList())),
                pageable, categories.getTotalElements());
    }

    private void validateFindAllAssetCategoriesByCodeAssetCategories(FindAllAssetCategoriesVisibleRequest request) throws ValidateFiledException {
        if (StringUtils.isBlank(request.getCodeName())){
            throw new ValidateFiledException("Validate data");
        }
        CsvcUser user = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Integer> idsDepartment = user.getIdsDepartmentCurrent();
        idsDepartment.add(Constants.DEFAULT_ASSET_CATEGORY);
        request.setIdsDepartment(user.getIdsDepartmentCurrent());
    }

    @Override
    public Page<FindAllAssetCategoriesResponse> findAllAssetCategories(FindAllDocumentAssetCategoriesRequest request) {
            Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
            setListIdsDepartmentOriginal(request);
            Page<FindAllAssetCategoryDto> categories =
                    assetCategoriesRepository.findAllAssetCategories(pageable, request);
            return new PageImpl<>(convertToFindAllAssetCategoriesBy(categories.get().collect(Collectors.toList())), pageable, categories.getTotalElements());
    }

    private void setListIdsDepartmentOriginal(FindAllDocumentAssetCategoriesRequest request) {
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Integer> idsDepartment = csvcUser.getIdsDepartmentCurrent();
        idsDepartment.add(Constants.DEFAULT_ASSET_CATEGORY);
        request.setIdsDepartmentOriginal(idsDepartment);
    }

    @Override
    public AssetCategories findAssetCategoriesVisibleByCodeName(String codeName) throws Exception {
        Optional<AssetCategories> categories = assetCategoriesRepository.findAssetCategoriesVisibleByCodeName(codeName);
        if (categories.isEmpty()) {
            throw new Exception("Not found asset category by code name!");
        }
        return categories.get();
    }

    @Override
    public AssetCategories findAssetCategoriesByVisibleAndIdAssetCategory(Integer idAssetCategory, Integer visible) {
        Optional<AssetCategories> categories = assetCategoriesRepository.
                findAssetCategoriesByVisibleAndIdAssetCategory(idAssetCategory, visible);
        if (categories.isEmpty()) {
            throw new NotFoundException("Don't exits asset category!");
        }
        return categories.get();
    }

    @Override
    public void createAssetCategory(CreateAssetCategoryRequest request) throws ValidateFiledException {
        validateCreateAssetCategory(request);
        assetCategoriesRepository.save(createAssetCategoryRequest(request));
    }

    @Override
    public void updateAssetCategory(UpdateAssetCategoryRequest request) throws ValidateFiledException {
        AssetCategories assetCategories = validateUpdateAssetCategory(request);
        assetCategoriesRepository.save(editAssetCategory(assetCategories, request));
    }

    private AssetCategories validateUpdateAssetCategory(UpdateAssetCategoryRequest request)  throws ValidateFiledException{
        Optional<AssetCategories> assetCategoriesOptional = assetCategoriesRepository.findAssetCategoryById(request.getIdAssetCategory());
        if (assetCategoriesOptional.isEmpty()) {
            throw new NotFoundException("Don't exits Medicine Type by id!");
        }
        if (StringUtils.isBlank(request.getName())) {
            throw new ValidateFiledException("Validate data request!");
        }
        if (!assetCategoriesOptional.get().getName().equals(request.getName()) ||
                !assetCategoriesOptional.get().getShortName().equals(request.getShortName())) {
            if (!assetCategoriesRepository.checkExitsAssetCategoriesByNameOrShortName(request.getName(),
                     request.getShortName())) {
                throw new ValidateFiledException("Exits  asset category in list categories by name or short name!");
            }
        }
        return assetCategoriesOptional.get();
    }

    private AssetCategories editAssetCategory(AssetCategories assetCategories, UpdateAssetCategoryRequest request) {
        assetCategories.setName(request.getName());
        assetCategories.setShortName(request.getShortName());
        assetCategories.setDescription(request.getDescription());
        assetCategories.setParent(request.getParentId());
        assetCategories.setVisible(request.getVisible());
        assetCategories.setPathImage(request.getPathImage());
        assetCategories.setTypeTarget(request.getTypeTarget());
        assetCategories.setNumberCodePattern(request.getNumberCodePattern());
//        assetCategories.setIsPick(request.getIsPick());
//        assetCategories.setAssetCount(Constants.ASSET_CATEGORY_INIT_ASSET_COUNT);
//        assetCategories.setSortOrder(null);
        String timeModified = String.valueOf(new Date().getTime());
        assetCategories.setTimeModified(timeModified);
        assetCategories.setValueWearTear(request.getValueWearTear());
        assetCategories.setYearUsedWearTear(request.getYearUsedWearTear());
        assetCategories.setMinimumTimeDepreciation(request.getMinimumTimeDepreciation());
        assetCategories.setMaximumTimeDepreciation(request.getMaximumTimeDepreciation());
        assetCategories.setIsPick(request.getIsPick());
        return assetCategories;
    }
    @Override
    public void deleteAssetCategoryByIdAssetCategory(Integer idAssetCategory) throws ValidateFiledException {
        Optional<AssetCategories> assetCategoriesOptional = assetCategoriesRepository.findAssetCategoryById(idAssetCategory);
        if (assetCategoriesOptional.isEmpty()) {
            throw new NotFoundException("Don't exits Asset category by id by id!");
        }
        if (assetCategoriesRepository.isCheckExitsAssetByIdAssetCategory(idAssetCategory)) {
            throw new ValidateFiledException("Validate data");
        }
        assetCategoriesRepository.delete(assetCategoriesOptional.get());
    }

    @Override
    public BluePrintParentAssetCategoryDto findBluePrintParentAssetCategoryDtoById(Integer idParentAssetCategory){
        Optional<BluePrintParentAssetCategoryDto> dto = assetCategoriesRepository.findBluePrintAssetCategoryDtoById(idParentAssetCategory);
        if (dto.isEmpty()) {
            throw new NotFoundException("Don't exits asset category picked by id!");
        }
        return dto.get();
    }

    @Override
    public FindAssetCategoryDetailsResponse findAssetCategoryDetailsResponseByCode(String codeAssetCategory) {
        Optional<FindAssetCategoryDetailsResponse> response =
                assetCategoriesRepository.findAssetCategoryDetailsPickedResponseByCode(codeAssetCategory);
        if (response.isEmpty()) {
            throw new NotFoundException("Don't exits asset category by code!");
        }
        return response.get();
    }

    @Override
    public void updateStatusAssetCategory(UpdateStatusAssetCategory statusAssetCategory) throws ValidateFiledException {
        Optional<AssetCategories> assetCategoriesOptional =
                assetCategoriesRepository.findAssetCategoryById(statusAssetCategory.getIdAssetCategory());
        if (assetCategoriesOptional.isEmpty()){
            throw new NotFoundException("Don't exits asset category!");
        }
        if(!statusAssetCategory.getStatus().equals(Constants.ASSET_CATEGORY_IS_VISIBLE) &&
            !statusAssetCategory.getStatus().equals(Constants.ASSET_CATEGORY_UN_VISIBLE)) {
            throw new ValidateFiledException("Validate data!");
        }
        assetCategoriesOptional.get().setVisible(statusAssetCategory.getStatus());
        assetCategoriesRepository.save(assetCategoriesOptional.get());
    }

    @Override
    public Map<String, List<FindAllAssetCategoriesToDownloadDto>> findAllAssetCategoriesVisibleResponseToDownload() {
        List<FindAllAssetCategoriesPickedResponse> assetCategoriesIsPicked = findAllAssetCategoriesIsPicked();
        Integer idDepartment = csvcUserService.getInformationUser().getIdDepartment();
        List<Integer> idsDepartment = departmentService.findIdsStructureDepartment(idDepartment);
        idsDepartment.add(Constants.DEFAULT_ASSET_CATEGORY);
        Map<String, List<FindAllAssetCategoriesToDownloadDto>> responses = new HashMap<>();
        String keyword;
        for (FindAllAssetCategoriesPickedResponse assetPicked : assetCategoriesIsPicked){
            keyword = "STT_" + assetPicked.getIdAssetCategory() + "_" + assetPicked.getName();
            keyword = ValueUtil.convertToVietnamese(keyword).replaceAll(ValueUtil.REGEX_letter_digit_period_underscore, "");
            responses.put(keyword, assetCategoriesRepository.findAllAssetCategoriesByCodeParentVisibleToDownload(idsDepartment, assetPicked.getCodeName()));
        }
        return responses;
    }

    private AssetCategories createAssetCategoryRequest(CreateAssetCategoryRequest request) {
        AssetCategories categories = new AssetCategories();
        categories.setName(request.getName());
        categories.setShortName(request.getShortName());
        categories.setDescription(request.getDescription());
        categories.setParent(request.getParentId());
        categories.setVisible(request.getVisible());
        categories.setPathImage(request.getPathImage());
        categories.setIsPick(null);
        categories.setAssetCount(Constants.ASSET_CATEGORY_INIT_ASSET_COUNT);
        categories.setSortOrder(null);
        String timeCurrent = String.valueOf(new Date().getTime());
        categories.setTimeCreated(timeCurrent);
        categories.setTimeModified(timeCurrent);
        categories.setNumberCodePattern(request.getNumberCodePattern());
        categories.setValueWearTear(request.getValueWearTear());
        categories.setYearUsedWearTear(request.getYearUsedWearTear());
        categories.setMinimumTimeDepreciation(request.getMinimumTimeDepreciation());
        categories.setMaximumTimeDepreciation(request.getMaximumTimeDepreciation());
        categories.setTypeTarget(request.getTypeTarget());
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Role> roles = new ArrayList<>(csvcUser.getRole());
        if (roles.get(0).getTitle().equals(RolePattern.SuperAdmin.name())){
            categories.setIdDepartmentOriginal(Constants.DEFAULT_ASSET_CATEGORY);
        } else {
            categories.setIdDepartmentOriginal(csvcUserService.getInformationUser().getIdDepartment());
        }
        return categories;
    }

    private void validateCreateAssetCategory(CreateAssetCategoryRequest request) throws ValidateFiledException {
        if (StringUtils.isBlank(request.getName()) || StringUtils.isBlank(request.getNumberCodePattern())){
            throw new ValidateFiledException("Validate data request!");
        }
        if (Objects.nonNull(request.getParentId())){
            Optional<AssetCategories> categories = assetCategoriesRepository.findAssetCategoryParentByParentId(request.getParentId());
            if (categories.isEmpty()) {
                throw new NotFoundException("Don't exits asset category by id " + request.getParentId());
            }
            if (!assetCategoriesRepository.checkAssetCategoriesByParentIdAndName(request.getParentId(), request.getName())){
                throw new ValidateFiledException("Exits name asset category in list categories, please use another name!");
            }
        }
    }

    private List<FindAllAssetCategoriesVisibleResponse> convertToFindAllAssetCategoriesByCodeAndVisible
            (List<FindAllAssetCategoriesByCodeAndVisibleDto> collect) {
        List<FindAllAssetCategoriesVisibleResponse> responses = new ArrayList<>();
        for (FindAllAssetCategoriesByCodeAndVisibleDto categorie : collect){
            FindAllAssetCategoriesVisibleResponse response = new FindAllAssetCategoriesVisibleResponse();
            response.setIdAssetCategory(categorie.getIdAssetCategory());
            response.setName(categorie.getName());
            response.setCodeName(categorie.getCodeName());
            response.setDepth(categorie.getDepth());
            response.setPath(categorie.getPath());
            response.setVisible(categorie.getVisible());
            response.setParent(categorie.getParent());
            response.setValueWearTear(categorie.getValueWearTear());
            response.setYearUsedWearTear(categorie.getYearUsedWearTear());
            response.setMinimumTimeDepreciation(categorie.getMinimumTimeDepreciation());
            response.setMaximumTimeDepreciation(categorie.getMaximumTimeDepreciation());
            responses.add(response);
        }
        return responses;
    }

    private List<FindAllAssetCategoriesResponse> convertToFindAllAssetCategoriesBy
            (List<FindAllAssetCategoryDto> collect) {
        List<FindAllAssetCategoriesResponse> responses = new ArrayList<>();
        for (FindAllAssetCategoryDto categorie : collect){
            FindAllAssetCategoriesResponse response = new FindAllAssetCategoriesResponse();
            response.setIdAssetCategory(categorie.getIdAssetCategory());
            response.setName(categorie.getName());
            response.setCodeName(categorie.getCodeName());
            response.setDepth(categorie.getDepth());
            response.setPath(categorie.getPath());
            response.setParent(categorie.getParent());
            response.setIsPicked(categorie.getIsPick());
            response.setValueWearTear(categorie.getValueWearTear());
            response.setYearUsedWearTear(categorie.getYearUsedWearTear());
            response.setMinimumTimeDepreciation(categorie.getMinimumTimeDepreciation());
            response.setMaximumTimeDepreciation(categorie.getMaximumTimeDepreciation());
            response.setNameParent(categorie.getNameParent());
            response.setVisible(categorie.getVisible());
            response.setShortName(categorie.getShortName());
            if (categorie.getIdDepartmentOriginal().equals(Constants.DEFAULT_ASSET_CATEGORY)){
                response.setIsDefault(Constants.IS_DEFAULT);
            } else {
                response.setIsDefault(Constants.NOT_IS_DEFAULT);
            }
            response.setNumberCodePattern(categorie.getNumberCodePattern());
            response.setValueUnitDisplay(categorie.getNameUnit());
            responses.add(response);
        }
        return responses;
    }

    private List<FindAllAssetCategoriesPickedResponse> convertToFindAllAssetCategoriesPicked(List<FindAllAssetCategoriesPickedDto> categories) {
        List<FindAllAssetCategoriesPickedResponse> responses = new ArrayList<>();
        for (FindAllAssetCategoriesPickedDto pickedDto: categories){
            FindAllAssetCategoriesPickedResponse res = new FindAllAssetCategoriesPickedResponse();
            res.setName(pickedDto.getName());
            res.setCodeName(pickedDto.getCodeName());
            res.setPathImage(pickedDto.getPathImage());
            res.setIdAssetCategory(pickedDto.getIdAssetCategory());
            res.setIdParent(pickedDto.getIdParent());
            responses.add(res);
        }
        return responses;
    }
}
