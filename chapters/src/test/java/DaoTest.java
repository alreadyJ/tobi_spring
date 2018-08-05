import com.splitcorp.first.dao.inheritance.NUserDao;
import com.splitcorp.first.dao.separate.ConnectionMaker;
import com.splitcorp.first.dao.separate.DConnectionMaker;
import com.splitcorp.first.dao.separate.UserDaoSeparated;
import com.splitcorp.first.dto.User;
import com.splitcorp.first.tamplateCallback.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext-database.xml")
public class DaoTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ApplicationContext context;
    // 이것이 되는 이유는 스프링 어플리케이션 컨텍스트는 초기화할 때 자기 자신도 빈으로 등록한다.

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


    /*
    @Test
    public void addAndGet() throws ClassNotFoundException , SQLException {

         User user = new User();
         user.setId("test");
         user.setName("ttt");
         user.setPassword("asdasdasd");

         userDaoSeparated.add(user);

         //userDaoSeparated.setDataSource(null);
         // 이 경우에는 테스트 전체에 영향을 미칠 수 있다, 그 이유는 userDaoSeparated를 공유하기 때문에
         // 따라서 @DirtiesContext Annotation을 붙여줘야 한다.



         User user2 = userDaoSeparated.get(user.getId());

         assertThat(user2.getName(), is(user.getName()));
         assertThat(user2.getPassword(), is(user.getPassword()));

    }*/

    @Test
    public void userDaoTest() {
        assertThat(userDao, is(notNullValue()));
    }

    @Test
    public void update() {
        userDao.deleteAll();
        /*
        * 이하 생략
        * */
    }
}
