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
public class TimeEntryNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>TimeEntryNotFoundException</code> without
     * detail message.
     */
    public TimeEntryNotFoundException() {
    }

    /**
     * Constructs an instance of <code>TimeEntryNotFoundException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public TimeEntryNotFoundException(String msg) {
        super(msg);
    }
}
