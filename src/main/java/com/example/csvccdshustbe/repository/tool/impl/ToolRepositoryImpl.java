package com.example.csvccdshustbe.repository.tool.impl;

import com.example.csvccdshustbe.dto.tool.AllocateToolDto;
import com.example.csvccdshustbe.dto.tool.FindDetailsToolDto;
import com.example.csvccdshustbe.dto.tool.ToolDto;
import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.entity.Tool;
import com.example.csvccdshustbe.repository.tool.ToolRepositoryCustom;
import com.example.csvccdshustbe.request.tool.FindAllToolRequest;
import com.example.csvccdshustbe.request.tool.FindAllToolToDecreaseRequest;
import com.example.csvccdshustbe.request.tool.FindAllToolToIncreaseRequest;
import com.example.csvccdshustbe.request.tool.FindAllToolToInventoryRequest;
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

    @Transactional
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
                "       de.code, de.name, lo.name, tol.price,tolca.name " +
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
                toolDtos.add(writeDataToolFindAllDtos(obj));
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
                "        de.code, de.name, lo.name, tol.price   " +
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
                "          id_department, id_location, id_user_use, year_use, price " +
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
        query.setParameter("isIncrease", Constants.TOOL_IS_INCREASED);
        query.setParameter("isDecrease", Constants.TOOL_IS_DECREASED);
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
        sb.append("select tool.name,tool.code_tool,tool.id_tool_category,  " +
                "        tool_categories.code_tool,tool_categories.name,  " +
                "        tool.parent,tool.quantity,tool.value, " +
                "        tool.id_department,department.name,tool.id_location,  " +
                "        location.name,tool.is_increase,tool.is_decrease, " +
                "        tool.status_use,tool.id_user_use,csvc_user.full_name,  " +
                "        tool.id_tool,tool.year_use, tool.price " +
                "  from tool   " +
                "left join tool_categories on tool.id_tool_category=tool_categories.id_tool_category   " +
                "left join department on tool.id_department=department.id_department   " +
                "left join location on tool.id_location=location.id_location   " +
                "left join csvc_user on tool.id_user_use = csvc_user.id_user where  tool.salt =:saltTool ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("saltTool", saltTool);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                FindDetailsToolDto findDetailsToolDto = new FindDetailsToolDto();
                findDetailsToolDto.setNameTool(ValueUtil.getStringByObject(obj[0]));
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
                findDetailsToolDto.setIdTool(ValueUtil.getIntegerByObject(obj[17]));
                findDetailsToolDto.setYearUse(ValueUtil.getStringByObject(obj[18]));
                findDetailsToolDto.setPrice(ValueUtil.getStringByObject(obj[19]));
                return Optional.of(findDetailsToolDto);
            }
        }
        return Optional.empty();
    }

    @Override
    public Integer countToolIncreasedNotDecreasedByIdsTool(List<Integer> idsTool) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(tool.id_tool) count " +
                "from tool " +
                "where tool.id_tool in (:idsTool) " +
                "and (is_increase = :isIncreased and is_decrease = :isNotDecreased) ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idsTool", idsTool);
        query.setParameter("isIncreased", Constants.TOOL_IS_INCREASED);
        query.setParameter("isNotDecreased", Constants.TOOL_IS_NOT_DECREASE);
        return ValueUtil.getIntegerByObject(query.getSingleResult());
    }

    @Override
    public List<Tool> findAllToolByIdsTool(List<Integer> idsTool) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select id_tool, name, code_tool,   " +
                "       salt, id_tool_category,   " +
                "       time_created, time_modified,   " +
                "       id_user_created, id_user_modified,   " +
                "       value, quantity, is_increase,   " +
                "       is_decrease, quantity_increase_current,   " +
                "       quantity_decrease_current, id_process_current,  " +
                "       status_process_current, id_type_process_current,   " +
                "       id_department_original, status_use, parent,  " +
                "       id_department, id_location, id_user_use, year_use, price  " +
                "from tool where id_tool in (:idsTool) ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idsTool", idsTool);
        List<Tool> tools = new ArrayList<>();
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                tools.add(writeDataTool(obj));
            }
        }
        return tools;
    }


    @Transactional
    @Modifying
    @Override
    public void updateStatusProcessCurrentAndIsIncrease(Integer idProcess, Integer status) {
        StringBuilder sb = new StringBuilder();
        sb.append("update tool tl             " +
                "   inner join tool_process tp on tl.id_tool = tp.id_tool      " +
                "   set tl.status_process_current = :statusProcessCurrent,     " +
                "       tl.is_increase = :isIncrease " +
                "where tp.id_process = :idProcess  ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idProcess", idProcess);
        query.setParameter("isIncrease", Constants.TOOL_IS_INCREASED);
        query.setParameter("statusProcessCurrent", status);
        query.executeUpdate();
    }

    @Override
    public void updateStatusProcessCurrentAndIsDecrease(Integer idProcess, Integer status) {
        StringBuilder sb = new StringBuilder();
        sb.append("update tool tl " +
                "       inner join tool_process tp on tl.id_tool = tp.id_tool " +
                "       set tl.status_process_current = :statusProcessCurrent,  " +
                "           tl.is_decrease = (case when tl.quantity_decrease_current = tl.quantity_increase_current  " +
                "               then :isDecreased else :isDecreasing end)  " +
                "where tp.id_process = :idProcess ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idProcess", idProcess);
        query.setParameter("isDecreased", Constants.TOOL_IS_DECREASED);
        query.setParameter("isDecreasing", Constants.TOOL_IS_DECREASING);
        query.setParameter("statusProcessCurrent", status);
        query.executeUpdate();
    }

    @Override
    public List<Integer> getAllIdsToolParentByIdProcess(Integer idProcess) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select distinct toolChildren.parent     " +
                "from tool toolChildren     " +
                "         inner join tool_process tp on toolChildren.id_tool = tp.id_tool     " +
                "         inner join process pr on tp.id_process = pr.id_process     " +
                "where pr.id_process = :idProcess ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idProcess", idProcess);
        List<Object> result = query.getResultList();
        List<Integer> idsTool = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object obj : result) {
                idsTool.add(ValueUtil.getIntegerByObject(obj));
            }
        }
        return idsTool;
    }

    @Transactional
    @Modifying
    @Override
    public void updateIsIncreaseAndQuantityIncreaseCurrentByIdsTool(List<Integer> idsToolParent) {
        StringBuilder sb = new StringBuilder();
        sb.append("update tool tl  " +
                "         inner join  " +
                "     (select result.id_tool,  " +
                "            case when totalSubChildrenTool = sumIncreased then :isIncreased else :isIncreasing end isIncreaseCurrent,  " +
                "            totalQuantityIncreaseCurrent  " +
                "     from (select toolParent.id_tool,  " +
                "            count(toolChildren.id_tool) totalSubChildrenTool,  " +
                "            sum(case when toolChildren.is_increase = :isIncreased then 1 else 0 end) sumIncreased,  " +
                "            sum(toolChildren.quantity_increase_current) totalQuantityIncreaseCurrent  " +
                "     from tool toolParent  " +
                "         inner join tool toolChildren on toolParent.id_tool = toolChildren.id_tool  " +
                "     where toolParent.id_tool in (:idsTool)  " +
                "     group by toolParent.id_tool) result) result on tl.id_tool = result.id_tool  " +
                "         set is_increase = result.isIncreaseCurrent,  " +
                "             quantity_increase_current = totalQuantityIncreaseCurrent  " +
                "     where 1 = 1 ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idsTool", idsToolParent);
        query.setParameter("isIncreased", Constants.TOOL_IS_INCREASED);
        query.setParameter("isIncreasing", Constants.TOOL_IS_INCREASING);
        query.executeUpdate();
    }

    @Transactional
    @Modifying
    @Override
    public void updateIsDecreaseAndQuantityDecreaseCurrentByIdsTool(List<Integer> idsToolParent) {
        StringBuilder sb = new StringBuilder();
        sb.append(" update tool tl     " +
                "    inner join     " +
                "(select result.id_tool,     " +
                "       case when totalSubChildrenTool = sumDecreased then :isDecreased else :isDecreasing end isDecreaseCurrent,     " +
                "       totalQuantityDecreaseCurrent     " +
                "from (select toolParent.id_tool,     " +
                "       count(toolChildren.id_tool) totalSubChildrenTool,     " +
                "       sum(case when toolChildren.is_decrease = :isDecreased then 1 else 0 end) sumDecreased,     " +
                "       sum(toolChildren.quantity_decrease_current) totalQuantityDecreaseCurrent     " +
                "from tool toolParent     " +
                "    inner join tool toolChildren on toolParent.id_tool = toolChildren.id_tool     " +
                "where toolParent.id_tool in (:idsTool)     " +
                "group by toolParent.id_tool) result) result on tl.id_tool = result.id_tool     " +
                "    set is_decrease = result.isDecreaseCurrent,     " +
                "        quantity_decrease_current = totalQuantityDecreaseCurrent     " +
                "where 1 = 1 ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idsTool", idsToolParent);
        query.setParameter("isDecreased", Constants.TOOL_IS_DECREASED);
        query.setParameter("isDecreasing", Constants.TOOL_IS_DECREASING);
        query.executeUpdate();
    }


    @Transactional
    @Modifying
    @Override
    public void updateToolIsIncreaseWhenNotApproved(Integer idProcessCurrent, Integer status) {
        StringBuilder sb = new StringBuilder();
        sb.append("update tool  " +
                "    inner join tool_process on tool.id_process_current = tool_process.id_tool_process  " +
                "set tool.status_process_current = :statusProcessCurrent,  " +
                "    tool.quantity_increase_current =  :toolDefaultQuantityIncreaseCurrent " +
                "where tool_process.id_tool_process = :idProcessCurrent ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("toolDefaultQuantityIncreaseCurrent", Constants.TOOL_DEFAULT_QUANTITY_INCREASE_CURRENT);
        query.setParameter("statusProcessCurrent", status);
        query.setParameter("idProcessCurrent", idProcessCurrent);
        query.executeUpdate();
    }

    @Transactional
    @Modifying
    @Override
    public void updateToolIsDecreaseWhenNotApproved(Integer idProcessCurrent, Integer status) {
        StringBuilder sb = new StringBuilder();
        sb.append("update tool    " +
                "      inner join tool_process on tool.id_process_current = tool_process.id_tool_process " +
                "set tool.status_process_current = :statusProcessCurrent, " +
                "    tool.quantity_decrease_current = tool.quantity_decrease_current - tool_process.quantity " +
                "where tool_process.id_tool_process = :idProcessCurrent ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("statusProcessCurrent", status);
        query.setParameter("idProcessCurrent", idProcessCurrent);
        query.executeUpdate();
    }

    @Override
    public Integer countToolIsNotIncreaseOrDecreaseOrPendingByIdsTool(List<Integer> idsTool) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(tool.id_tool) " +
                "from tool " +
                "where tool.id_tool in (:idsTool) " +
                "and (tool.is_increase = :isNotIncrease " +
                "  or tool.is_decrease = :isDecrease " +
                "  or tool.status_process_current = :statusPending) ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idsTool", idsTool);
        query.setParameter("isNotIncrease", Constants.TOOL_IS_NOT_INCREASE);
        query.setParameter("isDecrease", Constants.TOOL_IS_DECREASED);
        query.setParameter("statusPending", Constants.STATUS_PENDING_PROCESS);
        return ValueUtil.getIntegerByObject(query.getSingleResult());
    }

    @Override
    public Page<ToolDto> findAllToolDtoToInventory(FindAllToolToInventoryRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append("select tl.id_tool, tl.name, tl.code_tool,      " +
                "         tlc.id_tool_category, tlc.code_tool, tlc.name,      " +
                "         tl.time_created, tl.time_modified, tl.value,      " +
                "         tl.quantity, tl.is_increase, tl.is_decrease,      " +
                "         tl.quantity_increase_current, tl.quantity_decrease_current,      " +
                "         tl.id_process_current, tl.status_process_current,      " +
                "         tl.status_use, tl.parent,      " +
                "         de.id_department, de.code, de.name,      " +
                "         lo.id_location, lo.name,      " +
                "         cu.id_user, cu.user_name,      " +
                "         tl.year_use, tl.price      " +
                "  from tool tl      " +
                "      left join department de on tl.id_department = de.id_department      " +
                "      left join location lo on tl.id_location = lo.id_location      " +
                "      left join tool_categories tlc on tl.id_tool_category = tlc.id_tool_category      " +
                "      left join csvc_user cu on cu.id_user = tl.id_user_use      " +
                "  where tl.id_department_original in (:idsDepartmentOriginal)  " +
                "  and tl.status_process_current != :statusProcessCurrent  " +
                "  and tl.is_increase = :isIncrease  " +
                "  and tl.is_decrease != :isDecrease  " +
                "  and tl.parent is not null  ");
        setConditionFindAllToolDtoToInventory(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllToolDtoToInventory(request, query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<ToolDto> toolDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                toolDtos.add(writeDataToolDtoToInventory(obj));
            }
        }
        return new PageImpl<>(toolDtos, pageable, countFindAllToolDtoToInventory(request));
    }

    private long countFindAllToolDtoToInventory(FindAllToolToInventoryRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0) count   " +
                "  from tool tl      " +
                "      left join department de on tl.id_department = de.id_department      " +
                "      left join location lo on tl.id_location = lo.id_location      " +
                "      left join tool_categories tlc on tl.id_tool_category = tlc.id_tool_category      " +
                "      left join csvc_user cu on cu.id_user = tl.id_user_use      " +
                "  where tl.id_department_original in (:idsDepartmentOriginal)  " +
                "  and tl.status_process_current != :statusProcessCurrent  " +
                "  and tl.is_increase = :isIncrease  " +
                "  and tl.is_decrease != :isDecrease  " +
                "  and tl.parent is not null  ");
        setConditionFindAllToolDtoToInventory(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllToolDtoToInventory(request, query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }

    private ToolDto writeDataToolDtoToInventory(Object[] obj) {
        ToolDto toolDto = new ToolDto();
        toolDto.setIdTool(ValueUtil.getIntegerByObject(obj[0]));
        toolDto.setName(ValueUtil.getStringByObject(obj[1]));
        toolDto.setCodeTool(ValueUtil.getStringByObject(obj[2]));
        toolDto.setIdToolCategory(ValueUtil.getIntegerByObject(obj[3]));
        toolDto.setCodeToolCategory(ValueUtil.getStringByObject(obj[4]));
        toolDto.setNameToolCategory(ValueUtil.getStringByObject(obj[5]));
        toolDto.setTimeCreated(ValueUtil.getStringByObject(obj[6]));
        toolDto.setTimeModified(ValueUtil.getStringByObject(obj[7]));
        toolDto.setValue(ValueUtil.getStringByObject(obj[8]));
        toolDto.setQuantity(ValueUtil.getIntegerByObject(obj[9]));
        toolDto.setIsIncrease(ValueUtil.getIntegerByObject(obj[10]));
        toolDto.setIsDecrease(ValueUtil.getIntegerByObject(obj[11]));
        toolDto.setQuantityIncreaseCurrent(ValueUtil.getIntegerByObject(obj[12]));
        toolDto.setQuantityDecreaseCurrent(ValueUtil.getIntegerByObject(obj[13]));
        toolDto.setIdProcessCurrent(ValueUtil.getIntegerByObject(obj[14]));
        toolDto.setStatusProcessCurrent(ValueUtil.getIntegerByObject(obj[15]));
        toolDto.setStatusUse(ValueUtil.getIntegerByObject(obj[16]));
        toolDto.setParent(ValueUtil.getIntegerByObject(obj[17]));
        toolDto.setIdDepartment(ValueUtil.getIntegerByObject(obj[18]));
        toolDto.setCodeDepartment(ValueUtil.getStringByObject(obj[19]));
        toolDto.setNameDepartment(ValueUtil.getStringByObject(obj[20]));
        toolDto.setIdLocation(ValueUtil.getIntegerByObject(obj[21]));
        toolDto.setNameLocation(ValueUtil.getStringByObject(obj[22]));
        toolDto.setIdUserUse(ValueUtil.getIntegerByObject(obj[23]));
        toolDto.setUserName(ValueUtil.getStringByObject(obj[24]));
        toolDto.setYearUse(ValueUtil.getStringByObject(obj[25]));
        toolDto.setPrice(ValueUtil.getStringByObject(obj[26]));
        return toolDto;
    }

    private void setParameterFindAllToolDtoToInventory(FindAllToolToInventoryRequest request, Query query) {
        query.setParameter("idsDepartmentOriginal", request.getIdsDepartment());
        query.setParameter("statusProcessCurrent", Constants.STATUS_PENDING_PROCESS);
        query.setParameter("isIncrease", Constants.TOOL_IS_INCREASED);
        query.setParameter("isDecrease", Constants.TOOL_IS_DECREASED);
        if (StringUtils.isNotBlank(request.getCodeTool())){
            query.setParameter("codeTool", request.getCodeTool());
        }
        if (StringUtils.isNotBlank(request.getNameTool())){
            query.setParameter("nameTool", request.getNameTool());
        }
        if (ObjectUtils.isNotEmpty(request.getIdToolCategory())) {
            query.setParameter("idToolCategory", request.getIdToolCategory());
        }
        if (StringUtils.isNotBlank(request.getNameToolCategory())){
            query.setParameter("nameToolCategory", request.getNameToolCategory());
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())) {
            query.setParameter("idDepartment", request.getIdDepartment());
        }
        if (StringUtils.isNotBlank(request.getNameDepartment())){
            query.setParameter("nameDepartment", request.getNameDepartment());
        }
        if (ObjectUtils.isNotEmpty(request.getIdLocation())){
            query.setParameter("idLocation", request.getIdLocation());
        }
        if (StringUtils.isNotBlank(request.getNameLocation())){
            query.setParameter("nameLocation", request.getNameLocation());
        }
        if (StringUtils.isNotBlank(request.getUserName())){
            query.setParameter("userName", request.getUserName());
        }
        if (ObjectUtils.isNotEmpty(request.getStatusUse())){
            query.setParameter("statusUse", request.getStatusUse());
        }
    }

    private void setConditionFindAllToolDtoToInventory(FindAllToolToInventoryRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getCodeTool())){
            sb.append(" and (tl.code_tool REGEXP :codeTool ) ");
        }
        if (StringUtils.isNotBlank(request.getNameTool())){
            sb.append(" and (tl.name REGEXP :nameTool )  ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdToolCategory())) {
            sb.append(" and tlc.id_tool_category = :idToolCategory ");
        }
        if (StringUtils.isNotBlank(request.getNameToolCategory())){
            sb.append(" and (tlc.name REGEXP :nameToolCategory) ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())) {
            sb.append(" and de.id_department = :idDepartment ");
        }
        if (StringUtils.isNotBlank(request.getNameDepartment())){
            sb.append(" and (de.name REGEXP :nameDepartment) ");
        }
        if (ObjectUtils.isNotEmpty(request.getIdLocation())){
            sb.append(" and lo.id_location = :idLocation ");
        }
        if (StringUtils.isNotBlank(request.getNameLocation())){
            sb.append(" and (lo.name REGEXP :nameLocation) ");
        }
        if (StringUtils.isNotBlank(request.getUserName())){
            sb.append(" and (cu.user_name REGEXP :userName) ");
        }
        if (ObjectUtils.isNotEmpty(request.getStatusUse())){
            sb.append(" and tl.status_use = :statusUse ");
        }
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
                "       de.code, de.name, lo.name, tol.price, tolca.name " +
                "from tool tol " +
                "    left join tool_categories tolca on tol.id_tool_category = tolca.id_tool_category " +
                "    left join department de on tol.id_department = de.id_department " +
                "    left join location lo on tol.id_location = lo.id_location " +
                "where tol.id_department_original in (:idsDepartmentOriginal)  " +
                "  and (tol.status_process_current != :statusProcessCurrent or tol.status_process_current is null) " +
                "  and tol.parent is not null " +
                "  and tol.is_increase = :isIncrease ");
        setConditionFindAllToolToIncreaseDto(sb, request);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllToolToIncreaseDto(query, request);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<ToolDto> toolDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                toolDtos.add(writeDataToolInDocumentDtos(obj));
            }
        }
        return new PageImpl<>(toolDtos, pageable, countFindAllToolToIncreaseDtos(request));
    }

    @Override
    public Page<ToolDto> findAllToolDtoToDecrease(FindAllToolToDecreaseRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append("select tol.id_tool, tol.name, tol.code_tool, tol.salt, " +
                "        tol.id_tool_category, tol.time_created, tol.time_modified, " +
                "        tol.id_user_created, tol.id_user_modified, tol.value, " +
                "        tol.quantity, tol.is_increase, tol.is_decrease, tol.quantity_increase_current, " +
                "        tol.quantity_decrease_current, tol.id_process_current, tol.status_process_current, " +
                "        tol.id_type_process_current, tol.id_department_original, tol.status_use,  " +
                "        tol.parent, tol.id_department, tol.id_location,  " +
                "        tol.id_user_use, tol.year_use,  " +
                "        de.code, de.name, lo.name, tol.price,tolca.name  " +
                " from tool tol  " +
                "     left join tool_categories tolca on tol.id_tool_category = tolca.id_tool_category " +
                "     left join department de on tol.id_department = de.id_department  " +
                "     left join location lo on tol.id_location = lo.id_location  " +
                "     left join type_process tp on tol.id_type_process_current = tp.id_type_process " +
                " where tol.id_department_original in (:idsDepartmentOriginal)  " +
                " and tol.status_process_current != :statusProcessCurrent  " +
                " and tol.quantity_decrease_current < tol.quantity_increase_current " +
                " and tol.parent is not null " +
                " and tol.is_increase = :isIncrease and tol.is_decrease != :isDecrease ");
        setConditionFindAllToolToDecreaseDto(sb, request);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllToolToDecreaseDto(query, request);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<ToolDto> toolDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                toolDtos.add(writeDataToolInDocumentDtos(obj));
            }
        }
        return new PageImpl<>(toolDtos, pageable, countFindAllToolToDecreaseDtos(request));
    }

    @Override
    @Transactional
    public List<AllocateToolDto> findListAllocateToolByIdToolParent(Integer idToolParent) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select tool.id_department,department.name,tool.id_location,  " +
                "       location.name,csvc_user.user_name,csvc_user.full_name,  " +
                "       tool.quantity,tool.status_use  " +
                "         from tool  " +
                "         left join department on tool.id_department=department.id_department  " +
                "         left join location on tool.id_location=location.id_location  " +
                "         left join csvc_user on tool.id_user_use=csvc_user.id_user  " +
                "         where tool.parent = :idToolParent ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idToolParent", idToolParent);
        List<Object[]> result = query.getResultList();
        List<AllocateToolDto> allocateToolDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(query.getResultList())){
            for (Object[] obj : result){
                AllocateToolDto dto = new AllocateToolDto();
                dto.setIdDepartment(ValueUtil.getIntegerByObject(obj[0]));
                dto.setNameDepartment(ValueUtil.getStringByObject(obj[1]));
                dto.setIdLocation(ValueUtil.getIntegerByObject(obj[2]));
                dto.setNameLocation(ValueUtil.getStringByObject(obj[3]));
                dto.setUserName(ValueUtil.getStringByObject(obj[4]));
                dto.setFullName(ValueUtil.getStringByObject(obj[5]));
                dto.setQuantity(ValueUtil.getIntegerByObject(obj[6]));
                dto.setStatusUse(ValueUtil.getIntegerByObject(obj[7]));
                allocateToolDtos.add(dto);
            }
        }
        return allocateToolDtos;
    }

    private void setParameterFindAllToolToDecreaseDto(Query query, FindAllToolToDecreaseRequest request) {
        query.setParameter("idsDepartmentOriginal", request.getIdsDepartmentOriginal());
        query.setParameter("isIncrease", Constants.TOOL_IS_INCREASED);
        query.setParameter("isDecrease", Constants.TOOL_IS_DECREASED);
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
        query.setParameter("isIncrease", Constants.TOOL_IS_NOT_INCREASE);
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
        sb.append("select id_tool, name, code_tool, salt,   " +
                "         id_tool_category, time_created,     " +
                "         time_modified, id_user_created,  " +
                "         id_user_modified, value, quantity, " +
                "         is_increase, is_decrease, quantity_increase_current, " +
                "         quantity_decrease_current, id_process_current,   " +
                "         status_process_current, id_type_process_current, " +
                "         id_department_original, status_use, parent, " +
                "         id_department, id_location, id_user_use, year_use, price " +
                "  from tool     " +
                "  where id_department_original = :idDepartmentOriginal    " +
                "  order by tool.id_tool desc limit 1  ");
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

    @Transactional
    @Override
    public Optional<Tool> findToolBySaltTool(String salt) {
        StringBuilder sb = new StringBuilder();
        sb.append("select id_tool, name, code_tool, salt,    " +
                "         id_tool_category, time_created,      " +
                "         time_modified, id_user_created,   " +
                "         id_user_modified, value, quantity, " +
                "         is_increase, is_decrease, quantity_increase_current, " +
                "         quantity_decrease_current, id_process_current,    " +
                "         status_process_current, id_type_process_current, " +
                "         id_department_original, status_use, parent, " +
                "         id_department, id_location, id_user_use, year_use, price " +
                "  from tool where tool.salt = :salt  ");
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
                "       id_department, id_location, id_user_use, year_use, price  " +
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
                "       id_user_use, year_use, price, quantity_inventory_current " +
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
                "    left join location lo on tol.id_location = lo.id_location  " +
                "where tol.id_department_original in (:idsDepartmentOriginal)  " +
                " and (tol.status_process_current != :statusProcessCurrent or tol.status_process_current is null) " +
                " and tol.parent is not null " +
                " and tol.is_increase = :isIncrease ");
        setConditionFindAllToolToIncreaseDto(sb, request);
        Query query  = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllToolToIncreaseDto(query, request);
        return ValueUtil.getIntegerByObject(query.getSingleResult());
    }
    private long countFindAllToolToDecreaseDtos(FindAllToolToDecreaseRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0) count  " +
                "  from tool tol  " +
                "      left join tool_categories tolca on tol.id_tool_category = tolca.id_tool_category  " +
                "      left join department de on tol.id_department = de.id_department  " +
                "      left join location lo on tol.id_location = lo.id_location  " +
                "      left join type_process tp on tol.id_type_process_current = tp.id_type_process  " +
                "  where tol.id_department_original in (:idsDepartmentOriginal)  " +
                "  and ( (tol.status_process_current != :statusProcessCurrent and  tp.code != :codeToolDecrease) or  " +
                "      (tol.quantity_decrease_current < tol.quantity_increase_current and tp.code = :codeToolDecrease))  " +
                "  and tol.parent is not null  " +
                "  and tol.is_increase = :isIncrease and tol.is_decrease != :isDecrease ");
        setConditionFindAllToolToDecreaseDto(sb, request);
        Query query  = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllToolToDecreaseDto(query, request);
        return ValueUtil.getIntegerByObject(query.getSingleResult());
    }
    private ToolDto writeDataToolFindAllDtos(Object[] obj) {
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
        toolDto.setNameToolCategory(ValueUtil.getStringByObject(obj[29]));
        return toolDto;
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

    private ToolDto writeDataToolInDocumentDtos(Object[] obj) {
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
        toolDto.setNameToolCategory(ValueUtil.getStringByObject(obj[29]));
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
