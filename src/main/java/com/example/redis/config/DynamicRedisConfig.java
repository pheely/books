package com.example.redis.config;

import io.lettuce.core.SocketOptions;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;
import java.util.Collections;

@Configuration
public class DynamicRedisConfig {

    @Value("${spring.data.redis.cluster.nodes}")
    private String clusterNodes;

    @Bean
    @RefreshScope
    public LettuceConnectionFactory redisConnectionFactory() {
        // Essential for detecting cluster changes during failover
        ClusterTopologyRefreshOptions refreshOptions = ClusterTopologyRefreshOptions.builder()
                .enableAllAdaptiveRefreshTriggers() 
                .enablePeriodicRefresh(Duration.ofMinutes(1))
                .build();

        ClusterClientOptions clientOptions = ClusterClientOptions.builder()
                .topologyRefreshOptions(refreshOptions)
                .socketOptions(SocketOptions.builder()
                    .connectTimeout(Duration.ofSeconds(5))
                    .build())
                .build();

        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .clientOptions(clientOptions)
                .build();

        RedisClusterConfiguration clusterConfig = new RedisClusterConfiguration(
                Collections.singletonList(clusterNodes)
        );

        return new LettuceConnectionFactory(clusterConfig, clientConfig);
    }

    @Bean
    public StringRedisTemplate redisTemplate(LettuceConnectionFactory factory) {
        return new StringRedisTemplate(factory);
    }
}