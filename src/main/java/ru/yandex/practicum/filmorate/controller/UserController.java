package ru.yandex.practicum.filmorate.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public Set<Integer> addToFriendsById(@PathVariable Integer id, @PathVariable Integer friendId) {
        return userService.addToFriendsById(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public Set<Integer> deleteFriendsById(@PathVariable Integer id, @PathVariable Integer friendId) {
        return userService.deleteToFriendsById(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public Set<User> getSetFriends(@PathVariable Integer id) {
        return userService.getSetFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Set<Integer> getSetCommonFriends(@PathVariable Integer id, @PathVariable Integer otherId) {
        return userService.getSetCommonFriends(id, otherId);
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        if (user.getName() == null || user.getName().equals("")) {
            user.setName(user.getLogin());
        }
        return userService.saveUser(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        return userService.updateUser(user);
    }

    @GetMapping
    public List<User> getListUser() {
        return userService.getListUser();
    }
}
