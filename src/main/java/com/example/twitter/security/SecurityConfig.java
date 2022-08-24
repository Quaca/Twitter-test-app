package com.example.twitter.security;

import com.example.twitter.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    UserDetailsService userDetailsService;
    UserService userService;
    PasswordEncoder passwordEncoder;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.authorizeRequests().anyRequest().permitAll();
        http.authorizeRequests().antMatchers("/registrationConfirm").permitAll();
        http.authorizeRequests().antMatchers("/resetPassword").permitAll();
        http.authorizeRequests().antMatchers("/login").permitAll();
        http.authorizeRequests().antMatchers("/v1/users/role").permitAll();
        http.authorizeRequests().antMatchers("/v1/users").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/v1/api/tweets/**").hasAnyAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/v1/api/tweet/**").hasAnyAuthority("ROLE_USER");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
        http.addFilterBefore(new CustomAuthorizationFilter(userService), UsernamePasswordAuthenticationFilter.class);
    }
    //    private AppBasicAuthenticationEntryPoint appBasicAuthenticationEntryPoint;
//
//    public SecurityConfig(AppBasicAuthenticationEntryPoint appBasicAuthenticationEntryPoint) {
//        this.appBasicAuthenticationEntryPoint = appBasicAuthenticationEntryPoint;
//    }
////    @Bean
////    public UserDetailsService userDetailsService() {
////        return new ShopmeUserDetailsService();
////    }
//
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http.csrf().disable()
//                .authorizeRequests().anyRequest().authenticated()
//                .and()
//                .httpBasic()
//                .authenticationEntryPoint(appBasicAuthenticationEntryPoint);
//        return http.build();
//
////        http.csrf().disable();
////        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
////        http.authorizeRequests().anyRequest().permitAll();
////        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean(authenticationConfiguration)));
////
////        return http.build();
//
//    }
//
////    @Bean
////    public WebSecurityCustomizer webSecurityCustomizer() {
////
////    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
