import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import reflect.Hello;
import reflect.HelloTarget;
import reflect.UppercaseHandler;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ReflectionTest {

    @Test
    public void invokeMethod() throws Exception {
        String name = "Spring";

        assertThat(name.length(), is(6));

        Method lengthMethod = String.class.getMethod("length");
        assertThat(lengthMethod.invoke(name), is(6));

        assertThat(name.charAt(0), is('S'));

        Method charAtMehod = String.class.getDeclaredMethod("charAt", int.class);
        assertThat(charAtMehod.invoke(name, 0), is('S'));
    }

    @Test
    public void simpleProxy() {
        Hello hello = new HelloTarget();
        assertThat(hello.sayHello("Toby"), is("Hello Toby"));

        //Hello proxiedHello = new HelloUppercase(new HelloTarget());
        Hello proxiedHello = (Hello) Proxy.newProxyInstance(
                                getClass().getClassLoader(),
                                new Class[] {Hello.class},
                                new UppercaseHandler(new HelloTarget())
                            );
        assertThat(proxiedHello.sayHello("Toby"), is("HELLO TOBY"));

    }

    @Test
    public void proxyFactoryBean() {
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(new HelloTarget());

        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName("sayH*");

        //pfBean.addAdvice(new UppercaseAdvice());
        // 부가 기능만을 넣어주는 Advice
        pfBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvice()));
        // 메소드 선정 알고리즘 point cut

        Hello proxiedHello = (Hello) pfBean.getObject();

        assertThat(proxiedHello.sayHello("Toby"), is("HELLO TOBY"));

        // 메소드 이름이 포인트컷의 선정 조건에 맞지 않으므로, 부가기능이 적용되지 않는다.
        assertThat(proxiedHello.sayThankYou("Toby"), is("Thank you Toby"));
    }

    static class UppercaseAdvice implements MethodInterceptor {
        @Override
        public Object invoke(MethodInvocation methodInvocation) throws Throwable {
            String ret = (String)methodInvocation.proceed();
            return ret.toUpperCase();
        }
    }
}
