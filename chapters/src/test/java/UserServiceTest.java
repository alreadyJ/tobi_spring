import com.splitcorp.first.dto.User;
import com.splitcorp.first.factorybean.TxProxyFactoryBean;
import com.splitcorp.first.mail.MockMailSender;
import com.splitcorp.first.service.UserService;
import com.splitcorp.first.service.UserServiceImpl;
import com.splitcorp.first.tamplateCallback.UserDao;
import exception.TestUserServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;

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
    private ApplicationContext context;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private UserService userService;

    @Autowired
    private UserService testUserService;

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
    @DirtiesContext
    public void upgradeAllOrNothing() throws Exception {
        List<User> users = new ArrayList<>();
        UserServiceImpl testUserService = new TestUserServiceImpl(users.get(0).getId());
        testUserService.setUserDao(this.userDao);// 수동 DI?
        //testUserService.setDataSource(this.dataSource);
        testUserService.setMailSender(this.mailSender); // 이것은 수동

        /*
        UserServiceTx txUserService = new UserServiceTx();
        txUserService.setPlatformTransactionManager(transactionManager);
        txUserService.setUserService(testUserService);
        */

        // 다이나믹 프록시 패턴을 적용한 방법
        /*
        TransactionHandler txHandler = new TransactionHandler();
        txHandler.setTarget(testUserService);
        txHandler.setTransactionManager(transactionManager);
        txHandler.setPattern("upgradeLevels");
        UserService txUserService = (UserService) Proxy.newProxyInstance(
                getClass().getClassLoader(), new Class[] {UserService.class}, txHandler);
        */
        // 프록시 팩토리 빈을 활용한 방법
        // 이 방법을 사용하면 프록시 팩토리 빈에서 트랜젝션 기능이 부여된다.
        TxProxyFactoryBean txProxyFactoryBean =
        context.getBean("&userService", TxProxyFactoryBean.class);
        txProxyFactoryBean.setTarget(testUserService);
        UserService txUserService = (UserService) txProxyFactoryBean.getObject();


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

    private static class TestUserServiceImpl extends UserServiceImpl {
        private String id = "madnite1";

        /*
        @Autowired
        private UserServiceImpl userServiceImpl;
        */

        public TestUserServiceImpl(String id) {
            this.id = id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Override
        protected void upgradeLevel(User user) {
            if (user.getId().equals(this.id)) throw new TestUserServiceException();
            super.upgradeLevel(user);
        }
    }
}
