package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    private Integer countId = 0;
    private final Map<Integer, User> userMap = new HashMap<>();
    private final Logger log = LoggerFactory.getLogger(FilmService.class);

    public User saveUser(User user) {
        log.info("User Save");
        user.setId(countId++);
        userMap.put(user.getId(), user);
        return user;
    }

    public User updateUser(User user) {
        log.info("User update");
        User user1 = userMap.get(user.getId());
        user.setId(user1.getId());
        userMap.put(user.getId(), user);
        return user;
    }

    public List<User> getListUser() {
        return new ArrayList<>(userMap.values());
    }

    public void clearUsers() {
        this.userMap.clear();
        countId = 0;
    }
}
