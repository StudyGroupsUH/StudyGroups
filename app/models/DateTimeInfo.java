package models;

import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * A class to return the information for the current day.
 * 
 * @author Alvin Prieto
 *
 */
public class DateTimeInfo {

  private static DateTime today = new DateTime();

  /**
   * Returns the current month.
   * 
   * @return month
   */
  public static int getMonth() {
    return today.monthOfYear().get();
  }

  /**
   * Returns the current number day.
   * 
   * @return day number
   */
  public static int getDay() {
    return today.dayOfMonth().get();
  }

  /**
   * Returns the current year.
   * 
   * @return current year
   */
  public static int getYear() {
    return today.year().get();
  }

  /**
   * Returns the current hour.
   * 
   * @return current hour
   */
  public static int getHour() {
    return today.hourOfDay().get();
  }

  /**
   * Returns the current minute.
   * 
   * @return current minute
   */
  public static int getMinute() {
    return today.minuteOfHour().get();
  }

  /**
   * Formats the date part.
   * 
   * @param time the time
   * @return the formatted date
   */
  public static String getDateString(DateTime time) {

    String pattern = "dd, yyyy";
    DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
    return getStringMonth(time) + " " + fmt.print(time);
  }

  /**
   * Formats the time part.
   * 
   * @param time the time
   * @return the formatted time
   */
  public static String getTimeString(DateTime time) {
    String pattern = "h:mm a";
    DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
    return fmt.print(time);
  }

  /**
   * Returns the month in string form.
   * 
   * @param date date
   * @return month
   */
  private static String getStringMonth(DateTime date) {
    int currMonth = date.monthOfYear().get();
    String month = "";
    switch (currMonth) {
    case 1:
      month = "Jan";
      break;
    case 2:
      month = "Feb";
      break;
    // Magic numbers are cool
    case 3:
      month = "Mar";
      break;
    case 4:
      month = "Apr";
      break;
    case 5:
      month = "May";
      break;
    case 6:
      month = "Jun";
      break;
    case 7:
      month = "Jul";
      break;
    case 8:
      month = "Aug";
      break;
    case 9:
      month = "Sep";
    case 10:
      month = "Oct";
      break;
    case 11:
      month = "Nov";
      break;
    case 12:
      month = "Dec";
      break;
    default:
      // This really shouldn't happen
      break;
    }
    return month;
  }

}