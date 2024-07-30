package org.example.repository;

import jakarta.annotation.PostConstruct;
import org.example.entity.User;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;


@Service
public class UserRepository {
    private Connection conn;

    @PostConstruct
    private void init() {
        try {
            String url = "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=postgres";
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User findUser(String id) {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from users where id = '" + id + "'");

            if (rs.next()) {
                User user = new User();
                user.id = rs.getString(1);
                user.login = rs.getString(2);
                user.name = rs.getString(3);
                user.lastName = rs.getString(4);
                user.creationDate = rs.getObject(5, LocalDateTime.class);

                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(User userEntity) {
        try {
            Statement st = conn.createStatement();

            st.executeUpdate(String.format("""
                            insert into users(id,
                                    login,
                                    name,
                                    last_name,
                                    creation_date)
                            values ('%s', '%s', '%s', '%s', '%s');
                            """,
                    userEntity.id, userEntity.login, userEntity.name, userEntity.lastName, userEntity.creationDate
            ));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
