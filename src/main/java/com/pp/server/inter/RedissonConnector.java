package com.pp.server.inter;

import javax.annotation.PostConstruct;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.stereotype.Component;

@Component
public class RedissonConnector {
	
	RedissonClient redissonClient;

	@PostConstruct
	public void init() {
		Config config = new Config();
        config.useSingleServer().setAddress("10.200.11.156:6379");
        config.useSingleServer().setDatabase(3);
		redissonClient = Redisson.create(config);
	}
	
	public RedissonClient getClient() {
		return redissonClient;
	}
}
