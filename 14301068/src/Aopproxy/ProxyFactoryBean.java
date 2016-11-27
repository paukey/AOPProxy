package Aopproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactoryBean implements InvocationHandler {

    private String proxyInterfaces;
    private Object target;
    private String inNames;

    public void setFactory(BeanFactory factory) {
        this.factory = factory;
    }

    private BeanFactory factory;

    public BeanFactory getFactory() {
        return factory;
    }

    public String getProxyInterfaces() {
        return proxyInterfaces;
    }

    public void setProxyInterfaces(String proxyInterfaces) {
        this.proxyInterfaces = proxyInterfaces;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public String getInNames() {
        return inNames;
    }

    public void setInNames(String inNames) {
        this.inNames = inNames;
    }

  
    public Object invoke(Object proxy, Method met, Object[] args) throws Throwable {
        if(inNames!=null) {
            Object advice = factory.getBean(inNames);
            if ( advice instanceof MethodBeforeAdvice) {
                ((MethodBeforeAdvice)advice).before(met,args,target);
                Object result = met.invoke(target,args);
                return result;
            }
        }
        Object result = method.invoke(target,args);
        return result;
    }

  
    public String toString() {
        return "ProxyFactoryBean{" +
                "proxyInterfaces='" + proxyInterfaces + '\'' +
                ", target=" + target +
                ", interceptorNames='" + inNames + '\'' +
                '}';
    }

    public Object getProxy(){
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                target.getClass().getInterfaces(),
                this);
    }
}
