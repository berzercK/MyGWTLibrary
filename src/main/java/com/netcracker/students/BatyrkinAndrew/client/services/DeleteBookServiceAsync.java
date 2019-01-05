package com.netcracker.students.BatyrkinAndrew.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.netcracker.students.BatyrkinAndrew.shared.MyBook;

public interface DeleteBookServiceAsync {
    void deleteBook(MyBook deletedMyBook, AsyncCallback<Void> async);
}
