package com.zacharywarunek.financialfuture.config;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableAsync
@EnableWebSecurity
@AllArgsConstructor
@EnableGlobalMethodSecurity(
    securedEnabled = true,
    prePostEnabled = true,
    proxyTargetClass = true,
    jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final PasswordEncoder passwordEncoder;
  private final UserDetailsService userDetailsService;
  private final JwtAuthenticationEntryPoint unauthorizedHandler;

  @Bean
  public JwtFilter authenticationTokenFilterBean() {
    return new JwtFilter();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors()
        .and()
        .authorizeRequests()
        .antMatchers(HttpMethod.PUT, "/api/v*/accounts/{account_id}/addresses/{address_id}")
        .access("@secure.checkAddressIdAuth(authentication, #account_id, #address_id)")
        .antMatchers(HttpMethod.GET, "/api/v*/accounts/{account_id}/addresses/favorite")
        .access("@secure.checkAccountIdAuth(authentication,#account_id)")
        .antMatchers(
            HttpMethod.PUT, "/api/v*/accounts/{account_id}/addresses/{address_id}/favorite")
        .access("@secure.checkAddressIdAuth(authentication, #account_id, #address_id)")
        .antMatchers("/api/v*/accounts/{account_id}/**")
        .access("@secure.checkAccountIdAuth(authentication,#account_id)");
    http.cors()
        .and()
        .csrf()
        .disable()
        .authorizeRequests()
        .antMatchers(
            "/api/v*/registration/**",
            "/api/v*/authenticate/**",
            "/api/v*/apiTest",
            "/api/v*/passwordreset/**",
            "/api/v*/recaptcha-validate/**",
            "/v3/api-docs",
            "/api/v*/categories")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(unauthorizedHandler)
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.addFilterBefore(
        authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("*"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) {
    auth.authenticationProvider(daoAuthenticationProvider());
  }

  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(passwordEncoder);
    provider.setUserDetailsService(userDetailsService);

    return provider;
  }
}
