package com.clinic.management.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.clinic.management.security.jwt.AuthEntryPointJwt;
import com.clinic.management.security.jwt.AuthTokenFilter;



@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private AuthEntryPointJwt authEntryPointJwt;
	
	@Autowired
	private AuthTokenFilter authTokenFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) //validation for apis 
	{
		httpSecurity
		.csrf(csrf->csrf.disable())//to disable csrf we need jwc
		.cors(Customizer.withDefaults())
		.authorizeHttpRequests(request->
		request.requestMatchers(HttpMethod.POST,"/users/register","/doctor/register","/patient/register").permitAll() //check the request and its validations
		//permitAll() no need for login authentaction
		.requestMatchers(HttpMethod.GET,"/appointments/*/create/","/appointments/*","/appointments/patient/*","/users/exists","/doctor").permitAll()
		.requestMatchers(HttpMethod.POST,"/auth/login").permitAll()
//		.requestMatchers(HttpMethod.PUT,"/workshops/**",
//				"/requests/*/approve","/requests/*/reject").hasRole("ADMIN")
//		.requestMatchers(HttpMethod.DELETE,"/workshops/**").hasRole("ADMIN")
		.anyRequest().authenticated() //anyRequest needs authentaction 
		);
		
		httpSecurity.exceptionHandling(authentication->
		authentication.authenticationEntryPoint(authEntryPointJwt));
		
		httpSecurity.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
		return httpSecurity.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder(); //to access the methods of passwordEncoder
	}
	
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

	
	 @Bean
	    public CorsConfigurationSource corsConfigurationSource() {
	        CorsConfiguration config = new CorsConfiguration();
	        config.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:4200"));
	        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	        config.setAllowedHeaders(List.of("*")); //all headers
	        config.setAllowCredentials(true);

//	        For more restrictive CORS settings, you can specify allowed headers and methods explicitly
	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", config);
	        return source;
	        
	    }
}

	
	
	

