package com.example.csvccdshustbe.config;

import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.utility.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;


import java.io.IOException;


@Log4j2
@Component
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {


    @Value("${frontend.url.success}")
    private String frontendUrlSuccess;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        OidcUser oidcUser = (OidcUser) ((OAuth2AuthenticationToken) authentication).getPrincipal();
        CsvcUser csvcUser = oidcUser.getUserInfo().getClaim(Constants.CLAIMS_INFORMATION_USER);
        log.debug("User name " + csvcUser.getUsername() + " login success!");
        this.setAlwaysUseDefaultTargetUrl(true);
        this.setDefaultTargetUrl(frontendUrlSuccess);
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
