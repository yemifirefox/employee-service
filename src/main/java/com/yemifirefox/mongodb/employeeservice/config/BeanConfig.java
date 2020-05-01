package com.yemifirefox.mongodb.employeeservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class BeanConfig {

    @Autowired
    private Environment environment;
    /*@Bean
    JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration("localhost", 6379);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(""));
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }*/

    @Bean(name = "jedisConnectionFactory")
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
        redisSentinelConfiguration.master(environment.getProperty("redis.sentinel.master.name"));
        String redisSentinelHostAndPorts = environment.getProperty("redis.sentinel.host.and.ports");
        HostAndPort hostAndPort;
        if (redisSentinelHostAndPorts.contains(";")) {
            for (String node : redisSentinelHostAndPorts.split(";")) {
                if (null != node & node.contains(":")) {
                    String [] nodeArr = node.split(":");
                    hostAndPort = new HostAndPort(nodeArr[0], Integer.parseInt(nodeArr[1]));
                    redisSentinelConfiguration.sentinel(hostAndPort.getHost(), hostAndPort.getPort());
                }
            }
        } else {
            if (redisSentinelHostAndPorts.contains(":")) {
                String [] nodeArr = redisSentinelHostAndPorts.split(":");
                hostAndPort = new HostAndPort(nodeArr[0], Integer.parseInt(nodeArr[1]));
                redisSentinelConfiguration.sentinel(hostAndPort.getHost(), hostAndPort.getPort());
            }
        }
        return new JedisConnectionFactory(redisSentinelConfiguration, poolConfig());
    }

    @Bean
    public JedisPoolConfig poolConfig() {
        final JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setMaxTotal(100);
        jedisPoolConfig.setMaxIdle(100);
        jedisPoolConfig.setMinIdle(10);
        jedisPoolConfig.setTestOnReturn(true);
        jedisPoolConfig.setTestWhileIdle(true);
        return jedisPoolConfig;
    }

    @Bean
    public RedisTemplate redisTemplate() {
        RedisTemplate template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }
}
