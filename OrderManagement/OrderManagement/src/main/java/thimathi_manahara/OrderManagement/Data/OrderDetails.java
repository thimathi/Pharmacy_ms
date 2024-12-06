package thimathi_manahara.OrderManagement.Data;

import jakarta.persistence.*;

import java.sql.Time;
import java.util.Date;

@Entity

@Table(name = "orders")
public class OrderDetails {

    @Id                                                     //this is going to represent the pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "order_id")
    private int order_id;

    @Column(name = "user_id")
    private int userid;

    @Column(name = "order_date")
    @Temporal(TemporalType.DATE)  // Use this for date and time, or TemporalType.DATE for date only
    private Date order_date;

    @Column(name = "total_amount")
    private int total_amount;

    @Column(name = "status")
    private String status;

    @Column(name = "created_at")
    private Time created_at;

    @Column(name = "updated_at")
    private Time updated_at;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int  getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }

    public int getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Time getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Time created_at) {
        this.created_at = created_at;
    }

    public Time getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Time updated_at) {
        this.updated_at = updated_at;
    }
}
