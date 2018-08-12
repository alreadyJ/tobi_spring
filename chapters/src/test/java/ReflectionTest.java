import org.junit.Test;
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
}
