package maw_server;

import processing.core.PApplet;

public class Main extends PApplet {
	private Logica log;

	public static void main(String[] args) {
		System.setProperty("java.net.preferIPv4Stack", "true");
		PApplet.main("maw_server.Main");
	}

	@Override
	public void settings() {
		size(300, 500);
	}

	@Override
	public void setup() {
		log = new Logica(this);
	}

	@Override
	public void draw() {
		background(0);
		log.ejecutar();
	}
	
	@Override
	public void exit(){
		log.exit();
		super.exit();
	}
	
	
}
