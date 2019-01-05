package com.netcracker.students.BatyrkinAndrew.server;

import com.google.gson.Gson;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.netcracker.students.BatyrkinAndrew.client.services.AddNewBookService;
import com.netcracker.students.BatyrkinAndrew.shared.IPathFile;
import com.netcracker.students.BatyrkinAndrew.shared.MyBook;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AddNewBookServiceImpl extends RemoteServiceServlet implements AddNewBookService {
    @Override
    public void addNewBook(MyBook newMyBook) {
        File file = new File(getServletContext().getRealPath(IPathFile.PATH));
        try {
            FileWriter fileWriter = new FileWriter((file), true);
            Gson gson = new Gson();
            fileWriter.write(gson.toJson(newMyBook));
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
