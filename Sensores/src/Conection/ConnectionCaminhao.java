/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conection;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author kkaro
 */
public class ConnectionCaminhao {
    //ENDEREÇO DA CENTRAL
    //PORTA
    //ESTABELECER CONEXÃO
    //PROTOCOLO quem ta enviando, o que é pra fazer
    
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String adress;
    private int porta;
       
    public ConnectionCaminhao(String adress, String port) throws IOException {
        this.adress = adress;
        this.porta = Integer.parseInt(port);
        socket = new Socket (adress,porta);
    }       
    
    public String conecta() throws IOException, ClassNotFoundException {
        out = new ObjectOutputStream(socket.getOutputStream()); // CRIAMOS OUTPUTSTREAM USANDO O MÉTODO DO SOCKET PARA ENVIAR DADOS
        out.writeObject("C%CONECTA%"); //ESCREVEMOS OS DADOS NO OUTPUTSTREAM (ISSO BASTA PARA TRANSMITIR)        
        in = new ObjectInputStream(socket.getInputStream());     
        
        return (String)in.readObject();
    }    
    
    public String cadCaminhao(String ident) throws IOException, ClassNotFoundException{
        socket = new Socket(adress, porta);
        out = new ObjectOutputStream(socket.getOutputStream()); // CRIAMOS OUTPUTSTREAM USANDO O MÉTODO DO SOCKET PARA ENVIAR DADOS
        out.writeObject("C%CADASTRO%" + ident + "%"); //ESCREVEMOS OS DADOS NO OUTPUTSTREAM (ISSO BASTA PARA TRANSMITIR)        
        in = new ObjectInputStream(socket.getInputStream());
        String resp = (String)in.readObject();
        out.close();
        in.close();
        
        return resp;//RETORNA O OBJETO RECEBIDO   
    }
    
    public String solicitarRota(String ident) throws IOException, ClassNotFoundException{
        socket = new Socket(adress, porta);
        out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject("C%SOLICITANDO%ROTA%" + ident + "%");
        in = new ObjectInputStream(socket.getInputStream());
        String resp = (String)in.readObject();
        out.close();
        in.close();
        
        return resp;//RETORNA O OBJETO RECEBIDO          
    }
    
    public String lixeiraColetada(String lixeiras) throws IOException, ClassNotFoundException{
        socket = new Socket(adress, porta);
        out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject("C%RECOLHEU%LIXEIRA%" + lixeiras + "%" );
        in = new ObjectInputStream(socket.getInputStream());
        String resp = (String)in.readObject();
        out.close();
        in.close();
        
        return resp;//RETORNA O OBJETO RECEBIDO   
    }
    
    public String rotaFinalizada(String ident) throws IOException, ClassNotFoundException{
        socket = new Socket(adress, porta);
        out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject("C%ROTA%FINALIZADA%" + ident + "%");
        in = new ObjectInputStream(socket.getInputStream());
        String resp = (String)in.readObject();
        out.close();
        in.close();
        
        return resp;//RETORNA O OBJETO RECEBIDO   
    }
    
    //MÉTODOS ADICIONADOS PARA O SEGUNDO PROBLEMA
    
    //Protocolo definido para identificar um caminhão que quebrou
    public void quebrar(String ident) throws IOException, ClassNotFoundException{
        socket = new Socket(adress, porta);
        out = new ObjectOutputStream(socket.getOutputStream()); // CRIAMOS OUTPUTSTREAM USANDO O MÉTODO DO SOCKET PARA ENVIAR DADOS
        out.writeObject("C%QUEBROU%" + ident + "%"); //ESCREVEMOS OS DADOS NO OUTPUTSTREAM (ISSO BASTA PARA TRANSMITIR)        
        //in = new ObjectInputStream(socket.getInputStream());     
        
        //return (String)in.readObject();
    }
    
    //Protocolo definido para identificar caminão que foi concertado
    public void consertar(String ident) throws IOException, ClassNotFoundException{
        socket = new Socket(adress, porta);
        out = new ObjectOutputStream(socket.getOutputStream()); // CRIAMOS OUTPUTSTREAM USANDO O MÉTODO DO SOCKET PARA ENVIAR DADOS
        out.writeObject("C%CONSERTADO%" + ident + "%"); //ESCREVEMOS OS DADOS NO OUTPUTSTREAM (ISSO BASTA PARA TRANSMITIR)        
//        in = new ObjectInputStream(socket.getInputStream());     
//        
//        return (String)in.readObject();
    }
    
    
    public String identificar(String ident) throws IOException, ClassNotFoundException{
        out = new ObjectOutputStream(socket.getOutputStream()); // CRIAMOS OUTPUTSTREAM USANDO O MÉTODO DO SOCKET PARA ENVIAR DADOS
        out.writeObject("C%IDENTIFICAR%" + ident + "%"); //ESCREVEMOS OS DADOS NO OUTPUTSTREAM (ISSO BASTA PARA TRANSMITIR)        
        in = new ObjectInputStream(socket.getInputStream());
        String resp = (String)in.readObject();
        out.close();
        in.close();
        
        return resp;//RETORNA O OBJETO RECEBIDO   
    }
    

    
}
