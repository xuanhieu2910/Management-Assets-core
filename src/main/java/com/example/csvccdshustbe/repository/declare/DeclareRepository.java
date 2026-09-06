package com.example.csvccdshustbe.repository.declare;

import com.example.csvccdshustbe.entity.Declare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeclareRepository extends JpaRepository<Declare, Integer>, DeclareRepositoryCustom {


}
