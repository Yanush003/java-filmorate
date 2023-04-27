package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private User user;
    private List<User> users = new ArrayList<>();
    private final static Logger log = LoggerFactory.getLogger(UserService.class);

    public User createUser(User user) {
        log.info("User create");
        user.setId(1);
        this.user = user;
        users.add(user);
        return this.user;
    }

    public User updateUser(User user) {
        this.user.setName(user.getName());
        return this.user;
    }

    public List<User> getUserList() {
        return this.users;
    }
}
