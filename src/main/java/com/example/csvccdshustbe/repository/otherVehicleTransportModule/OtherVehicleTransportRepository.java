package com.example.csvccdshustbe.repository.otherVehicleTransportModule;

import com.example.csvccdshustbe.entity.OtherVehicleTransportModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtherVehicleTransportRepository extends JpaRepository<OtherVehicleTransportModule, Integer>,
                OtherVehicleTransportRepositoryCustom{
}
