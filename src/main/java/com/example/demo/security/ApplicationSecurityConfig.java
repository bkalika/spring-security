package com.example.demo.security;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.auth.ApplicationUserService;
import com.example.demo.jwt.JwtConfig;
import com.example.demo.jwt.JwtTokenVerifier;
import com.example.demo.jwt.JwtUsernameAndPasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
	private final PasswordEncoder passwordEncoder;
	private final ApplicationUserService applicationUserService;
	private final SecretKey secretKey;
	private final JwtConfig jwtConfig;
	
	@Autowired
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder,
									ApplicationUserService applicationUserService,
									SecretKey secretKey,
									JwtConfig jwtConfig) {
		this.passwordEncoder = passwordEncoder;
		this.applicationUserService = applicationUserService;
		this.secretKey = secretKey;
		this.jwtConfig = jwtConfig;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
//			.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//			.and()
			.csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig, secretKey))
			.addFilterAfter(new JwtTokenVerifier(secretKey, jwtConfig), JwtUsernameAndPasswordAuthenticationFilter.class)
			.authorizeRequests()
			.antMatchers("/", "index", "/css/*", "/js/*").permitAll()
			.antMatchers("/api/**").hasRole(ApplicationUserRole.STUDENT.name())
//			.antMatchers(HttpMethod.DELETE, "/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
//			.antMatchers(HttpMethod.POST, "/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
//			.antMatchers(HttpMethod.PUT, "/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
//			.antMatchers(HttpMethod.GET, "/management/api/**").hasAnyRole(ApplicationUserRole.ADMIN.name(), ApplicationUserRole.ADMINTRAINEE.name())
			.anyRequest()
			.authenticated()
//			.and()
////			.httpBasic();
//			.formLogin()
//				.loginPage("/login")
//				.permitAll()
//				.defaultSuccessUrl("/courses", true)
//				.passwordParameter("password")
//				.usernameParameter("username")
//			.and()
//			.rememberMe()
//				.tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(21))
//				.key("somethingverysecured")
//				.rememberMeParameter("remember-me")
//			.and()
//			.logout()
//				.logoutUrl("/logout")
//				.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
//				.invalidateHttpSession(true)
//				.deleteCookies("JSESSIONID", "remember-me")
//				.logoutSuccessUrl("/login")
			;
	}
	
//	@Override
//	@Bean
//	protected UserDetailsService userDetailsService() {
//		UserDetails annaUser = User.builder()
//			.username("anna")
//			.password(passwordEncoder.encode("password"))
////			.roles(ApplicationUserRole.STUDENT.name())
//			.authorities(ApplicationUserRole.STUDENT.getGrantedAuthorities())
//			.build();
//		
//		UserDetails bodAdmin = User.builder()
//			.username("bod")
//			.password(passwordEncoder.encode("pass"))
////			.roles(ApplicationUserRole.ADMIN.name())
//			.authorities(ApplicationUserRole.ADMIN.getGrantedAuthorities())
//			.build();
//		
//		UserDetails tomAdmin = User.builder()
//				.username("tom")
//				.password(passwordEncoder.encode("pass"))
////				.roles(ApplicationUserRole.ADMINTRAINEE.name())
//				.authorities(ApplicationUserRole.ADMINTRAINEE.getGrantedAuthorities())
//				.build();
//
//		return new InMemoryUserDetailsManager(
//				annaUser,
//				bodAdmin,
//				tomAdmin);
//	}
	
	

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder);
		provider.setUserDetailsService(applicationUserService);
		return provider;
	}
}
