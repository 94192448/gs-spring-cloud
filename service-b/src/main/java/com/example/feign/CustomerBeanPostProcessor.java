package com.example.feign;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @author yangzq80@gmail.com
 * @date 2020-07-13
 */
@Slf4j
@Component
public class CustomerBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.info("---------{}",beanName);
        if (beanName.contains(".FeignClientSpecification")){
            Class<?> targetClass = AopUtils.isAopProxy(bean) ? AopUtils.getTargetClass(bean) : bean.getClass();
            FeignClient annotation = targetClass.getAnnotation(FeignClient.class);
            if (annotation!=null){
                log.info(annotation.name());
            }
        }
//        if (beanName.endsWith("feignContext")){
//            Class<?> targetClass = AopUtils.isAopProxy(bean) ? AopUtils.getTargetClass(bean) : bean.getClass();
//
//            Field field = null;
//            try {
//
//                field = NamedContextFactory.class.getDeclaredField("configurations");
//
//                field.setAccessible(true);
//
//                Map<String,Object> newConfigurations = new ConcurrentHashMap();
//
//                newConfigurations = (Map<String, Object>) field.get(bean);
//
//                newConfigurations.put("service-a",newConfigurations.get("service-a/cc"));
//                newConfigurations.remove("service-a/cc");
//
//                field.set(bean, newConfigurations);
//
//                field.setAccessible(false);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//
//            log.info(bean.toString());
//        }
        if (beanName.equalsIgnoreCase("org.springframework.context.event.internalEventListenerFactory")){
//            ComponentScan componentScan = (ComponentScan) CustomerBeanPostProcessor.class.getAnnotation(ComponentScan.class);
//            InvocationHandler invocationHandler = Proxy.getInvocationHandler(componentScan);
//            Field value = null;
//            try {
//                value = invocationHandler.getClass().getDeclaredField("memberValues");
//                value.setAccessible(true);
//                Map<String, Object> memberValues = (Map<String, Object>) value.get(invocationHandler);
//                String[] values = (String[]) memberValues.get("value");
//                String[] newValues = new String[values.length + 1];
//                System.arraycopy(values, 0, newValues, 0, values.length);
//                newValues[newValues.length - 1] = "cn.jiangzeyin";
//                memberValues.put("value", newValues);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }

        return bean;
    }
}
