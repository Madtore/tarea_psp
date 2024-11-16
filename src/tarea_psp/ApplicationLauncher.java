package tarea_psp;

import java.io.IOException;

public class ApplicationLauncher {
	
	public void openApplication(String applicationPatch) {
		ProcessBuilder processBuilder = new ProcessBuilder(applicationPatch);
		
		try {
			processBuilder.start();
		} catch(IOException e) {
			System.out.println("Error al iniziar la aplicación " + e.getMessage());
		}
	}

}
