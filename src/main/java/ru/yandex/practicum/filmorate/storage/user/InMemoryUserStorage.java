package ru.yandex.practicum.filmorate.storage.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NoSuchCustomerException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Integer, User> users = new HashMap<>();
    private final Logger log = LoggerFactory.getLogger(FilmService.class);
    private Integer countId = 1;

    public User getUser(Integer id) {
        findUserInMap(id);
        return users.get(id);
    }

    public Set<Integer> addToFriendsById(Integer id, Integer friendId) {
        findUserInMap(id);
        findUserInMap(friendId);
        User user1 = findAndSaveFriend(id, friendId);
        User user2 = findAndSaveFriend(friendId, id);
        return user2.getFriendsId();
    }

    public Set<Integer> deleteToFriendsById(Integer id, Integer friendId) {
        findUserInMap(id);
        findUserInMap(friendId);
        User user = users.get(id);
        Set<Integer> friendsId = user.getFriendsId();
        friendsId.remove(friendId);
        user.setFriendsId(friendsId);
        users.put(id, user);
        return user.getFriendsId();
    }

    public Set<User> getSetFriends(Integer id) {
        findUserInMap(id);
        User user = users.get(id);
        Set<Integer> friendsId = user.getFriendsId();
        Set<User> collect = friendsId.stream().map(users::get).collect(Collectors.toSet());
        return collect;
    }

    public Set<Integer> getSetCommonFriends(Integer id, Integer overId) {
        findUserInMap(id);
        findUserInMap(overId);
        User user1 = users.get(id);
        User user2 = users.get(overId);
        Set<Integer> friendsId = user1.getFriendsId();
        Set<Integer> friendsId1 = user2.getFriendsId();
        friendsId.retainAll(friendsId1);
        return friendsId;
    }

    public User saveUser(User user) {
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        if(Objects.isNull(user.getId())){
            user.setId(countId++);
        }
        users.put(user.getId(), user);
        log.info("User Save " + user);
        return user;
    }

    public User updateUser(User user) {
        findUserInMap(user.getId());
        users.put(user.getId(), user);
        log.info("User update " + user);
        return user;
    }

    public List<User> getListUser() {
        log.info("Get Users " + this.users.values());
        return new ArrayList<>(users.values());
    }

    public void clearUsers() {
        this.users.clear();
        countId = 0;
    }

    private void findUserInMap(Integer id) {
        if(!users.containsKey(id)) throw new NoSuchCustomerException(String.format("Указанный id %s не найден", id));
    }

    private User findAndSaveFriend(Integer id, Integer friendId) {
        User user1 = users.get(friendId);
        Set<Integer> user1Friends = user1.getFriendsId();
        if(Objects.isNull(user1Friends) || user1Friends.isEmpty()){
            user1Friends = new HashSet<>();
        }
        user1Friends.add(friendId);
        user1.setFriendsId(user1Friends);
        users.put(id, user1);
        return user1;
    }
}
