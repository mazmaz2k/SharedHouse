package com.mazmaz.sharedhouse;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SharedUsersInHouse {

    private Collection<String> sharedAdminHousesList;
    private Collection<String> sharedViewOnlyHousesList;


//    public SharedUsersInHouse(){ }
    public SharedUsersInHouse(){
        this.sharedAdminHousesList = new LinkedList<>();
        this.sharedViewOnlyHousesList = new LinkedList<>();

    }

    public void addAdminHouseList(String adminHouseToken){
        this.sharedAdminHousesList.add(adminHouseToken);

//        for(String u: this.sharedUsersList){
//            if(u.equals(userId)){
//                throw new IllegalArgumentException();
//
//            }
//        }
//        this.sharedUsersList.add(userId);
    }

    public void deleteAdminHouseList(String adminHouseToken){
        if(!this.sharedAdminHousesList.isEmpty()){
            this.sharedAdminHousesList.remove(adminHouseToken);
        }
    }

    public void addViewOnlyHouseList(String viewOnlyHouseToken){
        this.sharedViewOnlyHousesList.add(viewOnlyHouseToken);
    }

    public void deleteViewOnlyHouseList(String viewOnlyHouseToken){
        if(!this.sharedViewOnlyHousesList.isEmpty()){
            this.sharedViewOnlyHousesList.remove(viewOnlyHouseToken);
        }
    }

    public Collection<String> getSharedAdminHousesList() {
        return sharedAdminHousesList;
    }

    public Collection<String> getSharedViewOnlyHousesList() {
        return sharedViewOnlyHousesList;
    }


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
//        result.put("House Name", uniqueID);
//        addViewOnlyHouseList("");
//        addAdminHouseList("");
        result.put("Admin houses", Arrays.toString(sharedAdminHousesList.toArray()));
        result.put("View Only houses", sharedViewOnlyHousesList.toString());
//        result.put("House users", Arrays.asList(getUsersList()));
//        result.put("House missions", Arrays.asList(getPostNewTodoMissionList()));
//        result.put("stars", stars);

        return result;
    }
}
