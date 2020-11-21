import static spark.Spark.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
public class FrontEndServer
{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		port(7111);
	 	Gson g = new Gson();
		get("lookup/:id",(request,response)
	->{ String id =request.params(":id");
	     String url = "http://10.0.2.15:4567/Lookup/"+ id;
	     URL obj = new URL (url);
	     HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		return con.getInputStream();});
		
		get("search/:topic",(request,response)
			->{ String topic =request.params(":topic");
		 	   String url = "http://10.0.2.15:4567/Search/"+ topic;
			 URL obj = new URL (url);
		 	 HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		 	  BufferedReader in = new BufferedReader(
			             new InputStreamReader(con.getInputStream()));
			    String inputLine;
			    StringBuffer data = new StringBuffer();

			    while ((inputLine = in.readLine()) != null) {
			        data.append(inputLine);
			    }
		   return data;});
		
		get("buy/:id",(request,response)
				->{ String id =request.params(":id");
				     String url = "http://10.0.2.16:6543/Buy/"+ id;
				     URL obj = new URL (url);
				     HttpURLConnection con = (HttpURLConnection) obj.openConnection();
					return con.getInputStream();});
	}//main
}