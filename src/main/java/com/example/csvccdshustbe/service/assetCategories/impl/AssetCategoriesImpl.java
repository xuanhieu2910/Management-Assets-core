package com.example.csvccdshustbe.service.assetCategories.impl;

import com.example.csvccdshustbe.dto.assetCategories.FindAllAssetCategoriesByCodeAndVisibleDto;
import com.example.csvccdshustbe.entity.AssetCategories;

import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.repository.assetCategories.AssetCategoriesRepository;
import com.example.csvccdshustbe.request.assetCategories.CreateAssetCategoryRequest;
import com.example.csvccdshustbe.request.assetCategories.FindAllAssetCategoriesRequest;
import com.example.csvccdshustbe.request.assetCategories.UpdateAssetCategoryRequest;
import com.example.csvccdshustbe.response.assetCategories.FindAllAssetCategoriesPickedResponse;
import com.example.csvccdshustbe.response.assetCategories.FindAllAssetCategoriesResponse;
import com.example.csvccdshustbe.service.assetCategories.AssetCategoriesService;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.PageUtils;
import com.example.csvccdshustbe.utility.ValueUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AssetCategoriesImpl implements AssetCategoriesService {


    @Autowired
    AssetCategoriesRepository assetCategoriesRepository;


    @Override
    public List<FindAllAssetCategoriesPickedResponse> findAllAssetCategoriesIsPicked() {
        List<AssetCategories> categories = assetCategoriesRepository.findAllAssetCategoriesIsPickedAndVisible();
        return convertToFindAllAssetCategoriesPicked(categories);
    }

    @Override
    public Page<FindAllAssetCategoriesResponse> findAllAssetCategoriesByCodeNameAndVisible(
            FindAllAssetCategoriesRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        Page<FindAllAssetCategoriesByCodeAndVisibleDto> categories =
                assetCategoriesRepository.findAllAssetCategoriesByCodeAndVisible(pageable, request);
        return new PageImpl<>(convertToFindAllAssetCategoriesByCodeAndVisible(categories.get().collect(Collectors.toList())), pageable, categories.getTotalElements());
    }

    @Override
    public AssetCategories findAssetCategoriesVisibleByCodeName(String codeName) throws Exception {
        Optional<AssetCategories> categories = assetCategoriesRepository.findAssetCategoriesVisibleByCodeName(codeName);
        if (!categories.isPresent()) {
            throw new Exception("Not found asset category by code name!");
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
        if (!assetCategoriesOptional.isPresent()) {
            throw new NotFoundException("Don't exits Medicine Type by id!");
        }
        if (StringUtils.isBlank(request.getName())) {
            throw new ValidateFiledException("Validate data request!");
        }
        if (!assetCategoriesOptional.get().getName().equals(request.getName()) ||
                !assetCategoriesOptional.get().getShortName().equals(request.getShortName())) {

            if (StringUtils.isNotBlank(request.getName())){
                ValueUtil.validateNumberOrCharacter(request.getName());
            }
            if (StringUtils.isNotBlank(request.getShortName())){
                ValueUtil.validateNumberOrCharacter(request.getShortName());
            }
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
        assetCategories.setIsPick(request.getIsPick());
//        assetCategories.setAssetCount(Constants.ASSET_CATEGORY_INIT_ASSET_COUNT);
//        assetCategories.setSortOrder(null);
        String timeModified = String.valueOf(new Date().getTime());
        assetCategories.setTimeModified(timeModified);
        return assetCategories;
    }
    @Override
    public void deleteAssetCategoryByIdAssetCategory(Integer idAssetCategory) {
        Optional<AssetCategories> assetCategoriesOptional = assetCategoriesRepository.findAssetCategoryById(idAssetCategory);
        if (!assetCategoriesOptional.isPresent()) {
            throw new NotFoundException("Don't exits Asset category by id by id!");
        }
        assetCategoriesRepository.delete(assetCategoriesOptional.get());
    }
    private AssetCategories createAssetCategoryRequest(CreateAssetCategoryRequest request) {
        AssetCategories categories = new AssetCategories();
        categories.setName(request.getName());
        categories.setShortName(request.getShortName());
        categories.setDescription(request.getDescription());
        categories.setParent(request.getParentId());
        categories.setVisible(request.getVisible());
        categories.setPathImage(request.getPathImage());
        categories.setIsPick(request.getIsPick());
        categories.setAssetCount(Constants.ASSET_CATEGORY_INIT_ASSET_COUNT);
        categories.setSortOrder(null);
        String timeCurrent = String.valueOf(new Date().getTime());
        categories.setTimeCreated(timeCurrent);
        categories.setTimeModified(timeCurrent);
        return categories;
    }

    private void validateCreateAssetCategory(CreateAssetCategoryRequest request) throws ValidateFiledException {
        if (StringUtils.isBlank(request.getName())){
            throw new ValidateFiledException("Validate data request!");
        }
        if (StringUtils.isNotBlank(request.getName())){
            ValueUtil.validateNumberOrCharacter(request.getName());
        }


        if (Objects.nonNull(request.getParentId())){
            Optional<AssetCategories> categories = assetCategoriesRepository.findAssetCategoryParentByParentId(request.getParentId());
            if (!categories.isPresent()) {
                throw new NotFoundException("Don't exits asset category by id " + request.getParentId());
            }
            if (!assetCategoriesRepository.checkAssetCategoriesByParentIdAndName(request.getParentId(), request.getName())){
                throw new ValidateFiledException("Exits name asset category in list categories, please use another name!");
            }
        }
        if (StringUtils.isNotBlank(request.getShortName())){
            ValueUtil.validateNumberOrCharacter(request.getShortName());
        }
    }

    private List<FindAllAssetCategoriesResponse> convertToFindAllAssetCategoriesByCodeAndVisible
            (List<FindAllAssetCategoriesByCodeAndVisibleDto> collect) {
        List<FindAllAssetCategoriesResponse> responses = new ArrayList<>();
        for (FindAllAssetCategoriesByCodeAndVisibleDto categorie : collect){
            FindAllAssetCategoriesResponse response = new FindAllAssetCategoriesResponse();
            response.setIdAssetCategory(categorie.getIdAssetCategory());
            response.setName(categorie.getName());
            response.setCodeName(categorie.getCodeName());
            response.setDepth(categorie.getDepth());
            response.setPath(categorie.getPath());
            response.setParent(categorie.getParent());
            responses.add(response);
        }
        return responses;
    }

    private List<FindAllAssetCategoriesPickedResponse> convertToFindAllAssetCategoriesPicked(List<AssetCategories> categories) {
        List<FindAllAssetCategoriesPickedResponse> responses = new ArrayList<>();
        for (AssetCategories ass: categories){
            FindAllAssetCategoriesPickedResponse res = new FindAllAssetCategoriesPickedResponse();
            res.setName(ass.getName());
            res.setCodeName(ass.getCodeName());
            res.setPathImage(ass.getPathImage());
            responses.add(res);
        }
        return responses;
    }
}
