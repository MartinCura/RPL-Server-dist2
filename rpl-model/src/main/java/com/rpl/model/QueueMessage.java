package com.rpl.model;

public class QueueMessage {

    private String msg;

    public QueueMessage() {
        //Default ctor needed to seralize into JSON
    }

    public QueueMessage(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
