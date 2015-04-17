/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpsd;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;



/**
 *
 * @author Pedro Cunha
 */
public class Warehouse implements Serializable{
    private HashMap<String,Objecto> items;
    private Lock lockArm=new ReentrantLock();
    private Condition vazio=lockArm.newCondition();

    public Warehouse() {
        this.items=new HashMap<>();
    }
   
    public void supply(String item,int quantity){
        this.lockArm.lock();
        try{
            if(this.items.containsKey(item))
                this.items.get(item).incQuant(quantity);
            else {
                this.items.put(item, new Objecto(item, quantity));
            }
            vazio.signalAll();
        }
        finally {
            this.lockArm.unlock();
        }
    }
    
   public void consume (List<String> items)throws InterruptedException{
        this.lockArm.lock();
        try{
            for(String it:items)
                if (!(this.items.containsKey(it)))
                    vazio.await();
                else if(this.items.get(it).isEmpty())
                    vazio.await();
                else this.items.get(it).decStock();
        }
        finally {
            this.lockArm.unlock();
        }
    }

    public HashMap<String, Objecto> getItems() {
        return items;
    }

    public Lock getLockArm() {
        return lockArm;
    }

    public Condition getVazio() {
        return vazio;
    }

    public void setItems(HashMap<String, Objecto> items) {
        this.items = items;
    }

    public void setLockArm(Lock lockArm) {
        this.lockArm = lockArm;
    }

    public void setVazio(Condition vazio) {
        this.vazio = vazio;
    }
    
}