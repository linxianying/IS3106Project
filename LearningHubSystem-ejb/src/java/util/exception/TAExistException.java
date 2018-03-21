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
public class TAExistException extends Exception {
        public TAExistException() {
    }
        
    public TAExistException(String msg) {
        super(msg);
    }
}
