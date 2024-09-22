
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.ImageIcon;
import java.io.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class frmAddReview extends JFrame implements ActionListener
{
  //map to store all the data
  private Map<UserInfo, HashMap<Book, Integer>> userBookRating;
  UserInfo thisUser;

  JLabel lblName;
  JLabel lblAuthor;
  JLabel lblISBN;
  JLabel lblUserRating;
  JLabel lblComments;
  JLabel lblAddBook;
  JLabel lblDate;
  JTextField txtName, txtAuthor, txtISBN, txtRating, txtComments;
  JButton btnAddBook;
  //string of files where to make store which file to make changes to
  String strRatingsFile;
  String strBookFile;
  String strComments;
  //array to store the books
  ArrayList<Book> arrayOfBooks = new ArrayList<Book>();
  //array to store the changed review
  ArrayList<String> changeRes = new ArrayList<String>();
  
  frmLoadData getData;
  JButton btnBack;

  public frmAddReview(Map<UserInfo, HashMap<Book, Integer>> loadData, UserInfo loggedInUser)
  {
    setSize(500,500);
    setLayout(null);
    //gets the logged in user
    thisUser = loggedInUser;
    //gets the data
    userBookRating = loadData;

    //used to get the ratings and bookfile
    getData = new frmLoadData();
    strBookFile = getData.strBookFile;

    strRatingsFile = getData.strRatingsFile;




    lblAddBook = new JLabel("Add Review!");
    lblAddBook.setFont(new Font("Comic Sans", Font.BOLD, 20));
    lblAddBook.setSize(500,100);
    lblAddBook.setLocation(20, 10);
    add(lblAddBook);

    lblName = new JLabel("Enter Book Name: ");
    lblName.setSize(300,50);
    lblName.setLocation(20,100);
    add(lblName);

    lblAuthor = new JLabel("And Enter Author Name: ");
    lblAuthor.setSize(300,50);
    lblAuthor.setLocation(20, 150);
    add(lblAuthor);

    lblISBN = new JLabel(" or Enter ISBN: ");
    lblISBN.setSize(300,50);
    lblISBN.setLocation(20,200);
    add(lblISBN);

    lblUserRating = new JLabel("Your Rating enter one of (-5, -3 ,0 , 1, 3, 5): ");
    lblUserRating.setSize(400,50);
    lblUserRating.setLocation(20,250);
    add(lblUserRating);


    txtName = new JTextField();
    txtName.setSize(110,20);
    txtName.setLocation(20,140);
    add(txtName);

    txtAuthor = new JTextField();
    txtAuthor.setSize(110,20);
    txtAuthor.setLocation(20,190);
    add(txtAuthor);

    txtISBN = new JTextField();
    txtISBN.setSize(110,20);
    txtISBN.setLocation(20,240);
    add(txtISBN);

    txtRating = new JTextField();
    txtRating.setSize(110,20);
    txtRating.setLocation(20,285);
    add(txtRating);

    btnAddBook = new JButton("Add Review!");
    btnAddBook.setSize(200,50);
    btnAddBook.setLocation(300,100);
    btnAddBook.setActionCommand("addReview");
    btnAddBook.addActionListener(this);
    add(btnAddBook);

    btnBack = new JButton("---->");
    btnBack.setSize(75,50);
    btnBack.setLocation(400, 20);
    btnBack.setActionCommand("back");
    btnBack.addActionListener(this);
    add(btnBack);
    //loops throught the books to add it to the array of all books
    for (Book currBook: userBookRating.get(thisUser).keySet())
    {
      arrayOfBooks.add(currBook);
    }




  }

  public void actionPerformed(ActionEvent e)  
  {
    
    if (e.getActionCommand().equals("back"))
    {
      //goes the the home page if user clicks back button, and stores the new data by wrtiing it in the files
      frmLoadData getNewData = new frmLoadData();
      getNewData.dataWriting(strBookFile,strRatingsFile);
      //gets the new data for all ratings
      userBookRating = getNewData.getData();
      //gets the user again since the memory location would have changed
      thisUser = getNewData.getUser(thisUser.getUsername());
      frmHomePage goHomePage = new frmHomePage(thisUser, userBookRating);
      goHomePage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      goHomePage.setSize(500,500);//set the frame size
      goHomePage.setVisible((true));
      this.dispose();
      
    }
    if (e.getActionCommand().equals("addReview"))
    {
      //makes sure that the isbn is entered
      if(txtISBN.getText().length() != 0)
      {
        //makes sure the ratings are right
        if (txtRating.getText().equals("-5") || txtRating.getText().equals("-3") || txtRating.getText().equals("0") ||txtRating.getText().equals("1") ||txtRating.getText().equals("3") ||txtRating.getText().equals("5"))
        {
          //checks whether the book is found
          if (isBookFoundRecursively("null", "null", txtISBN.getText(), arrayOfBooks.size() - 1, arrayOfBooks))
          {
            //adds the review
            addReview(thisUser, "null", "null", txtISBN.getText(), txtRating.getText(), true);
            JOptionPane.showMessageDialog(null,"Added Review!");

          }
          else JOptionPane.showMessageDialog(null,"ISBN not Found");
        }
        else JOptionPane.showMessageDialog(null,"Make sure rating is correct!");
        
      }
        //checks wheter the book name and author was entered
      else if (txtName.getText().length() != 0 && txtAuthor.getText().length() != 0)
      {
        //makes sure the rating is right
        if (txtRating.getText().equals("-5") || txtRating.getText().equals("-3") || txtRating.getText().equals("0") ||txtRating.getText().equals("1") ||txtRating.getText().equals("3") ||txtRating.getText().equals("5"))
        {
          //makes sure that book is in the database
          if (isBookFoundRecursively(txtName.getText(), txtAuthor.getText(), "null", arrayOfBooks.size() - 1, arrayOfBooks))
          {
            //adds the review
            addReview(thisUser, txtName.getText(), txtAuthor.getText(), "null", txtRating.getText(), false);
            JOptionPane.showMessageDialog(null,"Added Review");

          }
          else JOptionPane.showMessageDialog(null,"Book and Author not Found!");
        }
        else JOptionPane.showMessageDialog(null,"Make sure rating is correct!");
      }
      else
      {
        JOptionPane.showMessageDialog(null,"Must Fill out a either name and author or ISBN");
      }
      

    }
  }

  
  /*
  @ param currUser is userinfo that stores the user for which you want to change the review for
  @ param strTitle is the title of the book you want to change review for
  @ param strAuthor is the author name of the book you want to change review for
  @ param strISBN stores the isbn of book
  @ param intrating from user is the new rating user wanted to change to
  @ param isISBN is a boolean to know wheter the user entered in the ISBN or author and title
  */
  public void addReview(UserInfo currUser, String strTitle, String strAuthor, String strISBN, String intRatingFromUser, boolean isISBN)
  {
    try
    {
      //reads the book file
      FileReader bookFR = new FileReader(strBookFile);
      BufferedReader bookBR = new BufferedReader(bookFR);
      String strLine = bookBR.readLine();
      String [] strBookSplit;
      int intBookIndex = 0;
      //sets a booklean to check whether the book is found in the file
      boolean isFound = false;
      while (isISBN && strLine != null && isFound == false)
      {
        strBookSplit = strLine.split(",");
        if (strBookSplit[2].equals(strISBN))
        {
          isFound = true;
        }
        else 
        {
          //stores the line where the book is, so ti can make changes
          intBookIndex += 1;
          strLine = bookBR.readLine();
        }
      }
      //loops through the books 
      while (isISBN == false && strLine != null && isFound == false)
      {
        //splits the author title and comments of book
        strBookSplit = strLine.split(",");
        //checks whether the book and title is found
        if (strBookSplit[0].equals(strAuthor) && strBookSplit[1].equals(strTitle))
        {
          isFound = true;
        }
        else
        {
          //stores the line of where the book is found
          intBookIndex += 1;
          strLine = bookBR.readLine();
        }
        
      }
      bookBR.close();
      //reads the rating file
      FileReader ratingsFR = new FileReader(strRatingsFile);
      BufferedReader ratingsBR = new BufferedReader(ratingsFR);
      strLine = ratingsBR.readLine();
      String [] strRatingSplit;
      String [] strUserSplit;
      while (strLine != null)
      {
        //adds the the line to the array(since first line is just the user and you dont make any changes there)
        changeRes.add(strLine);
        //splits teh user
        strUserSplit = strLine.split(",");
        //checks whether the user which its reading is the logged in user
        if (strUserSplit[0].equals(currUser.getUsername()))
        {
          //reads the rating laine to make the change
          strLine = ratingsBR.readLine();
          strRatingSplit = strLine.split(" ");
          //changes the rating at the index where the book is
          strRatingSplit[intBookIndex] = intRatingFromUser;
          strLine = "";
          //loops through the ratings and add it to strline so we can add it to the array list to print it later
          for (int i = 0; i < strRatingSplit.length; i++)
          {
            strLine += strRatingSplit[i] + " ";
            
          }
          changeRes.add(strLine);
          strLine = ratingsBR.readLine();
          
        }
        else
        {
          //just reads the line since the user is not the logged in user
          strLine = ratingsBR.readLine();
          changeRes.add(strLine);
          strLine = ratingsBR.readLine();
        }
        
        
      }
      //re writes the ratings file
      FileWriter ratingsFileWrite = new FileWriter(strRatingsFile);
      PrintWriter ratingsPR = new PrintWriter(ratingsFileWrite);
      for (int i = 0; i < changeRes.size(); i++)
      {
        ratingsPR.println(changeRes.get(i));
      }
      ratingsPR.close();
      changeRes = new ArrayList<String>();

      
      
    }
    catch(IOException e1){}
      
    
    

  }
  /*
  @ param strBookTitle is the book title for the book you want to change review
  @ param strBookAuthor is the book author for the book you want to change review
  @ param strISBN is the ISBN of book you want to change review for
  @ param intIndex is the index in the arraylist
  @ param bookArray is the array with the books
  returns a boolean to know whether the book is found recrusvely
  */
  public boolean isBookFoundRecursively(String strBookTitle, String strBookAuthor, String strISBN, int intIndex, ArrayList<Book> bookArray)
  {
    //base case
    if (intIndex == -1)
    {
      return false;
    }
    if (bookArray.get(intIndex).getISBN().equals(strISBN) || bookArray.get(intIndex).getAuthor().equals(strBookAuthor) && bookArray.get(intIndex).getTitle().equals(strBookTitle))
    {
      return true;
    }
    return isBookFoundRecursively(strBookTitle, strBookAuthor, strISBN, intIndex - 1, bookArray); 
  }
}

