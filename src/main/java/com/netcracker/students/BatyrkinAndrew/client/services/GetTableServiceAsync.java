package com.netcracker.students.BatyrkinAndrew.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.netcracker.students.BatyrkinAndrew.shared.bean.MyBook;

import java.util.ArrayList;

public interface GetTableServiceAsync {
    void getListOfBook(AsyncCallback<ArrayList<MyBook>> async);
}
