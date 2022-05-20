package com.edu.icesi.dev.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private LoggingAccessDeniedHandler accessDeniedHandler;

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {

		httpSecurity.authorizeRequests()
				.antMatchers("/category/**").access("hasRole('administrator')")
				.antMatchers("/subcategory/**").access("hasRole('administrator')")
				.antMatchers("/product/**").access("hasRole('operator')")
				.antMatchers("/workorder/**").access("hasRole('operator')") .anyRequest().authenticated()
				.and().formLogin().loginPage("/login").permitAll().and().logout().invalidateHttpSession(true)
				.clearAuthentication(true).logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login?logout").permitAll().and().exceptionHandling()
				.accessDeniedHandler(accessDeniedHandler);

	}
}