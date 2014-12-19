package views.formdata;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.Id;
import models.UserInfoDB;
import play.data.Form;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;

/**
 * Backing class for user form.
 * 
 * @author Alvin Prieto and Alvin Wang(ProfilePic and its Validation)
 *
 */
public class UserForm {
  
  /** 
   * Simple e-mail regex
   * 
   * Added by: Alvin Wang
   * Source: http://stackoverflow.com/a/8204716
   */
  public static final Pattern VALID_EMAIl_REGEX = 
      Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

  @Id
  private long id;

  @Constraints.Required(message = "A first name is required.")
  private String firstName;

  @Constraints.Required(message = "A last name is required.")
  private String lastName;

  @Constraints.Required(message = "An email is required.")
  private String email;

  @Constraints.Required(message = "A password is required.")
  private String password;

  @Constraints.Required(message = "Please enter the password again.")
  private String password2;
  
  private String profilePic;

  /**
   * Constructor.
   */
  public UserForm() {
  }

  /**
   * Validation Method.
   * 
   * @return a list of errors
   */
  public List<ValidationError> validate() {
    List<ValidationError> errors = new ArrayList<>();

    if (UserInfoDB.isUser(email)) {
      errors.add(new ValidationError("email", "Email already exists."));
    }

    if (!isValidEmail(email)) {
      errors.add(new ValidationError("email", "Invalid e-mail address. Example: example@example.com"));
    }
    
    // Password must be between 4 and 8 characters long
    // Password must contain at least one number
    if (!password.matches("^(?=.*\\d).{4,8}$")) {
      errors.add(new ValidationError("password", "Password must be 4 to 8 characters long and contain a number."));
    }

    if (!password.equals(getPassword2())) {
      errors.add(new ValidationError("password2", "Passwords do not match."));
    }

    /** Alvin Wang */
    if (!(profilePic == null || profilePic.length() == 0)) {
      if (!exists(profilePic)) {
        errors.add(new ValidationError("profilePic", "Invalid URL. Example: http://www.example.com/me.jpg"));
      }
    
      if (!(profilePic.endsWith(".jpg") || profilePic.endsWith(".png") || profilePic.endsWith(".gif"))) {
        errors.add(new ValidationError("profilePic", "Invalid image URL. Must end with .jpg, .png, or .gif"));
      }
    }
    
    return errors.isEmpty() ? null : errors;
  }

  /**
   * Returns an empty user form.
   * 
   * @return an empty user form.
   */
  public static Form<UserForm> getForm() {
    UserForm uf = new UserForm();
    Form<UserForm> userForm = Form.form(UserForm.class).fill(uf);
    return userForm;
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

  public String getPassword2() {
    return password2;
  }

  public void setPassword2(String password2) {
    this.password2 = password2;
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
  
  /**
   * Crude validation of an email address using a regex.
   * 
   * Added by: Alvin Wang
   * Source: http://stackoverflow.com/a/8204716
   * 
   * @param email the email
   * @return true if valid, otherwise false.
   */
  public static boolean isValidEmail(String email) {
    Matcher matcher = VALID_EMAIl_REGEX.matcher(email);
    return matcher.find();
  }
  
  /**
   * Checks if URL is valid.
   * 
   * Added by: Alvin Wang
   * Source: http://stackoverflow.com/a/4177885
   * 
   * @param URLName
   * @return true if valid URL, false otherwise.
   */
  public static boolean exists(String URLName){
    try {
      HttpURLConnection.setFollowRedirects(false);
      // note : you may also need
      //        HttpURLConnection.setInstanceFollowRedirects(false)
      HttpURLConnection con =
         (HttpURLConnection) new URL(URLName).openConnection();
      con.setRequestMethod("HEAD");
      return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
    }
    catch (Exception e) {
       e.printStackTrace();
       return false;
    }
  }  
}
