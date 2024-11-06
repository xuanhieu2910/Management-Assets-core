package com.example.csvccdshustbe.repository.requestDetailStakeHolder;

import com.example.csvccdshustbe.entity.RequestDetailStakeHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestDetailStakeHolderRepository extends JpaRepository<RequestDetailStakeHolder, Integer>,
                                                    RequestDetailStakeHolderRepositoryCustom{
}
