package com.netcracker.students.BatyrkinAndrew;

import com.google.gson.Gson;
import com.netcracker.students.BatyrkinAndrew.shared.MyBook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TestGSON {
    public static void main(String[] args) {
        String fileName = "myBooks2.json";
        String fileName2 = "myBooks3.json";
        String fileName3 = "bookList.json";
        List<MyBook> myBookList = new ArrayList<>();

//        MyBook newMyBook = new MyBook(0, "Me", "HOHOHOH", 200, "2019", new Date(), BookType.Literature, 20);
//        MyBook newMyBook2 = new MyBook(1, "Пушкин", "Руслан и Людмила", 300, "1820", new Date(2018, 12, 29, 15, 30, 42));
//        MyBook newMyBook3 = new MyBook(2, "Толстой", "Война и мир", 1225, "1867", new Date(2018, 12, 29, 15, 32, 15));
//
//        myBookList.add(newMyBook);
//        myBookList.add(newMyBook2);
//        myBookList.add(newMyBook3);
//
//        for (MyBook mb :
//                myBookList) {
//            addBookToFile(fileName2, mb);
//        }

//        addBookToFile(fileName3, newMyBook);

        System.out.println(getBookBase(fileName3));
    }

    @SuppressWarnings("Duplicates")
    private static List<MyBook> getBookBase(String fileName) {
        List<MyBook> myBookList = new ArrayList<>();
        File file = new File(fileName);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String buffer = reader.readLine();
            while (buffer != null) {
                Gson gson = new Gson();
                MyBook myBook = gson.fromJson(buffer, MyBook.class);
                myBookList.add(myBook);
                buffer = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myBookList;
    }

    private static void addBookToFile(String fileName, MyBook newMyBook) {
        File file = new File(fileName);
        try {
            FileWriter fileWriter = new FileWriter((file), true);
            Gson gson = new Gson();
            fileWriter.write(gson.toJson(newMyBook));
            fileWriter.write('\n');
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

