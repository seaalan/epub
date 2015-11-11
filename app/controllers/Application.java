package controllers;

import models.Epub;
import models.Person;
import models.User;
import nl.siegmann.epublib.domain.Book;
import play.mvc.Controller;
import play.mvc.Result;
import java.util.List;
import java.io.File;
import java.util.Map;

import org.mindrot.jbcrypt.BCrypt;
import play.Play;
import play.data.Form;
import play.data.validation.Constraints.*;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.*;
import play.mvc.Http.MultipartFormData.FilePart;

public class Application extends Controller {
    
    public static Result index() {
        //return ok(views.html.index.render("Hello Play Framework"));

        String email = session("email");
        if (email != null) {
            //return ok(email);
            return ok(views.html.index.render(email+",Hello Play Framework"));
        } else {
            //return ok("not login");
            return redirect("/login");
        }
    }

    public static Result upload() {
        MultipartFormData body = request().body().asMultipartFormData();
        FilePart picture = body.getFile("picture");
        if (picture != null) {
            String fileName = picture.getFilename();
            String contentType = picture.getContentType();
            File file   = picture.getFile();
            // get the root path of the Play project
            File root = Play.application().path();
            // save file to the disk
            file.renameTo(new File(root, "/public/uploads/" + fileName));
            return ok(fileName + " " + contentType + " uploaded");
        } else {
            return badRequest("not a valid file");
        }
    }

    public static Result uploadForm() {
        return ok(views.html.upload.render());
    }

    public static class Registration {
        @Email
        public String email;
        @Required
        public String password;
    }

    public static Result register() {
        Form<Registration> userForm = Form.form(Registration.class);
        return ok(views.html.register.render(userForm));
    }

    public static Result postRegister() {
        Form<Registration> userForm = Form.form(Registration.class).bindFromRequest();
        User user = new User(userForm.get().email, userForm.get().password);
        user.save();
        return ok("registered");
    }

    public static class Login {
        @Email
        public String email;
        @Required
        public String password;

        public String validate() {
            if (User.authenticate(email, password) == null) {
                return "Invalid user or password";
            }
            return null;
        }
    }

    public static Result login() {
        Form<Login> userForm = Form.form(Login.class);
        return ok(views.html.login.render(userForm));
    }

    public static Result postLogin() {
        Form<Login> userForm = Form.form(Login.class).bindFromRequest();
        if (userForm.hasErrors()) {
            //return badRequest("Wrong user/password");
            return badRequest(views.html.login.render(userForm));
        } else {
            //return ok("Valid user");
            session().clear();
            session("email",userForm.get().email);
            return redirect("/");
        }
    }

    public static Result bcrypt() {
        String passwordHash = BCrypt.hashpw("Hello",BCrypt.gensalt());
        boolean correct = BCrypt.checkpw("Hello", passwordHash);
        boolean wrong = BCrypt.checkpw("World", passwordHash);
        return ok(passwordHash + " " + correct + " " + wrong);
    }

    public static Result addPerson() {
        Person p1 = new Person();
        Person p2 = new Person();
        p1.name = "vamei";
        p2.name = "play";
        p1.save();
        p2.save();
        return ok("Saved");
    }

    public static Result allPerson() {
        //List<Person> persons = Person.findAll();
        List<Person> persons = Person.find.all();
        return ok(views.html.personList.render(persons));
    }
//    public static Result getCity() {
//        List<CityInfo> detailList = CityInfo.find.all();
//        if (null == detailList) {
//            return notFound();
//        }
//        return ok(Json.toJson(detailList));
//    }Translator
    public static Result addePub() {
        Form<Epub> ePubForm = Form.form(Epub.class).bindFromRequest();

        AddePub translator = new AddePub();
        Book book = translator.addePub(ePubForm.get().title);
        if(book != null){
            Epub epub = new Epub();
            epub.title = ePubForm.get().title;
            epub.url = Play.application().path() +"\\"+ ePubForm.get().title +".epub";
            epub.save();
        }
        return ok("Saved");
    }

    public static Result editePub() {
        EditePub translator = new EditePub();
        Form<Epub> ePubForm = Form.form(Epub.class).bindFromRequest();
        Epub epub = Epub.findById(ePubForm.get().id.toString());
        translator.editePub(ePubForm.get().title,epub.url);
        epub.title = ePubForm.get().title;
        epub.update();
        return ok("edit success");
    }

    public static Result getePub() {
        GetePub translator = new GetePub();
        List<Map<String,Object>> list = translator.getePub();
        return ok(views.html.epubListDemo.render(list));
    }

    public static Result addePubPage() {
        Form<Epub> epubForm = Form.form(Epub.class);
        return ok(views.html.addepub.render(epubForm));
    }

    public static Result allePub() {
        List<Epub> epubs = Epub.find.all();
        return ok(views.html.epubList.render(epubs));
    }

    public static Result editePubPage(String id) {
        Form<Epub> epubForm = Form.form(Epub.class);
        Epub epub = Epub.findById(id);
        return ok(views.html.editepub.render(epubForm,epub));
    }
}
