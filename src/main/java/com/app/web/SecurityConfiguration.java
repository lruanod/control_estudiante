package com.app.web;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
	 
	
	
	
	@Bean
    public UserDetailsService userDetailsService() {
		
		UserDetails usuario1= User
				.withUsername("lruano")
				.password("$2a$10$uGexI06HeBbVXL0gwGf/aOKGJsE/pxnBwbYmknL5TvijdXeJDPnPG")
				.roles("USER")
				.build();
		
		UserDetails usuario2= User
				.withUsername("admin")
				.password("$2a$10$uGexI06HeBbVXL0gwGf/aOKGJsE/pxnBwbYmknL5TvijdXeJDPnPG")
				.roles("ADMIN")
				.build();
		
		return new InMemoryUserDetailsManager(usuario1,usuario2);
      //  return new ShopmeUserDetailsService();
    }
 
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
 
	
	@Bean
	 public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests()
		.antMatchers("/","/login","/estudiantes/nuevo/*","/estudiantes","/grados/nuevo/*","/grados","/cursos/nuevo/*","/cursos","/grados_cursos/nuevo/*","/grados_cursos").permitAll()
		.antMatchers("/estudiantes/editar/*","/estudiantes/eliminar/*","/grados/editar/*","/grados/eliminar/*","/cursos/editar/*","/cursos/eliminar/*","/grados_cursos/editar/*","/grados_cursos/eliminar/*").hasRole("ADMIN")
		.anyRequest().authenticated()
		.and()
		.formLogin()
		   .loginPage("/login")
		   .usernameParameter("username")
		   .permitAll()
		.and()
		.rememberMe().key("$2a$10$uGexI06HeBbVXL0gwGf/aOKGJsE/pxnBwbYmknL5TvijdXeJDPnPG")
		.and()
		.logout().permitAll();
		
		http.headers().frameOptions().sameOrigin();
		
		return http.build();
	}
	
	@Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
    }
}
