package cn.com.yusys.security.web.headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

/**
 * 扩展spring-security默认安全策略，提供通用web安全策略默认配置，扩展可通过配置文件{@link HeadersSecurityProperties}修改配置策略
 * @author yangzq80@gmail.com
 * @date 2020-04-03 18:02
 */
@EnableWebSecurity
@ConditionalOnClass(DefaultAuthenticationEventPublisher.class)
@Order(99)
@EnableConfigurationProperties(HeadersSecurityProperties.class)
public class HeadersSecurityAutoConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    HeadersSecurityProperties headersSecurityProperties;

    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).requireCsrfProtectionMatcher(csrfRequestMatcher)

                .and().headers().contentTypeOptions().disable().frameOptions().disable().xssProtection().disable().cacheControl().disable()

                .addHeaderWriter(new StaticHeadersWriter(
                        "X-Frame-Options",headersSecurityProperties.getFrameOptions()))

                .addHeaderWriter(new StaticHeadersWriter(
                        "Content-Security-Policy",headersSecurityProperties.getCsp()))

                .addHeaderWriter(new StaticHeadersWriter(
                        "Cache-Control",headersSecurityProperties.getCacheControl()))

                .addHeaderWriter(new StaticHeadersWriter(
                        "X-Content-Type-Options",headersSecurityProperties.getContentTypeOptions()))

                .addHeaderWriter(new StaticHeadersWriter(
                        "X-Xss-Protection",headersSecurityProperties.getXss()))

                .addHeaderWriter(new StaticHeadersWriter(
                        "Strict-Transport-Security",headersSecurityProperties.getHsts()))

                .addHeaderWriter(new StaticHeadersWriter(
                        "referrer-policy",headersSecurityProperties.getReferrerPolicy()))

                .addHeaderWriter(new StaticHeadersWriter(
                        "Feature-Policy",headersSecurityProperties.getFeaturePolicy()));
    }

    // Build the request matcher for CSFR
    RequestMatcher csrfRequestMatcher = new RequestMatcher() {
        @Override
        public boolean matches(HttpServletRequest request) {
            if (headersSecurityProperties.getCsrf().isEmpty()){
                return false;
            }

            String []patterns = headersSecurityProperties.getCsrf().split(",");

            for(String p:patterns){
                // If the request match one url the CSFR protection will be enabled
                if (new AntPathRequestMatcher(p).matches(request)){
                    return true;
                }
            }
            return false;
        }
    };

}