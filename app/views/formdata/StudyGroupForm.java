package views.formdata;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.joda.time.DateTime;
import org.joda.time.Days;
import models.Course;
import models.DateTimeInfo;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;

/**
 * Backing class for the study group form.
 * 
 * @author Alvin Prieto
 *
 */
public class StudyGroupForm {

  @Id
  @GeneratedValue
  public long id;

  @Constraints.Required(message = "A course is required.")
  public String course;

  @Constraints.Required(message = "A level is required.")
  public String level;

  @Constraints.Required(message = "A location is required.")
  public String location;

  @Constraints.Required(message = "A month is required.")
  public String month;

  @Constraints.Required(message = "A day is required.")
  public String day;

  public int year = Calendar.getInstance().get(Calendar.YEAR);

  @Constraints.Required(message = "An hour is required.")
  public String hour;

  @Constraints.Required(message = "Minutes are required.")
  public String min;

  public String topics;

  public int intLevel;
  public int intHours;
  public int intMinutes;
  public int intMonth;
  public int intDay;

  /**
   * Returns an empty study group form.
   */
  public StudyGroupForm() {
  }

  /**
   * Returns a study group form with the course info.
   * 
   * @param course the course
   */
  public StudyGroupForm(String course) {
    this.course = course;
  }

  /**
   * Returns the study group form with course and level info.
   * 
   * @param course the course
   * @param level the level
   */
  public StudyGroupForm(String course, String level) {
    this.course = course;
    this.level = level;
  }

  /**
   * Returns the id of this study group form.
   * 
   * @return the id
   */
  public long getId() {
    return id;
  }

  /**
   * Validation method.
   * 
   * @return a list containing any validation errors.
   */
  public List<ValidationError> validate() {

    List<ValidationError> errors = new ArrayList<>();

    if (!Course.isCourse(course.trim())) {
      errors.add(new ValidationError("course", "Not a valid course"));
    }

    try {
      intLevel = Integer.parseInt(level);
    }
    catch (NumberFormatException nfe) {
      errors.add(new ValidationError("level", "Course level must be a number."));
    }

    int year = Calendar.getInstance().get(Calendar.YEAR);
    int mon = -1;
    int numDay = -1;

    int hours = -1;
    int minutes = -1;

    try {
      mon = Integer.parseInt(month);
    }
    catch (NumberFormatException nfe) {
      errors.add(new ValidationError("month", "NaN"));
    }

    try {
      numDay = Integer.parseInt(day);
    }
    catch (NumberFormatException nfe) {
      errors.add(new ValidationError("day", "NaN"));
    }

    if (mon > 12 || mon < 1) {
      errors.add(new ValidationError("month", "Not a valid month"));
    }
    else {
      this.intMonth = mon;
    }

    if (numDay < 1) {
      errors.add(new ValidationError("day", "Not a valid day."));
    }
    else {
      this.intDay = numDay;
    }

    if ((mon == 4 || mon == 6 || mon == 9 || mon == 11) && (numDay >= 31)) {
      errors.add(new ValidationError("day", "Cannot have more than 30 numDays in this month"));
    }
    else {
      this.intMonth = mon;
      this.intDay = numDay;
    }

    if ((mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8 || mon == 10 || mon == 12) && (numDay >= 32)) {
      errors.add(new ValidationError("day", "Cannot have more than 31 numDays in this month"));
    }
    else {
      this.intMonth = mon;
      this.intDay = numDay;
    }

    if (isLeapYear(year) && mon == 2 && numDay >= 30) {
      errors.add(new ValidationError("day", "Cannot have more than 29 numDays in Feb"));
    }
    else {
      this.intMonth = mon;
      this.intDay = numDay;
    }

    if (!isLeapYear(year) && mon == 2 && numDay >= 29) {
      errors.add(new ValidationError("day", "Cannot have more than 28 numDays in Feb"));
    }
    else {
      this.intMonth = mon;
      this.intDay = numDay;
    }

    try {
      hours = Integer.parseInt(hour);
    }
    catch (NumberFormatException nfe) {
      errors.add(new ValidationError("hour", "NaN"));
    }

    if (hours > 24 || hours < 0) {
      errors.add(new ValidationError("hour", "Not a valid hour."));
    }
    else {
      this.intHours = hours;
    }

    try {
      minutes = Integer.parseInt(min);
    }
    catch (NumberFormatException nfe) {
      errors.add(new ValidationError("min", "NaN"));
    }

    if (minutes > 59 || minutes < 0) {
      errors.add(new ValidationError("min", "Not a valid minute"));
    }
    else {
      this.intMinutes = minutes;
    }

    DateTime today = new DateTime();

    DateTime date = new DateTime(year, intMonth, intDay, intHours, intMinutes);

    int days = Days.daysBetween(today, date).getDays();

    if (days < 0) {
      errors.add(new ValidationError("day", "You can't schedule something in the past."));
    }
    if (days == 0) {
      if (intHours < today.hourOfDay().get()) {
        errors.add(new ValidationError("hour", "You can't schedule something in the past."));
      }
      if (intHours == today.hourOfDay().get()) {
        if (intMinutes < today.minuteOfDay().get()) {
          errors.add(new ValidationError("min", "You can't schedule something in the past."));
        }
      }
    }

    return errors.isEmpty() ? null : errors;

  }

  /**
   * Checks to see if the given year is a leap year.
   * 
   * @param year the year
   * @return true if year is leap year, false if not.
   */
  private static boolean isLeapYear(int year) {

    if (year % 4 == 0) {
      if (year % 100 == 0) {
        if (year % 400 == 0) {
          return true;
        }
        return false;
      }
      return true;
    }
    return true;
  }
}
