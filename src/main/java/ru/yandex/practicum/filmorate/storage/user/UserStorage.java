package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    User getUser(Integer id);

    void addToFriendsById(Integer id, Long friendId);

    void deleteToFriendsById(Integer id, Long friendId);

    List<User> getFriends(Integer id);

    List<User> getCommonFriends(Integer id, Long overId);
}
