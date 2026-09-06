package com.example.csvccdshustbe.repository.document;

import com.example.csvccdshustbe.dto.document.FindAllDocumentAssetDto;
import com.example.csvccdshustbe.dto.document.FindDetailsDocumentDto;
import com.example.csvccdshustbe.dto.document.tool.FindAllDocumentToolDto;
import com.example.csvccdshustbe.dto.process.*;
import com.example.csvccdshustbe.entity.Document;
import com.example.csvccdshustbe.request.document.FindAllDocumentAssetRequest;
import com.example.csvccdshustbe.request.document.tool.FindAllDocumentToolRequest;
import com.example.csvccdshustbe.request.process.*;
import com.example.csvccdshustbe.request.tool.FindAllDocumentByToolRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DocumentRepositoryCustom {

    Optional<Document> findDocumentByCodeAndIdDepartment(String code, Integer idDepartment);
    Optional<Document> findDocumentByIdDepartment(Integer idDepartment);

    Page<FindAllDocumentAssetDto> findAllDocumentAssetDtoByIdsDepartment(FindAllDocumentAssetRequest request, Pageable pageable);
    Optional<FindDetailsDocumentDto> findDetailDocumentByCodeDocument(String codeDocument, List<Integer> idsDepartment);

    Page<FindAllProcessAssetIncreaseDto>
    findAllProcessAssetIncreaseDtoByIdsDepartment(FindAllProcessAssetIncreaseRequest request, Pageable pageable);

    Page<FindAllProcessAssetInventoryDto>
    findAllProcessAssetDocumentInventoryDtoByIdsDepartment(FindAllProcessAssetDocumentInventoryRequest request, Pageable pageable);

    Page<FindAllProcessAssetDecreaseDto>
    findAllProcessAssetDecreaseDtoByIdsDepartment(FindAllProcessAssetDecreaseRequest request, Pageable pageable);

    Page<FindAllProcessAssetChangeDto>
    findAllProcessAssetChangeDtoByIdsDepartment(FindAllProcessAssetChangeRequest request, Pageable pageable);

    Page<FindAllProcessAssetRevaluationDto>
    findAllProcessAssetRevaluationDtoByIdsDepartment(FindAllProcessAssetRevaluationRequest request, Pageable pageable);

    Page<FindAllProcessAssetUpdateInventoryDto>
    findAllProcessAssetUpdateInventoryDtoByIdsDepartment(FindAllProcessAssetUpdateInventoryRequest request, Pageable pageable);

    Optional<Document> findDocumentByIdProcess(Integer idProcess);
    Optional<Document> findDocumentByCodeDocumentAndStatus(String codeDocument, Integer status);

    Page<FindAllDocumentToolDto> findAllDocumentToolIncreaseDtoByIdsDepartment(FindAllDocumentToolRequest request, Pageable pageable);
    Page<FindAllDocumentToolDto> findAllDocumentToolDecreaseDtoByIdsDepartment(FindAllDocumentToolRequest request, Pageable pageable);

    Page<FindAllDocumentToolDto> findAllToolDocumentInventoryDtoByIdsDepartment(FindAllDocumentToolRequest request, Pageable pageable);

    Page<FindAllDocumentToolDto> findAllToolDocumentUpdateInventoryDtoByIdsDepartment(FindAllDocumentToolRequest request, Pageable pageable);


    Page<FindAllDocumentAssetDto> findAllDocumentByToolDtoByIdsDepartment(FindAllDocumentByToolRequest request, Pageable pageable);
}
