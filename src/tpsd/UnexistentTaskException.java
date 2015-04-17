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
class UnexistentTaskException extends Exception implements Serializable{

    public UnexistentTaskException(String string) {
    super(string);
    }

    public UnexistentTaskException() {
    super();
    }
    
    
}