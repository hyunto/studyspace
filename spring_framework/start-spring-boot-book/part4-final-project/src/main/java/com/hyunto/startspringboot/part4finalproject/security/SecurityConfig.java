package com.hyunto.startspringboot.part4finalproject.security;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Log
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

	@Autowired
    private HyuntoUserService userService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.info("Security Config..........");

		http.authorizeRequests()
                .antMatchers("/boards/list").permitAll()
                .antMatchers("/boards/register").hasAnyRole("BASIC", "MANAGER", "ADMIN")
                .and()
            .formLogin()
                .loginPage("/login")
				.successHandler(new LoginSuccessHandler())
                .and()
            .exceptionHandling()
                .accessDeniedPage("/accessDenied")
                .and()
            .logout()
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .and()
            .rememberMe()
                .key("hyunto")
                .userDetailsService(userService)
                .tokenRepository(this.getJDBCRepository())
                .tokenValiditySeconds(60*60*24);
	}

	private PersistentTokenRepository getJDBCRepository() {
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);
        return repo;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
    }

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		log.info("build auth global.....");

		/*
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
        */

		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
	}
}
