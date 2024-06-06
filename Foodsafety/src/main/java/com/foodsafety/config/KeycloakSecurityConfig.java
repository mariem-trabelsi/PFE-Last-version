package com.foodsafety.config;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@KeycloakConfiguration
@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true)
public class KeycloakSecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        KeycloakAuthenticationProvider keycloakAuthenticationProvider = new KeycloakAuthenticationProvider();
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
        auth.authenticationProvider(keycloakAuthenticationProvider);
    }

    @Bean
    public KeycloakConfigResolver keycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }

    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(buildSessionRegistry());
    }

    @Bean
    protected SessionRegistry buildSessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.csrf().disable();
        http.cors().and()
                .authorizeHttpRequests()
                .antMatchers(
                        "/swagger-ui/**",    
                        "/api-docs/**"
                ).permitAll()
                .antMatchers(HttpMethod.GET, "/goods/v1/category", "/goods/v1/category/**").hasAnyRole("admin", "agent", "donor")
                .antMatchers(HttpMethod.POST, "/goods/v1/category", "/goods/v1/category/**").hasAnyRole("admin", "agent")
                .antMatchers(HttpMethod.PUT, "/goods/v1/category", "/goods/v1/category/**").hasAnyRole("admin", "agent")
                .antMatchers(HttpMethod.GET, "/goods/v1/subCategory", "/goods/v1/subCategory/**").hasAnyRole("admin", "agent", "donor")
                .antMatchers(HttpMethod.GET, "/goods/v1/product", "/goods/v1/product/**").hasAnyRole("admin", "agent", "donor")
                .antMatchers(HttpMethod.GET, "/goods/v1/beneficiaryRequest", "/goods/v1/beneficiaryRequest/**").hasAnyRole("admin", "agent", "donor")
                .antMatchers(HttpMethod.GET, "/goods/v1/beneficiaryRequestContainsProduct", "/goods/v1/beneficiaryRequestContainsProduct/**").hasAnyRole("admin", "agent", "donor")
                .antMatchers(HttpMethod.POST, "/goods/v1/beneficiaryRequestContainsProduct", "/goods/v1/beneficiaryRequestContainsProduct/**").hasAnyRole("admin", "agent", "donor")
                .antMatchers(HttpMethod.GET, "/goods/v1/campaignRequest", "/goods/v1/campaignRequest/**").hasAnyRole("admin", "agent", "donor")
                .antMatchers(HttpMethod.GET, "/goods/v1/campaignRequestContainsProduct", "/goods/v1/campaignRequestContainsProduct/**").hasAnyRole("admin", "agent", "donor")
                .antMatchers(HttpMethod.GET, "/goods/v1/funds", "/goods/v1/funds/**").hasAnyRole("admin", "agent", "donor")
                .anyRequest().authenticated();
    }

}

