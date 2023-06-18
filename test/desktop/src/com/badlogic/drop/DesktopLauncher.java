package com.badlogic.drop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.drop.DropPlus;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Drop");	//Titulo de la ventana
		config.setWindowedMode(800,480);	//Tamaño de la ventana
		config.useVsync(true);	//Si usa Vsync
		config.setForegroundFPS(60);	//FPS en los que funciona
		new Lwjgl3Application(new DropPlus(), config);
	}
}
