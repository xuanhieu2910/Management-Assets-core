package com.example.csvccdshustbe.repository.typeProcess.impl;

import com.example.csvccdshustbe.entity.TypeProcess;
import com.example.csvccdshustbe.repository.typeProcess.TypeProcessRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

public class TypeProcessRepositoryImpl implements TypeProcessRepositoryCustom {
    
    @PersistenceContext
    EntityManager entityManager;
    
    @Override
    public Optional<TypeProcess> findTypeProcessByCode(String codeProcess) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select id_type_process, name, code,  " +
                "       description, time_created, time_modified  " +
                "from type_process   " +
                "where code = :code ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("code", codeProcess);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                TypeProcess process = new TypeProcess();
                process.setIdTypeProcess(ValueUtil.getIntegerByObject(obj[0]));
                process.setName(ValueUtil.getStringByObject(obj[1]));
                process.setCode(ValueUtil.getStringByObject(obj[2]));
                process.setDescription(ValueUtil.getStringByObject(obj[3]));
                process.setTimeCreated(ValueUtil.getStringByObject(obj[4]));
                process.setTimeModified(ValueUtil.getStringByObject(obj[5]));
                return Optional.of(process);
            }
        }
        return Optional.empty();
    }
}
