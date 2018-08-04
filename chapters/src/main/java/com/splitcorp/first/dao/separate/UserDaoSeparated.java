package com.splitcorp.first.dao.separate;

import com.mysql.jdbc.MysqlErrorNumbers;
import com.splitcorp.first.dto.User;
import com.splitcorp.first.exception.DuplicateUserIdException;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.dao.DuplicateKeyException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoSeparated {

    private DataSource dataSource;
    private JdbcContext jdbcContext;
    private ConnectionMaker connectionMaker;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setJdbcContext(JdbcContext jdbcContext) {
        this.jdbcContext = jdbcContext;
    }
    /*
    public ConnectionMaker getConnectionMaker() {
        return connectionMaker;
    }
*/
    // 수정자 메소드를 사용하는 경우
    /*public void setConnectionMaker(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }
*/
    public UserDaoSeparated() {

    }

    public UserDaoSeparated(ConnectionMaker connectionMaker) {
        //connectionMaker = new DConnectionMaker();
        this.connectionMaker = connectionMaker;
    }



    public void add(final User user) throws DuplicateUserIdException, SQLException {
        /*class AddStatement implements StatementStrategy {
            public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                PreparedStatement ps = c.prepareStatement(
                        "INSERT INTO user (id, name, password) VALUES (?, ?, ?)");
                ps.setString(1, user.getId());
                ps.setString(2, user.getName());
                ps.setString(3, user.getPassword());

                ps.executeUpdate();

                ps.close();
                c.close();
                return ps;
            }
        }
        익명 내부 클래스
        */


        /* 컨텍스트의 역할은? IoC를 할 수 있도록 해주는 Container의 역할이다? */
        /*
        * 이 컨텍스트는 JDBC의 일반적인 작업 흐름을 담고 있기 때문에 분리가 필요하다
        *
        * 여기서 전략패턴의 jdbcContext의 메소드를 템플릿이라 부르고, 익명 내부 클래스로 만들어진 오브젝트를 콜백이라고 부른다.
        * */
        try {
            this.jdbcContext.workWithStatementStrategy(new StatementStrategy() {
                public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                    PreparedStatement ps = c.prepareStatement(
                            "INSERT INTO user (id, name, password) VALUES (?, ?, ?)");
                    ps.setString(1, user.getId());
                    ps.setString(2, user.getName());
                    ps.setString(3, user.getPassword());

                    ps.executeUpdate();

                    ps.close();
                    c.close();
                    return ps;
                }
            });
        } catch (SQLException e) {
            if (e.getErrorCode() == MysqlErrorNumbers.ER_DUP_ENTRY) {
                throw new DuplicateUserIdException("duplicated user's id, please check it.", e);
            } else {
                throw new RuntimeException(e);
            }
        }


    }





    public void deleteAll() throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;

        try {
            //c = dataSource.getConnection();
            //dataSource가 없어서 사용 불가

            //ps = makeStatement(c);
            StatementStrategy strategy = new DeleteAllStatement();
            // 위의 줄은 클라이언트에 들어가야할 코드이다. 따라서 분리가 필요하다
            ps = strategy.makePreparedStatement(c);


            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {

                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {

                }
            }
        }
    }

    public void deleteAllByStrategy() throws SQLException {
        this.jdbcContext.executeSql("delete from user");
        /*
        StatementStrategy st = new DeleteAllStatement();
        this.jdbcContext.workWithStatementStrategy(st);
        */
    }
/*
    public void executeSql(final String query) throws SQLException {
        this.jdbcContext.workWithStatementStrategy(new StatementStrategy() {
            public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                return c.prepareStatement(query);
            }
        });
    }
*/

    protected PreparedStatement makeStatement(Connection c) throws SQLException {
        PreparedStatement ps = c.prepareStatement("delete from user");
        return ps;
    }

    public int getCount() throws SQLException {
        //Connection c = dataSource.getConnection();
        //사용 불가
        Connection c = null;

        PreparedStatement ps = c.prepareStatement("select count(*) from user");
        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        rs.close();
        ps.close();
        c.close();
        return count;
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement(
                "SELECT * FROM user where id = ?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        rs.next();
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));

        rs.close();
        ps.close();
        c.close();

        return user;
    }

}
