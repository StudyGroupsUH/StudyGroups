package models;

import java.util.ArrayList;
import java.util.List;
import play.data.Form;
import play.data.validation.Constraints;

/**
 * Search
 * 
 * Used for the search bar located in the navbar.
 * 
 * @author Alvin Prieto and Alvin Wang
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
   * 
   * @author Alvin Prieto
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
   * 
   * @author Alvin Prieto and Alvin Wang
   */
  public static void search(String keywords) {

    studyGroups = new ArrayList<>();
    lectures = new ArrayList<>();

    List<String> keywordList = keysToList(keywords);

    List<Lecture> currentLectures = Lecture.find().all();
    List<StudyGroup> currentSGs = StudyGroup.find().all();
    
    /* Alvin Wang (for loops) */
    for (Lecture lecture : currentLectures) {
      if (containsKeys(
          lecture
              .getTopic()
              .concat(" ")
              .concat(
                  lecture.getDescription().concat(" ").concat(lecture.getCourseLevel()).concat(" ")
                      .concat(lecture.getLevel())), keywordList)) {
        lectures.add(lecture);
      }
    }

    for (StudyGroup sg : currentSGs) {
      if (containsKeys(sg.getTopics().concat(" ").concat(sg.getCourse().concat(" ").concat(sg.getCourseLevel())),
          keywordList)) {
        studyGroups.add(sg);
      }
    }
  }

  /**
   * Puts search keys into List form.
   * 
   * @param keys the keywords
   * 
   * @return a list of keys
   * 
   * @author Alvin Wang
   */
  private static List<String> keysToList(String keys) {
    return java.util.Arrays.asList(keys.split("\\s+"));
  }

  /**
   * Checks if keys match topic, descriptions, course, etc...
   * 
   * @param target giant String of topics, descriptions, etc...
   * @param keys the keywords
   * 
   * @return false if target does not contain all the keys, true otherwise.
   * 
   * @author Alvin Wang
   */
  private static boolean containsKeys(String target, List<String> keys) {
    boolean result = true;
    for (String key : keys) {
      if (!target.toLowerCase().contains(key.toLowerCase())) {
        result = false;
        break;
      }
    }
    return result;
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
