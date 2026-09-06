package com.example.csvccdshustbe.repository.requestStakeHolder;

import com.example.csvccdshustbe.entity.RequestStakeHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestStakeHolderRepository extends JpaRepository<RequestStakeHolder, Integer>,
        RequestStakeHolderRepositoryCustom {
}
