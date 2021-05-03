package com.example.tutorfinder.Database;

public class NotificationModel {
    String notification,timestamp;

    public NotificationModel(){}

    public NotificationModel(String notification, String timestamp) {
        this.notification = notification;
        this.timestamp = timestamp;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
