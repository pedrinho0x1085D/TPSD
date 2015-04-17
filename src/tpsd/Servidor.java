/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpsd;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;


/**
 *
 * @author Asus
 */

public class Servidor {

    private static Database dados= new Database();
     
   
    public static void main(String[] args) throws IOException  {
       try{carregaDados();
            }catch(ClassNotFoundException e) {
                System.out.println("class exception");
            }
       ServerSocket sSock = new ServerSocket(2000);
       Thread tAdmin=new Thread(new AdminHandler(dados));
       tAdmin.start();
       while(true){ 
               Socket sck = new Socket();
               sck=sSock.accept();
               
               Thread tUtl=new Thread(new ClientHandler(sck,dados));
               tUtl.start();
            }
    }


 public static void carregaDados() throws IOException,ClassNotFoundException{
        FileInputStream door = new FileInputStream("Dados"); 
        ObjectInputStream reader = new ObjectInputStream(door); 
        if(dados instanceof Database)
            dados = (Database) reader.readObject();
    }
}
    

