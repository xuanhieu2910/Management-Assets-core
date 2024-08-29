package com.example.csvccdshustbe.repository.documentAttack;

import com.example.csvccdshustbe.entity.DocumentAttack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentAttackRepository extends JpaRepository<DocumentAttack,Integer>,DocumentAttackRepositoryCustom {

}
