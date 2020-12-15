import static spark.Spark.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
public class FrontEndServer
{
	
	
static ArrayList <Book> myCache =new ArrayList<Book>(4);
static int Max=4;
static Gson g = new Gson();
	
	public static void main(String[] args) {
		
	
		
		//********** ip's list for catalog server
		List<String> list = new ArrayList<String>();
		list.add("http://localhost:9638/");
		list.add("http://localhost:9639/");
		RoundRobinLoadBalancer roundRobinLoadBalancer = new RoundRobinLoadBalancer(list);
		
		//********** ip's list for order server 
		List<String> list2 = new ArrayList<String>();
		list2.add("http://localhost:7458/");
		list2.add("http://localhost:7459/");
		RoundRobinLoadBalancer roundRobinLoadBalancer2 = new RoundRobinLoadBalancer(list2);
		
		//****************************************
		port(7788);
		//myCache.add(new Book(102,"Xen and the Art of Surviving Graduate School",0,45));
	 	Gson g = new Gson();
		get("Lookup/:id",(request,response)
	->{ String id =request.params(":id");
	     int ID = Integer.parseInt(request.params(":id"));
	     String output;
	     StringBuffer data = new StringBuffer();
	     Book b;
	     System.out.println(ID);
	     System.out.println(SearchInCache(ID));
	    if (SearchInCache(ID)) 
	    {
	    	System.out.println("Book found , I'll get it from cache");
	    	for (Book book:myCache)
	    	{
	    		if (book.getId()==ID)
	    		{
	    		b=new Book(ID,book.getName(),book.getAmount(),book.getPrice());
	    		data.append(b.toString());
	    		break;
	    		}	
	    	}
	    }
	    	
	    	
	 
	     else 
	     {
	      String url = roundRobinLoadBalancer.getIp()+"Lookup/"+ id;
	      System.out.println("Hello from "+url);
	      URL obj = new URL (url);
	      HttpURLConnection con = (HttpURLConnection) obj.openConnection();
          BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
           String inputLine;
	   

	    while ((inputLine = reader.readLine()) != null) {
	    	
	        data.append(inputLine);
	    }
	
	    for (Book book:myCache)
    	{
    		if (book.getId()==ID)
    		{
    		b=new Book(ID,book.getName(),book.getAmount(),book.getPrice());
    		data.append(b.toString());
    		break;
    		}	
    	}
	    
	    
	   // myCache.add(new Book(ID,Name2,Amount2,price2));
	    if (myCache.size()>= Max)
	    {
	    	System.out.println("Cache is full");
	    	myCache.remove(0);
	    }
	    
	     }
	     
		return data;
		});
		
		System.out.println("Info in cache: "+myCache.toString());
		
		get("Search/:topic",(request,response)
			->{ String topic =request.params(":topic");
				System.out.println(topic);
		 	   String url = roundRobinLoadBalancer.getIp()+"Search/"+ topic;
		 	  System.out.println("Hello from "+url);
			 URL obj = new URL (url);
		 	 HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		 	BufferedReader reader = new BufferedReader(new InputStreamReader(((HttpURLConnection)
		 			(new URL(url)).openConnection()).getInputStream(),
		 			Charset.forName("UTF-8")));
		 
//		 	  BufferedReader in = new BufferedReader(
//			             new InputStreamReader(con.getInputStream()));
			    String inputLine;
			    StringBuffer data = new StringBuffer();

			    while ((inputLine = reader.readLine()) != null) {
			    	
			        data.append(inputLine);
			    }
			    System.out.println(data.toString());
		  // return data;
		 	 return con.getInputStream();
		 
			});
		
		get("Buy/:id",(request,response)
				->{ String id =request.params(":id");
				     String url = roundRobinLoadBalancer2.getIp()+"Buy/" + id;
				     System.out.println("Hello from "+url);
				     int ID = Integer.parseInt(request.params(":id"));
				     URL obj = new URL (url);
				     HttpURLConnection con = (HttpURLConnection) obj.openConnection();
					return con.getInputStream();});
		
		
	}//main
	
	
	
	
	public static boolean SearchInCache(int ID)
	{
		boolean found=false;
		for (Book b: myCache)
		{
			if (b.getId()==ID)
			{
				found=true;
				break;
			}
	
		}
		return found;
	}
	
	
	
}
