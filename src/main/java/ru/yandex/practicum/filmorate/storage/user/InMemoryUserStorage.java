package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NoSuchCustomerException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Integer, User> users = new HashMap<>();

    public void addToFriendsById(Integer id, Long friendId) {
        findUserInMap(id);
        findUserInMap(friendId.intValue());
        findAndSaveFriend(id, friendId);
        findAndSaveFriend(friendId.intValue(), id.longValue());
    }

    public void deleteToFriendsById(Integer id, Long friendId) {
        findUserInMap(id);
        findUserInMap(friendId.intValue());
        User user = users.get(id);
        Set<Long> friendsId = user.getFriendsId();
        friendsId.remove(friendId);
        user.setFriendsId(friendsId);
        users.put(id, user);
    }

    public List<User> getFriends(Integer id) {
        findUserInMap(id);
        User user = users.get(id);
        List<Integer> friendsId = user.getFriendsId().stream().map(Long::intValue).collect(Collectors.toList());
        return friendsId.stream().map(users::get).collect(Collectors.toList());
    }

    public List<User> getCommonFriends(Integer id, Long overId) {
        findUserInMap(id);
        findUserInMap(overId.intValue());
        User user1 = users.get(id);
        User user2 = users.get(overId.intValue());
        Set<Long> friendsId = user1.getFriendsId();
        Set<Long> friendsId1 = user2.getFriendsId();
        friendsId.retainAll(friendsId1);
        return friendsId.stream().map(Long::intValue).map(users::get).collect(Collectors.toList());
    }

    public void saveUser(User user) {
        users.put(user.getId(), user);
    }

    public User updateUser(User user) {
        users.put(user.getId(), user);
        return user;
    }

    public User getUser(Integer id) {
        return users.get(id);
    }

    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    public void findUserInMap(Integer id) {
        if (!users.containsKey(id)) throw new NoSuchCustomerException(String.format("Указанный id %s не найден", id));
        users.get(id);
    }

    private void findAndSaveFriend(Integer id, Long friendId) {
        User user1 = users.get(id);
        Set<Long> user1Friends = user1.getFriendsId();
        if (Objects.isNull(user1Friends) || user1Friends.isEmpty()) {
            user1Friends = new HashSet<>();
        }
        user1Friends.add(friendId);
        user1.setFriendsId(user1Friends);
        users.put(id, user1);
    }
}
