package com.netcracker.students.BatyrkinAndrew.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.netcracker.students.BatyrkinAndrew.client.services.BatyrkinAndrewService;

public class BatyrkinAndrewServiceImpl extends RemoteServiceServlet implements BatyrkinAndrewService {
    // Implementation of sample interface method
    public String getMessage(String msg) {
        return "Client said: \"" + msg + "\"<br>Server answered: \"Hi!\"";
    }
}