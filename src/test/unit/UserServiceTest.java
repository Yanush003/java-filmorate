package ru.yandex.practicum.filmorate.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private InMemoryUserStorage storage;

    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(storage);
    }

    @Test
    void testGetUserById() {
        User user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setLogin("testuser");
        user.setEmail("testuser@test.com");
        user.setBirthday(LocalDate.of(2000, 1, 1));
        Set<Long> friendsId = new HashSet<>();
        friendsId.add(2L);
        user.setFriendsId(friendsId);

        when(storage.get(1L)).thenReturn(user);

        User result = userService.getUserById(1L);

        assertEquals(user, result);
    }

    @Test
    void testAddToFriendsById() {
        User user1 = new User();
        user1.setId(1L);
        user1.setName("Test User 1");
        user1.setLogin("testuser1");
        user1.setEmail("testuser1@test.com");
        user1.setBirthday(LocalDate.of(2000, 1, 1));
        Set<Long> friendsId1 = new HashSet<>();
        user1.setFriendsId(friendsId1);

        User user2 = new User();
        user2.setId(2L);
        user2.setName("Test User 2");
        user2.setLogin("testuser2");
        user2.setEmail("testuser2@test.com");
        user2.setBirthday(LocalDate.of(2000, 1, 1));
        Set<Long> friendsId2 = new HashSet<>();
        user2.setFriendsId(friendsId2);

        when(storage.get(1L)).thenReturn(user1);
        when(storage.get(2L)).thenReturn(user2);

        userService.addToFriendsById(1L, 2L);

        assertTrue(user1.getFriendsId().contains(2L));
        assertTrue(user2.getFriendsId().contains(1L));
        verify(storage, times(2)).update(any(User.class));
    }

    @Test
    void testDeleteToFriendsById() {
        User user1 = new User();
        user1.setId(1L);
        user1.setName("Test User 1");
        user1.setLogin("testuser1");
        user1.setEmail("testuser1@test.com");
        user1.setBirthday(LocalDate.of(2000, 1, 1));
        Set<Long> friendsId1 = new HashSet<>();
        friendsId1.add(2L);
        user1.setFriendsId(friendsId1);

        User user2 = new User();
        user2.setId(2L);
        user2.setName("Test User 2");
        user2.setLogin("testuser2");
        user2.setEmail("testuser2@test.com");
        user2.setBirthday(LocalDate.of(2000, 1, 1));
        Set<Long> friendsId2 = new HashSet<>();
        friendsId2.add(1L);
        user2.setFriendsId(friendsId2);

        when(storage.get(1L)).thenReturn(user1);
        when(storage.get(2L)).thenReturn(user2);

        userService.deleteToFriendsById(1L, 2L);

        assertFalse(user1.getFriendsId().contains(2L));
        verify(storage, times(1)).update(any(User.class));
    }

    @Test
    void testGetSetFriends() {
        User user1 = new User();
        user1.setId(1L);
        user1.setName("Test User 1");
        user1.setLogin("testuser1");
        user1.setEmail("testuser1@test.com");
        user1.setBirthday(LocalDate.of(2000, 1, 1));
        Set<Long> friendsId1 = new HashSet<>();
        friendsId1.add(2L);
        user1.setFriendsId(friendsId1);

        User user2 = new User();
        user2.setId(2L);
        user2.setName("Test User 2");
        user2.setLogin("testuser2");
        user2.setEmail("testuser2@test.com");
        user2.setBirthday(LocalDate.of(2000, 1, 1));
        Set<Long> friendsId2 = new HashSet<>();
        friendsId2.add(1L);
        user2.setFriendsId(friendsId2);

        when(storage.get(1L)).thenReturn(user1);
        when(storage.get(2L)).thenReturn(user2);

        List<User> result = userService.getSetFriends(1L);

        assertEquals(1, result.size());
        assertEquals(user2, result.get(0));
    }

    @Test
    void testGetCommonFriends() {
        User user1 = new User();
        user1.setId(1L);
        user1.setName("Test User 1");
        user1.setLogin("testuser1");
        user1.setEmail("testuser1@test.com");
        user1.setBirthday(LocalDate.of(2000, 1, 1));
        Set<Long> friendsId1 = new HashSet<>();
        friendsId1.add(2L);
        friendsId1.add(3L);
        user1.setFriendsId(friendsId1);

        User user2 = new User();
        user2.setId(2L);
        user2.setName("Test User 2");
        user2.setLogin("testuser2");
        user2.setEmail("testuser2@test.com");
        user2.setBirthday(LocalDate.of(2000, 1, 1));
        Set<Long> friendsId2 = new HashSet<>();
        friendsId2.add(1L);
        friendsId2.add(3L);
        user2.setFriendsId(friendsId2);

        User user3 = new User();
        user3.setId(3L);
        user3.setName("Test User 3");
        user3.setLogin("testuser3");
        user3.setEmail("testuser3@test.com");
        user3.setBirthday(LocalDate.of(2000, 1, 1));
        Set<Long> friendsId3 = new HashSet<>();
        friendsId3.add(1L);
        friendsId3.add(2L);
        user3.setFriendsId(friendsId3);

        when(storage.get(1L)).thenReturn(user1);
        when(storage.get(2L)).thenReturn(user2);
        when(storage.get(3L)).thenReturn(user3);

        List<User> result = userService.getCommonFriends(1L, 2L);

        assertEquals(1, result.size());
        assertEquals(user3, result.get(0));
    }

    @Test
    void testSaveUser() {
        User user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setLogin("testuser");
        user.setEmail("testuser@test.com");
        user.setBirthday(LocalDate.of(2000, 1, 1));

        when(storage.create(user)).thenReturn(user);

        User result = userService.saveUser(user);

        assertEquals(user, result);
        verify(storage, times(1)).create(user);
    }

    @Test
    void testUpdateUser() {
        User user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setLogin("testuser");
        user.setEmail("testuser@test.com");
        user.setBirthday(LocalDate.of(2000, 1, 1));

        User oldUser = new User();
        oldUser.setId(1L);
        oldUser.setName("Old Test User");
        oldUser.setLogin("oldtestuser");
        oldUser.setEmail("oldtestuser@test.com");
        oldUser.setBirthday(LocalDate.of(1990, 1, 1));

        when(storage.get(1L)).thenReturn(oldUser);
        when(storage.update(oldUser)).thenReturn(user);

        User result = userService.updateUser(user);

        assertEquals(user, result);
    }

    @Test
    void testGetListUser() {
        User user1 = new User();
        user1.setId(1L);
        user1.setName("Test User 1");
        user1.setLogin("testuser1");
        user1.setEmail("testuser1@test.com");
        user1.setBirthday(LocalDate.of(2000, 1, 1));
        Set<Long> friendsId1 = new HashSet<>();
        friendsId1.add(2L);
        user1.setFriendsId(friendsId1);

        User user2 = new User();
        user2.setId(2L);
        user2.setName("Test User 2");
        user2.setLogin("testuser2");
        user2.setEmail("testuser2@test.com");
        user2.setBirthday(LocalDate.of(2000, 1, 1));
        Set<Long> friendsId2 = new HashSet<>();
        friendsId2.add(1L);
        user2.setFriendsId(friendsId2);

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        when(storage.getAll()).thenReturn(users);

        List<User> result = userService.getListUser();

        assertEquals(2, result.size());
        assertEquals(user1, result.get(0));
        assertEquals(user2, result.get(1));
        verify(storage, times(1)).getAll();
    }

    @Test
    void testCheckUserIsExisting() {
        User user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setLogin("testuser");
        user.setEmail("testuser@test.com");
        user.setBirthday(LocalDate.of(2000, 1, 1));

        when(storage.get(1L)).thenReturn(user);

        assertDoesNotThrow(() -> userService.checkUserIsExisting(1L));
        verify(storage, times(1)).get(1L);
    }

    @Test
    void testCheckUserIsExistingWithNullId() {
        assertThrows(NullPointerException.class, () -> userService.checkUserIsExisting(null));
        verify(storage, never()).get(anyLong());
    }
}
