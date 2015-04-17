/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpsd;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Asus
 */
public class TarefaMap implements Serializable {
    
    private HashMap<Integer, Tarefa> tarefasCriadas;
    private HashMap<Integer, Tarefa> tarefasExec;
    private Lock lockArm=new ReentrantLock();
    

    public TarefaMap() {
        this.tarefasCriadas=new HashMap<>();
        this.tarefasExec= new HashMap<>();
    }
    
    public Tarefa createTarefa(List<String> objetos, String username,String des){
        Tarefa t;
        lockArm.lock();
        try {
            t = new Tarefa(objetos, username,tarefasCriadas.size()+1,des);
            this.tarefasCriadas.put(t.getIdTarefa(), t);
        } finally {
            lockArm.unlock();
        }
        return t;
    }
    
    public int iniciarTarefa(Tarefa t){
        int id;
        lockArm.lock();
        try {
            id=tarefasExec.size()+1;
            this.tarefasExec.put(id,t);
        } finally {
            lockArm.unlock();
        }
        return id;
    }
    
    public void finish(Tarefa t){
        int id;
        lockArm.lock();
        try {
            id=t.getIdTarefa();
            this.tarefasExec.replace(id,t);
        } finally {
            lockArm.unlock();
        }
        
    }

    public HashMap<Integer, Tarefa> getTarefasExec() {
        return tarefasExec;
    }

    public HashMap<Integer, Tarefa> getTarefasCriadas() {
        return tarefasCriadas;
    }

    public Lock getLockArm() {
        return lockArm;
    }

    public void setLockArm(Lock lockArm) {
        this.lockArm = lockArm;
    }

    public void setTarefasCriadas(HashMap<Integer, Tarefa> tarefasCriadas) {
        this.tarefasCriadas = tarefasCriadas;
    }

    public void setTarefasExec(HashMap<Integer, Tarefa> tarefasExec) {
        this.tarefasExec = tarefasExec;
    }
    
    
    
    
    
}
