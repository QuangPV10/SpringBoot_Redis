package com.example.demo_redis_cache.vo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserVO {

    @NotBlank(message = "First Name is Mandatory")
    private String firstName;

    private String lastName;

    @NotNull(message = "Age is Mandatory")
    @Min(value = 18, message = "Age can not be less than 18")
    @Max(value = 50, message = "Age can not be greater than 50")
    private Integer age;

    private String sex;
}
