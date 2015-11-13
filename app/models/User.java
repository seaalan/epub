package models;

import org.mindrot.jbcrypt.BCrypt;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User extends Model {
    @Id
    private String email;
    private String password;

    // Constructor
    public User(String email, String password) {
        //Password encryption
        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
        this.email = email;
        this.password = passwordHash;
    }

    // Query
    public static Model.Finder<Integer, User> find =
            new Model.Finder<Integer, User>(Integer.class, User.class);

    // Authentification
    public static User authenticate(String email, String password) {
        User user =  find.where()
                .eq("email", email)
                .findUnique();
        if (user == null) {
            return user;
        } else if (BCrypt.checkpw(password, user.password)) {
            return user;
        } else {
            return null;
        }
    }
}