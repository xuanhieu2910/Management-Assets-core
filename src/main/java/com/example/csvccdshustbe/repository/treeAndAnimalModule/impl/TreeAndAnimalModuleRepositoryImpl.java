package com.example.csvccdshustbe.repository.treeAndAnimalModule.impl;

import com.example.csvccdshustbe.dto.modules.treeAndAnimalModules.TreeAndAnimalModulesDetailsDto;
import com.example.csvccdshustbe.entity.AnimalTreeModule;
import com.example.csvccdshustbe.repository.treeAndAnimalModule.TreeAndAnimalModuleRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

public class TreeAndAnimalModuleRepositoryImpl implements TreeAndAnimalModuleRepositoryCustom {

     @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<TreeAndAnimalModulesDetailsDto> findAnimalTreeModulesDetailsDtoById(Integer idAnimalTree) {
        StringBuilder sb = new StringBuilder();
        sb.append("select animalTree.id_animal_tree_module, animalTree.id_asset,  " +
                "        animalTree.publish_date, animalTree.id_type_use,  " +
                "        animalTree.id_country_producer,  " +
                "        co.name nameCountryProducer, ty.name nameTypeUse,  " +
                "        animalTree.spare_parts_attack  " +
                "from animal_tree_module animalTree  " +
                "        left join country_producer co on animalTree.id_country_producer = co.id_country_producer  " +
                "        left join type_use ty on animalTree.id_type_use = ty.id_type_use  " +
                "where animalTree.id_animal_tree_module = :idAnimalTreeModule  ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idAnimalTreeModule", idAnimalTree);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                TreeAndAnimalModulesDetailsDto animalTreeModule = new TreeAndAnimalModulesDetailsDto();
                animalTreeModule.setIdAnimalTreeModule(ValueUtil.getIntegerByObject(obj[0]));
                animalTreeModule.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                animalTreeModule.setPublishDate(ValueUtil.getStringByObject(obj[2]));
                animalTreeModule.setIdTypeUse(ValueUtil.getIntegerByObject(obj[3]));
                animalTreeModule.setIdCountryProducer(ValueUtil.getIntegerByObject(obj[4]));
                animalTreeModule.setNameCountryProducer(ValueUtil.getStringByObject(obj[5]));
                animalTreeModule.setNameTypeUse(ValueUtil.getStringByObject(obj[6]));
                animalTreeModule.setSparePartsAttack(ValueUtil.getStringByObject(obj[7]));
                return Optional.of(animalTreeModule);
            }
        }
        return Optional.empty();
    }


    @Modifying
    @Transactional
    @Override
    public void deleteTreeAndAnimalModuleById(Integer idInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append(" delete from animal_tree_module   " +
                "where animal_tree_module.id_animal_tree_module = :idAnimalAndTree ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idAnimalAndTree", idInstance);
        query.executeUpdate();
    }

    @Override
    public Optional<AnimalTreeModule> findAnimalTreeModulesByIdTreeAnimal(Integer idInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select ani.id_animal_tree_module, ani.id_asset, " +
                "       ani.publish_date, ani.id_type_use, " +
                "       ani.id_country_producer, ani.spare_parts_attack " +
                "from animal_tree_module ani  " +
                "where ani.id_animal_tree_module = :idAnimalAndTree ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idAnimalAndTree", idInstance);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result){
                AnimalTreeModule animalTreeModule = new AnimalTreeModule();
                animalTreeModule.setIdAnimalTreeModule(ValueUtil.getIntegerByObject(obj[0]));
                animalTreeModule.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                animalTreeModule.setPublishDate(ValueUtil.getStringByObject(obj[2]));
                animalTreeModule.setIdTypeUse(ValueUtil.getIntegerByObject(obj[3]));
                animalTreeModule.setIdCountryProducer(ValueUtil.getIntegerByObject(obj[4]));
                animalTreeModule.setSparePartsAttack(ValueUtil.getStringByObject(obj[5]));
                return Optional.of(animalTreeModule);
            }
        }
        return Optional.empty();
    }
}
