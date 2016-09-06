package batch.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Max Sveshnikov on 05.09.16.
 */
@Entity
public class Person {
    @Id
    @GeneratedValue
    private long id;

    private String firstName;
    private String title;
    private String phone;
    private String state;
    private String county;

    public Person() {

    }

    public Person(String name, String title, String phone, String state, String county) {
        this.firstName = name;
        this.title = title;
        this.phone = phone;
        this.state = state;
        this.county = county;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    @Override
    public String toString() {
        return "title: " + title + ", firstName: " + firstName + ", phone: " + phone;
    }
}