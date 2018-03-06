/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author Samango
 */
public class AnnouncementExistException extends Exception {

    /**
     * Creates a new instance of <code>AnnoucementExistException</code> without
     * detail message.
     */
    public AnnouncementExistException() {
    }

    /**
     * Constructs an instance of <code>AnnoucementExistException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public AnnouncementExistException(String msg) {
        super(msg);
    }
}
