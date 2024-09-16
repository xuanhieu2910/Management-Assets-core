package com.example.csvccdshustbe.repository.documentAttack;

import com.example.csvccdshustbe.entity.DocumentAttack;
import com.example.csvccdshustbe.request.documentAttack.FindAllDocumentAttackRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DocumentAttackRepositoryCustom {
    Page<DocumentAttack> findAllDocumentAttackActiveResponse(FindAllDocumentAttackRequest findAllDocumentAttackRequest,
                                                             Pageable pageable);
    Optional<DocumentAttack> findDocumentAttackByName(String name);
    Optional<DocumentAttack> findDocumentAttackById(Integer idDocumentAttack);

    Optional<DocumentAttack> findDocumentAttackByIdDocumentAndStatus(Integer idDocumentAttack, Integer status);

    boolean isExitsAssetByIdDocumentAttack(Integer idDocumentAttack);
}
