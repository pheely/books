package com.example.redis.controller;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cache")
public class RedisController {

    private final StringRedisTemplate redisTemplate;

    public RedisController(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostMapping("/{key}")
    public String write(@PathVariable String key, @RequestParam String value) {
        redisTemplate.opsForValue().set(key, value);
        return "Stored: " + key;
    }

    @GetMapping("/{key}")
    public String read(@PathVariable String key) {
        return redisTemplate.opsForValue().get(key);
    }
}