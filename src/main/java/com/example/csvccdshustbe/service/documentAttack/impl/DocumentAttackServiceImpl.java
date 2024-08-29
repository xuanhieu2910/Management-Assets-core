package com.example.csvccdshustbe.service.documentAttack.impl;

import com.example.csvccdshustbe.entity.DocumentAttack;
import com.example.csvccdshustbe.repository.documentAttack.DocumentAttackRepository;
import com.example.csvccdshustbe.service.documentAttack.DocumentAttackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentAttackServiceImpl implements DocumentAttackService {
    @Autowired
    DocumentAttackRepository documentAttackRepository;
    @Override
    public List<DocumentAttack> findAllDocumentAttack(){
        return documentAttackRepository.findAllDocumentAttack();
    }
}
