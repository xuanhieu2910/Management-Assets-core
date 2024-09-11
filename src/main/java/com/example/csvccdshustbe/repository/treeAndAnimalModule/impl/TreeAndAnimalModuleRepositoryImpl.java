package com.example.csvccdshustbe.repository.treeAndAnimalModule.impl;

import com.example.csvccdshustbe.entity.AnimalTreeModule;
import com.example.csvccdshustbe.repository.treeAndAnimalModule.TreeAndAnimalModuleRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

public class TreeAndAnimalModuleRepositoryImpl implements TreeAndAnimalModuleRepositoryCustom {

     @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<AnimalTreeModule> findAnimalTreeModulesById(Integer idAnimalTree) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select animalTree.id_animal_tree_module, animalTree.id_asset, " +
                "       animalTree.publish_date, animalTree.id_type_use, " +
                "       animalTree.id_country_producer " +
                "from animal_tree_module animalTree " +
                "where animalTree.id_animal_tree_module = :idAnimalTreeModule ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idAnimalTreeModule", idAnimalTree);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                AnimalTreeModule animalTreeModule = new AnimalTreeModule();
                animalTreeModule.setIdAnimalTreeModule(ValueUtil.getIntegerByObject(obj[0]));
                animalTreeModule.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                animalTreeModule.setPublishDate(ValueUtil.getStringByObject(obj[2]));
                animalTreeModule.setIdTypeUse(ValueUtil.getIntegerByObject(obj[3]));
                animalTreeModule.setIdCountryProducer(ValueUtil.getIntegerByObject(obj[4]));
                return Optional.of(animalTreeModule);
            }
        }
        return Optional.empty();
    }
}
