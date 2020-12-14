import static spark.Spark.*;

import com.google.gson.Gson;
public class CatalogServer
{

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		port(9638);
		Gson g = new Gson();
    	get("Lookup/:id",( req,res)->{int ID = Integer.parseInt(req.params(":id"));
    	String path = "D:/Documents/Desktop/Eng/Books.txt";
    		return g.toJson(CSVReaderInJava.LookUp(path, ID));			
    	});
    	
    	get("Search/:topic",( req,res)->{String topic = req.params(":topic");
    	String path = "D:/Documents/Desktop/Eng/Books.txt";
    		return g.toJson(CSVReaderInJava.SearchByTopic(path, topic));			
    	});

	}
	
	
	
	
}
