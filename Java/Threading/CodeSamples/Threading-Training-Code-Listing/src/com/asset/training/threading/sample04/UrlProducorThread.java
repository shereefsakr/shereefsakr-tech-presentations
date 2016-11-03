/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.training.threading.sample04;

import com.asset.training.threading.common.BusinessException;
import com.asset.training.threading.common.DBConnPoolManager;
import java.sql.Connection;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import oracle.jdbc.OracleConnection;
import oracle.jms.AQjmsQueueConnectionFactory;
import oracle.jms.AQjmsSession;
import oracle.jms.AQjmsTextMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Sherif Saker
 */
public class UrlProducorThread implements Runnable {

    private static Logger logger = LogManager.getLogger(UrlProducorThread.class);

    @Override
    public void run() {

        Connection conn = null;
        QueueSession queueSession = null;
        QueueReceiver receiver = null;

        while (JobManager.getInstance().isProducerRunningFlag()) {
            try {
                
                logger.info( "Begin in producer loop" );
                long time = System.currentTimeMillis();
                conn = JobManager.getInstance().getDbConnPoolManager().getConnection();

                OracleConnection oracleConnection = conn.unwrap(OracleConnection.class);

                QueueConnection qconn = AQjmsQueueConnectionFactory
                        .createQueueConnection(oracleConnection);
                qconn.start();
                queueSession = qconn.createQueueSession(true, 0);
                String schemaName = qconn.getClientID();
                Queue queue = ((AQjmsSession) queueSession).getQueue(schemaName, "URLs_QUEUE");
                receiver = ((AQjmsSession) queueSession).createReceiver(queue);

                AQjmsTextMessage aqMessage = (AQjmsTextMessage) receiver.receiveNoWait();
                
                if ( aqMessage == null ) {
                    try {
                        Thread.sleep(10000);
                    } catch ( Exception ex ) {
                        logger.error(ex.getMessage(), ex);
                    }
                } else {
                    
                    try {
                        DBConnPoolManager.commit(oracleConnection);
                    } catch (BusinessException ex) {
                        logger.error(ex.getMessage(), ex);
                    }
                    JobManager.getInstance().getUrlQueue().put(aqMessage.getText());
                }
            } catch (Throwable ex) {
                logger.error(ex.getMessage(), ex);
            }
            
            logger.info( "end in producor loop" );
        }

        try {
            if (receiver != null) {
                receiver.close();
            }
        } catch (JMSException ex) {
            logger.error(ex.getMessage(), ex);
        }

        try {
            if (queueSession != null) {
                queueSession.commit();
            }
        } catch (JMSException ex) {
            logger.error(ex.getMessage(), ex);
        }

        try {
            if (queueSession != null) {
                queueSession.close();
            }
        } catch (JMSException ex) {
            logger.error(ex.getMessage(), ex);
        }

        try {
            DBConnPoolManager.commit(conn);
        } catch (BusinessException ex) {
            logger.error(ex.getMessage(), ex);
        }

        try {
            DBConnPoolManager.closeConnection(conn);
        } catch (BusinessException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}
