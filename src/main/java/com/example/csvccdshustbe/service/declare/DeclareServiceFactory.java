package com.example.csvccdshustbe.service.declare;


import com.example.csvccdshustbe.entity.CommonDeclare;
import com.example.csvccdshustbe.entity.GroundDeclare;
import com.example.csvccdshustbe.entity.HouseDeclare;
import com.example.csvccdshustbe.entity.IDeclare;
import com.example.csvccdshustbe.enums.EnumDeclareFactory;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.service.declare.commonDeclare.CommonDeclareService;
import com.example.csvccdshustbe.service.declare.groundDeclare.GroundDeclareService;
import com.example.csvccdshustbe.service.declare.houseDeclare.HouseDeclareService;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.ValueUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DeclareServiceFactory {


    @Autowired
    CommonDeclareService commonDeclareService;
    @Autowired
    HouseDeclareService houseDeclareService;
    @Autowired
    GroundDeclareService groundDeclareService;


    public IDeclare save(IDeclare declare, Map<String,Object> declareDataAsset) throws ValidateFiledException {
        String typeDeclare = ValueUtil.getStringByObject(declareDataAsset.get(Constants.KEY_TYPE_DECLARE));
        EnumDeclareFactory enumDeclareFactory = Enum.valueOf(EnumDeclareFactory.class, typeDeclare);
        switch (enumDeclareFactory){
            case HouseDeclare -> {
                return houseDeclareService.save((HouseDeclare) declare);
            }
            case GroundDeclare -> {
                return groundDeclareService.save((GroundDeclare) declare);
            }
            case CommonDeclare -> {
                return commonDeclareService.save((CommonDeclare) declare);
            }
            default -> {
                throw new ValidateFiledException("Don't exits type declare!");
            }
        }

    }
}
