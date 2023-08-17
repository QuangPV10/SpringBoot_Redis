package com.example.demo_redis_cache.service.impl;

import com.example.demo_redis_cache.entity.UserEntity;
import com.example.demo_redis_cache.repository.UserRepository;
import com.example.demo_redis_cache.vo.UserVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserServiceImpl userService;

    private static final String ID = "123";

    @Test
    void testAddUser() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);

        UserEntity user = userService.addUser(mockUserVO());
        Assertions.assertEquals("Quang", user.getLastName());
    }

    @Test
    void testFindUserById() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);

        Mockito.when(userRepository.findUserById(ID)).thenReturn(mockUserEntity2());
        UserEntity user = userService.findUserById(ID);

        Assertions.assertEquals("Female", user.getSex());

    }

    @Test
    void testFindUserById_Null() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);

        Mockito.when(userRepository.findUserById(ID)).thenReturn(null);
        UserEntity user = userService.findUserById(ID);

        Assertions.assertNull(user);

    }

    @Test
    void testFindAllUsers() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);

        List<UserEntity> userEntities = new ArrayList<>();
        userEntities.add(mockUserEntity());
        userEntities.add(mockUserEntity2());

        Mockito.when(userRepository.findAllUsers()).thenReturn(userEntities);
        List<UserEntity> user = userService.findAllUsers();

        Assertions.assertEquals(2, user.size());

    }

    @Test
    void testDeleteUser() {
        userService = new UserServiceImpl(userRepository);
        Mockito.when(userService.deleteUser(ID)).thenReturn(true);
       boolean result = userService.deleteUser(ID);
        Assertions.assertTrue(result);
    }

    @Test
    void testDeleteUser_Fail() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);

        Mockito.when(userRepository.deleteUser(ID)).thenReturn(false);

        boolean result = userService.deleteUser(ID);
        Assertions.assertFalse(result);
    }

    @Test
    void testUpdateUser() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);

        Mockito.when(userRepository.updateUser(mockUserVO(),ID)).thenReturn(mockUserEntity2());

        UserEntity user = userService.updateUser(mockUserVO(),ID);
        Assertions.assertEquals("B", user.getLastName());

    }

    @Test
    void testUpdateUser_Null() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);

        Mockito.when(userRepository.updateUser(mockUserVO(),ID)).thenReturn(null);

        UserEntity user = userService.updateUser(mockUserVO(),ID);
        Assertions.assertNull(user);

    }

    private UserVO mockUserVO() {
        UserVO userVO = new UserVO();
        userVO.setAge(22);
        userVO.setSex("Male");
        userVO.setFirstName("Van");
        userVO.setLastName("Quang");

        return userVO;
    }

    private UserEntity mockUserEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(ID);
        userEntity.setAge(22);
        userEntity.setSex("Male");
        userEntity.setFirstName("Van");
        userEntity.setLastName("Quang");

        return userEntity;
    }

    private UserEntity mockUserEntity2() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId("456");
        userEntity.setAge(25);
        userEntity.setSex("Female");
        userEntity.setFirstName("A");
        userEntity.setLastName("B");

        return userEntity;
    }
}
