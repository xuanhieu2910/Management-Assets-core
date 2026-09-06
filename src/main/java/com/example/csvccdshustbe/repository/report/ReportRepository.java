package com.example.csvccdshustbe.repository.report;

import com.example.csvccdshustbe.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer>, ReportRepositoryCustom {
}
