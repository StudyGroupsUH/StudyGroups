package models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import play.data.Form;
import play.data.validation.Constraints;

/**
 * Search
 * 
 * Used for the search bar located in the navbar.
 * 
 * @author Alvin Prieto
 *
 */
public class Search {

  @Constraints.Required()
  public String term;

  public static List<StudyGroup> studyGroups;
  public static List<Lecture> lectures;

  /**
   * Default constructor.
   */
  public Search() {
  }

  /**
   * Returns a Form object of the Search object.
   * 
   * @return sf a Form object.
   */
  public static Form<Search> getForm() {
    Search searchForm = new Search();
    Form<Search> sf = Form.form(Search.class).fill(searchForm);
    return sf;
  }

  /**
   * Searches stuff.
   * 
   * @param term the term to be searched for.
   */
  public static void search(String term) {

    studyGroups = new ArrayList<>();
    lectures = new ArrayList<>();

    term = "%" + term + "%";
    Set<Lecture> lectureResults = new HashSet<>();

    List<Lecture> lectureTopics = Lecture.find().where().ilike("topic", term).findList();
    List<Lecture> lectureDescriptions = Lecture.find().where().ilike("description", term).findList();
    List<Lecture> lectureCourseLevel = Lecture.find().where().ilike("courseLevel", term).findList();
    List<Lecture> lectureCourse = Lecture.find().where().ilike("course", term).findList();

    lectureResults.addAll(lectureTopics);
    lectureResults.addAll(lectureDescriptions);
    lectureResults.addAll(lectureCourseLevel);
    lectureResults.addAll(lectureCourse);

    lectures.addAll(lectureResults);

    Set<StudyGroup> sgResults = new HashSet<>();

    List<StudyGroup> sgTopics = StudyGroup.find().where().ilike("topics", term).findList();
    List<StudyGroup> sgCourse = StudyGroup.find().where().ilike("course", term).findList();
    List<StudyGroup> sgCourseLevel = StudyGroup.find().where().ilike("courseLevel", term).findList();

    sgResults.addAll(sgTopics);
    sgResults.addAll(sgCourse);
    sgResults.addAll(sgCourseLevel);

    studyGroups.addAll(sgResults);

  }

  /**
   * Returns a list of Lectures that matches the search term.
   * 
   * @return List of lectures
   */
  public static List<Lecture> getLectureResults() {
    return lectures;
  }

  /**
   * Returns a list of StudyGroup that matches the search term.
   * 
   * @return List of study groups
   */
  public static List<StudyGroup> getStudyGroupResults() {
    return studyGroups;
  }

}