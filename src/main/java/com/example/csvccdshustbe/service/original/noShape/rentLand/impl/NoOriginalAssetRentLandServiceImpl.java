package com.example.csvccdshustbe.service.original.noShape.rentLand.impl;

import com.example.csvccdshustbe.dto.original.noShape.NoShapeOriginalAssetRentLandDetailsDto;
import com.example.csvccdshustbe.entity.NoShapeOriginalAssetRentLand;
import com.example.csvccdshustbe.repository.noShapeOriginalAssetRentLand.NoShapeOriginalAssetRentLandRepository;
import com.example.csvccdshustbe.service.original.noShape.rentLand.NoOriginalAssetRentLandService;
import com.example.csvccdshustbe.utility.ValueUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Map;
import java.util.Optional;

@Service
public class NoOriginalAssetRentLandServiceImpl implements NoOriginalAssetRentLandService {

    @Autowired
    NoShapeOriginalAssetRentLandRepository noShapeOriginalAssetRentLandRepository;

    @Override
    public NoShapeOriginalAssetRentLand save(NoShapeOriginalAssetRentLand land) {
        return noShapeOriginalAssetRentLandRepository.save(land);
    }

    @Override
    public Map<String, Object> findNoOriginalAssetRentLandById(Integer idInstance) throws IllegalAccessException {
        Optional<NoShapeOriginalAssetRentLandDetailsDto> detailsDto =
                noShapeOriginalAssetRentLandRepository.findNoShapeOriginalAssetRentLandDetailsDtoById(idInstance);
        if (!detailsDto.isPresent()){
            throw new NotFoundException("Don't exits no shape original asset rent land!");
        }
        return ValueUtil.convertObjectToMap(detailsDto.get());
    }

    @Override
    public void deleteNoShapeOriginalAssetRendLandById(Integer idInstance) {
        noShapeOriginalAssetRentLandRepository.deleteNoShapeOriginalAssetRentLandById(idInstance);
    }

    @Override
    public NoShapeOriginalAssetRentLand findNoShapeOriginalAssetRentLandById(Integer idInstance) {
        Optional<NoShapeOriginalAssetRentLand> rentLand =
                noShapeOriginalAssetRentLandRepository.findNoShapeOriginalAssetRentLandById(idInstance);
        if (rentLand.isEmpty()) {
            throw new NotFoundException("Don't exits no shape original asset rent land!");
        }
        return rentLand.get();
    }
}
