package com.example.csvccdshustbe.repository.documentAttack;

import com.example.csvccdshustbe.entity.DocumentAttack;

import java.util.List;
import java.util.Optional;

public interface DocumentAttackRepositoryCustom {
    List<DocumentAttack> findAllDocumentAttackResponseByStatus(Integer status);
    Optional<DocumentAttack> findDocumentAttackByName(String name);
    Optional<DocumentAttack> findDocumentAttackById(Integer idDocumentAttack);

    Optional<DocumentAttack> findDocumentAttackByIdDocumentAndStatus(Integer idDocumentAttack, Integer status);
}
