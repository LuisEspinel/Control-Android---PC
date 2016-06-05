package co.com.espinel.luis.pruebasocket;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    public ClienteSocket cliente;
    private EditText etIp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etIp=(EditText)findViewById(R.id.etIP);

    }

    public boolean iniciarSocket(){
        String ip=etIp.getText().toString();
        if(ip.trim().length() == 0 || ip.equals("")){
            Toast.makeText(this,"Ingrese la IP del pc a controlar",Toast.LENGTH_SHORT).show();
            return false;
        }else{
            cliente=new ClienteSocket(ip,11111);
            cliente.start();
            return true;
        }
    }

    public void iniciarRed(View view){
        try{
            if(iniciarSocket()){
                Toast.makeText(this,"Por favor espere...",Toast.LENGTH_SHORT).show();
                Thread.sleep(5000);
                if(cliente.socket.isConnected()){
                    Comunicador.setObjectSocket(cliente);
                    Intent intent=new Intent(this,SecondActivity.class);
                    intent.putExtra("MODO","RED");
                    Toast.makeText(this,"Conectado",Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }else Toast.makeText(this,"No se pudo conectar",Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(this,"Algo inesperado paso en la conexion",Toast.LENGTH_SHORT).show();
        }

    }

    public void iniciarBluetooth(View view){
        Intent intent=new Intent(this,BluetoothActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.about) {
            Intent intent=new Intent(this,AboutActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
