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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Asus
 */
public class Cliente {
    private static Menu menuLogin,menuPrincipal;
    private static Socket socket;
    private static BufferedReader in;
    private static BufferedWriter out;
   
   
    
    
    public static void main(String[] args) throws IOException{
        carregaMenus();
        socket =new Socket("localhost",2000);
        in= new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        execMenuLogin();
        
        
    }
    
     public static void execMenuLogin() throws IOException{
         do {
            System.out.println("************ Bem-vindo ao armazém ************");
            menuLogin.executa();
            switch (menuLogin.getOpcao()) {
                case 1:{
                    out.write(1);
                    out.flush();
                    eLogin();
                }
                case 2:{
                    out.write(2);
                    out.flush();
                    eRegister();
                }
                    
                
            }
        }while (menuLogin.getOpcao()!=0);
        sair();
    }
     
    public static void eLogin() throws IOException{
        int aux;
        System.out.println("Insira o utilizador");
        String email=Input.lerString();
        System.out.println("Insira a password");
        String pass=Input.lerString();
        out.write(email+'\n');
        out.flush();
        out.write(pass+'\n');
        out.flush();
        aux=in.read();
        switch (aux){
            case 1:{
                System.out.println("Utilizador inexistente");
                mostraMensagem();
            }
            break;
            case 2:{
                System.out.println("Utilizador ou password incorectos");
                mostraMensagem();
            }
            break;
            case 3:{
                System.out.println("Utilizador já se encontra online");
                mostraMensagem();
            }
            break;
            case 4:{
                execMenuPrincipal();
            }
            break;
        }
        execMenuLogin();
    }
    
     public static void eRegister() throws IOException{
        int aux;
        System.out.println("Insira o utilizador");
        String email=Input.lerString();
        System.out.println("Insira a password");
        String pass=Input.lerString();
        out.write(email+'\n');
        out.flush();
        out.write(pass+'\n');
        out.flush();
        aux=in.read();
        switch (aux){
            case 1:{
                System.out.println("Nome de utilizador existente");
                mostraMensagem();
            }
            break;
            case 2:{
                System.out.println("Utilizador registado");
                mostraMensagem();
            }
            break;
        }
        execMenuLogin();
        
    }
     
    public static void execMenuPrincipal() throws IOException{
         do {
            System.out.println("************ Bem-vindo ao armazém ************");
            menuPrincipal.executa();
            switch (menuPrincipal.getOpcao()) {
                case 1:{
                    out.write(3);
                    out.flush();
                    eAbastecer();
                }
                case 2:{
                    out.write(4);
                    out.flush();
                    eDefTar();
                }
                
                case 3:{
                    out.write(5);
                    out.flush();
                    eExecTar();
                }
                
                case 4:{
                    out.write(6);
                    out.flush();
                    eFinTar();
                }
                
                case 5:{
                    out.write(8);
                    out.flush();
                    eTarExec();
                }
                    
                
            }
        }while (menuPrincipal.getOpcao()!=0);
        out.write(7);
        out.flush();
        execMenuLogin();
    }
    
    public static void eAbastecer() throws IOException{
        System.out.println("Insira o nome do objecto");
        String nome=Input.lerString();
        System.out.println("Insira a quantidade do objecto");
        int quan=Input.lerInt();
        out.write(nome+'\n');
        out.flush();
        out.write(quan);
        out.flush();
        execMenuPrincipal();
    }
    
    public static void eDefTar() throws IOException{
        String nome,des;
        int quan;
        System.out.println("Insira o nome da tarefa");
        des=Input.lerString();
        out.write(des+'\n');
        out.flush();
        System.out.println("Insira quantos objectos vai desejar");
        int n=Input.lerInt();
        out.write(n);
        out.flush();
        while(n>0){
            System.out.println("Insira o nome do objecto");
            nome=Input.lerString();
            System.out.println("Insira a quantidade do objecto");
            quan=Input.lerInt();
            out.write(nome+'\n');
            out.flush();
            out.write(quan);
            out.flush();
            n--;
        }
        execMenuPrincipal();
    }
    
     public static void eExecTar() throws IOException{
         int aux,totTar,id;
         String des;
         totTar=in.read();
         if(totTar==0){
             System.out.println("Lista de tarefas vazia");
             mostraMensagem();
             execMenuPrincipal();
         }
         for(aux=1;aux<=totTar;aux++){
             des=in.readLine();
             System.out.println(aux+"-"+des);
         }
         System.out.print("Opção(0 para sair):");
         aux=Input.lerInt();
         out.write(aux);
         out.flush();
         if(aux!=0){
            id=in.read();
            System.out.println("Está a executar a tarefa identificada por:" + id);
            mostraMensagem();
         }
         execMenuPrincipal();
     }
     
     public static void eFinTar() throws IOException{
       int aux;
       System.out.println("Insira o código de tarefa");
       aux=Input.lerInt();
       out.write(aux);
       out.flush();
       aux=in.read();
       if(aux==3){
           System.out.println("Tarefa já foi terminada");
           mostraMensagem();
           execMenuPrincipal();
       }
       if(aux==4){
           System.out.println("Não tem permissão para terminar essa tarefa");
           mostraMensagem();
           execMenuPrincipal();
       }
       if(aux==2){
           System.out.println("Tarefa não se encontra a executar");
           mostraMensagem();
       }
       else{
           System.out.println("Tarefa terminada com sucesso");
           mostraMensagem();
       }
       execMenuPrincipal();
     }
     
     public static void eTarExec() throws IOException{
        int aux,totTar,id;
         String des;
         totTar=in.read();
         if(totTar==0){
             System.out.println("Não se encontra a executar nenhuma tarefa");
             mostraMensagem();
             execMenuPrincipal();
         }
         for(aux=1;aux<=totTar;aux++){
             System.out.print("Código: ");
             id=in.read();
             System.out.print(id);
             des=in.readLine();
             System.out.print(" "+des);
             System.out.print('\n');
         }
         System.out.print("Insira um inteiro para sair.");
         aux=Input.lerInt();
         execMenuPrincipal(); 
     }
    
   
    
    public static void sair(){
        try {
            socket.shutdownInput();
            socket.shutdownOutput();
            socket.close();
            System.exit(0);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
 
    
public static void carregaMenus(){
        String[] login={  "Login",
                          "Criar conta",
                          }; 
        
        String[] principal={   "Abastecer armazém",
                               "Definir tarefa",
                               "Executar tarefa",
                               "Terminar Tarefa",
                               "Lista de tarefas em execução"
                               };
                               
      
                          
       menuLogin=new Menu(login);
       menuPrincipal=new Menu(principal);
      
    }
    

private static void mostraMensagem(){
        String c;
        System.out.println("--- Prima <e> para sair ---");
        do {
            c = Input.lerString();
        }
        while(c.endsWith("e") != true);
        
    }
}

