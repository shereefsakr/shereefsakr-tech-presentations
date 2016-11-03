/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.training.threading.sample02;

import com.asset.training.threading.common.BusinessException;
import com.asset.training.threading.common.DBConnPoolManager;
import java.sql.Connection;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import oracle.jdbc.OracleConnection;
import oracle.jms.AQjmsQueueConnectionFactory;
import oracle.jms.AQjmsSession;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author Sherif Saker
 */
public class Sample02Main {

    private static org.apache.logging.log4j.Logger logger = LogManager.getLogger(Sample02Main.class);

    public static void main(String[] args) {

        QueueSession queueSession = null;
        QueueSender sender = null;
        TextMessage message = null;
        
        OracleConnection oracleConnection = null ;

        try {
            DBConnPoolManager dbConnPoolManager = new DBConnPoolManager("jdbc:oracle:thin:@localhost:1521:xe", "THREADING_SESSION", "t");

            Connection conn = dbConnPoolManager.getConnection();

            oracleConnection = conn.unwrap(OracleConnection.class);

            QueueConnection qconn = AQjmsQueueConnectionFactory
                    .createQueueConnection(oracleConnection);
            qconn.start();
            queueSession = qconn.createQueueSession(true, 0);
            String schemaName = qconn.getClientID();
            Queue queue = ((AQjmsSession) queueSession).getQueue(schemaName, "URLs_QUEUE");

            sender = queueSession.createSender(queue);

            message = queueSession.createTextMessage("URL should be put in queue");
            
            sender.send(message);
            
            try {
                DBConnPoolManager.commit(oracleConnection);
            } catch (BusinessException ex) {
                logger.error(ex.getMessage(), ex);
            }

            try {
                if (sender != null) {
                    sender.close();
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
                if (qconn != null) {
                    qconn.close();
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
                DBConnPoolManager.closeConnection(oracleConnection);
            } catch (BusinessException ex) {
                logger.error(ex.getMessage(), ex);
            }

            try {
                DBConnPoolManager.closeConnection(conn);
            } catch (BusinessException ex) {
                logger.error(ex.getMessage(), ex);
            }

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}
