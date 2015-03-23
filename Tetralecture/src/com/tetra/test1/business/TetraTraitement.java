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
	
//	public void filtreMatrice(List<String> auteurs, List<Integer[]> matrice) {
//		
//		for(int i=0; i<matrice.size(); i++) {
//			Integer[] vect = matrice.get(i);
//			for(int j=1; j<vect.length; j++) {
//				if(vect[j] == 0) {
//					continue;
//				}
//				if(j==i) {
//					continue;
//				} else {
//					System.out.println(auteurs.get(i) + " et " + auteurs.get(j) + " ont collaboré " + vect[j] + " fois.");
//				}
//			}
//		}
//		
//	}
	
	

}
