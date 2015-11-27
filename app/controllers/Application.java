package controllers;

import controllers.constant.Constant;
import controllers.convert.DocePub;
import controllers.convert.EmailePub;
import controllers.convert.TxtePub;
import controllers.crud.*;
import controllers.data.DataEntry;
import controllers.rssToePub.RssToEpub;
import controllers.utility.Html2Xhtml;
import models.Epub;
import models.Person;
import models.User;
import nl.siegmann.epublib.domain.Book;
import org.mindrot.jbcrypt.BCrypt;
import play.data.Form;
import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.Required;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Application extends Controller {

    /**
     * jump to index page.
     */
    public static Result index() {
        //return ok(views.html.index.render("Hello Play Framework"));
        //compare session information
        String email = session("email");
        if (email != null) {
            //return ok(email);
            return ok(views.html.index.render(email + ",Hello Play Framework"));
        } else {
            //return ok("not login");
            return redirect("/login");
        }
    }

    /**
     * registration class.
     */
    public static class Registration {
        @Email
        public String email;
        @Required
        public String password;
    }

    /**
     * jump to registration page.
     */
    public static Result register() {
        Form<Registration> userForm = Form.form(Registration.class);
        return ok(views.html.register.render(userForm));
    }

    /**
     * registration new user.
     */
    public static Result postRegister() {
        Form<Registration> userForm = Form.form(Registration.class).bindFromRequest();
        User user = new User(userForm.get().email, userForm.get().password);
        user.save();
        return ok("registered");
    }

    /**
     * Login class.
     */
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

    /**
     * jump to login page.
     */
    public static Result login() {
        Form<Login> userForm = Form.form(Login.class);
        return ok(views.html.login.render(userForm));
    }

    /**
     * login one user.
     */
    public static Result postLogin() {
        Form<Login> userForm = Form.form(Login.class).bindFromRequest();
        if (userForm.hasErrors()) {
            //return badRequest("Wrong user/password");
            return badRequest(views.html.login.render(userForm));
        } else {
            //return ok("Valid user");
            session().clear();
            session("email", userForm.get().email);
            return redirect("/");
        }
    }

    /**
     * add new person to datebase.
     */
    public static Result addPerson() {
        Person p1 = new Person();
        Person p2 = new Person();
        p1.name = "vamei";
        p2.name = "play";
        p1.save();
        p2.save();
        return ok("Saved");
    }

    /**
     * get all persons from datebase.
     */
    public static Result allPerson() {
        //List<Person> persons = Person.findAll();
        List<Person> persons = Person.find.all();
        return ok(views.html.personList.render(persons));
    }

    /**
     * upload file.
     *
     * @param picture
     */
    public static boolean toupload(FilePart picture) {
        if (picture != null) {
            String fileName = picture.getFilename();
            String contentType = picture.getContentType();
            File file = picture.getFile();
            // save file to the disk
            file.renameTo(new File(Constant.ROOT_PATH, "/public/uploads/" + fileName));
            return true;
        } else {
            return false;
        }
    }

    /**
     * upload one file.
     */
    public static Result upload() {
        MultipartFormData body = request().body().asMultipartFormData();
        FilePart picture = body.getFile("picture");
        boolean isSuccess = toupload(picture);
        if (isSuccess) {
            return ok("uploaded success");
        } else {
            return badRequest("not a valid file");
        }
    }

    /**
     * jump to upload one file page.
     */
    public static Result uploadForm() {
        return ok(views.html.upload.render());
    }

    /**
     * upload multiple file page.
     */
    public static Result uploads() {
        MultipartFormData body = request().body().asMultipartFormData();
        List<FilePart> pictures = body.getFiles();
        boolean isSuccess = false;
        int successCount = 0;
        for (FilePart picture : pictures) {
            isSuccess = toupload(picture);
            if (isSuccess) successCount++;
        }
        if (isSuccess) {
            return ok(successCount + " files uploaded success");
        } else {
            return badRequest("uploaded fail");
        }
    }

    /**
     * jump to upload multiple file page.
     */
    public static Result uploadsForm() {
        return ok(views.html.uploads.render());
    }

    /**
     * jump to add ePub page.
     */
    public static Result addePubPage() {
        Form<Epub> epubForm = Form.form(Epub.class);
        return ok(views.html.addepub.render(epubForm));
    }

    /**
     * add a new ePub file.
     */
    public static Result addePub() {
        Form<Epub> ePubForm = Form.form(Epub.class).bindFromRequest();
        String title = ePubForm.get().title;//from page's title

        AddePub translator = new AddePub();
        Book book = translator.addePub(title);
        if (book != null) {
            Epub epub = new Epub();
            epub.title = title;
            epub.url = Constant.ROOT_PATH + "\\" + title + ".epub";
            epub.save();
        }
        return ok("Saved");
    }

    /**
     * jump to edit ePub page.
     */
    public static Result editePubPage(String id) {
        Form<Epub> epubForm = Form.form(Epub.class);
        Epub epub = Epub.findById(id);
        return ok(views.html.editepub.render(epubForm, epub));
    }

    /**
     * edit one ePub file.
     */
    public static Result editePub() {
        Form<Epub> ePubForm = Form.form(Epub.class).bindFromRequest();
        EditePub.editePub(ePubForm);
        DataEntry.updateePub(ePubForm);
        return ok("edit success");
    }

    /**
     * get one ePub information to view.
     */
    public static Result getePub() {
        GetePub translator = new GetePub();
        List<Map<String, Object>> list = translator.getePub();
        return ok(views.html.epubListDemo.render(list));
    }

    /**
     * get all ePub files.
     */
    public static Result allePub() {
        List<Epub> epubs = Epub.find.all();
        return ok(views.html.epubList.render(epubs));
    }

    /**
     * jump to search ePub page.
     */
    public static Result searchePubPage() {
        return ok(views.html.searchepub.render());
    }

    /**
     * search ePub file.
     */
    public static Result searchePub() {
        Form<Epub> ePubForm = Form.form(Epub.class).bindFromRequest();
        List<Epub> epubList = SearchePub.searchePub(ePubForm);
        return ok(views.html.epubList.render(epubList));
    }

    /**
     * create ePub file from folder.
     */
    public static Result createePub() {
        CreateePub.createePubFromFolder();
        return ok("");
    }

    /**
     * create ePub file from chm file.
     */
    public static Result createePubFromCHM() {
//        CreateePub.createePubFromCHM();
        return ok("");
    }

    /**
     * create ePub file from chm file.
     */
    public static Result createePubFromRss() {
        RssToEpub.rssToEpub();
        return ok("");
    }

    /**
     * create ePub file from doc file.
     */
    public static Result docePub() {
        File file = new File(Constant.ROOT_PATH + "//e.epub");//"D:\\play\\epub\\e.doc"
        try {
            DocePub.docePub(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ok("ok");
    }

    /**
     * create ePub file from txt file.
     */
    public static Result txtePub() {
        File file = new File(Constant.ROOT_PATH + "//e.epub");//"D:\\play\\epub\\e.txt"
        try {
            TxtePub.txtePub(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ok("ok");
    }

    /**
     * create ePub file from doc file.
     */
    public static Result docx2ePub() {
        String filePath = (Constant.ROOT_PATH + "//public//docx//eee.docx").replace("\\", "//");//D://eee.docx
        String outFilePath = (Constant.ROOT_PATH + "//public//docx//out//").replace("\\", "//");//D://
        try {
            DocePub.docx2ePub(filePath, outFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ok("ok");
    }

    /**
     * create ePub file from txt file.
     */
    public static Result txt2ePub() {
        String filePath = (Constant.ROOT_PATH + "//public//txt//xxx.txt").replace("\\", "//");//D://xxx.txt
        String outFilePath = (Constant.ROOT_PATH + "//public//txt//out//").replace("\\", "//");//D://
        try {
            TxtePub.txt2ePub(filePath, outFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ok("ok");
    }

    /**
     * create ePub file from email file.
     */
    public static Result email2ePub() {
        String emailSubject = "me";//sea
        String outFilePath = (Constant.ROOT_PATH + "//public//email//out//").replace("\\", "//");//D://
        try {
            EmailePub.email2ePub(emailSubject, outFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok("ok");
    }

    /**
     * create ePub file from email Attachment file.
     */
    public static Result emailAttachment2ePub() {
        String emailSubject = "me";//sea
        String attachmentOutFilePath = (Constant.ROOT_PATH + "//public//docx//").replace("\\", "//");//D://
        String filePath = "";
        String ePubOutFilePath = (Constant.ROOT_PATH + "//public//docx//out//").replace("\\", "//");//D://
        try {
            filePath = EmailePub.emailAttachment2ePub(emailSubject, attachmentOutFilePath);
            DocePub.docx2ePub(filePath, ePubOutFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok("ok");
    }

    /**
     * create ePub file With Many Chapter from email Attachment file.
     */
    public static Result emailAttachment2ePubWithManyChapter() {
        String emailSubject = "me";//sea
        String attachmentOutFilePath = (Constant.ROOT_PATH + "//public//docx//").replace("\\", "//");//D://
        String filePath = "";
        String ePubOutFilePath = (Constant.ROOT_PATH + "//public//docx//out//").replace("\\", "//");//D://
        try {
            filePath = EmailePub.emailAttachment2ePub(emailSubject, attachmentOutFilePath);
            DocePub.docx2ePubWithManyChapter(filePath, ePubOutFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok("ok");
    }

    /**
     * create ePub file with many area from doc file.
     */
    public static Result docx2ePubWithManyChapter() {
        //String filePath = "D:\\play\\epub\\public\\docx\\out\\xxx\\xxx1.html";//D://eee.docx
        String filePath = (Constant.ROOT_PATH + "//public//docx//xxx.docx").replace("\\", "//");//D://eee.docx
        String outFilePath = (Constant.ROOT_PATH + "//public//docx//out//").replace("\\", "//");//D://
        try {
            DocePub.docx2ePubWithManyChapter(filePath, outFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok("ok");
    }

    /**
     * create ePub file With Many Chapter from txt file.
     */
    public static Result txt2ePubWithManyChapter() {
        String filePath = (Constant.ROOT_PATH + "//public//txt//xxx.txt").replace("\\", "//");//D://xxx.txt
        String outFilePath = (Constant.ROOT_PATH + "//public//txt//out//").replace("\\", "//");//D://
        try {
            TxtePub.txt2ePubWithManyChapter(filePath, outFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ok("ok");
    }

    /**
     * create xhtml From html
     */
    public static Result html2xhtml() {
        String filePath = (Constant.ROOT_PATH + "\\public\\docx\\out\\eee\\qqq.html");//D://eee.docx
        String outFilePath = (Constant.ROOT_PATH + "\\public\\docx\\out\\eee\\qqq.xhtml");//D://
        try {
            Html2Xhtml.html2Xhtml(filePath, outFilePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return ok("ok");
    }

    /**
     * Password encryption test.
     */
    public static Result bcrypt() {
        String passwordHash = BCrypt.hashpw("Hello", BCrypt.gensalt());
        boolean correct = BCrypt.checkpw("Hello", passwordHash);
        boolean wrong = BCrypt.checkpw("World", passwordHash);
        return ok(passwordHash + " " + correct + " " + wrong);
    }

//    public static Result getCity() {
//        List<CityInfo> detailList = CityInfo.find.all();
//        if (null == detailList) {
//            return notFound();
//        }
//        return ok(Json.toJson(detailList));
//    }Translator
}
