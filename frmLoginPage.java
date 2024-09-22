import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.ImageIcon;
import java.util.Map;
import java.util.HashMap;
public class frmLoginPage extends JFrame implements ActionListener
{
  
  JLabel lblWelcome, lblUsername, lblPassword;
  JButton btnLoadData;
  JButton btnLogin;
  JButton btnBack;
  JTextField txtUsername, txtPassword;
  //to get the data
  Map<UserInfo, HashMap<Book, Integer>> allData;
  //to get the logged in user
  UserInfo theUser;
  public frmLoginPage(Map<UserInfo, HashMap<Book, Integer>> loadData)
  {
    //gets the data
    allData = loadData;
    setSize(500,500);
    setLayout(null);
    Color myColor = new Color(108, 126, 148);
    getContentPane().setBackground(myColor);
    lblWelcome = new JLabel("Welcome, Login below");
    lblWelcome.setSize(500,100);
    lblWelcome.setLocation(70,50);
    lblWelcome.setFont(new Font("Comic Sans", Font.BOLD, 20));
    add(lblWelcome);

    lblUsername= new JLabel("Username: ");
    lblUsername.setSize(500,100);
    lblUsername.setLocation(70,100);
    add(lblUsername);

    lblPassword = new JLabel("Password: ");
    lblPassword.setSize(500,50);
    lblPassword.setLocation(70,170);
    add(lblPassword);

    

    btnBack = new JButton("---->");
    btnBack.setSize(75,50);
    btnBack.setLocation(400, 20);
    btnBack.setActionCommand("back");
    btnBack.addActionListener(this);
    add(btnBack);

    txtUsername = new JTextField();
    txtUsername.setLocation(150,140);
    txtUsername.setSize(100,25);
    add(txtUsername);

    txtPassword = new JTextField();
    txtPassword.setLocation(150,185);
    txtPassword.setSize(100,25);
    add(txtPassword);

    btnLogin = new JButton("Login");
    btnLogin.setSize(100,50);
    btnLogin.setLocation(200,225);
    btnLogin.setActionCommand("login");
    btnLogin.addActionListener(this);
    add(btnLogin);


    
    
  }
  public void actionPerformed(ActionEvent e)
  {
    if (e.getActionCommand().equals("back"))
    {
      //loads the signin page if user goes back
      frmSignInPage signInPage = new frmSignInPage(allData);
      signInPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      signInPage.setSize(500,500);//set the frame size
      signInPage.setVisible((true));
      this.dispose();
    }
    if (e.getActionCommand().equals("login"))
    {
      //checks if login is valid if user clicks login
      if (isLoginValid(txtUsername.getText(), txtPassword.getText()))
      {
        //loads homepage with user and their data
        frmHomePage homePage = new frmHomePage(theUser, allData);
        homePage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homePage.setSize(500,500);//set the frame size
        homePage.setVisible((true));
        this.dispose();

      }
      else
      {
        JOptionPane.showMessageDialog(null, "Username not found or Password is wrong");
      }

    }
    
  }
  /*
  @ param strUsername is the Users username
  @ param strPassword is the password user entered
  returns a boolean that tells you if the login is valid
  */
  public boolean isLoginValid(String strUsername, String strPassword)
  {
    
    boolean loginCheck = false;
    try
    {
      //looks for the username
      for (UserInfo currUser: allData.keySet())
      {
        if (currUser.getUsername().equals(strUsername))
        {
          loginCheck = true;
          theUser = currUser;
        } 

      }
      //checks wheter the password is correct
      if (!theUser.getPassword().equals(strEncryption(strPassword))) loginCheck = false;
    }
    catch(Exception e1){}
    
    return loginCheck;
  }
  /*
  returns the user
  */
  public UserInfo getUser()
  {
    return theUser;
  }
  /*
  @ param strWord is the string you want to encrypt
  returns the string encrypted with an ascii value buffer of 5
  */
  public String strEncryption(String strWord)
  {
    int intTemp;
    char charTemp;
    String strEncryptedFinal = "";
    //sets how much you want to shift the ascii value by
    int intBuffer = 5;
    //loops throught the characters in the word
    for (int i = 0; i < strWord.length(); i++)
    {
      //gets the character and converts it to the ascii value
      charTemp = strWord.charAt(i);
      intTemp = charTemp;
      //makes sure adding the buffer doesnt make the ascii value to go over 122
      if (intTemp + intBuffer > 122)
      {
        //if it does, it adds the amount to 65
        intTemp = 5 - (122 - intTemp) + 65;
        charTemp = (char)intTemp;
        //adds the new character
        strEncryptedFinal += charTemp;
      }
      else
      {
        //adds the buffer to ascii value and converts it to a character
        charTemp = (char)(intTemp + intBuffer);
        strEncryptedFinal += charTemp;
      }
    }
    return strEncryptedFinal;
  }
}
    
