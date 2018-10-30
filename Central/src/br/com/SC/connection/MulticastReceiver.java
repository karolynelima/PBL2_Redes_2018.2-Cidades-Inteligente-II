/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.SC.connection;

import br.com.SC.controller.Controller;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author karolyne
 */

public class MulticastReceiver extends Thread {
    protected MulticastSocket socket = null;
    protected byte[] buf = new byte[256];
    private Controller control;
    private String IP;
    private ObjectOutputStream out;

    public MulticastReceiver(Controller control) throws UnknownHostException{
        this.control = control;
        IP = InetAddress.getLocalHost().getHostAddress();
    }
    
    @Override
    public void run() {
        try {
            socket = new MulticastSocket(4446);
            InetAddress group = InetAddress.getByName("230.0.0.0");
            socket.joinGroup(group);
            //socket.setSoTimeout(10000);
            while (true) {
                System.out.println("entrou no while");
                
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);                
                String received = new String(packet.getData(), 0, packet.getLength());
                tratarConec(received);
              
                System.out.println(received);                
                //control.addBorda(received);
                if ("end".equals(received)) {
                    break;
                }
            }
            socket.leaveGroup(group);
            socket.close();
        }catch (IOException ex) {
            Logger.getLogger(MulticastReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public byte[] buffer(){
        return buf;
    }
    /*
    quando receber mensagem de socorro de outra central, 
    analisar se o ID q recebeu n é o mesmo que o da central atual
    */
    private void tratarConec(String receiver) throws UnknownHostException, IOException{
        String array[] = receiver.split("%");        
        switch (array[0]){
            case "B":
                switch (array[1]){
                    case "SOCORRO":
                        if(!IP.equals(array[2])){
                            MulticastPublisher resposta = new MulticastPublisher();
                            if(control.temCam()){
                                resposta.multicast("B%CONFIRMA%" + IP + "%1234%");
                            }
                        }
                        break;
                    case "CONFIRMA":
                        Socket socket = new Socket(array[2],Integer.parseInt(array[3]));
                        out = new ObjectOutputStream(socket.getOutputStream());
                        out.writeObject("B%CRIANDO%CONEXÃO%TCP" + control.gerarRota() + "%" );               
                        break;
                }
        }        
    }
}

