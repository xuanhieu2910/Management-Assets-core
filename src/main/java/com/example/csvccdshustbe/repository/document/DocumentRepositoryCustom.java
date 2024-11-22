package com.example.csvccdshustbe.repository.document;

import com.example.csvccdshustbe.dto.document.FindAllDocumentAssetDto;
import com.example.csvccdshustbe.dto.document.FindDetailsDocumentDto;
import com.example.csvccdshustbe.dto.process.FindAllProcessAssetDecreaseDto;
import com.example.csvccdshustbe.dto.process.FindAllProcessAssetIncreaseDto;
import com.example.csvccdshustbe.dto.process.FindAllProcessAssetInventoryDto;
import com.example.csvccdshustbe.entity.Document;
import com.example.csvccdshustbe.request.document.FindAllDocumentAssetRequest;
import com.example.csvccdshustbe.request.process.FindAllProcessAssetDecreaseRequest;
import com.example.csvccdshustbe.request.process.FindAllProcessAssetIncreaseRequest;
import com.example.csvccdshustbe.request.process.FindAllProcessAssetInventoryRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DocumentRepositoryCustom {

    Optional<Document> findDocumentByCodeAndIdDepartment(String code, Integer idDepartment);
    Optional<Document> findDocumentByIdDepartment(Integer idDepartment);

    Page<FindAllDocumentAssetDto> findAllDocumentAssetDtoByIdsDepartment(FindAllDocumentAssetRequest request, Pageable pageable);
    Optional<FindDetailsDocumentDto> findDetailDocumentByCodeDocument(String codeDocument, List<Integer> idsDepartment);

    Page<FindAllProcessAssetIncreaseDto> findAllProcessAssetIncreaseDtoByIdsDepartment(FindAllProcessAssetIncreaseRequest request,
                                                                                       Pageable pageable);

    Page<FindAllProcessAssetInventoryDto> findAllProcessAssetInventoryDtoByIdsDepartment(FindAllProcessAssetInventoryRequest request,
                                                                                         Pageable pageable);

    Page<FindAllProcessAssetDecreaseDto> findAllProcessAssetDecreaseDtoByIdsDepartment(FindAllProcessAssetDecreaseRequest request,
                                                                                       Pageable pageable);
}
