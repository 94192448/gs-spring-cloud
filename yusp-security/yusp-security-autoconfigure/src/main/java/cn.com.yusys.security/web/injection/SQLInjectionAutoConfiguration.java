package cn.com.yusys.security.web.injection;

import cn.com.yusys.security.web.filter.SQLInjectionServletFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * @author yangzq80@gmail.com
 * @date 2020-04-09 16:13
 */
@EnableConfigurationProperties(SQLInjectionProperties.class)
public class SQLInjectionAutoConfiguration {
    @Autowired
    SQLInjectionProperties sqlInjectionProperties;

    @Bean
    @ConditionalOnProperty(name = "yusp.security.sql-injection.enabled",
            havingValue = "true", matchIfMissing = false)
    public FilterRegistrationBean<SQLInjectionServletFilter> sqlInjectionFilter() {

        FilterRegistrationBean<SQLInjectionServletFilter> registration = new FilterRegistrationBean<SQLInjectionServletFilter>(
                new SQLInjectionServletFilter());

        registration.addUrlPatterns("/*");

        registration.addInitParameter("regex",sqlInjectionProperties.getRegex());

        return registration;
    }
}
