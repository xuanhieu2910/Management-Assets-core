package com.example.csvccdshustbe.repository.districts.impl;

import com.example.csvccdshustbe.dto.districts.DistrictsDto;
import com.example.csvccdshustbe.repository.districts.DistrictsRepositoryCustom;
import com.example.csvccdshustbe.request.districts.FindAllDistrictsRequest;
import com.example.csvccdshustbe.response.districts.FindAllDistrictsResponse;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DistrictsRepositoryImpl implements DistrictsRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Page<FindAllDistrictsResponse> findAllDistrictsResponse(FindAllDistrictsRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select dis.code, dis.name, " +
                "       pro.code provinceCode " +
                "from districts dis " +
                "    inner join provinces pro on dis.province_code = pro.code " +
                "where 1 = 1 " +
                "and pro.code = :provinceCode ");
        setConditionalFindAllDistrictsResponse(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllDistrictsResponse(request, query);
        List<FindAllDistrictsResponse> responses = new ArrayList<>();
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result){
                FindAllDistrictsResponse response = new FindAllDistrictsResponse();
                response.setCodeDistricts(ValueUtil.getStringByObject(obj[0]));
                response.setNameDistrict(ValueUtil.getStringByObject(obj[1]));
                response.setCodeProvince(ValueUtil.getStringByObject(obj[2]));
                responses.add(response);
            }
        }

        return new PageImpl<>(responses, pageable, countFindAllDistrictsResponse(request));
    }

    @Override
    public Map<String, List<DistrictsDto>> findAllDistrictsToDownload() {
        StringBuilder sb = new StringBuilder();
        sb.append(" select provinces.code codeProvinces, provinces.name,  " +
                "       districts.code codeDistrict, districts.name " +
                "from districts " +
                "    inner join provinces on districts.province_code = provinces.code ");
        Query query = entityManager.createNativeQuery(sb.toString());
        List<Object[]> result = query.getResultList();
        Map<String, List<DistrictsDto>> responses = new HashMap<>();
        if (!CollectionUtils.isEmpty(result)){
            String keyword;
            String codeProvinces;
            String nameProvinces;
            for (Object[] obj : result){
                codeProvinces = ValueUtil.getStringByObject(obj[0]);
                nameProvinces = ValueUtil.getStringByObject(obj[1]);
                keyword = "STT_" + codeProvinces + "_" + ValueUtil.convertToVietnamese(nameProvinces).
                        replaceAll(ValueUtil.REGEX_letter_digit_period_underscore,"");
                if (responses.containsKey(keyword)){
                    DistrictsDto districtsDto = new DistrictsDto();
                    districtsDto.setCode(ValueUtil.getStringByObject(obj[2]));
                    districtsDto.setNameDistrict(ValueUtil.getStringByObject(obj[3]));
                    responses.get(keyword).add(districtsDto);
                } else {
                    List<DistrictsDto> districtsDtos = new ArrayList<>();
                    DistrictsDto districtsDto = new DistrictsDto();
                    districtsDto.setCode(ValueUtil.getStringByObject(obj[2]));
                    districtsDto.setNameDistrict(ValueUtil.getStringByObject(obj[3]));
                    districtsDtos.add(districtsDto);
                    responses.put(keyword, districtsDtos);
                }
            }
        }
        return responses;
    }


    private long countFindAllDistrictsResponse(FindAllDistrictsRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0) count " +
                "from districts dis " +
                "    inner join provinces pro on dis.province_code = pro.code " +
                "where 1 = 1 " +
                "and pro.code = :provinceCode ");
        setConditionalFindAllDistrictsResponse(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllDistrictsResponse(request, query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }

    private void setParameterFindAllDistrictsResponse(FindAllDistrictsRequest request, Query query) {
        query.setParameter("provinceCode", request.getCodeProvince());
        if (StringUtils.isNotBlank(request.getKeyword())){
            query.setParameter("keyword", request.getKeyword());
        }
    }

    private void setConditionalFindAllDistrictsResponse(FindAllDistrictsRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getKeyword())) {
            sb.append(" and (dis.name REGEXP  :keyword ) ");
        }
    }
}
