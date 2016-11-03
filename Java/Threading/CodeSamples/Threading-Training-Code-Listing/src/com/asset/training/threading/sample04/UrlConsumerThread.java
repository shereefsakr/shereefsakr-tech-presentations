/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.training.threading.sample04;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author Sherif Saker
 */
public class UrlConsumerThread implements Runnable {

    private static org.apache.logging.log4j.Logger logger = LogManager.getLogger(UrlConsumerThread.class);

    @Override
    public void run() {
        while (JobManager.getInstance().isConsumerRunningFlag()) {
            try {
                String url = JobManager.getInstance().getUrlQueue().poll(10000,
                        TimeUnit.MILLISECONDS);
                
                if ( url != null ) {
                    logger.info("Retrieved url : " + url);
                    Thread.sleep(4000);
                    logger.info("URL retrieval should be done here");
                } else {
                    logger.info ( "no url found" ) ;
                    Thread.sleep(10000);
                }
            } catch (InterruptedException ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
    }
}
