package com.example.csvccdshustbe.service.toolCategories.impl;

import com.example.csvccdshustbe.entity.ToolCategories;
import com.example.csvccdshustbe.repository.toolCategories.ToolCategoriesRepository;
import com.example.csvccdshustbe.dto.toolCategories.FindAllToolCategoryDto;
import com.example.csvccdshustbe.entity.AssetCategories;
import com.example.csvccdshustbe.entity.CsvcUser;

import com.example.csvccdshustbe.entity.Role;
import com.example.csvccdshustbe.entity.ToolCategories;
import com.example.csvccdshustbe.enums.RolePattern;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.repository.toolCategories.ToolCategoriesRepository;
import com.example.csvccdshustbe.request.assetCategories.UpdateStatusAssetCategory;
import com.example.csvccdshustbe.request.toolCategories.CreateToolCategoryRequest;
import com.example.csvccdshustbe.request.toolCategories.FindAllToolCategoriesRequest;
import com.example.csvccdshustbe.request.toolCategories.UpdateStatusToolCategoryRequest;
import com.example.csvccdshustbe.request.toolCategories.UpdateToolCategoryRequest;
import com.example.csvccdshustbe.response.toolCategories.FindAllToolCategoriesResponse;
import com.example.csvccdshustbe.service.toolCategories.ToolCategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.csvccdshustbe.service.user.CsvcUserService;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.PageUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Optional;
import org.webjars.NotFoundException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ToolCategoriesServiceImpl implements ToolCategoriesService {

    @Autowired
    ToolCategoriesRepository toolCategoriesRepository;
    @Autowired
    CsvcUserService csvcUserService;

    @Override
    public ToolCategories findToolCategoryByIdToolCategoryAndVisible(Integer idToolCategory, Integer visible) {
        Optional<ToolCategories> toolCategory =
                toolCategoriesRepository.findToolCategoryByIdToolCategoryAndVisible(idToolCategory, visible);
        if (toolCategory.isEmpty()) {
            throw new NotFoundException("Don't exits tool category by id tool category and visible");
        }
        return toolCategory.get();
    }


    @Override
    public Page<FindAllToolCategoriesResponse> findAllToolCategories(FindAllToolCategoriesRequest request){
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        setListIdsDepartmentOriginal(request);
        Page<FindAllToolCategoryDto> toolCategories =
                toolCategoriesRepository.findAllToolCategories(pageable, request);
        return  new PageImpl<>(convertToFindAllToolCategoriesBy(toolCategories.get().collect(Collectors.toList())), pageable, toolCategories.getTotalElements());
    }

    private List<FindAllToolCategoriesResponse> convertToFindAllToolCategoriesBy(List<FindAllToolCategoryDto> collect) {
        List<FindAllToolCategoriesResponse> toolCategories = new ArrayList<>();
        for (FindAllToolCategoryDto toolCategoryDto : collect){
            FindAllToolCategoriesResponse response = new FindAllToolCategoriesResponse();
            response.setIdToolCategory(toolCategoryDto.getIdToolCategory());
            response.setName(toolCategoryDto.getName());
            response.setCodeTool(toolCategoryDto.getCodeTool());
            response.setShortName(toolCategoryDto.getShortName());
            response.setPath(toolCategoryDto.getPath());
            response.setDepth(toolCategoryDto.getDepth());
            response.setVisible(toolCategoryDto.getVisible());
            response.setParent(toolCategoryDto.getParent());
            response.setNameParent(toolCategoryDto.getNameParent());
            response.setIsLeaf(toolCategoryDto.getIsLeaf());
            if (toolCategoryDto.getIdDepartmentOriginal().equals(Constants.DEFAULT_ASSET_CATEGORY)){
                response.setIsDefault(Constants.IS_DEFAULT);
            } else {
                response.setIsDefault(Constants.NOT_IS_DEFAULT);
            }
            toolCategories.add(response);

        }
        return toolCategories;
    }

    private void setListIdsDepartmentOriginal(FindAllToolCategoriesRequest request) {
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Integer> idsDepartment = csvcUser.getIdsDepartmentCurrent();
        idsDepartment.add(Constants.DEFAULT_ASSET_CATEGORY);
        request.setIdsDepartmentOriginal(idsDepartment);
    }

    public void createToolCategory(CreateToolCategoryRequest request) throws ValidateFiledException {
        validateCreateToolCategory(request);
        toolCategoriesRepository.save(constructionCreateToolCategory(request));
    }

    private ToolCategories constructionCreateToolCategory(CreateToolCategoryRequest request) {
        ToolCategories categories = new ToolCategories();
        categories.setName(request.getName());
        categories.setShortName(request.getShortName());
        categories.setDescription(request.getDescription());
        categories.setParent(request.getParentId());
        categories.setVisible(request.getVisible());
        categories.setToolCount(Constants.ASSET_CATEGORY_INIT_ASSET_COUNT);
        categories.setSortOrder(null);
        String timeCurrent = String.valueOf(new Date().getTime());
        categories.setTimeCreated(timeCurrent);
        categories.setTimeModified(timeCurrent);
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Role> roles = new ArrayList<>(csvcUser.getRole());
        if (roles.get(0).getTitle().equals(RolePattern.SuperAdmin.name())){
            categories.setIdDepartmentOriginal(Constants.DEFAULT_ASSET_CATEGORY);
        } else {
            categories.setIdDepartmentOriginal(csvcUserService.getInformationUser().getIdDepartment());
        }
        return categories;
    }

    private void validateCreateToolCategory(CreateToolCategoryRequest request) throws ValidateFiledException {
        if (StringUtils.isBlank(request.getName())){
            throw new ValidateFiledException("Validate data request!");
        }
        if (Objects.nonNull(request.getParentId())){
            Optional<ToolCategories> categories = toolCategoriesRepository.findToolCategoryParentByParentId(request.getParentId());
            if (categories.isEmpty()) {
                throw new NotFoundException("Don't exits tool category by id " + request.getParentId());
            }
            if (!toolCategoriesRepository.checkToolCategoriesByParentIdAndName(request.getParentId(), request.getName())){
                throw new ValidateFiledException("Exits name tool category in list categories, please use another name!");
            }
        }
    }

    @Override
    public void updateToolCategory(UpdateToolCategoryRequest request) throws ValidateFiledException {
        ToolCategories toolCategories = validateUpdateToolCategory(request);
        toolCategoriesRepository.save(editAssetCategory(toolCategories, request));
    }

    private ToolCategories editAssetCategory(ToolCategories toolCategories, UpdateToolCategoryRequest request) {
        toolCategories.setName(request.getName());
        toolCategories.setShortName(request.getShortName());
        toolCategories.setDescription(request.getDescription());
        toolCategories.setParent(request.getParentId());
        toolCategories.setVisible(request.getVisible());
        String timeModified = String.valueOf(new Date().getTime());
        toolCategories.setTimeModified(timeModified);
        return toolCategories;
    }

    private ToolCategories validateUpdateToolCategory(UpdateToolCategoryRequest request)  throws ValidateFiledException{
        Optional<ToolCategories> toolCategoriesOptional = toolCategoriesRepository.findToolCategoryById(request.getIdToolCategory());
        if (toolCategoriesOptional.isEmpty()) {
            throw new NotFoundException("Don't exits Medicine Type by id!");
        }
        if (StringUtils.isBlank(request.getName())) {
            throw new ValidateFiledException("Validate data request!");
        }
        if (!toolCategoriesOptional.get().getName().equals(request.getName()) ||
                !toolCategoriesOptional.get().getShortName().equals(request.getShortName())) {
            if (!toolCategoriesRepository.checkExitsToolCategoriesByNameOrShortName(request.getName(),
                    request.getShortName())) {
                throw new ValidateFiledException("Exits  asset category in list categories by name or short name!");
            }
        }
        return toolCategoriesOptional.get();
    }
    @Override
    public void deleteToolCategoryByIdAssetCategory(Integer idToolCategory) throws ValidateFiledException {
        Optional<ToolCategories> toolCategoriesOptional = toolCategoriesRepository.findToolCategoryById(idToolCategory);
        if (toolCategoriesOptional.isEmpty()) {
            throw new NotFoundException("Don't exits Asset category by id by id!");
        }
        toolCategoriesRepository.delete(toolCategoriesOptional.get());
    }
    @Override
    public ToolCategories findToolCategoryDetailsResponse(Integer idToolCategory) {
        Optional<ToolCategories> response =
                toolCategoriesRepository.findToolCategoryById(idToolCategory);
        if (response.isEmpty()) {
            throw new NotFoundException("Don't exits asset category by code!");
        }
        return response.get();
    }
    @Override
    public void updateStatusToolCategory(UpdateStatusToolCategoryRequest request) throws ValidateFiledException {
        Optional<ToolCategories> toolCategoriesOptional =
                toolCategoriesRepository.findToolCategoryById(request.getIdToolCategory());
        if (toolCategoriesOptional.isEmpty()){
            throw new NotFoundException("Don't exits asset category!");
        }
        if(!request.getStatus().equals(Constants.ASSET_CATEGORY_IS_VISIBLE) &&
                !request.getStatus().equals(Constants.ASSET_CATEGORY_UN_VISIBLE)) {
            throw new ValidateFiledException("Validate data!");
        }
        toolCategoriesOptional.get().setVisible(request.getStatus());
        toolCategoriesRepository.save(toolCategoriesOptional.get());
    }
}
