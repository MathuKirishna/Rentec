package com.spartans.rentec;


import java.util.Date;

public class ChatMessage {
    private String messagetxt;
    private String messageuser;
    private long messagetime;

    public ChatMessage(String messagetxt, String messageuser) {
        this.messagetxt = messagetxt;
        this.messageuser = messageuser;

        this.messagetime=new Date().getTime();
    }

    public ChatMessage() {

    }

    public String getMessagetxt() {
        return messagetxt;
    }

    public void setMessagetxt(String messagetxt) {
        this.messagetxt = messagetxt;
    }

    public String getMessageuser() {
        return messageuser;
    }

    public void setMessageuser(String messageuser) {
        this.messageuser = messageuser;
    }

    public long getMessagetime() {
        return messagetime;
    }

    public void setMessagetime(long messagetime) {
        this.messagetime = messagetime;
    }
}
