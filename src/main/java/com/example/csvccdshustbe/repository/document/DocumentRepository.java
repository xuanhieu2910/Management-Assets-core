package com.example.csvccdshustbe.repository.document;

import com.example.csvccdshustbe.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer>, DocumentRepositoryCustom {
}
