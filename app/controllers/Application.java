package controllers;

import java.util.List;
import models.Course;
import models.Lecture;
import models.Search;
import models.StudyGroup;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.formdata.LoginForm;
import views.formdata.UserForm;
import views.html.Index;
import views.html.SearchResults;
import views.html.course.ListOfCourses;

/**
 * The main controller for this application.
 * 
 * @author Alvin Prieto and Alvin Wang
 *
 */
public class Application extends Controller {

  /**
   * Returns the index page.
   * 
   * @return the index page.
   * 
   * @author Alvin Prieto 
   */
  public static Result index() {
    return ok(Index.render("Welcome", LoginForm.getForm(), false, UserForm.getForm(), false));
  }

  /**
   * Returns the page containing the list of courses.
   * 
   * @return page containing list of courses
   * 
   * @author Alvin Prieto 
   */
  public static Result listOfCourses() {
    List<Course> courses = Course.find().all();
    return ok(ListOfCourses.render("List of Courses", courses));
  }

  /**
   * Used for the search bar in the navbar.
   * 
   * @param currUrl the url of the page where the search button was pressed.
   * @return the search results page
   * 
   * @author Alvin Prieto 
   */
  public static Result search(String currUrl) {

    Form<Search> searchForm = Form.form(Search.class).bindFromRequest();

    if (!searchForm.hasErrors()) {
      Search search = searchForm.get();
      String term = search.term;
      Search.search(term);
      List<Lecture> lectures = Search.getLectureResults();
      List<StudyGroup> studyGroups = Search.getStudyGroupResults();
      return ok(SearchResults.render("Search", term, lectures, studyGroups));
    }
    return redirect(currUrl);
  }

  /**
   * Creates a user account.
   * 
   * @return a page
   *
  public static Result createAccount() {
    Form<UserForm> userForm = Form.form(UserForm.class).bindFromRequest();

    if (userForm.hasErrors()) {
      return badRequest(Index.render("Welcome", LoginForm.getForm(), false, userForm, true));
    }

    UserForm uf = userForm.get();
    UserInfoDB.addUserInfo(uf.getFirstName(), uf.getLastName(), uf.getEmail(), uf.getPassword());
    return redirect(routes.Application.index());
  }*/
}
