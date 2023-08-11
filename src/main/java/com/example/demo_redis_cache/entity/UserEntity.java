package com.example.demo_redis_cache.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class UserEntity {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private int age;
    private String sex;

}
