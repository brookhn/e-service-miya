package com.pp.server.inter;


public interface DistributedLocker {
	<T> T lock(String resourceName, AquiredLockWorker<T> worker);
	
	<T> T lock(String resourceName, AquiredLockWorker<T> worker, int lockTime);
}
