package com.mazmaz.sharedhouse;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class PostNewTodoMission {

    private String mission, missionDate, missionContent;

    public PostNewTodoMission(){}
    public PostNewTodoMission(String mission, String missionDate, String missionContent){

        this.mission = mission;
        this.missionDate = missionDate;
        this.missionContent = missionContent;
    }

    public String getMission() {
        return mission;
    }

    public String getMissionContent() {
        return missionContent;
    }

    public String getMissionDate() {
        return missionDate;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public void setMissionContent(String missionContent) {
        this.missionContent = missionContent;
    }

    public void setMissionDate(String missionDate) {
        this.missionDate = missionDate;
    }


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Mission Name", mission);
        result.put("Mission Date", missionDate);
        result.put("Mission Content", missionContent);
//        result.put("body", body);
//        result.put("starCount", starCount);
//        result.put("stars", stars);

        return result;
    }
}
