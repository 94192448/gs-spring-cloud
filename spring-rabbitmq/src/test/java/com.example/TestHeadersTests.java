package com.example;

import com.example.header.HeaderSend;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yangzq80@gmail.com
 * @date 2020-07-03
 */
public class TestHeadersTests {
    // 测试线程池
    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    // rabbitmq的IP地址
    private final String rabbitmq_host = "192.168.251.157";
    // rabbitmq的用户名称
    private final String rabbitmq_user = "admin";
    // rabbitmq的用户密码
    private final String rabbitmq_pwd = "admin";

    @Test
    public void header() throws InterruptedException {

        // 消费者1：绑定 type=h1
//        executorService.submit(() -> {
//            Map<String,Object> headers = new HashMap();
//            headers.put("type","h1");
//            HeaderRecv.execute(rabbitmq_host, rabbitmq_user, rabbitmq_pwd, headers);
//        });
//
//        // 消费者2：绑定  type=h2
//        executorService.submit(() -> {
//            Map<String,Object> headers = new HashMap();
//            headers.put("type","h2");
//            HeaderRecv.execute(rabbitmq_host, rabbitmq_user, rabbitmq_pwd, headers);
//        });
//
//        // 消费者3：绑定  format=zip,type=report
//        executorService.submit(() -> {
//            Map<String,Object> headers = new HashMap();
//            headers.put("type","h3");
//            headers.put("type2","h22");
//            headers.put("x-match","any");
//            HeaderRecv.execute(rabbitmq_host, rabbitmq_user, rabbitmq_pwd, headers);
//        });



        Thread.sleep(2* 1000);
        // 生产者1 ： format=pdf,type=reprot,x-match=all
        executorService.submit(() -> {
            Map<String,Object> headers = new HashMap();
            headers.put("type","h1");
            //     headers.put("x-match","all");
            HeaderSend.execute(rabbitmq_host, rabbitmq_user, rabbitmq_pwd, headers);
        });


        Thread.sleep(5* 100);
        // 生产者2 ： format=pdf,x-match=any
        executorService.submit(() -> {
            Map<String,Object> headers = new HashMap();
            headers.put("type","h2");
            headers.put("type2","h22");
            //     headers.put("x-match","any");
            HeaderSend.execute(rabbitmq_host, rabbitmq_user, rabbitmq_pwd, headers);
        });

        Thread.sleep(5* 100);
        // 生产者3 ： format=zip,type=log,x-match=all
        executorService.submit(() -> {
            Map<String,Object> headers = new HashMap();
            headers.put("format","zip");
            headers.put("type","h3");
            //      headers.put("x-match","all");
            HeaderSend.execute(rabbitmq_host, rabbitmq_user, rabbitmq_pwd, headers);
        });

//        executorService.submit(() -> {
//            Map<String,Object> headers = new HashMap();
//            headers.put("type","h1");
//            HeaderRecv.execute(rabbitmq_host, rabbitmq_user, rabbitmq_pwd, headers);
//        });
        Thread.sleep(2* 1000);
        // 生产者1 ： format=pdf,type=reprot,x-match=all
        executorService.submit(() -> {
            Map<String,Object> headers = new HashMap();
            headers.put("type","h1");
            //     headers.put("x-match","all");
            HeaderSend.execute(rabbitmq_host, rabbitmq_user, rabbitmq_pwd, headers);
        });

        // sleep 10s
        Thread.sleep(30 * 1000);
    }
}
