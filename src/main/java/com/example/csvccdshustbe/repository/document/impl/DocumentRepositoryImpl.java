package com.example.csvccdshustbe.repository.document.impl;

import com.example.csvccdshustbe.entity.Document;
import com.example.csvccdshustbe.repository.document.DocumentRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

public class DocumentRepositoryImpl implements DocumentRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<Document> findDocumentByCodeAndIdDepartment(String code, Integer idDepartment) {
        StringBuilder sb = new StringBuilder();
        sb.append("select doc.id_document, doc.code, doc.time_created, " +
                "       doc.time_modified, doc.time_increase, doc.id_department " +
                "from document doc  " +
                "where doc.code = :code " +
                "and doc.id_department = :idDepartment ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("code", code);
        query.setParameter("idDepartment", idDepartment);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                Document document = new Document();
                document.setIdDocument(ValueUtil.getIntegerByObject(obj[0]));
                document.setCode(ValueUtil.getStringByObject(obj[1]));
                document.setTimeCreated(ValueUtil.getStringByObject(obj[2]));
                document.setTimeModified(ValueUtil.getStringByObject(obj[3]));
                document.setTimeIncrease(ValueUtil.getStringByObject(obj[4]));
                document.setIdDepartment(ValueUtil.getIntegerByObject(obj[5]));
                return Optional.of(document);
            }
        }
        return Optional.empty();
    }
}
