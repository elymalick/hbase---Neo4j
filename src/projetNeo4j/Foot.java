package projetNeo4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.graphdb.traversal.Uniqueness;
import org.neo4j.kernel.Traversal;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.DC;
import com.hp.hpl.jena.vocabulary.RDF;



public class Foot {
	private static enum RelTypes implements RelationshipType
	{
	    JOUEUR, COEQUIPIER, EQUIPE,CAT
	}

	
	
	
	//String fil = "file:sport.rdf";


	
	private long matrixNodeId=0;
	private long matrixNodeIdjour=0;
	private static final String neo4j_DBPath="/home/ely/neo4j-community-2.0.0";
	//public GraphDatabaseService graphdbservice;
	public GraphDatabaseService graphdbservice;
			
	
	public void createNodeSpace()
	{
		graphdbservice = new GraphDatabaseFactory().newEmbeddedDatabase(neo4j_DBPath);
	Transaction tx = graphdbservice.beginTx();
	try {
		
		
		Node Foot = graphdbservice.createNode(); // cr�ation du point d�acc�s au graphe
		matrixNodeId = Foot.getId();
		
		
		Node ligue = graphdbservice.createNode(); // cr�ation du 2�me point 
		//matrixNodeId = psg.getId();
		ligue.setProperty("name", "ligue1"); 
		Foot.createRelationshipTo(ligue, RelTypes.CAT);

		
		Node MSCH = graphdbservice.createNode(); // cr�ation la branche de MSCH
		//matrixNodeId = psg.getId();
		MSCH.setProperty("name", "MSCH"); // attribution du premier id
		Node kaoutari = graphdbservice.createNode(); // cr�ation d�un autre noeud
		kaoutari.setProperty("name", "kaoutari"); // property(name) = thomas
		kaoutari.setProperty("age", "29"); // property(age) = 29
		// cr�ation d�une relation entre l�entr�e du graphe et thomas (type neo_node)
		MSCH.createRelationshipTo(kaoutari, RelTypes.JOUEUR);
		Node kamara = graphdbservice.createNode();
		kamara.setProperty("name", "kamara");
		kamara.setProperty("age", "22"); // property(age) = 29
		kaoutari.createRelationshipTo(kamara, RelTypes.COEQUIPIER);
		
		Node Romain = graphdbservice.createNode(); // cr�ation d�un autre noeud
		Romain.setProperty("name", "Romain Armand"); // property(name) = thomas
		Romain.setProperty("age", "34"); // property(age) = 29
		kamara.createRelationshipTo(Romain, RelTypes.COEQUIPIER);
		Node Bruno = graphdbservice.createNode();
		Bruno.setProperty("name", "Bruno Alicarte");
		Bruno.setProperty("age", "19");
		Romain.createRelationshipTo(Bruno, RelTypes.COEQUIPIER);
		
		Node Christian = graphdbservice.createNode();
		Christian.setProperty("name", "Christian Andr�");
		Christian.setProperty("age", "20");
		Bruno.createRelationshipTo(Christian, RelTypes.COEQUIPIER);
		
		
	
		Node psg = graphdbservice.createNode(); // cr�ation de la branche de psg
		//matrixNodeId = psg.getId();
		psg.setProperty("name", "PSG"); // attribution du premier id
		Node zalatan = graphdbservice.createNode(); // cr�ation d�un autre noeud
		zalatan.setProperty("name", "zalatan"); // property(name) = thomas
		zalatan.setProperty("age", "29"); // property(age) = 29
		// cr�ation d�une relation entre l�entr�e du graphe et thomas (type neo_node)
		psg.createRelationshipTo(zalatan, RelTypes.JOUEUR);
		Node menez = graphdbservice.createNode();
		menez.setProperty("name", "menez");
		menez.setProperty("age", "20");
		zalatan.createRelationshipTo(menez, RelTypes.COEQUIPIER);

		Node cavani = graphdbservice.createNode();
		cavani.setProperty("name", "cavani");
		cavani.setProperty("age", "22 ans");
		menez.createRelationshipTo(cavani, RelTypes.COEQUIPIER);
	
		Node pastorie = graphdbservice.createNode();
		pastorie.setProperty("name", "pastorie");
		pastorie.setProperty("age", "23 ans");
		cavani.createRelationshipTo(pastorie, RelTypes.COEQUIPIER);
		
		Node tiago = graphdbservice.createNode();
		tiago.setProperty("name", "tiago");
		tiago.setProperty("age", "26 ans");
		pastorie.createRelationshipTo(tiago, RelTypes.COEQUIPIER);
	
	
	
	
	
		Node marseille = graphdbservice.createNode(); // cr�ation du point de OM
		//matrixNodeId = psg.getId();
		marseille.setProperty("name", "OM");
		Node Kanga = graphdbservice.createNode();
		Kanga.setProperty("name", "Kanga Akal�"); 
		Kanga.setProperty("age", "29"); 
	
		marseille.createRelationshipTo(Kanga, RelTypes.JOUEUR);
		Node Louis = graphdbservice.createNode();
		Louis.setProperty("name", "Louis Achard");
		Louis.setProperty("age", "30"); // property(age) = 29
		Kanga.createRelationshipTo(Louis, RelTypes.COEQUIPIER);
		Node Roberto = graphdbservice.createNode(); // cr�ation d�un autre noeud
		Roberto.setProperty("name", "Roberto Alarcon"); // property(name) = thomas
		Roberto.setProperty("age", "34"); // property(age) = 29
		Louis.createRelationshipTo(Roberto, RelTypes.COEQUIPIER);
		Node Joseph = graphdbservice.createNode();
		Joseph.setProperty("name", "Joseph Alcazar");
		Joseph.setProperty("age", "19");
		Roberto.createRelationshipTo(Joseph, RelTypes.COEQUIPIER);
		Node Klaus = graphdbservice.createNode();
		Klaus.setProperty("name", "Klaus Allofs");
		Klaus.setProperty("age", "20");
		Joseph.createRelationshipTo(Klaus, RelTypes.COEQUIPIER);
	

		ligue.createRelationshipTo(psg, RelTypes.EQUIPE);
		ligue.createRelationshipTo(MSCH, RelTypes.EQUIPE);
		ligue.createRelationshipTo(marseille, RelTypes.EQUIPE);

		
		tx.success(); // on commit les changements
	}
	 catch(Exception e){
		System.out.println(e.getMessage());
		tx.finish();
	}
	}
	
	private Node getFirstNode() {
		return graphdbservice.getNodeById(matrixNodeId)
		.getSingleRelationship( RelTypes.CAT, Direction.OUTGOING)
		.getEndNode();
		}
	
	private static Traverser getFriends(Node firstNode) {
		TraversalDescription td = Traversal.description()
		.breadthFirst()
		.relationships(RelTypes.EQUIPE, Direction.OUTGOING)
		.evaluator(Evaluators.excludeStartPosition())
		.uniqueness(Uniqueness.NODE_GLOBAL);
		return td.traverse(firstNode);
		}
	
	public void printNeoFriends(Model m) {
		String spt ="http://www.sportsfoot.fr#";
		m.setNsPrefix("sports", spt);
		Resource ligue = m.createResource(spt+"ligue");
		Property participe = m.createProperty(spt+"participe");
		
		
		
		Resource ligues = m.createResource(spt+"ligue");
		m.add(ligues, RDF.type, ligue);
		
		Node firstNode = getFirstNode();
		Node jouer;
		String output = firstNode.getProperty("name") + "'s friends :" +
		System.getProperty("line.separator");
		Traverser friendsTraverser = getFriends(firstNode); // noeuds parcourus
		int nbOfFriends = 0; // compteur d�amis
		for (Path friendPath : friendsTraverser) {
			
			output = "At depth " + friendPath.length() + " => "
					+ friendPath.endNode().getProperty( "name" ) +
					System.getProperty("line.separator");
			System.out.println(output);
			Resource eqi = m.createResource(spt+friendPath.endNode().getProperty( "name" ));
			eqi.addProperty(DC.title, friendPath.endNode().getProperty( "name" ).toString());
			m.add(ligues, participe, eqi);
			
			 
				long id=0;
				jouer=friendPath.endNode();
				id=jouer.getId();			 
		
				System.out.println(printNeoFriendsjouer(id,m,eqi));
				nbOfFriends++;
				
				}
		 m.write(System.out, "RDF/XML");
		 
			try {       
				FileOutputStream outStream = new FileOutputStream("sport.rdf");
			
				//exporte le resultat dans un fichier
				m.write(outStream, "RDF/XML");
				outStream.close();
			}
			catch (FileNotFoundException e) {System.out.println("File not found");}
			catch (IOException e) {System.out.println("IO problem");}

		 
		 
		}
	
	
	public String printNeoFriendsjouer(long i,Model m,Resource r) {
		String spt ="http://www.sportsfoot.fr#";
		m.setNsPrefix("sports", spt);
		Resource equipe = m.createResource(spt+"equipe");
		Resource joueur = m.createResource(spt+"joueur");
		Property joueurs = m.createProperty(spt+"joueurs");
		Property age = m.createProperty(spt+"age");
		
		m.add(r,RDF.type,equipe);
		
		
		Node firstNode = getFirstNodeJouer(i);
		Node jouer;
		
		String output = firstNode.getProperty("name") + "'s friends :" +
		System.getProperty("line.separator");
		Resource joueure = m.createResource(spt+firstNode.getProperty("name"));
		joueure.addProperty(FOAF.name, firstNode.getProperty("name").toString());
		joueure.addProperty(age, firstNode.getProperty("age").toString());
		m.add(r,joueurs,joueure);
		m.add(joueure,RDF.type,joueur);
		Traverser friendsTraverser = getFriendjouer(firstNode); // noeuds parcourus
		int nbOfFriends = 0; // compteur d�amis
		for (Path friendPath : friendsTraverser) {	
		
		output += "At depth " + friendPath.length() + " => "
		+ friendPath.endNode().getProperty( "name" ) +
		System.getProperty("line.separator");
		
		Resource joueures = m.createResource(spt+friendPath.endNode().getProperty("name"));
		joueures.addProperty(FOAF.name, friendPath.endNode().getProperty("name").toString());
		joueures.addProperty(age, friendPath.endNode().getProperty("age").toString());
		m.add(joueures,RDF.type,joueur);
		m.add(r,joueurs,joueures);
		nbOfFriends++;
		
		}
		output += "Number of friends found: " + nbOfFriends;
		return output;
		}
	
	private Node getFirstNodeJouer(long i) {
		return graphdbservice.getNodeById(i)
		.getSingleRelationship( RelTypes.JOUEUR, Direction.OUTGOING)
		.getEndNode();
		}
	
	private static Traverser getFriendjouer(Node firstNode) {
		TraversalDescription td = Traversal.description()
		.breadthFirst()
		.relationships(RelTypes.COEQUIPIER, Direction.OUTGOING)
		.evaluator(Evaluators.excludeStartPosition())
		.uniqueness(Uniqueness.NODE_GLOBAL);
		return td.traverse(firstNode);
		}
	

}
