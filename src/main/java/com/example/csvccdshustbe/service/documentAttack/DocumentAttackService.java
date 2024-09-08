package com.example.csvccdshustbe.service.documentAttack;

import com.example.csvccdshustbe.entity.DocumentAttack;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.documentAttack.CreateDocumentAttackRequest;
import com.example.csvccdshustbe.request.documentAttack.FindAllDocumentAttackRequest;
import com.example.csvccdshustbe.request.documentAttack.UpdateDocumentAttackRequest;
import com.example.csvccdshustbe.response.documentAttack.FindAllDocumentAttackResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DocumentAttackService {

    Page<FindAllDocumentAttackResponse> findAllDocumentAttackActiveResponse(FindAllDocumentAttackRequest  request);

    void createDocumentAttack(CreateDocumentAttackRequest request) throws ValidateFiledException;
    void updateDocumentAttack(UpdateDocumentAttackRequest request) throws ValidateFiledException;

    void deleteDocumentAttackByIdDA(Integer idDocumentAttack);

    DocumentAttack findDocumentAttackByIdDocumentAndStatus(Integer idDocumentAttack, Integer status);
}
