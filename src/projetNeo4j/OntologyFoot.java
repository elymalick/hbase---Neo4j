package projetNeo4j;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.DC;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDF;

public class OntologyFoot {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Model m = ModelFactory.createDefaultModel();
		String spt ="http://www.sportsfoot.fr#";
		m.setNsPrefix("sports", spt);
		
	
                 
     	m.setNsPrefix("foaf", FOAF.getURI());
        String xsd ="http://www.w3.org/2001/XMLSchema#";
        m.setNsPrefix("xsd",xsd );
		
		Resource ligue = m.createResource(spt+"ligue");
		Resource equipe = m.createResource(spt+"equipe");
		Resource joueur = m.createResource(spt+"joueur");

		Property participe = m.createProperty(spt+"participe");
		Property joueurs = m.createProperty(spt+"joueurs");
		Property age = m.createProperty(spt+"age");
		Literal StringType = m.createTypedLiteral(xsd+"string");
		
		// relier la ligue avec les �quipes qui participent 
		m.add(ligue, RDF.type, OWL.Class);
		m.add(ligue,participe,equipe);
		 
		 // d�finire l'�quipe avec ces joueurs
		 m.add(equipe,RDF.type,OWL.Class);
		 m.add(equipe, DC.title,StringType);
		 m.add(equipe,joueurs,joueur);
		 
		 
		 
		 m.add(joueur,RDF.type,OWL.Class);
		 m.add(joueur, FOAF.name,StringType);
		 m.add(joueur,age,StringType);
		 
        
		try {       
			FileOutputStream outStream = new FileOutputStream("sport.rdf");
		
			//exporte le resultat dans un fichier
			m.write(outStream, "RDF/XML");
			outStream.close();
		}
		catch (FileNotFoundException e) {System.out.println("File not found");}
		catch (IOException e) {System.out.println("IO problem");}

	}

}
