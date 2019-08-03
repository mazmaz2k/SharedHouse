package com.mazmaz.sharedhouse;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class PostNewTodoMission {

    private String content, date, name;

    public PostNewTodoMission(){}


    public PostNewTodoMission( String content,  String date, String name)
    {
        this.name = name;
        this.date = date;
        this.content = content;
    }

    public String getName() {
        return this.name;
    }

    public String getContent() {
        return this.content;
    }

    public String getDate() {
        return this.date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContent(String content) {this.content = content; }

    public void setMissionDate(String date) {
        this.date = date;
    }


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Mission Content", content);
        result.put("Mission Date", date);
        result.put("Mission Name", name);

        return result;
    }


}
