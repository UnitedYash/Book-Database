import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Main 
{
  public static void main(String[] args) 
  {
    //set the hashmap that stores all the data, userinfo(password, name, username) that corresponds to a hashmap with the book and rating.
    Map<UserInfo, HashMap<Book, Integer>> allData = new HashMap<UserInfo, HashMap<Book, Integer>>();
    //set temp user to get program
    frmSignInPage myFrame = new frmSignInPage(allData);
    //we also going to say close the frame when the X button ism pressed
    myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    myFrame.setSize(500,500);//set the frame size
    myFrame.setVisible((true));
  }


}


