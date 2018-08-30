package com.bcm.havoc.gnapp.ServerBean;

/**
 * Created by win on 2017/6/6.
 */

public class resultBean {
    String Status;
    String Msg;

    public resultBean(String status, String msg) {
        Status = status;
        Msg = msg;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    @Override
    public String toString() {
        return "resultBean{" +
                "Status='" + Status + '\'' +
                ", Msg='" + Msg + '\'' +
                '}';
    }
}
