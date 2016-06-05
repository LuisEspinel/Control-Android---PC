package co.com.espinel.luis.pruebasocket;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

/**
 * Created by luis on 21/05/2016.
 */
public class ClienteBluetooth extends Thread{
    private BluetoothAdapter bluetooth;
    public BluetoothSocket cliente;
    private Set<BluetoothDevice> dispositivos;
    private final String IDENTIFICADOPR_SERVICIO="00001101-0000-1000-8000-00805F9B34FB";
    public boolean corriendo;

    public ClienteBluetooth(){
        cliente=null;
        bluetooth=BluetoothAdapter.getDefaultAdapter();
        corriendo=false;
        getDevices();
    }

    public BluetoothAdapter getBluetoothAdapter(){
        return bluetooth;
    }

    public void getDevices(){
        bluetooth.cancelDiscovery();
        dispositivos=bluetooth.getBondedDevices();
    }

    // Retorna los nombres de los dispositivos emparejados
    public ArrayList<String> getNombresDevice(){
        ArrayList<String> nombres=new ArrayList<>();
        if(dispositivos.size() > 0){
            for(BluetoothDevice device : dispositivos)
                nombres.add(device.getName());
        }else nombres.add("NO HAY DISPOSITIVOS EMPAREJADOS");
        return nombres;
    }

    public boolean setServidor(String nombreDevice){
        BluetoothDevice dispositivoServidor=getDeviceByName(nombreDevice);
        if(dispositivoServidor != null){
            try{
                cliente=dispositivoServidor.createInsecureRfcommSocketToServiceRecord(UUID.fromString(IDENTIFICADOPR_SERVICIO));
                return true;
            }catch(Exception e){
                return false;
            }
        }
        return false;
    }

    // Retorna el dispositivo con el nombre name
    public BluetoothDevice getDeviceByName(String name){
        for(BluetoothDevice device : dispositivos){
            if(device.getName().equals(name))
                return device;
        }
        return null;
    }

    public boolean enviarDatos(int datos){
        if(corriendo){
            try{
                DataOutputStream salida=new DataOutputStream(cliente.getOutputStream());
                salida.writeInt(datos);
                return true;
            }catch (Exception e){ return false; }
        }
        return false;
    }

    @Override
    public void run(){
        try{
            cliente.connect();
            corriendo=true;
        }catch(Exception e){
            corriendo=false;
        }
    }

}
