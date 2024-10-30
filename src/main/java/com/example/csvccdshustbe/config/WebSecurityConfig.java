package com.example.csvccdshustbe.config;

import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.exception.RoleException;
import com.example.csvccdshustbe.service.user.CsvcUserService;
import com.example.csvccdshustbe.utility.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.*;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class WebSecurityConfig{

    @Autowired
    UnauthorizedEntryPoint unauthorizedEntryPoint;
    @Autowired
    CustomAccessDeniedHandler accessDeniedHandler;
    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    AuthenticationProvider authenticationProvider;
    @Autowired
    OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    @Autowired
    CsvcUserService csvcUserService;
    @Autowired
    OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    public final static String PREFERRED_USERNAME = "preferred_username";
    public final static String INFORMATION_USER = "informationUser";
    public final static String DOMAIN_DEV = "https://csvc-development.hust.edu.vn";
//    public final static String DOMAIN_DEV = "http://localhost:3000";
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable).cors(c->c.configurationSource(corsConfigurationSource())).
                exceptionHandling(exception -> exception
                        .authenticationEntryPoint(unauthorizedEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                ).authorizeHttpRequests(request ->
                        request.requestMatchers("/swagger-resources",
                                        "/swagger-resources/**",
                                        "/swagger-ui/**",
                                        "/webjars/**",
                                        "/swagger-ui.html",
                                        "/v3/api-docs/**",
                                        "/api/v1/user/auth/**").permitAll()
                                .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(oAuth2LoginSuccessHandler)
                        .failureHandler(oAuth2AuthenticationFailureHandler)
                        .userInfoEndpoint(x->x.oidcUserService(this.oidcUserService())))
                .authenticationProvider(authenticationProvider).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }


    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        final OidcUserService delegate = new OidcUserService();
        return (userRequest) -> {
            OidcUser oidcUser = delegate.loadUser(userRequest);
            String userName = oidcUser.getIdToken().getClaimAsString("preferred_username").trim().toLowerCase();
            String fullName = oidcUser.getIdToken().getClaimAsString("name").trim().toLowerCase();
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
            Optional<CsvcUser> userDetails = csvcUserService.findByUserName(userName);
            if (userDetails.isEmpty()){
                try {
                    userDetails = Optional.of(csvcUserService.createNewUser(userName,fullName));
                } catch (RoleException e) {
                    throw new RuntimeException(e);
                }
            }
            if (userDetails.isPresent() && !userDetails.get().isAccountNonLocked()) {
                throw new UsernameNotFoundException("User is locked!");
            }
            Map<String, Object> claims = new HashMap<>();
            claims.put(Constants.CLAIMS_INFORMATION_USER,userDetails.get());
            OidcUserInfo oidcUserInfo = new OidcUserInfo(claims);
            mappedAuthorities.addAll(userDetails.get().getAuthorities());
            ClientRegistration.ProviderDetails providerDetails = userRequest.getClientRegistration().getProviderDetails();
            String userNameAttributeName = providerDetails.getUserInfoEndpoint().getUserNameAttributeName();
            OAuth2AuthenticationToken authentication = new OAuth2AuthenticationToken(userDetails.get(),
                    mappedAuthorities, userRequest.getClientRegistration().getRegistrationId());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            if (StringUtils.hasText(userNameAttributeName)) {
                oidcUser = new DefaultOidcUser(mappedAuthorities, oidcUser.getIdToken(), oidcUserInfo, userNameAttributeName);
            } else {
                oidcUser = new DefaultOidcUser(mappedAuthorities, oidcUser.getIdToken(), oidcUserInfo);
            }
            return oidcUser;
        };
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedOrigins(List.of(DOMAIN_DEV));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
