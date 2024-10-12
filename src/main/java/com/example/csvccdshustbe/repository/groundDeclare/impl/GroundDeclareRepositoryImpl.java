package com.example.csvccdshustbe.repository.groundDeclare.impl;

import com.example.csvccdshustbe.dto.declare.GroundDeclareDetailsDto;
import com.example.csvccdshustbe.entity.GroundDeclare;
import com.example.csvccdshustbe.repository.groundDeclare.GroundDeclareRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

public class GroundDeclareRepositoryImpl implements GroundDeclareRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public Optional<GroundDeclareDetailsDto> findGroundDeclareDetailsDtoById(Integer idGroundDeclare) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select groundDeclare.id_ground_declare, groundDeclare.id_asset, groundDeclare.id_goals_use_ground, " +
                "       groundDeclare.workplace, groundDeclare.hdsn_no_bussiness, groundDeclare.hdsn_bussiness, " +
                "       groundDeclare.hdsn_rent, groundDeclare.hdsn_bonds, groundDeclare.live_place, groundDeclare.blank_place, " +
                "       groundDeclare.encroached_place, groundDeclare.synthetic_use, groundDeclare.other_use, " +
                "       groundDeclare.acreage, groundDeclare.license_certificate_use_ground, groundDeclare.date_license_certificate_use_ground, " +
                "       groundDeclare.number_decision_deliver_ground, groundDeclare.date_number_decision_deliver_ground, " +
                "       groundDeclare.contract_number_rent_ground, groundDeclare.date_contract_number_rent_ground, " +
                "       groundDeclare.another_contract, groundDeclare.time_created, groundDeclare.time_modified, " +
                "       groundDeclare.id_type_declare_asset, groundDeclare.contract_number_transfer_ground, " +
                "       groundDeclare.date_contract_number_transfer_ground, groundDeclare.number_decision_rent_ground, " +
                "       groundDeclare.date_number_decision_rent_ground, ty.name nameTypeDeclareAsset, go.name nameGoalsUseGround " +
                "from ground_declare groundDeclare " +
                "    left join type_declare_asset ty on groundDeclare.id_type_declare_asset = ty.id_type_declare_asset " +
                "    left join goals_use_ground go on groundDeclare.id_goals_use_ground = go.id_goals_use_ground " +
                "where groundDeclare.id_ground_declare = :idGroundDeclare ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idGroundDeclare", idGroundDeclare);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj: result){
                GroundDeclareDetailsDto detailsDto = new GroundDeclareDetailsDto();
                detailsDto.setIdGroundDeclare(ValueUtil.getIntegerByObject(obj[0]));
                detailsDto.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                detailsDto.setIdGoalsUseGround(ValueUtil.getIntegerByObject(obj[2]));
                detailsDto.setWorkplace(ValueUtil.getDoubleByObject(obj[3]));
                detailsDto.setHdsnNoBussiness(ValueUtil.getDoubleByObject(obj[4]));
                detailsDto.setHdsnBussiness(ValueUtil.getDoubleByObject(obj[5]));
                detailsDto.setHdsnRent(ValueUtil.getDoubleByObject(obj[6]));
                detailsDto.setHdsnBonds(ValueUtil.getDoubleByObject(obj[7]));
                detailsDto.setLivePlace(ValueUtil.getDoubleByObject(obj[8]));
                detailsDto.setBlankPlace(ValueUtil.getDoubleByObject(obj[9]));
                detailsDto.setEncroachedPlace(ValueUtil.getDoubleByObject(obj[10]));
                detailsDto.setSyntheticUse(ValueUtil.getDoubleByObject(obj[11]));
                detailsDto.setOtherUse(ValueUtil.getDoubleByObject(obj[12]));
                detailsDto.setAcreage(ValueUtil.getDoubleByObject(obj[13]));
                detailsDto.setLicenseCertificateUseGround(ValueUtil.getStringByObject(obj[14]));
                detailsDto.setDateLicenseCertificateUseGround(ValueUtil.getStringByObject(obj[15]));
                detailsDto.setNumberDecisionDeliverGround(ValueUtil.getStringByObject(obj[16]));
                detailsDto.setDateNumberDecisionDeliverGround(ValueUtil.getStringByObject(obj[17]));
                detailsDto.setContractNumberRentGround(ValueUtil.getStringByObject(obj[18]));
                detailsDto.setDateContractNumberRentGround(ValueUtil.getStringByObject(obj[19]));
                detailsDto.setAnotherContract(ValueUtil.getStringByObject(obj[20]));
                detailsDto.setTimeCreated(ValueUtil.getStringByObject(obj[21]));
                detailsDto.setTimeModified(ValueUtil.getStringByObject(obj[22]));
                detailsDto.setIdTypeDeclareAsset(ValueUtil.getIntegerByObject(obj[23]));
                detailsDto.setContractNumberTransferGround(ValueUtil.getStringByObject(obj[24]));
                detailsDto.setDateContractNumberTransferGround(ValueUtil.getStringByObject(obj[25]));
                detailsDto.setNumberDecisionRentGround(ValueUtil.getStringByObject(obj[26]));
                detailsDto.setDateNumberDecisionRentGround(ValueUtil.getStringByObject(obj[27]));
                detailsDto.setNameTypeDeclareAsset(ValueUtil.getStringByObject(obj[28]));
                detailsDto.setNameGoalUseGround(ValueUtil.getStringByObject(obj[29]));
                return Optional.of(detailsDto);
            }
        }
        return Optional.empty();
    }

    @Modifying
    @Transactional
    @Override
    public void deleteGroundDeclareById(Integer idInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append(" delete from ground_declare " +
                " where ground_declare.id_ground_declare = :idGroundDeclare ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idGroundDeclare", idInstance);
        query.executeUpdate();
    }

    @Override
    public Optional<GroundDeclare> findGroundDeclareById(Integer idInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select groundDeclare.id_ground_declare, groundDeclare.id_asset, groundDeclare.id_goals_use_ground,  " +
                "     groundDeclare.workplace, groundDeclare.hdsn_no_bussiness, groundDeclare.hdsn_bussiness,  " +
                "     groundDeclare.hdsn_rent, groundDeclare.hdsn_bonds, groundDeclare.live_place, groundDeclare.blank_place,  " +
                "     groundDeclare.encroached_place, groundDeclare.synthetic_use, groundDeclare.other_use,  " +
                "    groundDeclare.acreage, groundDeclare.license_certificate_use_ground, groundDeclare.date_license_certificate_use_ground,     " +
                "       groundDeclare.number_decision_deliver_ground, groundDeclare.date_number_decision_deliver_ground,     " +
                "       groundDeclare.contract_number_rent_ground, groundDeclare.date_contract_number_rent_ground,     " +
                "       groundDeclare.another_contract, groundDeclare.time_created, groundDeclare.time_modified,     " +
                "       groundDeclare.id_type_declare_asset, groundDeclare.contract_number_transfer_ground,     " +
                "       groundDeclare.date_contract_number_transfer_ground, groundDeclare.number_decision_rent_ground,     " +
                "       groundDeclare.date_number_decision_rent_ground   " +
                "  from ground_declare groundDeclare     " +
                "  where groundDeclare.id_ground_declare = :idGroundDeclare  ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idGroundDeclare", idInstance);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                GroundDeclare groundDeclare = new GroundDeclare();
                groundDeclare.setIdGroundDeclare(ValueUtil.getIntegerByObject(obj[0]));
                groundDeclare.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                groundDeclare.setIdGoalsUseGround(ValueUtil.getIntegerByObject(obj[2]));
                groundDeclare.setWorkplace(ValueUtil.getDoubleByObject(obj[3]));
                groundDeclare.setHdsnNoBussiness(ValueUtil.getDoubleByObject(obj[4]));
                groundDeclare.setHdsnBussiness(ValueUtil.getDoubleByObject(obj[5]));
                groundDeclare.setHdsnRent(ValueUtil.getDoubleByObject(obj[6]));
                groundDeclare.setHdsnBonds(ValueUtil.getDoubleByObject(obj[7]));
                groundDeclare.setLivePlace(ValueUtil.getDoubleByObject(obj[8]));
                groundDeclare.setBlankPlace(ValueUtil.getDoubleByObject(obj[9]));
                groundDeclare.setEncroachedPlace(ValueUtil.getDoubleByObject(obj[10]));
                groundDeclare.setSyntheticUse(ValueUtil.getDoubleByObject(obj[11]));
                groundDeclare.setOtherUse(ValueUtil.getDoubleByObject(obj[12]));
                groundDeclare.setAcreage(ValueUtil.getDoubleByObject(obj[13]));
                groundDeclare.setLicenseCertificateUseGround(ValueUtil.getStringByObject(obj[14]));
                groundDeclare.setDateLicenseCertificateUseGround(ValueUtil.getStringByObject(obj[15]));
                groundDeclare.setNumberDecisionDeliverGround(ValueUtil.getStringByObject(obj[16]));
                groundDeclare.setDateNumberDecisionDeliverGround(ValueUtil.getStringByObject(obj[17]));
                groundDeclare.setContractNumberRentGround(ValueUtil.getStringByObject(obj[18]));
                groundDeclare.setDateContractNumberRentGround(ValueUtil.getStringByObject(obj[19]));
                groundDeclare.setAnotherContract(ValueUtil.getStringByObject(obj[20]));
                groundDeclare.setTimeCreated(ValueUtil.getStringByObject(obj[21]));
                groundDeclare.setTimeModified(ValueUtil.getStringByObject(obj[22]));
                groundDeclare.setIdTypeDeclareAsset(ValueUtil.getIntegerByObject(obj[23]));
                groundDeclare.setContractNumberTransferGround(ValueUtil.getStringByObject(obj[24]));
                groundDeclare.setDateContractNumberTransferGround(ValueUtil.getStringByObject(obj[25]));
                groundDeclare.setNumberDecisionRentGround(ValueUtil.getStringByObject(obj[26]));
                groundDeclare.setDateNumberDecisionRentGround(ValueUtil.getStringByObject(obj[27]));
                return Optional.of(groundDeclare);
            }
        }
        return Optional.empty();
    }
}
