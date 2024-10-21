package com.example.csvccdshustbe.service.document;

import com.example.csvccdshustbe.entity.Document;

import java.util.Optional;

public interface DocumentService {

    Document findDocumentByCodeAndIdDepartment(String code, Integer idDepartment);

}
