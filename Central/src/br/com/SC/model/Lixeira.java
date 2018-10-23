/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.SC.model;

/**
 *
 * @author kkaro
 */
public class Lixeira {
    //IDENTIFICADOR
    //CAPACIDADE
    //VOLUME ALCANÇADO    
    private String idLixeira;
    private double cpcLixeira;   
    private double vlmLixeira;
    
    public Lixeira(String idLixeira, double cpcLixeira, double vlmLixeira) {
        this.idLixeira = idLixeira;
        this.cpcLixeira = cpcLixeira;
        this.vlmLixeira = vlmLixeira;
    }

    public String getIdLixeira() {
        return idLixeira;
    }

    public void setIdLixeira(String idLixeira) {
        this.idLixeira = idLixeira;
    }

    public double getCpcLixeira() {
        return cpcLixeira;
    }

    public void setCpcLixeira(double cpcLixeira) {
        this.cpcLixeira = cpcLixeira;
    }

    public double getVlmLixeira() {
        return vlmLixeira;
    }

    public void setVlmLixeira(double vlmLixeira) {
        this.vlmLixeira = vlmLixeira;
    }
    
    
}
