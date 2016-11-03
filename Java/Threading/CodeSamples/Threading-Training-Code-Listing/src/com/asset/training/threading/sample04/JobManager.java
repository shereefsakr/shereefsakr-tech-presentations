package com.asset.training.threading.sample04;


import com.asset.training.threading.common.DBConnPoolManager;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.logging.log4j.LogManager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sherif Saker
 */
public class JobManager {
    
    private static org.apache.logging.log4j.Logger logger = LogManager.getLogger(JobManager.class);
    
    private static JobManager instance = null ;

    /**
     * @return the urlQueue
     */
    public ArrayBlockingQueue<String> getUrlQueue() {
        return urlQueue;
    }

    /**
     * @param aMessagesArchiverQueue the urlQueue to set
     */
    public void setUrlQueue(ArrayBlockingQueue<String> aMessagesArchiverQueue) {
        urlQueue = aMessagesArchiverQueue;
    }
    
    private DBConnPoolManager dbConnPoolManager = null ;
    private boolean producerRunningFlag = false ;
    private boolean consumerRunningFlag = false ;
    private Thread producerThread  = null ;
    private ExecutorService consumerExecService = null ;
    
    private ArrayBlockingQueue<String> urlQueue = null ;
    
    public static JobManager getInstance () {
        if ( instance == null ) {
            instance = new JobManager() ;
        }
        
        return ( instance ) ;
    }

    /**
     * @return the dbConnPoolManager
     */
    public DBConnPoolManager getDbConnPoolManager() {
        return dbConnPoolManager;
    }

    /**
     * @param aDbConnPoolManager the dbConnPoolManager to set
     */
    public void setDbConnPoolManager(DBConnPoolManager aDbConnPoolManager) {
        dbConnPoolManager = aDbConnPoolManager;
    }
    
    private JobManager () {
    }
    
    public void init () {
        this.setDbConnPoolManager(new DBConnPoolManager ( "jdbc:oracle:thin:@localhost:1521:xe" , "THREADING_SESSION" , "t")) ;
        this.setProducerRunningFlag(true) ;
        this.setConsumerRunningFlag(true) ;
        this.setUrlQueue( new ArrayBlockingQueue<String>(500) );
        
        this.producerThread = new Thread ( new UrlProducorThread() ) ;
        
        this.producerThread.start () ;
        
        consumerExecService = Executors.newFixedThreadPool(1) ;
        
        for ( int i = 0 ; i < 1 ; i++ ) {
            consumerExecService.submit( new Thread (new UrlConsumerThread () ) ) ;
        }
    }
    
    public void shutdown () {
        try {
            this.setProducerRunningFlag(false) ;
            this.setConsumerRunningFlag(false) ;
            
            this.producerThread.join();
            
            this.getConsumerThreadPool().shutdown();
            this.getConsumerThreadPool().awaitTermination(20000, TimeUnit.MILLISECONDS) ;
        } catch (InterruptedException ex) {
            logger.error(ex.getMessage() , ex );
        }
    }

    /**
     * @return the producerRunningFlag
     */
    public boolean isProducerRunningFlag() {
        return producerRunningFlag;
    }

    /**
     * @param producerRunningFlag the producerRunningFlag to set
     */
    public void setProducerRunningFlag(boolean producerRunningFlag) {
        this.producerRunningFlag = producerRunningFlag;
    }

    /**
     * @return the consumerRunningFlag
     */
    public boolean isConsumerRunningFlag() {
        return consumerRunningFlag;
    }

    /**
     * @param consumerRunningFlag the consumerRunningFlag to set
     */
    public void setConsumerRunningFlag(boolean consumerRunningFlag) {
        this.consumerRunningFlag = consumerRunningFlag;
    }

    /**
     * @return the producerThread
     */
    public Thread getProducerThread() {
        return producerThread;
    }

    /**
     * @param producerThread the producerThread to set
     */
    public void setProducerThread(Thread producerThread) {
        this.producerThread = producerThread;
    }

    /**
     * @return the consumerThreadPool
     */
    public ExecutorService getConsumerThreadPool() {
        return consumerExecService;
    }

    /**
     * @param consumerThreadPool the consumerThreadPool to set
     */
    public void setConsumerThreadPool(ExecutorService consumerThreadPool) {
        this.consumerExecService = consumerThreadPool;
    }
}