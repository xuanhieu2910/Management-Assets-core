package com.example.csvccdshustbe.service.assetProcess.impl;

import com.example.csvccdshustbe.dto.asset.FindAllAssetDto;
import com.example.csvccdshustbe.dto.assetProcess.AssetProcessDto;
import com.example.csvccdshustbe.dto.fluctuatingSituationAsset.AssetsFluctuatingSituationAssetDto;
import com.example.csvccdshustbe.dto.process.*;
import com.example.csvccdshustbe.entity.Asset;
import com.example.csvccdshustbe.entity.AssetProcess;
import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.entity.Process;
import com.example.csvccdshustbe.repository.assetProcess.AssetProcessRepository;
import com.example.csvccdshustbe.request.assetProcess.*;
import com.example.csvccdshustbe.response.asset.FindAllAssetChildrenToInventoryResponse;
import com.example.csvccdshustbe.response.asset.FindAllAssetChildrenToUpdateInventoryResponse;
import com.example.csvccdshustbe.response.asset.FindAllAssetResponseToInventory;
import com.example.csvccdshustbe.response.asset.FindAllAssetResponseUpdateInventory;
import com.example.csvccdshustbe.response.assetProcess.FindAllAssetProcessResponse;
import com.example.csvccdshustbe.service.asset.AssetService;
import com.example.csvccdshustbe.service.assetProcess.AssetProcessService;
import com.example.csvccdshustbe.service.process.ProcessService;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.DateUtil;
import com.example.csvccdshustbe.utility.PageUtils;
import com.example.csvccdshustbe.utility.ValueUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.sl.draw.geom.GuideIf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
    @Lazy
    @Autowired
    ProcessService processService;

    @Lazy
    @Autowired
    AssetService assetService;


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
        Page<FindAllAssetLotParentToUpdateInventoryDto> responses = assetProcessRepository.findALlAssetProcessLotToUpdateInventory(request, pageable);
        return new PageImpl<>(convertToFindAllAssetProcessLotToUpdateInventoryResponse(responses.getContent()), pageable, responses.getTotalElements());
    }

    @Transactional
    @Override
    public List<AssetProcess> createNewAssetNotDeclareWhenInventory(AssetNotDeclareWhenInventoryRequest request) throws JsonProcessingException {
        Process process = processService.findProcessByIdProcess(request.getIdProcess());
        List<AssetProcess> assetProcessList = new ArrayList<>();
        for (AssetProcessNotDeclareInventoryRequest assetProcess: request.getListAssetDeclare()){
            Asset asset = constructionAssetNotDeclareWhenInventory(assetProcess);
            assetProcessList.add(constructionAssetProcessDeclareWhenInventory(process,asset,assetProcess));
        }
        return assetProcessRepository.saveAll(assetProcessList);
    }

    @Override
    public AssetProcess updateAssetProcessInventory(UpdateAssetProcessRequest request) throws JsonProcessingException {
        AssetProcess assetProcess = findAssetProcessByIdAssetProcess(request.getIdAssetProcess());
        updateInformationAsset(request.getValue(), assetProcess.getIdAsset());
        return updateInformationAssetProcess(request, assetProcess);
    }

    private void updateInformationAsset(String value, Integer idAsset) throws JsonProcessingException {
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Asset asset = assetService.findAssetByIdAsset(idAsset);
        HashMap<String, Object> informationAsset = (new ObjectMapper()).readValue(value, new TypeReference<>() {});
        asset.setName(ValueUtil.getStringByObject(informationAsset.get("name_asset")));
        asset.setIdAssetCategory(ValueUtil.getIntegerByObject(informationAsset.get("id_asset_category")));
        asset.setIdDepartmentOrigin(csvcUser.getIdDepartmentCurrent());
        asset.setQuantity(Constants.QUANTITY_DEFAULT);
        asset.setTimeModified(String.valueOf(new Date().getTime()));
        asset.setIdUserModified(csvcUser.getIdUser());
        asset.setStatusUse(ValueUtil.getIntegerByObject(informationAsset.get("status_use")));
        asset.setYearUse(ValueUtil.getStringByObject(informationAsset.get("year_use")));
        asset.setIdUnit(ValueUtil.getIntegerByObject(informationAsset.get("id_unit")));
        assetService.storeAsset(asset);
    }

    private AssetProcess updateInformationAssetProcess(UpdateAssetProcessRequest request, AssetProcess assetProcess) {
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        assetProcess.setValue(request.getValue());
        assetProcess.setTimeModified(String.valueOf(new Date().getTime()));
        assetProcess.setIdUserModified(csvcUser.getIdUser());
        return assetProcessRepository.save(assetProcess);
    }

    @Override
    public AssetProcess findAssetProcessByIdAssetProcess(Integer idAssetProcess) {
        Optional<AssetProcess> assetProcess = assetProcessRepository.findAssetProcessByIdAssetProcess(idAssetProcess);
        if (assetProcess.isEmpty()){
            throw new NotFoundException("Don't exits asset process by id!");
        }
        return assetProcess.get();
    }

    @Override
    public List<AssetsFluctuatingSituationAssetDto> findAssetsToFluctuatingSituationByIdProcess(Integer idProcess) {
        return assetProcessRepository.findAssetsToFluctuatingSituationByIdProcess(idProcess);
    }

    private Asset constructionAssetNotDeclareWhenInventory(AssetProcessNotDeclareInventoryRequest assetProcess) throws JsonProcessingException {
        String currentTime = String.valueOf(new Date().getTime());
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HashMap<String, Object> informationAsset = (new ObjectMapper()).readValue(assetProcess.getValue(), new TypeReference<>() {});
        Asset asset = new Asset();
        asset.setName(ValueUtil.getStringByObject(informationAsset.get("name_asset")));
        asset.setIdAssetCategory(ValueUtil.getIntegerByObject(informationAsset.get("id_asset_category")));
        asset.setIdDepartmentOrigin(csvcUser.getIdDepartmentCurrent());
        asset.setQuantity(Constants.QUANTITY_DEFAULT);
        asset.setIdUserCreated(csvcUser.getIdUser());
        asset.setIdUserModified(csvcUser.getIdUser());
        asset.setTimeCreated(currentTime);
        asset.setTimeModified(currentTime);
        asset.setCodeAsset(ValueUtil.getStringByObject(informationAsset.get("code_asset")));
        asset.setIdInstance(ValueUtil.getIntegerByObject(informationAsset.get("id_instance")));
        asset.setStatusUse(ValueUtil.getIntegerByObject(informationAsset.get("status_use")));
        asset.setYearUse(ValueUtil.getStringByObject(informationAsset.get("year_use")));
        asset.setIdUnit(ValueUtil.getIntegerByObject(informationAsset.get("id_unit")));
        asset.setSalt(String.valueOf(UUID.randomUUID()));
        return assetService.storeAsset(asset);
    }

    private AssetProcess constructionAssetProcessDeclareWhenInventory(Process process, Asset asset,
                                                                      AssetProcessNotDeclareInventoryRequest assetProcessRequest) {
        String timeCurrent = String.valueOf(new Date().getTime());
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AssetProcess assetProcess = new AssetProcess();
        assetProcess.setIdProcess(process.getIdProcess());
        assetProcess.setIdTypeProcess(process.getIdTypeProcess());
        assetProcess.setStatus(Constants.TYPE_FLUCTUATING_SITUATION_ASSET_DECLARE);
        assetProcess.setValue(assetProcessRequest.getValue());
        assetProcess.setTimeCreated(timeCurrent);
        assetProcess.setTimeModified(timeCurrent);
        assetProcess.setIdUserCreated(csvcUser.getIdUser());
        assetProcess.setIdUserModified(csvcUser.getIdUser());
        assetProcess.setIdAsset(asset.getIdAsset());
        return assetProcess;
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
        leaf.setStatus(assetLeaf.getStatus());
        leaf.setIsIncrease(assetLeaf.getIsCrease());
        return leaf;
    }


    private List<FindAllAssetResponseUpdateInventory>
    convertToFindAllAssetProcessLotToUpdateInventoryResponse(List<FindAllAssetLotParentToUpdateInventoryDto> content) {
        List<FindAllAssetResponseUpdateInventory> response = new ArrayList<>();
        for (FindAllAssetLotParentToUpdateInventoryDto dto : content){
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
                for (FindAllAssetLotChildrenToUpdateInventoryDto assetLeaf : dto.getAssetLeaves()) {
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
    constructionAssetProcessLotToUpdateInventoryLeaf(FindAllAssetLotChildrenToUpdateInventoryDto assetLeaf) {
        FindAllAssetChildrenToUpdateInventoryResponse leaf = new FindAllAssetChildrenToUpdateInventoryResponse();
        leaf.setIdAsset(assetLeaf.getIdAsset());
        leaf.setSalt(assetLeaf.getSalt());
        leaf.setNameAsset(assetLeaf.getNameAsset());
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
                        x.setStatus(assetProcessRequest.getStatus());
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
            response.setIdAssetProcess(dto.getIdAssetProcess());
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
            response.setIdAssetProcess(dto.getIdAssetProcess());
            response.setStatus(dto.getStatusAssetProcess());
            response.setIsIncrease(dto.getIsIncrease());
            responses.add(response);
        }
        return responses;
    }


}
