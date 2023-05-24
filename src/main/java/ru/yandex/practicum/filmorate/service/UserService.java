package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.util.*;

@Service
public class UserService {
    private final Logger log = LoggerFactory.getLogger(FilmService.class);
    private Integer countId = 1;

    private final InMemoryUserStorage storage;

    public UserService(InMemoryUserStorage storage) {
        this.storage = storage;
    }

    public User getUserById(Integer id) {
        storage.findUserInMap(id);
        return storage.getUser(id);
    }

    public void addToFriendsById(Integer id, Long friendId) {
       storage.addToFriendsById(id, friendId);
    }

    public void deleteToFriendsById(Integer id, Long friendId) {
        storage.deleteToFriendsById(id, friendId);
    }

    public List<User> getSetFriends(Integer id) {
        return storage.getFriends(id);
    }

    public List<User> getCommonFriends(Integer id, Long overId) {
        return storage.getCommonFriends(id, overId);
    }

    public User saveUser(User user) {
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        if (Objects.isNull(user.getId())) {
            user.setId(countId++);
        }
        storage.saveUser(user);
        log.info("User Save " + user);
        return user;
    }

    public User updateUser(User user) {
        storage.findUserInMap(user.getId());
       return storage.updateUser(user);
    }

    public List<User> getListUser() {
        return storage.getUsers();
    }

}
