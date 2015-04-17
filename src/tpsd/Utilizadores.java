/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpsd;

import java.io.Serializable;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Pedro Cunha
 */
public class Utilizadores implements Serializable {
    private HashMap<String,Utilizador> users;
    private Lock lockUtl;

    public Utilizadores() {
        this.users=new HashMap<>();
        this.lockUtl=new ReentrantLock();
    }
    
    public void register (String username,String password) throws UsernameAlreadyExistsException {
        this.lockUtl.lock();
        try{
        if (this.users.containsKey(username)) throw new UsernameAlreadyExistsException(username);
        else this.users.put(username, new Utilizador(username, password));
    }
        finally{
            this.lockUtl.unlock();
        }
    }
    
    public Utilizador logIn (String username,String password) throws UnexistentUserException,InvalidLoginException,UserAlreadyLoggedException{
        Utilizador a;
        this.lockUtl.lock();
        try{
        if (!(this.users.containsKey(username))) throw new UnexistentUserException(username);
        else if (!(this.users.get(username).validateLogin(username, password))) throw new InvalidLoginException();
        else if (this.users.get(username).isLoggedIn()) throw new UserAlreadyLoggedException(username);
        else {
            this.users.get(username).logIn();
            a=this.users.get(username);
        }
        }
        finally{
            this.lockUtl.unlock();
            }
        return a;
    }
    
    public Utilizador logOut (String username) {
        Utilizador a;
        this.lockUtl.lock();
        try {
            this.users.get(username).logOut();
            a=this.users.get(username);
        } finally {
            this.lockUtl.unlock();
        }
        return a;
    }
    
    public Utilizador getByUsername(String username) throws UnexistentUserException{
        if (!(this.users.containsKey(username))) throw new UnexistentUserException(username);
        else return this.users.get(username);
    }

    public Lock getLockUtl() {
        return lockUtl;
    }

    public HashMap<String, Utilizador> getUsers() {
        return users;
    }

    public void setLockUtl(Lock lockUtl) {
        this.lockUtl = lockUtl;
    }

    public void setUsers(HashMap<String, Utilizador> users) {
        this.users = users;
    }
    
    
    
}