import java.util.ArrayList;
import java.util.Comparator;

public class Author extends ListViewObject
{
	private ArrayList<Book> bookList = new ArrayList<>();
	public String author;
	
	public Author(String author)
	{
		this.author = author;
	}
	
	public String toString()
	{
		return author;
	}
	
	public void addToList(Book x)
	{
		bookList.add(x);
	}
	
	public void addWholeList(Author list)
	{
		ArrayList<Book> newList = list.getBookList();
		bookList.addAll(newList);
	}
	
	public ArrayList<Book> getBookList()
	{
		return bookList;
	}
	
	public String getAuthor()
	{
		return author;
	}
	
	public void setAuthor(String x)
	{
		author = x;
	}
	
	// http://stackoverflow.com/questions/12575833/java-compare-cannot-be-resolved-to-a-type-error
	public static Comparator<Author> authorComparator = new Comparator<Author>()
	{	
		public int compare(Author a1, Author a2) 
		{
			String author1 = a1.getAuthor().toUpperCase();
			String author2 = a2.getAuthor().toUpperCase();
			
			return author1.compareTo(author2);
		}
	};
}
