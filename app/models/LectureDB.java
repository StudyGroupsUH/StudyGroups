package models;

import java.util.List;
import com.avaje.ebean.Expr;
import views.formdata.LectureForm;

/**
 * Database for lectures.
 * 
 * These methods have not been tested yet.
 * 
 * @author Alvin Wang
 *
 */
public class LectureDB {

  /**
   * Add lecture to lecture database.
   * 
   * @param data Lecture data
   */
  public static void addLecture(LectureForm data) {
    if (!isRepeatVideo(data.course, data.level, data.videoId)) {
      Lecture lecture = new Lecture(data.course.toUpperCase(), data.level, data.topic, data.description, data.videoId);
      lecture.save();
    }
    else {
      Lecture lecture = getLecture(data.course.toUpperCase(), data.level, data.videoId);
      lecture.setCourse(data.course.toUpperCase());
      lecture.setLevel(data.level);
      lecture.setTopic(data.topic);
      lecture.setDescription(data.description);
      lecture.setVideoId(data.videoId);
      lecture.save();
    }
  }
  
  /**
   * Delete a lecture from database with matching course, level, and videoId.
   * 
   * @param course course, e.g. ICS
   * @param level level, e.g. 311
   * @param videoId YouTube video ID
   */
  public static void deleteLecture(Lecture lecture) {
    Lecture.find().where().and(Expr.and(Expr.eq("course", lecture.getCourse()), Expr.eq("level", lecture.getLevel())), 
        Expr.eq("videoId", lecture.getVideoId())).findUnique().delete();
  }
  
  /**
   * Return a lecture video associated with given course, level, and videoId.
   * 
   * @param course course, e.g. ICS
   * @param level level, e.g. 311
   * @param videoId YouTube video ID
   * 
   * @return The retrieved lecture.
   */
  public static Lecture getLecture(String course, String level, String videoId) {
    return Lecture.find().where().and(Expr.and(Expr.eq("course", course), Expr.eq("level", level)), 
        Expr.eq("videoId", videoId)).findUnique();
  }
  
  /**
   * Returns a list of lectures of a course.
   * 
   * @param course the course
   * 
   * @return List of lectures.
   */
  public static List<Lecture> getLectures(String course) {
    return Lecture.find().where().eq("course", course).findList();
  }
  
  /**
   * Returns a list of lectures filtered by a level
   * 
   * @param course the course
   * @param level the level
   * 
   * @return List of lectures of a certain level
   */
  public static List<Lecture> getLectures(String course, String level) {
    return Lecture.find().where().and(Expr.eq("course", course), Expr.eq("level", level)).findList();
  }
  
  /**
   * Checks if video already exists in given course and level.
   * 
   * @param course course, e.g. ICS
   * @param level level, e.g. 311
   * @param videoId YouTube video ID
   * 
   * @return true if it already exist, false otherwise.
   */
  public static boolean isRepeatVideo(String course, String level, String videoId) {
    return Lecture.find().where().and(Expr.and(Expr.eq("course", course), Expr.eq("level", level)), 
        Expr.eq("videoId", videoId)).findUnique() != null;
  }
}
