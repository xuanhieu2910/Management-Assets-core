package com.example.csvccdshustbe.service.documentAttack.impl;

import com.example.csvccdshustbe.entity.DocumentAttack;
import com.example.csvccdshustbe.repository.documentAttack.DocumentAttackRepository;
import com.example.csvccdshustbe.response.documentAttack.FindAllDocumentAttackResponse;
import com.example.csvccdshustbe.service.documentAttack.DocumentAttackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentAttackServiceImpl implements DocumentAttackService {


    @Autowired
    DocumentAttackRepository documentAttackRepository;

    public DocumentAttackServiceImpl(DocumentAttackRepository documentAttackRepository) {

        this.documentAttackRepository = documentAttackRepository;
    }
    @Override
    public List<FindAllDocumentAttackResponse> findAllDocumentAttackResponseByStatus(Integer status){
        return convertToFindAllDocumentAttack(documentAttackRepository.findAllDocumentAttackResponseByStatus(status));
    }

    private List<FindAllDocumentAttackResponse> convertToFindAllDocumentAttack(List<DocumentAttack> allDocumentAttackByStatus) {
        List<FindAllDocumentAttackResponse> responses = new ArrayList<>();
        for (DocumentAttack documentAttack : allDocumentAttackByStatus){
            FindAllDocumentAttackResponse response = new FindAllDocumentAttackResponse();
            response.setIdDocumentAttack(documentAttack.getIdDocumentAttack());
            response.setName(documentAttack.getName());
            responses.add(response);
        }
        return responses;
    }

    @Override
    public String createDocumentAttack(DocumentAttack documentAttack) {
        documentAttackRepository.save(documentAttack);
        return "Success";
    }

    @Override
    public String updateDocumentAttack(DocumentAttack documentAttack) {
        documentAttackRepository.save(documentAttack);

        return "success";
    }

    @Override
    public String deleteDocumentAttack(Integer documentAttackId) {
        documentAttackRepository.deleteById(documentAttackId);
        return "Delete success";
    }

    @Override
    public DocumentAttack getDocumentAttack(Integer documentAttackId) {
        DocumentAttack documentAttack = documentAttackRepository.findById(documentAttackId).get();
        return documentAttack;

    }
}
