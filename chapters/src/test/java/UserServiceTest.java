import com.splitcorp.first.dto.User;
import com.splitcorp.first.service.UserService;
import com.splitcorp.first.tamplateCallback.UserDao;
import exception.TestUserServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext-database.xml",
        "classpath:spring/applicationContext-bean.xml"})
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private DataSource dataSource;

    @Autowired
    MailSender mailSender;

    @Test
    public void bean() {
        assertThat(this.userService, is(notNullValue()));
    }

    @Test
    public void upgradeAllOrNothing() throws Exception {
        List<User> users = new ArrayList<>();
        UserService testUserService = new TestUserService(users.get(0).getId());
        testUserService.setUserDao(this.userDao);// 수동 DI?
        testUserService.setDataSource(this.dataSource);
        testUserService.setMailSender(this.mailSender); // 이것은 수동

        userDao.deleteAll();
        for (User user : users) userDao.add(user);

        try {
            testUserService.upgradeLevels();
            fail("TestUserSericeException expected");
        } catch (TestUserServiceException e) {

        }
    }

    private static class TestUserService extends UserService {
        private String id;

        private TestUserService(String id) {
            this.id = id;
        }

        @Override
        protected void upgradeLevel(User user) {
            if (user.getId().equals(this.id)) throw new TestUserServiceException();
            super.upgradeLevel(user);
        }
    }
}
