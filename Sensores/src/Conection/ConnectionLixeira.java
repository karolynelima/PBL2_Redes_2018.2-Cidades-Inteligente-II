/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author kkaro
 */
public class ConnectionLixeira {
    private DatagramSocket serverUDP; //Objeto responsável pela conexão com o servidor
    private byte[] saidaUDP; //Objeto que envia informação para o servidor
    private final byte[] entradaUDP = new byte[1024]; //Objeto que recebe informação do servidor
    private InetAddress end;
    
    private String adress = "127.0.0.1";
    private int porta = 1234;

    public ConnectionLixeira() throws UnknownHostException {
        this.end = InetAddress.getByName(adress);//Pega o endereço do host de conexão
    }
   
    public ConnectionLixeira(String adress, int porta) throws IOException {
        this.end = InetAddress.getByName(adress);//Pega o endereço do host de conexão
        this.porta = porta;//Seta a porta numa variavel global
        
    }
    
    //Metodo responsavel por fazer conexão com servidor
    public void conectar() throws IOException{
        serverUDP = new DatagramSocket();
    }
    
    public String cadLixeira(String ident, double vl, double capac) throws IOException, ClassNotFoundException{
        saidaUDP = ("L%CADASTRO%" + ident + "%" + vl + "%" + capac + "%" ).getBytes(); //ESCREVEMOS OS DADOS NO OUTPUTSTREAM (ISSO BASTA PARA TRANSMITIR)        
        DatagramPacket enviar = new DatagramPacket(saidaUDP, saidaUDP.length, end, porta);
        serverUDP.send(enviar);
        
        return new String(enviar.getData(),0,enviar.getLength()); // retorna informação pra View
    }
    
    public String capacidadeTotal(String ident, double capac) throws IOException, ClassNotFoundException{
        saidaUDP = ("L%TOTAL%CAPACIDADE%" + ident + "%" + capac + "%").getBytes(); //ESCREVEMOS OS DADOS NO OUTPUTSTREAM (ISSO BASTA PARA TRANSMITIR)                
        DatagramPacket enviar = new DatagramPacket(saidaUDP, saidaUDP.length, end, porta);
        serverUDP.send(enviar);
        
        return new String(enviar.getData(),0,enviar.getLength()); // retorna informação pra View
    }
    
    public String capacidadeAtual(String ident, double vl) throws IOException, ClassNotFoundException{
        saidaUDP = ("L%ATUAL%CAPACIDADE%" + ident + "%" + vl + "%").getBytes(); //ESCREVEMOS OS DADOS NO OUTPUTSTREAM (ISSO BASTA PARA TRANSMITIR)        
        DatagramPacket enviar = new DatagramPacket(saidaUDP, saidaUDP.length, end, porta);
        serverUDP.send(enviar);
        
        return new String(enviar.getData(),0,enviar.getLength()); // retorna informação pra View
    }
}
