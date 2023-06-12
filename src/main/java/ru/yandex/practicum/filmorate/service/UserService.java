package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserDbStorage storage;

    private final Logger log = LoggerFactory.getLogger(FilmService.class);


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
        List<User> users = friendsById.stream().map(storage::get).collect(Collectors.toList());
        return users;

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
        User user1 = storage.create(user);
        log.info("User Save " + user);
        return user1;
    }

    public User updateUser(User user) {
        Objects.requireNonNull(user, "User cannot be null");

        return storage.update(user);
    }

    public List<User> getListUser() {
        return storage.getAll();
    }

    private void findAndSaveFriend(Long id, Long friendId) {
        User user = storage.get(id);
        Set<Long> userFriends = user.getFriendsId();
        userFriends = Objects.requireNonNullElseGet(userFriends, HashSet::new);
        userFriends.add(friendId);
        storage.update(user);
    }

    public User checkAndGetUserIsExisting(Long id) {
        Objects.requireNonNull(id, "ID cannot be null");
        return storage.get(id);
    }

    public Set<Long> getByFilms(Long filmId) {
        return storage.getByFilms(filmId);
    }
}
