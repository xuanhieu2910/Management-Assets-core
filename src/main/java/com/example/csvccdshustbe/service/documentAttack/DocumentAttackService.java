package com.example.csvccdshustbe.service.documentAttack;

import com.example.csvccdshustbe.entity.DocumentAttack;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.documentAttack.*;
import com.example.csvccdshustbe.response.documentAttack.FindAllDocumentAttackResponse;
import com.example.csvccdshustbe.response.documentAttack.FindAllDocumentAttackVisibleResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DocumentAttackService {

    Page<FindAllDocumentAttackVisibleResponse> findAllDocumentAttackVisibleResponse(FindAllDocumentAttackVisibleRequest request);
    Page<FindAllDocumentAttackResponse> findAllDocumentAttackResponse(FindAllDocumentAttackRequest request);

    void createDocumentAttack(CreateDocumentAttackRequest request) throws ValidateFiledException;
    void updateDocumentAttack(UpdateDocumentAttackRequest request) throws ValidateFiledException;

    void deleteDocumentAttackByIdDA(Integer idDocumentAttack) throws ValidateFiledException;

    DocumentAttack findDocumentAttackByIdDocumentAndStatus(Integer idDocumentAttack, Integer status);

    void updateStatusDocumentAttack(UpdateStatusDocumentAttackRequest request) throws ValidateFiledException;
}
