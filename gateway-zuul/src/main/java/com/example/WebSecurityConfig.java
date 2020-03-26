package com.example;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity(debug = true)
//@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // by default uses a Bean by the name of corsConfigurationSource
                .cors()
                .and()
                    .csrf().ignoringAntMatchers("/**")
                .and()

                    //X-Frame-Options: SAMEORIGIN
                    .headers().frameOptions().sameOrigin()

                    //Strict-Transport-Security: max-age=31536000 ; includeSubDomains ; preload
                    .httpStrictTransportSecurity().maxAgeInSeconds(31536000L).and()

                    .addHeaderWriter(new StaticHeadersWriter(
                            "Content-Security-Policy","script-src 'self';report-uri /csp-report"))
                .and()
                    .headers().referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.SAME_ORIGIN);
//                .and()
//                    .authorizeRequests().antMatchers("/**").permitAll();
    }

    //@Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("https://example.com"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        //configuration.addExposedHeader("Location");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
