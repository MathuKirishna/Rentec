package com.spartans.rentec;

/**
 * Created by Mathu on 1/24/2018.
 */

public class SelectLawyerDataStructure {
    private String lawyername;
    private String fee;
    private String lawyerid;

    public SelectLawyerDataStructure(String lawyername, String fee, String lawyerid) {
        this.lawyername = lawyername;
        this.fee = fee;
        this.lawyerid = lawyerid;
    }

    public SelectLawyerDataStructure() {
    }

    public String getLawyername() {
        return lawyername;
    }

    public void setLawyername(String lawyername) {
        this.lawyername = lawyername;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getLawyerid() {
        return lawyerid;
    }

    public void setLawyerid(String lawyerid) {
        this.lawyerid = lawyerid;
    }
}
