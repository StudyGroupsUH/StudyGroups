package controllers;

import java.util.List;
import com.avaje.ebean.Page;
import models.ClassLevel;
import models.Course;
import models.Misc;
import models.StudyGroup;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.formdata.StudyGroupForm;
import views.html.InvalidUrl;
import views.html.studyGroup.CreateStudyGroup;
import views.html.studyGroup.ListStudyGroup;
import views.html.studyGroup.StudyGroupsForClass;
import views.html.studyGroup.ViewStudyGroup;

/**
 * The controller for the study groups.
 * 
 * @author Alvin Prieto
 *
 */
public class StudyGroups extends Controller {

  private static final int AMOUNT_OF_ENTRIES = 10;

  /**
   * Returns a page containing all the study groups for a course, i.e. ICS
   * 
   * @param course the course
   * @return the page with the study groups
   */
  public static Result listStudyGroups(String course) {
    Course courseName = Course.getCourse(course);
    List<ClassLevel> classes = courseName.getClassesAsList();
    return ok(ListStudyGroup.render(course.toUpperCase() + "- list of Study Groups", courseName, classes));
  }

  /**
   * Returns a page containing all the study groups for a specific class, i.e. ICS 311
   * 
   * @param course the course
   * @param classLevel the class level
   * @param page the current page number
   * @return the page containing the study groups for a class
   */
  public static Result viewClassStudyGroup(String course, String classLevel, int page) {
    ClassLevel cl = ClassLevel.getCL(Misc.unSlugify(classLevel));
    Page<StudyGroup> sg = cl.getStudyGroupPage(page, AMOUNT_OF_ENTRIES);
    return ok(StudyGroupsForClass.render(Misc.unSlugify(classLevel), cl, sg));
  }

  /**
   * Returns an empty form to create a study group.
   * 
   * @return the create study group page.
   */
  public static Result createStudyGroup() {
    StudyGroupForm sgf = new StudyGroupForm();
    Form<StudyGroupForm> sgForm = Form.form(StudyGroupForm.class).fill(sgf);
    return ok(CreateStudyGroup.render("Create Study Group", sgForm));
  }

  /**
   * Returns a create study group form with the course field filled out.
   * 
   * @param courseName the courseName, ie ICS
   * @return a create study group form.
   */
  public static Result createSgForCourse(String courseName) {
    StudyGroupForm sgf = new StudyGroupForm(Misc.unSlugify(courseName));
    Form<StudyGroupForm> sgForm = Form.form(StudyGroupForm.class).fill(sgf);
    return ok(CreateStudyGroup.render("Create Study Group", sgForm));
  }

  /**
   * Returns a create study group form with the course, ie ICS and level ie 465 already filled out.
   * 
   * @param course the course
   * @param classLevel the classLevel
   * @return a page with the create study group form.
   */
  public static Result createSgForClass(String course, String classLevel) {
    ClassLevel cl = ClassLevel.getCL(Misc.unSlugify(classLevel));
    StudyGroupForm sgf = new StudyGroupForm(cl.getCourse(), Integer.toString(cl.getLevel()));
    Form<StudyGroupForm> sgForm = Form.form(StudyGroupForm.class).fill(sgf);
    return ok(CreateStudyGroup.render("Create Study Group", sgForm));
  }

  /**
   * Adds a study group to a certain class level.
   * 
   * @return the page belonging to the added study group.
   */
  public static Result addStudyGroup() {

    Form<StudyGroupForm> sgf = Form.form(StudyGroupForm.class).bindFromRequest();

    if (sgf.hasErrors()) {
      return badRequest(CreateStudyGroup.render("CreateStudyGroup", sgf));
    }
    else {

      StudyGroupForm form = sgf.get();

      StudyGroup sg =
          new StudyGroup(form.id, form.course, form.intLevel, form.location, form.intMonth, form.intDay, form.year,
              form.intHours, form.intMinutes, form.topics);

      sg.save();

      String courseLevel = form.course.toUpperCase() + " " + form.intLevel;
      ClassLevel cl = ClassLevel.getCL(courseLevel);

      if (cl == null) {
        cl = new ClassLevel(form.course, form.intLevel);
        cl.save();
      }

      return redirect(routes.StudyGroups.viewStudyGroup(sg.getId(), Misc.slugify(sg.getCourse()),
          Misc.slugify(sg.getCourseLevel())));
    }
  }

  /**
   * The page of a study group.
   * 
   * @param id the id of a study group
   * @param course the course
   * @param classLevel the class
   * @return the page of the study group
   */
  public static Result viewStudyGroup(long id, String course, String classLevel) {

    StudyGroup sg = StudyGroup.getSG(id);

    if (sg == null) {
      return ok(InvalidUrl.render("Error 404"));
    }

    return ok(ViewStudyGroup.render(sg.getCourseLevel(), sg));
  }

}
