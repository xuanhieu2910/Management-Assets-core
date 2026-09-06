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
    NoShapeOriginalAssetTransferRepository noShapeOriginalAssetTransferRepository;

    @Override
    public NoShapeOriginalAssetTransfer save(NoShapeOriginalAssetTransfer transfer) {
        return noShapeOriginalAssetTransferRepository.save(transfer);
    }

    @Override
    public NoShapeOriginalAssetTransferDetailsDto findNoOriginalAssetTransferById(Integer idInstance) throws IllegalAccessException {
        Optional<NoShapeOriginalAssetTransferDetailsDto> detailsDto =
                noShapeOriginalAssetTransferRepository.findNoShapeOriginalAssetTransferDetailsDtoById(idInstance);
        if (detailsDto.isEmpty()) {
            throw new NotFoundException("Don't exits no shape original asset transfer!");
        }
        return detailsDto.get();
    }

    @Override
    public void deleteNoShapeOriginalAssetTransferById(Integer idInstance) {
        noShapeOriginalAssetTransferRepository.deleteNoShapeOriginalAssetTransferById(idInstance);
    }

    @Override
    public NoShapeOriginalAssetTransfer findNoShapeOriginalAssetTransferById(Integer idInstance) {
        Optional<NoShapeOriginalAssetTransfer> assetTransfer =
                noShapeOriginalAssetTransferRepository.findNoShapeOriginalAssetTransferById(idInstance);
        if (assetTransfer.isEmpty()){
            throw new NotFoundException("Don't exits no shape original asset transfer!");
        }
        return assetTransfer.get();
    }
}
