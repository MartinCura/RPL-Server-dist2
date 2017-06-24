package com.rpl.POJO;

import com.rpl.model.Activity;
import com.rpl.model.reports.Report3;

import java.util.List;
import java.util.stream.Collectors;

public class ReportCoursePOJO {
    private List<ActivityNamePOJO> activities;
    private List<Report3> data;

    public ReportCoursePOJO(List<Activity> activities, List<Report3> data) {
        this.activities = activities.stream().map(a -> new ActivityNamePOJO(a)).collect(Collectors.toList());
        this.data = data;
    }

    public List<ActivityNamePOJO> getActivities() {
        return activities;
    }

    public List<Report3> getData() {
        return data;
    }
}
