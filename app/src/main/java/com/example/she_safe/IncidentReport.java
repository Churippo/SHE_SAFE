package com.example.she_safe;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class IncidentReport {

    private String title;
    private String description;
    private String date;
    private String time;
    @ServerTimestamp
    private Date timestamp;

    public IncidentReport() {
    }

    public IncidentReport(String title, String description, String date, String time) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("title", title);
        map.put("description", description);
        map.put("date", date);
        map.put("time", time);
        map.put("timestamp", timestamp);
        return map;
    }
}
