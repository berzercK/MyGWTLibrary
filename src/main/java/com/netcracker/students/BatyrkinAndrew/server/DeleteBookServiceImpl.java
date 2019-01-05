package com.netcracker.students.BatyrkinAndrew.server;

import com.google.gson.Gson;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.netcracker.students.BatyrkinAndrew.client.services.DeleteBookService;
import com.netcracker.students.BatyrkinAndrew.shared.bean.IPathFile;
import com.netcracker.students.BatyrkinAndrew.shared.bean.MyBook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DeleteBookServiceImpl extends RemoteServiceServlet implements DeleteBookService {
    @Override
    public void deleteBook(MyBook deletedMyBook) {
        List<MyBook> myBooks = new ArrayList<>();

        try {
            //read file and remove book
            File myBooksFile = new File(getServletContext().getRealPath(IPathFile.PATH));
            BufferedReader reader = new BufferedReader(new FileReader(myBooksFile));

            String buffer = reader.readLine();
            while (buffer != null) {
                Gson gson = new Gson();
                MyBook myBook = gson.fromJson(buffer, MyBook.class);
                myBooks.add(myBook);
                buffer = reader.readLine();
            }
            myBooks.remove(deletedMyBook);

            //rewrite file
            FileWriter fileWriter = new FileWriter(myBooksFile, false);
            Gson gson = new Gson();
            for (MyBook b :
                    myBooks) {
                fileWriter.write(gson.toJson(b) + '\n');
            }
            fileWriter.flush();
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private void readFromFile()
}
