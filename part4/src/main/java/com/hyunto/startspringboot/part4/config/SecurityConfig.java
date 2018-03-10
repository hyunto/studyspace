package com.hyunto.startspringboot.part4.config;

import lombok.extern.java.Log;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Log
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.info("Security Config..........");

		http.authorizeRequests().antMatchers("/guest/**").permitAll();

		http.authorizeRequests().antMatchers("/manager/**").hasRole("MANAGER");

		http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");

		http.formLogin().loginPage("/login");

		http.exceptionHandling().accessDeniedPage("/accessDenied");

		http.logout().logoutUrl("/logout").invalidateHttpSession(true);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		log.info("build auth global.....");

		auth.inMemoryAuthentication()
				.withUser("manager")
				.password("{noop}1111")
				.roles("MANAGER");
	}
}
