package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Set;

public interface UserStorage {
    User getUser(Integer id);

    Set<Integer> addToFriendsById(Integer id, Integer friendId);

    Set<Integer> deleteToFriendsById(Integer id, Integer friendId);

    Set<Integer> getSetFriends(Integer id);

    Set<Integer> getSetCommonFriends(Integer id, Integer overId);
}
