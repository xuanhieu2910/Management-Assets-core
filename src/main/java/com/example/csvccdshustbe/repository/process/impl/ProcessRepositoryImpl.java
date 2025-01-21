package com.example.csvccdshustbe.repository.process.impl;

import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.entity.Process;
import com.example.csvccdshustbe.repository.process.ProcessRepositoryCustom;
import com.example.csvccdshustbe.request.process.FindAllProcessBeAssignedDocumentInventoryRequest;
import com.example.csvccdshustbe.request.process.FindAllProcessBeAssignedRequest;
import com.example.csvccdshustbe.response.process.*;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.DateUtil;
import com.example.csvccdshustbe.utility.PageUtils;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;

import java.util.*;

public class ProcessRepositoryImpl implements ProcessRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<Process> findProcessByIdProcess(Integer idProcess) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select pro.id_process, pro.id_type_process, pro.name,   " +
                "       pro.status, pro.time_created, pro.time_modified,   " +
                "       pro.id_user_created, pro.id_user_modified, pro.id_department   " +
                "from process pro    " +
                "where pro.id_process = :idProcess ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idProcess", idProcess);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                Process process = new Process();
                process.setIdProcess(ValueUtil.getIntegerByObject(obj[0]));
                process.setIdTypeProcess(ValueUtil.getIntegerByObject(obj[1]));
                process.setName(ValueUtil.getStringByObject(obj[2]));
                process.setStatus(ValueUtil.getIntegerByObject(obj[3]));
                process.setTimeCreated(ValueUtil.getStringByObject(obj[4]));
                process.setTimeModified(ValueUtil.getStringByObject(obj[5]));
                process.setIdUserCreated(ValueUtil.getIntegerByObject(obj[6]));
                process.setIdUserModified(ValueUtil.getIntegerByObject(obj[7]));
                process.setIdDepartment(ValueUtil.getIntegerByObject(obj[8]));
                return Optional.of(process);
            }
        }
        return Optional.empty();
    }

    @Override
    public Page<FindAllProcessBeAssignedResponse> findAllProcessBeAssigned(FindAllProcessBeAssignedRequest request,
                                                                           Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select rsh.id_request_stake_holder, pr.id_process, dc.id_document, dc.code codeDocument,  " +
                "       tp.name typeProcess, dc.description, pr.time_created,  " +
                "       pr.time_modified, csvcUserCreate.user_name, csvcUserCreate.full_name,  " +
                "       dc.time_created, dc.time_modified, dc.time_increase, dc.time_document,  " +
                "       st.id_state, ts.id_type_state, ts.code, st.status  " +
                "from request_stake_holder rsh  " +
                "    inner join request rq on rsh.id_request = rq.id_request  " +
                "    inner join state st on rq.id_state = st.id_state  " +
                "    inner join type_state ts on st.id_type_state = ts.id_type_state  " +
                "    inner join process pr on st.id_process = pr.id_process  " +
                "    inner join type_process tp on pr.id_type_process = tp.id_type_process  " +
                "    inner join document dc on pr.id_process = dc.id_process  " +
                "    inner join csvc_user csvcUserCreate on pr.id_user_created = csvcUserCreate.id_user  " +
                "    inner join csvc_user csvcUser on rsh.id_user = csvcUser.id_user  " +
                "where rsh.status = :statusPending  " +
                "and csvcUser.id_user = :idUser ");
        setConditionFindAllProcessBeAssigned(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllProcessBeAssigned(request, query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<FindAllProcessBeAssignedResponse> responses = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                FindAllProcessBeAssignedResponse response = new FindAllProcessBeAssignedResponse();
                response.setIdRequestStakeHolder(ValueUtil.getIntegerByObject(obj[0]));
                response.setIdProcess(ValueUtil.getIntegerByObject(obj[1]));
                response.setIdDocument(ValueUtil.getIntegerByObject(obj[2]));
                response.setCodeDocument(ValueUtil.getStringByObject(obj[3]));
                response.setTypeProcess(ValueUtil.getStringByObject(obj[4]));
                response.setDescription(ValueUtil.getStringByObject(obj[5]));
                response.setTimeCreatedProcess(DateUtil.formatToPattern(new Date(ValueUtil.getLongByObject(obj[6])), DateUtil.DATE_FORMAT));
                response.setTimeModifiedProcess(DateUtil.formatToPattern(new Date(ValueUtil.getLongByObject(obj[7])), DateUtil.DATE_FORMAT));
                response.setUserNameCreated(ValueUtil.getStringByObject(obj[8]));
                response.setFullNameCreated(ValueUtil.getStringByObject(obj[9]));
                response.setTimeCreatedDocument(DateUtil.formatToPattern(new Date(ValueUtil.getLongByObject(obj[10])), DateUtil.DATE_FORMAT));
                response.setTimeModifiedDocument(DateUtil.formatToPattern(new Date(ValueUtil.getLongByObject(obj[11])), DateUtil.DATE_FORMAT));
                response.setTimeIncrease(ValueUtil.getStringByObject(obj[12]));
                response.setTimeDocument(ValueUtil.getStringByObject(obj[13]));
                response.setIdState(ValueUtil.getIntegerByObject(obj[14]));
                response.setIdTypeState(ValueUtil.getIntegerByObject(obj[15]));
                response.setCodeTypeState(ValueUtil.getStringByObject(obj[16]));
                response.setStatusState(ValueUtil.getIntegerByObject(obj[17]));
                responses.add(response);
            }
        }
        return new PageImpl<>(responses, pageable, countFindAllProcessBeAssigned(request));
    }

    @Override
    public ProcessStatisticsIncreaseResponse getStatisticsIncrease() {
        StringBuilder sb = new StringBuilder();
        sb.append(" select sum(totalStatistic.countTotalPending) as countTotalPending,  " +
                "       sum(totalStatistic.countTotalBeApproved) as countTotalReject, " +
                "       sum(totalStatistic.countTotalReject) as countTotalBeApproved " +
                "from (select count(0) countTotalPending, 0 countTotalReject, 0 countTotalBeApproved " +
                "from process pr " +
                "     inner join department de on pr.id_department = de.id_department " +
                "     inner join type_process tp on pr.id_type_process = tp.id_type_process " +
                "where pr.status = :statusPending " +
                "and tp.code = :codeTypeProcess " +
                "and de.id_department in (:idsDepartmentOriginal) " +
                "union all " +
                "select 0 countTotalPending,count(0) countTotalReject, 0 countTotalBeApproved " +
                "from process pr " +
                "      inner join department de on pr.id_department = de.id_department " +
                "      inner join type_process tp on pr.id_type_process = tp.id_type_process " +
                "where pr.status = :statusReject " +
                "  and tp.code = :codeTypeProcess " +
                "  and de.id_department in (:idsDepartmentOriginal) " +
                "union all " +
                "select 0 countTotalPending, 0 countTotalReject, count(0) countTotalBeApproved " +
                "from request_stake_holder rsh " +
                "      inner join request rq on rsh.id_request = rq.id_request " +
                "      inner join state st on rq.id_state = st.id_state " +
                "      inner join process pr on st.id_process = pr.id_process " +
                "      inner join type_process tp on pr.id_type_process = tp.id_type_process " +
                "      inner join csvc_user csvcUser on rsh.id_user = csvcUser.id_user " +
                "where rsh.status = :statusRequestPending " +
                "  and tp.code = :codeTypeProcess " +
                "  and csvcUser.id_user = :idUser) totalStatistic ");
        CsvcUser csvcUser = (CsvcUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("statusPending", Constants.STATUS_PENDING_PROCESS);
        query.setParameter("statusReject", Constants.STATUS_FALSE_PROCESS);
        query.setParameter("statusRequestPending", Constants.STATUS_REQUEST_STAKE_HOLDER_PENDING);
        query.setParameter("codeTypeProcess", Constants.CODE_TYPE_PROCESS_INCREASE);
        query.setParameter("idsDepartmentOriginal", csvcUser.getIdsDepartmentCurrent());
        query.setParameter("idUser", csvcUser.getIdUser());
        List<Object[]> result = query.getResultList();
        ProcessStatisticsIncreaseResponse response = new ProcessStatisticsIncreaseResponse();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                response.setTotalIncreasePendingApproved(ValueUtil.getIntegerByObject(obj[0]));
                response.setTotalIncreasePendingBeApproved(ValueUtil.getIntegerByObject(obj[1]));
                response.setTotalIncreaseRejected(ValueUtil.getIntegerByObject(obj[2]));
            }
        }
        return response;
    }

    @Override
    public ProcessStatisticsDocumentInventoryResponse getStatisticsDocumentInventory() {
        StringBuilder sb = new StringBuilder();
        sb.append("select sum(totalStatistic.countTotalPending) as countTotalPending,  " +
                "       sum(totalStatistic.countTotalReject)  as countTotalReject  " +
                "from (select count(0) countTotalPending, 0 countTotalReject  " +
                "      from process pr  " +
                "               inner join department de on pr.id_department = de.id_department  " +
                "               inner join type_process tp on pr.id_type_process = tp.id_type_process  " +
                "      where pr.status = :statusPending  " +
                "        and tp.code = :codeTypeProcess  " +
                "        and de.id_department in (:idsDepartmentOriginal)  " +
                "      union all  " +
                "      select 0 countTotalPending, count(0) countTotalReject  " +
                "      from process pr  " +
                "               inner join department de on pr.id_department = de.id_department  " +
                "               inner join type_process tp on pr.id_type_process = tp.id_type_process  " +
                "      where pr.status = :statusReject  " +
                "        and tp.code = :codeTypeProcess  " +
                "        and de.id_department in (:idsDepartmentOriginal)) totalStatistic ");
        CsvcUser csvcUser = (CsvcUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("statusPending", Constants.STATUS_PENDING_PROCESS);
        query.setParameter("statusReject", Constants.STATUS_FALSE_PROCESS);
        query.setParameter("codeTypeProcess", Constants.CODE_TYPE_PROCESS_DOCUMENT_INVENTORY);
        query.setParameter("idsDepartmentOriginal", csvcUser.getIdsDepartmentCurrent());
        List<Object[]> result = query.getResultList();
        ProcessStatisticsDocumentInventoryResponse response = new ProcessStatisticsDocumentInventoryResponse();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                response.setTotalInventoryPendingApproved(ValueUtil.getIntegerByObject(obj[0]));
                response.setTotalInventoryRejected(ValueUtil.getIntegerByObject(obj[1]));
            }
        }
        return response;
    }

    @Override
    public ProcessStatisticsDecreaseResponse getStatisticsDecrease() {
        StringBuilder sb = new StringBuilder();
        sb.append(" select sum(totalStatistic.countTotalPending) as countTotalPending,  " +
                "       sum(totalStatistic.countTotalBeApproved) as countTotalReject, " +
                "       sum(totalStatistic.countTotalReject) as countTotalBeApproved " +
                "from (select count(0) countTotalPending, 0 countTotalReject, 0 countTotalBeApproved " +
                "from process pr " +
                "     inner join department de on pr.id_department = de.id_department " +
                "     inner join type_process tp on pr.id_type_process = tp.id_type_process " +
                "where pr.status = :statusPending " +
                "and tp.code = :codeTypeProcess " +
                "and de.id_department in (:idsDepartmentOriginal) " +
                "union all " +
                "select 0 countTotalPending,count(0) countTotalReject, 0 countTotalBeApproved " +
                "from process pr " +
                "      inner join department de on pr.id_department = de.id_department " +
                "      inner join type_process tp on pr.id_type_process = tp.id_type_process " +
                "where pr.status = :statusReject " +
                "  and tp.code = :codeTypeProcess " +
                "  and de.id_department in (:idsDepartmentOriginal) " +
                "union all " +
                "select 0 countTotalPending, 0 countTotalReject, count(0) countTotalBeApproved " +
                "from request_stake_holder rsh " +
                "      inner join request rq on rsh.id_request = rq.id_request " +
                "      inner join state st on rq.id_state = st.id_state " +
                "      inner join process pr on st.id_process = pr.id_process " +
                "      inner join type_process tp on pr.id_type_process = tp.id_type_process " +
                "      inner join csvc_user csvcUser on rsh.id_user = csvcUser.id_user " +
                "where rsh.status = :statusRequestPending " +
                "  and tp.code = :codeTypeProcess " +
                "  and csvcUser.id_user = :idUser) totalStatistic ");
        CsvcUser csvcUser = (CsvcUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("statusPending", Constants.STATUS_PENDING_PROCESS);
        query.setParameter("statusReject", Constants.STATUS_FALSE_PROCESS);
        query.setParameter("statusRequestPending", Constants.STATUS_REQUEST_STAKE_HOLDER_PENDING);
        query.setParameter("codeTypeProcess", Constants.CODE_TYPE_PROCESS_DECREASE);
        query.setParameter("idsDepartmentOriginal", csvcUser.getIdsDepartmentCurrent());
        query.setParameter("idUser", csvcUser.getIdUser());
        List<Object[]> result = query.getResultList();
        ProcessStatisticsDecreaseResponse response = new ProcessStatisticsDecreaseResponse();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                response.setTotalDecreasePendingApproved(ValueUtil.getIntegerByObject(obj[0]));
                response.setTotalDecreasePendingBeApproved(ValueUtil.getIntegerByObject(obj[1]));
                response.setTotalDecreaseRejected(ValueUtil.getIntegerByObject(obj[2]));
            }
        }
        return response;
    }

    @Override
    public ProcessStatisticsChangeResponse getStatisticsChange() {
        StringBuilder sb = new StringBuilder();
        sb.append(" select sum(totalStatistic.countTotalPending) as countTotalPending,  " +
                "       sum(totalStatistic.countTotalBeApproved) as countTotalReject, " +
                "       sum(totalStatistic.countTotalReject) as countTotalBeApproved " +
                "from (select count(0) countTotalPending, 0 countTotalReject, 0 countTotalBeApproved " +
                "from process pr " +
                "     inner join department de on pr.id_department = de.id_department " +
                "     inner join type_process tp on pr.id_type_process = tp.id_type_process " +
                "where pr.status = :statusPending " +
                "and tp.code = :codeTypeProcess " +
                "and de.id_department in (:idsDepartmentOriginal) " +
                "union all " +
                "select 0 countTotalPending,count(0) countTotalReject, 0 countTotalBeApproved " +
                "from process pr " +
                "      inner join department de on pr.id_department = de.id_department " +
                "      inner join type_process tp on pr.id_type_process = tp.id_type_process " +
                "where pr.status = :statusReject " +
                "  and tp.code = :codeTypeProcess " +
                "  and de.id_department in (:idsDepartmentOriginal) " +
                "union all " +
                "select 0 countTotalPending, 0 countTotalReject, count(0) countTotalBeApproved " +
                "from request_stake_holder rsh " +
                "      inner join request rq on rsh.id_request = rq.id_request " +
                "      inner join state st on rq.id_state = st.id_state " +
                "      inner join process pr on st.id_process = pr.id_process " +
                "      inner join type_process tp on pr.id_type_process = tp.id_type_process " +
                "      inner join csvc_user csvcUser on rsh.id_user = csvcUser.id_user " +
                "where rsh.status = :statusRequestPending " +
                "  and tp.code = :codeTypeProcess " +
                "  and csvcUser.id_user = :idUser) totalStatistic ");
        CsvcUser csvcUser = (CsvcUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("statusPending", Constants.STATUS_PENDING_PROCESS);
        query.setParameter("statusReject", Constants.STATUS_FALSE_PROCESS);
        query.setParameter("statusRequestPending", Constants.STATUS_REQUEST_STAKE_HOLDER_PENDING);
        query.setParameter("codeTypeProcess", Constants.CODE_TYPE_PROCESS_CHANGE);
        query.setParameter("idsDepartmentOriginal", csvcUser.getIdsDepartmentCurrent());
        query.setParameter("idUser", csvcUser.getIdUser());
        List<Object[]> result = query.getResultList();
        ProcessStatisticsChangeResponse response = new ProcessStatisticsChangeResponse();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                response.setTotalChangePendingApproved(ValueUtil.getIntegerByObject(obj[0]));
                response.setTotalChangePendingBeApproved(ValueUtil.getIntegerByObject(obj[1]));
                response.setTotalChangeRejected(ValueUtil.getIntegerByObject(obj[2]));
            }
        }
        return response;
    }

    @Override
    public ProcessStatisticsRevaluationResponse getStatisticsRevaluation() {
        StringBuilder sb = new StringBuilder();
        sb.append(" select sum(totalStatistic.countTotalPending) as countTotalPending,  " +
                "       sum(totalStatistic.countTotalBeApproved) as countTotalReject, " +
                "       sum(totalStatistic.countTotalReject) as countTotalBeApproved " +
                "from (select count(0) countTotalPending, 0 countTotalReject, 0 countTotalBeApproved " +
                "from process pr " +
                "     inner join department de on pr.id_department = de.id_department " +
                "     inner join type_process tp on pr.id_type_process = tp.id_type_process " +
                "where pr.status = :statusPending " +
                "and tp.code = :codeTypeProcess " +
                "and de.id_department in (:idsDepartmentOriginal) " +
                "union all " +
                "select 0 countTotalPending,count(0) countTotalReject, 0 countTotalBeApproved " +
                "from process pr " +
                "      inner join department de on pr.id_department = de.id_department " +
                "      inner join type_process tp on pr.id_type_process = tp.id_type_process " +
                "where pr.status = :statusReject " +
                "  and tp.code = :codeTypeProcess " +
                "  and de.id_department in (:idsDepartmentOriginal) " +
                "union all " +
                "select 0 countTotalPending, 0 countTotalReject, count(0) countTotalBeApproved " +
                "from request_stake_holder rsh " +
                "      inner join request rq on rsh.id_request = rq.id_request " +
                "      inner join state st on rq.id_state = st.id_state " +
                "      inner join process pr on st.id_process = pr.id_process " +
                "      inner join type_process tp on pr.id_type_process = tp.id_type_process " +
                "      inner join csvc_user csvcUser on rsh.id_user = csvcUser.id_user " +
                "where rsh.status = :statusRequestPending " +
                "  and tp.code = :codeTypeProcess " +
                "  and csvcUser.id_user = :idUser) totalStatistic ");
        CsvcUser csvcUser = (CsvcUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("statusPending", Constants.STATUS_PENDING_PROCESS);
        query.setParameter("statusReject", Constants.STATUS_FALSE_PROCESS);
        query.setParameter("statusRequestPending", Constants.STATUS_REQUEST_STAKE_HOLDER_PENDING);
        query.setParameter("codeTypeProcess", Constants.CODE_TYPE_PROCESS_REVALUATION);
        query.setParameter("idsDepartmentOriginal", csvcUser.getIdsDepartmentCurrent());
        query.setParameter("idUser", csvcUser.getIdUser());
        List<Object[]> result = query.getResultList();
        ProcessStatisticsRevaluationResponse response = new ProcessStatisticsRevaluationResponse();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                response.setTotalRevaluationPendingApproved(ValueUtil.getIntegerByObject(obj[0]));
                response.setTotalRevaluationPendingBeApproved(ValueUtil.getIntegerByObject(obj[1]));
                response.setTotalRevaluationRejected(ValueUtil.getIntegerByObject(obj[2]));
            }
        }
        return response;
    }

    @Override
    public ProcessStatisticsDocumentBeInventoryResponse getStatisticsDocumentByInventory() {
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0) countTotalBeApproved  " +
                "from process pr  " +
                "         inner join department de on pr.id_department = de.id_department  " +
                "         inner join type_process tp on pr.id_type_process = tp.id_type_process  " +
                "where pr.status = :statusPending  " +
                "  and tp.code = :codeTypeProcess  " +
                "  and de.id_department in (:idsDepartmentOriginal) ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("statusPending", Constants.STATUS_PENDING_PROCESS);
        query.setParameter("codeTypeProcess", Constants.CODE_TYPE_PROCESS_DOCUMENT_INVENTORY);
        query.setParameter("idsDepartmentOriginal", csvcUser.getIdsDepartmentCurrent());
        ProcessStatisticsDocumentBeInventoryResponse response = new ProcessStatisticsDocumentBeInventoryResponse();
        response.setTotalInventoryBeApproved(ValueUtil.getIntegerByObject(query.getSingleResult()));
        return response;
    }

    @Override
    public ProcessStatisticsUpdateInventoryResponse getStatisticsUpdateInventory() {
        StringBuilder sb = new StringBuilder();
        sb.append(" select sum(totalStatistic.countTotalPending) as countTotalPending,  " +
                "       sum(totalStatistic.countTotalBeApproved) as countTotalBeApproved, " +
                "       sum(totalStatistic.countTotalReject) as countTotalReject " +
                "from (select count(0) countTotalPending, 0 countTotalReject, 0 countTotalBeApproved " +
                "from process pr " +
                "     inner join department de on pr.id_department = de.id_department " +
                "     inner join type_process tp on pr.id_type_process = tp.id_type_process " +
                "where pr.status = :statusPending " +
                "and tp.code = :codeTypeProcess " +
                "and de.id_department in (:idsDepartmentOriginal) " +
                "union all " +
                "select 0 countTotalPending,count(0) countTotalReject, 0 countTotalBeApproved " +
                "from process pr " +
                "      inner join department de on pr.id_department = de.id_department " +
                "      inner join type_process tp on pr.id_type_process = tp.id_type_process " +
                "where pr.status = :statusReject " +
                "  and tp.code = :codeTypeProcess " +
                "  and de.id_department in (:idsDepartmentOriginal) " +
                "union all " +
                "select 0 countTotalPending, 0 countTotalReject, count(0) countTotalBeApproved " +
                "from request_stake_holder rsh " +
                "      inner join request rq on rsh.id_request = rq.id_request " +
                "      inner join state st on rq.id_state = st.id_state " +
                "      inner join process pr on st.id_process = pr.id_process " +
                "      inner join type_process tp on pr.id_type_process = tp.id_type_process " +
                "      inner join csvc_user csvcUser on rsh.id_user = csvcUser.id_user " +
                "where rsh.status = :statusRequestPending " +
                "  and tp.code = :codeTypeProcess " +
                "  and csvcUser.id_user = :idUser) totalStatistic ");
        CsvcUser csvcUser = (CsvcUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("statusPending", Constants.STATUS_PENDING_PROCESS);
        query.setParameter("statusReject", Constants.STATUS_FALSE_PROCESS);
        query.setParameter("statusRequestPending", Constants.STATUS_REQUEST_STAKE_HOLDER_PENDING);
        query.setParameter("codeTypeProcess", Constants.CODE_TYPE_PROCESS_UPDATE_INVENTORY);
        query.setParameter("idsDepartmentOriginal", csvcUser.getIdsDepartmentCurrent());
        query.setParameter("idUser", csvcUser.getIdUser());
        List<Object[]> result = query.getResultList();
        ProcessStatisticsUpdateInventoryResponse response = new ProcessStatisticsUpdateInventoryResponse();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                response.setTotalUpdateInventoryPendingApproved(ValueUtil.getIntegerByObject(obj[0]));
                response.setTotalUpdateInventoryPendingBeApproved(ValueUtil.getIntegerByObject(obj[1]));
                response.setTotalUpdateInventoryRejected(ValueUtil.getIntegerByObject(obj[2]));
            }
        }
        return response;
    }

    @Override
    public Page<FindAllProcessBeAssignedResponse>
    findAllProcessBeAssignedDocumentInventory(FindAllProcessBeAssignedDocumentInventoryRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append("select pr.id_process, dc.id_document, dc.code codeDocument,     " +
                "        tp.name typeProcess, dc.description, pr.time_created,         " +
                "        pr.time_modified, csvcUserCreate.user_name, csvcUserCreate.full_name,         " +
                "        dc.time_created, dc.time_modified, dc.time_increase, dc.time_document,  " +
                "        rsh.id_request_stake_holder  " +
                " from process pr  " +
                "        inner join department de on pr.id_department = de.id_department     " +
                "        inner join type_process tp on pr.id_type_process = tp.id_type_process     " +
                "        inner join document dc on pr.id_process = dc.id_process     " +
                "        inner join csvc_user csvcUserCreate on pr.id_user_created = csvcUserCreate.id_user  " +
                "        inner join request re on pr.id_process = re.id_process  " +
                "        inner join request_stake_holder rsh on re.id_request = rsh.id_request  " +
                " where pr.status = :statusPending  " +
                " and tp.code = :codeTypeProcess  " +
                " and de.id_department in (:idsDepartmentOriginal) ");
        setConditionFindAllProcessBeAssignedDocumentInventory(sb, request);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindallProcessBeAssignedDocumentInventory(query, request);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<FindAllProcessBeAssignedResponse> responses = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                FindAllProcessBeAssignedResponse response = new FindAllProcessBeAssignedResponse();
                response.setIdProcess(ValueUtil.getIntegerByObject(obj[0]));
                response.setIdDocument(ValueUtil.getIntegerByObject(obj[1]));
                response.setCodeDocument(ValueUtil.getStringByObject(obj[2]));
                response.setTypeProcess(ValueUtil.getStringByObject(obj[3]));
                response.setDescription(ValueUtil.getStringByObject(obj[4]));
                response.setTimeCreatedProcess(DateUtil.formatToPattern(new Date(ValueUtil.getLongByObject(obj[5])), DateUtil.DATE_FORMAT));
                response.setTimeModifiedProcess(DateUtil.formatToPattern(new Date(ValueUtil.getLongByObject(obj[6])), DateUtil.DATE_FORMAT));
                response.setUserNameCreated(ValueUtil.getStringByObject(obj[7]));
                response.setFullNameCreated(ValueUtil.getStringByObject(obj[8]));
                response.setTimeCreatedDocument(DateUtil.formatToPattern(new Date(ValueUtil.getLongByObject(obj[9])), DateUtil.DATE_FORMAT));
                response.setTimeModifiedDocument(DateUtil.formatToPattern(new Date(ValueUtil.getLongByObject(obj[10])), DateUtil.DATE_FORMAT));
                response.setTimeIncrease(ValueUtil.getStringByObject(obj[11]));
                response.setTimeDocument(ValueUtil.getStringByObject(obj[12]));
                response.setIdRequestStakeHolder(ValueUtil.getIntegerByObject(obj[13]));
                responses.add(response);
            }
        }
        return new PageImpl<>(responses, pageable, countFinaAllProcessBeAssignedDocumentInventory(request));
    }

    @Override
    public ProcessStatisticsToolIncreaseResponse getStatisticsToolIncrease() {
        StringBuilder sb = new StringBuilder();
        sb.append(" select sum(totalStatistic.countTotalPending) as countTotalPending,        " +
                "          sum(totalStatistic.countTotalBeApproved) as countTotalBeApproved,       " +
                "          sum(totalStatistic.countTotalReject) as countTotalReject       " +
                "   from (select count(0) countTotalPending, 0 countTotalReject, 0 countTotalBeApproved       " +
                "   from process pr       " +
                "        inner join department de on pr.id_department = de.id_department       " +
                "        inner join type_process tp on pr.id_type_process = tp.id_type_process       " +
                "   where pr.status = :statusPending       " +
                "   and tp.code = :codeTypeProcess       " +
                "   and de.id_department in (:idsDepartmentOriginal)       " +
                "   union all       " +
                "   select 0 countTotalPending,count(0) countTotalReject, 0 countTotalBeApproved       " +
                "   from process pr       " +
                "         inner join department de on pr.id_department = de.id_department       " +
                "         inner join type_process tp on pr.id_type_process = tp.id_type_process       " +
                "   where pr.status = :statusReject       " +
                "     and tp.code = :codeTypeProcess       " +
                "     and de.id_department in (:idsDepartmentOriginal)       " +
                "   union all       " +
                "   select 0 countTotalPending, 0 countTotalReject, count(0) countTotalBeApproved       " +
                "   from request_stake_holder rsh       " +
                "         inner join request rq on rsh.id_request = rq.id_request       " +
                "         inner join state st on rq.id_state = st.id_state       " +
                "         inner join process pr on st.id_process = pr.id_process       " +
                "         inner join type_process tp on pr.id_type_process = tp.id_type_process       " +
                "         inner join csvc_user csvcUser on rsh.id_user = csvcUser.id_user       " +
                "where rsh.status = :statusRequestPending   " +
                "     and tp.code = :codeTypeProcess       " +
                "     and csvcUser.id_user = :idUser) totalStatistic  ");
        Query query = entityManager.createNativeQuery(sb.toString());
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        query.setParameter("statusPending", Constants.STATUS_PENDING_PROCESS);
        query.setParameter("statusReject", Constants.STATUS_FALSE_PROCESS);
        query.setParameter("statusRequestPending", Constants.STATUS_REQUEST_STAKE_HOLDER_PENDING);
        query.setParameter("codeTypeProcess", Constants.CODE_TYPE_PROCESS_INCREASE_TOOL);
        query.setParameter("idUser", csvcUser.getIdUser());
        query.setParameter("idsDepartmentOriginal", csvcUser.getIdsDepartmentCurrent());
        List<Object[]> result = query.getResultList();
        ProcessStatisticsToolIncreaseResponse response = new ProcessStatisticsToolIncreaseResponse();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                response.setTotalIncreasePendingApproved(ValueUtil.getIntegerByObject(obj[0]));
                response.setTotalIncreasePendingBeApproved(ValueUtil.getIntegerByObject(obj[1]));
                response.setTotalIncreaseRejected(ValueUtil.getIntegerByObject(obj[2]));
            }
        }
        return response;
    }

    @Override
    public ProcessStatisticsToolDecreaseResponse getStatisticsToolDecrease() {
        StringBuilder sb = new StringBuilder();
        sb.append(" select sum(totalStatistic.countTotalPending) as countTotalPending,        " +
                "          sum(totalStatistic.countTotalBeApproved) as countTotalBeApproved,       " +
                "          sum(totalStatistic.countTotalReject) as countTotalReject       " +
                "   from (select count(0) countTotalPending, 0 countTotalReject, 0 countTotalBeApproved       " +
                "   from process pr       " +
                "        inner join department de on pr.id_department = de.id_department       " +
                "        inner join type_process tp on pr.id_type_process = tp.id_type_process       " +
                "   where pr.status = :statusPending       " +
                "   and tp.code = :codeTypeProcess       " +
                "   and de.id_department in (:idsDepartmentOriginal)       " +
                "   union all       " +
                "   select 0 countTotalPending,count(0) countTotalReject, 0 countTotalBeApproved       " +
                "   from process pr       " +
                "         inner join department de on pr.id_department = de.id_department       " +
                "         inner join type_process tp on pr.id_type_process = tp.id_type_process       " +
                "   where pr.status = :statusReject       " +
                "     and tp.code = :codeTypeProcess       " +
                "     and de.id_department in (:idsDepartmentOriginal)       " +
                "   union all       " +
                "   select 0 countTotalPending, 0 countTotalReject, count(0) countTotalBeApproved       " +
                "   from request_stake_holder rsh       " +
                "         inner join request rq on rsh.id_request = rq.id_request       " +
                "         inner join state st on rq.id_state = st.id_state       " +
                "         inner join process pr on st.id_process = pr.id_process       " +
                "         inner join type_process tp on pr.id_type_process = tp.id_type_process       " +
                "         inner join csvc_user csvcUser on rsh.id_user = csvcUser.id_user       " +
                "where rsh.status = :statusRequestPending   " +
                "     and tp.code = :codeTypeProcess       " +
                "     and csvcUser.id_user = :idUser) totalStatistic  ");
        Query query = entityManager.createNativeQuery(sb.toString());
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        query.setParameter("statusPending", Constants.STATUS_PENDING_PROCESS);
        query.setParameter("statusReject", Constants.STATUS_FALSE_PROCESS);
        query.setParameter("statusRequestPending", Constants.STATUS_REQUEST_STAKE_HOLDER_PENDING);
        query.setParameter("codeTypeProcess", Constants.CODE_TYPE_PROCESS_DECREASE_TOOL);
        query.setParameter("idUser", csvcUser.getIdUser());
        query.setParameter("idsDepartmentOriginal", csvcUser.getIdsDepartmentCurrent());
        List<Object[]> result = query.getResultList();
        ProcessStatisticsToolDecreaseResponse response = new ProcessStatisticsToolDecreaseResponse();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                response.setTotalDecreasePendingApproved(ValueUtil.getIntegerByObject(obj[0]));
                response.setTotalDecreasePendingBeApproved(ValueUtil.getIntegerByObject(obj[1]));
                response.setTotalDecreaseRejected(ValueUtil.getIntegerByObject(obj[2]));
            }
        }
        return response;
    }

    @Override
    public ProcessStatisticsToolDocumentInventoryResponse getStatisticsToolDocumentInventory() {
        StringBuilder sb = new StringBuilder();
        sb.append("select sum(totalStatistic.countTotalPending) as countTotalPending,  " +
                "       sum(totalStatistic.countTotalReject)  as countTotalReject  " +
                "from (select count(0) countTotalPending, 0 countTotalReject  " +
                "      from process pr  " +
                "               inner join department de on pr.id_department = de.id_department  " +
                "               inner join type_process tp on pr.id_type_process = tp.id_type_process  " +
                "      where pr.status = :statusPending  " +
                "        and tp.code = :codeTypeProcess  " +
                "        and de.id_department in (:idsDepartmentOriginal)  " +
                "      union all  " +
                "      select 0 countTotalPending, count(0) countTotalReject  " +
                "      from process pr  " +
                "               inner join department de on pr.id_department = de.id_department  " +
                "               inner join type_process tp on pr.id_type_process = tp.id_type_process  " +
                "      where pr.status = :statusReject  " +
                "        and tp.code = :codeTypeProcess  " +
                "        and de.id_department in (:idsDepartmentOriginal)) totalStatistic ");
        CsvcUser csvcUser = (CsvcUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("statusPending", Constants.STATUS_PENDING_PROCESS);
        query.setParameter("statusReject", Constants.STATUS_FALSE_PROCESS);
        query.setParameter("codeTypeProcess", Constants.CODE_TYPE_PROCESS_INVENTORY_TOOL);
        query.setParameter("idsDepartmentOriginal", csvcUser.getIdsDepartmentCurrent());
        List<Object[]> result = query.getResultList();
        ProcessStatisticsToolDocumentInventoryResponse response = new ProcessStatisticsToolDocumentInventoryResponse();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                response.setTotalInventoryPendingApproved(ValueUtil.getIntegerByObject(obj[0]));
                response.setTotalInventoryRejected(ValueUtil.getIntegerByObject(obj[1]));
            }
        }
        return response;
    }

    @Override
    public ProcessStatisticsToolDocumentBeInventoryResponse getStatisticsToolDocumentByInventory() {
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0) countTotalBeApproved  " +
                "from process pr  " +
                "         inner join department de on pr.id_department = de.id_department  " +
                "         inner join type_process tp on pr.id_type_process = tp.id_type_process  " +
                "where pr.status = :statusPending  " +
                "  and tp.code = :codeTypeProcess  " +
                "  and de.id_department in (:idsDepartmentOriginal) ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("statusPending", Constants.STATUS_PENDING_PROCESS);
        query.setParameter("codeTypeProcess", Constants.CODE_TYPE_PROCESS_INVENTORY_TOOL);
        query.setParameter("idsDepartmentOriginal", csvcUser.getIdsDepartmentCurrent());
        ProcessStatisticsToolDocumentBeInventoryResponse response = new ProcessStatisticsToolDocumentBeInventoryResponse();
        response.setTotalInventoryBeApproved(ValueUtil.getIntegerByObject(query.getSingleResult()));
        return response;
    }

    @Override
    public ProcessStatisticsToolUpdateInventoryResponse getStatisticsToolUpdateInventory() {
        StringBuilder sb = new StringBuilder();
        sb.append(" select sum(totalStatistic.countTotalPending) as countTotalPending,  " +
                "       sum(totalStatistic.countTotalBeApproved) as countTotalBeApproved, " +
                "       sum(totalStatistic.countTotalReject) as countTotalReject " +
                "from (select count(0) countTotalPending, 0 countTotalReject, 0 countTotalBeApproved " +
                "from process pr " +
                "     inner join department de on pr.id_department = de.id_department " +
                "     inner join type_process tp on pr.id_type_process = tp.id_type_process " +
                "where pr.status = :statusPending " +
                "and tp.code = :codeTypeProcess " +
                "and de.id_department in (:idsDepartmentOriginal) " +
                "union all " +
                "select 0 countTotalPending,count(0) countTotalReject, 0 countTotalBeApproved " +
                "from process pr " +
                "      inner join department de on pr.id_department = de.id_department " +
                "      inner join type_process tp on pr.id_type_process = tp.id_type_process " +
                "where pr.status = :statusReject " +
                "  and tp.code = :codeTypeProcess " +
                "  and de.id_department in (:idsDepartmentOriginal) " +
                "union all " +
                "select 0 countTotalPending, 0 countTotalReject, count(0) countTotalBeApproved " +
                "from request_stake_holder rsh " +
                "      inner join request rq on rsh.id_request = rq.id_request " +
                "      inner join state st on rq.id_state = st.id_state " +
                "      inner join process pr on st.id_process = pr.id_process " +
                "      inner join type_process tp on pr.id_type_process = tp.id_type_process " +
                "      inner join csvc_user csvcUser on rsh.id_user = csvcUser.id_user " +
                "where rsh.status = :statusRequestPending " +
                "  and tp.code = :codeTypeProcess " +
                "  and csvcUser.id_user = :idUser) totalStatistic ");
        CsvcUser csvcUser = (CsvcUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("statusPending", Constants.STATUS_PENDING_PROCESS);
        query.setParameter("statusReject", Constants.STATUS_FALSE_PROCESS);
        query.setParameter("statusRequestPending", Constants.STATUS_REQUEST_STAKE_HOLDER_PENDING);
        query.setParameter("codeTypeProcess", Constants.CODE_TYPE_PROCESS_UPDATE_INVENTORY_TOOL);
        query.setParameter("idsDepartmentOriginal", csvcUser.getIdsDepartmentCurrent());
        query.setParameter("idUser", csvcUser.getIdUser());
        List<Object[]> result = query.getResultList();
        ProcessStatisticsToolUpdateInventoryResponse response = new ProcessStatisticsToolUpdateInventoryResponse();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                response.setTotalUpdateInventoryPendingApproved(ValueUtil.getIntegerByObject(obj[0]));
                response.setTotalUpdateInventoryPendingBeApproved(ValueUtil.getIntegerByObject(obj[1]));
                response.setTotalUpdateInventoryRejected(ValueUtil.getIntegerByObject(obj[2]));
            }
        }
        return response;
    }

    private long countFinaAllProcessBeAssignedDocumentInventory(FindAllProcessBeAssignedDocumentInventoryRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0) count  " +
                "from process pr  " +
                "         inner join department de on pr.id_department = de.id_department  " +
                "         inner join type_process tp on pr.id_type_process = tp.id_type_process  " +
                "         inner join document dc on pr.id_process = dc.id_process  " +
                "         inner join csvc_user csvcUserCreate on pr.id_user_created = csvcUserCreate.id_user  " +
                "where pr.status = :statusPending  " +
                "  and tp.code = :codeTypeProcess  " +
                "  and de.id_department in (:idsDepartmentOriginal) ");
        setConditionFindAllProcessBeAssignedDocumentInventory(sb, request);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindallProcessBeAssignedDocumentInventory(query, request);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }

    private void setParameterFindallProcessBeAssignedDocumentInventory(Query query,
                                                                       FindAllProcessBeAssignedDocumentInventoryRequest
                                                                               request) {
        query.setParameter("statusPending", Constants.STATUS_PENDING_PROCESS);
        query.setParameter("codeTypeProcess", Constants.CODE_TYPE_PROCESS_DOCUMENT_INVENTORY);
        query.setParameter("idsDepartmentOriginal", request.getIdsDepartment());
        if (Objects.nonNull(request.getIdDepartment())) {
            query.setParameter("idDepartment", request.getIdDepartment());
        }
    }

    private void setConditionFindAllProcessBeAssignedDocumentInventory(StringBuilder sb,
                                                                       FindAllProcessBeAssignedDocumentInventoryRequest
                                                                               request) {
        if (Objects.nonNull(request.getIdDepartment())) {
            sb.append(" and de.id_department = :idDepartment ");
        }
        sb.append("   ORDER BY pr.id_process DESC  ");
    }

    private long countFindAllProcessBeAssigned(FindAllProcessBeAssignedRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0) count  " +
                "from request_stake_holder rsh  " +
                "    inner join request rq on rsh.id_request = rq.id_request  " +
                "    inner join state st on rq.id_state = st.id_state  " +
                "    inner join type_state ts on st.id_type_state = ts.id_type_state  " +
                "    inner join process pr on st.id_process = pr.id_process  " +
                "    inner join type_process tp on pr.id_type_process = tp.id_type_process  " +
                "    inner join document dc on pr.id_process = dc.id_process  " +
                "    inner join csvc_user csvcUserCreate on pr.id_user_created = csvcUserCreate.id_user  " +
                "    inner join csvc_user csvcUser on rsh.id_user = csvcUser.id_user  " +
                "where rsh.status = :statusPending  " +
                "and csvcUser.id_user = :idUser ");
        setConditionFindAllProcessBeAssigned(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllProcessBeAssigned(request, query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }

    private void setParameterFindAllProcessBeAssigned(FindAllProcessBeAssignedRequest request, Query query) {
        query.setParameter("statusPending", Constants.STATUS_REQUEST_STAKE_HOLDER_PENDING);
        query.setParameter("idUser", ((CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getIdUser());
        if (StringUtils.isNotBlank(request.getCodeTypeProcess())) {
            query.setParameter("typeProcess", request.getCodeTypeProcess());
        }
        if (StringUtils.isNotBlank(request.getTimeCreated())) {
            query.setParameter("timeCreate", request.getTimeCreated());
        }
        if (StringUtils.isNotBlank(request.getTimeIncrease())) {
            query.setParameter("timeIncrease", request.getTimeIncrease());
        }
        if (StringUtils.isNotBlank(request.getTimeDocument())) {
            query.setParameter("timeDocument", request.getTimeDocument());
        }
        if (StringUtils.isNotBlank(request.getNameUserCreate())) {
            query.setParameter("nameUserCreate", request.getNameUserCreate());
        }
        if (StringUtils.isNotBlank(request.getDescription())) {
            query.setParameter("description", request.getDescription());
        }
        if (StringUtils.isNotBlank(request.getCodeDocument())) {
            query.setParameter("codeDocument", request.getCodeDocument());
        }
    }

    private void setConditionFindAllProcessBeAssigned(FindAllProcessBeAssignedRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getCodeTypeProcess())){
            sb.append(" and tp.code = :typeProcess  ");
        }
        if (StringUtils.isNotBlank(request.getNameUserCreate())){
            sb.append(" and (csvcUserCreate.full_name REGEXP :nameUserCreate ) ");
        }
        if (StringUtils.isNotBlank(request.getTimeCreated())){
            sb.append(" and (dc.time_created REGEXP :timeCreate ) ");
        }
        if (StringUtils.isNotBlank(request.getTimeDocument())){
            sb.append(" and (dc.time_document REGEXP :timeDocument) ");
        }
        if (StringUtils.isNotBlank(request.getTimeIncrease())){
            sb.append(" and (dc.time_increase REGEXP :timeIncrease) ");
        }
        if (StringUtils.isNotBlank(request.getDescription())){
            sb.append(" and (dc.description REGEXP :description) ");
        }
        if (StringUtils.isNotBlank(request.getCodeDocument())) {
                sb.append(" and (dc.code REGEXP :codeDocument )  ");
        }
        sb.append(" order by rsh.time_created DESC  ");
    }
}
