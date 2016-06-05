package grafico;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Event;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingWorker;

import logico.ServerBluetooth;
import logico.ServidorSocket;
import servidorcontrolandroidpc.ServidorControlAndroidPC;


public final class VentanaPrincipal extends JFrame implements ActionListener {
	private ServerBluetooth serverBluetooth;
	private ServidorSocket servidorSocket;
	private JPanel panel;
	private JLabel labelTitulo;
	private JButton btnIniciar,btnSalir,btnBluetooth;
	private String texto="<html>"
			+ "<head>"
			+ "<h3>OPCIONES PARA EL INICIO DE CONTROL ANDROID-PC</h3>"
			+ "</head>"
			+ "<body><center>"
			+ "<h5>Asegurese de Iniciar primero uno de estos servidores (Internet o Bluetooth).</h5>"
			+ "</center></body>"
			+ "</html>";
	private final JPopupMenu menuEmergente;
	private JMenuItem mostrar,salir;
	private TrayIcon trayIcon;	
	private SystemTray barraTareas;

	public VentanaPrincipal(){
		super("Servidor CONTROL ANDROID-PC");
		super.setSize(500,250);
		super.setLocation(400,200);
		super.setResizable(false);
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		menuEmergente=new JPopupMenu();
		mostrar=new JMenuItem("Mostrar.");
		mostrar.addActionListener(this);
		salir=new JMenuItem("Salir.");
		salir.addActionListener(this);
		menuEmergente.add(mostrar);
		menuEmergente.add(salir);
		
                Image image = Toolkit.getDefaultToolkit().getImage("logo.png");
		//trayIcon=new TrayIcon(Toolkit.getDefaultToolkit().getImage(ServidorControlAndroidPC.class.getResource("logo.png")));	                
		trayIcon=new TrayIcon(image,"Control Android - PC");
                trayIcon.setImageAutoSize(true);
		trayIcon.addMouseListener(new MouseAdapter() {
			@Override
		    public void mouseReleased(MouseEvent e) {
		     menuEmergente.setVisible(true);
		     menuEmergente.setLocation(e.getX(), e.getY()-80);
		    }
		});
		
		barraTareas = SystemTray.getSystemTray();
		
		this.addWindowListener(new WindowListener() {			
			@Override
			public void windowOpened(WindowEvent arg0) {
				
			}
			
			@Override
			public void windowIconified(WindowEvent arg0) {				
				try {
					barraTareas.add(trayIcon);
				} catch (AWTException e) {					
					e.printStackTrace();
				}				
				setVisible(false);
			}
			
			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
								
			}
			
			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		labelTitulo=new JLabel(texto);
		labelTitulo.setBounds(50,1,450,100);
		
		btnIniciar=new JButton("INICIAR SERVER INTERNET");
		btnIniciar.setBounds(30,100,200,20);		
		btnIniciar.addActionListener(this);
		
		btnBluetooth=new JButton("INICIAR SERVER BLUETOOTH");
		btnBluetooth.setBounds(250,100,200,20);
		btnBluetooth.addActionListener(this);
		
		btnSalir=new JButton("SALIR");
		btnSalir.setBounds(190,150,100,20);
		btnSalir.addActionListener(this);
		
		panel=new JPanel(null);
		panel.add(labelTitulo);
		panel.add(btnIniciar);
		panel.add(btnBluetooth);
		panel.add(btnSalir);
		
		super.getContentPane().add(panel);
	}	
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnIniciar){
			final SwingWorker worker = new SwingWorker(){			 
				@Override
				protected Object doInBackground() throws Exception {
					servidorSocket=new ServidorSocket(11111);
					servidorSocket.start();													
					return null;
				}	
			};
			worker.execute();		
			btnIniciar.setText("SERVIDOR ESCUCHANDO");	
			btnIniciar.setEnabled(false);
		}
		if(e.getSource() == btnBluetooth){
			final SwingWorker worker = new SwingWorker(){			 
				@Override
				protected Object doInBackground() throws Exception {
					ServerBluetooth server=new ServerBluetooth();					
					server.start();					
					return null;
				}	
			};
			worker.execute();
			btnBluetooth.setText("BLUETOOTH ESCUCHANDO");
			btnBluetooth.setEnabled(false);
		}
		if(e.getSource() == btnSalir || e.getSource() == salir)System.exit(0);
		if(e.getSource() == mostrar) {
			this.setVisible(true);
			menuEmergente.setVisible(false);
			if(barraTareas != null)
				barraTareas.remove(trayIcon);							
		}
	}
	
	
}
