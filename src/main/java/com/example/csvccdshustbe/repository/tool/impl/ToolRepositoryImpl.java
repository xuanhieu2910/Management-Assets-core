package com.example.csvccdshustbe.repository.tool.impl;

import com.example.csvccdshustbe.dto.tool.FindDetailsToolDto;
import com.example.csvccdshustbe.dto.tool.ToolDto;
import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.entity.Tool;
import com.example.csvccdshustbe.repository.tool.ToolRepositoryCustom;
import com.example.csvccdshustbe.request.tool.FindAllToolRequest;
import com.example.csvccdshustbe.request.tool.FindAllToolToDecreaseRequest;
import com.example.csvccdshustbe.request.tool.FindAllToolToIncreaseRequest;
import com.example.csvccdshustbe.response.tool.StatisticToolsResponse;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.PageUtils;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ToolRepositoryImpl implements ToolRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Page<ToolDto> findAllToolParentDto(FindAllToolRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select tol.id_tool, tol.name, tol.code_tool, tol.salt, " +
                "       tol.id_tool_category, tol.time_created, tol.time_modified, " +
                "       tol.id_user_created, tol.id_user_modified, tol.value, " +
                "       tol.quantity, tol.is_increase, tol.is_decrease, tol.quantity_increase_current, " +
                "       tol.quantity_decrease_current, tol.id_process_current, tol.status_process_current, " +
                "       tol.id_type_process_current, tol.id_department_original, tol.status_use, " +
                "       tol.parent, tol.id_department, tol.id_location, " +
                "       tol.id_user_use, tol.year_use, " +
                "       de.code, de.name, lo.name, tol.price " +
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
    public Page<ToolDto> findAllChildrenToolDto(FindAllToolRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select tol.id_tool, tol.name, tol.code_tool, tol.salt,    " +
                "        tol.id_tool_category, tol.time_created, tol.time_modified,    " +
                "        tol.id_user_created, tol.id_user_modified, tol.value,    " +
                "        tol.quantity, tol.is_increase, tol.is_decrease, tol.quantity_increase_current,    " +
                "        tol.quantity_decrease_current, tol.id_process_current, tol.status_process_current,    " +
                "        tol.id_type_process_current, tol.id_department_original, tol.status_use,    " +
                "        tol.parent, tol.id_department, tol.id_location,    " +
                "        tol.id_user_use, tol.year_use,    " +
                "        de.code, de.name, lo.name, tol.price    " +
                " from tool tol    " +
                "     left join tool_categories tolca on tol.id_tool_category = tolca.id_tool_category    " +
                "     left join department de on tol.id_department = de.id_department    " +
                "     left join location lo on de.id_department = lo.id_department  " +
                "     inner join (select id_tool from tool where salt = :salt) tolParent  " +
                "         on tol.parent = tolParent.id_tool  " +
                " where 1 = 1 ");
        setConditionFindAllChildrenToolDto(sb, request);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllChildrenToolDto(query, request);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<ToolDto> toolDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result){
                toolDtos.add(writeDataToolDtos(obj));
            }
        }
        return new PageImpl<>(toolDtos, pageable, countFindAllChildrenToolDtos(request));
    }

    @Override
    public Optional<Tool> findToolById(Integer idTool) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select id_tool, name, code_tool, salt,   " +
                "          id_tool_category, time_created,   " +
                "          time_modified, id_user_created,   " +
                "          id_user_modified, value, quantity,   " +
                "          is_increase, is_decrease, quantity_increase_current,   " +
                "          quantity_decrease_current, id_process_current,   " +
                "          status_process_current, id_type_process_current,   " +
                "          id_department_original, status_use, parent,   " +
                "          id_department, id_location, id_user_use, year_use, price   " +
                "from tool where tool.id_tool = :idTool ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idTool", idTool);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                return Optional.of(writeDataTool(obj));
            }
        }
        return Optional.empty();
    }


    @Transactional
    @Modifying
    @Override
    public void updateQuantityAndIncreaseAndDecreaseTool(Integer idParent) {
        StringBuilder sb = new StringBuilder();
        sb.append(" update tool   " +
                "    inner join (select result.id_tool,   " +
                "                       case when result.countToolChildren = result.countIsIncrease then 1 else -1 end isIncrease,   " +
                "                       case when result.countToolChildren = result.countIsDecrease then 1 else -1 end isDecrease,   " +
                "                       quantity, sumIncrease, sumDecrease, sumPrice   " +
                "                from (select toolParent.id_tool, count(toolChildren.id_tool) countToolChildren,   " +
                "                             sum(case when toolChildren.is_increase = :isIncrease then 1 else 0 end) countIsIncrease,   " +
                "                             sum(case when toolChildren.is_decrease = :isDecrease then 1 else 0 end) countIsDecrease,   " +
                "                             sum(toolChildren.quantity) quantity,   " +
                "                             sum(case when toolChildren.is_increase = :isIncrease then toolChildren.quantity else 0 end) sumIncrease,   " +
                "                             sum(case when toolChildren.is_decrease = :isDecrease then toolChildren.quantity else 0 end) sumDecrease,   " +
                "                             CONVERT(varchar(255), CAST(toolChildren.price as INT))sumPrice   " +
                "                      from tool toolParent   " +
                "                               inner join tool toolChildren on toolParent.id_tool = toolChildren.parent   " +
                "                      where toolParent.id_tool = :idTool   " +
                "                      group by toolParent.id_tool) result ) result   " +
                "    on tool.id_tool = result.id_tool   " +
                "    set tool.is_increase = (case when result.isIncrease = 1 then 2 else tool.is_increase end),   " +
                "        tool.is_decrease = (case when result.isDecrease = 1 then 2 else tool.is_decrease end),   " +
                "        tool.quantity = result.quantity,   " +
                "        tool.quantity_increase_current = sumIncrease,   " +
                "        tool.quantity_decrease_current = sumDecrease,   " +
                "        tool.price = sumPrice   " +
                "where 1=1    ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idTool", idParent);
        query.setParameter("isIncrease", Constants.IS_INCREASED);
        query.setParameter("isDecrease", Constants.IS_DECREASED);
        query.executeUpdate();
    }

    @Override
    public StatisticToolsResponse getStatisticTool() {
        StringBuilder sb = new StringBuilder();
        sb.append(" select sum(tool.quantity) as sumQuantity,   " +
                "       CAST(sum(cast(tool.price as INTEGER)) as NCHAR) as sumPrice   " +
                "from tool   " +
                "where id_department_original in (:idsDepartmentOriginal) ");
        Query query = entityManager.createNativeQuery(sb.toString());
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        query.setParameter("idsDepartmentOriginal", csvcUser.getIdsDepartmentCurrent());
        List<Object[]> result = query.getResultList();
        StatisticToolsResponse response = new StatisticToolsResponse();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                response.setSumQuantityTools(ValueUtil.getIntegerByObject(obj[0]));
                response.setSumPrice(ValueUtil.getStringByObject(obj[1]));
            }
        }
        return response;
    }

    @Override
    public Optional<FindDetailsToolDto> findDetailToolBySalt(String saltTool) {
        StringBuilder sb = new StringBuilder();
        sb.append("select tool.name,tool.code_tool,tool.id_tool_category, " +
                "       tool_categories.code_tool,tool_categories.name, " +
                "       tool.parent,tool.quantity,tool.value, " +
                "       tool.id_department,department.name,tool.id_location, " +
                "       location.name,tool.is_increase,tool.is_decrease, " +
                "       tool.status_use,tool.id_user_use,csvc_user.full_name " +
                "from tool left join tool_categories on tool.id_tool_category=tool_categories.id_tool_category " +
                "left join department on tool.id_department=department.id_department " +
                "left join location on tool.id_location=location.id_location " +
                "left join csvc_user on tool.id_user_use = csvc_user.id_user where  tool.salt =:saltTool ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("saltTool", saltTool);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                FindDetailsToolDto findDetailsToolDto = new FindDetailsToolDto();
                findDetailsToolDto.setName(ValueUtil.getStringByObject(obj[0]));
                findDetailsToolDto.setCodeTool(ValueUtil.getStringByObject(obj[1]));
                findDetailsToolDto.setIdToolCategory(ValueUtil.getIntegerByObject(obj[2]));
                findDetailsToolDto.setCodeToolCategory(ValueUtil.getStringByObject(obj[3]));
                findDetailsToolDto.setNameToolCategory(ValueUtil.getStringByObject(obj[4]));
                findDetailsToolDto.setIdParent(ValueUtil.getIntegerByObject(obj[5]));
                findDetailsToolDto.setQuantity(ValueUtil.getIntegerByObject(obj[6]));
                findDetailsToolDto.setValue(ValueUtil.getStringByObject(obj[7]));
                findDetailsToolDto.setIdDepartment(ValueUtil.getIntegerByObject(obj[8]));
                findDetailsToolDto.setNameDepartment(ValueUtil.getStringByObject(obj[9]));
                findDetailsToolDto.setIdLocation(ValueUtil.getIntegerByObject(obj[10]));
                findDetailsToolDto.setNameLocation(ValueUtil.getStringByObject(obj[11]));
                findDetailsToolDto.setIsIncrease(ValueUtil.getIntegerByObject(obj[12]));
                findDetailsToolDto.setIsDecrease(ValueUtil.getIntegerByObject(obj[13]));
                findDetailsToolDto.setStatusUse(ValueUtil.getIntegerByObject(obj[14]));
                findDetailsToolDto.setIdUserUse(ValueUtil.getIntegerByObject(obj[15]));
                findDetailsToolDto.setNameUserUse(ValueUtil.getStringByObject(obj[16]));
                return Optional.of(findDetailsToolDto);

            }
        }
        return Optional.empty();
    }

    @Override
    public Page<ToolDto> findAllToolDtoToIncrease(FindAllToolToIncreaseRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select tol.id_tool, tol.name, tol.code_tool, tol.salt, " +
                "       tol.id_tool_category, tol.time_created, tol.time_modified, " +
                "       tol.id_user_created, tol.id_user_modified, tol.value, " +
                "       tol.quantity, tol.is_increase, tol.is_decrease, tol.quantity_increase_current, " +
                "       tol.quantity_decrease_current, tol.id_process_current, tol.status_process_current, " +
                "       tol.id_type_process_current, tol.id_department_original, tol.status_use, " +
                "       tol.parent, tol.id_department, tol.id_location, " +
                "       tol.id_user_use, tol.year_use, " +
                "       de.code, de.name, lo.name, tol.price " +
                "from tool tol " +
                "    left join tool_categories tolca on tol.id_tool_category = tolca.id_tool_category " +
                "    left join department de on tol.id_department = de.id_department " +
                "    left join location lo on de.id_department = lo.id_department " +
                "where tol.id_department_original in (:idsDepartmentOriginal)  " +
                "  and (tol.status_process_current != :statusProcessCurrent or tol.status_process_current is null) ");
        setConditionFindAllToolToIncreaseDto(sb, request);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllToolToIncreaseDto(query, request);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<ToolDto> toolDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                toolDtos.add(writeDataToolDtos(obj));
            }
        }
        return new PageImpl<>(toolDtos, pageable, countFindAllToolToIncreaseDtos(request));
    }

    @Override
    public Page<ToolDto> findAllToolDtoToDecrease(FindAllToolToDecreaseRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select tol.id_tool, tol.name, tol.code_tool, tol.salt, " +
                "       tol.id_tool_category, tol.time_created, tol.time_modified, " +
                "       tol.id_user_created, tol.id_user_modified, tol.value, " +
                "       tol.quantity, tol.is_increase, tol.is_decrease, tol.quantity_increase_current, " +
                "       tol.quantity_decrease_current, tol.id_process_current, tol.status_process_current, " +
                "       tol.id_type_process_current, tol.id_department_original, tol.status_use, " +
                "       tol.parent, tol.id_department, tol.id_location, " +
                "       tol.id_user_use, tol.year_use, " +
                "       de.code, de.name, lo.name, tol.price " +
                "from tool tol " +
                "    left join tool_categories tolca on tol.id_tool_category = tolca.id_tool_category " +
                "    left join department de on tol.id_department = de.id_department " +
                "    left join location lo on de.id_department = lo.id_department " +
                "where tol.id_department_original in (:idsDepartmentOriginal)  " +
                "  and (tol.status_process_current != :statusProcessCurrent or tol.status_process_current is null) ");
        setConditionFindAllToolToDecreaseDto(sb, request);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllToolToDecreaseDto(query, request);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<ToolDto> toolDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                toolDtos.add(writeDataToolDtos(obj));
            }
        }
        return new PageImpl<>(toolDtos, pageable, countFindAllToolToDecreaseDtos(request));
    }

    private void setParameterFindAllToolToDecreaseDto(Query query, FindAllToolToDecreaseRequest request) {
        query.setParameter("idsDepartmentOriginal", request.getIdsDepartmentOriginal());
        if (request.getNotChildren()) {
            query.setParameter("isIncrease", Constants.IS_INCREASED);
            query.setParameter("isDecrease", Constants.IS_DECREASED);
        } else {
            query.setParameter("isIncrease", Constants.TOOL_PARENT_IS_INCREASED_WHOLE);
            query.setParameter("isIncreasePart", Constants.TOOL_PARENT_IS_INCREASING);
            query.setParameter("isDecrease", Constants.TOOL_PARENT_IS_DECREASED_WHOLE);
        }
        query.setParameter("statusProcessCurrent", Constants.STATUS_PENDING_PROCESS);
        if (StringUtils.isNotBlank(request.getNameTool())){
            query.setParameter("nameTool", request.getNameTool());
        }
        if (ObjectUtils.isNotEmpty(request.getIdToolCategory())){
            query.setParameter("idToolCategory", request.getIdToolCategory());
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())){
            query.setParameter("idDepartment", request.getIdDepartment());
        }
        if (ObjectUtils.isNotEmpty(request.getStatusUse())){
            query.setParameter("statusUse", request.getStatusUse());
        }
    }

    private void setConditionFindAllToolToDecreaseDto(StringBuilder sb, FindAllToolToDecreaseRequest request) {
        if (request.getNotChildren()) {
            sb.append("  and tol.is_increase = :isIncrease and tol.is_decrease != :isDecrease");
            sb.append("  and tol.parent is null ");
        } else {
            sb.append(" and (tol.is_increase = :isIncrease or tol.is_increase = :isIncreasePart) ");
            sb.append("  and tol.is_decrease != :isDecrease ");
        }
        if (StringUtils.isNotBlank(request.getNameTool())) {
            sb.append(" and (tol.name REGEXP :nameTool ) ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdToolCategory())) {
            sb.append(" and tolca.id_tool_category = :idToolCategory ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())) {
            sb.append(" and de.id_department = :idDepartment ");
        }
        if (ObjectUtils.isNotEmpty(request.getStatusUse())){
            sb.append(" and tol.status_use = : statusUse ");
        }
        if (StringUtils.isNotBlank(request.getSortBy())) {
            sb.append("ORDER BY ");
            if (request.getSortBy().equals("nameTool")) {
                sb.append(" tol.name ");
            }
            if (request.getSortBy().equals("nameDepartment")) {
                sb.append(" de.name ");
            }
            sb.append(" ").append(request.getSortOrder());
        } else {
            sb.append(" ORDER BY tol.id_tool desc ");
        }
    }

    private void setParameterFindAllToolToIncreaseDto(Query query, FindAllToolToIncreaseRequest request) {
        query.setParameter("idsDepartmentOriginal", request.getIdsDepartmentOriginal());
        if (request.getNotChildren()) {
            query.setParameter("isIncrease", Constants.IS_NOT_INCREASED);
        } else {
            query.setParameter("isIncrease", Constants.TOOL_PARENT_NOT_IS_INCREASE_WHOLE);
            query.setParameter("isIncreasePart", Constants.TOOL_PARENT_IS_INCREASING);
        }
        query.setParameter("statusProcessCurrent", Constants.STATUS_PENDING_PROCESS);
        if (StringUtils.isNotBlank(request.getNameTool())){
            query.setParameter("nameTool", request.getNameTool());
        }
        if (ObjectUtils.isNotEmpty(request.getIdToolCategory())){
            query.setParameter("idToolCategory", request.getIdToolCategory());
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())){
            query.setParameter("idDepartment", request.getIdDepartment());
        }
        if (ObjectUtils.isNotEmpty(request.getStatusUse())){
            query.setParameter("statusUse", request.getStatusUse());
        }
    }

    private void setConditionFindAllToolToIncreaseDto(StringBuilder sb, FindAllToolToIncreaseRequest request) {
        if (request.getNotChildren()) {
            sb.append("  and tol.is_increase = :isIncrease ");
            sb.append("  and tol.parent is null ");
        } else {
            sb.append(" and (tol.is_increase = :isIncrease or tol.is_increase = :isIncreasePart) ");
        }
        if (StringUtils.isNotBlank(request.getNameTool())) {
            sb.append(" and (tol.name REGEXP :nameTool ) ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdToolCategory())) {
            sb.append(" and tolca.id_tool_category = :idToolCategory ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())) {
            sb.append(" and de.id_department = :idDepartment ");
        }
        if (ObjectUtils.isNotEmpty(request.getStatusUse())){
            sb.append(" and tol.status_use = : statusUse ");
        }
        if (StringUtils.isNotBlank(request.getSortBy())) {
            sb.append("ORDER BY ");
            if (request.getSortBy().equals("nameTool")) {
                sb.append(" tol.name ");
            }
            if (request.getSortBy().equals("nameDepartment")) {
                sb.append(" de.name ");
            }
            sb.append(" ").append(request.getSortOrder());
        } else {
            sb.append(" ORDER BY tol.id_tool desc ");
        }
    }


    private long countFindAllChildrenToolDtos(FindAllToolRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0)   " +
                " from tool tol     " +
                "     left join tool_categories tolca on tol.id_tool_category = tolca.id_tool_category     " +
                "     left join department de on tol.id_department = de.id_department     " +
                "     left join location lo on de.id_department = lo.id_department   " +
                "     inner join (select id_tool from tool where salt = :salt) tolParent   " +
                "         on tol.parent = tolParent.id_tool   " +
                " where 1 = 1 ");
        setConditionFindAllChildrenToolDto(sb, request);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllChildrenToolDto(query, request);
        return ValueUtil.getIntegerByObject(query.getSingleResult());
    }

    private void setParameterFindAllChildrenToolDto(Query query, FindAllToolRequest request) {
        query.setParameter("salt", request.getSalt());
        if (StringUtils.isNotBlank(request.getCodeTool())){
            query.setParameter("codeTool", request.getCodeTool());
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

    private void setConditionFindAllChildrenToolDto(StringBuilder sb, FindAllToolRequest request) {
        if (StringUtils.isNotBlank(request.getCodeTool())){
            sb.append(" and (tol.code_tool REGEXP :codeTool ) ");
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
                "       id_department, id_location, id_user_use, year_use, price " +
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
                tool.setPrice(ValueUtil.getStringByObject(obj[25]));
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
                "       id_department, id_location, id_user_use, year_use, price  " +
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
        tool.setPrice(ValueUtil.getStringByObject(obj[25]));
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
                "       id_department, id_location, id_user_use, year_use, price   " +
                "from tool where tool.parent = :idToolParent ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idToolParent", idToolParent);
        List<Object[]> result = query.getResultList();
        List<Tool> tools = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                tools.add(writeDataTool(obj));
            }
        }
        return tools;
    }

    @Override
    public List<Tool> findAllToolBySaltsAndIsIncrease(List<String> listSalts, Integer isIncrease) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select id_tool, name, code_tool, salt,  " +
                "       id_tool_category, time_created,  " +
                "       time_modified, id_user_created,  " +
                "       id_user_modified, value, quantity,  " +
                "       is_increase, is_decrease, quantity_increase_current,  " +
                "       quantity_decrease_current, id_process_current,  " +
                "       status_process_current, id_type_process_current,  " +
                "       id_department_original, status_use,  " +
                "       parent, id_department, id_location,  " +
                "       id_user_use, year_use, price " +
                "from tool " +
                "where tool.salt in (:salts) " +
                "and tool.is_increase = :isIncrease ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("salts", listSalts);
        query.setParameter("isIncrease", isIncrease);
        List<Object[]> result = query.getResultList();
        List<Tool> tools = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                tools.add(writeDataTool(obj));
            }
        }
        return tools;
    }

    private Tool writeDataTool(Object[] obj) {
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
        tool.setPrice(ValueUtil.getStringByObject(obj[25]));
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
    private long countFindAllToolToIncreaseDtos(FindAllToolToIncreaseRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0) count  " +
                "from tool tol  " +
                "    left join tool_categories tolca on tol.id_tool_category = tolca.id_tool_category  " +
                "    left join department de on tol.id_department = de.id_department  " +
                "    left join location lo on de.id_department = lo.id_department  " +
                "where tol.id_department_original in (:idsDepartmentOriginal)  " +
                " and (tol.status_process_current != :statusProcessCurrent or tol.status_process_current is null) ");
        setConditionFindAllToolToIncreaseDto(sb, request);
        Query query  = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllToolToIncreaseDto(query, request);
        return ValueUtil.getIntegerByObject(query.getSingleResult());
    }
    private long countFindAllToolToDecreaseDtos(FindAllToolToDecreaseRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0) count  " +
                "from tool tol  " +
                "    left join tool_categories tolca on tol.id_tool_category = tolca.id_tool_category  " +
                "    left join department de on tol.id_department = de.id_department  " +
                "    left join location lo on de.id_department = lo.id_department  " +
                "where tol.id_department_original in (:idsDepartmentOriginal)  " +
                " and (tol.status_process_current != :statusProcessCurrent or tol.status_process_current is null) ");
        setConditionFindAllToolToDecreaseDto(sb, request);
        Query query  = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllToolToDecreaseDto(query, request);
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
        toolDto.setPrice(ValueUtil.getStringByObject(obj[28]));
        return toolDto;
    }

    private void setParameterFindAllToolDto(Query query, FindAllToolRequest request) {
        query.setParameter("idsDepartmentOriginal", request.getIdsDepartmentOriginal());
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
