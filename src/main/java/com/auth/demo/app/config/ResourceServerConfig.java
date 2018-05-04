package com.auth.demo.app.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.jwk.JwkTokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	private static final Logger log = LoggerFactory.getLogger(ResourceServerConfig.class);

	@Autowired
	private Environment env;

	@Override
	public void configure(final HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().authenticated().antMatchers("/actuator/health").permitAll()
				.antMatchers("/actuator/info").permitAll().antMatchers("/actuator/env").permitAll()
				.antMatchers("/public/**").permitAll();
	}

	// The following is used for Opaque tokens - Save for future use
	/*
	 * @Bean public ResourceServerTokenServices tokenService() {
	 * RemoteTokenServices tokenServices = new RemoteTokenServices();
	 * tokenServices.setClientId("xxxxxxxxxx");
	 * tokenServices.setClientSecret("yyyyyyyyyyyyyyyyyyyyyyyyy");
	 * tokenServices.setCheckTokenEndpointUrl("introspect endpoint"); return
	 * tokenServices; }
	 */

	@Bean
	@Primary
	public JwkTokenStore tokenStore() {
		log.debug("Entering JwtTokenStore");
		JwkTokenStore jwkTokenStore = new JwkTokenStore(
				env.getProperty("spring.security.oauth2.resource.jwk.key-set-uri"), accessTokenConverter());
		log.debug("Using JwkTokenStore: " + jwkTokenStore.toString());
		return jwkTokenStore;
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		log.debug("accessTokenConverter:  " + converter.getAccessTokenConverter().toString());
		return converter;
	}

	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices tokenServices = new DefaultTokenServices();
		tokenServices.setTokenStore(tokenStore());
		tokenServices.setSupportRefreshToken(false);
		tokenServices.setTokenEnhancer(accessTokenConverter());
		log.debug("DefaultTokenServices Initiated");
		return tokenServices;
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer config) {
		config.tokenServices(tokenServices())
				.resourceId(env.getProperty("spring.security.oauth2.resource.api-audience"));
	}

}