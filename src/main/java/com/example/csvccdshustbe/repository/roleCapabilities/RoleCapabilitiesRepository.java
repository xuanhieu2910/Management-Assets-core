package com.example.csvccdshustbe.repository.roleCapabilities;

import com.example.csvccdshustbe.entity.RoleCapabilities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleCapabilitiesRepository extends JpaRepository<RoleCapabilities, Integer>,
        RoleCapabilitiesRepositoryCustom {



}
