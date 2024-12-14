package com.example.csvccdshustbe.service.assetProcess.impl;

import com.example.csvccdshustbe.dto.asset.FindAllAssetDto;
import com.example.csvccdshustbe.dto.assetProcess.AssetProcessDto;
import com.example.csvccdshustbe.dto.process.FindAllAssetChildrenToInventoryDto;
import com.example.csvccdshustbe.dto.process.FindAllAssetChildrenToUpdateInventoryDto;
import com.example.csvccdshustbe.dto.process.FindAllAssetParentToInventoryDto;
import com.example.csvccdshustbe.dto.process.FindAllAssetParentToUpdateInventoryDto;
import com.example.csvccdshustbe.entity.AssetProcess;
import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.repository.assetProcess.AssetProcessRepository;
import com.example.csvccdshustbe.request.assetProcess.AssetProcessRequest;
import com.example.csvccdshustbe.request.assetProcess.FindAllAssetProcessRequest;
import com.example.csvccdshustbe.request.assetProcess.UpdateAllAssetProcessRequest;
import com.example.csvccdshustbe.response.asset.FindAllAssetChildrenToInventoryResponse;
import com.example.csvccdshustbe.response.asset.FindAllAssetChildrenToUpdateInventoryResponse;
import com.example.csvccdshustbe.response.asset.FindAllAssetResponseToInventory;
import com.example.csvccdshustbe.response.asset.FindAllAssetResponseUpdateInventory;
import com.example.csvccdshustbe.response.assetProcess.FindAllAssetProcessResponse;
import com.example.csvccdshustbe.service.assetProcess.AssetProcessService;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.DateUtil;
import com.example.csvccdshustbe.utility.PageUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.sl.draw.geom.GuideIf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.webjars.NotFoundException;

import java.util.*;

@Service
public class AssetProcessServiceImpl implements AssetProcessService {

    @Autowired
    AssetProcessRepository assetProcessRepository;

    @Override
    public List<AssetProcess> saveListAssetProcess(List<AssetProcess> assetProcessList) {
        return assetProcessRepository.saveAll(assetProcessList);
    }

    @Override
    public Page<FindAllAssetProcessResponse> findAllAssetProcess(FindAllAssetProcessRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        List<Integer> idsDepartment = ((CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getIdsDepartmentCurrent();
        request.setIdsDepartmentOriginal(idsDepartment);
        Page<FindAllAssetDto> findAllAssetDtos  = assetProcessRepository.findAllAssetProcess(request, pageable);
        return new PageImpl<>(convertToFindAllAssetProcess(findAllAssetDtos.getContent()),
                pageable, findAllAssetDtos.getTotalElements());
    }

    @Override
    public AssetProcess findAssetProcessByIdProcess(Integer idProcess) {
        Optional<AssetProcess> assetProcess = assetProcessRepository.findAssetProcessByIdProcess(idProcess);
        if (assetProcess.isEmpty()){
            throw new NotFoundException("Don't exits asset process!");
        }
        return assetProcess.get();
    }

    @Override
    public List<AssetProcessDto> findAllAssetProcessByIdProcess(Integer idProcess) {
        return assetProcessRepository.findAssetProcessDtoByIdProcess(idProcess);
    }

    @Override
    public void updateListAssetProcessByIdProcess(UpdateAllAssetProcessRequest request, Integer idProcess) {
        List<Integer> idsAsset = getIdsAssetFromUpdateAllAssetProcessRequest(request);
        List<AssetProcess> assetProcessList =
                assetProcessRepository.findAssetProcessListByIdsAssetAndIdProcess(idsAsset, idProcess);
        if (assetProcessList.size() != idsAsset.size()){
            throw new NotFoundException("Don't exist asset in process!");
        }
        updateChangeAssetProcess(assetProcessList, request);
    }

    @Override
    public void updateFinishListAssetProcessByIdProcess(UpdateAllAssetProcessRequest request) {
        List<AssetProcess> assetProcessList =
                assetProcessRepository.findAllAssetProcessListByIdProcess(request.getIdProcess());
        if (assetProcessList.size() != request.getAssets().size()){
            throw new NotFoundException("Don't exist asset in process!");
        }
        updateChangeAssetProcess(assetProcessList, request);
    }

    @Override
    public List<AssetProcessDto> findResultAssetLotByIdProcessAndCalculatorIsIncreaseAndIsDecrease(Integer idProcess) {
        return assetProcessRepository.findResultAssetLotByIdProcessAndCalculatorIsIncreaseAndIsDecrease(idProcess);
    }

    @Override
    public Page<FindAllAssetProcessResponse> findAllAssetLotProcess(FindAllAssetProcessRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        List<Integer> idsDepartment = ((CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getIdsDepartmentCurrent();
        request.setIdsDepartmentOriginal(idsDepartment);
        Page<FindAllAssetDto> findAllAssetDtos  = assetProcessRepository.findAllAssetLotProcess(request, pageable);
        return new PageImpl<>(convertToFindAllAssetLotProcess(findAllAssetDtos.getContent()),
                pageable, findAllAssetDtos.getTotalElements());
    }

    @Override
    public Page<FindAllAssetProcessResponse> findAllAssetChildrenProcess(FindAllAssetProcessRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        List<Integer> idsDepartment = ((CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getIdsDepartmentCurrent();
        request.setIdsDepartmentOriginal(idsDepartment);
        Page<FindAllAssetDto> findAllAssetDtos  = assetProcessRepository.findAllAssetChildrenProcess(request, pageable);
        return new PageImpl<>(convertToFindAllAssetLotProcess(findAllAssetDtos.getContent()),
                pageable, findAllAssetDtos.getTotalElements());
    }

    @Override
    public Page<FindAllAssetResponseUpdateInventory> findAllAssetUpdateInventoryProcess(FindAllAssetProcessRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        List<Integer> idsDepartment = ((CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getIdsDepartmentCurrent();
        idsDepartment.add(Constants.DEFAULT_ASSET_CATEGORY);
        request.setIdsDepartmentOriginal(idsDepartment);
        Page<FindAllAssetParentToUpdateInventoryDto> responses = assetProcessRepository.findALlAssetProcessToUpdateInventory(request, pageable);
        return new PageImpl<>(convertToFindAllAssetProcessToUpdateInventoryResponse(responses.getContent()),
                pageable,
                responses.getTotalElements());
    }

    @Override
    public Page<FindAllAssetResponseUpdateInventory> findAllAssetProcessLotUpdateInventoryProcess(FindAllAssetProcessRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        List<Integer> idsDepartment = ((CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getIdsDepartmentCurrent();
        idsDepartment.add(Constants.DEFAULT_ASSET_CATEGORY);
        request.setIdsDepartmentOriginal(idsDepartment);
        Page<FindAllAssetParentToUpdateInventoryDto> responses = assetProcessRepository.findALlAssetProcessLotToUpdateInventory(request, pageable);
        return new PageImpl<>(convertToFindAllAssetProcessLotToUpdateInventoryResponse(responses.getContent()), pageable, responses.getTotalElements());
    }

    private List<FindAllAssetResponseUpdateInventory>
    convertToFindAllAssetProcessToUpdateInventoryResponse(List<FindAllAssetParentToUpdateInventoryDto> content) {
        List<FindAllAssetResponseUpdateInventory> response = new ArrayList<>();
        for (FindAllAssetParentToUpdateInventoryDto dto : content){
            FindAllAssetResponseUpdateInventory inventory = new FindAllAssetResponseUpdateInventory();
            inventory.setIdAssetCategory(dto.getIdAssetCategory());
            inventory.setNameAssetCategory(dto.getNameAssetCategory());
            inventory.setDepth(dto.getDepth());
            inventory.setIdParentAssetCategory(dto.getIdParentAssetCategory());
            inventory.setCodeAssetCategory(dto.getCodeAssetCategory());
            inventory.setPath(dto.getPath());
            inventory.setNumberCodePattern(dto.getNumberCodePattern());
            inventory.setIsLeaf(dto.getIsLeaf());
            inventory.setTypeTarget(dto.getTypeTarget());
            if (!CollectionUtils.isEmpty(dto.getAssetLeaves())) {
                List<FindAllAssetChildrenToUpdateInventoryResponse> assetLeaves = new ArrayList<>();
                for (FindAllAssetChildrenToUpdateInventoryDto assetLeaf : dto.getAssetLeaves()) {
                    FindAllAssetChildrenToUpdateInventoryResponse leaf = constructionAssetProcessToUpdateInventoryLeaf(assetLeaf);
                    assetLeaves.add(leaf);
                }
                inventory.setAssetLeaves(assetLeaves);
            }
            response.add(inventory);
        }
        return response;
    }

    private FindAllAssetChildrenToUpdateInventoryResponse
    constructionAssetProcessToUpdateInventoryLeaf(FindAllAssetChildrenToUpdateInventoryDto assetLeaf) {
        FindAllAssetChildrenToUpdateInventoryResponse leaf = new FindAllAssetChildrenToUpdateInventoryResponse();
        leaf.setIdAsset(assetLeaf.getIdAsset());
        leaf.setSalt(assetLeaf.getSalt());
        leaf.setValue(assetLeaf.getValue());
        return leaf;
    }


    private List<FindAllAssetResponseUpdateInventory>
    convertToFindAllAssetProcessLotToUpdateInventoryResponse(List<FindAllAssetParentToUpdateInventoryDto> content) {
        List<FindAllAssetResponseUpdateInventory> response = new ArrayList<>();
        for (FindAllAssetParentToUpdateInventoryDto dto : content){
            FindAllAssetResponseUpdateInventory inventory = new FindAllAssetResponseUpdateInventory();
            inventory.setIdAssetCategory(dto.getIdAssetCategory());
            inventory.setNameAssetCategory(dto.getNameAssetCategory());
            inventory.setDepth(dto.getDepth());
            inventory.setIdParentAssetCategory(dto.getIdParentAssetCategory());
            inventory.setCodeAssetCategory(dto.getCodeAssetCategory());
            inventory.setPath(dto.getPath());
            inventory.setNumberCodePattern(dto.getNumberCodePattern());
            inventory.setIsLeaf(dto.getIsLeaf());
            inventory.setTypeTarget(dto.getTypeTarget());
            if (!CollectionUtils.isEmpty(dto.getAssetLeaves())) {
                List<FindAllAssetChildrenToUpdateInventoryResponse> assetLeaves = new ArrayList<>();
                for (FindAllAssetChildrenToUpdateInventoryDto assetLeaf : dto.getAssetLeaves()) {
                    FindAllAssetChildrenToUpdateInventoryResponse leaf = constructionAssetProcessLotToUpdateInventoryLeaf(assetLeaf);
                    assetLeaves.add(leaf);
                }
                inventory.setAssetLeaves(assetLeaves);
            }
            response.add(inventory);
        }
        return response;
    }

    private FindAllAssetChildrenToUpdateInventoryResponse
    constructionAssetProcessLotToUpdateInventoryLeaf(FindAllAssetChildrenToUpdateInventoryDto assetLeaf) {
        FindAllAssetChildrenToUpdateInventoryResponse leaf = new FindAllAssetChildrenToUpdateInventoryResponse();
        leaf.setIdAsset(assetLeaf.getIdAsset());
        leaf.setSalt(assetLeaf.getSalt());
        leaf.setValue(assetLeaf.getValue());
        return leaf;
    }


    private void updateChangeAssetProcess(List<AssetProcess> assetProcessList, UpdateAllAssetProcessRequest request) {
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String timeCurrent = String.valueOf(new Date().getTime());
        for (AssetProcessRequest assetProcessRequest : request.getAssets()){
            assetProcessList.stream().
                    filter(x->x.getIdAsset().equals(assetProcessRequest.getIdAsset()))
                    .findFirst()
                    .ifPresent(x->{
                        x.setValue(assetProcessRequest.getValue());
                        x.setIdUserModified(csvcUser.getIdUser());
                        x.setTimeModified(timeCurrent);
                    });
        }
        assetProcessRepository.saveAll(assetProcessList);
    }

    private List<Integer> getIdsAssetFromUpdateAllAssetProcessRequest(UpdateAllAssetProcessRequest request) {
        List<Integer> idsAsset = new ArrayList<>();
        request.getAssets().forEach(x->idsAsset.add(x.getIdAsset()));
        return idsAsset;
    }

    private List<FindAllAssetProcessResponse> convertToFindAllAssetProcess(List<FindAllAssetDto> content) {
        List<FindAllAssetProcessResponse> responses = new ArrayList<>();
        for (FindAllAssetDto dto : content) {
            FindAllAssetProcessResponse response = new FindAllAssetProcessResponse();
            response.setCodeAsset(dto.getCodeAsset());
            response.setNameAsset(dto.getNameAsset());
            response.setNameAssetCategory(dto.getNameAssetCategory());
            response.setCodeAssetCategory(dto.getCodeAssetCategory());
            response.setCodeDepartment(dto.getCodeDepartment());
            response.setNameDepartment(dto.getNameDepartment());
            response.setTimeCreated(DateUtil.formatToPattern(new Date(dto.getTimeCreated()), DateUtil.DATE_FORMAT));
            response.setTimeModified(DateUtil.formatToPattern( new Date(dto.getTimeModified()),DateUtil.DATE_FORMAT));
            response.setIdAsset(dto.getIdAsset());
            response.setSalt(dto.getSalt());
            response.setValue(dto.getValue());
            response.setQuantity(dto.getQuantity());
            responses.add(response);
        }
        return responses;
    }

    private List<FindAllAssetProcessResponse> convertToFindAllAssetLotProcess(List<FindAllAssetDto> content) {
        List<FindAllAssetProcessResponse> responses = new ArrayList<>();
        for (FindAllAssetDto dto : content) {
            FindAllAssetProcessResponse response = new FindAllAssetProcessResponse();
            response.setCodeAsset(dto.getCodeAsset());
            response.setNameAsset(dto.getNameAsset());
            response.setNameAssetCategory(dto.getNameAssetCategory());
            response.setCodeAssetCategory(dto.getCodeAssetCategory());
            response.setCodeDepartment(dto.getCodeDepartment());
            response.setNameDepartment(dto.getNameDepartment());
            response.setTimeCreated(DateUtil.formatToPattern(new Date(dto.getTimeCreated()), DateUtil.DATE_FORMAT));
            response.setTimeModified(DateUtil.formatToPattern( new Date(dto.getTimeModified()),DateUtil.DATE_FORMAT));
            response.setIdAsset(dto.getIdAsset());
            response.setSalt(dto.getSalt());
            response.setValue(dto.getValue());
            responses.add(response);
        }
        return responses;
    }


}
