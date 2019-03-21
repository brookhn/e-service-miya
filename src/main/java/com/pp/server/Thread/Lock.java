package com.pp.server.Thread;

public class Lock {

    private boolean isLocked = false;

    private Thread lockingThread = null;

    public synchronized void lock()
    {
        while (isLocked)
        {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        isLocked = true;
        lockingThread = Thread.currentThread();
    }

    public synchronized void unlock()
    {
        if(this.lockingThread != Thread.currentThread()) {
            throw new IllegalMonitorStateException("");
        }
        isLocked = false;
        lockingThread = null;
        notify();
    }
}
