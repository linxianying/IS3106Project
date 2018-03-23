/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author lin
 */
public class TimeEntryExistException extends Exception {

    /**
     * Creates a new instance of <code>TimeEntryExistException</code> without
     * detail message.
     */
    public TimeEntryExistException() {
    }

    /**
     * Constructs an instance of <code>TimeEntryExistException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public TimeEntryExistException(String msg) {
        super(msg);
    }
}
