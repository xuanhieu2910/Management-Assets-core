package com.example.csvccdshustbe.repository.shapeOriginalAssetOther;

import com.example.csvccdshustbe.entity.ShapeOriginalAssetOther;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShapeOriginalAssetOtherRepository extends JpaRepository<ShapeOriginalAssetOther, Integer>, ShapeOriginalAssetOtherRepositoryCustom {

}
