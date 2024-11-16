package tarea_psp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UrlManager {

	private String filePath = "url.txt";
	
	public void setUrl(File directorioSelecionado) {
		this.filePath = directorioSelecionado+ "\\urls.txt";
	}

	public void initializeFile() {
		File file = new File(filePath);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.out.println("Error al crear el archivo de URLs: " + e.getMessage());
			}
		}
	}

	public void saveUrl(String url) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
			bw.write(url);
			bw.newLine();
		} catch (IOException e) {
			System.out.println("Error al guardar la url : " + e.getMessage());
		}
	}
	
	private void actualizarUrls(Set<String> urls) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
			
			for(String u: urls) {
				bw.write(u);
				bw.newLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error al leer el archivo de URLs: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Error al leer el archivo de URLs: " + e.getMessage());
		}
	}

	public Set<String> loadUrls() {
		Set<String> urls = new HashSet<String>();

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				urls.add(line);
			}

		} catch (FileNotFoundException e) {
			System.out.println("Error al leer el archivo de URLs: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Error al leer el archivo de URLs: " + e.getMessage());
		}

		return urls;
	}

	public void eliminaUrl(String url) {
		Set<String> urls = loadUrls();
		
		if(urls.remove(url)){
			actualizarUrls(urls);
		}
		
	}

}
