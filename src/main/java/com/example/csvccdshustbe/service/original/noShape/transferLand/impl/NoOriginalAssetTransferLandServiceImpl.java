package com.example.csvccdshustbe.service.original.noShape.transferLand.impl;

import com.example.csvccdshustbe.dto.original.noShape.NoShapeOriginalAssetTransferLandDetailsDto;
import com.example.csvccdshustbe.entity.NoShapeOriginalAssetTransferLand;
import com.example.csvccdshustbe.repository.noShapeOriginalAssetTransferLand.NoShapeOriginalAssetTransferLandRepository;
import com.example.csvccdshustbe.service.original.noShape.transferLand.NoOriginalAssetTransferLandService;
import com.example.csvccdshustbe.utility.ValueUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Map;
import java.util.Optional;

@Service
public class NoOriginalAssetTransferLandServiceImpl implements NoOriginalAssetTransferLandService {

    @Autowired
    NoShapeOriginalAssetTransferLandRepository noShapeOriginalAssetTransferLandRepository;


    @Override
    public NoShapeOriginalAssetTransferLand save(NoShapeOriginalAssetTransferLand land) {
        return noShapeOriginalAssetTransferLandRepository.save(land);
    }

    @Override
    public Map<String, Object> findNoOriginalAssetTransferLandById(Integer idInstance) throws IllegalAccessException {
        Optional<NoShapeOriginalAssetTransferLandDetailsDto> detailsDto =
                noShapeOriginalAssetTransferLandRepository.findNoShapeOriginalAssetTransferLandDetailsDtoById(idInstance);
        if (!detailsDto.isPresent()) {
            throw new NotFoundException("Don't exits no shape original asset transfer!");
        }
        return ValueUtil.convertObjectToMap(detailsDto.get());
    }

    @Override
    public void deleteNoShapOriginalAssetTransferLandById(Integer idInstance) {
        noShapeOriginalAssetTransferLandRepository.deleteNoShapeOriginalAssetTransferLandById(idInstance);
    }
}
