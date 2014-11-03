package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import play.db.ebean.Model;

@Entity
public class Course extends Model {

  /**
   * 
   */
  private static final long serialVersionUID = -5763824393646461390L;

  @Id
  private String id;
  private String courseName;
  private String classes = "";

  public Course() {
  }

  public Course(String id, String courseName) {
    this.id = id;
    this.courseName = courseName;
  }

  @Override
  public String toString() {
    return courseName + " (" + id + ")";
  }

  public String getId() {
    return this.id;
  }

  public String getCourseName() {
    return this.courseName;
  }

  public static Finder<String, Course> find() {
    return new Finder<String, Course>(String.class, Course.class);
  }

  public static Course getCourse(String id) {
    return find().where().eq("id", id.toUpperCase()).findUnique();
  }

  public static boolean isCourse(String course) {
    List<Course> byId = find().where().contains("id", course.toUpperCase()).findList();
    List<Course> byName = find().where().eq("courseName", course).findList();

    if ((byId.size() == 0) && (byName.size() == 0)) {
      return false;
    }
    return true;
  }

  public void addClass(ClassLevel cl) {
    if (!classes.contains(cl.toString())) {
      StringBuilder sb = new StringBuilder(classes);
      sb.append(cl.toString() + "|");
      classes = sb.toString();
    }
  }

  public String getClasses() {
    return classes;
  }

  public List<ClassLevel> getClassesAsList() {
    List<ClassLevel> classes = new ArrayList<>();

    String[] cl = this.classes.split("\\|");

    for (int x = 0; x < cl.length; x++) {
      ClassLevel cLevel = ClassLevel.getCL(cl[x]);
      classes.add(cLevel);
    }

    return classes;
  }

}
