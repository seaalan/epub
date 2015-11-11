package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by alex on 5/4/14.
 */
@Entity
@Table(name="cityinfo")
public class CityInfo extends Model {

    private static final long serialVersionUID = 1L;

    @Id
    private Integer ID;

    private String CITY_NO;

    private String CITY_NAME;

    public Integer getID() { return ID; }

    public void setID(Integer ID) { this.ID = ID; }

    public String getCITY_NO() { return CITY_NO; }

    public void setCITY_NO(String CITY_NO) { this.CITY_NO = CITY_NO; }

    public String getCITY_NAME() { return CITY_NAME; }

    public void setCITY_NAME(String CITY_NAME) { this.CITY_NAME = CITY_NAME; }

    public static Finder<String, CityInfo> find = new Finder<String, CityInfo>(
            String.class, CityInfo.class
    );


}
