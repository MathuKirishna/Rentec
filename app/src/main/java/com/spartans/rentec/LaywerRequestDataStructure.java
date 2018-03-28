package com.spartans.rentec;

/**
 * Created by Mathu on 1/24/2018.
 */

public class LaywerRequestDataStructure {

    private String firstline;
    private String secondline;
    private String notificationid;
    private String customerid;
    private String thirdline;
    private String forthline;


    public LaywerRequestDataStructure(String customerid,String firstline, String secondline, String thirdline,String forthline, String notificationid) {
        this.firstline = firstline;
        this.secondline = secondline;
        this.notificationid = notificationid;
        this.forthline=forthline;
        this.thirdline = thirdline;
        this.customerid=customerid;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getForthline() {
        return forthline;
    }

    public void setForthline(String forthline) {
        this.forthline = forthline;
    }

    public String getfirstline() {
        return firstline;
    }

    public void setfirstline(String firstline) {
        this.firstline = firstline;
    }

    public String getsecondline() {
        return secondline;
    }

    public void setsecondline(String secondline) {
        this.secondline = secondline;
    }

    public String getNotificationid() {
        return notificationid;
    }

    public void setNotificationid(String notificationid) {
        this.notificationid = notificationid;
    }

    public String getthirdline() {
        return thirdline;
    }

    public void setthirdline(String thirdline) {
        this.thirdline = thirdline;
    }
}
