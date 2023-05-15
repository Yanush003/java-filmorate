package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import javax.validation.ConstraintViolationException;
import java.util.*;

@Service
public class UserService {

    private final InMemoryUserStorage storage;

    public UserService(InMemoryUserStorage storage) {
        this.storage = storage;
    }

    public User getUserById(Integer id) {
        return storage.getUser(id);
    }

    public Set<Integer> addToFriendsById(Integer id, Integer friendId) {
        return storage.addToFriendsById(id, friendId);
    }

    public Set<Integer> deleteToFriendsById(Integer id, Integer friendId) {
        return storage.deleteToFriendsById(id, friendId);
    }

    public Set<Integer> getSetFriends(Integer id) {
        return storage.getSetFriends(id);
    }

    public Set<Integer> getSetCommonFriends(Integer id, Integer overId) {
        return storage.getSetCommonFriends(id, overId);
    }

    public User saveUser(User user) {
        return storage.saveUser(user);
    }

    public User updateUser(User user) {
        return storage.updateUser(user);
    }

    public List<User> getListUser() {
        return storage.getListUser();
    }

    public void clearUsers() {
        storage.clearUsers();
    }
}
