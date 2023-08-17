package com.example.demo_redis_cache.repository;

import com.example.demo_redis_cache.constant.Constant;
import com.example.demo_redis_cache.entity.UserEntity;
import com.example.demo_redis_cache.vo.UserVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.HashOperations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

    @InjectMocks
    private UserRepository userRepository;

    @Mock
    private HashOperations<String, String, Object> hashOperations;

    private static final String ID = "123";

    @Test
    void testSaveUser() {
        doNothing().when(hashOperations).put(Mockito.any(), Mockito.any(), Mockito.any());
        userRepository.saveUser(mockUserEntity());
        verify(hashOperations, times(1)).put(Constant.CACHE_KEY, "123", mockUserEntity());

    }

    @Test
    void testSaveUser_Fail() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(null);

        final String exception = "Error";

        Mockito.doThrow(new RuntimeException(exception)).when(hashOperations).put(any(),any(),any());
        userRepository.saveUser(userEntity);

        Assertions.assertEquals("Error",exception);
    }


    @Test
    void testFindUserById() {
        Mockito.when(hashOperations.get(Constant.CACHE_KEY,ID)).thenReturn(mockUserEntity());
        UserEntity result = userRepository.findUserById(ID);
        Assertions.assertEquals("Quang",result.getLastName());

    }

    @Test
    void testFindAllUsers() {
        Map<String, Object> userEntities = new HashMap<>();
        userEntities.put("123",mockUserEntity());
        userEntities.put("456",mockUserEntity2());
        Mockito.when(hashOperations.entries(Constant.CACHE_KEY)).thenReturn(userEntities);
        List<UserEntity> result = userRepository.findAllUsers();
        Assertions.assertEquals(2,result.size());
        Assertions.assertEquals("A",result.get(1).getFirstName());

    }

    @Test
    void testDeleteUser_True() {
        Mockito.when(hashOperations.delete(Constant.CACHE_KEY, ID)).thenReturn(1L);
        boolean result = userRepository.deleteUser(ID);
        Assertions.assertTrue(result);

    }

    @Test
    void testDeleteUser_Fail() {
        Mockito.when(hashOperations.delete(Constant.CACHE_KEY,ID)).thenReturn(0L);
        boolean result = userRepository.deleteUser(ID);
        Assertions.assertFalse(result);

    }

    @Test
    void testUpdateUser() {
        Mockito.when(hashOperations.get(Constant.CACHE_KEY, ID)).thenReturn(mockUserEntity());
        Mockito.doNothing().when(hashOperations).put(any(), any(), any());
        UserEntity result = userRepository.updateUser(mockUserVO(),"123");
        Assertions.assertEquals("Male", result.getSex());

    }

    @Test
    void testUpdateUser_Null() {
        when(hashOperations.get(Constant.CACHE_KEY, ID)).thenReturn(null);
        UserEntity result = userRepository.updateUser(mockUserVO(),"123");
        Assertions.assertNull(result);

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
