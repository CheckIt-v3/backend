package com.techeer.checkIt.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@RequiredArgsConstructor
@Configuration
@EnableRedisRepositories
public class RedisRepositoryConfig {

    // lettuce
    // RedisConnectionFactory 인터페이스를 통해 LettuceConnectionFactory를 생성하여 반환한다.
    // RedisProperties로 yaml에 저장한 host, post를 가지고 와서 연결한다.

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisClusterConfiguration clusterConfig = new RedisClusterConfiguration();
        clusterConfig.addClusterNode(new RedisNode("redis-master-1", 7001));
        clusterConfig.addClusterNode(new RedisNode("redis-master-2", 7002));
        clusterConfig.addClusterNode(new RedisNode("redis-master-3", 7003));
        clusterConfig.addClusterNode(new RedisNode("redis-slave-1", 7101));
        clusterConfig.addClusterNode(new RedisNode("redis-slave-2", 7102));
        clusterConfig.addClusterNode(new RedisNode("redis-slave-3", 7103));
//        clusterConfig.addClusterNode(new RedisNode("127.0.0.1", 7001));
//        clusterConfig.addClusterNode(new RedisNode("127.0.0.1", 7002));
//        clusterConfig.addClusterNode(new RedisNode("127.0.0.1", 7003));
//        clusterConfig.addClusterNode(new RedisNode("127.0.0.1", 7101));
//        clusterConfig.addClusterNode(new RedisNode("127.0.0.1", 7102));
//        clusterConfig.addClusterNode(new RedisNode("127.0.0.1", 7103));

        return new LettuceConnectionFactory(clusterConfig);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }

    @Bean
    public RedisTemplate<String, String> redisLikeTemplate() {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }
}
