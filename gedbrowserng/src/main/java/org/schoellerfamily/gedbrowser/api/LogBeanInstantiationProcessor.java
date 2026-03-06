package org.schoellerfamily.gedbrowser.api;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Dick Schoeller
 */
@Component
@Slf4j
public final class LogBeanInstantiationProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(
        final Object bean,
        final String beanName) throws BeansException {
        // No action needed before initialization for this purpose
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(
        final Object bean,
        final String beanName) throws BeansException {
        log.info("Bean instantiated: Name='{}', Class='{}'", beanName, bean.getClass().getName());
        return bean;
    }
}
