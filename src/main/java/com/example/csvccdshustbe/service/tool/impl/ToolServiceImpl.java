package com.example.csvccdshustbe.service.tool.impl;

import com.example.csvccdshustbe.dto.documentAttack.FindAllDocumentAttackDto;
import com.example.csvccdshustbe.dto.location.FindAllLocationDto;
import com.example.csvccdshustbe.dto.modules.medicineModules.medicineGroup.MedicineGroupDetailsDto;
import com.example.csvccdshustbe.dto.modules.medicineModules.medicineType.MedicineTypeDetailsDto;
import com.example.csvccdshustbe.dto.originalOfFormationTool.FindAllOriginalOfFormationToolDto;
import com.example.csvccdshustbe.dto.originalTool.FindAllOriginalToolDto;
import com.example.csvccdshustbe.dto.projects.FindAllProjectsDto;
import com.example.csvccdshustbe.dto.suppliers.FindAllSuppliersDto;
import com.example.csvccdshustbe.dto.tool.FindDetailsToolDto;
import com.example.csvccdshustbe.dto.tool.ToolDto;
import com.example.csvccdshustbe.dto.tool.ToolImportDto;
import com.example.csvccdshustbe.dto.toolCategories.FindAllToolCategoryDto;
import com.example.csvccdshustbe.dto.user.FindAllUserUsedDto;
import com.example.csvccdshustbe.entity.*;
import com.example.csvccdshustbe.entity.Process;
import com.example.csvccdshustbe.exception.FileExcelException;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.repository.tool.ToolRepository;
import com.example.csvccdshustbe.request.tool.*;
import com.example.csvccdshustbe.response.tool.*;
import com.example.csvccdshustbe.service.department.DepartmentService;
import com.example.csvccdshustbe.service.documentAttack.DocumentAttackService;
import com.example.csvccdshustbe.service.medicineGroup.MedicineGroupService;
import com.example.csvccdshustbe.service.medicineType.MedicineTypeService;
import com.example.csvccdshustbe.service.originalOfFormationTool.OriginalOfFormationToolService;
import com.example.csvccdshustbe.service.originalTool.OriginalToolService;
import com.example.csvccdshustbe.service.projects.ProjectsService;
import com.example.csvccdshustbe.service.suppliers.SuppliersService;
import com.example.csvccdshustbe.service.tool.ToolService;
import com.example.csvccdshustbe.service.toolCategories.ToolCategoriesService;
import com.example.csvccdshustbe.service.unitsTool.UnitsToolService;
import com.example.csvccdshustbe.service.upload.FilesStorageService;
import com.example.csvccdshustbe.service.upload.impl.FileUploadService;
import com.example.csvccdshustbe.service.user.CsvcUserService;
import com.example.csvccdshustbe.utility.*;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.util.*;

@Service
public class ToolServiceImpl implements ToolService {

    @Autowired
    ToolRepository toolRepository;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    ToolCategoriesService toolCategoriesService;
    @Autowired
    CsvcUserService csvcUserService;
    @Autowired
    FilesStorageService filesStorageService;
    @Autowired
    SuppliersService suppliersService;
    @Autowired
    DocumentAttackService documentAttackService;
    @Autowired
    ProjectsService projectsService;
    @Autowired
    OriginalToolService originalToolService;
    @Autowired
    OriginalOfFormationToolService originalOfFormationToolService;
    @Autowired
    UnitsToolService unitsToolService;
    @Autowired
    MedicineTypeService medicineTypeService;
    @Autowired
    MedicineGroupService medicineGroupService;


    @Override
    public Page<FindAllToolResponse> findAllToolParentResponse(FindAllToolRequest request) {
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        request.setIdsDepartmentOriginal(csvcUser.getIdsDepartmentCurrent());
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        Page<ToolDto> toolParentDtos = toolRepository.findAllToolParentDto(request, pageable);
        return new PageImpl<>(convertToFindAllToolResponse(toolParentDtos), pageable, toolParentDtos.getTotalElements());
    }

    @Override
    public Page<FindAllToolResponse> findAllToolChildrenResponse(FindAllToolRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        Page<ToolDto> toolChildrenDtos = toolRepository.findAllChildrenToolDto(request, pageable);
        return new PageImpl<>(convertToFindAllToolResponse(toolChildrenDtos), pageable, toolChildrenDtos.getTotalElements());
    }

    @Override
    public FindDetailsToolResponse findDetailsToolBySaltTool(String saltTool) throws ValidateFiledException, IllegalAccessException {
        Optional<FindDetailsToolDto> findDetailsToolDto=toolRepository.findDetailToolBySalt(saltTool);
        if (findDetailsToolDto.isEmpty()){
            throw new NotFoundException("Don't exist Tool by salt!");
        }
        setAllocateToolDetail(findDetailsToolDto.get());
        return convertToFindDetailsToolResponse(findDetailsToolDto.get());
    }

    private void setAllocateToolDetail(FindDetailsToolDto findDetailsToolDto) {
        findDetailsToolDto.setAllocateToolDto(toolRepository.findListAllocateToolByIdToolParent(findDetailsToolDto.getIdTool()));
    }

    private FindDetailsToolResponse convertToFindDetailsToolResponse(FindDetailsToolDto findDetailsToolDto) {
        FindDetailsToolResponse findDetailsToolResponse = new FindDetailsToolResponse();
        findDetailsToolResponse.setName(findDetailsToolDto.getNameTool());
        findDetailsToolResponse.setCodeTool(findDetailsToolDto.getCodeTool());
        findDetailsToolResponse.setCodeToolCategory(findDetailsToolDto.getCodeToolCategory());
        findDetailsToolResponse.setIdToolCategory(findDetailsToolDto.getIdToolCategory());
        findDetailsToolResponse.setNameToolCategory(findDetailsToolDto.getNameToolCategory());
        findDetailsToolResponse.setIdParent(findDetailsToolDto.getIdParent());
        findDetailsToolResponse.setQuantity(findDetailsToolDto.getQuantity());
        findDetailsToolResponse.setValue(findDetailsToolDto.getValue());
        findDetailsToolResponse.setIsIncrease(findDetailsToolDto.getIsIncrease());
        findDetailsToolResponse.setIsDecrease(findDetailsToolDto.getIsDecrease());
        findDetailsToolResponse.setStatusUse(findDetailsToolDto.getStatusUse());
        findDetailsToolResponse.setIdUserUse(findDetailsToolDto.getIdUserUse());
        findDetailsToolResponse.setNameUserUse(findDetailsToolDto.getNameUserUse());
        findDetailsToolResponse.setYearUse(findDetailsToolDto.getYearUse());
        findDetailsToolResponse.setAllowcateToolDtoList(findDetailsToolDto.getAllocateToolDto());
        return findDetailsToolResponse;
    }

    @Override
    public StatisticToolsResponse getStatisticTool() {
        return toolRepository.getStatisticTool();
    }

    @Override
    public Integer countToolIncreasedNotDecreased(List<Integer> idsTool) {
        return toolRepository.countToolIncreasedNotDecreasedByIdsTool(idsTool);
    }

    @Override
    public List<Tool> findAllToolByIdsTool(List<Integer> idsTool) {
        return toolRepository.findAllToolByIdsTool(idsTool);
    }

    @Override
    public void updateToolStatusProcessCurrentAndIsIncreaseAndIsDecrease(Integer idProcess, Integer status, String code) {
        if (code.equals(Constants.CODE_TYPE_PROCESS_INCREASE_TOOL)) {
            toolRepository.updateStatusProcessCurrentAndIsIncrease(idProcess, status);
        } else if (code.equals(Constants.CODE_TYPE_PROCESS_DECREASE_TOOL)) {
            toolRepository.updateStatusProcessCurrentAndIsDecrease(idProcess, status);
        }
    }

    @Override
    public void updateToolParentIsIncreaseAndIsDecrease(Integer idProcess, String code) {
        List<Integer> idsToolParent = toolRepository.getAllIdsToolParentByIdProcess(idProcess);
        if (code.equals(Constants.CODE_TYPE_PROCESS_INCREASE_TOOL)) {
            toolRepository.updateIsIncreaseAndQuantityIncreaseCurrentByIdsTool(idsToolParent);
        } else if (code.equals(Constants.CODE_TYPE_PROCESS_DECREASE_TOOL)) {
            toolRepository.updateIsDecreaseAndQuantityDecreaseCurrentByIdsTool(idsToolParent);
        }
    }

    @Override
    public void updateToolStatusProcessCurrentByIdProcessCurrentWhenNotApproved(Integer idProcessCurrent,
                                                                             Integer status, String codeTypeProcess) {
        switch (codeTypeProcess){
            case Constants.CODE_TYPE_PROCESS_INCREASE_TOOL -> {
                toolRepository.updateToolIsIncreaseWhenNotApproved(idProcessCurrent, status);
            }
            case Constants.CODE_TYPE_PROCESS_DECREASE_TOOL -> {
                toolRepository.updateToolIsDecreaseWhenNotApproved(idProcessCurrent, status);
            }
            case Constants.CODE_TYPE_PROCESS_DOCUMENT_INVENTORY_TOOL -> {
                toolRepository.updateToolInventoryWhenNotApproved(idProcessCurrent, status);
            }
            default -> {return;}
        }

    }

    @Override
    public Integer countToolIsNotIncreaseOrIsDecreaseOrPendingByIdsTool(List<Integer> idsTool) {
        return toolRepository.countToolIsNotIncreaseOrDecreaseOrPendingByIdsTool(idsTool);
    }

    @Override
    public Page<FindAllToolToInventoryResponse>
    findAllToolToInventory(FindAllToolToInventoryRequest findAllToolRequest) {
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        findAllToolRequest.setIdsDepartment(csvcUser.getIdsDepartmentCurrent());
        Pageable pageable = PageUtils.buildPage(findAllToolRequest.getPage(), findAllToolRequest.getSize());
        Page<ToolDto> toolDtos = toolRepository.findAllToolDtoToInventory(findAllToolRequest, pageable);
        return new PageImpl<>(convertToFindAllToolToInventoryResponse(toolDtos.getContent()),
                pageable, toolDtos.getTotalElements());
    }

    @Override
    public void updateToolStatusProcessCurrentByIdProcessCurrent(Integer idProcess, Integer status) {
        toolRepository.updateToolStatusProcessCurrentByIdProcessCurrent(idProcess, status);
    }

    @Override
    public void updateInformationProcessCurrentTool(List<Integer> idsTool, Process process) {
        List<Tool> tools = toolRepository.findAllToolByIdsTool(idsTool);
        if (tools.size() != idsTool.size()){
            throw new NotFoundException("Don't exits tools by ids!");
        }
        tools.forEach(x->{
            x.setIdProcessCurrent(process.getIdProcess());
            x.setStatusProcessCurrent(process.getStatus());
            x.setIdTypeProcessCurrent(process.getIdTypeProcess());
        });
        toolRepository.saveAll(tools);
    }

    @Override
    public String downloadFileTemplateImportTool() throws IOException {
        return filesStorageService.downloadFileImportTool();
    }

    @Override
    public StatisticToolsFindAllResponse getStatisticFindAllTool() {
        return toolRepository.getStatisticFindAllTool();
    }

    @Override
    public void uploadFileImportTool(MultipartFile file) throws FileExcelException {
        ValidateExcelUtils.checkFileExcel(file);
        List<Map<String, Object>> dataTool = handleUploadFileTool(file);
    }

    private List<Map<String, Object>> handleUploadFileTool(MultipartFile file) {
        List<ToolImportDto> dataImportExcel = handleDataImportToolFromFile(file);

        return null;
    }

    private List<ToolImportDto> handleDataImportToolFromFile(MultipartFile file) {
        List<FindAllToolCategoryDto> toolCategoryToSelected =
                toolCategoriesService.findAllToolCategoriesToDownloadAndSelected();
        List<FindAllToolCategoryDto> toolCategoryToView =
                toolCategoriesService.findAllToolCategoriesToDownloadAndView();
        List<FindAllSuppliersDto> suppliers =
                suppliersService.findAllSuppliersByIdsDepartmentOriginal();
        List<FindAllDocumentAttackDto> documentAttack =
                documentAttackService.findAllDocumentAttackByIdsDepartmentOriginal();
        List<FindAllProjectsDto> projects =
                projectsService.findAllProjectToDownload();
        List<FindAllOriginalToolDto> originalTool =
                originalToolService.findAllOriginalToolByVisible(Constants.ORIGINAL_VISIBLE);
        List<FindAllOriginalOfFormationToolDto> originalOfFormationTool =
                originalOfFormationToolService.findAllOriginalOfFormationDtoByVisible(Constants.ORIGINAL_OF_FORMATION_VISIBLE);
        List<UnitsTool> unitsTools = unitsToolService.findAllUnitsToolByStatus(Constants.UNITS_IS_ACTIVE);
        Map<String,List<FindAllLocationDto>> dataDepartment =
                departmentService.findAllDepartmentLocationVisibleToDownload();
        Map<String, List<FindAllUserUsedDto>> dataUserUsed =
                csvcUserService.findAllUserUsedToDownload();
        List<MedicineTypeDetailsDto> dataMedicineType =
                medicineTypeService.findAllMedicineTypeToDownload();
        List<MedicineGroupDetailsDto> dataMedicineGroup =
                medicineGroupService.findAllMedicineGroupToDownload();
        int maxRowData = 2000;
        int indexRowStartToReadData = 3;
        int amountCheckRowHasData = 10;
        List<ToolImportDto> dataToolsImport = new ArrayList<>();
        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet xssfSheet = xssfWorkbook.getSheet(FileUploadService.NAME_SHEET_DATA_TOOL_CATEGORY);
            int totalRow = xssfSheet.getLastRowNum();
            if (totalRow > maxRowData + indexRowStartToReadData) {
                totalRow = maxRowData;
            }
            for (int i = indexRowStartToReadData; i < totalRow ; i++){
                XSSFRow row = xssfSheet.getRow(i);
                ToolImportDto toolImportDto = new ToolImportDto();
                List<String> errors = new ArrayList<>();
                if (row != null && hasDataInRow(row, 1)) {
                    if (ExcelUtil.convertValue(row.getCell(0), CellType.STRING) == null) {
                        errors.add("Không được để trống loại công cụ dụng cụ!");
                    } else {
                        toolImportDto.setIdToolCategory(Integer.valueOf(((String) ExcelUtil.convertValue(row.getCell(0), CellType.STRING)).split("_")[0]));
                        toolImportDto.setNameToolCategory(((String) ExcelUtil.convertValue(row.getCell(0), CellType.STRING)).split("_")[1]);
                    }
                    if (ExcelUtil.convertValue(row.getCell(1), CellType.STRING) == null) {
                        errors.add("Không được để trống tên công cụ dụng cụ!");
                    } else {
                        toolImportDto.setNameTool((String)ExcelUtil.convertValue(row.getCell(1), CellType.STRING));
                    }
                    if (ExcelUtil.convertValue(row.getCell(2), CellType.STRING) != null) {
                        toolImportDto.setIdSupply(Integer.valueOf(((String) ExcelUtil.convertValue(row.getCell(2), CellType.STRING)).split("_")[0]));
                        toolImportDto.setNameSupply(((String) ExcelUtil.convertValue(row.getCell(2), CellType.STRING)).split("_")[1]);
                    }
                    if (ExcelUtil.convertValue(row.getCell(3), CellType.STRING) != null) {
                        toolImportDto.setIdDocumentAttach(Integer.valueOf(((String) ExcelUtil.convertValue(row.getCell(3), CellType.STRING)).split("_")[0]));
                        toolImportDto.setNameDocumentAttach(((String) ExcelUtil.convertValue(row.getCell(3), CellType.STRING)).split("_")[1]);
                    }
                    if (ExcelUtil.convertValue(row.getCell(4), CellType.STRING) != null) {
                        toolImportDto.setIdProject(Integer.valueOf(((String) ExcelUtil.convertValue(row.getCell(4), CellType.STRING)).split("_")[0]));
                        toolImportDto.setNameProject(((String) ExcelUtil.convertValue(row.getCell(4), CellType.STRING)).split("_")[1]);
                    }
                    if (ExcelUtil.convertValue(row.getCell(5), CellType.STRING) == null) {
                        errors.add("Không được để trống năm đưa vào sử dụng");
                    } else {
                        toolImportDto.setYearUsed((String)ExcelUtil.convertValue(row.getCell(5), CellType.STRING));
                    }
                    if (ExcelUtil.convertValue(row.getCell(6), CellType.STRING) == null) {
                        errors.add("Không được để trống lý do tăng");
                    } else {
                        toolImportDto.setIdOriginal(Integer.valueOf(((String) ExcelUtil.convertValue(row.getCell(6), CellType.STRING)).split("_")[0]));
                        toolImportDto.setNameOriginal(((String) ExcelUtil.convertValue(row.getCell(6), CellType.STRING)).split("_")[1]);
                    }
                    if (ExcelUtil.convertValue(row.getCell(7), CellType.STRING) == null) {
                        errors.add("Không được để trống nguồn hình thành");
                    } else {
                        toolImportDto.setIdOriginalOfFormation(Integer.valueOf(((String) ExcelUtil.convertValue(row.getCell(7), CellType.STRING)).split("_")[0]));
                        toolImportDto.setNameOriginalOfFormation(((String) ExcelUtil.convertValue(row.getCell(7), CellType.STRING)).split("_")[1]);
                    }
                    if (ExcelUtil.convertValue(row.getCell(8), CellType.STRING) == null) {
                        errors.add("Không được để trống đơn vị tính");
                    } else {
                        toolImportDto.setIdUnit(Integer.valueOf(((String) ExcelUtil.convertValue(row.getCell(8), CellType.STRING)).split("_")[0]));
                        toolImportDto.setNameUnit(((String) ExcelUtil.convertValue(row.getCell(8), CellType.STRING)).split("_")[1]);
                    }
                    if (ExcelUtil.convertValue(row.getCell(9), CellType.STRING) == null) {
                        errors.add("Không được để trống đơn giá");
                    } else {
                        toolImportDto.setPrice((String)ExcelUtil.convertValue(row.getCell(9), CellType.STRING));
                    }
                    if (ExcelUtil.convertValue(row.getCell(10), CellType.STRING) == null) {
                        errors.add("Không được để trống đơn vị sử dụng");
                    } else {
                        toolImportDto.setIdDepartment(Integer.valueOf(((String) ExcelUtil.convertValue(row.getCell(10), CellType.STRING)).replace("STT","").split("_")[0]));
                        toolImportDto.setNameDepartment(((String) ExcelUtil.convertValue(row.getCell(10), CellType.STRING)).replace("STT","").split("_")[1]);
                    }
                    if (ExcelUtil.convertValue(row.getCell(11), CellType.STRING) == null){
                        errors.add("Không được để trống địa điểm sử dụng");
                    } else {
                        toolImportDto.setIdLocation(Integer.valueOf(((String) ExcelUtil.convertValue(row.getCell(11), CellType.STRING)).split("_")[0]));
                        toolImportDto.setNameDepartment(((String) ExcelUtil.convertValue(row.getCell(11), CellType.STRING)).split("_")[1]);
                    }
                    if (ExcelUtil.convertValue(row.getCell(12), CellType.STRING) == null){
                        errors.add("Không được để trống người sử dụng");
                    } else {
                        toolImportDto.setUserName(((String) ExcelUtil.convertValue(row.getCell(12), CellType.STRING)).split("\\(")[1]);
                    }
                    if (ExcelUtil.convertValue(row.getCell(13), CellType.STRING) == null){
                        errors.add("Không được để trống người tình trạng của công cụ dụng cụ");
                    } else {
                        toolImportDto.setStatusUse(Integer.valueOf(((String) ExcelUtil.convertValue(row.getCell(13), CellType.STRING))));
                    }
                    if (ExcelUtil.convertValue(row.getCell(14), CellType.STRING) != null) {
                        toolImportDto.setTypeAllocate(Integer.valueOf(((String) ExcelUtil.convertValue(row.getCell(14), CellType.STRING))));
                    }
                    if (ExcelUtil.convertValue(row.getCell(15), CellType.STRING) != null) {
                        toolImportDto.setAmountAllocate(Integer.valueOf(((String) ExcelUtil.convertValue(row.getCell(15), CellType.STRING))));
                    }
                    if (ExcelUtil.convertValue(row.getCell(16), CellType.STRING) != null) {
                        toolImportDto.setAmountAllocated(Integer.valueOf(((String) ExcelUtil.convertValue(row.getCell(16), CellType.STRING))));
                    }
                    if (ExcelUtil.convertValue(row.getCell(17), CellType.STRING) != null) {
                        toolImportDto.setIdMedicineType(Integer.valueOf(((String) ExcelUtil.convertValue(row.getCell(17), CellType.STRING)).split("_")[0]));
                        toolImportDto.setNameMedicineType(((String) ExcelUtil.convertValue(row.getCell(17), CellType.STRING)).split("_")[1]);
                    }
                    if (ExcelUtil.convertValue(row.getCell(18), CellType.STRING) != null) {
                        toolImportDto.setIdMedicineGroup(Integer.valueOf(((String) ExcelUtil.convertValue(row.getCell(18), CellType.STRING)).split("_")[0]));
                        toolImportDto.setNameMedicineGroup(((String) ExcelUtil.convertValue(row.getCell(18), CellType.STRING)).split("_")[1]);
                    }
                    if (ExcelUtil.convertValue(row.getCell(19), CellType.STRING) != null) {
                        toolImportDto.setTimeProduced(((String) ExcelUtil.convertValue(row.getCell(19), CellType.STRING)));
                    }
                    if (ExcelUtil.convertValue(row.getCell(20), CellType.STRING) != null) {
                        toolImportDto.setTimeExpiry(((String) ExcelUtil.convertValue(row.getCell(20), CellType.STRING)));
                    }
                    if (ExcelUtil.convertValue(row.getCell(21), CellType.STRING) != null) {
                        toolImportDto.setNumberUsed(Integer.valueOf(((String) ExcelUtil.convertValue(row.getCell(21), CellType.STRING))));
                    }
                    if (ExcelUtil.convertValue(row.getCell(22), CellType.STRING) != null) {
                        toolImportDto.setNumberLot(Integer.valueOf(((String) ExcelUtil.convertValue(row.getCell(22), CellType.STRING))));
                    }
                    if (ExcelUtil.convertValue(row.getCell(23), CellType.STRING) != null) {
                        toolImportDto.setNameOwner(((String) ExcelUtil.convertValue(row.getCell(23), CellType.STRING)));
                    }
                    if (ExcelUtil.convertValue(row.getCell(24), CellType.STRING) != null) {
                        toolImportDto.setAddressOwner(((String) ExcelUtil.convertValue(row.getCell(24), CellType.STRING)));
                    }
                    dataToolsImport.add(toolImportDto);
                } else {
                    if (checkNextRowEmpty(i,xssfSheet, amountCheckRowHasData)) {
                        i = totalRow;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dataToolsImport;
    }

    private boolean checkNextRowEmpty(int i, XSSFSheet xssfSheet, int amountCheckRowHasData) {
        int tmp = 0;
        while(i < (i + amountCheckRowHasData) && i < 2000) {
            if (xssfSheet.getRow(i) != null && hasDataInRow(xssfSheet.getRow(i), 1)) {
                return false;
            }
            ++i;
            ++tmp;
        }
        if (tmp == amountCheckRowHasData) {
            return true;
        }
        return true;
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

    private List<FindAllToolToInventoryResponse> convertToFindAllToolToInventoryResponse(List<ToolDto> content) {
        List<FindAllToolToInventoryResponse> responses = new ArrayList<>();
        for (ToolDto toolDto : content){
            responses.add(constructionFindAllToolToInventoryResponse(toolDto));
        }
        return responses;
    }

    private FindAllToolToInventoryResponse constructionFindAllToolToInventoryResponse(ToolDto toolDto) {
        FindAllToolToInventoryResponse response = new FindAllToolToInventoryResponse();
        response.setIdTool(toolDto.getIdTool());
        response.setCodeTool(toolDto.getCodeTool());
        response.setNameTool(toolDto.getName());
        response.setNameToolCategory(toolDto.getNameToolCategory());
        response.setCodeToolCategory(toolDto.getCodeToolCategory());
        response.setIdToolCategory(toolDto.getIdToolCategory());
        response.setCodeDepartment(toolDto.getCodeDepartment());
        response.setIdDepartment(toolDto.getIdDepartment());
        response.setNameDepartment(toolDto.getNameDepartment());
        response.setTimeCreated(DateUtil.formatToPattern(new Date(toolDto.getTimeCreated()), DateUtil.DATE_FORMAT));
        response.setTimeModified(DateUtil.formatToPattern(new Date(toolDto.getTimeModified()), DateUtil.DATE_FORMAT));
        response.setQuantity(toolDto.getQuantity());
        response.setQuantityIncreaseCurrent(toolDto.getQuantityIncreaseCurrent());
        response.setQuantityDecreaseCurrent(toolDto.getQuantityDecreaseCurrent());
        response.setQuantityToInventory(toolDto.getQuantityIncreaseCurrent() - toolDto.getQuantityDecreaseCurrent());
        response.setValue(toolDto.getValue());
        response.setNameLocation(toolDto.getNameLocation());
        response.setIdLocation(toolDto.getIdLocation());
        response.setUserName(toolDto.getUserName());
        response.setStatusUse(toolDto.getStatusUse());
        response.setFullName(toolDto.getFullName());
        return response;
    }

    @Override
    public Page<FindAllToolResponseToIncrease> findAllToolToIncrease(FindAllToolToIncreaseRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        List<Integer> idsDepartment = ((CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getIdsDepartmentCurrent();
        request.setIdsDepartmentOriginal(idsDepartment);
        Page<ToolDto> findAllToolDtos = toolRepository.findAllToolDtoToIncrease(request, pageable);
        return new PageImpl<>(convertToFindAllToolToIncreaseResponse(findAllToolDtos.getContent()),pageable,findAllToolDtos.getTotalElements());
    }

    @Override
    public Page<FindAllToolResponseToDecrease> findAllToolToDecrease(FindAllToolToDecreaseRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        List<Integer> idsDepartment = ((CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getIdsDepartmentCurrent();
        request.setIdsDepartmentOriginal(idsDepartment);
        Page<ToolDto> findAllToolToDecreaseDtos = toolRepository.findAllToolDtoToDecrease(request, pageable);
        return new PageImpl<>(convertToFindAllToolToDecreaseResponse(findAllToolToDecreaseDtos.getContent()),pageable,findAllToolToDecreaseDtos.getTotalElements());
    }

    private List<FindAllToolResponseToDecrease> convertToFindAllToolToDecreaseResponse(List<ToolDto> content) {
        List<FindAllToolResponseToDecrease> responseToDecreaseList = new ArrayList<>();
        for (ToolDto toolDto : content) {
            FindAllToolResponseToDecrease findAllToolResponseToDecrease = new FindAllToolResponseToDecrease();
            findAllToolResponseToDecrease.setCodeTool(toolDto.getCodeTool());
            findAllToolResponseToDecrease.setNameTool(toolDto.getName());
            findAllToolResponseToDecrease.setCodeToolCategory(toolDto.getCodeToolCategory());
            findAllToolResponseToDecrease.setNameToolCategory(toolDto.getNameToolCategory());
            findAllToolResponseToDecrease.setCodeDepartment(toolDto.getCodeDepartment());
            findAllToolResponseToDecrease.setNameDepartment(toolDto.getNameDepartment());
            findAllToolResponseToDecrease.setTimeCreated(DateUtil.formatToPattern(new Date(toolDto.getTimeCreated()), DateUtil.DATE_FORMAT));
            findAllToolResponseToDecrease.setTimeModified(DateUtil.formatToPattern(new Date(toolDto.getTimeModified()), DateUtil.DATE_FORMAT));
            findAllToolResponseToDecrease.setIdTool(toolDto.getIdTool());
            findAllToolResponseToDecrease.setSalt(toolDto.getSalt());
            findAllToolResponseToDecrease.setQuantity(toolDto.getQuantity());
            findAllToolResponseToDecrease.setValue(toolDto.getValue());
            findAllToolResponseToDecrease.setQuantityIncreaseCurrent(toolDto.getQuantityIncreaseCurrent());
            findAllToolResponseToDecrease.setQuantityDecreaseCurrent(toolDto.getQuantityDecreaseCurrent());
            findAllToolResponseToDecrease.setStatusUse(toolDto.getStatusUse());
            findAllToolResponseToDecrease.setFullName(toolDto.getFullName());
            findAllToolResponseToDecrease.setNameLocation(toolDto.getNameLocation());
            responseToDecreaseList.add(findAllToolResponseToDecrease);
        }
        return responseToDecreaseList;
    }

    private List<FindAllToolResponseToIncrease> convertToFindAllToolToIncreaseResponse(List<ToolDto> content) {
        List<FindAllToolResponseToIncrease> responseToIncreases=new ArrayList<>();
        for (ToolDto toolDto : content) {
            FindAllToolResponseToIncrease responseToIncrease=new FindAllToolResponseToIncrease();
            responseToIncrease.setCodeTool(toolDto.getCodeTool());
            responseToIncrease.setNameTool(toolDto.getName());
            responseToIncrease.setCodeToolCategory(toolDto.getCodeToolCategory());
            responseToIncrease.setNameToolCategory(toolDto.getNameToolCategory());
            responseToIncrease.setCodeDepartment(toolDto.getCodeDepartment());
            responseToIncrease.setNameDepartment(toolDto.getNameDepartment());
            responseToIncrease.setTimeCreated(DateUtil.formatToPattern(new Date(toolDto.getTimeCreated()), DateUtil.DATE_FORMAT));
            responseToIncrease.setTimeModified(DateUtil.formatToPattern(new Date(toolDto.getTimeModified()), DateUtil.DATE_FORMAT));
            responseToIncrease.setIdTool(toolDto.getIdTool());
            responseToIncrease.setSalt(toolDto.getSalt());
            responseToIncrease.setQuantity(toolDto.getQuantity());
            responseToIncrease.setValue(toolDto.getValue());
            responseToIncrease.setQuantityIncreaseCurrent(toolDto.getQuantityIncreaseCurrent());
            responseToIncrease.setQuantityDecreaseCurrent(toolDto.getQuantityDecreaseCurrent());
            responseToIncreases.add(responseToIncrease);
        }
        return responseToIncreases;
    }

    @Override
    public String generateCodeTool() {
        int minLength = 4;
        String prefix = Constants.PREFIX_ASSET_CCDC;
        Integer idDepartment = ((CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getIdDepartmentCurrent();
        Department department =
                departmentService.findDepartmentByIdDepartmentAndStatus(idDepartment, Constants.DEPARTMENT_ACTIVE_STATUS);
        int codeValueCurrent = 1;
        if (StringUtils.isNotBlank(department.getCode())){
            prefix = department.getCode() + Constants.PREFIX_ASSET_CCDC;
        }
        Optional<Tool> tool = toolRepository.findLastToolByIdDepartmentOriginal(idDepartment);
        if (tool.isEmpty()) {
            return prefix + String.format("%0" + minLength +"d", codeValueCurrent) + "-" + String.valueOf(new Date().getTime());
        }
        String codeDocument = tool.get().getCodeTool().split("-")[0];
        codeValueCurrent = Integer.parseInt(codeDocument.replaceAll(ValueUtil.PATTERN_NON_NUMBER, ""));
        if (String.valueOf(codeValueCurrent).length() > minLength) {
            minLength = minLength + 2;
        }
        return prefix + String.format("%0" + minLength + "d",(codeValueCurrent + 1)) + "-" + String.valueOf(new Date().getTime());
    }

    @Transactional
    @Override
    public List<Tool> createNewTool(CreateNewToolRequest createNewToolRequest) throws ValidateFiledException {
        validateFieldCreateNewTool(createNewToolRequest);
        return storeNewTool(createNewToolRequest);
    }

    @Override
    public void updateTool(UpdateToolRequest updateToolRequest) throws ValidateFiledException {
        validateFiledUpdateTool(updateToolRequest);
        Tool toolParent = findToolBySaltTool(updateToolRequest.getSalt());
        updateFieldToolParent(toolParent, updateToolRequest);
        updateFiledChildrenTool(toolParent, updateToolRequest.getAllocateToolRequestList());
    }

    private void updateFiledChildrenTool(Tool tool, List<UpdateListAllocateToolRequest> allocateToolRequestList) {
        List<Tool> tools = findAllChildrenToolByIdToolParent(tool.getIdTool());
        int sumQuantity = 0;
        for (UpdateListAllocateToolRequest allocateToolRequest : allocateToolRequestList){
            Optional<CsvcUser> user = csvcUserService.findByUserName(allocateToolRequest.getUserName());
            if (user.isEmpty()) {
                throw new NotFoundException("Don't exits user by user name");
            }
            if (StringUtils.isNotBlank(allocateToolRequest.getSalt())) {
                tools.stream()
                        .filter(x -> x.getSalt().equals(allocateToolRequest.getSalt()))
                        .findFirst().ifPresent(x -> {
                            x.setName(tool.getName());
                            x.setYearUse(tool.getYearUse());
                            x.setIdDepartment(allocateToolRequest.getIdDepartment());
                            x.setIdLocation(allocateToolRequest.getIdLocation());
                            x.setStatusUse(allocateToolRequest.getStatusUse());
                            x.setQuantity(allocateToolRequest.getQuantity());
                            x.setIdDepartmentOriginal(allocateToolRequest.getIdDepartment());
                            x.setIdUserUse(user.get().getIdUser());
                            x.setTimeModified(String.valueOf(new Date().getTime()));
                            x.setIdUserModified(tool.getIdUserModified());
                        });
            } else {
                sumQuantity += allocateToolRequest.getQuantity();
                createNewChildrenTool(tool, allocateToolRequest);
            }
        }
        toolRepository.saveAll(tools);
        if (sumQuantity > 0) {
            sumQuantity += tool.getQuantity();
            tool.setQuantity(sumQuantity);
            toolRepository.save(tool);
        }
    }

    private void updateFieldToolParent(Tool tool, UpdateToolRequest updateToolRequest) {
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        tool.setName(updateToolRequest.getNameTool());
        tool.setIdToolCategory(updateToolRequest.getIdToolCategory());
        tool.setValue(updateToolRequest.getValue());
        tool.setQuantity(updateToolRequest.getQuantity());
        tool.setYearUse(updateToolRequest.getYearUse());
        tool.setTimeModified(String.valueOf(new Date().getTime()));
        tool.setIdUserModified(csvcUser.getIdUser());
        toolRepository.save(tool);
    }

    @Override
    public Tool findToolBySaltTool(String salt) {
        Optional<Tool> tool = toolRepository.findToolBySaltTool(salt);
        if (tool.isEmpty()){
            throw new NotFoundException("Don't exits tool by salt");
        }
        return tool.get();
    }

    @Override
    public Tool findToolByIdTool(Integer idTool) {
        Optional<Tool> tool = toolRepository.findToolById(idTool);
        if (tool.isEmpty()){
            throw new NotFoundException("Don't exits tool by id tool");
        }
        return tool.get();
    }

    @Override
    public List<Tool> findAllChildrenToolByIdToolParent(Integer idToolParent) {
        return toolRepository.findAllChildrenToolByIdToolParent(idToolParent);
    }

    @Transactional
    @Override
    public void deleteToolBySalt(String saltTool) {
        Tool tool = findToolBySaltTool(saltTool);
        if (tool.getParent() == null) {
            handleDeleteToolParent(tool);
        } else {
            handleDeleteToolChild(tool);
        }
    }

    private void handleDeleteToolChild(Tool tool) {
        toolRepository.delete(tool);
        updateQuantityAndIncreaseAndDecreaseToolParent(tool.getParent());
    }

    private void updateQuantityAndIncreaseAndDecreaseToolParent(Integer idParent) {
        toolRepository.updateQuantityAndIncreaseAndDecreaseTool(idParent);
    }

    private void handleDeleteToolParent(Tool toolParent) {
        toolRepository.delete(toolParent);
        toolRepository.deleteAll(findAllChildrenToolByIdToolParent(toolParent.getIdTool()));
    }

    @Override
    public List<Tool> findAllToolBySaltsAndIsIncrease(List<String> listSalts, Integer isIncrease) {
        return toolRepository.findAllToolBySaltsAndIsIncrease(listSalts, isIncrease);
    }



    private void validateFiledUpdateTool(UpdateToolRequest updateToolRequest) throws ValidateFiledException {
        if (StringUtils.isBlank(updateToolRequest.getSalt()) ||
        StringUtils.isBlank(updateToolRequest.getNameTool()) ||
        StringUtils.isBlank(updateToolRequest.getValue()) ||
        StringUtils.isBlank(updateToolRequest.getYearUse()) ||
        ObjectUtils.isEmpty(updateToolRequest.getIdToolCategory()) ||
        ObjectUtils.isEmpty(updateToolRequest.getQuantity()) ||
        CollectionUtils.isEmpty(updateToolRequest.getAllocateToolRequestList())) {
            throw new ValidateFiledException("Valid data");
        }
        toolCategoriesService.findToolCategoryByIdToolCategoryAndVisible(updateToolRequest.getIdToolCategory(),
                Constants.TOOL_CATEGORY_IS_VISIBLE);
        if (!CollectionUtils.isEmpty(updateToolRequest.getAllocateToolRequestList())){
            Integer totalQuantityChild = 0;
            for (UpdateListAllocateToolRequest allocateToolRequest : updateToolRequest.getAllocateToolRequestList()){
                totalQuantityChild += allocateToolRequest.getQuantity();
            }
            if (!totalQuantityChild.equals(updateToolRequest.getQuantity())) {
                throw new ValidateFiledException("Valid data");
            }
        }
    }

    private List<Tool> storeNewTool(CreateNewToolRequest createNewToolRequest) {
        Tool toolParent = toolRepository.save(constructionCreateNewToolParent(createNewToolRequest));
        return storeChildrenTool(toolParent, createNewToolRequest.getAllocateToolRequestList());
    }

    private List<Tool> storeChildrenTool(Tool toolParent, List<ListAllocateToolRequest> allocateToolRequestList) {
        List<Tool> childrenTool = new ArrayList<>();
        for (ListAllocateToolRequest allocateToolRequest : allocateToolRequestList) {
            childrenTool.add(constructionChildTool(toolParent, allocateToolRequest));
        }
        return toolRepository.saveAll(childrenTool);
    }

    private void createNewChildrenTool(Tool toolParent, UpdateListAllocateToolRequest updateAllocateToolRequestList) {
        ListAllocateToolRequest allocateToolRequest = new ListAllocateToolRequest();
        allocateToolRequest.setIdLocation(updateAllocateToolRequestList.getIdLocation());
        allocateToolRequest.setUserName(updateAllocateToolRequestList.getUserName());
        allocateToolRequest.setStatusUse(updateAllocateToolRequestList.getStatusUse());
        allocateToolRequest.setQuantity(updateAllocateToolRequestList.getQuantity());
        toolRepository.save(constructionChildTool(toolParent, allocateToolRequest));
    }

    private Tool constructionChildTool(Tool toolParent, ListAllocateToolRequest allocateToolRequest) {
        Optional<CsvcUser> user = csvcUserService.findByUserName(allocateToolRequest.getUserName());
        if (user.isEmpty()){
            throw new NotFoundException("Don't exits user by user name");
        }
        String currentTime = String.valueOf(new Date().getTime());
        Tool childTool = new Tool();
        childTool.setName(toolParent.getName());
        childTool.setCodeTool(toolParent.getCodeTool() + "-" + UUID.randomUUID());
        childTool.setSalt(String.valueOf(UUID.randomUUID()));
        childTool.setIdToolCategory(toolParent.getIdToolCategory());
        childTool.setTimeCreated(currentTime);
        childTool.setTimeModified(currentTime);
        childTool.setIdUserCreated(childTool.getIdUserCreated());
        childTool.setIdUserModified(childTool.getIdUserCreated());
        childTool.setQuantity(allocateToolRequest.getQuantity());
        childTool.setValue(toolParent.getValue());
        childTool.setIsIncrease(Constants.IS_NOT_INCREASED);
        childTool.setIsDecrease(Constants.IS_NOT_DECREASED);
        childTool.setQuantityIncreaseCurrent(Constants.TOOL_DEFAULT_QUANTITY_INCREASE_CURRENT);
        childTool.setQuantityDecreaseCurrent(Constants.TOOL_DEFAULT_QUANTITY_DECREASE_CURRENT);
        childTool.setStatusUse(allocateToolRequest.getStatusUse());
        childTool.setParent(toolParent.getIdTool());
        childTool.setIdDepartment(allocateToolRequest.getIdDepartment());
        childTool.setIdLocation(allocateToolRequest.getIdLocation());
        childTool.setIdUserUse(user.get().getIdUser());
        childTool.setYearUse(toolParent.getYearUse());
        childTool.setIdDepartmentOriginal(allocateToolRequest.getIdDepartment());
        return childTool;
    }

    private Tool constructionCreateNewToolParent(CreateNewToolRequest createNewToolRequest) {
        String currentTime = String.valueOf(new Date().getTime());
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Tool tool = new Tool();
        tool.setName(createNewToolRequest.getNameTool());
        tool.setCodeTool(createNewToolRequest.getCodeTool());
        tool.setSalt(String.valueOf(UUID.randomUUID()));
        tool.setIdToolCategory(createNewToolRequest.getIdToolCategory());
        tool.setTimeCreated(currentTime);
        tool.setTimeModified(currentTime);
        tool.setIdUserCreated(csvcUser.getIdUser());
        tool.setIdUserModified(csvcUser.getIdUser());
        tool.setValue(createNewToolRequest.getValue());
        tool.setQuantity(createNewToolRequest.getQuantity());
        tool.setIsIncrease(Constants.TOOL_IS_NOT_INCREASE);
        tool.setIsDecrease(Constants.TOOL_IS_NOT_DECREASE);
        tool.setQuantityIncreaseCurrent(Constants.TOOL_DEFAULT_QUANTITY_INCREASE_CURRENT);
        tool.setQuantityDecreaseCurrent(Constants.TOOL_DEFAULT_QUANTITY_DECREASE_CURRENT);
        tool.setIdDepartmentOriginal(csvcUser.getIdDepartmentCurrent());
        tool.setYearUse(createNewToolRequest.getYearUse());
        return tool;
    }

    private void validateFieldCreateNewTool(CreateNewToolRequest createNewToolRequest) throws ValidateFiledException {
        if (StringUtils.isBlank(createNewToolRequest.getNameTool()) ||
            StringUtils.isBlank(createNewToolRequest.getCodeTool()) ||
            StringUtils.isBlank(createNewToolRequest.getValue()) ||
            ObjectUtils.isEmpty(createNewToolRequest.getIdToolCategory()) ||
            ObjectUtils.isEmpty(createNewToolRequest.getYearUse())){
            throw new ValidateFiledException("Valid data");
        }
        toolCategoriesService.findToolCategoryByIdToolCategoryAndVisible(createNewToolRequest.getIdToolCategory(),
                Constants.TOOL_CATEGORY_IS_VISIBLE);
        if (!CollectionUtils.isEmpty(createNewToolRequest.getAllocateToolRequestList())){
            Integer totalQuantityChild = 0;
            for (ListAllocateToolRequest allocateToolRequest : createNewToolRequest.getAllocateToolRequestList()){
                totalQuantityChild += allocateToolRequest.getQuantity();
            }
            if (!totalQuantityChild.equals(createNewToolRequest.getQuantity())) {
                throw new ValidateFiledException("Valid data");
            }
        }
    }

    private List<FindAllToolResponse> convertToFindAllToolResponse(Page<ToolDto> toolDtos) {
        List<FindAllToolResponse> responses = new ArrayList<>();
        for (ToolDto toolDto : toolDtos){
            responses.add(constructionFindAllToolResponse(toolDto));
        }
        return responses;
    }

    private FindAllToolResponse constructionFindAllToolResponse(ToolDto toolDto) {
        FindAllToolResponse allToolResponse = new FindAllToolResponse();
        allToolResponse.setCodeTool(toolDto.getCodeTool());
        allToolResponse.setNameTool(toolDto.getName());
        allToolResponse.setNameToolCategory(toolDto.getNameToolCategory());
        allToolResponse.setCodeDepartment(toolDto.getCodeDepartment());
        allToolResponse.setNameDepartment(toolDto.getNameDepartment());
        allToolResponse.setTimeCreated(DateUtil.formatToPattern(new Date(toolDto.getTimeCreated()), DateUtil.DATE_FORMAT));
        allToolResponse.setTimeModified(DateUtil.formatToPattern(new Date(toolDto.getTimeModified()), DateUtil.DATE_FORMAT));
        allToolResponse.setSalt(toolDto.getSalt());
        allToolResponse.setQuantity(toolDto.getQuantity());
        allToolResponse.setIsIncrease(toolDto.getIsIncrease());
        allToolResponse.setIsDecrease(toolDto.getIsDecrease());
        allToolResponse.setValue(toolDto.getValue());
        allToolResponse.setYearUse(toolDto.getYearUse());
        allToolResponse.setStatusUse(toolDto.getStatusUse());
        return allToolResponse;
    }
}
