/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.SC.controller;

import br.com.SC.model.Caminhao;
import br.com.SC.model.Lixeira;
import java.util.LinkedList;

/**
 *
 * @author kkaro
 */
public class Controller {

    private LinkedList<Caminhao> caminhoes;
    private LinkedList<Caminhao> listaDeEspera;
    private LinkedList<Lixeira> lixeirasRecolher;
    private LinkedList<Lixeira> lixeiras;
    //private LinkedList<Borda> bordaP;

    public Controller(){
        caminhoes = new LinkedList<>();
        lixeirasRecolher = new LinkedList<>();
        listaDeEspera = new LinkedList<>();
        lixeiras = new LinkedList<>();
        //bordaP = new LinkedList<>();        
    }
    
    
    
    public String cadCaminhao(String ident) {
        Caminhao cam = new Caminhao(ident);
        if(caminhoes.indexOf(cam)<0){
            caminhoes.add(cam);
            System.out.println("Chegou");
            return "cadastrado";
        }else
            return "nao_cadastrado";
    }
    
    public String solicitarRota(String ident){
//        lixeirasRecolher(); //Atualiza a lista de lixeiras prontas para coleta
//        int i=0;
//        
//        while(i != 3 && !lixeirasRecolher.isEmpty()){
//            aux += lixeirasRecolher.get(0).getIdLixeira() + "%";
//            lixeirasRecolher.remove(0);
//            i++;
//        }   
        String aux = gerarRota();     
        Caminhao cam = pesquisarCaminhao(ident);
        cam.setRota(aux);
        return aux;
    }        
    
    public Caminhao pesquisarCaminhao(String idcam){
        Caminhao caminhao = null;
        for (Caminhao c:caminhoes){
            if(c.getIdCaminhao().equals(idcam)){
                caminhao = c;
            }
        }
//        for(int i=0;i<=caminhoes.size();i++){
//            if(caminhoes.get(i).getIdCaminhao().equals(idcam)){
//                caminhao = caminhoes.get(i);
//            }
//        }
        return caminhao;        
    }
    
   
    public String rotaFinalizada(String ident){
        Caminhao cam = pesquisarCaminhao(ident);
        cam.setRota(" ");
        return cam.getRota();
    }    
    
    
    
    public void lixeirasRecolher(){
        for(Lixeira l:lixeiras){
            if(l.getVlmLixeira() >= 10 && l.getVlmLixeira() <=100){
                lixeirasRecolher.add(l);
            }
        }
        
        
        
//        for(int i=0;i<lixeiras.size();i++){
//            if(lixeiras.get(i).getVlmLixeira() >= 10 && lixeiras.get(i).getVlmLixeira() <= 100 ){
//                lixeirasRecolher.add(lixeiras.get(i));  
//            }
//        }        
    }
    
    public String lixeiraColetada(String lixeiras){        
        Lixeira lixeira = pesquisarLixeira(lixeiras);
        lixeira.setVlmLixeira(0);

        return lixeira.getIdLixeira();
    }
    
    public Lixeira pesquisarLixeira(String idlix){
        Lixeira lixeira = null;
        for(Lixeira l:lixeiras){
            if(l.getIdLixeira().equals(idlix)){
                lixeira = l;
                return lixeira;
            }
        }    
        
//        for(int i=0;i<=lixeiras.size();i++){
//            if(lixeiras.get(i).getIdLixeira().equals(idlix)){
//                lixeira = lixeiras.get(i);
//            }
//        }
        return null;        
    }  
    
    public String cadLixeira(String ident, double vl, double capac){
        Lixeira lixeira = new Lixeira(ident, capac, vl);
        lixeiras.add(lixeira);
        System.out.println("Lixeira cadastrada");
        return "Lixeira cadastrada";
    }
    
    public void capacidadeTotal(String ident, double capac){
        Lixeira lixeira = getLixeira(ident);
        lixeira.setCpcLixeira(capac);
    }
    
    public void capacidadeAtual(String ident, double vl){
        Lixeira lixeira = getLixeira(ident);
        lixeira.setVlmLixeira(vl);
    }
    
    public Lixeira getLixeira(String ident){
        Lixeira lixeira = null;
        for(Lixeira l:lixeiras){
            if(l.getIdLixeira().equals(ident)){
                lixeira = l;
                return lixeira;
            }
        }
        
//        for(int i=0;i<=lixeiras.size();i++){
//            if(lixeiras.get(i).getIdLixeira().equals(ident)){
//                return lixeiras.get(i);
//            }
//        }
        return null;
    }   
    
    public String[] getRotas(){
        String str[] = new String[caminhoes.size()];
        int i = 0;
        for(Caminhao c:caminhoes){            
            str[i] = c.getIdCaminhao()+"->"+c.getRota();
            i++;
        }
        return str;
    }
    
    // ----------------------------------------------------------------------------------//
    
//    public void addBorda(String bordas){
//        String array[] = bordas.split("%");
//        for(String a:array){//FOR EACH
//            String recebe[] = a.split(":");
//            Borda bord = new Borda(recebe[0], recebe[1], array.length+"");
//            
//            bordaP.add(bord);
//        }
//    }
//    
//    public String listarBordas(){
//        String str = "";
//        
//        for(Borda b:bordaP){
//            String aux = b.getEndereco()+":"+b.getPorta();
//            str += aux + "%";
//        }        
//        return str;
//    }
    
//    public String getCaminhaoEspera(String id){
//        return listaDeEspera.get(listaDeEspera.indexOf(new Caminhao(id))).toString();
//    }
    
    public Caminhao pesquisarCaminhaoEspera(String idcam){
        Caminhao caminhao;
        for (Caminhao c:listaDeEspera){
            if(c.getIdCaminhao().equals(idcam)){
                caminhao = c;
                return caminhao;
            }
        }
//        for(int i=0;i<=listaDeEspera.size();i++){
//            if(listaDeEspera.get(i).getIdCaminhao().equals(idcam)){
//                caminhao = listaDeEspera.get(i);
//            }
//        }
        return null;        
    }    
    
    public String caminhaoQuebrar(String ident){
        Caminhao cam = pesquisarCaminhao(ident);
        listaDeEspera.add(cam);
        caminhoes.remove(cam);
        
        return cam.getIdCaminhao();
    }    
    
    public String caminhaoConsertar(String ident){
        Caminhao cam = pesquisarCaminhaoEspera(ident);
        caminhoes.add(cam);
        listaDeEspera.remove(cam);
        
        return cam.getIdCaminhao();
    }
    
    public String gerarRota(){
        lixeirasRecolher(); //Atualiza a lista de lixeiras prontas para coleta
        int i=0;
        String aux = ""; 
        while(i != 3 && !lixeirasRecolher.isEmpty()){
            aux += lixeirasRecolher.get(0).getIdLixeira() + ":";
            lixeirasRecolher.remove(0);
            i++;
        }
        return aux;
    }
    
    //Método que retorna para o MulticastReceiver se há ou não caminhões no servidor
    public boolean temCam(){ 
        return !caminhoes.isEmpty();
    } 
    
    
}