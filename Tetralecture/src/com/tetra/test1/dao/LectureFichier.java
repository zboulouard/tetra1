package com.tetra.test1.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LectureFichier {

	List<String> listeAuteurs = new ArrayList<String>();
	List<Integer[]> matriceInt = new ArrayList<Integer[]>();

	public List<String> getListeAuteurs() {
		return listeAuteurs;
	}

	public List<Integer[]> getMatriceInt() {
		return matriceInt;
	}

	public void lireFichier() {

		BufferedReader br = null;
		List<String[]> matrice = new ArrayList<String[]>();
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader("src/com/tetra/test1/source/A-A.1"));
			sCurrentLine = br.readLine();
			String[] nomsAuteurs = sCurrentLine.split(" ");
			for(String auteur : nomsAuteurs) {
				listeAuteurs.add(auteur);
			}
			while ((sCurrentLine = br.readLine()) != null) {
				String[] colonnes = sCurrentLine.split(" ");
				for(int i=1; i<colonnes.length; i++) {
					colonnes[i] = colonnes[i] + " ";
				}
				matrice.add(colonnes);
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
		for(int i=0; i<matrice.size(); i++) {
			String[] v = matrice.get(i);
			Integer[] vInt = new Integer[v.length];
			for(int j=1; j<vInt.length; j++) {
				vInt[j] = Integer.parseInt(v[j].trim());
			}
			matriceInt.add(vInt);
		}
	}

}
