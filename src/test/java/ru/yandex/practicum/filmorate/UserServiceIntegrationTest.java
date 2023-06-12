package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.time.LocalDate;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserServiceIntegrationTest {
    private final UserService userService;


    @Test
    void testGetUserById() {
        // Создаем пользователя
        User user = new User();
        user.setName("John");
        user.setLogin("john123");
        user.setEmail("john123@gmail.com");
        user.setBirthday(LocalDate.of(1990, 1, 1));
        User savedUser = userService.saveUser(user);

        // Получаем пользователя по ID
        User retrievedUser = userService.getUserById(savedUser.getId());

        // Проверяем, что полученный пользователь соответствует ожидаемым значениям
        assertThat(retrievedUser).isNotNull();
        assertThat(retrievedUser.getId()).isEqualTo(savedUser.getId());
        assertThat(retrievedUser.getName()).isEqualTo("John");
        assertThat(retrievedUser.getLogin()).isEqualTo("john123");
        assertThat(retrievedUser.getEmail()).isEqualTo("john123@gmail.com");
        assertThat(retrievedUser.getBirthday()).isEqualTo(LocalDate.of(1990, 1, 1));
    }

    @Test
    void testAddToFriendsById() {
        // Создаем двух пользователей
        User user1 = new User();
        user1.setName("John");
        user1.setLogin("john123");
        user1.setEmail("john123@gmail.com");
        user1.setBirthday(LocalDate.of(1990, 1, 1));
        User savedUser1 = userService.saveUser(user1);

        User user2 = new User();
        user2.setName("Mike");
        user2.setLogin("mike123");
        user2.setEmail("mike123@gmail.com");
        user2.setBirthday(LocalDate.of(1990, 2, 2));
        User savedUser2 = userService.saveUser(user2);

        // Добавляем друга
        userService.addToFriendsById(savedUser1.getId(), savedUser2.getId());

        // Получаем пользователей и проверяем, что друг добавлен
        User result1 = userService.getUserById(savedUser1.getId());
        User result2 = userService.getUserById(savedUser2.getId());

        assertThat(result1.getFriendsId()).contains(savedUser2.getId());
        assertThat(result2.getFriendsId()).contains(savedUser1.getId());
    }

    @Test
    void testDeleteFriendById() {
// Создаем двух пользователей
        User user1 = new User();
        user1.setName("John");
        user1.setEmail("john123@gmail.com");
        user1.setBirthday(LocalDate.of(1990, 1, 1));
        User savedUser1 = userService.saveUser(user1);


        User user2 = new User();
        user2.setName("Mike");
        user2.setLogin("mike123");
        user2.setEmail("mike123@gmail.com");
        user2.setBirthday(LocalDate.of(1990, 2, 2));
        User savedUser2 = userService.saveUser(user2);

// Добавляем друга
        if (!user1.getFriendsId().contains(savedUser2.getId())) {
            userService.addToFriendsById(savedUser1.getId(), savedUser2.getId());
        }

// Удаляем друга
        userService.deleteToFriendsById(savedUser1.getId(), savedUser2.getId());

// Получаем пользователей и проверяем, что друг удален
        User result1 = userService.getUserById(savedUser1.getId());
      

        assertThat(result1.getFriendsId()).doesNotContain(savedUser2.getId());

    }

    @Test
    void testGetSetFriends() {
        // Создаем двух пользователей
        User user1 = new User();
        user1.setName("John");
        user1.setLogin("john123");
        user1.setEmail("john123@gmail.com");
        user1.setBirthday(LocalDate.of(1990, 1, 1));
        User savedUser1 = userService.saveUser(user1);

        User user2 = new User();
        user2.setName("Mike");
        user2.setLogin("mike123");
        user2.setEmail("mike123@gmail.com");
        user2.setBirthday(LocalDate.of(1990, 2, 2));
        User savedUser2 = userService.saveUser(user2);

        // Добавляем друга
        userService.addToFriendsById(savedUser1.getId(), savedUser2.getId());

        // Получаем список друзей и проверяем, что список содержит добавленного друга
        List<User> friends = userService.getSetFriends(savedUser1.getId());

        assertThat(friends).hasSize(1);
        assertThat(friends.get(0).getId()).isEqualTo(savedUser2.getId());
    }

    @Test
    void testGetCommonFriends() {
        // Создаем три пользователя
        User user1 = new User();
        user1.setName("John");
        user1.setLogin("john123");
        user1.setEmail("john123@gmail.com");
        user1.setBirthday(LocalDate.of(1990, 1, 1));
        User savedUser1 = userService.saveUser(user1);

        User user2 = new User();
        user2.setName("Mike");
        user2.setLogin("mike123");
        user2.setEmail("mike123@gmail.com");
        user2.setBirthday(LocalDate.of(1990, 2, 2));
        User savedUser2 = userService.saveUser(user2);

        User user3 = new User();
        user3.setName("Tom");
        user3.setLogin("tom123");
        user3.setEmail("tom123@gmail.com");
        user3.setBirthday(LocalDate.of(1990, 3, 3));
        User savedUser3 = userService.saveUser(user3);

        // Добавляем друзей
        userService.addToFriendsById(savedUser1.getId(), savedUser2.getId());
        userService.addToFriendsById(savedUser1.getId(), savedUser3.getId());
        userService.addToFriendsById(savedUser2.getId(), savedUser3.getId());

        // Получаем список общих друзей и проверяем, что список содержит только одного друга
        List<User> commonFriends = userService.getCommonFriends(savedUser1.getId(), savedUser2.getId());

        assertThat(commonFriends).hasSize(1);
        assertThat(commonFriends.get(0).getId()).isEqualTo(savedUser3.getId());
    }

    @Test
    void testSaveUser() {
        // Создаем пользователя
        User user = new User();
        user.setName("John");
        user.setLogin("john123");
        user.setEmail("john123@gmail.com");
        user.setBirthday(LocalDate.of(1990, 1, 1));

        // Сохраняем пользователя
        User savedUser = userService.saveUser(user);

        // Получаем пользователя и проверяем, что он соответствует ожидаемым значениям
        User retrievedUser = userService.getUserById(savedUser.getId());

        assertThat(retrievedUser).isNotNull();
        assertThat(retrievedUser.getId()).isEqualTo(savedUser.getId());
        assertThat(retrievedUser.getName()).isEqualTo("John");
        assertThat(retrievedUser.getLogin()).isEqualTo("john123");
        assertThat(retrievedUser.getEmail()).isEqualTo("john123@gmail.com");
        assertThat(retrievedUser.getBirthday()).isEqualTo(LocalDate.of(1990, 1, 1));
    }

    @Test
    void testUpdateUser() {
        // Создаем пользователя
        User user = new User();
        user.setName("John");
        user.setLogin("john123");
        user.setEmail("john123@gmail.com");
        user.setBirthday(LocalDate.of(1990, 1, 1));
        User savedUser = userService.saveUser(user);

        // Обновляем пользователя
        User updatedUser = new User();
        updatedUser.setId(savedUser.getId());
        updatedUser.setName("John Smith");
        updatedUser.setLogin("johnsmith123");
        updatedUser.setEmail("johnsmith123@gmail.com");
        updatedUser.setBirthday(LocalDate.of(1990, 2, 2));
        userService.updateUser(updatedUser);

        // Получаем пользователя и проверяем, что он соответствует ожидаемым значениям
        User retrievedUser = userService.getUserById(savedUser.getId());

        assertThat(retrievedUser).isNotNull();
        assertThat(retrievedUser.getId()).isEqualTo(savedUser.getId());
        assertThat(retrievedUser.getName()).isEqualTo("John Smith");
        assertThat(retrievedUser.getLogin()).isEqualTo("johnsmith123");
        assertThat(retrievedUser.getEmail()).isEqualTo("johnsmith123@gmail.com");
        assertThat(retrievedUser.getBirthday()).isEqualTo(LocalDate.of(1990, 2, 2));
    }
}