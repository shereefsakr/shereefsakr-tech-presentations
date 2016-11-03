/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.training.threading.sample01;

import java.util.ArrayList;

/**
 *
 * @author Sherif Saker
 */
public class Sample01Main {

    public static void main(String[] args) {

        ArrayList<Thread> threadList = new ArrayList<Thread>();

        for (int i = 0; i < 1000; i++) {
            final int currentThreadNum = i;

            Thread currThread = new Thread(new Runnable() {

                private int mySeq = currentThreadNum;

                @Override
                public void run() {
                    try {
                        System.out.println("[" + Thread.currentThread().getName() + "] " + mySeq);
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        System.out.println("[" + Thread.currentThread().getName() + "] " + ex.getMessage());
                        ex.printStackTrace();
                    }
                }
            });

            threadList.add(currThread);

            //currThread.setName("MyThread : " + currentThreadNum);

            // Wrong, it executes run function sequentially
            //currThread.run();
            // Correct, justs initiates the threads and continues in the loop.
            currThread.start();
        }
        
        // Test with or without this block
        // Begin : Join all threads before main ends
        //*
        for (Thread thread : threadList) {
            try {
                thread.join();
            } catch (InterruptedException ex) {
                System.out.println("[" + Thread.currentThread().getName() + "] " + ex.getMessage());
                ex.printStackTrace();
            }
        }
        //*/
        // End : Join all threads before main ends
    }
}
