package com.tetra.test1;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.tetra.test1.dao.neo4j.DaoNeo4j;

public class Main {

	private static final String DB_PATH = "C:\\Zakaria\\NeoTests\\Tetralecture";

	public static void main(String[] args) {

		double startTime = System.currentTimeMillis();
		clearDbPath();
		DaoNeo4j daoNeo4j = new DaoNeo4j();
		double endTime = System.currentTimeMillis();
		double executionTime = (endTime - startTime) / 1000;
		System.out.println( "Temps d'exécution Total : " + executionTime);

	}

	private static void clearDbPath() {
		try {
			FileUtils.deleteDirectory(new File( DB_PATH ));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}
