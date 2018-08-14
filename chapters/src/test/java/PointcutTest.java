import org.junit.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import reflect.Hello;
import reflect.HelloTarget;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PointcutTest {

    @Test
    public void classNamePointcutAdvisor () {
        NameMatchMethodPointcut classMethodPointcut = new NameMatchMethodPointcut() {
            public ClassFilter getClassFilter() {
                return new ClassFilter() {
                    public boolean matches(Class<?> clazz) {
                        return clazz.getSimpleName().startsWith("HelloT");
                    }
                };
            }
        };
        classMethodPointcut.setMappedName("sayH*");

        checkAdviced(new HelloTarget(), classMethodPointcut, true);

        class Helloworld extends HelloTarget {}
        checkAdviced(new Helloworld(), classMethodPointcut, false);

        class HelloToby extends HelloTarget {}
        checkAdviced(new HelloToby(), classMethodPointcut, true);
    }


    private void checkAdviced(Object target, Pointcut pointcut, boolean adviced) {
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(target);
        pfBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new ReflectionTest.UppercaseAdvice()));
        Hello proixedHello = (Hello) pfBean.getObject();

        if (adviced) {
            assertThat(proixedHello.sayHello("Toby"), is("HELLO TOBY"));
            assertThat(proixedHello.sayHi("Toby"), is("HI TOBY"));
            assertThat(proixedHello.sayThankYou("Toby"), is("Thank you Toby"));
        } else {
            assertThat(proixedHello.sayHello("Toby"), is("Hello Toby"));
            assertThat(proixedHello.sayHi("Toby"), is("Hi Toby"));
            assertThat(proixedHello.sayThankYou("Toby"), is("Thank you Toby"));
        }
    }
}
