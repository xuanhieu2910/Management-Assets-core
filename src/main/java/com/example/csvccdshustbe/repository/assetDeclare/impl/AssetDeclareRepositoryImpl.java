package com.example.csvccdshustbe.repository.assetDeclare.impl;

import com.example.csvccdshustbe.dto.declare.BluePrintDeclareDto;
import com.example.csvccdshustbe.repository.assetDeclare.AssetDeclareRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

public class AssetDeclareRepositoryImpl implements AssetDeclareRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public Optional<BluePrintDeclareDto> findBluePrintAssetDeclareDtoByIdAsset(Integer idAsset) {
        StringBuilder sb = new StringBuilder();
        sb.append("select decl.hard_code typeDeclare, decl.id_declare, " +
                "       decl.name nameDeclare, assetDeclare.id_instance " +
                "from asset asset " +
                "    inner join asset_declare assetDeclare on asset.id_asset = assetDeclare.id_asset " +
                "    inner join `declare` decl on assetDeclare.id_declare = decl.id_declare " +
                "where asset.id_asset = :idAsset ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idAsset", idAsset);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                BluePrintDeclareDto bluePrintDeclareDto = new BluePrintDeclareDto();
                bluePrintDeclareDto.setTypeDeclare(ValueUtil.getStringByObject(obj[0]));
                bluePrintDeclareDto.setIdDeclare(ValueUtil.getIntegerByObject(obj[1]));
                bluePrintDeclareDto.setNameDeclare(ValueUtil.getStringByObject(obj[2]));
                bluePrintDeclareDto.setIdInstance(ValueUtil.getIntegerByObject(obj[3]));
                return Optional.of(bluePrintDeclareDto);
            }
        }
        return Optional.empty();
    }

    @Override
    public void deleteAssetDeclareByIdInstanceAndIdDeclare(Integer idInstance, Integer idDeclare) {
        StringBuilder sb = new StringBuilder();
        sb.append(" delete from asset_declare assetDeclare " +
                "where assetDeclare.id_instance = :idInstance " +
                "and assetDeclare.id_declare = :idDeclare ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idInstance", idInstance);
        query.setParameter("idDeclare", idDeclare);
        query.executeUpdate();
    }
}
