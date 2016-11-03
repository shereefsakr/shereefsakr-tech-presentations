/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.training.threading.insession2.sample01;

import java.util.ArrayList;

/**
 *
 * @author Sherif Saker
 */
public class Sample01Main {
    
    private static Object sharedVarLock = new Object () ;
    private static int sharedVar = 0 ;

    public static class MyThread implements Runnable {

        private int i_internal = 0;

        public MyThread(int i) {
            i_internal = i;
        }

        @Override
        public void run() {
            
            
            synchronized (sharedVarLock) {
                System.out.println("[" + Thread.currentThread().getName() + "] : " + "begin thread " + sharedVar);

                sharedVar++;

                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    System.out.println(ex.getMessage());
                    ex.printStackTrace();
                }

                System.out.println("[" + Thread.currentThread().getName() + "] : " + "end thread " + sharedVar);

            }
            
        }
    }

    public static void main(String[] args) {
        
        ArrayList<Thread> threads = new ArrayList<Thread> () ;

        for (int i = 0; i < 10; i++) {
            Thread currThread = new Thread(new MyThread(i) );

            currThread.start();
            //currThread.run();
            threads.add(currThread ) ;
            //System.out.println(i + "Thread has begun");
            System.out.println("Thread has begun");
        }
        
            //*
        for ( Thread currThread : threads ) {
            try {
                currThread.join();
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }
        }
                    //*/
    }
}
