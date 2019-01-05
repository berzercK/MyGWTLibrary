package com.netcracker.students.BatyrkinAndrew.shared.bean.entity;

import java.util.List;
import java.util.Objects;

public class Book {
    private BookType typeEnum;
    private List<Author> authors;
    private String year; //isValidYear - parseInt
    private String name;
    private Short volumeOfPage;
    private Integer count;
    private Boolean isStock;

    public Book() {
    }

    public Book(List<Author> authors, String name, short volumeOfPage) {
        typeEnum = BookType.Literature;
        year = String.valueOf(2019);
        isStock = true;
        count = 1;
        this.authors = authors;
        this.name = name;
        this.volumeOfPage = volumeOfPage;
    }

    public Book(BookType typeEnum, List<Author> authors, String year, String name, short volumeOfPage, int count, boolean isStock) {
        this.typeEnum = typeEnum;
        this.authors = authors;
        this.year = year;
        this.name = name;
        this.volumeOfPage = volumeOfPage;
        this.count = count;
        this.isStock = isStock;
    }

    public BookType getTypeEnum() {
        return typeEnum;
    }

    public void setTypeEnum(BookType typeEnum) {
        this.typeEnum = typeEnum;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTitle() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getVolumeOfPage() {
        return volumeOfPage;
    }

    public void setVolumeOfPage(short volumeOfPage) {
        this.volumeOfPage = volumeOfPage;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String isStock() {
        return isStock ? " Yes " : " No ";
    }

    public void setStock(boolean stock) {
        this.isStock = stock;
    }

    @Override
    public String toString() {
        return "Book{" +
                "typeEnum=" + typeEnum +
                ", authors=" + printAuthors() +
                ", year=" + year +
                ", name='" + name + '\'' +
                ", volumeOfPage=" + volumeOfPage +
                ", count=" + count +
                ", isStock=" + isStock +
                '}';
    }

    private String printAuthors() {
        StringBuilder authors = new StringBuilder();
        for (Author a :
                this.authors) {
            authors.append(a.toString());
        }

        return authors.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return typeEnum == book.typeEnum &&
                authors.equals(book.authors) &&
                Objects.equals(year, book.year) &&
                name.equals(book.name) &&
                volumeOfPage.equals(book.volumeOfPage) &&
                Objects.equals(count, book.count) &&
                isStock.equals(book.isStock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeEnum, authors, year, name, volumeOfPage, count, isStock);
    }
}
