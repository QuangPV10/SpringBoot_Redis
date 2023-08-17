package com.example.demo_redis_cache.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;


@ExtendWith(MockitoExtension.class)
class RedisConfigTest {

    @InjectMocks
    private RedisConfig redisConfig;

    private static final String HOST_NAME = "127.0.0.1";
    private static final int PORT = 6379;

    @Test
    void testRedisTemplate() {
        RedisTemplate<String, Object> redisTemplate = redisConfig.redisTemplate();
        Assertions.assertNotNull(redisTemplate);

        JedisConnectionFactory connectionFactoryMock = Mockito.mock(JedisConnectionFactory.class);
        Mockito.when(connectionFactoryMock.getHostName()).thenReturn(HOST_NAME);
        Mockito.when(connectionFactoryMock.getPort()).thenReturn(PORT);

        Assertions.assertEquals(HOST_NAME,connectionFactoryMock.getHostName());
        Assertions.assertEquals(PORT,connectionFactoryMock.getPort());
    }

}
