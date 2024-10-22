package com.example.csvccdshustbe.repository.request;

import com.example.csvccdshustbe.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer>, RequestRepositoryCustom {
}
