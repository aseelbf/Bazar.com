import static spark.Spark.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    	//System.out.println(g.toJson(CSVReaderInJava.LookUp(path, ID)));
    		return g.toJson(CSVReaderInJava.LookUp(path, ID));			
    	});
    	
    	get("Search/:topic",( req,res)->{String topic = req.params(":topic");
    	String path = "D:/Documents/Desktop/Eng/Books.txt";
    		return g.toJson(SearchByTopic(path, topic));			
    	});

	}
	
	
	public static Object SearchByTopic(String fileName,String Topic)
	{
		System.out.println("the topic is " +Topic);
		String output=""; 
		List<Book> bookData = new ArrayList<>(); 
		List<Book> books = new ArrayList<>(); 
		Path pathToFile = Paths.get(fileName);

	try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) 
	{
		System.out.println("inside the buffer reader");
		String line = br.readLine(); 
		while (line != null) 
		{ 
			String[] attributes = line.split(",");
			Book book = createBook(attributes); 
			System.out.println("the book is "+book.getName()+" tobic is "+book.getTopic());
			 books.add(book); 
		
			line = br.readLine(); 
			
			}

		
		} 
	catch (IOException ioe)
	{
		ioe.printStackTrace(); 
	}
		

	for (Book b : books) 
	{ 
		System.out.println("inside the book loop the book is " +b.getName() + " topic" + b.getTopic());
				if (Topic.equals(b.getTopic()))
				{
					System.out.println("it's equal");
					bookData.add(b);
					output+=b.getId()+","+b.getName()+","+b.getAmount() +"," + b.getPrice()+"/";
					
				}

	}
	System.out.println("the output is " +output);
	return output;



				
	}
	
	private static Book createBook(String[] metadata)

	{
		
	int id =Integer.parseInt(metadata[0]);	
	String name = metadata[1];
	int price = Integer.parseInt(metadata[2]);
	int Amount =Integer.parseInt(metadata[3]);	
	String Topic = metadata[4]; // create and return book of this metadata 
	return new Book(id,name, price,Amount,Topic );
	} 


} 



