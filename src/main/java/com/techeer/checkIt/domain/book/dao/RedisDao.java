package com.techeer.checkIt.domain.book.dao;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisDao {
  private final RedisTemplate<String, String> redisTemplate;

  public String setValues(String key, String data) {
    ValueOperations<String, String> values = redisTemplate.opsForValue();
    values.set(key, data);
    return "0";
  }

  public void setValuesList(String key, String data) {
    redisTemplate.opsForList().rightPushAll(key,data);
  }

  public void deleteValueList(String key, String data) {
    redisTemplate.opsForList().remove(key, 0, data);
  }

  public List<String> getValuesList(String key) {
    Long len = redisTemplate.opsForList().size(key);
    return len == 0 ? new ArrayList<>() : redisTemplate.opsForList().range(key, 0, len-1);
  }

  public String getValues(String key) {
    ValueOperations<String, String> values = redisTemplate.opsForValue();
    return values.get(key);
  }

}