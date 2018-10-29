/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.SC.connection;

import br.com.SC.controller.Controller;
import br.com.SC.model.Borda;
import br.com.SC.model.Lixeira;
import com.sun.corba.se.impl.io.IIOPInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kkaro
 */
public class AtividadeServidor extends Thread{
    private Socket server;
    private Controller control;//INSTANCIAÇÃO DO CONTROLLER
    private Socket clienteTCP;//INSTANCIAÇÃO DO CLIENTE TCP (CAMINHÃO)
    private DatagramSocket clienteUDP;//INSTANCIAÇÃO DO CLIENTE UDP (LIXEIRA)
    private ObjectInputStream entradaTCP;
    private ObjectOutputStream saidaTCP;
    private Socket socket;
    private String mensagem;
    private DatagramPacket entradaUDP;
    private byte[] saidaUDP;
    private MulticastPublisher multEnviar; 
    private MulticastReceiver multReceber;
    private String IP;
    
    public AtividadeServidor(Socket cliente, Controller control) throws IOException, ClassNotFoundException   {
        clienteTCP = cliente;//Recebe a conexão          
        this.control = control;//Seta o objeto que contém as informações do sistema        
        entradaTCP = new ObjectInputStream(clienteTCP.getInputStream());//Decifra as informações vindas do cliente
        System.out.println("Cliente TCP: "+clienteTCP.getInetAddress()+":"+clienteTCP.getPort());
        mensagem = (String) entradaTCP.readObject();  
        System.out.println(mensagem);
        
        IP = InetAddress.getLocalHost().getHostAddress();        
        multEnviar = new MulticastPublisher();
        multReceber = new MulticastReceiver(control);
    }

    AtividadeServidor(DatagramSocket client, DatagramPacket p, Controller control) throws UnknownHostException {
        clienteUDP = client;
        this.control = control;
        this.entradaUDP = p;
        System.out.println("Conectou");
        
        System.out.println("Cliente UDP: "+entradaUDP.getAddress()+":"+entradaUDP.getPort());
        mensagem = new String(entradaUDP.getData(),0,entradaUDP.getLength());//Transforma o objeto passado em String
        System.out.println("Recebido: "+mensagem);
        
        IP = InetAddress.getLocalHost().getHostAddress();        
        multEnviar = new MulticastPublisher();
        multReceber = new MulticastReceiver(control);
    }
    
    
    @Override
    public void run() {
        try {
            String array[] = mensagem.split("%");
            
            switch (array[0]){
                case "C":
                    switch (array[1]){
                        case "CONECTA":
                            respondeConexao();
                            break;
                        case "CADASTRO":
                            cadCaminhao(array[2]);
                            break;
                        case "SOLICITANDO":
                            solicitarRota(array[3]);
                            break;
                        case "RECOLHEU":
                            lixeiraColetada(array[3]);
                            break;
                        case "ROTA":
                            rotaFinalizada(array[3]);
                            break;
                        case "QUEBROU":
                            caminhaoQuebrar(array[2]);
                            break;
                        case "CONCERTADO":
                            caminhaoConcertar(array[2]);
                            break;    
                            
                    }break;
                case "L":
                    switch(array[1]){
                        case "CADASTRO":
                            cadLixeira(array[2],array[3],array[4]);
                            break;
                        case "ATUAL":
                            capacidadeAtual(array[3], array[4]);
                            break;                            
                    }
                    break;
            }
        } catch (IOException ex) {
            Logger.getLogger(AtividadeServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void respondeConexao() throws IOException {
        ObjectOutputStream output = new ObjectOutputStream(clienteTCP.getOutputStream());
        output.writeObject("Conectado com sucesso");
    }

    private void cadCaminhao(String ident) throws IOException {
        String s = control.cadCaminhao(ident);
        ObjectOutputStream output = new ObjectOutputStream(clienteTCP.getOutputStream());
        if(s.equals("cadastrado"))
            output.writeObject("Cadastrado com sucesso");
        else
            output.writeObject("Não foi possível cadastrar");
    }
    
    private void solicitarRota(String ident) throws IOException{
        String s = control.solicitarRota(ident);
        ObjectOutputStream output = new ObjectOutputStream(clienteTCP.getOutputStream());
        if(s != null){
            output.writeObject(s);
        }else
            output.writeObject("Não existe lixeira para ser coletada!");
    }
    private void lixeiraColetada(String lixeira) throws IOException{
        String L = control.lixeiraColetada(lixeira);
        ObjectOutputStream output = new ObjectOutputStream(clienteTCP.getOutputStream());
        if(L != null){
            output.writeObject(L);
        }else
            output.writeObject("Lixeira não pode ser coletada!");
    }
    private void rotaFinalizada(String ident) throws IOException{
        String s = control.rotaFinalizada(ident);
        ObjectOutputStream output = new ObjectOutputStream(clienteTCP.getOutputStream());
        if(s != null){
            output.writeObject(s);
        }else
            output.writeObject("Rota ainda não foi finalizada!");
    }
    
    private void cadLixeira(String ident, String vl, String capac){           
        control.cadLixeira(ident,Double.parseDouble(vl),Double.parseDouble(capac));
        System.out.println("Lixeira cadastrada");
    }
    
    private void capacidadeAtual(String ident, String vl){
        control.capacidadeAtual(ident, Double.parseDouble(vl));
    }
    
    private void caminhaoConcertar(String ident){
        control.caminhaoConcertar(ident);
        System.out.println("Caminhao " + ident + " concertado");
    }
    
    private void caminhaoQuebrar(String ident ) throws UnknownHostException, IOException{
        control.caminhaoQuebrar(ident);
        System.out.println("Caminhao " + ident + " quebrou");         
       
        multEnviar.multicast("B%SOCORRO%" + IP);//Enviando o IP do Localhost
                
    }
}
