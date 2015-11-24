package partiehbase;



import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.tdb.TDBFactory;
import com.hp.hpl.jena.util.FileManager;
 
public class HbaseClass {
	
	  static Configuration conf= HBaseConfiguration.create();
	    static HTable table;
	    static ResultScanner scanner = null;
	    public static final String region = "/home/ely/workspace/proget_BDC/region.rdf";
	    public static final String liaison = "/home/ely/workspace/proget_BDC/liaison.rdf";
	    public static final String sprt = "/home/ely/workspace/proget_BDC/sport.rdf";

    public static void creatTable(String tableName, String[] familys)
            throws Exception {
        HBaseAdmin admin1 = new HBaseAdmin(conf);
        if (admin1.tableExists(tableName)) {
            System.out.println("table already exists!");
        } else {
            HTableDescriptor tableDesc = new HTableDescriptor(tableName);
            for (int i = 0; i < familys.length; i++) {
                tableDesc.addFamily(new HColumnDescriptor(familys[i]));
            }
            admin1.createTable(tableDesc);
            System.out.println("create table " + tableName + " ok.");
        }
    }
 
    
 
    /**
     * Put (or insert) a row
     */
    public static void addRecord(String tableName, String rowKey,
            String family, String qualifier, String value) throws Exception {
        try {
            HTable table = new HTable(conf, tableName);
            Put put = new Put(Bytes.toBytes(rowKey));
            put.add(Bytes.toBytes(family), Bytes.toBytes(qualifier),
            		Bytes.toBytes(value));
            table.put(put);
            System.out.println("insert recored " + rowKey + " to table "
                    + tableName + " ok.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    
    public static void main(String[] agrs) {
    		Model ModelHbase= ModelFactory.createDefaultModel();
    		  String NameSpaceH = "http://www.ely.fr#";
    		  ModelHbase.setNsPrefix("sport", NameSpaceH);
    	
		  String NameSpace = "http://www.ely.fr#";
		 
		  

	
        
    	
    	try {
            String tablename = "equipe";
            String[] familys = { "EquipeCommune" };
                        
            HbaseClass.creatTable(tablename, familys);
// 
            HbaseClass.addRecord(tablename, "Montpellier", "EquipeCommune", "equipeNom", "MSHC");
            HbaseClass.addRecord(tablename, "Paris", "EquipeCommune", "equipeNom", "PSG");
            HbaseClass.addRecord(tablename, "Marseille", "EquipeCommune", "equipeNom", "OM");
          

           try {table = new HTable(conf, "equipe");	
           Scan scan = new Scan();
           scanner = table.getScanner(scan);
           
       } catch (IOException ex) {
           Logger.getLogger(HbaseClass.class.getName()).log(Level.SEVERE, null, ex);
       }
        
       Iterator<Result> it = scanner.iterator();
       while (it.hasNext()) {

           Result result = it.next();
           for (KeyValue kv : result.raw()) {
           	Resource Region = ModelHbase.createResource("http://www.ely.fr#" + Bytes.toString(kv.getRow()));
          	Resource equipe = ModelHbase.createResource( "http://www.ely.fr#" + Bytes.toString(kv.getValue()));
               String column = Bytes.toString(kv.getQualifier());
               if (column.equals("equipeNom")) {
            	   ModelHbase.addLiteral(Region, ModelHbase.createProperty(NameSpace + "ApourEquipe"), 
            			//   kv.getValue());
            			//   "http://www.sports.fr#" + Bytes.toString(kv.getValue()));
            			   equipe);
            	   
            	   System.out.println("valueeeeeeeeeeeee "+kv);
            	   System.out.println("valueeeeeee2222 "+Region.getLocalName());
            	   System.out.println("valueeeeeee2222 "+Region.toString());
            	   System.out.println("valueee33333333 "+equipe.toString());
            	   System.out.println("valueee33333333 "+equipe.getLocalName());
            	   System.out.println("colommmm "+column);
            	   
            	   System.out.println("elyy "+  Bytes.toString(kv.getRow()));
            	  // System.out.println("elyy "+  Bytes.toString(Bytes.toString(kv.getValue()));
            	 
           }
           }
       }

       

       
       
       
      	try {       
   			FileOutputStream outStream = new FileOutputStream("sortiehbase.rdf");
   		
   			//exporte le resultat dans un fichier
   			ModelHbase.write(outStream, "RDF/XML");
   			outStream.close();
   		}
   		catch (FileNotFoundException e) {System.out.println("File not found");}
   		catch (IOException e) {System.out.println("IO problem");}
       
       
       
       
       } catch (Exception e) { e.printStackTrace();}
    	
    	
    	
    	
 
        
    	

    }
    
    
    
    
    }