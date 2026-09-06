package com.example.csvccdshustbe.repository.originalOfFormation;

import com.example.csvccdshustbe.entity.OriginalOfFormation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OriginalOfFormationRepository extends JpaRepository<OriginalOfFormation, Integer>, OriginalOfFormationRepositoryCustom {


}
