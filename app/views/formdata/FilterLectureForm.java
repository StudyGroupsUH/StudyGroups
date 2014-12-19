package views.formdata;

/**
 * Backing class for Filter data.
 * 
 * @author Alvin Wang
 *
 */
public class FilterLectureForm {

  public String level = "";
  
  /**
   * Empty constructor.
   */
  public FilterLectureForm() {
    
  }
  
  /**
   * Constructor for FilterLectureForm
   * 
   * @param level the course level
   */
  public FilterLectureForm(String level) {
    this.level = level;
  }
}
