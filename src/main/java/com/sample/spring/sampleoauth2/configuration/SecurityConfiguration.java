package com.sample.spring.sampleoauth2.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private DataSource dataSource;
	
	private static final String SQL_LOGIN = "select username, password, true as enabled "
            + "from s_user where username= ? ";
    private static final String SQL_PERMISSION = "select u.username, p.name "
            + "from s_user u "
            + "inner join s_user_role ur on u.id = ur.id_user "
            + "inner join s_role r on r.id = ur.id_role "
            + "inner join s_role_permission rp on rp.id_role = r.id "
            + "inner join s_permission p on p.id = rp.id_permission "
            + "where u.username = ?";

	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
				.jdbcAuthentication().dataSource(dataSource)
		        .usersByUsernameQuery(SQL_LOGIN)
		        .authoritiesByUsernameQuery(SQL_PERMISSION);
		
		/*
		auth
				.jdbcAuthentication()
				.usersByUsernameQuery(SQL_LOGIN)
				.authoritiesByUsernameQuery(SQL_PERMISSION)
				.dataSource(dataSource)
				.passwordEncoder(bCryptPasswordEncoder);
		*/
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	http.formLogin().permitAll()
			.and().logout()
			.and().authorizeRequests()
			.anyRequest().authenticated();
    	
    	//harusnya tidak bisa akses /api/dummy di DummyController karena user tidak ada yang punya permission USER_VIEW2
    	//tetapi ternyata semua user masih bisa akses.
    }
    
    
    @Override
    @Bean
    public UserDetailsService userDetailsServiceBean() throws Exception {
    	return super.userDetailsServiceBean();
    }

}
