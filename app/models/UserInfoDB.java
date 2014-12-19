package models;

/**
 * Provides an in-memory repository for UserInfo. Storing credentials in the clear is kind of bogus.
 * 
 * @author Philip Johnson
 * 
 * @modified by Alvin Prieto and Alvin Wang
 */
public class UserInfoDB {

  /**
   * Adds the specified user to the UserInfoDB.
   * 
   * @param name Their name.
   * @param email Their email.
   * @param password Their password.
   */
  public static void addUserInfo(String firstName, String lastName, String email, String password, String profilePic) {
    UserInfo userInfo = new UserInfo(firstName, lastName, email, password, profilePic);
    userInfo.save();
  }

  /**
   * Returns true if the email represents a known user.
   * 
   * @param email The email.
   * @return True if known user.
   */
  public static boolean isUser(String email) {
    return (UserInfo.find().where().eq("email", email).findUnique() != null);
  }

  /**
   * Returns the UserInfo associated with the email, or null if not found.
   * 
   * @param email The email.
   * @return The UserInfo.
   */
  public static UserInfo getUser(String email) {
    return UserInfo.find().where().eq("email", email).findUnique();
  }

  /**
   * Return the UserInfo associated with an id.
   * 
   * @param id
   * @return The UserInfo
   */
  public static UserInfo getUser(long id) {
    return UserInfo.find().where().eq("id", id).findUnique();
  }

  /**
   * Returns true if email and password are valid credentials.
   * 
   * @param email The email.
   * @param password The password.
   * @return True if email is a valid user email and password is valid for that email.
   */
  public static boolean isValid(String email, String password) {
    return ((email != null) && (password != null) && isUser(email) && getUser(email).getPassword().equals(password));
  }
}
