package cn.com.yusys.security.web.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Content-Security-Policy 攻击者安全攻击信息收集
 * @author yangzq80@gmail.com
 * @date 2020-04-10 17:13
 */
@Controller
@RequestMapping("/csp-report")
public class CspReport {
    private static final Logger logger = LoggerFactory.getLogger(CspReport.class);

    @PostMapping
    public void saveCSPReport(@RequestBody String cspReport){
        logger.error("yusp-security-headers-csp error:{}",cspReport);
    }
}
