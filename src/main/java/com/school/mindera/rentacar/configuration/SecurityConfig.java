package com.school.mindera.rentacar.configuration;

import com.school.mindera.rentacar.security.CookieAuthFilter;
import com.school.mindera.rentacar.security.JwtAuthFilter;
import com.school.mindera.rentacar.security.UserAuthenticationEntryPoint;
import com.school.mindera.rentacar.security.UserAuthenticationProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * Class for security configurations
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserAuthenticationEntryPoint userAuthenticationEntryPoint;
    private final UserAuthenticationProvider userAuthenticationProvider;

    public SecurityConfig(UserAuthenticationEntryPoint userAuthenticationEntryPoint,
                          UserAuthenticationProvider userAuthenticationProvider) {
        this.userAuthenticationEntryPoint = userAuthenticationEntryPoint;
        this.userAuthenticationProvider = userAuthenticationProvider;
    }

    /**
     * Override HttpSecurity configs
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .exceptionHandling().authenticationEntryPoint(userAuthenticationEntryPoint)
            .and()
            .addFilterBefore(new JwtAuthFilter(userAuthenticationProvider), BasicAuthenticationFilter.class)
            .addFilterBefore(new CookieAuthFilter(userAuthenticationProvider),JwtAuthFilter.class)
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests().antMatchers(HttpMethod.POST, "/api/auth/login", "/api/users").permitAll()
            .antMatchers("/api/swagger-ui.html", "/api/swagger-ui/*", "/v3/api-docs", "/v3/api-docs/*", "/api/health").permitAll()
            .anyRequest().authenticated();
    }
}
