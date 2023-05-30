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
    private Long countId = 1L;

    private final InMemoryUserStorage storage;

    public UserService(InMemoryUserStorage storage) {
        this.storage = storage;
    }

    public User getUserById(Long id) {
        storage.checkUserIsExisting(id);
        return storage.get(id);
    }

    public void addToFriendsById(Long id, Long friendId) {
        storage.addToFriendsById(id, friendId);
    }

    public void deleteToFriendsById(Long id, Long friendId) {
        storage.deleteToFriendsById(id, friendId);
    }

    public List<User> getSetFriends(Long id) {
        return storage.getFriends(id);
    }

    public List<User> getCommonFriends(Long id, Long overId) {
        return storage.getCommonFriends(id, overId);
    }

    public User saveUser(User user) {
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        if (Objects.isNull(user.getId())) {
            user.setId(countId++);
        }
        user.setFriendsId(new HashSet<>());
        storage.create(user);
        log.info("User Save " + user);
        return user;
    }

    public User updateUser(User user) {
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        storage.checkUserIsExisting(user.getId());
        return storage.update(user);
    }

    public List<User> getListUser() {
        return storage.getAll();
    }

}
