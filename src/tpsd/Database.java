
package tpsd;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pedro Cunha
 */
public class Database implements Serializable {

    private Warehouse warehouse;
    private Utilizadores users;
    private TarefaMap tarefas;

    public Database() {
        this.warehouse = new Warehouse();
        this.users = new Utilizadores();
        this.tarefas= new TarefaMap();
    }

    
    public void registerUser(String username, String password) throws UsernameAlreadyExistsException {
        users.register(username, password);
    }

    public Utilizador userLogin(String username, String password) throws UnexistentUserException, InvalidLoginException, UserAlreadyLoggedException {
        return this.users.logIn(username, password);
    }
    
     public Utilizador userLogout(String username, String password) throws UnexistentUserException, InvalidLoginException, UserAlreadyLoggedException {
        return this.users.logOut(username);
    }

    public int createTarefa(List<String> objetos, String username,String des) throws UnexistentUserException {
        Tarefa t = tarefas.createTarefa(objetos, username, des);
        this.users.getByUsername(username).addTarefaCriada(t);
        return t.getIdTarefa();
    }

    

    public int iniciarTarefa(Tarefa t,String user) throws InterruptedException {
        int id;
        id=this.tarefas.iniciarTarefa(t);
        try {
            this.users.getByUsername(user).addTarefaSeguida(id,t);
        } catch (UnexistentUserException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        warehouse.consume(t.getObjetos());
        return id;
    }

    public int finalizarTarefa(int idT,String user) throws UnexistentTaskException {
        Tarefa t;
        if (!(this.tarefas.getTarefasExec().containsKey(idT))) {
            throw new UnexistentTaskException("" + idT);
        }
        else{
            t=this.tarefas.getTarefasExec().get(idT);
            if(t.isFinished()){
                return -1;
            }
            else{
                if(t.getUtiliz_cria().equals(user)){
                    t.finish();
                    tarefas.finish(t);
                for (String nome : this.tarefas.getTarefasExec().get(idT).getObjetos()) {
                    warehouse.supply(nome, 1);
                }
                try {
                    users.getByUsername(user).removeTarefaSeguida(idT);
                } catch (UnexistentUserException ex) {
                    Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        
        else{
            return -2;
        }
    return 0;
    }
        }
    }

    public void gravaObj(String fich) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fich));
        oos.writeObject(this);
        oos.flush();
        oos.close();
    }

    public Utilizadores getUsers() {
        return this.users;
    }

    public Warehouse getWarehouse() {
        return this.warehouse;
    }

    public TarefaMap getTarefas() {
        return tarefas;
    }

    public void setTarefas(TarefaMap tarefas) {
        this.tarefas = tarefas;
    }

    public void setUsers(Utilizadores users) {
        this.users = users;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }
    
    

}
