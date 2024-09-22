import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.ImageIcon;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
//class to load and edit the data
public class frmLoadData extends JFrame implements ActionListener
{
  //initilize variables
  JLabel lblBookFile, lblRatingFile, lblAddTXT1, lblAddTXT2;
  JTextField txtBookFile, txtRatingFile;
  JButton btnAddData, btnBack;
  public static String strBookFile = "book", strRatingsFile = "ratings";
  //initilizes an array to store the books, used later to add books to the file
  ArrayList<String> strListBooks = new ArrayList<String>();
  //initilizes an array to store ratings of each user from the file, used to make changes to the file
  ArrayList<String> strListOfRatings = new ArrayList<String>();
  //initilizes strListOfUsers to store all the usernames
  ArrayList<String> strListOfUsers = new ArrayList<String>();
  //initlizes a list to keep track of all isbns, inorder to add them to the book file
  ArrayList<String> strISBNList = new ArrayList<String>();
  //thisUser is used later to get the username of the logged in user
  UserInfo thisUser;
  //this is a map that stores the User(their password, username, and name) that corresponds to a HashMap that contains each book, and the users rating
  public Map<UserInfo, HashMap<Book, Integer>> userBookRating = new HashMap<UserInfo, HashMap<Book, Integer>>();
  /*
  @ param strBookFile is a string with the bookFile
  @ param strRatingFile is a string with thte rating file
  returns a boolean, which shows whether a the files exist or not.
  */
  public boolean doesFileExist(String strBookFile, String strRatingFile)
  {
    //itilizes a variable to false to signal to whether the file is found
    boolean isFileExist = false;
    //tries to read the bookFile, since if it cannot be found it will give an IO exception, then it sets isFileExist to false
    try
    {
      FileReader fr = new FileReader(strBookFile);
      BufferedReader br = new BufferedReader(fr);
      //checks whether the ratings file exists
      try
      {
        FileReader dfr = new FileReader(strRatingFile);
        BufferedReader dbr = new BufferedReader(dfr);
        isFileExist = true;
      }
      catch(IOException e1){}

    }
    catch(IOException e1){}
    return isFileExist;
  }
  /*
  returns the Map which stores all the data
  */
  public Map<UserInfo, HashMap<Book, Integer>> getData()
  {
    return userBookRating;
  }
  /*
  @ param strWord is a string
  returns the string encrypted(by shifting each chartacter 5 ascii values right)
  */
  public String strEncrypted(String strWord)
  {
    //initilizes temp integer
    int intTemp;
    //initilizes a temp character
    char charTemp;
    //string to store the final encrypted password
    String strEncryptedFinal = "";
    //a buffer to know how much to shift each ascii value by
    int intBuffer = 5;
    //loops throught he characters in strWord
    for (int i = 0; i < strWord.length(); i++)
    {
      //keeps the character as a temp variable
      charTemp = strWord.charAt(i);
      //stores the chacter as an ascii value
      intTemp = charTemp;
      //makes sure the ascii value does not go above 122 when adding it to the buffer
      if (intTemp + intBuffer > 122)
      {
        //if its greater than 122, it just adds it from 65
        intTemp = 5 - (122 - intTemp) + 65;
        //turns the ascii value back to chacter
        charTemp = (char)intTemp;
        //adds it back to the final string
        strEncryptedFinal += charTemp;
      }
      else
      {
        //just adds the buffer to the ascii value and converts it to a char
        charTemp = (char)(intTemp + intBuffer);
        strEncryptedFinal += charTemp;
      }
    }
    return strEncryptedFinal;
  }

  public frmLoadData()
  {
    Color myColor = new Color(105, 150, 137);
    getContentPane().setBackground(myColor);
    setSize(500,500);
    setLayout(null);
    lblBookFile = new JLabel("Enter Name of the Book file: ");
    lblBookFile.setSize(400, 50);
    lblBookFile.setLocation(40, 70);
    add(lblBookFile);

    lblRatingFile = new JLabel("Enter Name of the Ratings file: ");
    lblRatingFile.setSize(400, 50);
    lblRatingFile.setLocation(40, 160);
    add(lblRatingFile);

    lblAddTXT1 = new JLabel(".txt");
    lblAddTXT1.setSize(50,50);
    lblAddTXT1.setLocation(220,120);
    add(lblAddTXT1);

    lblAddTXT2 = new JLabel(".txt");
    lblAddTXT2.setSize(50,50);
    lblAddTXT2.setLocation(220,200);
  add(lblAddTXT2);

    txtBookFile = new JTextField();
    txtBookFile.setSize(180, 35);
    txtBookFile.setLocation(40, 120);
    add(txtBookFile);

    txtRatingFile = new JTextField();
    txtRatingFile.setSize(180, 35);
    txtRatingFile.setLocation(40, 200);
    add(txtRatingFile);    

    btnAddData = new JButton("Add Data");
    btnAddData.setSize(150,40);
    btnAddData.setLocation(140, 280);
    btnAddData.setActionCommand("addData");
    btnAddData.addActionListener(this);
    add(btnAddData);

    btnBack = new JButton("---->");
    btnBack.setSize(75,50);
    btnBack.setLocation(400, 20);
    btnBack.setActionCommand("back");
    btnBack.addActionListener(this);
    add(btnBack);
  }
  public void actionPerformed(ActionEvent e)
  {
    
    if(e.getActionCommand().equals("back"))
      {
        //goes to sign in page if user goes back
        frmSignInPage signInPage = new frmSignInPage(userBookRating);
        signInPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        signInPage.setSize(500,500);//set the frame size
        signInPage.setVisible((true));
        this.dispose();

      }
    if (e.getActionCommand().equals("addData"))
    {
      //checks whetehr file exists
      if (doesFileExist(txtBookFile.getText() + ".txt",  txtRatingFile.getText() + ".txt"))
      {
        //writes the data and edits the data if file is found
        userBookRating = new HashMap<UserInfo, HashMap<Book, Integer>>();
        fileEditing(txtBookFile.getText() + ".txt", txtRatingFile.getText() + ".txt");
        dataWriting(txtBookFile.getText() + ".txt", txtRatingFile.getText() + ".txt");  

      }
      else
      {
        JOptionPane.showMessageDialog(null, "File(s) not found");
      }
    }
  }
  
  /*
  @ param strBookFile is the bookfile you want to edit
  @ param ratings file is the ratigs file you want to edit
  edits the files to the proper format
  */
  public void fileEditing(String bookFile, String ratingsFile)
  {
      try
      {
        //reads the book file and ratings file
        FileReader bookfr = new FileReader(bookFile);
        BufferedReader booksbr = new BufferedReader(bookfr);
        FileReader ratingsfr = new FileReader(ratingsFile);
        BufferedReader ratingsbr = new BufferedReader(ratingsfr);
        //array list to format the lines and add them later
        ArrayList<String> strFormatedListBooks = new ArrayList<String>();
        ArrayList<String> strFormatedListUsernames = new ArrayList<String>();
        //checks whether the book file is already formatted
        if (isBookFormatted(bookFile) == false)
        {
          String strLine = booksbr.readLine();
          String [] strBookLine;
          //sets an integer to give isbn
          int intNum = 0;
          while (strLine != null)
          {
            //adds it to the array
            strListBooks.add(strLine);
            strLine = booksbr.readLine();
          }
          booksbr.close();
          for (String bookName: strListBooks)
          {
            //splits the array to look for whether its formatted
            strBookLine = bookName.split(",");
            if (strBookLine.length < 4)
            {
              //formats the arraylist
              bookName += "," + intNum + ", No Comments";
              intNum += 1;
              strFormatedListBooks.add(bookName);
              strISBNList.add(intNum + "");
            }
  
          }
          FileWriter bookfw = new FileWriter(bookFile);
          PrintWriter bookpw = new PrintWriter(bookfw);
          //rewrites the book file
          for (String strBookFormatted: strFormatedListBooks)
          {
            bookpw.println(strBookFormatted);
          }
        bookpw.close(); 
        }
        //checks whether the ratings file is formatted
        if (isUsernameFormatted(ratingsFile) == false)
        {
          String strLine = ratingsbr.readLine();
          //intIndex to keep track of whether you are reading the username, or ratings
          int intIndex = 0;
          while (strLine != null)
          {
            //if its the first line its going to be username, second line will be ratings
            if (intIndex % 2 == 0) strListOfUsers.add(strLine);
            else strListOfRatings.add(strLine);
            intIndex += 1;
            strLine = ratingsbr.readLine();
  
          }
          
          String [] strUserLine;
          FileWriter ratingsfw = new FileWriter(ratingsFile);
          PrintWriter ratingspw = new PrintWriter(ratingsfw);
          String strFormatUsername = "";
          for(String strUsername: strListOfUsers)
          {
            //rewrites the listOfusers and formats it
            strUserLine = strUsername.split(",");
            if (strUserLine.length < 3)
            {
              //encryptes the password and adds the no name 
              strFormatUsername = strUsername + ", No name," + strEncrypted("123");
              strFormatedListUsernames.add(strFormatUsername);
            }
          }
          intIndex = 0;
          //loops throught the formatted list to add it to the file
          for (String strUsernameFormated: strFormatedListUsernames)
          {
            ratingspw.println(strUsernameFormated);
            ratingspw.println(strListOfRatings.get(intIndex));
            intIndex += 1;
          }
          ratingspw.close();         
        }
      }
    catch(IOException e1)
    {
    }
    //sets the name and ratings file
    this.strBookFile = bookFile;
    this.strRatingsFile = ratingsFile;
  
  }   

  /*
  @ param bookFile is the file with books
  @ param ratingFile is the file with ratings
  writes the data into the hashmap
  */
  public void dataWriting(String bookFile, String ratingFile)
  {
    try
    {
      FileReader bookFr = new FileReader(bookFile);
      BufferedReader bookBr = new BufferedReader(bookFr);
      FileReader ratingsFr = new FileReader(ratingFile);
      BufferedReader ratingsBr = new BufferedReader(ratingsFr);
      String strBookLine = bookBr.readLine();
      //arraylist to keep the books
      ArrayList<Book> arrayOfBooks = new ArrayList<Book>();
      //hashmap to store the book and corresponding rating for the book
      HashMap<Book, Integer> bookAndRatings = new HashMap<Book, Integer>();
      Book currBook;
      while (strBookLine != null)
      {
        //adds the book to the arraylist
        currBook = new Book(strBookLine);
        arrayOfBooks.add(currBook);
        strBookLine = bookBr.readLine();
      }
      String strRatingsLine = ratingsBr.readLine();
      String [] strRatingsSplit;
      UserInfo currUser;
      while (strRatingsLine != null)
      {
        
        currUser = new UserInfo(strRatingsLine);
        strRatingsLine = ratingsBr.readLine();
        //splits up the ratings
        strRatingsSplit = strRatingsLine.split(" ");
        for (int i =0; i < strRatingsSplit.length; i++)
        {
          //adds the ratings of the book with the intger the user gave at that index
          bookAndRatings.put(arrayOfBooks.get(i), Integer.parseInt(strRatingsSplit[i]));
        }
        //adds it to the hashmap
        userBookRating.put(currUser, bookAndRatings);
        strRatingsLine = ratingsBr.readLine();
        bookAndRatings = new HashMap<Book, Integer>();
        
        
      }
    }
    catch(IOException e1){}
  }
      
  /*
  @ param strBookFile is the file with books
  returns a boolean that tells you if the file is formatted
  */
  public boolean isBookFormatted(String strBookFile)
  {
    boolean isFormatted = true;
    try
    {
      FileReader fr = new FileReader(strBookFile);
      BufferedReader br = new BufferedReader(fr);
      String [] strList;
      String strLine = br.readLine();
      while (strLine != null && isFormatted)
      {
        //tries to see if it can split it into a list of length 4
        strList = strLine.split(",");
        //if it cant it sets isFormatted to false
        if (strList.length != 4) isFormatted = false;
        strLine = br.readLine();
      }
    }
    catch(IOException e1) {}
    return isFormatted;
  }
  /*
  @ param strRatingsFile is the file with ratings from users1
  returns a boolean to know wheter the file is formatted correctly
  */
  public boolean isUsernameFormatted(String strRatingsFile)
  {
    boolean isFormatted = true;
    try
    { 
      //reads through the file
      FileReader fr = new FileReader(strRatingsFile);
      BufferedReader br = new BufferedReader(fr);
      String [] strList;
      String strLine = br.readLine();
      while (strLine != null)
      {
        //tries to split username in the file
        strList = strLine.split(",");
        //if its not the correct length, then it says that the file is not formatted correctly
        if (strList.length != 3) isFormatted = false;
        strLine = br.readLine();
        strLine = br.readLine();
        
      }
    }
    catch(IOException e1){}
    return isFormatted;
    
  }
  /*
  @ param strUsername is the username of the user you are looking for
  it returns the username as UserInfo with all of the users information
  */
  public UserInfo getUser(String strUsername)
  {
    //loops throught the hashmap to find user
    for (UserInfo currUser: userBookRating.keySet())
    {
      if (currUser.getUsername().equals(strUsername)) return currUser;
    }
    return thisUser;
  }
} 
