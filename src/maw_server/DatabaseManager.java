package maw_server;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import processing.data.XML;
import serial.Usuario;

public class DatabaseManager {

	private XML data;
	private File archivo;
	private ArrayList<Usuario> usuarios;

	public DatabaseManager(String ruta) {

		archivo = new File(ruta);
		usuarios = new ArrayList<Usuario>();

		if (archivo.exists() && archivo.isFile()) {
			try {
				data = new XML(archivo);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			}
		} else {
			// Aqui iria algo >:V Si supiera hacerlo
		}
	}

	public void agregarUsuario(Object o) {
		if (o instanceof Usuario) {
			XML users = data.getChild("usuarios");
			XML[] users_all = users.getChildren();

			Usuario u = (Usuario) o;

			String name = u.getUsuario();
			String pass = u.getPassword();
			int id = users_all[users_all.length - 1].getInt("id") + 1;

			XML newUser = users.addChild("user");

			newUser.setInt("id", id);
			newUser.setString("nombre", name);
			newUser.setString("password", pass);

			usuarios.add(new Usuario(name, pass));
		}

	}

	public void guardar() {
		data.save(archivo);
	}

	public ArrayList<Usuario> getUsuarios() {
		XML users = data.getChild("usuarios");
		XML[] users_all = users.getChildren("user");

		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

		if (usuarios.isEmpty()) {
			for (int i = 0; i < users_all.length; i++) {
				String name = users_all[i].getString("nombre");
				String pass = users_all[i].getString("password");
				usuarios.add(new Usuario(name,pass));
			}
		}

		return usuarios;
	}

}
