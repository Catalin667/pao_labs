package com.company.services;


import com.company.serialization.WriteInCSV;
import com.company.users.ActionUser;

import java.io.IOException;
import java.util.Date;

public class AuditService {
    private static AuditService instance;

    private AuditService(){
    }

    public static AuditService createAuditService() {
        if (instance == null) {
            instance = new AuditService();
        }
        return instance;
    }

    void action(String user, String action){
        Date date = new Date();
        ActionUser actionUser = new ActionUser(user,action,date);
        try {
            WriteInCSV.writeInCSV(ActionUser.class,"src/com.company/resorces/AuditFile.csv", actionUser);
        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }




}
