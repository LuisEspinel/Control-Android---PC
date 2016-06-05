package co.com.espinel.luis.pruebasocket;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by luis on 17/05/2016.
 */



public class ClienteSocket extends Thread {
    public Socket socket;
    private String idServer;
    private int puerto;
    private ObjectOutputStream flujoSalida;

    public ClienteSocket(String idServer,int puerto){
        this.idServer=idServer;
        this.puerto=puerto;
    }

    @Override
    public void run(){
        try {
            socket = new Socket(idServer, puerto);
        }
        catch(UnknownHostException e){  }
        catch(IOException e){ }
    }

    public boolean enviarDatos(String texto){
        try{
            if(socket.isConnected()){
                flujoSalida=new ObjectOutputStream(socket.getOutputStream());
                flujoSalida.writeObject(texto);
                return true;
            }
            return false;
        }catch(IOException e){ return false; }
    }

}
