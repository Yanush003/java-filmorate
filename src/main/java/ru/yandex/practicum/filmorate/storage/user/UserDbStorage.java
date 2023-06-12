package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.NotUpdateException;
import ru.yandex.practicum.filmorate.mapper.UserRowMapper;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Repository
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }

    @Override
    public User create(User user) {
        String sqlQuery = "insert into USERS (NAME, LOGIN, EMAIL, BIRTHDAY) values (?, ?, ?, ?)";
        jdbcTemplate.update(sqlQuery, user.getName(), user.getLogin(), user.getEmail(), user.getBirthday());
        String sqlSelectUser = "select * from USERS WHERE NAME = ? and LOGIN = ?";

        User user1 = jdbcTemplate.queryForObject(sqlSelectUser, new Object[]{user.getName(), user.getLogin()}, new UserRowMapper());
        return user1;
    }

    @Override
    public User update(User user) {
        get(user.getId());
        String sqlQuery = "update USERS set NAME = ?, LOGIN = ?, EMAIL = ?, BIRTHDAY = ? where ID = ?";
        try {
            jdbcTemplate.update(sqlQuery, user.getName(), user.getLogin(), user.getEmail(), user.getBirthday(), user.getId());
        } catch (Exception e) {
            throw new NotUpdateException("User can not update");
        }
        String sqlSelectUser = "select * from USERS WHERE ID = ?";
        User user1;

        user1 = jdbcTemplate.queryForObject(sqlSelectUser, new Object[]{user.getId()}, new UserRowMapper());

        return user1;
    }

    @Override
    public User get(Long id) {
        String sqlQuery = "select * from USERS where ID = ?";

        User user;
        try {
            user = jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new UserRowMapper());
        } catch (Exception e) {
            throw new NotFoundException("ID cannot be found");
        }
        return user;
    }


    @Override
    public void delete(Long id) {
        String sqlQuery = "delete from USERS where ID = ?";
        jdbcTemplate.update(sqlQuery, id);
    }

    @Override
    public List<User> getAll() {
        String sqlQuery = "select * from USERS";
        return jdbcTemplate.query(sqlQuery, new UserRowMapper());
    }

    public void addFriend(Long id, Long friendId) {
        String sqlQuery = "insert into FRIENDS (USER_ID, FRIEND_ID, FRIENDSHIP) values (?, ?, ?)";
        jdbcTemplate.update(sqlQuery, id, friendId, true);
    }

    public void deleteFriends(Long id, Long friendId) {
        String sqlQuery = "update FRIENDS set FRIENDSHIP = ? where USER_ID = ? and FRIEND_ID = ?";
        jdbcTemplate.update(sqlQuery, false, id, friendId);
    }

    public List<Long> getFriendsById(Long id) {
        String sqlQuery = "select FRIEND_ID from FRIENDS WHERE USER_ID = ? AND FRIENDSHIP = TRUE";
        List<Long> friends = jdbcTemplate.queryForList(sqlQuery, Long.class, id);
        return friends;
    }

    public Set<Long> getByFilms(Long filmId) {
        String sqlQuery = "select l.user_id from LIKES as l where l.film_id =?";
        return new HashSet<>(jdbcTemplate.queryForList(sqlQuery, Long.class, filmId));
    }
}
