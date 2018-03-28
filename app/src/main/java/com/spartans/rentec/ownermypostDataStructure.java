package com.spartans.rentec;


import android.graphics.Bitmap;

public class ownermypostDataStructure {
    private Bitmap imgmypost;
    private String firstline;
    private String secondline;
    private String rentview;
    private String rent;
    private String postid;

    public ownermypostDataStructure(String postid,Bitmap imgmypost, String firstline, String secondline, String rentview, String rent) {
        this.imgmypost = imgmypost;
        this.firstline = firstline;
        this.secondline = secondline;
        this.rentview = rentview;
        this.rent = rent;
        this.postid=postid;
    }

    public Bitmap getImgmypost() {
        return imgmypost;
    }

    public void setImgmypost(Bitmap imgmypost) {
        this.imgmypost = imgmypost;
    }

    public String getFirstline() {
        return firstline;
    }

    public void setFirstline(String firstline) {
        this.firstline = firstline;
    }

    public String getSecondline() {
        return secondline;
    }

    public void setSecondline(String secondline) {
        this.secondline = secondline;
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
