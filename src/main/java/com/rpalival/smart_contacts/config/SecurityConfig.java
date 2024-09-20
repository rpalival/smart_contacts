package com.rpalival.smart_contacts.config;

import com.rpalival.smart_contacts.helpers.ConfigHelper;
import com.rpalival.smart_contacts.services.impl.SecurityCustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final SecurityCustomUserDetailService userDetailsService;
    private final OAuthAuthenticationSuccessHandler successHandler;
    private final AuthFailureHandler authFailureHandler;
    private final ConfigHelper configHelper;

    @Autowired
    public SecurityConfig(
            SecurityCustomUserDetailService userDetailsService,
            OAuthAuthenticationSuccessHandler successHandler,
            ConfigHelper configHelper,
            AuthFailureHandler authFailureHandler
    ) {
        this.userDetailsService = userDetailsService;
        this.successHandler = successHandler;
        this.configHelper = configHelper;
        this.authFailureHandler = authFailureHandler;
    }




    // configuration of authentication provider
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        // user details service object
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        //password encoder object
        daoAuthenticationProvider.setPasswordEncoder(configHelper.passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{



        httpSecurity.authorizeHttpRequests(authorize->{
//           authorize.requestMatchers("/home", "/register", "/services").permitAll();
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });

        httpSecurity.formLogin(formLogin -> {
            formLogin.loginPage("/login");
            formLogin.loginProcessingUrl("/authenticate");
            formLogin.defaultSuccessUrl("/user/profile");
//            formLogin.failureForwardUrl("/login?error=true");
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");
            
            formLogin.failureHandler(authFailureHandler);
        });

        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        httpSecurity.logout(logoutForm -> {
            logoutForm.logoutUrl("/logout");
            logoutForm.logoutSuccessUrl("/login?logout=true");
        });

        // oauth configs
        httpSecurity.oauth2Login(oauth -> {
            oauth.loginPage("/login");
            oauth.successHandler(successHandler);
        });

        return httpSecurity.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
