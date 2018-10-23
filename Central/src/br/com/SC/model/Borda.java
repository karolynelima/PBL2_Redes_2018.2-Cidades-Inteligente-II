/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.SC.model;

/**
 *
 * @author karolyne
 */
public class Borda {
    private String endereco;
    private String porta;
    private String ID;    

    public Borda(String endereço, String porta, String ID){
        this.endereco = endereço;
        this.porta = porta;
        this.ID = ID;
    }
    
    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getPorta() {
        return porta;
    }

    public void setPorta(String porta) {
        this.porta = porta;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
    
}
