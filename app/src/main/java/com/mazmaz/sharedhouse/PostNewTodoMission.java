package com.mazmaz.sharedhouse;

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
}
