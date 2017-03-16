package maw_server;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;
import processing.data.XML;
import serial.Usuario;

public class Logica implements Observer {

	private PApplet app;
	private Comunicacion c;
	private DatabaseManager dM;
	private ArrayList<Usuario> usuarios;

	public Logica(PApplet app) {
		this.app = app;
		c = new Comunicacion();
		Thread nt = new Thread(c);
		nt.start();

		c.addObserver(this);

		dM = new DatabaseManager("data/databaseInfo.xml");

		usuarios = dM.getUsuarios();

	}

	public void ejecutar() {

		for (int i = 0; i < usuarios.size(); i++) {
			app.text(" Usuario: " + usuarios.get(i).getUsuario() + " Contraseña: " + usuarios.get(i).getPassword(), 15, 30 + (30 * i));
		}

	}

	public void exit() {
		System.out.println("Bye bye hasta otro dia");
		dM.guardar();
	}

	@Override
	public void update(Observable o, Object arg) {

	}

}
