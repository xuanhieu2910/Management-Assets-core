package com.example.csvccdshustbe.repository.typeUse;

import com.example.csvccdshustbe.entity.TypeUse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeUseRepository extends JpaRepository<TypeUse,Integer>,TypeUseRepositoryCustom {
}
