package com.example.csvccdshustbe.repository.toolProcess.impl;


import com.example.csvccdshustbe.dto.toolProcess.FindAllToolProcessDto;
import com.example.csvccdshustbe.repository.toolProcess.ToolProcessRepositoryCustom;
import com.example.csvccdshustbe.request.toolProcess.FindAllToolProcessRequest;
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

public class ToolProcessRepositoryImpl implements ToolProcessRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Page<FindAllToolProcessDto> findAllToolProcess(FindAllToolProcessRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append("select tool.id_tool idTool, tool.code_tool codeTl,  " +
                "tool.name nameTool, toolCategories.id_tool_category idToolCategory,  " +
                "ToolCategories.name nameToolCategory, ToolCategories.code_tool codeToolCategory,  " +
                "de.id_department idDepartment, de.code codeDepartment, de.name nameDepartment,  " +
                "lo.id_location idLocation, lo.name nameLocation,  " +
                "tool.time_created, tool.time_modified, tool.parent, tool.salt,  " +
                "tool.quantity, toolProcess.value, toolProcess.id_Tool_process  " +
                "from tool  " +
                "       left join tool_process toolProcess on tool.id_tool = toolProcess.id_tool      " +
                "       left join process process on toolProcess.id_process = process.id_process      " +
                "       left join tool_categories toolCategories      " +
                "               on tool.id_tool_category = toolCategories.id_tool_category      " +
                "       left join department de on tool.id_department = de.id_department             " +
                "       left join location lo on tool.id_location = lo.id_location             " +
                "       left join document do on process.id_process = do.id_process              " +
                "where 1 = 1           " +
                "and tool.id_department_original in (:idsDepartmentOriginal)  " +
                "and do.code = :codeDocument  and tool.parent is null");
        setConditionFindAllToolProcess(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllToolProcess(request, query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<FindAllToolProcessDto>responses = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] objects : result) {
                FindAllToolProcessDto findAllToolProcessDto = new FindAllToolProcessDto();
                findAllToolProcessDto.setIdTool(ValueUtil.getIntegerByObject(objects[0]));
                findAllToolProcessDto.setCodeTool(ValueUtil.getStringByObject(objects[1]));
                findAllToolProcessDto.setNameTool(ValueUtil.getStringByObject(objects[2]));
                findAllToolProcessDto.setIdToolProcess(ValueUtil.getIntegerByObject(objects[3]));
                findAllToolProcessDto.setNameToolCategory(ValueUtil.getStringByObject(objects[4]));
                findAllToolProcessDto.setCodeToolCategory(ValueUtil.getStringByObject(objects[5]));
                findAllToolProcessDto.setIdDepartment(ValueUtil.getIntegerByObject(objects[6]));
                findAllToolProcessDto.setCodeDepartment(ValueUtil.getStringByObject(objects[7]));
                findAllToolProcessDto.setNameDepartment(ValueUtil.getStringByObject(objects[8]));
                findAllToolProcessDto.setIdLocation(ValueUtil.getIntegerByObject(objects[9]));
                findAllToolProcessDto.setNameLocation(ValueUtil.getStringByObject(objects[10]));
                findAllToolProcessDto.setTimeCreated(ValueUtil.getLongByObject(objects[11]));
                findAllToolProcessDto.setTimeModified(ValueUtil.getLongByObject(objects[12]));
                findAllToolProcessDto.setParent(ValueUtil.getIntegerByObject(objects[13]));
                findAllToolProcessDto.setSalt(ValueUtil.getStringByObject(objects[14]));
                findAllToolProcessDto.setQuantity(ValueUtil.getIntegerByObject(objects[15]));
                findAllToolProcessDto.setValue(ValueUtil.getStringByObject(objects[16]));
                findAllToolProcessDto.setIdToolProcess(ValueUtil.getIntegerByObject(objects[17]));
                responses.add(findAllToolProcessDto);
            }
        }
        return new PageImpl<>(responses, pageable, countFindAllToolProcess(request));
    }

    private long countFindAllToolProcess(FindAllToolProcessRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("  select count(0)   " +
                "from tool  " +
                "       left join tool_process toolProcess on tool.id_tool = toolProcess.id_tool      " +
                "       left join process process on toolProcess.id_process = process.id_process      " +
                "       left join tool_categories toolCategories      " +
                "               on tool.id_tool_category = toolCategories.id_tool_category      " +
                "       left join department de on tool.id_department = de.id_department             " +
                "       left join location lo on tool.id_location = lo.id_location             " +
                "       left join document do on process.id_process = do.id_process              " +
                "where 1 = 1           " +
                "and tool.id_department_original in (:idsDepartmentOriginal)  " +
                "and do.code = :codeDocument  and tool.parent is null");
        setConditionFindAllToolProcess(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllToolProcess(request, query);
        return ValueUtil.getIntegerByObject(query.getSingleResult());
    }

    private void setParameterFindAllToolProcess(FindAllToolProcessRequest request, Query query) {
        query.setParameter("idsDepartmentOriginal", request.getIdsDepartmentOriginal());
        query.setParameter("codeDocument", request.getCodeDocument());
        if (StringUtils.isNotBlank(request.getNameTool())){
            query.setParameter("nameTool", request.getNameTool());
        }
        if (ObjectUtils.isNotEmpty(request.getIdToolCategory())){
            query.setParameter("idToolCategory", request.getIdToolCategory());
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())){
            query.setParameter("idDepartment", request.getIdDepartment());
        }
    }

    private void setConditionFindAllToolProcess(FindAllToolProcessRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getNameTool())){
            sb.append(" and (tool.name REGEXP :nameTool ) ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdToolCategory())){
            sb.append(" and toolCategories.id_tool_category = :idToolCategory ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())){
            sb.append(" and de.id_department = :idDepartment ");
        }
        if (StringUtils.isNotBlank(request.getSortBy())){
            sb.append("ORDER BY ");
            if (request.getSortBy().equals("nameTool")) {
                sb.append(" tool.name ");
            }
            if (request.getSortBy().equals("nameDepartment")) {
                sb.append(" de.name ");
            }
            sb.append(" ").append(request.getSortOrder());
        } else {
            sb.append(" ORDER BY tool.id_tool desc ");
        }
    }
}
