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
		
		 
	


System.out.println(readBooksFromCSV("Books.txt"));
Buy("Books.txt",1);

}
	


private static String readBooksFromCSV(String fileName) 
{ 
	String output="";
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



for (Book book : books) 
{ 
	
	output+=("ID: "+ book.getId() + ", Name: " + book.getName() + ", Amount: " + book.getAmount() + ", Price: "+book.getPrice()+"\n"); 
} 




         return output; 
 }

//**************************************************************************************
public static Object SearchByTopic(String fileName,String Topic)
{
	String output=""; 
	List<Book> bookData = new ArrayList<>(); 
	List<Book> books = new ArrayList<>(); 
	Path pathToFile = Paths.get(fileName);

try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) 
{

	String line = br.readLine(); 
	while (line != null) 
	{ 
		String[] attributes = line.split(",");
		Book book = createBook(attributes); 
		
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
			if (Topic.equals(b.getTopic()))
			{
				
				bookData.add(b);
				output+=b.getId()+","+b.getName()+","+b.getAmount() +"," + b.getPrice()+"/";
				
			}

}
return output;



			
}

//*********************************************************************************************************

public static Object LookUp(String fileName,int id)
{
	
	String bookData = ""; 
	List<Book> books = new ArrayList<>(); 
	Path pathToFile = Paths.get(fileName);

try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) 
{

	String line = br.readLine(); 
	while (line != null) 
	{ 
		String[] attributes = line.split(",");
		Book book = createBook(attributes); 
		
		 books.add(book); 
	
		line = br.readLine(); 
		
		}

	
	} 
catch (IOException ioe)
{
	ioe.printStackTrace(); 
}
	

for (Book book : books) 
{ 
			if (id==book.getId())
			{
				
			bookData+= book.getId()+","+book.getName() + "," + book.getAmount() + ","+book.getPrice();
			break;
				
			}

}

return bookData;
			

}

//************************************************************************************************************

public static Object Buy(String fileName,int id)
{
	String output="";
	List<Book> books = new ArrayList<>(); 
	Path pathToFile = Paths.get(fileName);

try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) 
{

	String line = br.readLine(); 
	while (line != null) 
	{ 
		String[] attributes = line.split(",");
		Book book = createBook(attributes); 
		
		 books.add(book); 
	
		line = br.readLine(); 
		
		}

	
	} 
catch (IOException ioe)
{
	ioe.printStackTrace(); 
}
	

for (Book book : books) 
{ 
			if (id==book.getId() && book.getAmount()>0)
			{
				
			book.BuyBook(id);
			output="Buying book done successfully";
				break;
			}
			else
				output="This book is out of stock";

}
return output;

}



//**********************************************************************************************************
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
	private int id=0;
	private String name="";
	private int price=0;
	private int Amount=0;
	private String Topic="";
	public Book(int id,String name, int price, int Amount,String Topic)
	{ 
		this.id=id; this.name = name; this.price = price; this.Amount=Amount; this.Topic=Topic;
		
	} 
	
	public Book(int id,String name,int Amount,int price)
	{
		this.name = name; this.price = price; this.Amount=Amount;
	}
	
	
	public int getId() { return id; } 
	public String getName() { return name; } 
	public int getPrice() { return price; } 
	public int getAmount() { return Amount; } 
	public String getTopic() { return Topic; } 
	
	public void setName(String name) { this.name = name; }
	public void setPrice(int price) { this.price = price; }

	
	
	public void BuyBook(int ID)
	{
	if (this.id==ID)	
	{
		this.Amount-=1;
		
	}
	
	System.out.println(this);
	
	}
	
	
	
@Override 
public String toString()

{ return id +","+ name + "," + Amount +","+ price; }

}
