import static spark.Spark.*;
public class CatalogServer
{

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		port(456);
		Gson g = new Gson();
    	get("Lookup/:id",( req,res)->{int ID = Integer.parseInt(req.params(":id"));
    	String path = "/home/aseel/Desktop/SharedFolders/books.txt";
    		return g.toJson(CSVReaderInJava.LookUp(path, ID));			
    	});
    	
    	get("Search/:topic",( req,res)->{String topic = req.params(":topic");
    	String path = "/home/aseel/Desktop/SharedFolders/books.txt";
    		return g.toJson(CSVReaderInJava.SearchByTopic(path, topic));			
    	});

	}
	
	
	
	
}
