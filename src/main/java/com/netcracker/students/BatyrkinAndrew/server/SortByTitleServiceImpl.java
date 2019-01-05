package com.netcracker.students.BatyrkinAndrew.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.netcracker.students.BatyrkinAndrew.client.services.SortByTitleService;
import com.netcracker.students.BatyrkinAndrew.shared.bean.MyBook;

import java.util.ArrayList;

public class SortByTitleServiceImpl extends RemoteServiceServlet implements SortByTitleService {
    @Override
    public ArrayList<MyBook> sortByTitle(ArrayList<MyBook> myBooks) {
        myBooks.sort(MyBook::compareTo);
        return myBooks;
    }
}
