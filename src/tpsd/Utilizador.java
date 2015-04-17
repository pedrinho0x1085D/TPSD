/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpsd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Asus
 */
public class Utilizador implements Serializable {
    
    private String username;
    private String password;
    private boolean loggedIn;
    private HashMap<Integer,Tarefa> tarefas_seguir;
    private ArrayList<Tarefa> tarefas_criadas;
    
    public Utilizador() {
        this.username="";
        this.password="";
        this.loggedIn=false;
        this.tarefas_seguir=new HashMap<>();
        this.tarefas_criadas=new ArrayList<>();
    }

    public Utilizador(String username, String password) {
        this.username = username;
        this.password = password;
        this.loggedIn=false;
        this.tarefas_seguir=new HashMap();
        this.tarefas_criadas=new ArrayList<>();
    }
    
    public Utilizador(Utilizador u){
        this.username=u.getUsername();
        this.password=u.getPassword();
        this.loggedIn=u.isLoggedIn();
        this.tarefas_seguir=new HashMap<>();
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isLoggedIn() {
        return this.loggedIn;
    }

    public void logIn(){
        this.loggedIn=true;
    }
    public void logOut(){
        this.loggedIn=false;
    }
    
    public boolean validateLogin(String username,String password){
        return this.equals(new Utilizador(username, password));
    }
    @Override
    public Object clone(){
        return new Utilizador(this); 
    }

    @Override
    public boolean equals(Object obj) {
      if(this == obj) return true; 
      if((obj == null) || (this.getClass() != obj.getClass())) return false;
      Utilizador u = (Utilizador) obj;
      return (this.username.equals(u.getUsername())&&this.password.equals(u.getPassword())); 
   } 

    public HashMap<Integer,Tarefa> getTarefas_seguir() {
        return tarefas_seguir;
    }

    public ArrayList<Tarefa> getTarefas_criadas() {
        return tarefas_criadas;
    }
    public void addTarefaCriada(Tarefa t){
        this.tarefas_criadas.add(t);
    }
    public void addTarefaSeguida(int id,Tarefa t){
        this.tarefas_seguir.put(id,t);
    }
    
    public void removeTarefaSeguida(int id){
        this.tarefas_seguir.remove(id);
    }
    @Override
     public String toString(){
        StringBuilder s= new StringBuilder();
        s.append("Username: ");
        s.append(this.getUsername());
        s.append("Password: ");
        s.append(this.getPassword());
        s.append("Logged In: ");
        s.append(this.isLoggedIn());
        return s.toString();
    }
}