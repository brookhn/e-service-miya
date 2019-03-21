package com.pp.server.Thread;

import java.util.ArrayList;
import java.util.List;

public class FairLock {
    private boolean isLocked = false;
    private Thread lockingThread = null;
    private List<QueueObject> wathingThreads = new ArrayList<>();

    public void lock() throws InterruptedException {
         QueueObject queueObject = new QueueObject();
         boolean isLockedForThisQueue = true;
         synchronized (this)
         {
             wathingThreads.add(queueObject);
         }
         while (isLockedForThisQueue)
         {
             synchronized (this)
             {
                 isLockedForThisQueue = isLocked || queueObject != wathingThreads.get(0);
                 if (!isLockedForThisQueue)
                 {
                     isLocked  = true;
                     wathingThreads.remove(queueObject);
                     lockingThread = Thread.currentThread();
                     return;
                 }
             }
             try {
                 queueObject.dowait();
             } catch (InterruptedException e) {
                 e.printStackTrace();
                 wathingThreads.remove(queueObject);
                 throw e;
             }
         }
    }

    public synchronized void unlock()
    {
        if (this.lockingThread != Thread.currentThread()) {
            throw new IllegalMonitorStateException("");
        }
        isLocked = false;
        lockingThread = null;
        if (wathingThreads.size()>0)
        {
            wathingThreads.get(0).doNotify();
        }
    }
}
