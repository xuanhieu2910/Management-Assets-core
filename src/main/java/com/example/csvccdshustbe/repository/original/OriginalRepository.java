package com.example.csvccdshustbe.repository.original;

import com.example.csvccdshustbe.entity.Original;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OriginalRepository extends JpaRepository<Original, Integer>, OriginalRepositoryCustom {



}
