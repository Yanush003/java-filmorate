package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDbStorage userDbStorage;

    @Test
    public void testSave() {
        User user = new User();
        user.setName("John Doe");
        user.setLogin("johndoe");
        user.setEmail("johndoe@example.com");
        user.setBirthday(LocalDate.of(1990, 1, 1));
        User savedUser = userService.saveUser(user);
        assertNotNull(savedUser.getId());
        assertEquals(user.getName(), savedUser.getName());
        assertEquals(user.getLogin(), savedUser.getLogin());
        assertEquals(user.getEmail(), savedUser.getEmail());
        assertEquals(user.getBirthday(), savedUser.getBirthday());
    }

    @Test
    public void testGetUserById() {
        User user = new User();
        user.setName("John Doe1");
        user.setLogin("johndoe1");
        user.setEmail("johndoe@example1.com");
        user.setBirthday(LocalDate.of(1990, 1, 1));
        User savedUser = userDbStorage.create(user);
        User retrievedUser = userService.getUserById(savedUser.getId());
        assertEquals(savedUser, retrievedUser);
    }

    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setName("John Doe2");
        user.setLogin("johndoe2");
        user.setEmail("johndoe@example2.com");
        user.setBirthday(LocalDate.of(1990, 1, 1));
        User savedUser = userDbStorage.create(user);
        savedUser.setName("Jane Doe3");
        User updatedUser = userService.updateUser(savedUser);
        assertEquals(savedUser, updatedUser);
    }

    @Test
    public void testAddToFriendsById() {
        User user1 = new User();
        user1.setName("John Doe4");
        user1.setLogin("johndoe4");
        user1.setEmail("johndoe@example4.com");
        user1.setBirthday(LocalDate.of(1990, 1, 1));
        User savedUser1 = userDbStorage.create(user1);

        User user2 = new User();
        user2.setName("Jane Doe5");
        user2.setLogin("janedoe5");
        user2.setEmail("janedoe@example5.com");
        user2.setBirthday(LocalDate.of(1990, 1, 1));
        User savedUser2 = userDbStorage.create(user2);

        userService.addToFriendsById(savedUser1.getId(), savedUser2.getId());

        List<User> friends = userService.getSetFriends(savedUser1.getId());
        assertEquals(1, friends.size());
        assertEquals(savedUser2, friends.get(0));
    }

    @Test
    public void testDeleteToFriendsById() {
        User user1 = new User();
        user1.setName("John Doe6");
        user1.setLogin("johndoe6");
        user1.setEmail("johndoe@example6.com");
        user1.setBirthday(LocalDate.of(1990, 1, 1));
        User savedUser1 = userDbStorage.create(user1);

        User user2 = new User();
        user2.setName("Jane Doe7");
        user2.setLogin("janedoe7");
        user2.setEmail("janedoe@example7.com");
        user2.setBirthday(LocalDate.of(1990, 1, 1));
        User savedUser2 = userDbStorage.create(user2);

        userService.addToFriendsById(savedUser1.getId(), savedUser2.getId());

        List<User> friends = userService.getSetFriends(savedUser1.getId());
        assertEquals(1, friends.size());
        assertEquals(savedUser2, friends.get(0));

        userService.deleteToFriendsById(savedUser1.getId(), savedUser2.getId());

        friends = userService.getSetFriends(savedUser1.getId());
        assertEquals(0, friends.size());
    }

    @Test
    public void testGetSetFriends() {
        User user1 = new User();
        user1.setName("John Doe8");
        user1.setLogin("johndoe8");
        user1.setEmail("johndoe@example8.com");
        user1.setBirthday(LocalDate.of(1990, 1, 1));
        User savedUser1 = userDbStorage.create(user1);

        User user2 = new User();
        user2.setName("Jane Doe9");
        user2.setLogin("janedoe9");
        user2.setEmail("janedoe@example9.com");
        user2.setBirthday(LocalDate.of(1990, 1, 1));
        User savedUser2 = userDbStorage.create(user2);

        userService.addToFriendsById(savedUser1.getId(), savedUser2.getId());

        List<User> friends = userService.getSetFriends(savedUser1.getId());
        assertEquals(1, friends.size());
        assertEquals(savedUser2, friends.get(0));
    }

    @Test
    public void testGetCommonFriends() {
        User user1 = new User();
        user1.setName("John Doe10");
        user1.setLogin("johndoe10");
        user1.setEmail("johndoe@example10.com");
        user1.setBirthday(LocalDate.of(1990, 1, 1));
        User savedUser1 = userDbStorage.create(user1);

        User user2 = new User();
        user2.setName("Jane Doe11");
        user2.setLogin("janedoe11");
        user2.setEmail("janedoe@example11.com");
        user2.setBirthday(LocalDate.of(1990, 1, 1));
        User savedUser2 = userDbStorage.create(user2);

        User user3 = new User();
        user3.setName("Bob Smith");
        user3.setLogin("bobsmith");
        user3.setEmail("bobsmith@example.com");
        user3.setBirthday(LocalDate.of(1990, 1, 1));
        User savedUser3 = userDbStorage.create(user3);

        userService.addToFriendsById(savedUser1.getId(), savedUser2.getId());
        userService.addToFriendsById(savedUser1.getId(), savedUser3.getId());
        userService.addToFriendsById(savedUser2.getId(), savedUser3.getId());

        List<User> commonFriends = userService.getCommonFriends(savedUser1.getId(), savedUser2.getId());
        assertEquals(1, commonFriends.size());
        assertEquals(savedUser3, commonFriends.get(0));
    }

    @Test
    public void testGetByFilms() {
        User user1 = new User();
        user1.setName("John Doe12");
        user1.setLogin("johndoe12");
        user1.setEmail("johndoe@example12.com");
        user1.setBirthday(LocalDate.of(1990, 1, 1));
        User savedUser1 = userDbStorage.create(user1);

        User user2 = new User();
        user2.setName("Jane Doe13");
        user2.setLogin("janedoe13");
        user2.setEmail("janedoe@example13.com");
        user2.setBirthday(LocalDate.of(1990, 1, 1));
        User savedUser2 = userDbStorage.create(user2);

        User user3 = new User();
        user3.setName("Bob Smith14");
        user3.setLogin("bobsmith14");
        user3.setEmail("bobsmith@example14.com");
        user3.setBirthday(LocalDate.of(1990, 1, 1));
        User savedUser3 = userDbStorage.create(user3);

        Set<Long> expectedUsers = new HashSet<>();
        expectedUsers.add(savedUser1.getId());
        expectedUsers.add(savedUser2.getId());

        userService.addToFriendsById(savedUser1.getId(), savedUser2.getId());
        userService.addToFriendsById(savedUser1.getId(), savedUser3.getId());

        Set<Long> users = userService.getByFilms(1L);
        assertEquals(expectedUsers, expectedUsers);
    }
}
