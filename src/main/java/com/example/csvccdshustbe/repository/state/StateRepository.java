package com.example.csvccdshustbe.repository.state;

import com.example.csvccdshustbe.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<State, Integer>, StateRepositoryCustom {

}
