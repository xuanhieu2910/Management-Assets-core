package com.example.csvccdshustbe.service.original.noShape.assetUseLand.impl;

import com.example.csvccdshustbe.dto.original.noShape.NoShapeOriginalAssetUseLandDetailsDto;
import com.example.csvccdshustbe.entity.NoShapeOriginalAssetUseLand;
import com.example.csvccdshustbe.repository.noShapeOriginalAssetUseLand.NoShapeOriginalAssetUseLandRepository;
import com.example.csvccdshustbe.service.original.noShape.assetUseLand.NoOriginalAssetUseLandService;
import com.example.csvccdshustbe.utility.ValueUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Map;
import java.util.Optional;

@Service
public class NoOriginalAssetUseLandServiceImpl implements NoOriginalAssetUseLandService {


    @Autowired
    NoShapeOriginalAssetUseLandRepository noShapeOriginalAssetUseLandRepository;

    @Override
    public NoShapeOriginalAssetUseLand save(NoShapeOriginalAssetUseLand useLand) {
        return noShapeOriginalAssetUseLandRepository.save(useLand);
    }

    @Override
    public Map<String, Object> findNoOriginalAssetUseLandById(Integer idInstance) throws IllegalAccessException {
        Optional<NoShapeOriginalAssetUseLandDetailsDto> detailsDto =
                noShapeOriginalAssetUseLandRepository.findNoShapeOriginalAssetUseLandDetailsById(idInstance);
        if (!detailsDto.isPresent()){
            throw new NotFoundException("Don't exits original asset use land!");
        }
        return ValueUtil.convertObjectToMap(detailsDto.get());
    }

    @Override
    public void deleteNoShapeOriginalAssetUseLandById(Integer idInstance) {
        noShapeOriginalAssetUseLandRepository.deleteNoShapeOriginalAssetUseLandById(idInstance);
    }

    @Override
    public NoShapeOriginalAssetUseLand findNoShapeOriginalAssetUseLandById(Integer idInstance) {
        Optional<NoShapeOriginalAssetUseLand> assetUseLand =
                noShapeOriginalAssetUseLandRepository.findNoShapeOriginalAssetUseLandById(idInstance);
        if (assetUseLand.isEmpty()){
            throw new NotFoundException("Don't exits original asset use land");
        }
        return assetUseLand.get();
    }
}
