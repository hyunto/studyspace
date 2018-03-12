package com.hyunto.startspringboot.part4.security;

import com.hyunto.startspringboot.part4.security.HyuntoUserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@Log
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
    private HyuntoUserService userService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.info("Security Config..........");

		http.authorizeRequests().antMatchers("/guest/**").permitAll();

		http.authorizeRequests().antMatchers("/manager/**").hasRole("MANAGER");

		http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");

		http.formLogin().loginPage("/login");

		http.exceptionHandling().accessDeniedPage("/accessDenied");

		http.logout().logoutUrl("/logout").invalidateHttpSession(true);

		http.userDetailsService(userService);
	}

	/*
	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		log.info("build auth global.....");

		auth.inMemoryAuthentication()
				.withUser("manager")
				.password("{noop}1111")
				.roles("MANAGER");

		String query1 = "SELECT uid username, CONCAT('{noop}', upw) password, true enabled FROM tbl_members WHERE uid = ?";
		String query2 = "SELECT member uid, role_name role FROM tbl_member_roles WHERE member = ?";

		auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(query1)
                .rolePrefix("ROLE_")
                .authoritiesByUsernameQuery(query2);
	}
	*/
}
