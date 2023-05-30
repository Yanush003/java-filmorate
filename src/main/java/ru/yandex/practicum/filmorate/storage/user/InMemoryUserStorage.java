package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();


    public void addToFriendsById(Long id, Long friendId) {
        checkUserIsExisting(id);
        checkUserIsExisting(friendId);
        findAndSaveFriend(id, friendId);
        findAndSaveFriend(friendId, id);
    }

    public void deleteToFriendsById(Long id, Long friendId) {
        checkUserIsExisting(id);
        checkUserIsExisting(friendId);
        User user = users.get(id);
        Set<Long> friendsId = user.getFriendsId();
        friendsId.remove(friendId);
        user.setFriendsId(friendsId);
        users.put(id, user);
    }

    public List<User> getFriends(Long id) {
        checkUserIsExisting(id);
        User user = users.get(id);
        return user.getFriendsId().stream()
                .map(users::get)
                .collect(Collectors.toList());
    }

    public List<User> getCommonFriends(Long id, Long overId) {
        checkUserIsExisting(id);
        checkUserIsExisting(overId);
        User user1 = users.get(id);
        User user2 = users.get(overId);
        Set<Long> friendsId = user1.getFriendsId();
        Set<Long> friendsId1 = user2.getFriendsId();
        Set<Long> friendsIdCopy = new HashSet<>(friendsId);
        Set<Long> friendsId1Copy = new HashSet<>(friendsId1);
        friendsIdCopy.retainAll(friendsId1Copy);
        return friendsIdCopy.stream()
                .map(users::get)
                .collect(Collectors.toList());
    }

    @Override
    public User update(User user) {
        User oldUser = users.get(user.getId());
        oldUser.setName(user.getName());
        oldUser.setLogin(user.getLogin());
        oldUser.setEmail(user.getEmail());
        oldUser.setBirthday(user.getBirthday());
        return oldUser;
    }

    public User get(Long id) {
        if (!users.containsKey(id)) {
            throw new NotFoundException("not found");
        }
        return users.get(id);
    }

    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    public void checkUserIsExisting(Long id) {
        if (!users.containsKey(id)) {
            throw new NotFoundException(String.format("Указанный id %s не найден", id));
        }

    }

    private void findAndSaveFriend(Long id, Long friendId) {
        User user1 = users.get(id);
        Set<Long> user1Friends = user1.getFriendsId();

        if (Objects.isNull(user1Friends)) {
            user1Friends = new HashSet<>();
            user1.setFriendsId(user1Friends);
        }
        user1Friends.add(friendId);

    }

    @Override
    public User create(User user) {
        return users.put(user.getId(), user);
    }

    @Override
    public void delete(Long id) {
        users.remove(id);
    }

}
