/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpsd;

import java.io.Serializable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Asus
 */
public class Objecto implements Serializable{

    private String nome;
    private int quantidade;
    private Lock lockObj;

    public Objecto(String nome, int quantidade) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.lockObj = new ReentrantLock();
    }

    public Objecto(String nome) {
        this.nome = nome;
        this.quantidade = 0;
        this.lockObj = new ReentrantLock();
    }

    public boolean isEmpty(){
        this.lockObj.lock();
        try {
            return (this.quantidade==0);
        }
        finally {
            this.lockObj.unlock();
        }
    } 
    
    public int getQuantidade() {
        this.lockObj.lock();
        try {
            return this.quantidade;
        } finally {
            this.lockObj.unlock();
        }

    }

    public void incQuant(int qty) {
        this.lockObj.lock();
        try {
            this.quantidade+=qty;
        }
        finally {
            this.lockObj.unlock();
        }
    }
    public void decStock(){
        this.lockObj.lock();
        try{
            this.quantidade--;
        }
        finally {
            this.lockObj.unlock();
        }
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public String toString(){
        return new String("Objecto "+this.getNome()+";\nQtd "+this.getQuantidade()+";\n");
    }

    public Lock getLockObj() {
        return lockObj;
    }

    public void setLockObj(Lock lockObj) {
        this.lockObj = lockObj;
    }

    public void setQuantidade(int quantidade) {
        this.lockObj.lock();
        try {
            this.quantidade = quantidade;
        } finally {
            this.lockObj.unlock();
        }
        
    }

    
    
    
   
}
