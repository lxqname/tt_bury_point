package com.deepexi.bury.point.domain.dto;

import cn.hutool.json.JSONObject;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: 白猛
 * @Date: 2019/11/15 16:19
 */
public class Message implements Serializable {

    private String id;

    private String env;

    private Date ctime;

    private String type;

    private JSONObject event;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public JSONObject getEvent() {
        return event;
    }

    public void setEvent(JSONObject event) {
        this.event = event;
    }
}
