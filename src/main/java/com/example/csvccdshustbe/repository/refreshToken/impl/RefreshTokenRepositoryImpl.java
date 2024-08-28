package com.example.csvccdshustbe.repository.refreshToken.impl;

import com.example.csvccdshustbe.entity.RefreshToken;
import com.example.csvccdshustbe.repository.refreshToken.RefreshTokenRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

public class RefreshTokenRepositoryImpl implements RefreshTokenRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;
    @Override
    public Optional<RefreshToken> findByToken(String refreshToken) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select refreshToken.id_refresh_token, refreshToken.id_user, " +
                "       refreshToken.token, refreshToken.expire_date, " +
                "       refreshToken.revoked " +
                "from refresh_token refreshToken " +
                "where refreshToken.token = :refreshToken ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("refreshToken", refreshToken);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                RefreshToken res = new RefreshToken();
                res.setIdRefreshToken(ValueUtil.getIntegerByObject(obj[0]));
                res.setIdUser(ValueUtil.getIntegerByObject(obj[1]));
                res.setToken(ValueUtil.getStringByObject(obj[2]));
                res.setExpireDate(ValueUtil.getStringByObject(obj[3]));
                res.setRevoked(ValueUtil.getBooleanByObject(obj[4]));
                return Optional.of(res);
            }
        }
        return Optional.empty();
    }
}
