package com.example.csvccdshustbe.service.original.noShape.assetTranfer.impl;

import com.example.csvccdshustbe.dto.original.noShape.NoShapeOriginalAssetTransferDetailsDto;
import com.example.csvccdshustbe.entity.NoShapeOriginalAssetTransfer;
import com.example.csvccdshustbe.repository.noShapeOriginalAssetTransfer.NoShapeOriginalAssetTransferRepository;
import com.example.csvccdshustbe.service.original.noShape.assetTranfer.NoOriginalAssetTransferService;
import com.example.csvccdshustbe.utility.ValueUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Map;
import java.util.Optional;

@Service
public class NoOriginalAssetTransferServiceImpl implements NoOriginalAssetTransferService {

    @Autowired
    NoShapeOriginalAssetTransferRepository shapeOriginalAssetTransferRepository;

    @Override
    public NoShapeOriginalAssetTransfer save(NoShapeOriginalAssetTransfer transfer) {
        return shapeOriginalAssetTransferRepository.save(transfer);
    }

    @Override
    public Map<String, Object> findNoOriginalAssetTransferById(Integer idInstance) throws IllegalAccessException {
        Optional<NoShapeOriginalAssetTransferDetailsDto> detailsDto =
                shapeOriginalAssetTransferRepository.findNoShapeOriginalAssetTransferDetailsDtoById(idInstance);
        if (!detailsDto.isPresent()) {
            throw new NotFoundException("Don't exits no shape original asset transfer!");
        }
        return ValueUtil.convertObjectToMap(detailsDto);
    }
}
