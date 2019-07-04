package com.mazmaz.sharedhouse;

import android.widget.Toast;

import java.util.LinkedList;
import java.util.UUID;

public class postNewHouse {
    private String address, city, uniqueID;
    private LinkedList<String> UsersList;
    public postNewHouse(){
        UsersList = new LinkedList<>();
    }
    public postNewHouse(String address, String city){
        this.address = address;
        this.city = city;
        String uniqueID = UUID.randomUUID().toString();

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

    public String getUniqueID() {
        return uniqueID;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
