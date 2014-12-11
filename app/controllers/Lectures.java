package controllers;

import java.util.List;
import models.Course;
import models.Lecture;
import models.LectureDB;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.formdata.FilterLectureForm;
import views.formdata.LectureForm;
import views.formdata.LectureLevels;
import views.html.lecture.ListOfLectures;
import views.html.lecture.CreateLecture;
import play.mvc.Security;

/**
 * Implements the controller for Lectures.
 * 
 * @author Alvin Wang
 *
 */
public class Lectures extends Controller{
  
  /**
   * Returns the lecture list page.
   * 
   * @param id course id e.g. ICS
   * 
   * @return the lecture list page.
   */
  public static Result viewLecture(String id) {
    Course courseName = Course.find().byId(id.toUpperCase());
    List<Lecture> lectures = LectureDB.getLectures(id.toUpperCase());
    LectureForm data = new LectureForm();
    Form<LectureForm> formData = Form.form(LectureForm.class).fill(data);
    FilterLectureForm filterData = new FilterLectureForm();
    Form<FilterLectureForm> filterLectureForm = Form.form(FilterLectureForm.class).fill(filterData);
    
    return ok(ListOfLectures.render(courseName.getId(), courseName, lectures, formData, false, filterLectureForm,
        LectureLevels.getLectureLevels(id.toUpperCase())));
  }
  
 
  /**
   * Add lecture to course.
   * 
   * @param id
   * 
   * @return lecture list page
   */
  @Security.Authenticated(Secured.class)
  public static Result addLecture(String id) {
    Course courseName = Course.find().byId(id.toUpperCase());
    List<Lecture> lectures = LectureDB.getLectures(id.toUpperCase());
    Form<LectureForm> formData = Form.form(LectureForm.class).bindFromRequest();
    FilterLectureForm filterData = new FilterLectureForm();
    Form<FilterLectureForm> filterLectureForm = Form.form(FilterLectureForm.class).fill(filterData);
    
    if (formData.hasErrors()) {
      flash("error", "Login credentials not valid.");
      return badRequest(ListOfLectures.render(courseName.getId(), courseName, lectures, formData, true, filterLectureForm,
          LectureLevels.getLectureLevels(id.toUpperCase())));
    }
    else {
      LectureForm data = formData.get();
      LectureDB.addLecture(data); 
      
      return redirect(routes.Lectures.viewLecture(courseName.getId()));
    }
  }
  
  /**
   * Adds a new lecture.  Used in CreateLecture.scala.html
   * 
   * @return lecture list page.
   */
  @Security.Authenticated(Secured.class)
  public static Result addNewLecture() {
    
    Form<LectureForm> formData = Form.form(LectureForm.class).bindFromRequest();
    
    if (formData.hasErrors()) {
      return badRequest(CreateLecture.render("Create Lecture", formData));
    }
    else {
      LectureForm data = formData.get();
      LectureDB.addLecture(data); 
      
      Course courseName = Course.find().byId(data.getCourse().toUpperCase());
      
      return redirect(routes.Lectures.viewLecture(courseName.getId()));
    }
  }

  /**
   * Delete lecture from repository
   * 
   * Issue: Only deletes from the top of the list. -AW
   * 
   * @param course
   * @param level
   * @param videoId
   * 
   * @return the lecture list page.
   */
  @Security.Authenticated(Secured.class)
  public static Result deleteLecture(String id, String course, String level, String videoId) {
    Course courseName = Course.find().byId(id.toUpperCase());
    Lecture lecture = LectureDB.getLecture(course, level, videoId);
    LectureDB.deleteLecture(lecture); 
    
    return redirect(routes.Lectures.viewLecture(courseName.getId()));
  }
  
  /**
   * Returns the create lecture page.
   * 
   * @return the create lecture page.
   */
  @Security.Authenticated(Secured.class)
  public static Result createLecture() {
    LectureForm lf = new LectureForm();
    Form<LectureForm> lectureForm = Form.form(LectureForm.class).fill(lf);
    return ok(CreateLecture.render("Create Lecture", lectureForm));
  }
  
  /**
   * Returns a filtered lectures page.
   * 
   * @param id the course
   * 
   * @return the filtered lecture page.
   */
  public static Result filterLectures(String id) {
    Course courseName = Course.find().byId(id.toUpperCase());
    LectureForm data = new LectureForm();
    Form<LectureForm> formData = Form.form(LectureForm.class).fill(data);
    Form<FilterLectureForm> filterLectureForm = Form.form(FilterLectureForm.class).bindFromRequest();
    FilterLectureForm filterData = filterLectureForm.get();
    List<Lecture> lectures;
    
    if (filterData.level == null || filterData.level.length() == 0) {
      return redirect(routes.Lectures.viewLecture(courseName.getId()));
    }
    else {
      lectures = LectureDB.getLectures(id.toUpperCase(), filterData.level);
      return ok(ListOfLectures.render(courseName.getId(), courseName, lectures, formData, false, filterLectureForm,
          LectureLevels.getLectureLevels(id.toUpperCase())));
    }
  }
}
