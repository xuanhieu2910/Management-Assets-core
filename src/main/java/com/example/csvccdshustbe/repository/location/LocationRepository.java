package com.example.csvccdshustbe.repository.location;

import com.example.csvccdshustbe.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location,Integer>,LocationRepositoryCustom {
}
