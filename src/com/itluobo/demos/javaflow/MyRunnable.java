package com.itluobo.demos.javaflow;

import org.apache.commons.javaflow.Continuation;

/**
 * Created by kenvi on 16/4/28.
 */

class MyRunnable implements Runnable {
    public void run() {
        System.out.println("started!");
        for( int i=0; i<10; i++ )
            echo(i);
    }
    private void echo(int x) {
        System.out.println(x);
        Continuation.suspend();
    }
}
