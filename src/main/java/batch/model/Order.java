package batch.model;

import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * Created by Max Sveshnikov on 06.09.16.
 */
public class Order {
    @Id
    public String id;

    public String no;
    public Date date;
    public Customer customer;

    public Order(Customer customer, String no) {
        date = new Date();
        this.customer = customer;
        this.no = no;
    }
}
