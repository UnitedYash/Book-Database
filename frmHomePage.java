import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.ImageIcon;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class frmHomePage extends Algorithm
{
  

 
  JTextArea txtRecBooks, txtSearchResults;
  JLabel lblWelcome, lblTopPicks;
  //to store the logged in user
  UserInfo currUser;
  //to store the data
  private Map<UserInfo, HashMap<Book, Integer>> userBookRating;
  JButton btnAddBook, btnAddReview, btnLogOut, btnSeeReviews, btnSearch;
  JTextField txtSearch;
  
  public frmHomePage(UserInfo loggedInUser, Map<UserInfo, HashMap<Book, Integer>> loadData)
  {
    //gets the data from algortihm to later recommend books
    super(loadData, loggedInUser);
    setSize(500,500);
    setLayout(null);
    Color myColor = new Color(105, 150, 137);
    getContentPane().setBackground(myColor);
    userBookRating = loadData;
    currUser = loggedInUser;
    txtRecBooks = new JTextArea();
    txtRecBooks.setSize(350,150);
    txtRecBooks.setLocation(10,130);
    add(txtRecBooks);

    lblWelcome = new JLabel("Welcome Back, " + currUser.getUsername());
    lblWelcome.setFont(new Font("Comic Sans", Font.BOLD, 20));
    lblWelcome.setSize(500,100);
    lblWelcome.setLocation(10, 10);
    add(lblWelcome);

    lblTopPicks = new JLabel("Here are our top picks: ");
    lblTopPicks.setSize(200,100);
    lblTopPicks.setLocation(10, 60);
    lblTopPicks.setFont(new Font("SansSerif", Font.BOLD, 14));
    add(lblTopPicks);

    btnAddBook = new JButton("Add Book");
    btnAddBook.setSize(120,30);
    btnAddBook.setLocation(375,110);
    btnAddBook.setActionCommand("addBook");
    btnAddBook.addActionListener(this);
    add(btnAddBook);

    btnLogOut = new JButton("Logout");
    btnLogOut.setSize(100,30);
    btnLogOut.setLocation(370, 20);
    btnLogOut.setActionCommand("logOut");
    btnLogOut.addActionListener(this);
    add(btnLogOut);

    btnAddReview = new JButton("Add Review");
    btnAddReview.setSize(120,30);
    btnAddReview.setLocation(375,175);
    btnAddReview.setActionCommand("addReview");
    btnAddReview.addActionListener(this);
    add(btnAddReview);

    btnSeeReviews = new JButton("See Reviews");
    btnSeeReviews.setSize(120,30);
    btnSeeReviews.setLocation(375,250);
    btnSeeReviews.setActionCommand("seeReviews");
    btnSeeReviews.addActionListener(this);
    btnSeeReviews.setFont(new Font("Arial", Font.BOLD, 10));
    add(btnSeeReviews);


    txtSearch = new JTextField();
    txtSearch.setSize(200,20);
    txtSearch.setLocation(10,295);
    add(txtSearch);

    btnSearch = new JButton("Search");
    btnSearch.setSize(100,20);
    btnSearch.setLocation(240, 295);
    btnSearch.setFont(new Font("Arial", Font.BOLD, 10));
    btnSearch.setActionCommand("search");
    btnSearch.addActionListener(this);
    add(btnSearch);

    txtSearchResults = new JTextArea();
    txtSearchResults.setSize(470,150);
    txtSearchResults.setLocation(10,320);
    add(txtSearchResults);


    
    
    
    
    
    
    //gets best best books for user
    ArrayList<Book> bestBooks = getBestBook();
    //checks whther there are no books to recomend
    if (bestBooks.size() == 0)
    {
      txtRecBooks.setText("No Books to recomend, maybe add a review");
    }
    //if there is only one book to recomend
    else if(bestBooks.size() == 1)
    {
      txtRecBooks.setText(bestBooks.get(0).getTitle() + " by " + bestBooks.get(0).getAuthor() + "\n\t ISBN: " + bestBooks.get(0).getISBN() + "\n" + bestBooks.get(0).getComments() + "\n");
    }
    //only two books to recommend
    else if (bestBooks.size() == 2)
    {
      txtRecBooks.setText(bestBooks.get(0).getTitle() + " by " + bestBooks.get(0).getAuthor() + "\n\t ISBN: " + bestBooks.get(0).getISBN() + "\n" + bestBooks.get(0).getComments() + "\n" + bestBooks.get(1).getTitle() + " by " + bestBooks.get(1).getAuthor() + "\n\t ISBN: " + bestBooks.get(1).getISBN() + "\n" + bestBooks.get(1).getComments()  + "\n\n");
    }
    //recommends 3 books
    else
    {
      txtRecBooks.setText(bestBooks.get(0).getTitle() + " by " + bestBooks.get(0).getAuthor() + "\n\t ISBN: " + bestBooks.get(0).getISBN() + "\n" + bestBooks.get(0).getComments() + "\n" + bestBooks.get(1).getTitle() + " by " + bestBooks.get(1).getAuthor() + "\n\t ISBN: " + bestBooks.get(1).getISBN() + "\n" + bestBooks.get(1).getComments()  + "\n\n" + bestBooks.get(2).getTitle() + " by " + bestBooks.get(2).getAuthor() + "\n\t ISBN: " + bestBooks.get(2).getISBN() +"\n" + bestBooks.get(2).getComments());
    }
    

  
    
  }
  public void actionPerformed(ActionEvent e)
  {
    if (e.getActionCommand().equals("logOut"))
    {
      //goes back to logout page if user loggs out
      frmLoginPage logOut = new frmLoginPage(userBookRating);
      logOut.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      logOut.setSize(500,500);//set the frame size
      logOut.setVisible((true));
      this.dispose();
    }
    else if (e.getActionCommand().equals("addBook"))
    {
      //goes to addbook page if user adds book
      frmAddBook addBookPage = new frmAddBook(userBookRating, currUser);
      addBookPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      addBookPage.setSize(500,500);//set the frame size
      addBookPage.setVisible((true));
      this.dispose();


    }
    else if (e.getActionCommand().equals("addReview"))
    {
      //goes to review book page if user wants to change review
      frmAddReview addReviewPage = new frmAddReview(userBookRating, currUser);
      addReviewPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      addReviewPage.setSize(500,500);//set the frame size
      addReviewPage.setVisible((true));
      this.dispose();


    }
    else if (e.getActionCommand().equals("search"))
    {
      //search to get the search results
      HashMap<Book,Integer> theBookList = searchResults(txtSearch.getText());
      String strOutput= "";
      //prints the name of book, author, ISBN , their review of that book and the comments for that book
      for (Book currBook: theBookList.keySet())
      {
        strOutput += currBook.getTitle() + " by " + currBook.getAuthor() + ", your rating is:  " + theBookList.get(currBook) + "\n\t" + "ISBN: " + currBook.getISBN() + "\n" + currBook.getComments() + "\n";
      }
      txtSearchResults.setText(strOutput);
      
      
    }

    

    else if (e.getActionCommand().equals("seeReviews"))
    {
      //lets user see some of their reviews if they clicked see reviews
      frmSeeReviews addSeeReviewsPage = new frmSeeReviews(currUser, userBookRating);
      addSeeReviewsPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      addSeeReviewsPage.setSize(500,500);//set the frame size
      addSeeReviewsPage.setVisible((true));
      this.dispose();

    }
    
  }
  /*
  @ param strBookName is a string which stores the book name of the searched book
  returns a hashmap with books that have some of the same chacters
  */
  public HashMap<Book, Integer> searchResults(String strBookName)
  {
    //initilizes the hashmap
    HashMap<Book, Integer> getSearchResults = new HashMap<Book, Integer>();
    //makes the name lower case to make it more easy to search
    String strUserBook = strBookName.toLowerCase();
    //gets the length of the book name
    int intLength = strUserBook.length();
    //loops through books in the data base
    for (Book currBook: userBookRating.get(currUser).keySet())
    {
      //checks whether the title is less than the currbooks title to avoid errors
      if (currBook.getTitle().length() > intLength)
      {
        //checks whether the substring of the book name is found
        if (currBook.getTitle().substring(0, intLength).toLowerCase().equals(strUserBook))
        {
          getSearchResults.put(currBook,userBookRating.get(currUser).get(currBook));
        }
      }
      else
      {
        //checks whether the book is found
        if (currBook.getTitle().toLowerCase().equals(strUserBook))
        {
          getSearchResults.put(currBook, userBookRating.get(currUser).get(currBook));
        }
        
      }
    }
    
    return getSearchResults;
  }
}
