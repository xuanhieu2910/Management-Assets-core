package com.example.csvccdshustbe.service.original.shape.assetTransfer.impl;

import com.example.csvccdshustbe.dto.original.shape.ShapeOriginalAssetTransferDetailsDto;
import com.example.csvccdshustbe.entity.ShapeOriginalAssetTransfer;
import com.example.csvccdshustbe.repository.shapeOriginalAssetTransfer.ShapeOriginalAssetTransferRepository;
import com.example.csvccdshustbe.service.original.shape.assetTransfer.OriginalAssetTransferService;
import com.example.csvccdshustbe.utility.ValueUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Map;
import java.util.Optional;

@Service
public class OriginalAssetTransferServiceImpl implements OriginalAssetTransferService {

    @Autowired
    ShapeOriginalAssetTransferRepository shapeOriginalAssetTransferRepository;

    @Override
    public ShapeOriginalAssetTransfer save(ShapeOriginalAssetTransfer shapeOriginalAssetTransfer) {
        return shapeOriginalAssetTransferRepository.save(shapeOriginalAssetTransfer);
    }

    @Override
    public Map<String, Object> findOriginalAssetTransferById(Integer idInstance) throws IllegalAccessException {
        Optional<ShapeOriginalAssetTransferDetailsDto> detailsDto =
                shapeOriginalAssetTransferRepository.findShapeOriginalAssetTransferDetailsDtoById(idInstance);
        if (!detailsDto.isPresent()) {
            throw new NotFoundException("Don't exits shape original asset transfer!");
        }
        return ValueUtil.convertObjectToMap(detailsDto);
    }
}
