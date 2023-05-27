package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Integer, User> users = new HashMap<>();

    public void addToFriendsById(Integer id, Long friendId) {
        checkUserIsExisting(id);
        checkUserIsExisting(friendId.intValue());
        findAndSaveFriend(id, friendId);
        findAndSaveFriend(friendId.intValue(), id.longValue());
    }

    public void deleteToFriendsById(Integer id, Long friendId) {
        checkUserIsExisting(id);
        checkUserIsExisting(friendId.intValue());
        User user = users.get(id);
        Set<Long> friendsId = user.getFriendsId();
        friendsId.remove(friendId);
        user.setFriendsId(friendsId);
        users.put(id, user);
    }

    public List<User> getFriends(Integer id) {
        checkUserIsExisting(id);
        User user = users.get(id);
        List<User> users1 = user.getFriendsId().stream()
                .map(Long::intValue)
                .map(users::get)
                .collect(Collectors.toList());
        return users1;
    }

    public List<User> getCommonFriends(Integer id, Long overId) {
        checkUserIsExisting(id);
        checkUserIsExisting(overId.intValue());
        User user1 = users.get(id);
        User user2 = users.get(overId.intValue());
        Set<Long> friendsId = user1.getFriendsId();
        Set<Long> friendsId1 = user2.getFriendsId();
        Set<Long> friendsIdCopy = new HashSet<>(friendsId);
        Set<Long> friendsId1Copy = new HashSet<>(friendsId1);
        friendsIdCopy.retainAll(friendsId1Copy);
        return friendsIdCopy.stream()
                .map(Long::intValue)
                .map(users::get)
                .collect(Collectors.toList());
    }

    public void saveUser(User user) {
        users.put(user.getId(), user);
    }

    public User updateUser(User user) {
        User oldUser = users.get(user.getId());
        oldUser.setName(user.getName());
        oldUser.setLogin(user.getLogin());
        oldUser.setEmail(user.getEmail());
        oldUser.setBirthday(user.getBirthday());
        return user;
    }

    public User getUser(Integer id) {
        if (!users.containsKey(id)) {
            throw new NotFoundException("not found");
        }
        return users.get(id);
    }

    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    public void checkUserIsExisting(Integer id) {
        if (!users.containsKey(id)) {
            throw new NotFoundException(String.format("Указанный id %s не найден", id));
        }

    }

    private void findAndSaveFriend(Integer id, Long friendId) {
        User user1 = users.get(id);
        Set<Long> user1Friends = user1.getFriendsId();

        if (Objects.isNull(user1Friends)) { //|| user1Friends.isEmpty()
            user1Friends = new HashSet<>();
            user1.setFriendsId(user1Friends);
        }
        user1Friends.add(friendId);

        // users.put(id, user1);
    }
}
