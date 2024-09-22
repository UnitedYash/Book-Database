import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.ImageIcon;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
public class Algorithm extends JFrame implements ActionListener 
{
  //Map to store all the data
  private Map<UserInfo, HashMap<Book, Integer>> userBookRating;
  //userinfo to store the logged in user
  UserInfo thisUser;
  //array which contains all the books
  ArrayList<Book> arrayOfBooks = new ArrayList<Book>();
  //UserInfo to store the user with the closest dot product
  UserInfo closestUser;
  
  public Algorithm(Map<UserInfo, HashMap<Book, Integer>> loadData, UserInfo loggedInUser)
  {
    //gets the data from before
    userBookRating = loadData;
    //gets the logged in user
    thisUser = loggedInUser;
  }
  /*
  returns the closest user to the logged in user
  */
  public UserInfo findCloseUser()
  {
    //makes the dotproduct
    int intDotProduct = 0;
    //makes the max to find the person with best dot prodcut
    int intMax = -1000;
    //sets best user to the curr user just so we can return it later without error
    UserInfo bestUser = thisUser;
    //loops through the users in the database
    for (UserInfo currUser: userBookRating.keySet())
    {
      //checks whether the current user is the logged in user
      if (currUser != thisUser)
      {
        //loops throguh the rating selections of that user
        for (Book currBook: userBookRating.get(currUser).keySet())
        {
          //calculates the dot product
          intDotProduct = intDotProduct + userBookRating.get(currUser).get(currBook) * userBookRating.get(thisUser).get(currBook);

        }
        //checks wheter this dotprodcut is greater than the max
        if (intDotProduct > intMax)
        {
          intMax = intDotProduct;
          //sets the best user as the curruser if it has the highest dotproduct
          bestUser = currUser;
        }
      //resets dot product for next loop
      intDotProduct= 0;
      
      }
    }
    return bestUser;
    
  }
    
  public void actionPerformed(ActionEvent e)
  {
    
  }
  /*
  returns the best books which the curr user didnt read yet from the best users books
  */
  public ArrayList<Book> getBestBook()
  {
    //stores the bestbooks
    ArrayList<Book> bestBooks = new ArrayList<Book>();
    //to store the best ratings so we can sort it later
    ArrayList<Integer> bestRatings = new ArrayList<Integer>();
    //calls the method to get the best user
    UserInfo bestUser = findCloseUser();
    
    int intBestRating = 0;
    //loops throught the logged in users books
    for (Book currBook: userBookRating.get(thisUser).keySet())
    {
      //checks wheter the logged in user didnt read it and whether the best user read it
      if (userBookRating.get(bestUser).get(currBook) != 0 && userBookRating.get(thisUser).get(currBook) == 0)
      {
        //adds the book to the arraylist
        bestBooks.add(currBook);
        //adds the rating to the arraylist
        bestRatings.add(userBookRating.get(bestUser).get(currBook));
      }
    }
    //selection sort algorithm
    int intTemp;
    Book bookTemp;
    for (int i = 0; i < bestBooks.size() - 1; i++)
    {
      for (int  j = 0; j < bestBooks.size(); j++)
      {

        if (bestRatings.get(i) > bestRatings.get(j))
        {
          intTemp = bestRatings.get(i);
          bookTemp = bestBooks.get(i);
          //changes around the rating in both the book array and ratings array, that way each index is the corresponds to the right rating
          bestRatings.set(i, bestRatings.get(j));
          bestBooks.set(i, bestBooks.get(j));
          bestRatings.set(j, intTemp);
          bestBooks.set(j, bookTemp);
        }
      }
      
    }
    //returns the sorted array of the best books
    return bestBooks;
  }
  
}
