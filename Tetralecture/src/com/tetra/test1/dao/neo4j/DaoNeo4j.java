package com.tetra.test1.dao.neo4j;

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

import com.tetra.test1.dao.LectureFichier;
import com.tetra.test1.dao.neo4j.nodeLabels.NodeLabels;
import com.tetra.test1.dao.neo4j.typesRelations.TypeRelation;

public class DaoNeo4j {
	
	GraphDatabaseFactory dbFactory = new GraphDatabaseFactory();
	GraphDatabaseService db = dbFactory.newEmbeddedDatabase("C:\\Zakaria\\NeoTests\\Tetralecture");

	public DaoNeo4j() {
		
		LectureFichier lectureFichier = new LectureFichier();
		lectureFichier.lireFichier();
		createNodes(lectureFichier.getListeAuteurs());
		createLinks(lectureFichier.getListeAuteurs(), lectureFichier.getMatriceInt());
		
	}
	
	private void createNodes(List<String> nomsNoeuds) {
		
//		GraphDatabaseFactory dbFactory = new GraphDatabaseFactory();
//		GraphDatabaseService db = dbFactory.newEmbeddedDatabase("C:\\Zakaria\\NeoTests\\Tetralecture");
		
		for(int i=0; i<nomsNoeuds.size(); i++) {
			
			try (Transaction tx = db.beginTx()) {
				Node noeud = db.createNode(NodeLabels.AUTEUR);
				noeud.setProperty("Nom_Court", nomsNoeuds.get(i));
				tx.success();
			}
			
		}
		
	}
	
	private void createLinks(List<String> auteurs, List<Integer[]> matriceInt) {
		
//		GraphDatabaseFactory dbFactory = new GraphDatabaseFactory();
//		GraphDatabaseService db = dbFactory.newEmbeddedDatabase("C:\\Zakaria\\NeoTests\\Tetralecture");
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
					//System.out.println("Auteur : " + auteurs.get(i) + " et auteur : " + auteurs.get(j) + " ont collaboré " + vect[j] + " fois.");
					try (Transaction tx = db.beginTx()) {
						String query = "MATCH (auteur1:AUTEUR{Nom_Court:'" + auteurs.get(i) + "'}), (auteur2:AUTEUR{Nom_Court:'" + auteurs.get(j) + "'}) return auteur1, auteur2";
						ExecutionResult result = execEngine.execute(query);
//						Iterator<Node> aut_column = result.columnAs("auteur2");
//						for(Node node : IteratorUtil.asIterable(aut_column)) {
//							String nodeResult = node + " : " + node.getProperty("Nom_Court");
//							System.out.println(nodeResult);
//						}
//						for ( Map<String, Object> row : result ) {
//						    for ( Entry<String, Object> column : row.entrySet() ) {
//						    	String nodeResult = column.getKey() + " : " + column.getValue();
//						    	System.out.println(nodeResult);
//						    }
//						}
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
	
}
