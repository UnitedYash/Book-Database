import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.ImageIcon;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
public class frmSignUpPage extends JFrame implements ActionListener
{
  JLabel lblWelcome, lblUsername, lblName, lblPassword, lblConfirmPassword;
  JButton btnSignUp;
  JTextField txtUsername, txtName, txtPassword, txtConfirmPassword;

  JButton btnBack;
  Map<UserInfo, HashMap<Book, Integer>> allData;


  
  
  public frmSignUpPage(Map<UserInfo, HashMap<Book, Integer>> loadData)
  {
    Color myColor = new Color(108, 126, 148);
    getContentPane().setBackground(myColor);
    //gets data
    allData = loadData;
    setSize(500,500);
    setLayout(null);

    lblWelcome = new JLabel("Welcome, fill in your credentials below: ");
    lblWelcome.setSize(300,50);
    lblWelcome.setLocation(100,50);
    add(lblWelcome);

    
    lblUsername = new JLabel("Username: ");
    lblUsername.setSize(100,75);
    lblUsername.setLocation(50,85);
    add(lblUsername);

    lblName = new JLabel("Name: ");
    lblName.setSize(50,50);
    lblName.setLocation(50,145);
    add(lblName);

    lblPassword = new JLabel("Password: ");
    lblPassword.setSize(100,50);
    lblPassword.setLocation(50,190);
    add(lblPassword);

    lblConfirmPassword = new JLabel("Confirm Password: ");
    lblConfirmPassword.setSize(200, 50);
    lblConfirmPassword.setLocation(0, 240);
    add(lblConfirmPassword);
  
    txtUsername = new JTextField();
    txtUsername.setLocation(140,110);
    txtUsername.setSize(100, 20);
    add(txtUsername);

    txtName = new JTextField();
    txtName.setLocation(140, 160);
    txtName.setSize(100, 20);
    add(txtName);

    txtPassword =  new JTextField();
    txtPassword.setLocation(140, 210);
    txtPassword.setSize(100,20);
    add(txtPassword);

    txtConfirmPassword = new JTextField();
    txtConfirmPassword.setLocation(140, 255);
    txtConfirmPassword.setSize(100,20);
    add(txtConfirmPassword);

    btnSignUp = new JButton("Sign Up");
    btnSignUp.setSize(150,40);
    btnSignUp.setLocation(140, 280);
    btnSignUp.setActionCommand("signup");
    btnSignUp.addActionListener(this);
    add(btnSignUp);



    btnBack = new JButton("---->");
    btnBack.setSize(75,50);
    btnBack.setLocation(400, 20);
    btnBack.setActionCommand("back");
    btnBack.addActionListener(this);
    add(btnBack);
    
  
    
    
  }

  public void actionPerformed(ActionEvent e)
  {
  
    if (e.getActionCommand().equals("signup"))
    {
      //checks whether the user entered in all the fields
      if (txtUsername.getText().length() > 0 && txtName.getText().length() > 0 && txtPassword.getText().length() > 0 && txtConfirmPassword.getText().length() > 0)
      {
        //makes sure the username is not taken
        if (isUserAlreadyIn(txtUsername.getText()) == false)
        {
          //makes sure the password and confirm passowrd is the same thing
          if (txtPassword.getText().equals(txtConfirmPassword.getText()))
          {
            //adds the suer to data base
            addUser(txtUsername.getText(), txtName.getText(), txtPassword.getText());
            frmLoadData getData = new frmLoadData();
            //updates the data hashmap
            getData.dataWriting(getData.strBookFile, getData.strRatingsFile);
            allData = getData.getData();
            getData.fileEditing(getData.strBookFile, getData.strRatingsFile);
            JOptionPane.showMessageDialog(null, "Added User, You can now Login");
            
          }
          else
          {
            JOptionPane.showMessageDialog(null, "Passwords don't match");
          }
        }
        else
        {
          JOptionPane.showMessageDialog(null, "Username is taken");
        }
      }
      else
      {
        JOptionPane.showMessageDialog(null, "Enter All Fields");
      }
    }
    
    if (e.getActionCommand().equals("back"))
    {
      //goes to signin page again if user goes back
      frmSignInPage signInPage = new frmSignInPage(allData);
      signInPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      signInPage.setSize(500,500);//set the frame size
      signInPage.setVisible((true));
      this.dispose();
    }
  }
  /*
  @ param strUsername is a string of the username the user entered
  returns a boolean to tell you if the username is taken
  */
  public boolean isUserAlreadyIn(String strUsername)
  {
    boolean isTaken = false;
    //goes throught the data base and usernames
    for (UserInfo currUser: allData.keySet())
    {
      if(currUser.getUsername().equals(strUsername)) isTaken = true;
    }

    return isTaken;
    
  }
  /*
  @ param strUsername is a string 
  @ param strName is string of the users name
  @ param strPassword is a string of the users password
  adds the user to data base
  */
  public void addUser(String strUsername, String strName, String strPassword)
  {
    //to track how many zeros to add in the file
    int intNumberOfZeros = 0;
    //loops thorugh data base to get the number of books in database
    for (UserInfo currUser: allData.keySet())
    {
      intNumberOfZeros = allData.get(currUser).size();
    }
    String strAddZeros = "";
    //adds the zeros to a string
    for (int i = 0; i < intNumberOfZeros; i++)
    {
      strAddZeros += "0 ";
      
    }
    try
    {
      //gets the new data
      frmLoadData getData = new frmLoadData();
      String ratingsFile= getData.strRatingsFile;
      //writes thenew user to end of the ratings file
      FileWriter ratingsFR = new FileWriter(ratingsFile, true);
      PrintWriter ratingsPR = new PrintWriter(ratingsFR);
      ratingsPR.println(strUsername + "," + strName + "," + strEncrypted(strPassword));
      ratingsPR.println(strAddZeros);
      allData = getData.getData();
      ratingsPR.close();
    }
    catch(IOException e1){}
  }
  /*
  @ param strWord is the string you want to encrypt
  returns a string with the encrypted word
  */
  public String strEncrypted(String strWord)
  {
    
    int intTemp;
    char charTemp;
    String strEncryptedFinal = "";
    //buffer for shifting the ascii value
    int intBuffer = 5;
    //loops thorugh the words
    for (int i = 0; i < strWord.length(); i++)
    {
      charTemp = strWord.charAt(i);
      intTemp = charTemp;
      //makes sure ascii value does not go out of bounds
      if (intTemp + intBuffer > 122)
      {
        //shifts the ascii value
        intTemp = 5 - (122 - intTemp) + 65;
        charTemp = (char)intTemp;
        strEncryptedFinal += charTemp;
      }
      else
      {
        //shifts asci value
        charTemp = (char)(intTemp + intBuffer);
        if(charTemp == ',') charTemp = '!';
        strEncryptedFinal += charTemp;
      }
     
    }
    return strEncryptedFinal;
  }
}

