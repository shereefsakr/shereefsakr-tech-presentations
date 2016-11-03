/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.training.threading.common;

/**
 *
 * @author Sherif Saker
 */
public class BusinessException extends Exception {

    private Exception cause;
    private Throwable causeThrw;
    private int errorCode;
    private String errorMessage;
    private int initialErrorCode;
    private int errorPrefix = 10;

    public BusinessException(int errorCode) {
        String error = String.valueOf(errorPrefix) + String.valueOf(errorCode);
        this.errorCode = Integer.parseInt(error);
        this.initialErrorCode = errorCode;
        this.errorMessage = null;
    }

    public BusinessException(int errorCode, String errorMessage) {
        String error = String.valueOf(errorPrefix) + String.valueOf(errorCode);
        this.errorCode = Integer.parseInt(error);
        this.initialErrorCode = errorCode;
        this.errorMessage = errorMessage;
    }
    
         public  BusinessException(Exception e, int errorCode) {
        this.cause = e;
        String error = String.valueOf(errorPrefix) + String.valueOf(errorCode);
        this.errorCode = Integer.parseInt(error);
        this.initialErrorCode = errorCode;
        this.errorMessage = null ;
    }

     public BusinessException(Exception e, int errorCode, String errorMessage) {
        this.cause = e;
        String error = String.valueOf(errorPrefix) + String.valueOf(errorCode);
        this.errorCode = Integer.parseInt(error);
        this.initialErrorCode = errorCode;
        this.errorMessage = errorMessage;
        
       
    }

     public BusinessException(Throwable e, int errorCode) {
        this.causeThrw = e;
        String error = String.valueOf(errorPrefix) + String.valueOf(errorCode);
        this.errorCode = Integer.parseInt(error);
        this.initialErrorCode = errorCode;
        this.errorMessage = null ;
    }

     public BusinessException(Throwable e, int errorCode, String errorMessage) {
        this.causeThrw = e;
        String error = String.valueOf(errorPrefix) + String.valueOf(errorCode);
        this.errorCode = Integer.parseInt(error);
        this.initialErrorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    /**
     * @return the cause
     */
    public Exception getCause() {
        return cause;
    }

    /**
     * @param cause the cause to set
     */
    public void setCause(Exception cause) {
        this.cause = cause;
    }

    /**
     * @return the causeThrw
     */
    public Throwable getCauseThrw() {
        return causeThrw;
    }

    /**
     * @param causeThrw the causeThrw to set
     */
    public void setCauseThrw(Throwable causeThrw) {
        this.causeThrw = causeThrw;
    }

    /**
     * @return the errorCode
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * @return the initialErrorCode
     */
    public int getInitialErrorCode() {
        return initialErrorCode;
    }

    /**
     * @param initialErrorCode the initialErrorCode to set
     */
    public void setInitialErrorCode(int initialErrorCode) {
        this.initialErrorCode = initialErrorCode;
    }

    /**
     * @return the errorPrefix
     */
    public int getErrorPrefix() {
        return errorPrefix;
    }

    /**
     * @param errorPrefix the errorPrefix to set
     */
    public void setErrorPrefix(int errorPrefix) {
        this.errorPrefix = errorPrefix;
    }

}
