package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import play.db.ebean.Model;

/**
 * A model for a lecture.
 * 
 * @author Alvin
 *
 */
@Entity
public class Lecture extends Model {

  private static final long serialVersionUID = 1L;

  @Id
  private long id;

  private String course;
  private String level;
  private String topic;
  private String description;
  private String videoId;
  private String uniqueId;

  /**
   * Empty constructor.
   */
  public Lecture() {
  }

  /**
   * Constructs a new lecture.
   * 
   * @param course course, e.g. ICS
   * @param level level, e.g. 311
   * @param topic topic of lecture video
   * @param description optional description of video.
   * @param videoId YouTube video ID
   */
  public Lecture(String course, String level, String topic, String description, String videoId) {
    this.setCourse(course);
    this.setLevel(level);
    this.setTopic(topic);
    this.setDescription(description);
    this.setVideoId(videoId.substring(videoId.length() - 11, videoId.length()));
    this.setUniqueId(course.concat(level.concat(videoId.substring(videoId.length() - 11, videoId.length()))));
  }

  /**
   * THe EBean ORM finder method for database queries.
   * 
   * @return
   */
  public static Finder<Long, Lecture> find() {
    return new Finder<Long, Lecture>(Long.class, Lecture.class);
  }

  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @param description the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * @return the topic
   */
  public String getTopic() {
    return topic;
  }

  /**
   * @param topic the topic to set
   */
  public void setTopic(String topic) {
    this.topic = topic;
  }

  /**
   * @return the videoId
   */
  public String getVideoId() {
    return videoId;
  }

  /**
   * @param videoId the videoId to set
   */
  public void setVideoId(String videoId) {
    this.videoId = videoId;
  }

  /**
   * @return the course
   */
  public String getCourse() {
    return course;
  }

  /**
   * @param course the course to set
   */
  public void setCourse(String course) {
    this.course = course;
  }

  /**
   * @return the level
   */
  public String getLevel() {
    return level;
  }

  /**
   * @param level the level to set
   */
  public void setLevel(String level) {
    this.level = level;
  }

  /**
   * @return the uniqueId
   */
  public String getUniqueId() {
    return uniqueId;
  }

  /**
   * @param uniqueId the uniqueId to set
   */
  public void setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
  }

}
