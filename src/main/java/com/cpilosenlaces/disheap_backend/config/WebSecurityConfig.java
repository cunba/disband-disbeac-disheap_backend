package com.cpilosenlaces.disheap_backend.config;

import com.cpilosenlaces.disheap_backend.model.UserModel;
import com.cpilosenlaces.disheap_backend.security.JwtAuthenticationEntryPoint;
import com.cpilosenlaces.disheap_backend.security.JwtTokenFilterConfigurer;
import com.cpilosenlaces.disheap_backend.security.JwtTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthEndPoint;

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return super.userDetailsService();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors().and()
                .authorizeRequests().antMatchers(
                        "/login",
                        "/users",
                        "/ambient-noises",
                        "/heart-rates",
                        "/humidities",
                        "/lightnings",
                        "/locations-disbeacs",
                        "/oxygens",
                        "/pressures",
                        "/temperatures",
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/swagger-resources/**",
                        "/v2/api-docs",
                        "/v2/api-docs/**",
                        "/v3/api-docs",
                        "/v3/api-docs/**",
                        "/webjars/**")
                .permitAll()
                .anyRequest().authenticated().and().httpBasic().and().headers().frameOptions().disable().and().csrf()
                .disable()
                .exceptionHandling().authenticationEntryPoint(jwtAuthEndPoint).and()
                .apply(new JwtTokenFilterConfigurer(jwtTokenProvider))
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
        web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
                "/configuration/security", "/swagger-ui.html", "/webjars/**");
    }

    // Method to configure and register the users of our system:
    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception {

        // Configuration for JPA:
        builder.userDetailsService(jwtUserDetailsService).passwordEncoder(UserModel.encoder());

    }
}