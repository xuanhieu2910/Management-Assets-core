package com.example.csvccdshustbe.repository.documentAttack;

import com.example.csvccdshustbe.entity.DocumentAttack;

import java.util.List;

public interface DocumentAttackRepositoryCustom {
    List<DocumentAttack> findAllDocumentAttackResponseByStatus(Integer status);
}
