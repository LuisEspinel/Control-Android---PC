package logico;

import java.io.DataInputStream;
import java.io.IOException;

import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

public class ServerBluetooth{
	private LocalDevice bluetooth;
	private final String ID="0000110100001000800000805F9B34FB";
	private final String URL="btspp://localhost:" + ID + ";name=Server";
	private StreamConnectionNotifier server;
	private StreamConnection connection;
	private boolean correr;
	private Ejecutar ejecutar;
	
	public ServerBluetooth(){
		try{			
			bluetooth=LocalDevice.getLocalDevice();			
			if(LocalDevice.isPowerOn()) System.out.println("Dis On");
			System.out.println(bluetooth.getBluetoothAddress());
			bluetooth.setDiscoverable(DiscoveryAgent.GIAC);	
			correr=true;
			ejecutar=new Ejecutar();
			
		}catch(IOException e){
			System.out.println("HUBO PROBLEMAS AL ACCEDER AL DISPOSITIVO");
		}
		
	}
		
	public void start(){
		try{
			while(correr){				
				server = (StreamConnectionNotifier) Connector.open(URL);
				System.out.println("Esperando conexion Bluetooth");
				connection = server.acceptAndOpen(); 
				System.out.println("Conexion establecida..");
				recibirDatos();
			}			
		}catch(IOException e){
			System.out.println("Error al iniciar el server");			
		}
	}
	
	public void recibirDatos(){
		try{
			DataInputStream dis = connection.openDataInputStream();
			while(true){											
				int numero=dis.readInt();
				if(numero != 100){
					ejecutar.ejecutar(numero);
				}else {
					dis.close();
					cerrarConexion();
					break;
				}
			}
		}catch(IOException e){
			System.out.println("Error al leer datos de entrada "+e.getMessage());
			e.printStackTrace();
			cerrarConexion();
		}			
	}
	
	public void cerrarConexion(){
		try{
			server.close();
			connection.close();				
			System.out.println("Se cerro conexion");
		}catch(IOException e){
			System.out.println("Error al cerrar las conexiones "+e.getMessage());
		}
		
	}
}
