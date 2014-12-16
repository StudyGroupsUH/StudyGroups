package models;

/**
 * Handles url stuff.
 * 
 * @author Alvin Prieto
 *
 */
public class Slug {

  /**
   * Slugifies a string.
   * 
   * ex. ICS 311; normal string -> ICS%311, slugified -> ics-311
   * 
   * @param url string to be slugified.
   * @return slugified url
   */
  public static String slugify(String url) {
    return url.replace(' ', '-').toLowerCase();
  }

  /**
   * Unslugifies a string.
   * 
   * ex. ICS 311, slugified -> ics-311, unslugified -> ICS 311
   * 
   * @param url string to be unslugified
   * @return unslugified string.
   */
  public static String unSlugify(String url) {
    return url.replace('-', ' ').toUpperCase();
  }

  /**
   * Unslugifies a name.
   * 
   * @param name the name
   * @return a name
   */
  public static String unSlugifyName(String name) {
    name = name.replace('-', ' ');
    String[] names = name.split("\\s+");
    names[0] = names[0].substring(0, 1).toUpperCase() + names[0].substring(1, names[0].length());
    names[1] = names[1].substring(0, 1).toUpperCase() + names[1].substring(1, names[1].length());
    return names[0] + " " + names[1];
  }

}
