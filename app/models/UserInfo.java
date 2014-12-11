package models;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import play.db.ebean.Model;

/**
 * A simple representation of a user.
 * 
 * @author Philip Johnson
 * 
 * @modified by Alvin Prieto and Alvin Wang
 */
@Entity
public class UserInfo extends Model {

  private static final long serialVersionUID = 1L;

  @Id
  private long id;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private String profilePic;

  /**
   * The EBean ORM finder method for database queries.
   * 
   * @return The finder method for UserInfo.
   */
  public static Finder<Long, UserInfo> find() {
    return new Finder<Long, UserInfo>(Long.class, UserInfo.class);
  }

  /**
   * Creates a new UserInfo instance.
   * 
   * @param name The name.
   * @param email The email.
   * @param password The password.
   */
  public UserInfo(String firstName, String lastName, String email, String password, String profilePic) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.profilePic = profilePic;
  }

  /**
   * Returns a list of study groups that this user has created.
   * 
   * @return list
   */
  public List<StudyGroup> userStudyGroups() {
    return StudyGroup.getUserStudyGroups(this);
  }

  /**
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * @param email the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * @param password the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * @return the id
   */
  public long getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @param firstName the firstName to set
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * @param lastName the lastName to set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Returns the user's full name.
   * 
   * @return user's full name
   */
  public String getFullName() {
    return firstName + " " + lastName;
  }

  /**
   * @return the profilePic
   */
  public String getProfilePic() {
    return profilePic;
  }

  /**
   * @param profilePic the profilePic to set
   */
  public void setProfilePic(String profilePic) {
    this.profilePic = profilePic;
  }
}
