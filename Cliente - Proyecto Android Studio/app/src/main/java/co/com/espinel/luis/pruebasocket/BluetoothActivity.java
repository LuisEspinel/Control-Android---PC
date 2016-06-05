package co.com.espinel.luis.pruebasocket;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by luis on 21/05/2016.
 */
public class BluetoothActivity extends AppCompatActivity {
    private ClienteBluetooth clienteBluetooth;
    private ListView lista;
    private ArrayAdapter adapter;
    private ArrayList<String> nameDevices;
    private final int RESPUESTA_BLUETOOTH=500;
    private ProgressDialog dialogoProgreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetooth_layout);

        loadBlutooth();
        if(clienteBluetooth.getBluetoothAdapter() == null)
            Toast.makeText(this,"EL DISPOSITIVO NO TIENE BLUETOOTH",Toast.LENGTH_SHORT).show();
        else{
            Intent intentHabilitarBluetooth=new Intent(clienteBluetooth.getBluetoothAdapter().ACTION_REQUEST_ENABLE);
            startActivityForResult(intentHabilitarBluetooth,RESPUESTA_BLUETOOTH);
        }
        loadList();
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialogoProgreso=ProgressDialog.show(BluetoothActivity.this,"CONECTANDO","Intentando Conectar con "+nameDevices.get(position),true,false);
                new ClaseCargar().execute(nameDevices.get(position));
            }
        });
    }

    public void loadBlutooth(){
        clienteBluetooth=new ClienteBluetooth();
    }

    public void loadList(){
        nameDevices=clienteBluetooth.getNombresDevice();
        lista=(ListView) findViewById(R.id.lvDispositivos);
        adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,nameDevices);
        lista.setAdapter(adapter);
    }

    public boolean conectar(String nombre){
        if(nameDevices.size() == 1 && nameDevices.get(0).equals("NO HAY DISPOSITIVOS EMPAREJADOS")){
            return false;
        }else{
            try{
                if(clienteBluetooth.setServidor(nombre)){
                    if(!clienteBluetooth.corriendo){
                        clienteBluetooth.start();
                    }
                }
                try{
                    Thread.sleep(15000);
                }catch(InterruptedException e){  }
                if(clienteBluetooth.corriendo)
                    return true;
                else
                    clienteBluetooth=new ClienteBluetooth();
                return false;
            }catch(Exception e){
                return false;
            }
        }
    }

    public void lanzarActivity(){
        Intent intent=new Intent(BluetoothActivity.this,SecondActivity.class);
        intent.putExtra("MODO","BLUETOOTH");
        Comunicador.setObjectSocket(clienteBluetooth);
        startActivity(intent);
    }

    public void mensajeNoConexion(){
        Toast.makeText(this,"NO SE PUDO CONECTAR CON EL DISPOSITIVO",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RESPUESTA_BLUETOOTH){
            if(resultCode == RESULT_OK)
                loadList();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private class ClaseCargar extends AsyncTask{
        @Override
        protected Object doInBackground(Object[] params) {
            String nombre=(String)params[0];
            if(conectar(nombre)){
                dialogoProgreso.dismiss();
                lanzarActivity();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            if(!clienteBluetooth.corriendo)
                mensajeNoConexion();
            if(dialogoProgreso != null)
                dialogoProgreso.dismiss();
        }
    }
}
