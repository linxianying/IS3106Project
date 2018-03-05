/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author wyh
 */
public class LecturerExistException extends Exception {

    /**
     * Creates a new instance of <code>LecturerExistException</code> without
     * detail message.
     */
    public LecturerExistException() {
    }

    /**
     * Constructs an instance of <code>LecturerExistException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public LecturerExistException(String msg) {
        super(msg);
    }
}
