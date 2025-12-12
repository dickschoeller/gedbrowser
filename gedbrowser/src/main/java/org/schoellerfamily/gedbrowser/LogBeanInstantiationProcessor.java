package org.schoellerfamily.gedbrowser;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LogBeanInstantiationProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(
        @NonNull
        final Object bean,
        @NonNull
        final String beanName) throws BeansException {
        // No action needed before initialization for this purpose
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(
        @NonNull
        final Object bean,
        @NonNull
        final String beanName) throws BeansException {
        log.debug("Bean instantiated: Name='{}', Class='{}'", beanName, bean.getClass().getName());
        return bean;
    }
}
