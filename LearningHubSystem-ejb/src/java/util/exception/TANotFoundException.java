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
public class TANotFoundException extends Exception{
        public TANotFoundException() {
    }

    public TANotFoundException(String msg) {
        super(msg);
    }
}
