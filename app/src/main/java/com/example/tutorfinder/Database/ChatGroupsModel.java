package com.example.tutorfinder.Database;

public class ChatGroupsModel {
    String groupTitle;
    public ChatGroupsModel(){}
    public ChatGroupsModel(String groupTitle) {
        this.groupTitle = groupTitle;
    }

    public String getGroupTitle() {
        return groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }
}
