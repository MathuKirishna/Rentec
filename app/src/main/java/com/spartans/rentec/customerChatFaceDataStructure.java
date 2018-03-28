package com.spartans.rentec;



public class customerChatFaceDataStructure {
    private String customername;
    private String customernumber;
    private int notificationnumber;
    private String passid;

    public customerChatFaceDataStructure(String passid,int notificationnumber,String customername, String customernumber) {
        this.customername = customername;
        this.customernumber = customernumber;
        this.notificationnumber=notificationnumber;
        this.passid=passid;
    }

    public String getPassid() {
        return passid;
    }

    public void setPassid(String passid) {
        this.passid = passid;
    }

    public int getNotificationnumber() {
        return notificationnumber;
    }

    public void setNotificationnumber(int notificationnumber) {
        this.notificationnumber = notificationnumber;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getCustomernumber() {
        return customernumber;
    }

    public void setCustomernumber(String customernumber) {
        this.customernumber = customernumber;
    }
}
