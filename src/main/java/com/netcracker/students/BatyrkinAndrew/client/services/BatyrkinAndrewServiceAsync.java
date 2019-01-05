package com.netcracker.students.BatyrkinAndrew.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface BatyrkinAndrewServiceAsync {
    void getMessage(String msg, AsyncCallback<String> async);
}
