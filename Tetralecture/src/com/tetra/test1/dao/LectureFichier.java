package com.tetra.test1.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LectureFichier {
	
	public void lireFichier() {
		
double startTime = System.currentTimeMillis();
		
		BufferedReader br = null;
		 
		try {
 
			String sCurrentLine;
 
			br = new BufferedReader(new FileReader("src/com/tetra/test1/source/A-A.1"));
 
			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);
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
