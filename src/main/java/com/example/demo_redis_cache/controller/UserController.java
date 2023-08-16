package com.example.demo_redis_cache.controller;

import com.example.demo_redis_cache.constant.Constant;
import com.example.demo_redis_cache.entity.UserEntity;
import com.example.demo_redis_cache.exception.InternalServerException;
import com.example.demo_redis_cache.exception.NotFoundException;
import com.example.demo_redis_cache.service.UserService;
import com.example.demo_redis_cache.vo.UserVO;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1.0")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add-user")
    public ResponseEntity<UserEntity> addUser(@Valid @RequestBody UserVO userVO) {
        return ResponseEntity.ok(userService.addUser(userVO));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable String id) {
        UserEntity userEntity = userService.findUserById(id);
        if (Objects.isNull(userEntity)) {
            throw new NotFoundException(Constant.NOT_FOUND + id, 1L);
        }
        return ResponseEntity.ok(userEntity);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id){
        boolean result = userService.deleteUser(id);
        if (!result) {
            throw new InternalServerException(Constant.NOT_FOUND + id, 1L);
        }
        return ResponseEntity.ok(Constant.DELETE + id + Constant.SUCCESS);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<UserEntity> updateUser(@Valid @RequestBody UserVO userVO, @PathVariable String id) {
        UserEntity userEntity = userService.updateUser(userVO,id);
        if (Objects.isNull(userEntity)) {
            throw new NotFoundException(Constant.NOT_FOUND + id, 1L);
        }
        return ResponseEntity.ok(userEntity);

    }

}
