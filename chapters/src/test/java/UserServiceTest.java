import com.splitcorp.first.dto.User;
import com.splitcorp.first.mail.MockMailSender;
import com.splitcorp.first.service.UserService;
import com.splitcorp.first.service.UserServiceImpl;
import com.splitcorp.first.tamplateCallback.TransactionHandler;
import com.splitcorp.first.tamplateCallback.UserDao;
import exception.TestUserServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;

import java.lang.reflect.Proxy;
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
    private UserServiceImpl userServiceImpl;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    MailSender mailSender;

    @Test
    public void bean() {
        assertThat(this.userService, is(notNullValue()));
    }

    @Test
    public void upgradeAllOrNothing() throws Exception {
        List<User> users = new ArrayList<>();
        UserServiceImpl testUserService = new TestUserService(users.get(0).getId());
        testUserService.setUserDao(this.userDao);// 수동 DI?
        //testUserService.setDataSource(this.dataSource);
        testUserService.setMailSender(this.mailSender); // 이것은 수동
        /*
        UserServiceTx txUserService = new UserServiceTx();
        txUserService.setPlatformTransactionManager(transactionManager);
        txUserService.setUserService(testUserService);
        */
        TransactionHandler txHandler = new TransactionHandler();
        txHandler.setTarget(testUserService);
        txHandler.setTransactionManager(transactionManager);
        txHandler.setPattern("upgradeLevels");
        UserService txUserService = (UserService) Proxy.newProxyInstance(
                getClass().getClassLoader(), new Class[] {UserService.class}, txHandler);

        userDao.deleteAll();
        for (User user : users) userDao.add(user);

        try {
            txUserService.upgradeLevels(); // 기능이 분리된 tx로 호출하도록 변경
            fail("TestUserSericeException expected");
        } catch (TestUserServiceException e) {

        }
    }

    @Test
    public void upgradeLevels() throws Exception {
        List<User> users = new ArrayList<>();
        userDao.deleteAll();
        for (User user : users) {
            userDao.add(user);
        }

        MockMailSender mockMailSender = new MockMailSender();
        userServiceImpl.setMailSender(mockMailSender);

        userService.upgradeLevels();


    }

    private static class TestUserService extends UserServiceImpl {
        private String id;
        @Autowired
        private UserServiceImpl userServiceImpl;

        private TestUserService(String id) {
            this.id = id;
        }

        @Override
        protected void upgradeLevel(User user) {
            if (user.getId().equals(this.id)) throw new TestUserServiceException();
            super.upgradeLevel(user);
            MockMailSender mockMailSender = new MockMailSender();
            userServiceImpl.setMailSender(mockMailSender);
        }
    }
}
