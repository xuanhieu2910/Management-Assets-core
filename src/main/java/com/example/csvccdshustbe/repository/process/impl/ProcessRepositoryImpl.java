package com.example.csvccdshustbe.repository.process.impl;

import com.example.csvccdshustbe.entity.Process;
import com.example.csvccdshustbe.repository.process.ProcessRepositoryCustom;
import com.example.csvccdshustbe.request.process.FindAllProcessBeAssignedRequest;
import com.example.csvccdshustbe.response.process.FindAllProcessBeAssignedResponse;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;

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
    public Page<FindAllProcessBeAssignedResponse> findAllProcessBeAssigned(FindAllProcessBeAssignedRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
//        sb.append("")
        return null;
    }
}
