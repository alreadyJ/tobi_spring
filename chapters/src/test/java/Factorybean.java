import com.splitcorp.first.message.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext-bean.xml")
public class Factorybean {
    @Autowired
    private ApplicationContext context;

    @Test
    public void getMessageFromFactoryBean() {
        Object message = context.getBean("message");
        assertThat((Message)message, is(Message.class));
        assertThat(((Message)message).getText(), is("Factory Bean"));
    }

    @Test
    public void getFactoryBean() {
        Object factory = context.getBean("&message");


    }
}
