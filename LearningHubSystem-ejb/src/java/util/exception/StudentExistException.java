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
public class StudentExistException extends Exception {

    /**
     * Creates a new instance of <code>StudentExistException</code> without
     * detail message.
     */
    public StudentExistException() {
    }

    /**
     * Constructs an instance of <code>StudentExistException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public StudentExistException(String msg) {
        super(msg);
    }
}
