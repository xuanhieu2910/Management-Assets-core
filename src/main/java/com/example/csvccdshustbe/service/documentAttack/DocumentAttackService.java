package com.example.csvccdshustbe.service.documentAttack;

import com.example.csvccdshustbe.entity.DocumentAttack;
import com.example.csvccdshustbe.response.documentAttack.FindAllDocumentAttackResponse;

import java.util.List;

public interface DocumentAttackService {

    List<FindAllDocumentAttackResponse> findAllDocumentAttackResponseByStatus(Integer status);

    public String createDocumentAttack(DocumentAttack documentAttack);
    public String updateDocumentAttack(DocumentAttack documentAttack);

    public String deleteDocumentAttack(Integer documentAttackId);
    public DocumentAttack getDocumentAttack(Integer documentAttackId);

}
