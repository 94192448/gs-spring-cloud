package cn.com.yusys.security.web.headers;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * SQL injection filter
 *
 * @author yangzq80@gmail.com
 * @date 2020-04-03 16:25
 */
@ConfigurationProperties(prefix = "yusp.security.headers")
public class HeadersSecurityProperties {

    private String xss = "1; mode=block";

    /**
     * Strict-Transport-Security: max-age=31536000 ; includeSubDomains ; preload
     */
    private String hsts = "max-age=31536000 ; includeSubDomains ; preload";

    /**
     * Content-Security-Policy
     */
    private String csp = "script-src 'self';report-uri /csp-report";


    private String csrf;


    /**
     * X-Frame-Options
     */
    private String frameOptions = "SAMEORIGIN";

    /**
     * X-Content-Type-Options: nosniff
     */
    private String contentTypeOptions = "nosniff";

    /**
     * Cache-Control
     */
    private String cacheControl = "no-cache, no-store, max-age=0, must-revalidate";

    /**
     * referrer-policy
     */
    private String referrerPolicy = "origin-when-cross-origin, strict-origin-when-cross-origin";

    /**
     * Feature-Policy
     */
    private String featurePolicy = "vibrate 'self'; sync-xhr 'self'";

    public String getXss() {
        return xss;
    }

    public void setXss(String xss) {
        this.xss = xss;
    }

    public String getHsts() {
        return hsts;
    }

    public void setHsts(String hsts) {
        this.hsts = hsts;
    }

    public String getCsrf() {
        return csrf;
    }

    public void setCsrf(String csrf) {
        this.csrf = csrf;
    }

    public String getCsp() {
        return csp;
    }

    public void setCsp(String csp) {
        this.csp = csp;
    }

    public String getFrameOptions() {
        return frameOptions;
    }

    public void setFrameOptions(String frameOptions) {
        this.frameOptions = frameOptions;
    }

    public String getContentTypeOptions() {
        return contentTypeOptions;
    }

    public void setContentTypeOptions(String contentTypeOptions) {
        this.contentTypeOptions = contentTypeOptions;
    }

    public String getReferrerPolicy() {
        return referrerPolicy;
    }

    public void setReferrerPolicy(String referrerPolicy) {
        this.referrerPolicy = referrerPolicy;
    }

    public String getFeaturePolicy() {
        return featurePolicy;
    }

    public void setFeaturePolicy(String featurePolicy) {
        this.featurePolicy = featurePolicy;
    }

    public String getCacheControl() {
        return cacheControl;
    }

    public void setCacheControl(String cacheControl) {
        this.cacheControl = cacheControl;
    }
}
