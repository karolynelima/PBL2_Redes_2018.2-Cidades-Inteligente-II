/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.SC.connection;

import br.com.SC.controller.Controller;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author karolyne
 */

//Essa é a classe responsável por criar novas Threads do servidor UDP
public class ServerUDP implements Runnable{
    private final DatagramSocket servidor; //atributo responsável por estabelecer a conexão UDP
    private byte[] recebe = new byte[2048];//Array para receber a informação da conexão
    private final Controller ctrl; //Atributo que recebe o controlador
    private MulticastPublisher multEnviar;
    private MulticastReceiver multReceber;           
            
            
    //Construtor 
    public ServerUDP(int porta, Controller ctrl) throws SocketException, UnknownHostException, IOException {
        servidor = new DatagramSocket(porta);//Abre uma coneão UDP para uma determinada porta
        this.ctrl = ctrl;
        System.out.println("UDP: Ouvindo a porta "+porta);
        multEnviar = new MulticastPublisher();
        multEnviar.multicast(InetAddress.getLocalHost().getHostAddress());//Enviando o IP do Localhost
        System.out.println(InetAddress.getLocalHost().getHostAddress());
        multReceber = new MulticastReceiver(ctrl);//Adicionando nova borda à lista
        multReceber.run();
        new Thread(this).start();//Criando e iniciando a thread principal
    }

    @Override
    public void run() {
        while (!servidor.isClosed()) {//Laço de repetição para a criação de várias threads a medida que receber novas conexões
            try {
                DatagramPacket p = new DatagramPacket(recebe, recebe.length);//Cria um pacote pra armazenar informações advindas do cliente
                System.out.println("Esperando conexão UDP");
                servidor.receive(p);//Espera a conexão
                new AtividadeServidor(servidor, p, ctrl).start();//Cria uma thread do tipo AtividadeServidor que irá tratar as informações recebidas do cliente
                System.out.println("Cliente UDP conectado!");
            } catch (IOException ex) {
                System.out.println("Pacote não recebido!");
            }
        }
    }
    
    //Metodo que "fecha" a conexão do servidor UDP
    public void stop() throws IOException{
        //servidor.disconnect();
        servidor.close();        
    }
    
}
