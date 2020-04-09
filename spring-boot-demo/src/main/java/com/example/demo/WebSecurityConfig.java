package com.example.demo;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

//@EnableWebSecurity(debug = true)
//@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // by default uses a Bean by the name of corsConfigurationSource
                .cors()
                .and()
                    .csrf().ignoringAntMatchers("/**")


                    //X-Frame-Options: SAMEORIGIN
                .and().headers().frameOptions().sameOrigin()
                    .addHeaderWriter(new StaticHeadersWriter(
                        "Content-Security-Policy","script-src 'self';report-uri /csp-report"))

                    //Strict-Transport-Security: max-age=31536000 ; includeSubDomains ; preload
                .addHeaderWriter(new StaticHeadersWriter("Strict-Transport-Security","max-age=31536000 ; includeSubDomains ; preload"))
                    //.httpStrictTransportSecurity().includeSubdomains(true).maxAgeInSeconds(31536000L)

                .addHeaderWriter(new StaticHeadersWriter("referrer-policy","origin-when-cross-origin, strict-origin-when-cross-origin"))

                .addHeaderWriter(new StaticHeadersWriter("Feature-Policy","vibrate 'self'; sync-xhr 'self'"))

                .referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.SAME_ORIGIN);

        http.authorizeRequests().antMatchers("/user").permitAll();
    }
}
