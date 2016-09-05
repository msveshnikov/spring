package batch;

/**
 * Created by Max Sveshnikov on 05.09.16.
 */
public class Person {

    private String firstName;
    private String title;
    private String phone;

    public Person() {

    }


    public Person(String name, String title, String phone) {
        this.firstName = name;
        this.title = title;
        this.phone = phone;
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
