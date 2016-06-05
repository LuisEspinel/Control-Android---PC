package co.com.espinel.luis.pruebasocket;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by luis on 17/05/2016.
 */
public class SecondActivity extends AppCompatActivity {
    private ClienteSocket clienteRed;
    private ClienteBluetooth clienteBluetooth;
    private final int ARRIBA=1;
    private final int ABAJO=2;
    private final int DERECHA=3;
    private final int IZQUIERDA=4;
    private final int ENTER=5;
    private final int ATRAS=6;
    private final int EXPLORADOR=7;
    private final int CERRAR_VENTANA=8;
    private final int APAGAR=9;
    private final int CERRAR_SESION=10;
    private final int VOLUMEN =11;
    private final int F5=13;
    private final int ESCAPE=12;
    private final int TERMINAR_CONEXION=100;
    private String modo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Bundle bundle=getIntent().getExtras();
        modo=bundle.getString("MODO");
        setClienteSocket();
    }

    public void setClienteSocket(){
        if(modo.equals("RED"))
            clienteRed=(ClienteSocket)Comunicador.getObjetoSocket();
        else
            clienteBluetooth=(ClienteBluetooth)Comunicador.getObjetoSocket();

    }

    public void ejecutarAccion(View view){
        Button boton=(Button) view;
        String texto="";
        switch(boton.getId()){
            case R.id.btnArriba:
                texto+=ARRIBA;
                break;
            case R.id.btnAbajo:
                texto+=ABAJO;
                break;
            case R.id.btnDerecha:
                texto+=DERECHA;
                break;
            case R.id.btnIzquierdo:
                texto+=IZQUIERDA;
                break;
            case R.id.btnEnter:
                texto+=ENTER;
                break;
            case R.id.btnAtras:
                texto+=ATRAS;
                break;
            case R.id.btnExp:
                texto+=EXPLORADOR;
                break;
            case R.id.btnCerrarVentana:
                texto+=CERRAR_VENTANA;
                break;
            case R.id.btnApagar:
                texto+=APAGAR;
                break;
            case R.id.btnSesion:
                texto+=CERRAR_SESION;
                break;
            case R.id.btnVolumen:
                texto+= VOLUMEN;
                break;
            case R.id.btnEscape:
                texto+=ESCAPE;
                break;
            default:
                break;
        }
        if(modo.equals("RED")){
            if(!clienteRed.enviarDatos(texto)){
                Toast.makeText(this,"AL PARECER SE PERDIO LA CONEXION...",Toast.LENGTH_SHORT).show();
            }
        }else{
            if(!clienteBluetooth.enviarDatos(Integer.valueOf(texto))){
                Toast.makeText(this,"AL PARECER SE PERDIO LA CONEXION...",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onKeyDown(int codigo,KeyEvent event){
        try{
            if(codigo == KeyEvent.KEYCODE_BACK){
                Log.d("Luis","Saliendo");
                if(modo.equals("RED"))
                    clienteRed.enviarDatos(String.valueOf(TERMINAR_CONEXION));
                else
                    clienteBluetooth.enviarDatos(TERMINAR_CONEXION);
            }
        }catch(Exception e){}
        return super.onKeyDown(codigo,event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_second, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.f5) {
            if(modo.equals("RED"))
                clienteRed.enviarDatos(String.valueOf(F5));
            else
                clienteBluetooth.enviarDatos(F5);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
