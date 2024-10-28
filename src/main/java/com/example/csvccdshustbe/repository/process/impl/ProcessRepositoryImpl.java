package com.example.csvccdshustbe.repository.process.impl;

import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.entity.Process;
import com.example.csvccdshustbe.repository.process.ProcessRepositoryCustom;
import com.example.csvccdshustbe.request.process.FindAllProcessBeAssignedRequest;
import com.example.csvccdshustbe.response.process.FindAllProcessBeAssignedResponse;
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
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
                response.setTimeCreatedProcess(DateUtil.convertStringDateToDate(ValueUtil.getStringByObject(obj[6]), DateUtil.DATE_FORMAT));
                response.setTimeModifiedProcess(DateUtil.convertStringDateToDate(ValueUtil.getStringByObject(obj[7]), DateUtil.DATE_FORMAT));
                response.setUserNameCreated(ValueUtil.getStringByObject(obj[8]));
                response.setFullNameCreated(ValueUtil.getStringByObject(obj[9]));
                response.setTimeCreatedDocument(DateUtil.convertStringDateToDate(ValueUtil.getStringByObject(obj[10]), DateUtil.DATE_FORMAT));
                response.setTimeModifiedDocument(DateUtil.convertStringDateToDate(ValueUtil.getStringByObject(obj[11]), DateUtil.DATE_FORMAT));
                response.setTimeIncrease(ValueUtil.getStringByObject(obj[12]));
                response.setTimeModifiedDocument(ValueUtil.getStringByObject(obj[13]));
                response.setIdState(ValueUtil.getIntegerByObject(obj[14]));
                response.setIdTypeState(ValueUtil.getIntegerByObject(obj[15]));
                response.setCodeTypeState(ValueUtil.getStringByObject(obj[16]));
                response.setStatusState(ValueUtil.getIntegerByObject(obj[17]));
                responses.add(response);
            }
        }
        return new PageImpl<>(responses, pageable, countFinaAllProcessBeAssigned(request));
    }

    private long countFinaAllProcessBeAssigned(FindAllProcessBeAssignedRequest request) {
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
        if (StringUtils.isNotBlank(request.getCodeDocument())) {
            query.setParameter("codeDocument", request.getCodeDocument());
        }
    }

    private void setConditionFindAllProcessBeAssigned(FindAllProcessBeAssignedRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getCodeTypeProcess())){
            sb.append(" and ts.code = :typeProcess  ");
        }
        if (StringUtils.isNotBlank(request.getCodeDocument())) {
            sb.append(" and (dc.code REGEXP :codeDocument )  ");
        }
        sb.append(" order by rsh.time_created DESC  ");
    }
}
