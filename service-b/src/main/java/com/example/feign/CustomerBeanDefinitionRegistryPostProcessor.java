package com.example.feign;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.PropertySource;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yangzq80@gmail.com
 * @date 2020-07-13
 * 自定义FeignCliens增强，支持根据配置文件动态映射serviceId
 */
@Configuration
@Slf4j
public class CustomerBeanDefinitionRegistryPostProcessor{


    @Bean
    public BeanDefinitionRegistryPostProcessor beanPostProcessor(ConfigurableEnvironment environment) {
        return new PostProcessor(environment);
    }

    class PostProcessor implements BeanDefinitionRegistryPostProcessor {

        Map<String,String> mappings = new HashMap<>();

        public PostProcessor(ConfigurableEnvironment environment) {
            readProperty(environment);
        }
        @Override
        public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
            for (String name : registry.getBeanDefinitionNames()) {

                BeanDefinition beanDefinition = registry.getBeanDefinition(name);
                if("org.springframework.cloud.openfeign.FeignClientFactoryBean".equalsIgnoreCase(beanDefinition.getBeanClassName())){
                    //log.info(beanDefinition.getPropertyValues().toString());

                    MutablePropertyValues mutablePropertyValues = beanDefinition.getPropertyValues();

                    //动态读取配置信息
                    String mappingService = getMappingService(mutablePropertyValues.get("name").toString());

                    if (mappingService==null){
                        return;
                    }

                    mutablePropertyValues.add("name",mappingService);

                    try {
                        Field field = AbstractBeanDefinition.class.getDeclaredField("propertyValues");
                        field.setAccessible(true);
                        field.set(beanDefinition,mutablePropertyValues);
                        field.setAccessible(false);
                        registry.registerBeanDefinition(name,beanDefinition);

                    }catch (Exception e){
                        log.error("Customer register FeignClientFactoryBean error {}",e.getMessage());
                    }
                }

            }
        }

        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        }


        /*
         * Iterates over all configuration sources, looking for the property value.
         * As Spring orders the property sources by relevance, the value of the first
         * encountered property with the correct name is read and returned.
         */
        private void readProperty(ConfigurableEnvironment environment) {
            for (PropertySource<?> source : environment.getPropertySources()) {
                if (source instanceof EnumerablePropertySource) {
                    EnumerablePropertySource<?> propertySource = (EnumerablePropertySource<?>) source;
                    for (String property : propertySource.getPropertyNames()) {
                        if (property.matches("zuul.routes.(.*?).path")){
                            String value = (String)propertySource.getProperty(property);

                            String key = property.replace(".path","")+".serviceId";

                            mappings.put(value.replaceAll("/","").replaceAll("\\*",""),(String) propertySource.getProperty(key));
                        }
                    }
                }
            }
        }
        private String getMappingService(String originaService){
            originaService = originaService.replaceAll("/","");
            return mappings.get(originaService);
        }
    }

}
