package com.example.csvccdshustbe.repository.requestStakeHolder.impl;

import com.example.csvccdshustbe.dto.requestStakeHolder.RequestStakeHolderDetails;
import com.example.csvccdshustbe.entity.RequestStakeHolder;
import com.example.csvccdshustbe.repository.requestStakeHolder.RequestStakeHolderRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RequestStakeHolderRepositoryImpl implements RequestStakeHolderRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<RequestStakeHolder> findRequestStakeHolderById(Integer idRequestStakeHolder) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select rth.id_request_stake_holder, rth.id_request,  " +
                "       rth.id_user, rth.id_department, rth.status,  " +
                "       rth.time_created, rth.time_modified, rth.id_reason,  " +
                "       rth.description  " +
                "from request_stake_holder rth  " +
                "where rth.id_request_stake_holder = :idrth ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idrth", idRequestStakeHolder);
        List<Object[]> result = query.getResultList();
        if (CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                RequestStakeHolder stakeHolder = new RequestStakeHolder();
                stakeHolder.setIdRequestStakeHolder(ValueUtil.getIntegerByObject(obj[0]));
                stakeHolder.setIdRequest(ValueUtil.getIntegerByObject(obj[1]));
                stakeHolder.setIdUser(ValueUtil.getIntegerByObject(obj[2]));
                stakeHolder.setIdDepartment(ValueUtil.getIntegerByObject(obj[3]));
                stakeHolder.setStatus(ValueUtil.getIntegerByObject(obj[4]));
                stakeHolder.setTimeCreated(ValueUtil.getStringByObject(obj[5]));
                stakeHolder.setTimeModified(ValueUtil.getStringByObject(obj[6]));
                stakeHolder.setIdReason(ValueUtil.getIntegerByObject(obj[7]));
                stakeHolder.setDescription(ValueUtil.getStringByObject(obj[8]));
                return Optional.of(stakeHolder);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<RequestStakeHolder> findRequestStakeHolderByIdRequest(Integer idRequest) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select rsh.id_request_stake_holder, rsh.id_request,  " +
                "       rsh.id_user, rsh.id_department, " +
                "       rsh.status, rsh.time_created,  " +
                "       rsh.time_modified, rsh.id_reason,  " +
                "       rsh.description " +
                "from request_stake_holder rsh " +
                "    inner join request re on rsh.id_request = re.id_request " +
                "where re.id_request = :idRequest ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idRequest", idRequest);
        List<RequestStakeHolder> stakeHolders = new ArrayList<>();
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                RequestStakeHolder stakeHolder = new RequestStakeHolder();
                stakeHolder.setIdRequestStakeHolder(ValueUtil.getIntegerByObject(obj[0]));
                stakeHolder.setIdRequest(ValueUtil.getIntegerByObject(obj[1]));
                stakeHolder.setIdUser(ValueUtil.getIntegerByObject(obj[2]));
                stakeHolder.setIdDepartment(ValueUtil.getIntegerByObject(obj[3]));
                stakeHolder.setStatus(ValueUtil.getIntegerByObject(obj[4]));
                stakeHolder.setTimeCreated(ValueUtil.getStringByObject(obj[5]));
                stakeHolder.setTimeModified(ValueUtil.getStringByObject(obj[6]));
                stakeHolder.setIdReason(ValueUtil.getIntegerByObject(obj[7]));
                stakeHolder.setDescription(ValueUtil.getStringByObject(obj[8]));
                stakeHolders.add(stakeHolder);
            }
        }
        return stakeHolders;
    }

    @Override
    public List<RequestStakeHolderDetails> findRequestStakeHolderDetailsByIdRequest(Integer idRequest) {
        StringBuilder sb = new StringBuilder();
        sb.append("select rsh.id_request_stake_holder, rsh.id_request, csvcUser.id_user,  " +
                "       rsh.id_department, csvcUser.user_name, csvcUser.full_name,  " +
                "       rsh.status, rsh.time_created, rsh.time_modified,  " +
                "       re.id_reason, re.name, rsh.description  " +
                " from request_stake_holder rsh    " +
                "     inner join request re on rsh.id_request = re.id_request  " +
                "     inner join csvc_user csvcUser on rsh.id_user = csvcUser.id_user  " +
                "     inner join reason re on rsh.id_reason = re.id_reason  " +
                " where re.id_request = :idRequest  ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idRequest", idRequest);
        List<RequestStakeHolderDetails> stakeHolders = new ArrayList<>();
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                RequestStakeHolderDetails stakeHolder = new RequestStakeHolderDetails();
                stakeHolder.setIdRequestStakeHolder(ValueUtil.getIntegerByObject(obj[0]));
                stakeHolder.setIdRequest(ValueUtil.getIntegerByObject(obj[1]));
                stakeHolder.setIdUser(ValueUtil.getIntegerByObject(obj[2]));
                stakeHolder.setIdDepartment(ValueUtil.getIntegerByObject(obj[3]));
                stakeHolder.setUserName(ValueUtil.getStringByObject(obj[4]));
                stakeHolder.setFullName(ValueUtil.getStringByObject(obj[5]));
                stakeHolder.setStatus(ValueUtil.getIntegerByObject(obj[6]));
                stakeHolder.setTimeCreated(ValueUtil.getStringByObject(obj[7]));
                stakeHolder.setTimeModified(ValueUtil.getStringByObject(obj[8]));
                stakeHolder.setIdReason(ValueUtil.getIntegerByObject(obj[9]));
                stakeHolder.setReason(ValueUtil.getStringByObject(obj[10]));
                stakeHolder.setDescription(ValueUtil.getStringByObject(obj[11]));
                stakeHolders.add(stakeHolder);
            }
        }
        return stakeHolders;
    }
}
