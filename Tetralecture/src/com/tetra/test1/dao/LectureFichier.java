package com.tetra.test1.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LectureFichier {
	
	public void lireFichier() {
		
		double startTime = System.currentTimeMillis();
		
		BufferedReader br = null;
		
		List<String> listeAuteurs = new ArrayList<String>();
		 
		try {
 
			String sCurrentLine;
 
			br = new BufferedReader(new FileReader("src/com/tetra/test1/source/A-A.1"));
			
			sCurrentLine = br.readLine();
			
			System.out.println("*******************Lignes*******************");
			
			String[] nomsAuteurs = sCurrentLine.split(" ");
			
			for(String auteur : nomsAuteurs) {
				System.out.println(auteur);
				listeAuteurs.add(auteur);
			}
			
			System.out.println("*******************Colonnes*******************");
			
			while ((sCurrentLine = br.readLine()) != null) {
				String[] colonnes = sCurrentLine.split(" ");
				for(int i=1; i<colonnes.length; i++) {
					String contenu = colonnes[i] + " ";
					System.out.print(contenu);
				}
				System.out.println();
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		double endTime = System.currentTimeMillis();
		
		double executionTime = (endTime - startTime) / 1000;
		
		System.out.println(executionTime);
		
	}

}
