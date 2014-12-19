package views.formdata;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import models.Lecture;
import models.LectureDB;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;

/**
 * Java backing class for Lecture's form data.
 * 
 * @author Alvin Wang
 *
 */
public class LectureForm {
  //private static final Integer VIDEO_ID_LENGTH = 11;

  @Id
  @GeneratedValue
  public long id;

  @Constraints.Required(message = "A course is required.")
  public String course;

  @Constraints.Required(message = "A level is required.")
  public String level;

  @Constraints.Required(message = "A topic(title) is requried.")
  public String topic;

  @Constraints.Required(message = "YouTube's 11 character ID is required.")
  public String videoId;

  public String description;

  /**
   * Empty constructor.
   */
  public LectureForm() {

  }

  /**
   * Creates LectureForm data for an existing lecture.
   * 
   * @param lecture an existing lecture
   */
  public LectureForm(Lecture lecture) {
    this.course = lecture.getCourse().toUpperCase();
    this.level = lecture.getLevel();
    this.topic = lecture.getTopic();
    this.description = lecture.getDescription();
    // this.videoId = lecture.getVideoId();
    this.videoId =
        lecture.getVideoId().substring(lecture.getVideoId().length() - 11, lecture.getVideoId().length());
  }

  /**
   * Creates new LectureForm data
   * 
   * @param course course, e.g. ICS
   * @param level level, e.g. 311
   * @param topic topic of lecture video
   * @param description optional description of video.
   * @param videoId YouTube video ID
   */
  public LectureForm(String course, String level, String topic, String description, String videoId) {
    this.course = course.toUpperCase();
    this.level = level;
    this.topic = topic;
    this.description = description;
    this.videoId = videoId.substring(videoId.length() - 11, videoId.length());
  }

  public long getId() {
    return id;
  }
  
  public String getCourse() {
    return course;
  }

  /**
   * Checks if all form fields are valid.
   * 
   * @return null if valid, a List of ValidationErrors if invalid.
   */
  public List<ValidationError> validate() {
    List<ValidationError> errors = new ArrayList<>();

    if (course == null || course.length() == 0) {
      errors.add(new ValidationError("course", "A course(e.g. 'ICS' is required."));
    }

    if (level == null || level.length() == 0) {
      errors.add(new ValidationError("level", "A level(e.g. '311' is required."));
    }

    if (topic == null || topic.length() == 0) {
      errors.add(new ValidationError("topic", "A topic(title) is required."));
    }

   // if (videoId.length() != VIDEO_ID_LENGTH) {
   //   errors.add(new ValidationError("videoId", "The Video Id needs to be 11 characters long."));
   // }

    if (LectureDB.isRepeatVideo(course, level, videoId)) {
      errors.add(new ValidationError("videoId", "Video with ID: " + videoId + " already exists for course: " + course
          + " " + level + "."));
    }
    return errors.isEmpty() ? null : errors;
  }
}
