package com.example.csvccdshustbe.repository.tool.impl;

import com.example.csvccdshustbe.dto.tool.ToolDto;
import com.example.csvccdshustbe.entity.Tool;
import com.example.csvccdshustbe.repository.tool.ToolRepositoryCustom;
import com.example.csvccdshustbe.request.tool.FindAllToolRequest;
import com.example.csvccdshustbe.utility.PageUtils;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ToolRepositoryImpl implements ToolRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Page<ToolDto> findAllToolDto(FindAllToolRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select tol.id_tool, tol.name, tol.code_tool, tol.salt, " +
                "       tol.id_tool_category, tol.time_created, tol.time_modified, " +
                "       tol.id_user_created, tol.id_user_modified, tol.value, " +
                "       tol.quantity, tol.is_increase, tol.is_decrease, tol.quantity_increase_current, " +
                "       tol.quantity_decrease_current, tol.id_process_current, tol.status_process_current, " +
                "       tol.id_type_process_current, tol.id_department_original, tol.status_use, " +
                "       tol.parent, tol.id_department, tol.id_location, " +
                "       tol.id_user_use, tol.year_use, " +
                "       de.code, de.name, lo.name " +
                "from tool tol " +
                "    left join tool_categories tolca on tol.id_tool_category = tolca.id_tool_category " +
                "    left join department de on tol.id_department = de.id_department " +
                "    left join location lo on de.id_department = lo.id_department " +
                "where tol.id_department_original in (:idsDepartmentOriginal)  " +
                "  and tol.parent is null ");
        setConditionFindAllToolDto(sb, request);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllToolDto(query, request);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<ToolDto> toolDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                toolDtos.add(writeDataToolDtos(obj));
            }
        }
        return new PageImpl<>(toolDtos, pageable, countFindAllToolDtos(request));
    }

    @Override
    public Optional<Tool> findLastToolByIdDepartmentOriginal(Integer idDepartment) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select id_tool, name, code_tool, salt,  " +
                "       id_tool_category, time_created,  " +
                "       time_modified, id_user_created,  " +
                "       id_user_modified, value, quantity,  " +
                "       is_increase, is_decrease, quantity_increase_current,  " +
                "       quantity_decrease_current, id_process_current,  " +
                "       status_process_current, id_type_process_current,  " +
                "       id_department_original, status_use, parent,  " +
                "       id_department, id_location, id_user_use, year_use " +
                "from tool  " +
                "where id_department_original = :idDepartmentOriginal " +
                "order by tool.id_tool desc limit 1  ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idDepartmentOriginal", idDepartment);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                Tool tool = new Tool();
                tool.setIdTool(ValueUtil.getIntegerByObject(obj[0]));
                tool.setName(ValueUtil.getStringByObject(obj[1]));
                tool.setCodeTool(ValueUtil.getStringByObject(obj[2]));
                tool.setSalt(ValueUtil.getStringByObject(obj[3]));
                tool.setIdToolCategory(ValueUtil.getIntegerByObject(obj[4]));
                tool.setTimeCreated(ValueUtil.getStringByObject(obj[5]));
                tool.setTimeModified(ValueUtil.getStringByObject(obj[6]));
                tool.setIdUserCreated(ValueUtil.getIntegerByObject(obj[7]));
                tool.setIdUserModified(ValueUtil.getIntegerByObject(obj[8]));
                tool.setValue(ValueUtil.getStringByObject(obj[9]));
                tool.setQuantity(ValueUtil.getIntegerByObject(obj[10]));
                tool.setIsIncrease(ValueUtil.getIntegerByObject(obj[11]));
                tool.setIsDecrease(ValueUtil.getIntegerByObject(obj[12]));
                tool.setQuantityIncreaseCurrent(ValueUtil.getIntegerByObject(obj[13]));
                tool.setQuantityDecreaseCurrent(ValueUtil.getIntegerByObject(obj[14]));
                tool.setIdTypeProcessCurrent(ValueUtil.getIntegerByObject(obj[15]));
                tool.setStatusProcessCurrent(ValueUtil.getIntegerByObject(obj[16]));
                tool.setIdTypeProcessCurrent(ValueUtil.getIntegerByObject(obj[17]));
                tool.setIdDepartmentOriginal(ValueUtil.getIntegerByObject(obj[18]));
                tool.setStatusUse(ValueUtil.getIntegerByObject(obj[19]));
                tool.setParent(ValueUtil.getIntegerByObject(obj[20]));
                tool.setIdDepartment(ValueUtil.getIntegerByObject(obj[21]));
                tool.setIdLocation(ValueUtil.getIntegerByObject(obj[22]));
                tool.setIdUserUse(ValueUtil.getIntegerByObject(obj[23]));
                tool.setYearUse(ValueUtil.getStringByObject(obj[24]));
                return Optional.of(tool);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Tool> findToolBySaltTool(String salt) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select id_tool, name, code_tool, salt,   " +
                "       id_tool_category, time_created,   " +
                "       time_modified, id_user_created,   " +
                "       id_user_modified, value, quantity,   " +
                "       is_increase, is_decrease, quantity_increase_current,  " +
                "       quantity_decrease_current, id_process_current,   " +
                "       status_process_current, id_type_process_current,   " +
                "       id_department_original, status_use, parent,   " +
                "       id_department, id_location, id_user_use, year_use  " +
                "from tool where tool.salt = :salt ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("salt", salt);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                return Optional.of(writeDataToolBySaltTool(obj));
            }
        }
        return Optional.empty();
    }

    private Tool writeDataToolBySaltTool(Object[] obj) {
        Tool tool = new Tool();
        tool.setIdTool(ValueUtil.getIntegerByObject(obj[0]));
        tool.setName(ValueUtil.getStringByObject(obj[1]));
        tool.setCodeTool(ValueUtil.getStringByObject(obj[2]));
        tool.setSalt(ValueUtil.getStringByObject(obj[3]));
        tool.setIdToolCategory(ValueUtil.getIntegerByObject(obj[4]));
        tool.setTimeCreated(ValueUtil.getStringByObject(obj[5]));
        tool.setTimeModified(ValueUtil.getStringByObject(obj[6]));
        tool.setIdUserCreated(ValueUtil.getIntegerByObject(obj[7]));
        tool.setIdUserModified(ValueUtil.getIntegerByObject(obj[8]));
        tool.setValue(ValueUtil.getStringByObject(obj[9]));
        tool.setQuantity(ValueUtil.getIntegerByObject(obj[10]));
        tool.setIsIncrease(ValueUtil.getIntegerByObject(obj[11]));
        tool.setIsDecrease(ValueUtil.getIntegerByObject(obj[12]));
        tool.setQuantityIncreaseCurrent(ValueUtil.getIntegerByObject(obj[13]));
        tool.setQuantityDecreaseCurrent(ValueUtil.getIntegerByObject(obj[14]));
        tool.setIdProcessCurrent(ValueUtil.getIntegerByObject(obj[15]));
        tool.setStatusProcessCurrent(ValueUtil.getIntegerByObject(obj[16]));
        tool.setIdTypeProcessCurrent(ValueUtil.getIntegerByObject(obj[17]));
        tool.setIdDepartmentOriginal(ValueUtil.getIntegerByObject(obj[18]));
        tool.setStatusUse(ValueUtil.getIntegerByObject(obj[19]));
        tool.setParent(ValueUtil.getIntegerByObject(obj[20]));
        tool.setIdDepartment(ValueUtil.getIntegerByObject(obj[21]));
        tool.setIdLocation(ValueUtil.getIntegerByObject(obj[22]));
        tool.setIdUserUse(ValueUtil.getIntegerByObject(obj[23]));
        tool.setYearUse(ValueUtil.getStringByObject(obj[24]));
        return tool;
    }

    @Override
    public List<Tool> findAllChildrenToolByIdToolParent(Integer idToolParent) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select id_tool, name, code_tool, salt,   " +
                "       id_tool_category, time_created,   " +
                "       time_modified, id_user_created,   " +
                "       id_user_modified, value, quantity,   " +
                "       is_increase, is_decrease, quantity_increase_current,   " +
                "       quantity_decrease_current, id_process_current,   " +
                "       status_process_current, id_type_process_current,   " +
                "       id_department_original, status_use, parent,   " +
                "       id_department, id_location, id_user_use, year_use   " +
                "from tool where tool.parent = :idToolParent ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idToolParent", idToolParent);
        List<Object[]> result = query.getResultList();
        List<Tool> tools = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                tools.add(writeDataToolByIdToolParent(obj));
            }
        }
        return tools;
    }

    private Tool writeDataToolByIdToolParent(Object[] obj) {
        Tool tool = new Tool();
        tool.setIdTool(ValueUtil.getIntegerByObject(obj[0]));
        tool.setName(ValueUtil.getStringByObject(obj[1]));
        tool.setCodeTool(ValueUtil.getStringByObject(obj[2]));
        tool.setSalt(ValueUtil.getStringByObject(obj[3]));
        tool.setIdToolCategory(ValueUtil.getIntegerByObject(obj[4]));
        tool.setTimeCreated(ValueUtil.getStringByObject(obj[5]));
        tool.setTimeModified(ValueUtil.getStringByObject(obj[6]));
        tool.setIdUserCreated(ValueUtil.getIntegerByObject(obj[7]));
        tool.setIdUserModified(ValueUtil.getIntegerByObject(obj[8]));
        tool.setValue(ValueUtil.getStringByObject(obj[9]));
        tool.setQuantity(ValueUtil.getIntegerByObject(obj[10]));
        tool.setIsIncrease(ValueUtil.getIntegerByObject(obj[11]));
        tool.setIsDecrease(ValueUtil.getIntegerByObject(obj[12]));
        tool.setQuantityIncreaseCurrent(ValueUtil.getIntegerByObject(obj[13]));
        tool.setQuantityDecreaseCurrent(ValueUtil.getIntegerByObject(obj[14]));
        tool.setIdProcessCurrent(ValueUtil.getIntegerByObject(obj[15]));
        tool.setStatusProcessCurrent(ValueUtil.getIntegerByObject(obj[16]));
        tool.setIdTypeProcessCurrent(ValueUtil.getIntegerByObject(obj[17]));
        tool.setIdDepartmentOriginal(ValueUtil.getIntegerByObject(obj[18]));
        tool.setStatusUse(ValueUtil.getIntegerByObject(obj[19]));
        tool.setParent(ValueUtil.getIntegerByObject(obj[20]));
        tool.setIdDepartment(ValueUtil.getIntegerByObject(obj[21]));
        tool.setIdLocation(ValueUtil.getIntegerByObject(obj[22]));
        tool.setIdUserUse(ValueUtil.getIntegerByObject(obj[23]));
        tool.setYearUse(ValueUtil.getStringByObject(obj[24]));
        return tool;
    }

    private long countFindAllToolDtos(FindAllToolRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0) count  " +
                "from tool tol  " +
                "    left join tool_categories tolca on tol.id_tool_category = tolca.id_tool_category  " +
                "    left join department de on tol.id_department = de.id_department  " +
                "    left join location lo on de.id_department = lo.id_department  " +
                "where tol.id_department_original in (:idsDepartmentOriginal)  " +
                " and tol.parent is null ");
        setConditionFindAllToolDto(sb, request);
        Query query  = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllToolDto(query, request);
        return ValueUtil.getIntegerByObject(query.getSingleResult());
    }

    private ToolDto writeDataToolDtos(Object[] obj) {
        ToolDto toolDto = new ToolDto();
        toolDto.setIdTool(ValueUtil.getIntegerByObject(obj[0]));
        toolDto.setName(ValueUtil.getStringByObject(obj[1]));
        toolDto.setCodeTool(ValueUtil.getStringByObject(obj[2]));
        toolDto.setSalt(ValueUtil.getStringByObject(obj[3]));
        toolDto.setIdToolCategory(ValueUtil.getIntegerByObject(obj[4]));
        toolDto.setTimeCreated(ValueUtil.getStringByObject(obj[5]));
        toolDto.setTimeModified(ValueUtil.getStringByObject(obj[6]));
        toolDto.setIdUserCreated(ValueUtil.getIntegerByObject(obj[7]));
        toolDto.setIdUserModified(ValueUtil.getIntegerByObject(obj[8]));
        toolDto.setValue(ValueUtil.getStringByObject(obj[9]));
        toolDto.setQuantity(ValueUtil.getIntegerByObject(obj[10]));
        toolDto.setIsIncrease(ValueUtil.getIntegerByObject(obj[11]));
        toolDto.setIsDecrease(ValueUtil.getIntegerByObject(obj[12]));
        toolDto.setQuantityIncreaseCurrent(ValueUtil.getIntegerByObject(obj[13]));
        toolDto.setQuantityDecreaseCurrent(ValueUtil.getIntegerByObject(obj[14]));
        toolDto.setIdProcessCurrent(ValueUtil.getIntegerByObject(obj[15]));
        toolDto.setStatusProcessCurrent(ValueUtil.getIntegerByObject(obj[16]));
        toolDto.setIdTypeProcessCurrent(ValueUtil.getIntegerByObject(obj[17]));
        toolDto.setIdDepartmentOriginal(ValueUtil.getIntegerByObject(obj[18]));
        toolDto.setStatusUse(ValueUtil.getIntegerByObject(obj[19]));
        toolDto.setParent(ValueUtil.getIntegerByObject(obj[20]));
        toolDto.setIdDepartment(ValueUtil.getIntegerByObject(obj[21]));
        toolDto.setIdLocation(ValueUtil.getIntegerByObject(obj[22]));
        toolDto.setIdUserUse(ValueUtil.getIntegerByObject(obj[23]));
        toolDto.setYearUse(ValueUtil.getStringByObject(obj[24]));
        toolDto.setCodeDepartment(ValueUtil.getStringByObject(obj[25]));
        toolDto.setNameDepartment(ValueUtil.getStringByObject(obj[26]));
        toolDto.setNameLocation(ValueUtil.getStringByObject(obj[27]));
        return toolDto;
    }

    private void setParameterFindAllToolDto(Query query, FindAllToolRequest request) {
        if (StringUtils.isNotBlank(request.getCodeTool())){
            query.setParameter("codeTool", request.getCodeTool());
        }
        if (StringUtils.isNotBlank(request.getNameTool())) {
            query.setParameter("nameTool", request.getNameTool());
        }
        if (ObjectUtils.isNotEmpty(request.getIdToolCategory())){
            query.setParameter("idToolCategory", request.getIdToolCategory());
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())) {
            query.setParameter("idDepartment", request.getIdDepartment());
        }
        if (ObjectUtils.isNotEmpty(request.getIsDecrease())) {
            query.setParameter("isDecrease", request.getIsDecrease());
        }
        if (ObjectUtils.isNotEmpty(request.getIsIncrease())) {
            query.setParameter("isIncrease", request.getIsIncrease());
        }
        if (ObjectUtils.isNotEmpty(request.getStatusUse())) {
            query.setParameter("statusUse", request.getStatusUse());
        }
    }

    private void setConditionFindAllToolDto(StringBuilder sb, FindAllToolRequest request) {
        if (StringUtils.isNotBlank(request.getCodeTool())){
            sb.append(" and (tol.code_tool REGEXP :codeTool ) ");
        }
        if (StringUtils.isNotBlank(request.getNameTool())) {
            sb.append(" and ( tol.name REGEXP :nameTool ) ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdToolCategory())){
            sb.append("  and tolca.id_tool_category = :idToolCategory ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())) {
            sb.append("  and de.id_department = :idDepartment ");
        }
        if (ObjectUtils.isNotEmpty(request.getIsDecrease())) {
            sb.append("  and tol.is_decrease = :isDecrease ");
        }
        if (ObjectUtils.isNotEmpty(request.getIsIncrease())) {
            sb.append("  and tol.is_increase = :isIncrease ");
        }
        if (ObjectUtils.isNotEmpty(request.getStatusUse())) {
            sb.append("  and tol.status_use = :statusUse ");
        }
    }
}
