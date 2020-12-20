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
	 	Gson g = new Gson();
		get("Lookup/:id",(request,response)
	->{ String id =request.params(":id");
	     int ID = Integer.parseInt(request.params(":id"));
	     StringBuffer data = new StringBuffer();
	     Book b;
	    if (SearchInCache(ID)) 
	    {
	    	System.out.println("Book found , I'll get it from cache");
	    	for (Book book:myCache)
	    	{
	    		if (book.getId()==ID)
	    		{
	    		b=new Book(ID,book.getName(),book.getAmount(),book.getPrice());
	    		data.append(b);
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
	    System.out.println("I'm from buffer: "+data.toString());
	    String[] Splitted=data.toString().replace("\"", "").split(",",4);
	    //System.out.println("Hello from splitted : " +Splitted[0]+","+Splitted[1]+","+Splitted[2]+","+Splitted[3]);
	    b=new Book(Integer.parseInt(Splitted[0]),Splitted[1],Integer.parseInt(Splitted[2]),Integer.parseInt(Splitted[3]));
	    data.append(b);
	    myCache.add(b);
	    if (myCache.size()>= Max)
	    {
	    	System.out.println("Cache is full");
	    	myCache.remove(0);
	    }
	    
	     }
	     
		return data;
		});
		
	//********************************************************Search
		get("Search/:topic",(request,response)
			->{ String topic =request.params(":topic");
			
			
			 StringBuffer data = new StringBuffer();
		     Book b;
			//Check first if the book is in cache
			if(SearchInCacheByTopic(topic))
			{
				System.out.println("Book found , I'll get it from cache");
			for (Book book: myCache)
			{
				if (book.getTopic().equals(topic))
					{
					b= new Book(book.getId(),book.getName(),book.getAmount(),book.getPrice());
	    		     data.append(b+"/");
	    		    }
			}
			}
			
			
			else {
		 	   String url = roundRobinLoadBalancer.getIp()+"Search/"+ topic;
		 	  System.out.println("Hello from "+url);
			 URL obj = new URL (url);
		 	 HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		 	BufferedReader reader = new BufferedReader(new InputStreamReader(((HttpURLConnection)
		 			(new URL(url)).openConnection()).getInputStream(),
		 			Charset.forName("UTF-8")));
		 
	 	  BufferedReader in = new BufferedReader(
			             new InputStreamReader(con.getInputStream()));
			    String inputLine;
			    while ((inputLine = reader.readLine()) != null) {
			    	
			        data.append(inputLine);
			    }
			}
			//add new books to cache
			
			String[] SameTopic=data.toString().replace("\"", "").split("/",4);
			System.out.println("Hello from SameTopic : " +SameTopic[0]+","+SameTopic[1]+","+SameTopic[2]+","+SameTopic[3]);
			for (String s:SameTopic)
			{
				if (!s.isEmpty())
				{
					String[] eachBook=s.split(",",4);
					Book addToCache=new Book(Integer.parseInt(eachBook[0]),eachBook[1],Integer.parseInt(eachBook[2]),Integer.parseInt(eachBook[3].replace("/","")));
					myCache.add(addToCache);
					System.out.println("Book of name '"+addToCache.getName()+"'"+"added to cache successfully! ");
				}
			}
			System.out.println("All books in this topic: " +SameTopic.toString());
		 return data;
		 	
		 
			});
		
		
		
		
		
		
	//****************************** Buy
		
		
		
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
	
	
	public static boolean SearchInCacheByTopic(String Topic)
	{
		boolean found=false;
		for (Book b: myCache)
		{
			if (b.getTopic().equals(Topic))
			{
				found=true;
				break;
			}
	
		}
		return found;
	}
	
	
	
	
	private static Book createBook(String[] metadata)

	{
		
	int id =Integer.parseInt(metadata[0]);	
	String name = metadata[1];
	int price = Integer.parseInt(metadata[2]);
	int Amount =Integer.parseInt(metadata[3]);	
	String Topic = metadata[4]; // create and return book of this metadata 
	return new Book(id,name, price,Amount );
	} 

	
}
