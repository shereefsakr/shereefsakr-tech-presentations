/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.training.threading.common;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;

/**
 *
 * @author Sherif Saker
 */
public class DBConnPoolManager {
    
    private String dbUrl = null ;
    private String username = null ;
    private String password = null ;
    private DataSource dataSource = null ;

    public DBConnPoolManager( String dbUrl , String username , String password ) {
        this.dbUrl = dbUrl ;
        this.username = username ;
        this.password = password ;
    }

    private DataSource init() {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        /*    try {*/
        //	cpds.setDriverClass("oracle.jdbc.driver.OracleDriver");
        cpds.setJdbcUrl( this.dbUrl );
        cpds.setUser(this.username);
        cpds.setPassword(this.password);
        /*   
         } catch (PropertyVetoException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
         }*/
        this.dataSource = cpds ;
        return cpds;

    }

    public Connection getConnection() throws BusinessException {
        Connection con = null;
        try {
            if (dataSource == null) {
                dataSource = init();
            }

            con = dataSource.getConnection();
            
            con.setAutoCommit(false);
            
        } catch (Exception e) {
            System.err.println(e);
            throw new BusinessException(ErrorCodes.ERROR_IN_DATABASE_POOL);
        }
        return con;
    }

    public static void closeConnection(Connection connection) throws BusinessException {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                throw new BusinessException(ErrorCodes.ERROR_IN_DATABASE_POOL);
            }
        }

    }

    public static void close(ResultSet resultSet) throws BusinessException {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new BusinessException(ErrorCodes.ERROR_IN_DATABASE_POOL);
            }
        }
    }

    public static void close(Statement statement) throws BusinessException {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new BusinessException(ErrorCodes.ERROR_IN_DATABASE_POOL);
            }
        }
    }

    public static void rollback(Connection connection) throws BusinessException {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new BusinessException(ErrorCodes.ERROR_IN_DATABASE_POOL);
            }
        }

    }

    public static void commit(Connection connection) throws BusinessException {
        if (connection != null) {
            try {
                connection.commit();
            } catch (SQLException e) {
                throw new BusinessException(ErrorCodes.ERROR_IN_DATABASE_POOL);
            }
        }
    }
}