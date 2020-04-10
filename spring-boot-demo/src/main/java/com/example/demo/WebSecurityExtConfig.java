package com.example.demo;

import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

//@EnableWebSecurity(debug = true)
@Order(99)
public class WebSecurityExtConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().requireCsrfProtectionMatcher(csrfRequestMatcher);
    }

    // Build the request matcher for CSFR
    RequestMatcher csrfRequestMatcher = new RequestMatcher() {

        private RegexRequestMatcher requestMatcher =
                new RegexRequestMatcher("/user/*", null);

        @Override
        public boolean matches(HttpServletRequest request) {

            // Enable the CSRF
            if(requestMatcher.matches(request))
                return true;

            // You can add here any other rule on the request object, returning
            // true if the CSRF must be enabled, false otherwise
            // ....

            // No CSRF for other requests
            return false;
        }

    }; // new RequestMatcher
}
