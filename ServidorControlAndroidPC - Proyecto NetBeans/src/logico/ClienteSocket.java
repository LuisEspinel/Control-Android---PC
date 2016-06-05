package logico;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClienteSocket {
	private Socket cliente;
	private DataInputStream entrada;
	private DataOutputStream salida;
	private String idServer;
	private int puerto;
	
	public ClienteSocket(String ip,int puerto) throws IOException{
		idServer=ip;
		this.puerto=puerto;
	}
	
	public void establecerComunicacion(){
		try{
			cliente=new Socket(idServer,puerto);
			entrada=new DataInputStream(cliente.getInputStream());
			salida=new DataOutputStream(cliente.getOutputStream());
			salida.writeUTF("Hola");
			String rta=entrada.readLine();
			salida.close();
			entrada.close();
			cliente.close();
			System.out.println("El servidor contesto :"+rta);
		}
		catch(UnknownHostException e){	System.out.println("Servidor no disponible"); }
		catch(IOException e){ System.out.println(e.getMessage()); }
		catch(Exception e){ System.out.println(e.getMessage()); }		
	}
	
	
}
