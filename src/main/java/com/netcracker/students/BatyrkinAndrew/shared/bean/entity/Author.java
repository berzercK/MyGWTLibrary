package com.netcracker.students.BatyrkinAndrew.shared.bean.entity;

import java.util.Objects;

public class Author {
    private String name;
    private String email;

    public Author(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Author() {}

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return name + " (" + email + ")";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return name.equals(author.name) &&
                Objects.equals(email, author.email);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(email);
        result = 31 * result + name.hashCode();
        return result;
    }
}
