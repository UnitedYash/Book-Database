//class for storing info about a book
public class Book
{
  private String strAuthor;
  private String strTitle;
  private String strISBN;
  private String strComments;
  public Book(String strBookInfo)
  {
    String [] strSplitBook = strBookInfo.split(",");
    this.strAuthor = strSplitBook[0];
    this.strTitle = strSplitBook[1];
    this.strISBN = strSplitBook[2];
    this.strComments = strSplitBook[3];
  }
  /*
  returns the author of the book
  */
  public String getAuthor()
  {
    return strAuthor;
  }
  /*
  returns the title of the book
  */
  public String getTitle()
  {
    return strTitle;
  }
  /*
  returns the ISBN of the book
  */
  public String getISBN()
  {
    return strISBN;
  }
  /*
  returns the comments of the book
  */
  public String getComments()
  {
    return strComments;
  }

  
}



