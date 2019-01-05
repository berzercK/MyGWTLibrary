package com.netcracker.students.BatyrkinAndrew.shared.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class MyBook implements Serializable, Comparable<MyBook> {
    private int id;
    private String author;
    private String title;
    private int pageAmount;
    private String dateOfPublication;
    private Date dateOfAdded;

    public MyBook() {
    }

    public MyBook(int id, String author, String title, int pageAmount, String dateOfPublication, Date dateOfAdded) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.pageAmount = pageAmount;
        this.dateOfPublication = dateOfPublication;
        this.dateOfAdded = dateOfAdded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPageAmount() {
        return pageAmount;
    }

    public void setPageAmount(int pageAmount) {
        this.pageAmount = pageAmount;
    }

    public String getDateOfPublication() {
        return dateOfPublication;
    }

    public void setDateOfPublication(String dateOfPublication) {
        this.dateOfPublication = dateOfPublication;
    }

    public Date getDateOfAdded() {
        return dateOfAdded;
    }

    public void setDateOfAdded(Date dateOfAdded) {
        this.dateOfAdded = dateOfAdded;
    }

    @Override
    public int compareTo(MyBook o) {
        return o.author.compareTo(this.author); //sort by Title of book
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyBook myBook = (MyBook) o;
        return id == myBook.id &&
                pageAmount == myBook.pageAmount &&
                Objects.equals(author, myBook.author) &&
                Objects.equals(title, myBook.title) &&
                Objects.equals(dateOfPublication, myBook.dateOfPublication) &&
                Objects.equals(dateOfAdded, myBook.dateOfAdded);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, title, pageAmount, dateOfPublication, dateOfAdded);
    }

    @Override
    public String toString() {
        return "MyBook{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", pageAmount=" + pageAmount +
                ", dateOfPublication='" + dateOfPublication + '\'' +
                ", dateOfAdded=" + dateOfAdded +
                '}';
    }
}


/*
* {
  "id": 2,
  "author": "Толстой",
  "title": "Война и Мир",
  "pageAmount": 1225,
  "dateOfPublication": "1867",
  "dateOfAdded": "Sat Dec 29 15:32:15 MSK 2018"
}
*/

