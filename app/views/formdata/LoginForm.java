package views.formdata;

import play.data.Form;
import play.data.validation.ValidationError;
import java.util.ArrayList;
import java.util.List;
import models.UserInfoDB;
import play.data.validation.Constraints;

/**
 * Backing class for the login form.
 * 
 * @author Alvin Prieto
 * 
 */
public class LoginForm {

  /**
   * for test log in.
   */
  public static final String ERROR_CHECK = "Login is not valid.";

  /** The submitted email. */
  @Constraints.Required(message = "")
  public String email = "";
  /** The submitted password. */
  @Constraints.Required(message = "")
  public String password = "";

  /** Required for form instantiation. */
  public LoginForm() {
  }

  /**
   * Returns a form object of LoginForm.
   * 
   * @return a login form
   */
  public static Form<LoginForm> getForm() {
    LoginForm lf = new LoginForm();
    Form<LoginForm> loginForm = Form.form(LoginForm.class).fill(lf);
    return loginForm;
  }

  /**
   * Validates Form<LoginFormData>. Called automatically in the controller by bindFromRequest(). Checks to see that
   * email and password are valid credentials.
   * 
   * @return Null if valid, or a List[ValidationError] if problems found.
   */
  public List<ValidationError> validate() {

    List<ValidationError> errors = new ArrayList<>();

    if (UserInfoDB.getUser(email) == null) {
      errors.add(new ValidationError("email", "Incorrect email address."));
    }
    else if (!UserInfoDB.getUser(email).getPassword().equals(password)) {
      errors.add(new ValidationError("password", "Incorrect password."));
    }

    return (errors.size() > 0) ? errors : null;
  }

}
