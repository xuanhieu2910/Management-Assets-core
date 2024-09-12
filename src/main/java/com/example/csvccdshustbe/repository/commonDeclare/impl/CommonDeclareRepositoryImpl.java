package com.example.csvccdshustbe.repository.commonDeclare.impl;

import com.example.csvccdshustbe.dto.declare.CommonDeclareDetailsDto;
import com.example.csvccdshustbe.repository.commonDeclare.CommonDeclareRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

public class CommonDeclareRepositoryImpl implements CommonDeclareRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<CommonDeclareDetailsDto> findCommonDeclareDetailDtoById(Integer idCommonDeclare) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select commonDeclare.id_other_declare, commonDeclare.id_asset, " +
                "       commonDeclare.specification, commonDeclare.id_type_declare_asset, " +
                "       commonDeclare.time_created, commonDeclare.time_modified, " +
                "       ty.name nameTypeDeclareAsset " +
                "from common_declare commonDeclare " +
                "    left join type_declare_asset ty on commonDeclare.id_type_declare_asset = ty.id_type_declare_asset " +
                "where commonDeclare.id_other_declare = :idOtherDeclare ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idOtherDeclare", idCommonDeclare);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                CommonDeclareDetailsDto detailsDto = new CommonDeclareDetailsDto();
                detailsDto.setIdOtherDeclare(ValueUtil.getIntegerByObject(obj[0]));
                detailsDto.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                detailsDto.setSpecification(ValueUtil.getStringByObject(obj[2]));
                detailsDto.setIdTypeDeclareAsset(ValueUtil.getIntegerByObject(obj[3]));
                detailsDto.setTimeCreated(ValueUtil.getStringByObject(obj[4]));
                detailsDto.setTimeModified(ValueUtil.getStringByObject(obj[5]));
                detailsDto.setNameTypeDeclare(ValueUtil.getStringByObject(obj[6]));
                return Optional.of(detailsDto);
            }
        }
        return Optional.empty();
    }
}
