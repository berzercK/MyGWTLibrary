package com.netcracker.students.BatyrkinAndrew.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.netcracker.students.BatyrkinAndrew.shared.bean.MyBook;

@RemoteServiceRelativePath("AddNewBookService")
public interface AddNewBookService extends RemoteService {
    void addNewBook(MyBook newMyBook);
}