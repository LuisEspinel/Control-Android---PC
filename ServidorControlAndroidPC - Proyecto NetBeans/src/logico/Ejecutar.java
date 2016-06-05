package logico;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class Ejecutar {
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
    private final int VOLUMEN=11;
    private final int F5=13;
    private final int ESCAPE=12;    
    private Robot robot;
    
    public Ejecutar(){ 
    	try{
    		robot=new Robot();
    	}catch(AWTException e){}    	
    }
    
    public void ejecutar(int numero){
    	System.out.println("Orden "+numero);
    	Process proceso;
    	try{
    		switch(numero){
        	case ARRIBA:
        		robot.keyPress(KeyEvent.VK_UP);
        		break;
        	case ABAJO:
        		robot.keyPress(KeyEvent.VK_DOWN);
        		break;
        	case DERECHA:
        		robot.keyPress(KeyEvent.VK_LEFT);
        		break;
        	case IZQUIERDA:
        		robot.keyPress(KeyEvent.VK_RIGHT);
        		break;
        	case ENTER:
        		robot.keyPress(KeyEvent.VK_ENTER);
        		break;
        	case ATRAS:
        		robot.keyPress(KeyEvent.VK_BACK_SPACE);
        		break;
        	case EXPLORADOR:    		
        		proceso=Runtime.getRuntime().exec("explorer");
        		break;
        	case CERRAR_VENTANA:    	
        		robot.keyPress(KeyEvent.VK_ALT);
        		robot.keyPress(KeyEvent.VK_F4);
        		robot.keyRelease(KeyEvent.VK_ALT);        		        
        		break;
        	case APAGAR:    		
        		proceso=Runtime.getRuntime().exec("shutdown -s -t 0");
        		break;
        	case CERRAR_SESION:    		
        		proceso=Runtime.getRuntime().exec("logoff");
        		break;
        	case VOLUMEN:    		
        		proceso=Runtime.getRuntime().exec("sndvol -f");        		
        		break;
        	case ESCAPE:
        		robot.keyPress(KeyEvent.VK_ESCAPE);
        		robot.keyRelease(KeyEvent.VK_ESCAPE);
        		break; 
        	case F5:
        		robot.keyPress(KeyEvent.VK_F5);
        		robot.keyRelease(KeyEvent.VK_F5);
        		break;        		
        	default:        		
        		break;
        	}
    	}catch(Exception e){}
    	
    }
}
