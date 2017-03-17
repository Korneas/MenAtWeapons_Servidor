package maw_server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;
import processing.data.XML;
import serial.Confirmacion;
import serial.Usuario;

public class Logica implements Observer {

	private PApplet app;
	private Comunicacion c;
	private DatabaseManager dM;
	private static String GROUP_ADDRESS;
	private String ANDROID_ADDRESS;

	private ArrayList<Usuario> usuarios;

	public Logica(PApplet app) {
		this.app = app;
		c = new Comunicacion();
		Thread nt = new Thread(c);
		nt.start();

		c.addObserver(this);

		dM = new DatabaseManager("data/databaseInfo.xml");

		GROUP_ADDRESS = c.getGroupAddress();

		usuarios = dM.getUsuarios();
	}

	public void ejecutar() {
		for (int i = 0; i < usuarios.size(); i++) {
			app.text(" Usuario: " + usuarios.get(i).getUsuario() + " Contraseña: " + usuarios.get(i).getPassword(), 15,
					30 + (30 * i));
		}

	}

	public void exit() {
		System.out.println("Bye");
		dM.guardar();
	}

	@Override
	public void update(Observable o, Object arg) {

		if (arg instanceof Usuario) {
			Usuario u = (Usuario) arg;
			dM.agregarUsuario(u);
			usuarios = dM.getUsuarios();
			ANDROID_ADDRESS = c.getAndroidAddress();
			ANDROID_ADDRESS = ANDROID_ADDRESS.replaceAll("/", "");
		}

		if (arg instanceof Confirmacion) {
			ANDROID_ADDRESS = c.getAndroidAddress();
			ANDROID_ADDRESS = ANDROID_ADDRESS.replaceAll("/", "");
			System.out.println("Android address es: "+ANDROID_ADDRESS);
			Confirmacion user = (Confirmacion) arg;
			if (!user.isConfirmado()) {
				for (int i = 0; i < usuarios.size(); i++) {
					if (user.getName().contentEquals(usuarios.get(i).getUsuario())
							&& user.getPassword().contentEquals(usuarios.get(i).getPassword())) {
						System.out.println("Esta registrado");
						try {
							c.enviar(new Confirmacion(user.getName(), user.getPassword(), true), ANDROID_ADDRESS);
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						try {
							c.enviar(new Confirmacion(user.getName(), user.getPassword(), false), ANDROID_ADDRESS);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

}
