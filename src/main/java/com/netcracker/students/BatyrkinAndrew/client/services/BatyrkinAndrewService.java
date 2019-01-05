package com.netcracker.students.BatyrkinAndrew.client.services;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("BatyrkinAndrewService")
public interface BatyrkinAndrewService extends RemoteService {
    // Sample interface method of remote interface
    String getMessage(String msg);

    /**
     * Utility/Convenience class.
     * Use BatyrkinAndrewService.App.getInstance() to access static instance of BatyrkinAndrewServiceAsync
     */
    public static class App {
        private static BatyrkinAndrewServiceAsync ourInstance = GWT.create(BatyrkinAndrewService.class);

        public static synchronized BatyrkinAndrewServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
