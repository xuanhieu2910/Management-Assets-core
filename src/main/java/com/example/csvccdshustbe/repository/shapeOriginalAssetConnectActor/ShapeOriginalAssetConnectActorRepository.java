package com.example.csvccdshustbe.repository.shapeOriginalAssetConnectActor;

import com.example.csvccdshustbe.entity.ShapeOriginalAssetConnectActor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShapeOriginalAssetConnectActorRepository extends JpaRepository<ShapeOriginalAssetConnectActor, Integer>,
                ShapeOriginalAssetConnectActorRepositoryCustom{
}
