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
public class UsernameAlreadyExistsException extends Exception implements Serializable {

    public UsernameAlreadyExistsException() {
    super();
    }

    public UsernameAlreadyExistsException(String message) {
        super(message);
    }
    
}