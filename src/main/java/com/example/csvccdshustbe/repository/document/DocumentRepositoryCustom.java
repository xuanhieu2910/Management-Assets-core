package com.example.csvccdshustbe.repository.document;

import com.example.csvccdshustbe.entity.Document;

import java.util.Optional;

public interface DocumentRepositoryCustom {
    Optional<Document> findDocumentByCodeAndIdDepartment(String code, Integer idDepartment);
    Optional<Document> findDocumentByIdDepartment(Integer idDepartment);
}
