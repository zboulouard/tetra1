package com.tetra.test1.business;

import java.util.List;

import com.tetra.test1.dao.LectureFichier;

public class TetraTraitement {
	
	public TetraTraitement() {
		
		LectureFichier lectureFichier = new LectureFichier();
		
		lectureFichier.lireFichier();
		
		filtreMatrice(lectureFichier.getListeAuteurs(), lectureFichier.getMatriceInt());
		
	}

	private void filtreMatrice(List<String> auteurs, List<Integer[]> matriceInt) {
		
		for(int i=0; i<auteurs.size(); i++) {
			Integer[] vect = matriceInt.get(i);
			for(int j=0; j<vect.length-1; j++) {
				if(i==j) {
					continue;
				}
				if(vect[j]==null || vect[j]==0) {
					continue;
				} 
				else {
					System.out.println("Auteur : " + auteurs.get(i) + " et auteur : " + auteurs.get(j) + " ont collaboré " + vect[j] + " fois.");
				}
			}
		}
		
	}

}
