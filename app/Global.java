import static play.mvc.Results.badRequest;
import static play.mvc.Results.notFound;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import models.Course;
import models.Lecture;
import models.LectureDB;
import models.UserInfo;
import models.UserInfoDB;
import play.Application;
import play.GlobalSettings;
import play.libs.F.Promise;
import play.mvc.Http.RequestHeader;
import play.mvc.SimpleResult;
import views.formdata.LectureForm;
import views.html.InvalidUrl;

public class Global extends GlobalSettings {

  File courses = new File("public/files/Courses.txt");
  BufferedReader br = null;

  public void onStart(Application app) {

    if (Course.find().all().isEmpty()) {
      loadCourses(courses);
    }

    if (UserInfo.find().all().isEmpty()) {
      UserInfoDB
          .addUserInfo(
              "Administrative",
              "User",
              "admin@admin.com",
              "admin",
              "http://upload.wikimedia.org/wikipedia/commons/thumb/a/aa/Warning_notice_-_EVIL_ADMIN.svg/500px-Warning_notice_-_EVIL_ADMIN.svg.png");
    }

    if (Lecture.find().all().isEmpty()) {
      LectureDB.addLecture(new LectureForm("ICS", "311", "Topic 03 A: Asymtotic Notations",
          "Introduces asymptotic concepts and big-O notation.", "https://www.youtube.com/watch?v=y86z2OrIYQQ"));

      LectureDB.addLecture(new LectureForm("ICS", "311", "Topic 06 C: Hash Functions",
          "Examples of Hash Functions and Universal Hashing", "https://www.youtube.com/watch?v=jW4wCfz3DwE"));

      LectureDB.addLecture(new LectureForm("ICS", "314", "Introduction to ICS 314, Fall 2013",
          "introduction to software engineering. See http://ics314f13.wordpress.com",
          "https://www.youtube.com/watch?v=H_Oc1x-XdYo"));

      LectureDB.addLecture(new LectureForm("KOR", "101", "How to Introduce Yourself in Korean",
          "In Korea, manners are important, and this step-by-step video teaches you some of the basics you need to"
              + " be polite while speaking Korean. A native Korean teacher will explain the simple phrases necessary.",
          "https://www.youtube.com/watch?v=x9_BmcUk_Xs"));

      LectureDB.addLecture(new LectureForm("KOR", "201", "Intermediate Korean Practice 1",
          "Create complex sentence(s)", "https://www.youtube.com/watch?v=ZRJ5QKqstTM"));
    }

  }

  public Promise<SimpleResult> onHandlerNotFound(RequestHeader request) {
    return Promise.<SimpleResult> pure(notFound(InvalidUrl.render("Error 404")));
  }

  public Promise<SimpleResult> onBadRequest(RequestHeader request, String error) {
    return Promise.<SimpleResult> pure(badRequest("Don't try to hack the URL!"));
  }

  private void loadCourses(File file) {

    String line = "";

    try {
      br = new BufferedReader(new FileReader(file));
    }
    catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    try {
      while ((line = br.readLine()) != null) {
        String[] split = line.split("[()]");

        Course course = new Course(split[1].trim(), split[0].trim());

        course.save();
      }
    }
    catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
