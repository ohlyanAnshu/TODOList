package com.example.todo;

public class MyTasks {

    String taskTitle;
    String date;
    String desc;
    String time;
    String taskKey;

    public MyTasks() {
    }

    public MyTasks(String taskTitle, String date, String desc, String time, String taskKey) {
        this.taskTitle = taskTitle;
        this.date = date;
        this.desc = desc;
        this.time = time;
        this.taskKey = taskKey;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }
}

