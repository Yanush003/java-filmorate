package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ConstraintViolationException;
import java.util.*;

@Service
public class UserService {
    private Integer countId = 1;
    private final Map<Integer, User> userMap = new HashMap<>();
    private final Logger log = LoggerFactory.getLogger(FilmService.class);

    public User saveUser(User user) {
        user.setId(countId++);
        userMap.put(user.getId(), user);
        log.info("User Save " + user);
        return user;
    }

    public User updateUser(User user) {
        if (!userMap.containsKey(user.getId())) {
            throw new ConstraintViolationException(Set.of());
        }
        User user1 = userMap.get(user.getId());
        user.setId(user1.getId());
        userMap.put(user.getId(), user);
        log.info("User update " + user);
        return user;
    }

    public List<User> getListUser() {
        log.info("Get Users " + this.userMap.values());
        return new ArrayList<>(userMap.values());
    }

    public void clearUsers() {
        this.userMap.clear();
        countId = 0;
    }
}
