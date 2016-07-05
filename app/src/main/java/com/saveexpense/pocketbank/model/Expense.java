package com.saveexpense.pocketbank.model;

import java.util.Date;

/**
 * Created by Parteek on 7/2/2016.
 */
public class Expense {
    private int id;

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", price=" + price +
                ", note='" + note + '\'' +
                ", date=" + date +
                ", category='" + category + '\'' +
                '}';
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private Float price ;
    private String note;
    private Date date;
    private String category;

}
