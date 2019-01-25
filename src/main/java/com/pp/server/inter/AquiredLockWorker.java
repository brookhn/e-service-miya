package com.pp.server.inter;

public interface AquiredLockWorker<T> {
   T invokeAfterLockAcquire();
}
