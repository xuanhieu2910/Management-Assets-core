package com.example.csvccdshustbe.repository.document;

import com.example.csvccdshustbe.dto.asset.AssetBluePrintDto;
import com.example.csvccdshustbe.dto.document.DetailsDocumentBluePrintDto;
import com.example.csvccdshustbe.dto.document.FindAllDocumentAssetDto;
import com.example.csvccdshustbe.entity.Document;
import com.example.csvccdshustbe.request.document.FindAllDocumentAssetRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface DocumentRepositoryCustom {
    Optional<Document> findDocumentByCodeAndIdDepartment(String code, Integer idDepartment);
    Optional<Document> findDocumentByIdDepartment(Integer idDepartment);

    Page<FindAllDocumentAssetDto> findAllDocumentAssetDtoByIdsDepartment(FindAllDocumentAssetRequest request, Pageable pageable);
    Optional<DetailsDocumentBluePrintDto> findDetailDocumentByCodeDocument(String codeDocument);

}
