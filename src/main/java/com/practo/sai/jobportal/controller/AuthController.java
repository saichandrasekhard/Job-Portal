//package com.practo.sai.jobportal.controller;
//
//import java.security.Principal;
//
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@SpringBootApplication
//@EnableOAuth2Sso
//@RestController
//public class AuthController extends WebSecurityConfigurerAdapter {
//
//	@RequestMapping("/user")
//	public Principal user(Principal principal) {
//		System.out.println("PLEASE WORK - " + principal.toString());
//		return principal;
//	}
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.antMatcher("/**").authorizeRequests().antMatchers("/", "/login**", "/webjars/**").permitAll()
//				.anyRequest().authenticated();
//	}
//
//}
