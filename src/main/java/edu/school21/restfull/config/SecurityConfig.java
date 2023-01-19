package edu.school21.restfull.config;

import edu.school21.restfull.security.CustomAccessDeniedHandler;
import edu.school21.restfull.security.jwt.JwtAuthenticationEntryPoint;
import edu.school21.restfull.security.jwt.JwtAuthenticationProvider;
import edu.school21.restfull.security.password.LoginPasswordAuthenticationEntryPoint;
import edu.school21.restfull.security.jwt.JwtAuthenticationFilter;
import edu.school21.restfull.security.password.LoginPasswordAuthenticationFilter;
import edu.school21.restfull.security.password.LoginPasswordAuthenticationProvider;
import org.aspectj.weaver.ast.Or;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}

	@Bean
	public LoginPasswordAuthenticationProvider loginPasswordAuthenticationProvider() {
		return new LoginPasswordAuthenticationProvider();
	}

	@Bean
	public JwtAuthenticationProvider jwtAuthenticationProvider() {
		return new JwtAuthenticationProvider();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Добавляем все префиксы урл, которые нужно авторизовать с помощью jwt
		RequestMatcher jwtAuthenticationMatcher = new OrRequestMatcher(
				new AntPathRequestMatcher("/users/**"),
				new AntPathRequestMatcher("/courses/**")
		);

		http
				.csrf().disable()
				.authorizeRequests()
					.antMatchers( "/swagger-ui/**", "/swagger-resources/**", "/v2/api-docs/**", "/webjars/**", "/explorer/**").permitAll()
					.anyRequest().authenticated()
					.and()
				.addFilterAt(
						new LoginPasswordAuthenticationFilter("/signIn", authenticationManager(), new LoginPasswordAuthenticationEntryPoint()),
						BasicAuthenticationFilter.class)
				.addFilterAt(
						new JwtAuthenticationFilter(jwtAuthenticationMatcher, authenticationManager(), new JwtAuthenticationEntryPoint()),
						BasicAuthenticationFilter.class)
				.exceptionHandling()
					.defaultAccessDeniedHandlerFor(new CustomAccessDeniedHandler(), new AntPathRequestMatcher("/**"))
					.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder builder) {
		builder.authenticationProvider(loginPasswordAuthenticationProvider())
				.authenticationProvider(jwtAuthenticationProvider());
	}

}
