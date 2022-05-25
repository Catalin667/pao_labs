package com.company.users;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.UUID;

public class ActionUser {
    private static final UUID id = UUID.randomUUID();
    private String user;
    private String action;
    private String date;

    public ActionUser(){}

    public ActionUser(String user, String action, String date) {
        this.user = user;
        this.action = action;
        this.date = date;
    }

    public UUID getId(){return id;}

    public String getUser() {
        return user;
    }

    public ActionUser setUser(String user) {
        this.user = user;
        return this;
    }

    public String getAction() {
        return action;
    }

    public ActionUser setAction(String action) {
        this.action = action;
        return this;
    }

    public String getDate() {
        return date;
    }

    public ActionUser setDate(String date) {
        this.date = date;
        return this;
    }

    @Override
    public String toString() {
        return "Audit{" +
                "user='" + user + '\'' +
                ", action='" + action + '\'' +
                ", date=" + date +
                '}';
    }
}
