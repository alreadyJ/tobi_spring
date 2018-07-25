import com.splitcorp.first.dao.separate.UserDaoSeparated;
import com.splitcorp.first.factory.DaoFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class FactoryTest {

    @Test
    public void test() {
        DaoFactory daoFactory = new DaoFactory();
        UserDaoSeparated userDaoSeparated = daoFactory.userDaoSeparated();

    }

    @Test
    public void applicationContextTest() {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDaoSeparated userDaoSeparated = context.getBean("userDaoSeparated", UserDaoSeparated.class);

        System.out.println(userDaoSeparated.hashCode());
    }
}
