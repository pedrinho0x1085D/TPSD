/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpsd;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Asus
 */
public class ClientHandler implements Runnable {

    
    private final Socket socket;
    private Database dados;
    private BufferedReader in;
    private BufferedWriter out;
    private String user;
    

    public ClientHandler(Socket s, Database d) throws IOException {
        this.socket = s;
        this.dados=d;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            int op;
            do {
                switch (op=in.read()) {
                    case 1:login();
                    break;
                    
                    case 2:register();
                    break;
                        
                    case 3:abastecer();
                    break;
                        
                    case 4:registerTar();
                    break;
                    
                    case 5:execTar();
                    break;
                        
                    case 6:finTar();
                    break;
                        
                    case 7:logOut();
                    break;
                        
                    case 8:TarExec();
                            
                        
                }
            }while (op!=0);
            sair();
        } catch (IOException | UnexistentUserException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }    
    }
    
    public void sair(){
        try {
            dados.gravaObj("Dados");
            socket.shutdownInput();
            socket.shutdownOutput();
            socket.close();
            System.exit(0);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void login() throws IOException{
        Boolean aux=true;
        String utl;
        String pass;
        utl=in.readLine();
        pass=in.readLine();
        try {
            dados.userLogin(utl, pass);
        } catch (UnexistentUserException ex) {
            out.write(1);
            out.flush();
            aux=false;
        } catch (InvalidLoginException ex) {
            out.write(2);
            out.flush();
            aux=false;
        } catch (UserAlreadyLoggedException ex) {
            out.write(3);
            out.flush();
            aux=false;
        }
        if(aux){
            user=utl;
            out.write(4);
            out.flush();
        }
    }
    
    public void register() throws IOException{
        Boolean aux=true;
        String utl;
        String pass;
        utl=in.readLine();
        pass=in.readLine();
        try {
            dados.registerUser(utl, pass);
        } catch (UsernameAlreadyExistsException ex) {
            out.write(1);
            out.flush();
            aux=false;
        }
        if(aux){
            out.write(2);
            out.flush();
        }
    }
    
    public void abastecer() throws IOException{
        String obj;
        int n;
        obj=in.readLine();
        n=in.read();
        dados.getWarehouse().supply(obj, n);
    }
    
    public void registerTar() throws IOException, UnexistentUserException{
        List<String> objectos = new ArrayList<>();
        String obj,des;
        int n,quan;
        des=in.readLine();
        n=in.read();
        while(n>0){
            obj=in.readLine();
            quan=in.read();
            while(quan>0){
                objectos.add(obj);
                quan--;
            }
            n--;
        }
        dados.createTarefa(objectos, user,des);
    }
    
    public void execTar() throws IOException, UnexistentUserException{
        HashMap<String,Integer> agr;
        Utilizador userAux;
        int totTar,aux,totObj,id=0;
        Tarefa tar;
        userAux=dados.getUsers().getByUsername(user);
        totTar=userAux.getTarefas_criadas().size();
        out.write(totTar);
        out.flush();
        if(totTar!=0){
            for(Tarefa t: userAux.getTarefas_criadas()){
                out.write(t.getDescricao()+'\n');
                out.flush();
            }
            aux=in.read();
            if(aux!=0){
                tar=userAux.getTarefas_criadas().get(aux-1);
                try {
                    id=dados.iniciarTarefa(tar,user);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
                out.write(id);
                out.flush();
            }
        }
    }
    
    public void finTar() throws IOException{
        int id,error=0;
        boolean aux = true;
        id=in.read();
        try {
            error=dados.finalizarTarefa(id,user);
        } catch (UnexistentTaskException ex) {
            out.write(2);
            out.flush();
            aux=false;
        }
        if(error==-1){
            out.write(3);
            out.flush();
            aux=false;
        }
        if(error==-2){
            out.write(4);
            out.flush();
            aux=false;
        }
        if(aux){
            out.write(1);
            out.flush();
        }
            
    }
    
    public void logOut() throws IOException{
        dados.getUsers().logOut(user);
    }
    
    public void TarExec() throws IOException, UnexistentUserException{
      HashMap<String,Integer> agr;
        Utilizador userAux;
        int totTar,aux,totObj,id;
        Tarefa tar;
        userAux=dados.getUsers().getByUsername(user);
        totTar=userAux.getTarefas_seguir().size();
        out.write(totTar);
        out.flush();
        if(totTar!=0){
            for(Integer i: userAux.getTarefas_seguir().keySet()){
                out.write(i);
                out.flush();
                out.write(userAux.getTarefas_seguir().get(i).getDescricao()+'\n');
                out.flush();
                }   
            }
        }
    }  


