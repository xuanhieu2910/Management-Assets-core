package com.example.csvccdshustbe.repository.dataDocument;

import com.example.csvccdshustbe.entity.DataDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataDocumentRepository extends JpaRepository<DataDocument, Integer>, DataDocumentRepositoryCustom {
}
