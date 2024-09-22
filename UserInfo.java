
//class for storing user info
public class UserInfo
{
  private String strUsername;
  private String strPassword;
  private String strName;
  public UserInfo(String strLine)
  {
    String [] strSplitName = strLine.split(",");
    this.strUsername = strSplitName[0];
    this.strName = strSplitName[1];
    this.strPassword = strSplitName[2];
  }
  /*
  returns the username of user
  */
  public String getUsername()
  {
    return strUsername;
  }
  /*
  returns the password of user
  */
  public String getPassword()
  {
    return strPassword;
  }
  /*
  returns the name of user
  */
  public String getName()
  {
    //check to makesure this is not a temp username
    if (strName.equals("t"))
    {
      return strUsername;
    }
    return strName;
  }
  /*
  returns the username of user
  */
}

