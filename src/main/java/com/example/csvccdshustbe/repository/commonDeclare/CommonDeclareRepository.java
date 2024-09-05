package com.example.csvccdshustbe.repository.commonDeclare;

import com.example.csvccdshustbe.entity.CommonDeclare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommonDeclareRepository extends JpaRepository<CommonDeclare, Integer>, CommonDeclareRepositoryCustom {
}
