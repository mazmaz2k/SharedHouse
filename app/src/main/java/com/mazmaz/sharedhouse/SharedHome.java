package com.mazmaz.sharedhouse;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class SharedHome {

    private String adminMail;
    private SharedHouse sharedHouse;


    public SharedHome(){}

    public SharedHome(String adminMail){
        this.adminMail = adminMail;

    }

    public String getAdminMail() {
        return adminMail;
    }

    public void setAdminMail(String adminMail) {
        this.adminMail = adminMail;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("admin mail", adminMail);
        result.put("shared houses", "");
//        result.put("title", title);
//        result.put("body", body);
//        result.put("starCount", starCount);
//        result.put("stars", stars);

        return result;
    }
}
