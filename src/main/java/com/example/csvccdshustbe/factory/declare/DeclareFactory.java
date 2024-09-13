package com.example.csvccdshustbe.factory.declare;

import com.example.csvccdshustbe.entity.IDeclare;
import org.springframework.stereotype.Service;

import java.util.Map;


public interface DeclareFactory {

    IDeclare createDeclare(Map<String, Object> mapDeclareRequest);


    IDeclare updateDeclare(Map<String, Object> declareDataAsset, IDeclare iDeclareDetails);
}
