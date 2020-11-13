public class Book 
{
 private String Name;
  private int number;
  private double cost;
  private String topic;
   
  // getter/setter
}

public class CatalogServer
 {
 
 
  // returns number of this book in stock.
  public int Stock(String bookTopic) { .. }
   
  // returns cost of this book
  public double getCost(String bookName) { .. }
 
  // creates a new user
  public String getTopic(String bookName) { .. }
 
  
}

public class OrderServer 
{
	//returns all orders
	public List<Book> getAllOrders() { .. }
	
}



public class FrontEnd
{
	//returns all intries and number of each one in stock
	public String search( String topic)
	{
		//will use Stock() from CatalogServer
	}
	
}






