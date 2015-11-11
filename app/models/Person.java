package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class Person extends Model {
    @Id
    public Integer id;
    public String name;

    // Query
    public static Finder<Integer,Person> find =
            new Finder<Integer,Person>(Integer.class, Person.class);

    public static List<Person> findAll() {
        return find.all();
    }

    public static Person findByName (String name) {
        return find.where().eq("name", name).findUnique();
    }
}