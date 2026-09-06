package com.example.csvccdshustbe.repository.requestData;

import com.example.csvccdshustbe.entity.RequestData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestDataRepository extends JpaRepository<RequestData, Integer>, RequestDataRepositoryCustom {
}
