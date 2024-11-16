package tarea_psp;

import java.io.IOException;

import javax.swing.JOptionPane;

public class BrowserLauncher {

	public void openBrowserWithUrl(String url) {
		ProcessBuilder processBuilder = new ProcessBuilder("rundll32", "url.dll,FileProtocolHandler", url);
		
		try {
			Process process = processBuilder.start();
			
			int exitCode = process.waitFor();
	        
	        if (exitCode == 0) {
	            new JOptionPane();
				JOptionPane.showMessageDialog(null, "Navigador abierto con exito");
	        } else {
	        	new JOptionPane();
				JOptionPane.showMessageDialog(null,"Errore nell'aprire il browser. Codice di uscita: " + exitCode, "ERROR", JOptionPane.WARNING_MESSAGE  );
	        }
	    } catch (IOException e) {
	    	new JOptionPane();
			JOptionPane.showMessageDialog(null,"Errore nell'eseguire il comando: " + e.getMessage(),"ERROR", JOptionPane.WARNING_MESSAGE  );
	        
	    } catch (InterruptedException e) {
	    	new JOptionPane();
			JOptionPane.showMessageDialog(null,"Il processo è stato interrotto: " + e.getMessage(),"ERROR", JOptionPane.WARNING_MESSAGE  );
	    }
	}
}
