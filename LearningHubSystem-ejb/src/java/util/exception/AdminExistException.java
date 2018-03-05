/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author mango
 */
public class AdminExistException extends Exception {

    public AdminExistException() {
    }
    
    public AdminExistException(String msg) {
        super(msg);
    }
    
}
