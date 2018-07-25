import com.splitcorp.first.dao.inheritance.NUserDao;
import com.splitcorp.first.dao.separate.ConnectionMaker;
import com.splitcorp.first.dao.separate.DConnectionMaker;
import com.splitcorp.first.dao.separate.UserDaoSeparated;
import com.splitcorp.first.dto.User;
import com.splitcorp.first.factory.DaoFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


public class DaoTest {

    @Test
    public void inheritanceTest() {

        User user = new User();
        user.setId("test1");
        user.setName("테스터1");
        user.setPassword("123");

        NUserDao nUser = new NUserDao();

        try {
            nUser.add(user);
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void separateTest() {
        ConnectionMaker connectionMaker = new DConnectionMaker();
        UserDaoSeparated userDaoSeparated = new UserDaoSeparated(connectionMaker);
    }


}
