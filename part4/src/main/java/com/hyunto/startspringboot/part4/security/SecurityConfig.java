package com.hyunto.startspringboot.part4.security;

import com.hyunto.startspringboot.part4.security.HyuntoUserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Log
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

	@Autowired
    private HyuntoUserService userService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.info("Security Config..........");

		http.authorizeRequests()
                .antMatchers("/guest/**").permitAll()
                .antMatchers("/manager/**").hasRole("MANAGER")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .and()
            .formLogin()
                .loginPage("/login")
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

	/*
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
