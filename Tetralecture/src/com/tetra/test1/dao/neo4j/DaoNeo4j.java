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

	private static final String DB_PATH = "C:\\Zakaria\\NeoTests\\Tetralecture";
	GraphDatabaseFactory dbFactory = new GraphDatabaseFactory();
	GraphDatabaseService db = dbFactory.newEmbeddedDatabase(DB_PATH);

	public DaoNeo4j() {
		LectureFichier lectureFichier = new LectureFichier();
		lectureFichier.lireFichier();
		createNodes(lectureFichier.getListeAuteurs());
		createLinks(lectureFichier.getListeAuteurs(), lectureFichier.getMatriceInt());
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

}
