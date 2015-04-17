/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpsd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Asus
 */
public class AdminHandler implements Runnable {
   
    private Database dados;
    private String user;
    private static Menu menuLogin,menuPrincipal;
    
    public AdminHandler(Database d){
        dados=d;
    }

    @Override
    public void run() {
        carregaMenus();
        try {
            aExecMenuLogin();
        } catch (UnexistentUserException ex) {
            Logger.getLogger(AdminHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void aExecMenuLogin() throws UnexistentUserException{
         do {
            
            System.out.println("************ Bem-vindo ao armazém ************");
            menuLogin.executa();
            switch (menuLogin.getOpcao()) {
                case 1:aLogin();
                break;
                    
                case 2:aRegister();
                break;
                    
                
            }
        }while (menuLogin.getOpcao()!=0);
        try {
            dados.gravaObj("Dados");
        } catch (IOException ex) {
            Logger.getLogger(AdminHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit(0);
    }
    
    public void aLogin() throws UnexistentUserException{
        System.out.println("Insira o utilizador");
        String email=Input.lerString();
        System.out.println("Insira a password");
        String pass=Input.lerString();
        try {
            dados.userLogin(email, pass);
        } catch (UnexistentUserException ex) {
            System.out.println("Utilizador inexistente");
            mostraMensagem();
            aExecMenuLogin();
        } catch (InvalidLoginException ex) {
            System.out.println("Utilizador ou password incorectos");
            mostraMensagem();
            aExecMenuLogin();
        } catch (UserAlreadyLoggedException ex) {
            System.out.println("Utilizador já se encontra online");
            mostraMensagem();
            aExecMenuLogin();
        }
        user=email;
        aExecMenuPrincipal();
    }
    
    public void aRegister() throws UnexistentUserException{
        System.out.println("Insira o utilizador");
        String email=Input.lerString();
        System.out.println("Insira a password");
        String pass=Input.lerString();
        try {
            dados.registerUser(email, pass);
        } catch (UsernameAlreadyExistsException ex) {
             System.out.println("Nome de utilizador existente");
             mostraMensagem();
             aExecMenuLogin();
        }
        System.out.println("Utilizador registado");
        mostraMensagem();
        aExecMenuLogin();
    }
    
    public void aExecMenuPrincipal() throws UnexistentUserException{
         do {
            System.out.println("************ Bem-vindo ao armazém ************");
            menuPrincipal.executa();
            switch (menuPrincipal.getOpcao()) {
                case 1:aAbastecer();
                break;
                
                case 2:aDefTar();
                break;
                
                case 3:aExecTar();
                break;
                
                case 4:aFinTar();
                break;
                
                case 5:aTarExec();
                break;
                    
                
            }
        }while (menuPrincipal.getOpcao()!=0);
        logOut();
        aExecMenuLogin();
    }
    
    public void aAbastecer() throws UnexistentUserException{
        System.out.println("Insira o nome do objecto");
        String nome=Input.lerString();
        System.out.println("Insira a quantidade do objecto");
        int quan=Input.lerInt();
        dados.getWarehouse().supply(nome, quan);
        aExecMenuPrincipal();
    }
    
    public void aDefTar() throws UnexistentUserException{
       String nome,des;
       int quan;
       List<String> objectos = new ArrayList<>();
       System.out.println("Insira o nome da tarefa");
       des=Input.lerString();
       System.out.println("Insira quantos objectos vai desejar");
       int n=Input.lerInt();
       while(n>0){
           System.out.println("Insira o nome do objecto");
           nome=Input.lerString();
           System.out.println("Insira a quantidade do objecto");
           quan=Input.lerInt();
           while(quan>0){
                objectos.add(nome);
                quan--;
            }
           n--;
       }
       dados.createTarefa(objectos, user,des);
       aExecMenuPrincipal();
    }
    
    public void aExecTar() throws UnexistentUserException{
        HashMap<String,Integer> agr;
        int aux,ntar=1,id=-1;
        Utilizador userAux;
        Tarefa tar;
        
        userAux=dados.getUsers().getByUsername(user);
        if(userAux.getTarefas_criadas().isEmpty()){
            System.out.println("Lista de tarefas vazia");
            mostraMensagem();
            aExecMenuPrincipal();
        }
        for(Tarefa t: userAux.getTarefas_criadas()){
            System.out.print(ntar+"- ");
            System.out.print(t.getDescricao());
            System.out.print('\n');
            ntar++;
        }
        System.out.print("Opção:");
        aux=Input.lerInt();
        if(aux!=0){
            tar=userAux.getTarefas_criadas().get(aux-1);
            try {
                id=dados.iniciarTarefa(tar,user);
            } catch (InterruptedException ex) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Está a executar a tarefa identificada por:" + id);
            mostraMensagem();
          }
      aExecMenuPrincipal();
    }
    
    public void aFinTar() throws UnexistentUserException {
       int id,aux=0;
       System.out.println("Insira o código de tarefa");
       id=Input.lerInt();
       try {
            aux=dados.finalizarTarefa(id,user);
        } catch (UnexistentTaskException ex) {
            System.out.println("Tarefa não se encontra a executar");
            mostraMensagem();
            aExecMenuPrincipal();
        }
       if(aux==-1){
           System.out.println("Tarefa já foi terminada");
           mostraMensagem();
           aExecMenuPrincipal();
       }
       if(aux==-2){
           System.out.println("Não tem permissão para terminar essa tarefa");
           mostraMensagem();
           aExecMenuPrincipal();
       }
       if(aux==0){
            System.out.println("Tarefa executada com sucesso");
            mostraMensagem();
            aExecMenuPrincipal();
       }
     }
    
    public void aTarExec() throws UnexistentUserException{
        int aux;
        String des;
        Utilizador userAux;
        userAux=dados.getUsers().getByUsername(user);
        if(userAux.getTarefas_seguir().isEmpty()){
            System.out.println("Não se encontra a executar nenhuma tarefa");
            mostraMensagem();
            aExecMenuPrincipal();
        }
        for(Integer i: userAux.getTarefas_seguir().keySet()){
           System.out.print("Código: ");
           System.out.print(i);
           des=userAux.getTarefas_seguir().get(i).getDescricao();
           System.out.print(" "+des);
           System.out.print('\n');
        }
        System.out.print("Insira um inteiro para sair.");
        aux=Input.lerInt();
        aExecMenuPrincipal(); 
     }

    
    
    
    public void logOut(){
        dados.getUsers().logOut(user);
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
