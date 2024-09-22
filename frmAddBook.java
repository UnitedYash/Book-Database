import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.ImageIcon;
import java.io.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
//frame to add book
public class frmAddBook extends JFrame implements ActionListener
{
  //hashmap to later instance to get all the data
  private Map<UserInfo, HashMap<Book, Integer>> userBookRating;
  //store the logged in user
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
  //files to find where to add the book, and ratings
  String strRatingsFile;
  String strBookFile;
  String strComments;
  JButton btnBack;
  //arraylist that is used later to get all the books in database
  ArrayList<Book> arrayOfBooks = new ArrayList<Book>();
  //arraylist that is used to change the rating of ratings of each user when added a book(add zero to end of each line in ratings.txt)
  ArrayList<String> changeRes = new ArrayList<String>();
  //instance to get data
  frmLoadData getData;
  
  public frmAddBook(Map<UserInfo, HashMap<Book, Integer>> loadData, UserInfo loggedInUser)
  {
    setSize(500,500);
    setLayout(null);
    //sets the logged in user
    thisUser = loggedInUser;
    //loads in the data from before
    userBookRating = loadData;
    //instances the load data 
    getData = new frmLoadData();
    //uses get data to get the files names so later it can write in them
    strBookFile = getData.strBookFile;
    
    strRatingsFile = getData.strRatingsFile;




    lblAddBook = new JLabel("Add Book");
    lblAddBook.setFont(new Font("Comic Sans", Font.BOLD, 20));
    lblAddBook.setSize(500,100);
    lblAddBook.setLocation(20, 10);
    add(lblAddBook);
    
    lblName = new JLabel("Enter Book Name: ");
    lblName.setSize(300,50);
    lblName.setLocation(20,100);
    add(lblName);

    lblAuthor = new JLabel("Enter Author Name: ");
    lblAuthor.setSize(300,50);
    lblAuthor.setLocation(20, 150);
    add(lblAuthor);

    lblUserRating = new JLabel("Your Rating enter one of (-5, -3 ,0 , 1, 3, 5): ");
    lblUserRating.setSize(400,50);
    lblUserRating.setLocation(20,250);
    add(lblUserRating);

    lblComments = new JLabel("Enter a short comment(max 30 characters): ");
    lblComments.setSize(400,50);
    lblComments.setLocation(20,300);
    add(lblComments);

    txtName = new JTextField();
    txtName.setSize(110,20);
    txtName.setLocation(20,140);
    add(txtName);

    txtAuthor = new JTextField();
    txtAuthor.setSize(110,20);
    txtAuthor.setLocation(20,190);
    add(txtAuthor);

    txtRating = new JTextField();
    txtRating.setSize(110,20);
    txtRating.setLocation(20,285);
    add(txtRating);

    txtComments = new JTextField();
    txtComments.setSize(200,20);
    txtComments.setLocation(20,350);
    add(txtComments);

    btnAddBook = new JButton("Add Book!");
    btnAddBook.setSize(200,50);
    btnAddBook.setLocation(300,100);
    btnAddBook.setActionCommand("addBook");
    btnAddBook.addActionListener(this);
    add(btnAddBook);
    
    btnBack = new JButton("---->");
    btnBack.setSize(75,50);
    btnBack.setLocation(400, 20);
    btnBack.setActionCommand("back");
    btnBack.addActionListener(this);
    add(btnBack);

  }

  public void actionPerformed(ActionEvent e)  
  {
    if (e.getActionCommand().equals("back"))
    {
      //stores the data again by writing it into the file
      frmLoadData getNewData = new frmLoadData();
      getNewData.dataWriting(strBookFile,strRatingsFile);
      //makes the new hashmap with the updated data
      userBookRating = getNewData.getData();
      //gets the userinfo again since the memory location of the user is changed after changing the data
      thisUser = getNewData.getUser(thisUser.getUsername());
      //calls homepage to go back to homepage
      frmHomePage goHomePage = new frmHomePage(thisUser, userBookRating);
      goHomePage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      goHomePage.setSize(500,500);//set the frame size
      goHomePage.setVisible((true));
      this.dispose();
    }
    if (e.getActionCommand().equals("addBook"))
    {
      //gets the name of the book
      String strName = txtName.getText();
      //gets the author
      String strAuthor = txtAuthor.getText();
      //gets the rating from user
      String strRating = txtRating.getText();
      //makes a rating variable using to store the rating
      int intRating;
      try
      {
        //tries to parseint the rating, if it can't it sets intrating to -1000 to signal that the user entered in something that is not an integer
        intRating = Integer.parseInt(strRating);
      }
      catch(Exception e1)
      {
        intRating = -1000;
      }
      //instances the comments as default to no comments in case user dosent enter in comments since its optional
      String strComments = "No Comments";
      //checks if the user added comments and stores it
      if (txtComments.getText().length() > 1) strComments = txtComments.getText();
      //checks whther the user entered in the author name and book name, with a rating
      if ((strName.length() > 0) && (strAuthor.length() > 0)  && (strRating.length() > 0))
      {
        //does the valid checks like if rating and comments are valid
        if (strComments.length() <= 30 && (intRating == -5 || intRating == -3 || intRating == 0|| intRating == 1|| intRating == 3|| intRating == 5))
        {
          //checks wheter the book is already in the database
          if (isBookTaken(strName, strAuthor) == false)
          {
            //makes the book from the user
            Book thisBook = new Book(strAuthor + "," + strName + "," + (userBookRating.get(thisUser).size() + 1) + "," + strComments);
            //adds book to the data base
            addBook(thisUser, thisBook, strRating);
            //makes the main data base again since there is a new book added
            getData = new frmLoadData();
            getData.dataWriting(strBookFile, strRatingsFile);
            //stores the new data
            userBookRating = getData.getData();
            //gets the user again since its no longer in the same memory location in the hashmap
            thisUser = getData.getUser(thisUser.getUsername());
            //loops thorugh the books from the logged in user to add the new book to the arraylist
            for (Book currBook: userBookRating.get(thisUser).keySet())
            {
              arrayOfBooks.add(currBook);
            }
            JOptionPane.showMessageDialog(null, "Succesfully added book!");
            
            
              
          }
          else
          {
            JOptionPane.showMessageDialog(null, "Book is Already in DataBase");
          }
        }
        else JOptionPane.showMessageDialog(null, "Comment is too long or Rating is not right");
      }
      else JOptionPane.showMessageDialog(null, "Make sure to fill out every box(comments are optional)");


      
    }
  }
  /*
  @ param strBookName is a string which contains the book name
  @ param strAuthorName is a string that contains the author's name
  returns a boolean which tells you whether the book is already in the database
  */
  public boolean isBookTaken(String strBookName, String strAuthorName)
  {
    //itilizes the boolean which it will return later
    boolean isNameAndAuthorTaken = false;
    //loops throught the books in the database
    for (Book currBook: userBookRating.get(thisUser).keySet())
    {
      //checks if the book is found
      if (currBook.getTitle().equals(strBookName) && currBook.getAuthor().equals(strAuthorName))
      {
        isNameAndAuthorTaken = true;
      }
    }
    return isNameAndAuthorTaken;
  }
  /*
  @ param currUser is UserInfo is who is logged in at the momment
  @ param theBook is a Book that is the book that you want to add
  @ intRatingFromUser is a integer that is the rating which the user gave the book
  */
  public void addBook(UserInfo currUser, Book theBook, String intRatingFromUser)
  {
    try
    {
      //writes the new book to the end of the book file
      FileWriter bookFR = new FileWriter(strBookFile, true);
      PrintWriter bookPR = new PrintWriter(bookFR);
      bookPR.println(theBook.getAuthor() + ","  + theBook.getTitle() + "," + theBook.getISBN() + "," + theBook.getComments());
      bookPR.close();
      //reads throught the ratings file
      FileReader ratingsFR = new FileReader(strRatingsFile);
      BufferedReader ratingsBR = new BufferedReader(ratingsFR);
      String strLine = ratingsBR.readLine();
      //used to store the username, name, and password split up
      String [] strSplitUser;
      
      while (strLine != null)
      {
        //change res is the arraylist with all the ratings, which will be later used to write it in the new ratings file
        changeRes.add(strLine);
        //splits the commas
        strSplitUser = strLine.split(",");
        strLine = ratingsBR.readLine();
        //checks wheter the username which the current line its reading in is the one with the user who added the book
        if (strSplitUser[0].equals(thisUser.getUsername()))
        {
          //adds the rating from the user
          strLine += intRatingFromUser + " ";
          changeRes.add(strLine);
        }
        else
        {
          //since its another user it just adds a zero to the end since the other users arent rating the book at the momment
          strLine += + 0 + " ";
          changeRes.add(strLine);
        }
        strLine = ratingsBR.readLine();
      }
      //filewriter to write the new updated data
      FileWriter ratingsFileWriter = new FileWriter(strRatingsFile);
      PrintWriter ratingsPR = new PrintWriter(ratingsFileWriter);
      //loops throught the arraylist of changed responses
      for (int i = 0; i < changeRes.size(); i++)
      {
        //prints it to the file
        ratingsPR.println(changeRes.get(i));
      }
      ratingsPR.close();
      
      
    }
    catch (IOException e1){}
    //re initlizes the vairables incase another book is added
    changeRes = new ArrayList<String>();
    arrayOfBooks = new ArrayList<Book>();
    

  }
  
  
}
