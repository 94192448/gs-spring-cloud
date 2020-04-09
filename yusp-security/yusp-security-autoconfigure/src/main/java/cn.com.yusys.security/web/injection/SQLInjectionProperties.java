package cn.com.yusys.security.web.injection;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yangzq80@gmail.com
 * @date 2020-04-09 14:53
 */
@ConfigurationProperties(prefix = "yusp.security.sql-injection")
public class SQLInjectionProperties {
    private boolean enabled = false;

    //\\b  表示 限定单词边界  比如  select 不通过   1select则是可以的
    private String regex = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"
            + "(\\b(select|update|union|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";


    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

}
