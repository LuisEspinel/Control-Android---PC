package logico;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorSocket{	
	private int puerto;	
	private ServerSocket server;	
	private Socket socket;
	private ObjectInputStream entrada;
	private Ejecutar ejecutar;	
	
	public ServidorSocket(int puerto)throws IOException{		
		this.puerto=puerto;
		server=new ServerSocket(puerto);
		socket=null;
		ejecutar=new Ejecutar();
	}
	
	public void start(){
		try{
			while(true){
				System.out.println("Esperando Conexion ");
				socket=server.accept();
				System.out.println("Conexion Establecida con  "+socket.getInetAddress().toString()+":"+socket.getPort());
				if( !socket.isClosed() && socket.isConnected())
					recibirDatos();	
			}					
		}catch(IOException e){
			e.printStackTrace();
		}				
		catch(Exception e){ e.printStackTrace(); }
	}
	
	public int getPuerto() {
		return puerto;
	}
	
	public void recibirDatos(){
		try{
			while(true){
				if(socket.isConnected() && ! socket.isClosed()){
					entrada=new ObjectInputStream(socket.getInputStream());
					Object object=entrada.readObject();
					if(object instanceof String){				
						String objectMensaje=(String) object;
						int orden=Integer.valueOf(objectMensaje);
						if(orden != 100)
							ejecutar.ejecutar(orden);
						else cerrarConexion();
					}				
				}else{
					System.out.println("No hay ninguna conexion");					
					return;
				}
			}
			
		}catch(IOException e){
			e.printStackTrace();
		}				
		catch(Exception e){ e.printStackTrace(); }		
	}
	
	public void cerrarConexion(){
		try{
			if(socket == null) return;
			if(socket.isConnected())
				socket.close();
			System.out.println("Se cerro el socket.");
		}catch(IOException e){}
		
	}
		
}


