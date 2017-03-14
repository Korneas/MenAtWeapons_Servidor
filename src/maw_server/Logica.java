package maw_server;

import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;
import processing.data.XML;

public class Logica implements Observer {

	private PApplet app;
	private Comunicacion c;
	private XML usuarios;
	private int idMax;

	public Logica(PApplet app) {
		this.app = app;
		usuarios = app.loadXML("../data/usuarios.xml");
		c = new Comunicacion();
		Thread nt = new Thread(c);
		nt.start();

		c.addObserver(this);
	}

	public void ejecutar() {
		XML[] profile = usuarios.getChildren("user");

		for (int i = 0; i < profile.length; i++) {
			int userID = profile[i].getInt("id");
			String nombre = profile[i].getString("nombre");
			String pass = profile[i].getString("password");
			System.out.println(userID + " Usuario: " + nombre + " Contraseña: " + pass);
		}

		idMax = profile[profile.length - 1].getInt("id");

		if (app.mousePressed) {
			int id = idMax + 1;
			String name = "Carlos";
			String pass = "carlitos123";

			XML newUser = usuarios.addChild("user");

			newUser.setInt("id", id);
			newUser.setString("nombre", name);
			newUser.setString("password", pass);
		}
	}

	public void exit() {
		app.saveXML(usuarios, "../data/usuarios.xml");
	}

	@Override
	public void update(Observable o, Object arg) {

	}

}
