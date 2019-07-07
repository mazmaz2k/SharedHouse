package com.mazmaz.sharedhouse;

import android.widget.Toast;

import com.google.firebase.database.Exclude;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;

public class postNewHouse {
    private String address, city, uniqueID;
    private LinkedList<String> UsersList;
    private String adminToken;

    private LinkedList<PostNewTodoMission> postNewTodoMissionList;
    public postNewHouse(){
        UsersList = new LinkedList<>();
        postNewTodoMissionList = new LinkedList<>();
    }
    public postNewHouse(String address, String city, String adminToken){
        this.address = address;
        this.city = city;
        String uniqueID = UUID.randomUUID().toString();
        this.adminToken = adminToken;

    }

    public LinkedList<String> getUsersList() {
        return UsersList;
    }

    public void addUser(String user){

        for(String u: this.UsersList){
            if(u.equals(user)){
                throw new IllegalArgumentException();

            }
        }
        this.UsersList.add(user);
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public LinkedList<PostNewTodoMission> getPostNewTodoMissionList() {
        return postNewTodoMissionList;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public String getAdminToken(){return  this.adminToken;}

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("House Name", uniqueID);
        result.put("House address", address);
        result.put("House city", city);
        result.put("House users", Arrays.asList(getUsersList()));
        result.put("House missions", Arrays.asList(getPostNewTodoMissionList()));
//        result.put("stars", stars);

        return result;
    }
}
