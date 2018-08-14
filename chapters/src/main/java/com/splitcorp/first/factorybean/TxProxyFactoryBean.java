package com.splitcorp.first.factorybean;

import com.splitcorp.first.service.advice.TransactionAdvice;
import com.splitcorp.first.tamplateCallback.TransactionHandler;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import java.lang.reflect.Proxy;
import java.util.List;

public class TxProxyFactoryBean implements FactoryBean<Object> {
    Object target;
    PlatformTransactionManager transactionManager;
    String pattern;
    Class<?> serviceInterface;
    List<TransactionAdvice> intercepterNames;

    public void setTarget(Object target) {
        this.target = target;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public void setServiceInterface(Class<?> serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    public void setIntercepterNames(List<TransactionAdvice> intercepterNames) {
        this.intercepterNames = intercepterNames;
    }

    @Override
    public Object getObject() throws Exception {
        TransactionHandler txHandler = new TransactionHandler();
        txHandler.setTarget(target);
        txHandler.setTransactionManager(transactionManager);
        txHandler.setPattern(pattern);
        return Proxy.newProxyInstance(
                getClass().getClassLoader(), new Class[] {serviceInterface},
                txHandler
        );
    }

    @Override
    public Class<?> getObjectType() {
        return serviceInterface;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
