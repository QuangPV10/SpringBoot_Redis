package com.example.demo_redis_cache.controller;

import com.example.demo_redis_cache.entity.UserEntity;
import com.example.demo_redis_cache.service.UserService;
import com.example.demo_redis_cache.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add-user")
    public ResponseEntity<UserEntity> addUser(@RequestBody  UserVO userVO) {
        return ResponseEntity.ok(userService.addUser(userVO));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id){
        boolean result = userService.deleteUser(id);
        return result ? ResponseEntity.ok("User deleted Successfully!!") : ResponseEntity.badRequest().body("Bad Request");
    }

    @PutMapping("user/{id}")
    public ResponseEntity<UserEntity> updateUser(@RequestBody UserVO userVO, @PathVariable String id) {
        return ResponseEntity.ok(userService.updateUser(userVO,id));

    }

}
