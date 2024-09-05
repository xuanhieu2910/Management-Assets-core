package com.example.csvccdshustbe.repository.shapeOriginalAssetConnectWoActor;

import com.example.csvccdshustbe.entity.ShapeOriginalAssetConnectWoActor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShapeOriginalAssetConnectWoActorRepository extends JpaRepository<ShapeOriginalAssetConnectWoActor, Integer>,
                ShapeOriginalAssetConnectWoActorRepositoryCustom{
}
