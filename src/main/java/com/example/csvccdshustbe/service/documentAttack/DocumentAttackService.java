package com.example.csvccdshustbe.service.documentAttack;

import com.example.csvccdshustbe.entity.DocumentAttack;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.documentAttack.CreateDocumentAttackRequest;
import com.example.csvccdshustbe.request.documentAttack.UpdateDocumentAttackRequest;
import com.example.csvccdshustbe.response.documentAttack.FindAllDocumentAttackResponse;

import java.util.List;

public interface DocumentAttackService {

    List<FindAllDocumentAttackResponse> findAllDocumentAttackResponseByStatus(Integer status);

    void createDocumentAttack(CreateDocumentAttackRequest request) throws ValidateFiledException;
    void updateDocumentAttack(UpdateDocumentAttackRequest request) throws ValidateFiledException;

    void deleteDocumentAttackByIdDA(Integer idDocumentAttack);

    DocumentAttack findDocumentAttackByIdDocumentAndStatus(Integer idDocumentAttack, Integer status);
}
