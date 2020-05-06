package com.student_act.bookreserver;

import com.orm.SugarRecord;

public class StudentMeetingDataModel extends SugarRecord<StudentMeetingDataModel> {
    private String name;
    private String year;
    private String branch;
    private String dateTime;
    private String sec;

    public StudentMeetingDataModel() {
        //this is required
    }

    public StudentMeetingDataModel(String name, String year, String branch, String dateTime, String sec) {
        this.name = name;
        this.year = year;
        this.branch = branch;
        this.dateTime = dateTime;
        this.sec = sec;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getSec() {
        return sec;
    }

    public void setSec(String sec) {
        this.sec = sec;
    }

    public String getBranch() {
        return branch;
    }

    public void setBrach(String branch) {
        this.branch = branch;
    }


}
