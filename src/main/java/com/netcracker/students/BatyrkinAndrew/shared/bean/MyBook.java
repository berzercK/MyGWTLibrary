package com.netcracker.students.BatyrkinAndrew.shared.bean;

import com.netcracker.students.BatyrkinAndrew.shared.bean.entity.BookType;

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

    private BookType typeEnum;
    private int count;

    public MyBook() {
    }

    public MyBook(int id, String author, String title, int pageAmount, String dateOfPublication, Date dateOfAdded, BookType typeEnum, int count) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.pageAmount = pageAmount;
        this.dateOfPublication = dateOfPublication;
        this.dateOfAdded = dateOfAdded;

        this.typeEnum = typeEnum;
        this.count = count;
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

    public BookType getTypeEnum() {
        return typeEnum;
    }

    public void setTypeEnum(BookType typeEnum) {
        this.typeEnum = typeEnum;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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
                count == myBook.count &&
                author.equals(myBook.author) &&
                title.equals(myBook.title) &&
                Objects.equals(dateOfPublication, myBook.dateOfPublication) &&
                dateOfAdded.equals(myBook.dateOfAdded) &&
                typeEnum == myBook.typeEnum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, title, pageAmount, dateOfPublication, dateOfAdded, typeEnum, count);
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
                ", typeEnum=" + typeEnum +
                ", count=" + count +
                '}';
    }

    public int compareId(MyBook o) {
        if (o.id == id) return 0;
        return o.id > id ? 1 : -1;
    }

    public int compareTitle(MyBook o) {
        return o.title.compareTo(this.title);
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

