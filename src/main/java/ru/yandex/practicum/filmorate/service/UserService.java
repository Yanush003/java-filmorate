package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    private final UserDbStorage storage;

    public UserService(UserDbStorage storage) {
        this.storage = storage;
    }

    public User getUserById(Long id) {
        checkAndGetUserIsExisting(id);
        return storage.get(id);
    }

    public void addToFriendsById(Long id, Long friendId) {
        checkAndGetUserIsExisting(id);
        checkAndGetUserIsExisting(friendId);
        storage.addFriend(id, friendId);
    }

    public void deleteToFriendsById(Long id, Long friendId) {
        checkAndGetUserIsExisting(id);
        checkAndGetUserIsExisting(friendId);
        storage.deleteFriends(id, friendId);
    }

    public List<User> getSetFriends(Long id) {
        List<Long> friendsById = storage.getFriendsById(id);
        return friendsById.stream().map(storage::get).collect(Collectors.toList());
    }

    public List<User> getCommonFriends(Long id, Long otherId) {
        checkAndGetUserIsExisting(id);
        checkAndGetUserIsExisting(otherId);
        List<Long> friendsIdUser = storage.getFriendsById(id);
        List<Long> friendsIdOtherUser = storage.getFriendsById(otherId);
        Set<Long> commonFriendsId = new HashSet<>();
        for (Long friendId : friendsIdUser) {
            boolean contains = friendsIdOtherUser.contains(friendId);
            if (contains) {
                commonFriendsId.add(friendId);
            }
        }
        List<User> commonFriends = new ArrayList<>();
        for (Long friendId : commonFriendsId) {
            User user1 = storage.get(friendId);
            commonFriends.add(user1);
        }
        return commonFriends;
    }

    public User saveUser(User user) {
        Objects.requireNonNull(user, "User cannot be null");
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        user.setFriendsId(new HashSet<>());
        log.info("User Save " + user);
        return storage.create(user);
    }

    public User updateUser(User user) {
        Objects.requireNonNull(user, "User cannot be null");
        return storage.update(user);
    }

    public List<User> getListUser() {
        return storage.getAll();
    }

    public void checkAndGetUserIsExisting(Long id) {
        Objects.requireNonNull(id, "ID cannot be null");
        storage.get(id);
    }

    public Set<Long> getByFilms(Long filmId) {
        return storage.getByFilms(filmId);
    }
}
