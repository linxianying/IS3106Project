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
public class LecturerNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>LecturerNotFoundException</code> without
     * detail message.
     */
    public LecturerNotFoundException() {
    }

    /**
     * Constructs an instance of <code>LecturerNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public LecturerNotFoundException(String msg) {
        super(msg);
    }
}
