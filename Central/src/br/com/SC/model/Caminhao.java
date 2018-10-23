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
public class Caminhao {
    //IDENTIFICAR
    //CAPACIDADE
    //COLETA REALIZADA
    private String idCaminhao;
//    private double capacidade;
    private String rota;
    
//     public Caminhao(int idCaminhao, double capacidade) {
//        this.idCaminhao = idCaminhao;
//        this.capacidade = capacidade;
//    }

    public Caminhao(String idCaminhao) {
        this.idCaminhao = idCaminhao;
    }
    
    
    public String getRota() {
        return rota;
    }

    public void setRota(String rota) {
        this.rota = rota;
    }

    public String getIdCaminhao() {
        return idCaminhao;
    }

    public void setIdCaminhao(String idCaminhao) {
        this.idCaminhao = idCaminhao;
    }
}
