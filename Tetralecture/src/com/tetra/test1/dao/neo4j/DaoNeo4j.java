package com.tetra.test1.dao.neo4j;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.helpers.collection.IteratorUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tetra.test1.dao.LectureFichier;
import com.tetra.test1.dao.beans.Graphe;
import com.tetra.test1.dao.beans.Lien;
import com.tetra.test1.dao.beans.Noeud;
import com.tetra.test1.dao.neo4j.nodeLabels.NodeLabels;
import com.tetra.test1.dao.neo4j.typesRelations.TypeRelation;

public class DaoNeo4j {

	private static final String DB_PATH = "C:\\Zakaria\\NeoTests\\Tetralecture";
	GraphDatabaseFactory dbFactory = new GraphDatabaseFactory();
	GraphDatabaseService db = dbFactory.newEmbeddedDatabase(DB_PATH);

	private Graphe graphe = new Graphe();

	public DaoNeo4j() {
		LectureFichier lectureFichier = new LectureFichier();
		lectureFichier.lireFichier();
		createNodes(lectureFichier.getListeAuteurs());
		createLinks(lectureFichier.getListeAuteurs(), lectureFichier.getMatriceInt());
		generateJson();

	}

	private void createNodes(List<String> nomsNoeuds) {
		for(int i=0; i<nomsNoeuds.size(); i++) {
			try (Transaction tx = db.beginTx()) {
				Node noeud = db.createNode(NodeLabels.AUTEUR);
				noeud.setProperty("Nom_Court", nomsNoeuds.get(i));
				tx.success();
			}

		}
	}

	private void createLinks(List<String> auteurs, List<Integer[]> matriceInt) {
		ExecutionEngine execEngine = new ExecutionEngine(db);
		for(int i=0; i<matriceInt.size(); i++) {
			Integer[] vect = matriceInt.get(i);
			for(int j=0; j<vect.length-1; j++) {
				if(i==j) {
					continue;
				}
				if(vect[j]==null || vect[j]==0) {
					continue;
				}
				else {
					try (Transaction tx = db.beginTx()) {
						String query = "MATCH (auteur1:AUTEUR{Nom_Court:'" + auteurs.get(i) + "'}), (auteur2:AUTEUR{Nom_Court:'" + auteurs.get(j) + "'}) return auteur1, auteur2";
						ExecutionResult result = execEngine.execute(query);
						ResourceIterator<Map<String, Object>> rows = result.iterator();
						for(Map<String,Object> row : IteratorUtil.asIterable(rows)) {
							Node n1 = (Node) row.get("auteur1");
							Node n2 = (Node) row.get("auteur2");
							Relationship collaborer = n2.createRelationshipTo(n1, TypeRelation.COLLABORER);
							collaborer.setProperty("weight", vect[j]);
						}
						tx.success();
					}
				}
			}
		}
		System.out.println("Succès!");
	}

	private void getNeo4jNodes() {
		List<Noeud> noeuds = new ArrayList<Noeud>();
		ExecutionEngine execEngine = new ExecutionEngine(db);
		try (Transaction tx = db.beginTx()) {
			String query = "MATCH (auteur1:AUTEUR) return auteur1";
			ExecutionResult result = execEngine.execute(query);
			ResourceIterator<Map<String, Object>> rows = result.iterator();
			for(Map<String,Object> row : IteratorUtil.asIterable(rows)) {
				Node n1 = (Node) row.get("auteur1");
				Noeud noeud1 = new Noeud();
				noeud1.setName(n1.getProperty("Nom_Court").toString());
				noeuds.add(noeud1);
			}
			tx.success();
			graphe.setNodes(noeuds);
		}
	}

	private void getNeo4jLinks() {
		List<Lien> liens = new ArrayList<Lien>();
		ExecutionEngine execEngine = new ExecutionEngine(db);
		try (Transaction tx = db.beginTx()) {
			String query = "MATCH (auteur1:AUTEUR)-[lien]->(auteur2:AUTEUR) return auteur1, auteur2, lien";
			ExecutionResult result = execEngine.execute(query);
			ResourceIterator<Map<String, Object>> rows = result.iterator();
			for(Map<String,Object> row : IteratorUtil.asIterable(rows)) {
				Node n1 = (Node) row.get("auteur1");
				Node n2 = (Node) row.get("auteur2");
				Relationship rel1 = (Relationship) row.get("lien");
				Lien lien1 = new Lien();
				lien1.setSource(n1.getId());
				lien1.setTarget(n2.getId());
				lien1.setValue(Integer.parseInt(rel1.getProperty("weight").toString()));
				liens.add(lien1);
			}
			tx.success();
			graphe.setLinks(liens);
		}
	}

	private void generateJson() {
		getNeo4jNodes();
		getNeo4jLinks();
		Gson gson = new GsonBuilder()
							        .disableHtmlEscaping()
							        .setPrettyPrinting()
							        .serializeNulls()
							        .create();
		String gsonGraphe = gson.toJson(graphe);
		File file = new File("src/com/tetra/test1/output/output.json");
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter writer = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			bufferedWriter.write(gsonGraphe);
			bufferedWriter.close();
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

}
