package com.example.tutorfinder.StudentModels;

public class ChatHelperClass {

    String groupTitle,participents;

    public ChatHelperClass() {}

    public ChatHelperClass(String groupTitle, String participents) {
        this.groupTitle = groupTitle;
        //this.participents = participents;
    }

    public String getGroupTitle() {
        return groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

//    public String getParticipents() {
//        return participents;
//    }
//
//    public void setParticipents(String participents) {
//        this.participents = participents;
//    }
}
