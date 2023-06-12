package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();

    @Override
    public User create(User user) {

        Objects.requireNonNull(user, "User cannot be null");
        users.put(user.getId(), user);
        return users.put(user.getId(), user);
    }

    @Override
    public User update(User user) {
        Objects.requireNonNull(user, "User cannot be null");
        users.put(user.getId(), user);
        return users.get(user.getId());
    }

    public User get(Long id) {

        Objects.requireNonNull(id, "ID cannot be null");
        if (!users.containsKey(id)) {
            throw new NotFoundException("not found");
        }
        return users.get(id);
    }

    @Override
    public void delete(Long id) {
        Objects.requireNonNull(id, "ID cannot be null");
        users.remove(id);
    }

    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }
}
