package separate.user.dao;

import java.sql.Connection;
import java.sql.SQLException;

//@Configuration
public class CountingConnectionMaker implements ConnectionMaker {
    int count = 0;
    private ConnectionMaker realConnectionMaker;

    //@Bean
    CountingConnectionMaker(ConnectionMaker realConnectionMaker) {
        this.realConnectionMaker = realConnectionMaker;
    }
    //@Bean
    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        this.count++;
        return realConnectionMaker.makeConnection();
    }

    public int getCounter() {
        return this.count;
    }
}
