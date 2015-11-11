package models;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
public class Epub extends Model {

    @Id
    public Integer id;
    public String title;
    public String url;

    // Query
    public static Finder<Integer, Epub> find =
            new Finder<>(Integer.class, Epub.class);

    public static List<Epub> findAll() {
        return find.all();
    }

    public static Epub findById (String id) {
        return find.where().eq("id", id).findUnique();
    }
}