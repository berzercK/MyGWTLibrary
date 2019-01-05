package com.netcracker.students.BatyrkinAndrew.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.netcracker.students.BatyrkinAndrew.shared.bean.MyBook;

public interface AddNewBookServiceAsync {
    void addNewBook(MyBook newMyBook, AsyncCallback<Void> async);
}
