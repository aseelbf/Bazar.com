import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList; 
import java.util.List;

public class CSVReaderInJava 
{
	public static void main(String... args)
{
	
		
		System.out.print(SearchByTopic("distributed systems"));
		
		
		/*List<Book> books = readBooksFromCSV("books.txt"); // let's print all the person read from CSV file
for (Book b : books) 
{ 
	System.out.println(b); 
} 

*/
}
	


private static List<Book> readBooksFromCSV(String fileName) 
{ 
	List<Book> books = new ArrayList<>(); 
	Path pathToFile = Paths.get(fileName);
// create an instance of BufferedReader 
// using try with resource, Java 7 feature to close resources
try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) 
{
	// read the first line from the text file 
	String line = br.readLine(); // loop until all lines are read 
	while (line != null) 
	{ 
		// use string.split to load a string array with the values from 
		// each line of 
		// the file, using a comma as the delimiter 
		String[] attributes = line.split(",");
		Book book = createBook(attributes); 
		// adding book into Arraylist
		 books.add(book); 
		// read next line before looping 
		// if end of file reached, line would be null
		line = br.readLine(); 
		}
	} 
catch (IOException ioe)
{
	ioe.printStackTrace(); 
}
return books; 
}


public static String SearchByTopic(String Topic)
{
	String bookData = "This topic is not valid!";
	List<Book> books = readBooksFromCSV("books.txt");
	for (Book book : books) 
	{ 
		
		
				if (Topic==book.getTopic())
				{
					
					bookData= "ID: "+ book.getId() + ", Name: " + book.getName() + ", Amount: " + book.getAmount() + " Price: "+book.getPrice();
				}

	}
		return bookData;
	
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



class Book
{
	private int id;
	private String name;
	private int price;
	private int Amount;
	private String Topic;
	public Book(int id,String name, int price, int Amount,String Topic)
	{ 
		this.id=id; this.name = name; this.price = price; this.Amount=Amount; this.Topic=Topic;
		
	} 
	public int getId() { return id; } 
	public String getName() { return name; } 
	public int getPrice() { return price; } 
	public int getAmount() { return Amount; } 
	public String getTopic() { return Topic; } 
	
	public void setName(String name) { this.name = name; }
	public void setPrice(int price) { this.price = price; }

	
@Override 
public String toString()
{ return "Book [name=" + name + ", price=" + price + ", price=" + price  + ", Amount=" + Amount + ", Topic=" + Topic+ "]"; }




}
	


