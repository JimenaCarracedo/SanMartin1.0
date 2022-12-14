package com.sanmartin.club;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.sanmartin.club.Service.SocioService;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity( prePostEnabled =  true)
public class SecurityConfiguration  extends WebSecurityConfigurerAdapter {
	
	@Autowired
	SocioService servicio;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(servicio).passwordEncoder(new BCryptPasswordEncoder());

	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.headers().frameOptions().sameOrigin().and().authorizeRequests()
			.antMatchers("/css/*", "/js/*", "/img/*").permitAll()
			.and().formLogin().loginPage("/login")
										.loginProcessingUrl("/logincheck")
										.usernameParameter("username")
										.passwordParameter("password")
										.defaultSuccessUrl("/index")
										.permitAll()
							.and().logout()
									.logoutUrl("/logout")
									.logoutSuccessUrl("/")
									.permitAll();
	}

}