package com.example.demo_redis_cache.controller;

import com.example.demo_redis_cache.constant.Constant;
import com.example.demo_redis_cache.entity.UserEntity;
import com.example.demo_redis_cache.exception.InternalServerException;
import com.example.demo_redis_cache.exception.NotFoundException;
import com.example.demo_redis_cache.service.UserService;
import com.example.demo_redis_cache.vo.UserVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private static final String ID = "123";
    private static final String URL = "/api/v1.0";
    private static final String POST = "/add-user";
    private static final String PUT = "/user/{id}";
    private static final String GET = "/user/{id}";
    private static final String GET_ALL = "/users";
    private static final String DELETE = "/user/{id}";

    @Mock
    private UserService userService;
    private MockMvc mockMvc;
    private final ObjectMapper mapper = new ObjectMapper();

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testAddUser() throws Exception {
        Mockito.when(userService.addUser(mockUserVO())).thenReturn(mockUserEntity());

        this.mockMvc.perform(post(URL + POST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(mockUserVO()))

                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Van"))
                .andExpect(jsonPath("$.lastName").value("Quang"))
                .andExpect(jsonPath("$.sex").value("Male"))
                .andExpect(jsonPath("$.age").value(22));
    }

    @Test
    void testGetUserById() throws Exception {
        Mockito.when(userService.findUserById(ID)).thenReturn(mockUserEntity());

        this.mockMvc.perform(get(URL + GET,ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Van"))
                .andExpect(jsonPath("$.lastName").value("Quang"))
                .andExpect(jsonPath("$.sex").value("Male"))
                .andExpect(jsonPath("$.age").value(22));
    }

    @Test
    void testGetUserById_Fail() {
        Mockito.when(userService.findUserById(ID)).thenReturn(null);

        Assertions.assertThrows(NotFoundException.class, () -> userController.getUserById(ID));
    }

    @Test
    void testGetAllUser() throws Exception {
        List<UserEntity> userEntities = new ArrayList<>();
        userEntities.add(mockUserEntity());
        userEntities.add(mockUserEntity2());

        Mockito.when(userService.findAllUsers()).thenReturn(userEntities);

        this.mockMvc.perform(get(URL + GET_ALL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[1].firstName").value("A"))
                .andExpect(jsonPath("$[1].lastName").value("B"))
                .andExpect(jsonPath("$[1].sex").value("Female"))
                .andExpect(jsonPath("$[1].age").value(25));
    }

    @Test
    void testDeleteUser() throws Exception {
        Mockito.when(userService.deleteUser(ID)).thenReturn(true);

        this.mockMvc.perform(delete(URL + DELETE,ID))
                .andExpect(status().isOk())
                .andExpect(content().string(Constant.DELETE + ID + Constant.SUCCESS));
    }

    @Test
    void testDeleteUser_Fail() {
        Mockito.when(userService.deleteUser(ID)).thenReturn(false);

        Assertions.assertThrows(InternalServerException.class, () -> userController.deleteUser(ID));
    }

    @Test
    void testUpdateUser() throws Exception {
        Mockito.when(userService.updateUser(mockUserVO(),ID)).thenReturn(mockUserEntity());

        this.mockMvc.perform(put(URL + PUT,ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(mockUserVO()))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Van"))
                .andExpect(jsonPath("$.lastName").value("Quang"))
                .andExpect(jsonPath("$.sex").value("Male"))
                .andExpect(jsonPath("$.age").value(22));
    }

    @Test
    void testUpdateUser_Fail() {
        Mockito.when(userService.updateUser(mockUserVO(),ID)).thenReturn(null);

        Assertions.assertThrows(NotFoundException.class, () -> userController.updateUser(mockUserVO(),ID));
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
