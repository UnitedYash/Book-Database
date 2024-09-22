import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.ImageIcon;
import java.util.Map;
import java.util.HashMap;
public class frmSignInPage extends JFrame implements ActionListener
{
  JLabel lblWelcome;
  JButton btnLoadData;
  JButton btnLogin;
  JButton btnSignUp;
  Map<UserInfo, HashMap<Book, Integer>> allData;
  String strBookFile;
  String strRatingsFile;
  public frmSignInPage(Map<UserInfo, HashMap<Book, Integer>> loadData)
  
  {
    //gets the data
    allData = loadData;
    setSize(500,500);
    setLayout(null);
    Color myColor = new Color(108, 126, 148);
    getContentPane().setBackground(myColor);
    //if there is no data, it auto loads the book and ratings file
    if (allData.size() == 0)
    {
      frmLoadData loadDataPage = new frmLoadData();
      loadDataPage.fileEditing("book.txt", "ratings.txt");
      loadDataPage.dataWriting("book.txt", "ratings.txt");
      allData = loadDataPage.getData();
    }
    
    lblWelcome = new JLabel("Welcome, \nChoose one of the options below");
    lblWelcome.setSize(500,100);
    lblWelcome.setLocation(100,50);
    lblWelcome.setFont(new Font("Arial", Font.BOLD, 15));
    add(lblWelcome);   

    btnLogin = new JButton("Login");
    btnLogin.setSize(100,50);
    btnLogin.setLocation(100, 150);
    btnLogin.setActionCommand("login");
    btnLogin.addActionListener(this);
    add(btnLogin);

    btnSignUp = new JButton("Sign Up!");
    btnSignUp.setSize(100,50);
    btnSignUp.setLocation(250, 150);
    btnSignUp.setActionCommand("SignUp");
    btnSignUp.addActionListener(this);
    add(btnSignUp);

    btnLoadData = new JButton("Load External Data");
    btnLoadData.setSize(200,50);
    btnLoadData.setLocation(130, 250);
    btnLoadData.setActionCommand("loadData");
    btnLoadData.addActionListener(this);
    add(btnLoadData);

    
  }
  public void actionPerformed(ActionEvent e)
    {
      if (e.getActionCommand().equals("login"))
      {
        //if user clicks login it goes to login page
        frmLoginPage loginPage = new frmLoginPage(allData);
        loginPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginPage.setSize(500,500);//set the frame size
        loginPage.setVisible((true));
        this.dispose();

      }

      else if (e.getActionCommand().equals("SignUp"))
      {
        //goes ot sign in page if user clicks sign in
        frmSignUpPage signUpPage = new frmSignUpPage(allData);
        signUpPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        signUpPage.setSize(500,500);//set the frame size
        signUpPage.setVisible((true));
        this.dispose();

      }
      else if (e.getActionCommand().equals("loadData"))
      {
        //loads the load data file it user clicks load data
        frmLoadData loadDataPage = new frmLoadData();
        loadDataPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loadDataPage.setSize(500,500);//set the frame size
        loadDataPage.setVisible((true));
        this.dispose();

      }
      
    }
}
