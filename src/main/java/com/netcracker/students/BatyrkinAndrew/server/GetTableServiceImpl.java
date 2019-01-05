package com.netcracker.students.BatyrkinAndrew.server;

import com.google.gson.Gson;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.netcracker.students.BatyrkinAndrew.client.services.GetTableService;
import com.netcracker.students.BatyrkinAndrew.shared.IPathFile;
import com.netcracker.students.BatyrkinAndrew.shared.MyBook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GetTableServiceImpl extends RemoteServiceServlet implements GetTableService {
    @Override
    public ArrayList<MyBook> getListOfBook() {
        ArrayList<MyBook> myBookList = new ArrayList<>();

        File file = new File(getServletContext().getRealPath(IPathFile.PATH));
//        System.out.println(getServletContext().getRealPath("/myBooks.json"));

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

}
