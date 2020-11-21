import static spark.Spark.*;
import com.google.gson.Gson;


public class OrderServer

{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Gson g = new Gson();
		port(6543);
    	get("Buy/:id",( req,res)->{int ID = Integer.parseInt(req.params(":id"));
    	String path = "/home/aseel/Desktop/SharedFolders/books.txt";
    		return g.toJson(CSVReaderInJava.Buy(path, ID));			
    	});

	}
	
	
	
}
