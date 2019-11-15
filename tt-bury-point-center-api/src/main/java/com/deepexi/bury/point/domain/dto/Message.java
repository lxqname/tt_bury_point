package com.deepexi.bury.point.domain.dto;

import cn.hutool.json.JSONObject;

import java.io.Serializable;

/**
 * @Author: 白猛
 * @Date: 2019/11/15 16:19
 */
public class Message implements Serializable {

    private String id;

    private String env;

    private String time;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
