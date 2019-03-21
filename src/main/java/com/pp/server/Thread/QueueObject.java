package com.pp.server.Thread;

public class QueueObject {

    private boolean isNotified = false;

    public synchronized void dowait() throws InterruptedException {
        while (!isNotified) {
            this.wait();
        }
        this.isNotified = false;
    }

    public synchronized void doNotify()
    {
        this.isNotified = true;
        this.notify();
    }

    @Override
    public boolean equals(Object o)
    {
        return this == o;
    }
}
