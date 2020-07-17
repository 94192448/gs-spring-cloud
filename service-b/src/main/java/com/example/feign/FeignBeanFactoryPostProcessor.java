package com.example.feign;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * @author yangzq80@gmail.com
 * @date 2020-07-10
 */
//@Component
@Slf4j
public class FeignBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        //beanFactory.getBeanDefinition("");

        log.info(beanFactory.getBeanNamesIterator().next());
    }
}
