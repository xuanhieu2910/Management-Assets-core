package com.example.csvccdshustbe.repository.capabilities;

import com.example.csvccdshustbe.entity.Capabilities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CapabilitiesRepository extends JpaRepository<Capabilities, Integer>, CapabilitiesRepositoryCustom {
}
