package ru.yandex.practicum.catsgram.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.catsgram.model.User;

import java.util.Optional;

@Component
@Slf4j
public class UserDaoImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> findUserById(String id) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("select * from cat_user where id = ?", id);
        if (userRows.next()) {
            User user = new User(userRows.getString("id"),
                    userRows.getString("username"),
                    userRows.getString("nickname"));

            log.info("Found user: {} {}", user.getId(), user.getNickname());

            return Optional.of(user);
        } else {
            log.info("User id {} not found", id);
            return Optional.empty();
        }
    }
}
