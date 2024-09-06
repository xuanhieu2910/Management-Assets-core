package com.example.csvccdshustbe.repository.documentAttack.impl;

import com.example.csvccdshustbe.entity.DocumentAttack;
import com.example.csvccdshustbe.entity.LevelTypeAsset;
import com.example.csvccdshustbe.repository.documentAttack.DocumentAttackRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DocumentAttackRepositoryImpl implements DocumentAttackRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;
    @Override
    public List<DocumentAttack>findAllDocumentAttackResponseByStatus(Integer status){
        StringBuilder sb = new StringBuilder();
        sb.append("select document_attack.id_document_attack, " +
                "document_attack.name, document_attack.code, document_attack.id_department, " +
                " document_attack.date_determination_document, document_attack.status, " +
                " document_attack.time_created, document_attack.time_modified " +
                "from document_attack " +
                "where 1=1 and document_attack.status = :status ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("status", status);
        List<Object[]> result = query.getResultList();
        List<DocumentAttack> documentAttacks = new ArrayList<>();
        if(!CollectionUtils.isEmpty(result)) {
            for(Object[] obj :result) {
             DocumentAttack documentAttack=new DocumentAttack();
             documentAttack.setIdDocumentAttack(ValueUtil.getIntegerByObject(obj[0]));
             documentAttack.setName(ValueUtil.getStringByObject(obj[1]));
             documentAttack.setCode(ValueUtil.getStringByObject(obj[2]));
             documentAttack.setIdDepartment(ValueUtil.getIntegerByObject(obj[3]));
             documentAttack.setDateDeterminationDocument(ValueUtil.getStringByObject(obj[4]));
             documentAttack.setStatus(ValueUtil.getIntegerByObject(obj[5]));
             documentAttack.setTimeCreated((ValueUtil.getStringByObject(obj[6])));
             documentAttack.setTimeModified(ValueUtil.getStringByObject(obj[7]));
             documentAttacks.add(documentAttack);
            }
        }
        return documentAttacks;
    }

    @Override
    public Optional<DocumentAttack> findDocumentAttackByName(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append("select document_attack.id_document_attack, " +
                "document_attack.name, document_attack.code, document_attack.id_department, " +
                " document_attack.date_determination_document, document_attack.status, " +
                " document_attack.time_created, document_attack.time_modified " +
                "from document_attack " +
                "where document_attack.name = :name ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("name", name);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                DocumentAttack documentAttack=new DocumentAttack();
                documentAttack.setIdDocumentAttack(ValueUtil.getIntegerByObject(obj[0]));
                documentAttack.setName(ValueUtil.getStringByObject(obj[1]));
                documentAttack.setCode(ValueUtil.getStringByObject(obj[2]));
                documentAttack.setIdDepartment(ValueUtil.getIntegerByObject(obj[3]));
                documentAttack.setDateDeterminationDocument(ValueUtil.getStringByObject(obj[4]));
                documentAttack.setStatus(ValueUtil.getIntegerByObject(obj[5]));
                documentAttack.setTimeCreated((ValueUtil.getStringByObject(obj[6])));
                documentAttack.setTimeModified(ValueUtil.getStringByObject(obj[7]));
                return Optional.of(documentAttack);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<DocumentAttack> findDocumentAttackById(Integer idDocumentAttack) {
        StringBuilder sb = new StringBuilder();
        sb.append("select document_attack.id_document_attack, " +
                "document_attack.name, document_attack.code, document_attack.id_department, " +
                " document_attack.date_determination_document, document_attack.status, " +
                " document_attack.time_created, document_attack.time_modified " +
                "from document_attack " +
                "where document_attack.id_document_attack = :idDocumentAttack ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idDocumentAttack", idDocumentAttack);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                DocumentAttack documentAttack=new DocumentAttack();
                documentAttack.setIdDocumentAttack(ValueUtil.getIntegerByObject(obj[0]));
                documentAttack.setName(ValueUtil.getStringByObject(obj[1]));
                documentAttack.setCode(ValueUtil.getStringByObject(obj[2]));
                documentAttack.setIdDepartment(ValueUtil.getIntegerByObject(obj[3]));
                documentAttack.setDateDeterminationDocument(ValueUtil.getStringByObject(obj[4]));
                documentAttack.setStatus(ValueUtil.getIntegerByObject(obj[5]));
                documentAttack.setTimeCreated((ValueUtil.getStringByObject(obj[6])));
                documentAttack.setTimeModified(ValueUtil.getStringByObject(obj[7]));
                return Optional.of(documentAttack);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<DocumentAttack> findDocumentAttackByIdDocumentAndStatus(Integer idDocumentAttack, Integer status) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select document_attack.id_document_attack,   " +
                "       document_attack.name,   " +
                "       document_attack.code,   " +
                "       document_attack.id_department,   " +
                "       document_attack.date_determination_document,   " +
                "       document_attack.status,   " +
                "       document_attack.time_created,   " +
                "       document_attack.time_modified   " +
                "from document_attack   " +
                "where document_attack.id_document_attack = :idDocumentAttack   " +
                "and document_attack.status = :status ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idDocumentAttack", idDocumentAttack);
        query.setParameter("status", status);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                DocumentAttack documentAttack=new DocumentAttack();
                documentAttack.setIdDocumentAttack(ValueUtil.getIntegerByObject(obj[0]));
                documentAttack.setName(ValueUtil.getStringByObject(obj[1]));
                documentAttack.setCode(ValueUtil.getStringByObject(obj[2]));
                documentAttack.setIdDepartment(ValueUtil.getIntegerByObject(obj[3]));
                documentAttack.setDateDeterminationDocument(ValueUtil.getStringByObject(obj[4]));
                documentAttack.setStatus(ValueUtil.getIntegerByObject(obj[5]));
                documentAttack.setTimeCreated((ValueUtil.getStringByObject(obj[6])));
                documentAttack.setTimeModified(ValueUtil.getStringByObject(obj[7]));
                return Optional.of(documentAttack);
            }
        }
        return Optional.empty();
    }
}
