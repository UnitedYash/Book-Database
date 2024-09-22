import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.ImageIcon;
import java.io.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
public class frmSeeReviews extends JFrame implements ActionListener
{

  JTextArea txtRead, txtUnread;
  Map<UserInfo, HashMap<Book, Integer>> currUserRatings = new HashMap<UserInfo, HashMap<Book, Integer>>();
  HashMap<Book, Integer> userBookRatings= new HashMap<Book, Integer>();
  UserInfo thisUser;
  ArrayList<Book> listOfUnReadBooks = new ArrayList<Book>();
  ArrayList<String> listOfReadBooks = new ArrayList<String>();
  JLabel lblYourReviews;
  JButton btnBack;
  

  public frmSeeReviews(UserInfo currUser, Map<UserInfo, HashMap<Book, Integer>> loadData)
  {
    //gets logged in user
    thisUser = currUser;
    //gets the data
    currUserRatings = loadData;
    //gets bookratings from user
    userBookRatings = currUserRatings.get(thisUser); 

    setSize(500,500);
    setLayout(null);

    lblYourReviews = new JLabel("Your Reviews");
    lblYourReviews.setSize(200,100);
    lblYourReviews.setLocation(20,0);
    lblYourReviews.setFont(new Font("SansSerif", Font.BOLD, 14));
    add(lblYourReviews);

    
    txtRead = new JTextArea();
    txtRead.setSize(350,390);
    txtRead.setLocation(20,60);
    add(txtRead);

    btnBack = new JButton("---->");
    btnBack.setSize(75,50);
    btnBack.setLocation(400, 20);
    btnBack.setActionCommand("back");
    btnBack.addActionListener(this);
    add(btnBack);



    //gets the unreadBooks   
    findReadBooksAndUnreadBooks();
    String strLine = "";
    //gets the line to display the books
    for (int i = 0; i < listOfReadBooks.size();i++)
    {
      strLine += listOfReadBooks.get(i) + "\n";
    }
    txtRead.setText(strLine);

  }

  public void actionPerformed(ActionEvent e)
  {
    if (e.getActionCommand().equals("back"))
    {
      //goes back to homepage if user clicks back
      frmHomePage homePage = new frmHomePage(thisUser, currUserRatings);
      homePage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      homePage.setSize(500,500);//set the frame size
      homePage.setVisible((true));
      this.dispose();
    }
  } 
  /*
  stores the read books by user
  */
  public void findReadBooksAndUnreadBooks()
  {
    //loops throught the books
    for (Book currBook: userBookRatings.keySet())
    {
      //if the book is not read it adds it to the list with unread books
      if (userBookRatings.get(currBook) == 0)
      {
        listOfUnReadBooks.add(currBook);
      } 
      //adds it to the read books formated
      else
      {
        listOfReadBooks.add(currBook.getTitle() + " by " + currBook.getAuthor());
        listOfReadBooks.add(" ◆ Rating:" + userBookRatings.get(currBook));
      }
    }
  }
  
}
