package com.example.csvccdshustbe.repository.noShapeOriginalAssetOther;


import com.example.csvccdshustbe.entity.NoShapeOriginalAssetOther;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoShapeOriginalAssetOtherRepository extends JpaRepository<NoShapeOriginalAssetOther, Integer>,NoShapeOriginalAssetOtherRepositoryCustom {
}
