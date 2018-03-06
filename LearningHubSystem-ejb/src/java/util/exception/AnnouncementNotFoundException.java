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
public class AnnouncementNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>AnnouncementNotFoundException</code>
     * without detail message.
     */
    public AnnouncementNotFoundException() {
    }

    /**
     * Constructs an instance of <code>AnnouncementNotFoundException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public AnnouncementNotFoundException(String msg) {
        super(msg);
    }
}
