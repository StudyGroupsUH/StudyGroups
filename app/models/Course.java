package models;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import play.db.ebean.Model;

/**
 * A Course model.
 * 
 * @author Alvin Prieto
 *
 */
@Entity
public class Course extends Model {

  /**
   * 
   */
  private static final long serialVersionUID = -5763824393646461390L;

  @Id
  private String id;
  private String courseName;

  /**
   * No args constructor.
   */
  public Course() {
  }

  /**
   * Constructor.
   * 
   * @param id the course id, i.e. ICS
   * @param courseName the course name, i.e. Information and Computer Sciences.
   */
  public Course(String id, String courseName) {
    this.id = id;
    this.courseName = courseName;
  }

  @Override
  public String toString() {
    return courseName + " (" + id + ")";
  }

  /**
   * 
   * @return the id
   */
  public String getId() {
    return this.id;
  }

  /**
   * 
   * @return course name
   */
  public String getCourseName() {
    return this.courseName;
  }

  /**
   * 
   * @return the finder object for course
   */
  public static Finder<String, Course> find() {
    return new Finder<String, Course>(String.class, Course.class);
  }

  /**
   * Returns the course based on the id.
   * 
   * @param id the id of the course
   * @return a Course object
   */
  public static Course getCourse(String id) {
    return find().where().eq("id", id.toUpperCase()).findUnique();
  }

  /**
   * Checks to see that it is a valid course.
   * 
   * @param course the course to be checked
   * @return true or false
   */
  public static boolean isCourse(String course) {
    List<Course> byId = find().where().contains("id", course.toUpperCase()).findList();
    List<Course> byName = find().where().eq("courseName", course).findList();

    if ((byId.size() == 0) && (byName.size() == 0)) {
      return false;
    }
    return true;
  }

  /**
   * Returns the different classes as a List.
   * 
   * @return ClassLevel in a List
   */
  public List<ClassLevel> getClassesAsList() {
    return ClassLevel.getClassesForCourse(this.id);
  }

}
