package com.spartans.rentec;


import android.graphics.Bitmap;

public class WallPostDataStructure {
    private String line4;
    private String line1;
    private String line2;
    private String line3;
    private Bitmap wallimg;
    private String rentview;
    private String rent;
    private String postid;
    private String ownerid;
    private double longtitude;
    private double latitude;

    public WallPostDataStructure(double longtitude,double latitude, String ownerid,String line1, String line2, String line3,String line4, Bitmap wallimg, String rentview, String rent, String postid) {
        this.line1 = line1;
        this.line2 = line2;
        this.line3 = line3;
        this.wallimg = wallimg;
        this.rentview = rentview;
        this.rent = rent;
        this.postid = postid;
        this.line4=line4;
        this.ownerid=ownerid;
        this.longtitude=longtitude;
        this.latitude=latitude;
    }

    public WallPostDataStructure() {
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(String ownerid) {
        this.ownerid = ownerid;
    }

    public String getLine4() {
        return line4;
    }

    public void setLine4(String line4) {
        this.line4 = line4;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getLine3() {
        return line3;
    }

    public void setLine3(String line3) {
        this.line3 = line3;
    }

    public Bitmap getWallimg() {
        return wallimg;
    }

    public void setWallimg(Bitmap    wallimg) {
        this.wallimg = wallimg;
    }

    public String getRentview() {
        return rentview;
    }

    public void setRentview(String rentview) {
        this.rentview = rentview;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

}
