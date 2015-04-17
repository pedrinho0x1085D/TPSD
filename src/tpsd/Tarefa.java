/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpsd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/*
 *
 * @author Pedro Cunha
 */
public class Tarefa implements Serializable {

    private int idTarefa;
    private List<String> objetos;
    private String utiliz_cria;
    private boolean finished;
    private String descricao;

    public Tarefa() {
        this.objetos = new ArrayList<>();
        this.utiliz_cria="";
        this.finished=false;
    }

    public Tarefa(List<String> objetos, String username) {
        this.objetos = objetos;
        this.utiliz_cria= username;
        this.finished=false;
    }
    
    public Tarefa(List<String> objetos, String username,int id,String s) {
        this.idTarefa=id;
        this.objetos = objetos;
        this.utiliz_cria= username;
        this.finished=false;
        this.descricao=s;
        
    }

    public int getIdTarefa() {
        return this.idTarefa;
    }

    public List<String> getObjetos() {
        return this.objetos;
    }

    public String getUtiliz_cria() {
        return utiliz_cria;
    }

    public boolean isFinished() {
        return this.finished;
    }
    public void finish(){
        this.finished=true;
    }

    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public void setIdTarefa(int idTarefa) {
        this.idTarefa = idTarefa;
    }

    public void setObjetos(List<String> objetos) {
        this.objetos = objetos;
    }

    public void setUtiliz_cria(String utiliz_cria) {
        this.utiliz_cria = utiliz_cria;
    }   
}
