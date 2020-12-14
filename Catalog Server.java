import static spark.Spark.*;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
public class CatalogServer
{

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		port(Integer.parseInt(args[0]));
		Gson g = new Gson();
    	get("Lookup/:id",( req,res)->{int ID = Integer.parseInt(req.params(":id"));
    	String path = "D:/Documents/Desktop/Eng/Books.txt";
    	System.out.println(g.toJson(CSVReaderInJava.LookUp(path, ID)));
    		return g.toJson(CSVReaderInJava.LookUp(path, ID));			
    	});
    	
    	get("Search/:topic",( req,res)->{String topic = req.params(":topic");
    	String path = "D:/Documents/Desktop/Eng/Books.txt";
    		return g.toJson(CSVReaderInJava.SearchByTopic(path, topic));			
    	});

	}
	
	
	
	
}
