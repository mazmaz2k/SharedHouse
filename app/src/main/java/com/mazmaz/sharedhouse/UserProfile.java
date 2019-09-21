package com.mazmaz.sharedhouse;


import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class UserProfile {

    private String username;
    private String password;
    private SharedUsersInHouse sharedUsersInHouse;

    public UserProfile() {
        //firebase constructor
    }


    public UserProfile(String username, String password) {

        this.username = username;
        this.password = password;
        this.sharedUsersInHouse = new SharedUsersInHouse();

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public SharedUsersInHouse getSharedUsersInHouse() {
        return sharedUsersInHouse;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("username", username);
        result.put("password", password);
        result.put("Permissions", sharedUsersInHouse.toMap());
//        result.put("House users", Arrays.asList(getUsersList()));
//        result.put("House missions", Arrays.asList(getPostNewTodoMissionList()));
//        result.put("stars", stars);

        return result;
    }
}
