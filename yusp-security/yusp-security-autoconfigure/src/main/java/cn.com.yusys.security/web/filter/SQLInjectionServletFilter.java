package cn.com.yusys.security.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;
import java.util.Enumeration;
import java.util.regex.Pattern;

/**
 * 防止参数中SQL注入的过滤器
 * @author yangzq80@gmail.com
 * @date 2020-04-07 09:48
 */
public class SQLInjectionServletFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(SQLInjectionServletFilter.class);

    private static String regex="";

    private Pattern sqlPattern;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
         regex = filterConfig.getInitParameter("regex");
         sqlPattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        Enumeration<String> params = servletRequest.getParameterNames();
        while (params.hasMoreElements()) {
            //得到参数名
            String name = params.nextElement();
            //得到参数对应值
            String[] value = servletRequest.getParameterValues(name);
            for (int i = 0; i < value.length; i++) {
                if (!isValid(value[i])) {
                    throw new IOException("The parameter in your request contains illegal characters:"+value[i]);
                }
            }
        }

        //ignore stream handle

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }

    // Verify SQL
    private boolean isValid(String str) {

        if (sqlPattern.matcher(str).find()) {
            logger.error("sql injection：str={}",str);
            return false;
        }
        return true;
    }
}
