import com.splitcorp.first.dao.separate.ConnectionMaker;
import com.splitcorp.first.dao.separate.CountingConnectionMaker;
import com.splitcorp.first.dao.separate.UserDaoSeparated;
import com.splitcorp.first.factory.CountingDaoFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.apache.cocoon.configuration.Settings;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring/applicationContext-database.xml")
public class UserDaoConnectionCountingTest {
    @Test
    public void Test() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CountingDaoFactory.class);
        UserDaoSeparated userDaoSeparated = context.getBean("userDaoSeparated", UserDaoSeparated.class);

        CountingConnectionMaker ccm = context.getBean("connectionMaker", CountingConnectionMaker.class);
        System.out.println("Connection counter : " + ccm.getCount());
    }
}
