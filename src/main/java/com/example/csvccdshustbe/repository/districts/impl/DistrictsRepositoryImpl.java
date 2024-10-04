package com.example.csvccdshustbe.repository.districts.impl;

import com.example.csvccdshustbe.entity.Districts;
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
import java.util.List;

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

    @Override
    public List<Districts> findAllDistrictsByCodes(List<String> DistrictCodes ) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select dis.code, dis.name " +
                "from districts dis " +
                "where dis.code in :DistrictCodes ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("DistrictCodes", DistrictCodes);

        List<Districts> districtsList = new ArrayList<>();
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj: result){
                Districts districts = new Districts();
                districts.setCode(ValueUtil.getStringByObject(obj[0]));
                districts.setName(ValueUtil.getStringByObject(obj[1]));
                districtsList.add(districts);
            }
        }
        return districtsList;
    }
}
