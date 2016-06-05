package co.com.espinel.luis.pruebasocket;

/**
 * Created by luis on 17/05/2016.
 */
public class Comunicador {
    private static Object objetoSocket;

    public static void setObjectSocket(Object object){ objetoSocket =object; }
    public static Object getObjetoSocket(){ return objetoSocket; }
}
