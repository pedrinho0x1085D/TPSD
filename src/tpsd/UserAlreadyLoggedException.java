/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpsd;

import java.io.Serializable;

/**
 *
 * @author Pedro Cunha
 */
public class UserAlreadyLoggedException extends Exception implements Serializable{

    public UserAlreadyLoggedException() {
    super();
    }

    public UserAlreadyLoggedException(String message) {
        super(message);
    }
    
}
