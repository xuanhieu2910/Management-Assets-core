package com.example.csvccdshustbe.repository.reason;

import com.example.csvccdshustbe.entity.Reason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReasonRepository extends JpaRepository<Reason, Integer>, ReasonRepositoryCustom  {
}
