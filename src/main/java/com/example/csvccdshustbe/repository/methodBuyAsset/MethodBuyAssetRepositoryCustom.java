package com.example.csvccdshustbe.repository.methodBuyAsset;

import com.example.csvccdshustbe.entity.MethodBuyAsset;
import com.example.csvccdshustbe.request.methodBuyAsset.FindAllMethodBuyAssetPickedRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface MethodBuyAssetRepositoryCustom {

    Page<MethodBuyAsset> findAllActiveMethodBuyAsset(FindAllMethodBuyAssetPickedRequest request, Pageable pageable);

    Optional<MethodBuyAsset> findMethodBuyAssetByTitle(String title);

    Optional<MethodBuyAsset> findMethodBuyAssetById(Integer idMethodBuyAsset);
}
