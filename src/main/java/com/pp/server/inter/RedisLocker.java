package com.pp.server.inter;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RedisLocker implements DistributedLocker{

	private final static String LOCKER_PREFIX = "lock:";
	
	@Autowired
	RedissonConnector redissonConnector;
	
	@Override
	public <T> T lock(String resourceName, AquiredLockWorker<T> worker) {
		// TODO Auto-generated method stub
		return lock(resourceName, worker, 100);
	}

	@Override
	public <T> T lock(String resourceName, AquiredLockWorker<T> worker, int lockTime) {
		// TODO Auto-generated method stub
		RedissonClient redissonClient = redissonConnector.getClient();
		RLock lock = redissonClient.getLock(LOCKER_PREFIX+resourceName);
		try {
			boolean success = lock.tryLock(100, lockTime, TimeUnit.SECONDS);
			if (success) {
				try {
					return worker.invokeAfterLockAcquire();
				}finally {
					lock.unlock();
				}
						
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
